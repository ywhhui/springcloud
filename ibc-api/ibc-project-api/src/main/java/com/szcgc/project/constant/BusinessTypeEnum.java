package com.szcgc.project.constant;

/**
 * @Author liaohong
 * @create 2020/9/17 16:49
 * //考虑是否把bussinessType定义为enum，启动的时候，利用BusinessSetting进行初始化
 */
public enum BusinessTypeEnum {

    DKDB_kjt("科技通", BusinessTypeCateEnum.DKDB),
    DKDB_qydkdb("企业贷款担保", BusinessTypeCateEnum.DKDB),
    DKDB_sbcydkdb("社保创业贷款担保", BusinessTypeCateEnum.DKDB,true),
    DKDB_ksdb("科三担保", BusinessTypeCateEnum.DKDB, true),
    DKDB_jgdb("技改担保", BusinessTypeCateEnum.DKDB, true),
    DKDB_gddb("个贷担保", BusinessTypeCateEnum.DKDB, true),

    SXDB_kjt("科技通", BusinessTypeCateEnum.SXDB),
    SXDB_ldzj("流动资金额度担保", BusinessTypeCateEnum.SXDB),
    SXDB_qydk("企业贷款担保", BusinessTypeCateEnum.SXDB),
    SXDB_pjcd("票据承兑担保", BusinessTypeCateEnum.SXDB),
    SXDB_qt("其他融资性业务担保", BusinessTypeCateEnum.SXDB),

    FZDB_fz("发债担保", BusinessTypeCateEnum.FZDB),

    CYZJ_wtdk("市产业技术进步资金委托贷款", BusinessTypeCateEnum.CYZJ),

    KYZJ_wtdk("市科技研发资金委托贷款", BusinessTypeCateEnum.KYZJ),

    WTDK_pwjj("平稳基金", BusinessTypeCateEnum.WTDK),
    WTDK_ssgs("上市公司相关委贷", BusinessTypeCateEnum.WTDK),
    WTDK_csgx("城市更新委贷", BusinessTypeCateEnum.WTDK),
    WTDK_stqy("实体企业委贷", BusinessTypeCateEnum.WTDK),
    WTDK_qt("其它委托业务", BusinessTypeCateEnum.WTDK),

    RZBH_db("单笔融资性保函", BusinessTypeCateEnum.RZBH),
    RZBH_ed("融资性保函额度", BusinessTypeCateEnum.RZBH, true, false),
    RZBH_xx("额度项下融资性保函", BusinessTypeCateEnum.RZBH, false, true),   //额度向下融资性保函

    XEDK_dbxd("单笔小贷", BusinessTypeCateEnum.XEDK),
    XEDK_ed("小贷额度", BusinessTypeCateEnum.XEDK, true, false),
    XEDK_edxx("额度项下小贷", BusinessTypeCateEnum.XEDK, false, true),
    XEDK_xdedxx("汕担额度项下小贷", BusinessTypeCateEnum.XEDK, true),

    DD_dd("典当", BusinessTypeCateEnum.DD),

    BL_db("单笔保理", BusinessTypeCateEnum.BL),
    BL_ed("保理额度", BusinessTypeCateEnum.BL, true, false),
    BL_edxx("额度项下保理", BusinessTypeCateEnum.BL, false, true),

    RZZL_rzzl("融资租赁", BusinessTypeCateEnum.RZZL),

    BH_db("单笔保函", BusinessTypeCateEnum.BH),
    BH_ed("保函额度", BusinessTypeCateEnum.BH, true, false),
    BH_edxx("额度项下保函", BusinessTypeCateEnum.BH, false, true),
    BH_zf("支付保函", BusinessTypeCateEnum.BH),

    CXB_cxb("诚信榜评审", BusinessTypeCateEnum.CXB),

    CYDBD_ptdkdb("普通贷款担保", BusinessTypeCateEnum.CYDBD),
    CYDBD_kjt("科技通", BusinessTypeCateEnum.CYDBD),

