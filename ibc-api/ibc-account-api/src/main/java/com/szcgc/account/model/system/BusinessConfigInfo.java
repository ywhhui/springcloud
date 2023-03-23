package com.szcgc.account.model.system;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szcgc.comm.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

/**
 * @Author liaohong
 * @create 2020/9/4 15:29
 */
@Entity
@Table(name = "s_businesscfginfo", schema = "gmis_account")
public class BusinessConfigInfo {

    @Id
    @Column(name = "id", length = 11)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Schema(description = "名称")
    @Column(length = 50)
    private String name;

    @Schema(description = "业务类型")
    @Column(length = 50)
    private String businessType;

    @JsonIgnore
    @Schema(description = "流程序列")
    @Column(length = 200)
    private String processSequence;

    @JsonIgnore
    @Schema(description = "必选文件")
    @Column(length = 200)
    private String requireFile;

    @JsonIgnore
    @Schema(description = "可选文件")
    @Column(length = 200)
    private String optionFile;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType;
    }

    public String getProcessSequence() {
        return processSequence;
    }

    public void setProcessSequence(String processSequence) {
        this.processSequence = processSequence;
    }

    public String getRequireFile() {
        return requireFile;
    }

    public void setRequireFile(String requireFile) {
        this.requireFile = requireFile;
    }

    public String getOptionFile() {
        return optionFile;
    }

    public void setOptionFile(String optionFiles) {
        this.optionFile = optionFiles;
    }

    @Transient
    private String[] processes;

    @Transient
    private String[] rqrFiles;

    @Transient
    private String[] optFiles;

    public String[] getProcesses() {
        if (processes != null && !StringUtils.isEmpty(processSequence)) {
            processes = processSequence.split(",");
        }
        return processes;
    }

    public String[] getRqrFiles() {
        if (requireFile != null && !StringUtils.isEmpty(requireFile)) {
            rqrFiles = requireFile.split(",");
        }
        return rqrFiles;
    }

    public String[] getOptFiles() {
        if (optionFile != null && !StringUtils.isEmpty(optionFile)) {
            optFiles = optionFile.split(",");
        }
        return optFiles;
    }

//    public String getNextProcess(String name) {
//        //这里要调用getProcesses()，以便做一个初始化操作
//        String[] pes = getProcesses();
//        if (pes == null || pes.length <= 0)
//            return null;
//        for (int i = 0; i < processes.length; i++) {
//            if (name.equals(processes[i])) {
//                if (i < processes.length - 1)
//                    return processes[i + 1];
//            }
//        }
//        return null;
//    }
}
