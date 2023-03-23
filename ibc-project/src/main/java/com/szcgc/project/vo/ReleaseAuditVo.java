package com.szcgc.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDateTime;

/**
 * 解保审核提交 需要的参数
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReleaseAuditVo {

    public static final int AUDIT_YES = 1;
    private static final int AUDIT_NO = 2;

    @Schema(name = "项目Id")
    private int projectId;

    /**
     * 任务id
     */
    private String taskId;

    @Schema(description = "审核用户Id")
    private int auditBy;

    @Schema(description = "审核时间")
    private LocalDateTime auditAt;

    @Schema(description = "审核意见(1:同意,2:不同意)")
    private int auditRst;

    @Schema(description = "审核意见")
    private String auditOpinion;

    public void setAuditRst(int auditRst) {
        if (auditRst != AUDIT_YES && auditRst != AUDIT_NO)
            throw new RuntimeException("error param" + auditRst);
        this.auditRst = auditRst;
    }

    public boolean isAuditYes() {
        return auditBy != 0 && auditRst == AUDIT_YES;
    }
}
