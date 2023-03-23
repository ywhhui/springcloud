package com.szcgc.project.vo.evaluate;

import com.szcgc.project.vo.BaseInVo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDate;

/**
 * @Author liaohong
 * @create 2022/10/13 9:07
 */
@Data
public class AttendInVo extends BaseInVo {

    @Schema(description = "拟同意金额")
    private long amount;

    @Schema(description = "拟上会日期(yyyy-MM-dd格式)")
    private LocalDate meetingAt;

    @Schema(description = "会议组别")
    private String opinion;

    @Schema(description = "意见详情")
    private String remarks;
}