    LSYL_wtps("委托评审", BusinessTypeCateEnum.LSYL, true),
    LSYL_tz("投资", BusinessTypeCateEnum.LSYL, true),
    LSYL_ytcyzj("盐田区产业管理资金委托发放", BusinessTypeCateEnum.LSYL, true),
    LSYL_fgwwxjk("发改委无息借款", BusinessTypeCateEnum.LSYL, true),
    LSYL_ssbqdb("诉讼保全担保", BusinessTypeCateEnum.LSYL, true),
    LSYL_zdb("再担保", BusinessTypeCateEnum.LSYL, true),
    LSYL_jxgy("金信共赢", BusinessTypeCateEnum.LSYL, true),
    LSYL_zcbzl("招财宝直连借款", BusinessTypeCateEnum.LSYL, true),
    LSYL_wz("未知", BusinessTypeCateEnum.LSYL, true),

    ;

    private String cnName;

    private BusinessTypeCateEnum cate;

    private boolean isCredit;

    private boolean isCreditItem;

    private boolean isDeprecated;

    BusinessTypeEnum(String cnName, BusinessTypeCateEnum cate) {
        this(cnName, cate, false, false);
    }

    BusinessTypeEnum(String cnName, BusinessTypeCateEnum cate, boolean isCredit, boolean isCreditItem) {
        this.cnName = cnName;
        this.cate = cate;
        this.isCredit = isCredit;
        this.isCreditItem = isCreditItem;
        this.cate.addChildren(this);
    }

    BusinessTypeEnum(String cnName, BusinessTypeCateEnum cate, boolean isDeprecated) {
        this.cnName = cnName;
        this.cate = cate;
        this.isDeprecated = isDeprecated;
        this.cate.addChildren(this);
    }

    public String getCnName() {
        return cnName;
    }

    public BusinessTypeCateEnum getCate() {
        return cate;
    }

    public boolean isCredit() {
        return isCredit;
    }

    public boolean isCreditItem() {
        return isCreditItem;
    }

    public boolean isDeprecated() {
        return isDeprecated;
    }


    private String[] processes;

    private String[] requireFiles;

    private String[] optionalFiles;

    public String[] getProcesses() {
        if (processes != null)
            return processes;
        if (getCate().getTop().isBh()) {
            if (isCredit)
                return DFT_PROCESSES_CREDIT_BH;
            return DFT_PROCESSES_BH;
        }
        if (isCredit)
            return DFT_PROCESSES_CREDIT;
        return DFT_PROCESSES;
    }

    public void setProcesses(String[] processes) {
        this.processes = processes;
    }

    public String[] getRequireFiles() {
        if (requireFiles == null)
            return DFT_RFILE;
        return requireFiles;
    }

    public void setRequireFiles(String[] requireFiles) {
        this.requireFiles = requireFiles;
    }

    public String[] getOptionalFiles() {
        if (optionalFiles == null)
            return DFT_OFILE;
        return optionalFiles;
    }

    public void setOptionalFiles(String[] optionalFiles) {
        this.optionalFiles = optionalFiles;
    }

    private static final String[] DFT_PROCESSES = "preliminary,evaluate,signing,loan,ongoing,terminate".split(",");
    private static final String[] DFT_PROCESSES_CREDIT = "preliminary,evaluate,signing,credit".split(",");
    private static final String[] DFT_PROCESSES_BH = "preliminary,evaluate_bh,signing_bh,loan_bh,ongoing_bh,terminate".split(",");
    private static final String[] DFT_PROCESSES_CREDIT_BH = "preliminary,evaluate_bh,signing_bh,credit_bh".split(",");
    private static final String[] DFT_RFILE = new String[0];
    private static final String[] DFT_OFILE = new String[]{FileCateEnum.Cl_Dbszb.name()};

    public String getNextProcess(String name) {
        //这里要调用getProcesses()，以便做一个初始化操作
        String[] pes = getProcesses();
        if (pes == null || pes.length <= 0)
            return null;
        for (int i = 0; i < pes.length; i++) {
            if (name.equals(pes[i])) {
                if (i < pes.length - 1)
                    return pes[i + 1];
            }
        }
        return null;
    }

    public boolean contains(String name) {
        String[] pes = getProcesses();
        if (pes == null || pes.length <= 0)
            return false;
        for (String ps : pes) {
            if (ps.equals(name))
                return true;
        }
        return false;
    }
}
