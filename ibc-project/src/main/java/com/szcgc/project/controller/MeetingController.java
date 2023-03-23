package com.szcgc.project.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcResponse;
import com.szcgc.comm.model.IbcTree;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.comm.util.SundryUtils;
import com.szcgc.comm.web.IbcId;
import com.szcgc.comm.web.WebConstants;
import com.szcgc.project.constant.ProjectConstants;
import com.szcgc.project.model.AttendInfo;
import com.szcgc.project.model.MeetingInfo;
import com.szcgc.project.service.IAttendService;
import com.szcgc.project.service.IMeetingService;
import com.szcgc.project.vo.meet.*;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.szcgc.comm.util.SundryUtils.page;
import static com.szcgc.comm.util.SundryUtils.size;

/**
 * @Author liaohong
 * @create 2022/10/13 10:42
 * <p>
 * 1.新增会议（会议时间，主持人，评委）
 * 2.选择项目到会议中(更新AttendInfo表中的meetId，带入默认的主持人和评委)
 * 3.重新设置项目的主持人(更新AttendInfo表中的主持人和评委)(保存该项目排会的taskId)
 * 4.提交会议(taskId都不会为空的情况下才允许提交)
 */
@Api(tags = "项目评审-排会")
@RestController
@RequestMapping("eva-meet")
public class MeetingController {

    @Autowired
    IMeetingService meetingService;

    @Autowired
    IAttendService attendService;

    @Operation(summary = "会议列表")
    @GetMapping("meets" + WebConstants.INDEX)
    public IbcResponse<IbcPager<MeetingInfo>> listMeeting(
            @RequestParam(name = WebConstants.PGNM, required = false) Integer page,
            @RequestParam(name = WebConstants.PGSZ, required = false) Integer size,
            @RequestParam(value = "startDate", required = false) String startDate,
            @RequestParam(value = "endDate", required = false) String endDate) {
        LocalDate start = SundryUtils.tryGetLocalDateOrNull(startDate);
        if (start == null)
            return IbcResponse.ok(IbcPager.of(meetingService.findAll(page(page), size(size))));
        LocalDate end = SundryUtils.tryGetLocalDate(endDate);
        return IbcResponse.ok(IbcPager.of(meetingService.findByDate(start, end)));
    }

    @Operation(summary = "新增会议")
    @PostMapping("meets" + WebConstants.INSERT)
    public IbcResponse<MeetingInfo> addMeeting(@IbcId @Parameter(hidden = true) int accountId,
                                               @RequestBody MeetingInfo info) {
        info.setCreateBy(accountId);
        info.setCreateAt(LocalDateTime.now());
        info.setUpdateAt(info.getCreateAt());
        meetingService.insert(info);
        return IbcResponse.ok(info);
    }

    @Operation(summary = "删除会议")
    @PostMapping("meets" + WebConstants.DELETE)
    public IbcResponse<String> delMeeting(@IbcId @Parameter(hidden = true) int accountId,
                                          @RequestParam("meetingId") int meetingId) {
        attendService.clear(meetingId);
        meetingService.delete(meetingId);
        return IbcResponse.ok();
    }

    @Operation(summary = "提交会议")
    @PostMapping("meets/confirm")
    public IbcResponse<String> delAttendMeeting(@IbcId @Parameter(hidden = true) int accountId,
                                                @RequestParam("meetingId") int meetingId) {
        return IbcResponse.ok();
    }

    @Operation(summary = "会议主持人评委记录员名单列表")
    @GetMapping("judge" + WebConstants.INDEX)
    public IbcResponse<MeetingDicVo> listMeetingDic() {
        List<IbcTree> infos = Lists.newArrayList(IbcTree.of("1", "胡董"),
                IbcTree.of("2", "温总"),
                IbcTree.of("3", "汤总"),
                IbcTree.of("4", "李总"));
        List<IbcTree> infos2 = new ArrayList<>(infos);
        infos2.add(IbcTree.of("5", "蔡总"));
        infos2.add(IbcTree.of("6", "陈总"));
        List<IbcTree> infos3 = Lists.newArrayList(IbcTree.of("10", "廖侑宏"),
                IbcTree.of("12", "江丹"));
        MeetingDicVo vo = new MeetingDicVo();
        vo.hosts = infos;
        vo.judges = infos2;
        vo.recorders = infos3;
        return IbcResponse.ok(vo);
    }

