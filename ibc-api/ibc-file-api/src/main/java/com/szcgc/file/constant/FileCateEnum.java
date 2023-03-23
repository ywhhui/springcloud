package com.szcgc.file.constant;

/**
 * @Author liaohong
 * @create 2020/11/3 14:44
 */
public enum FileCateEnum {

    Cl_Dbszb("担保申请表", FileCateTopEnum.CL),
    Cl_Qyzp("签约照片", FileCateTopEnum.CL),
    Cl_Xmpsyjb("项目评审意见表", FileCateTopEnum.CL),
    Cl_Dbyxs("担保意向书", FileCateTopEnum.CL),
    Cl_Fktzs("放款通知书", FileCateTopEnum.CL),
    Cl_Hjbhtzs("出具保函通知书", FileCateTopEnum.CL),
    Cl_Hkzms("还款证明书", FileCateTopEnum.CL),
    Cl_Ssxxb("涉税信息登记表", FileCateTopEnum.CL),
    Cl_Qygmzmcl("企业规模证明材料", FileCateTopEnum.CL),
    Cl_Dzydjz("质抵押登记证", FileCateTopEnum.CL),
    Cl_Fkhz("放款回执", FileCateTopEnum.CL),
    Cl_Pgzj("评估作价报告", FileCateTopEnum.CL),
    Cl_Xmzzbb("项目终止报告", FileCateTopEnum.CL),
    Cl_Xmzztzs("项目终止通知书", FileCateTopEnum.CL),
    Cl_Xmclyjb("项目处理意见表", FileCateTopEnum.CL),
    Cl_Bhjzjlb("保后检查记录表", FileCateTopEnum.CL),
    Cl_Bhsmj("保函扫描件", FileCateTopEnum.CL),//用于保函项目
    Cl_Qtbczl("其他补充资料", FileCateTopEnum.CL),//用于保函项目
    Cl_Bzzrzztzs("保证责任终止通知书", FileCateTopEnum.CL),//用于保函项目
    Cl_Fdbwpgzjyjs_Fdc("反担保物评估作价意见书(房地产)", FileCateTopEnum.CL),
    Cl_Fdbwpgzjyjs_Td("反担保物评估作价意见书(土地)", FileCateTopEnum.CL),
    Cl_Fdbwpgzjyjs_Gp("反担保物评估作价意见书(股票)", FileCateTopEnum.CL),
    Cl_Dzbhfj("电子保函相关附件", FileCateTopEnum.CL),//用于电子保函
    Cl_examineBhAttachment("保函保后检查相关附件", FileCateTopEnum.CL),
    Cl_Chtzs("出函通知书", FileCateTopEnum.CL),
    Cl_Fcz("房产证", FileCateTopEnum.CL),
    Cl_Tudi("土地", FileCateTopEnum.CL),
    Cl_Zlwj("专利文件", FileCateTopEnum.CL),
    Cl_Zzql("软件著作权文件", FileCateTopEnum.CL),
    Cl_Sbzc("商标注册证", FileCateTopEnum.CL),
    Cl_Fczgr("房产证(个人保证)", FileCateTopEnum.CL),
    Cl_Tudigr("土地(个人保证)", FileCateTopEnum.CL),
    Cl_Zlwjgr("专利文件(个人保证)", FileCateTopEnum.CL),
    Cl_Zzqlgr("软件著作权文件(个人保证)", FileCateTopEnum.CL),
    Cl_Sbzcgr("商标注册证(个人保证)", FileCateTopEnum.CL),
    Cl_QtQk("其他", FileCateTopEnum.CL),

    Bg_Ptywpsbg("普通类业务评审报告", FileCateTopEnum.BG),
    Bg_Gpzywpsbg("股票质押业务评审报告", FileCateTopEnum.BG),
    Bg_Fzywpsbg("发债担保业务评审报告", FileCateTopEnum.BG),
    Bg_Bhywpsbg("保函评审报告", FileCateTopEnum.BG),
    Bg_Bhywjbpsbg("保函简版评审报告", FileCateTopEnum.BG),
    Bg_Zxbg("征信报告", FileCateTopEnum.BG),
    Bg_Fdbwbg("反担保物评估报告", FileCateTopEnum.BG),
    Bg_CommentB("B角报告", FileCateTopEnum.BG),

    Ht_Zdy("非模板合同", FileCateTopEnum.HT),
    Ht_Mb("模板合同", FileCateTopEnum.HT),
    Ht_Bhyb("保函样本", FileCateTopEnum.HT),
    Ht_Htwb("合同文本", FileCateTopEnum.HT),    //为了兼容历史数据设立的


    ;

    private String cnName;
    private FileCateTopEnum top;

    FileCateEnum(String cnName, FileCateTopEnum top) {
        this.cnName = cnName;
        this.top = top;
    }

    public String getCnName() {
        return cnName;
    }

    public FileCateTopEnum getTop() {
        return top;
    }

    public boolean isPsbg() {
        return this == Bg_Ptywpsbg || this == Bg_Gpzywpsbg || this == Bg_Fzywpsbg || this == Bg_Bhywpsbg;
    }
}
