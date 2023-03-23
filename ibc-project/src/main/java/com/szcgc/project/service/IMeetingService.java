package com.szcgc.project.service;

import com.szcgc.comm.IbcService;
import com.szcgc.project.model.MeetingInfo;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: chenxinli
 * @Date: 2020/8/7 16:06
 * @Description:
 */
public interface IMeetingService extends IbcService<MeetingInfo, Integer> {

    List<MeetingInfo> findByDate(LocalDate startDate, LocalDate endDate);
}