    @Operation(summary = "会议默认主持人评委记录员")
    @GetMapping("judge" + WebConstants.DETAIL)
    public IbcResponse<MeetingDftVo> listMeetingDft(@RequestParam(value = "meetingType") @Parameter(name = "meetingType", example = "会议类型") String meetingType) {
        MeetingDftVo vo = new MeetingDftVo();
        vo.hostId = 3;
        vo.judges = Arrays.asList(1, 2);
        vo.recorderId = 12;
        return IbcResponse.ok(vo);
    }

    @Operation(summary = "待排会项目列表")
    @GetMapping("attend/todo" + WebConstants.INDEX)
    public IbcResponse<List<AttendInfo>> attendTodo(@RequestParam(value = "meetingType", required = false) @Parameter(name = "meetingType", example = "会议类型，可选") String meetingType) {
        List<AttendInfo> infos = attendService.findByMeeting(0);
        if (!StringUtils.isEmpty(meetingType)) {
            infos.removeIf(item -> !meetingType.equals(item.getOpinion()));
        }
        for (AttendInfo info : infos) {
            info.setTaskId("task");
        }
        return IbcResponse.ok(infos);
    }

    @Operation(summary = "已排会项目列表")
    @GetMapping("attend/done" + WebConstants.INDEX)
    public IbcResponse<List<AttendInfo>> attendDone(@RequestParam("meetingId") int meetingId) {
        List<AttendInfo> infos = attendService.findByMeeting(meetingId);
        Map<Integer, String> names = Maps.newHashMap();
        names.put(1, "胡董");
        names.put(2, "温总");
        names.put(3, "汤总");
        names.put(4, "李总");
        names.put(5, "蔡总");
        names.put(6, "陈总");
        for (AttendInfo info : infos) {
            info.setHostName(names.get(info.getHostId()));
            info.setJudgeNames(info.getJudgeIds().stream().map(item -> names.get(item)).collect(Collectors.toList()));
            info.setTaskId("task");
        }
        return IbcResponse.ok(infos);
    }


    @Operation(summary = "设定入会项目")
    @PostMapping("attend/meeting" + WebConstants.INSERT)
    public IbcResponse<String> addAttendMeeting(@IbcId @Parameter(hidden = true) int accountId,
                                                @RequestBody MeetAttendVo vo) {
        attendService.clear(vo.getMeetingId());
        int order = 1;
        LocalDateTime now = LocalDateTime.now();
        MeetingInfo meeting = meetingService.find(vo.getMeetingId()).get();
        for (MeetAttendItemVo item : vo.getItems()) {
            AttendInfo attend = attendService.find(item.getAttendId()).get();
            attend.setMeetingId(meeting.getId());
            attend.setMeetingOrder(order++);
            if (attend.getHostId() == 0) {
                attend.setHostId(meeting.getHostId());
                attend.setJudgeIds(meeting.getJudgeIds());
            }
            attend.setUpdateBy(accountId);
            attend.setUpdateAt(now);
            attendService.update(attend);
        }
        return IbcResponse.ok();
    }

    @Operation(summary = "设定已排会项目评委")
    @PostMapping("attend/judge" + WebConstants.UPDATE)
    public IbcResponse<String> attendJudge(@IbcId @Parameter(hidden = true) int accountId,
                                           @RequestBody AttendJudgeVo vo) {
        AttendInfo attend = attendService.find(vo.getAttendId()).get();
        attend.setHostId(vo.getHostId());
        attend.setJudgeIds(vo.getJudgeIds());
        attend.setConfirmed(AttendInfo.CONFIRM_YES);
        attend.setUpdateBy(accountId);
        attend.setUpdateAt(LocalDateTime.now());
        attendService.update(attend);
        //TODO 修改meetingInfo的confirmed字段
        return IbcResponse.ok();
    }

}
