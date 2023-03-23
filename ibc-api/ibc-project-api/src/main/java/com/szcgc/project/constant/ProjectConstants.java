package com.szcgc.project.constant;

import java.util.Arrays;
import java.util.List;

/**
 * @Author liaohong
 * @create 2020/9/17 11:22
 */
public class ProjectConstants {

    public static int DURING_UNIT_DAY = 1;
    public static int DURING_UNIT_MONTH = 2;
    public static int DURING_UNIT_YEAR = 3;


    public static String AUDIT_PASS = "pass";//通过
    public static String AUDIT_HOLD = "hold";//补充调研

    public static String[] operateObject = new String[]{
            "", //顺序一定不可随便修改
            "深圳担保集团有限公司",                      //1
            "深圳市中小企业融资担保有限公司",            //2
            "深圳市金鼎信典当行有限公司",                //3
            "深圳市中小担小额贷款有限公司",              //4
            "深圳市汇博创业成长投资有限公司",            //5
            "深圳市前海汇博互联网金融服务有限公司",      //6
            "汕头市中小企业融资担保有限公司",            //7
            "深圳市中小担商业保理有限公司",              //8
            "深圳市深担增信融资担保有限公司",            //9
            "深圳市中小担非融资性担保有限公司",          //10
    };

    public static String MEET_ONE = "one";
    public static String MEET_TWO = "two";
    public static String MEET_SPEC = "spec";
    public static String MEET_TECH = "tech";
    public static String MEET_ANNOTATE = "annotate";
    public static String MEET_STOP = "stop";
    public static List<String> MEETS = Arrays.asList(ProjectConstants.MEET_SPEC, ProjectConstants.MEET_ONE, ProjectConstants.MEET_TWO, ProjectConstants.MEET_TECH);

    public static int SUPERVISOR_STATUS_NML = 1;    //正常
    public static int SUPERVISOR_STATUS_DEL = 2;    //失效

    public static String PROPOSAL_FLOW_MEETING = "meeting";  // "上会"
    public static String PROPOSAL_FLOW_ANNOTATE = "annotate";    //"签批"
    public static String PROPOSAL_FLOW_STOP = "stop";        //"暂缓"
    public static String PROPOSAL_FLOW_REVIEW = "review";      //"专审"   //融资业务
    public static String PROPOSAL_FLOW_ANNOTATE_RISK = "annotate_risk";//"风险签批"   //保函
    public static String PROPOSAL_FLOW_ANNOTATE_MANAGER = "annotate_manager";//"总经理签批"   //保函


    public static String EVALUATE_FLOW_PASS = "pass";  // "通过"
    public static String EVALUATE_FLOW_HOLD = "hold";    //"补充调研"
    public static String EVALUATE_FLOW_STOP = "stop";      //"暂缓"
    public static String EVALUATE_FLOW_ADDENDUM = "addendum";        //"补充评审信息" //保函业务

    public static String ANNOTATE_FLOW_MEETING = "meeting";  // "上会"
    public static String ANNOTATE_FLOW_RETRY = "retry";  // "重新申请"("补充调研")
    public static String ANNOTATE_FLOW_PASS = "pass";       //"通过"
    public static String ANNOTATE_FLOW_STOP = "stop";        //"暂缓"
    public static String ANNOTATE_FLOW_GROUP = "group";        //"转给集团审批"
    public static long ANNOTATE_AMOUNT = 300 * 10000 * 100;        //300w以下由子公司风控，300w以上由集团风控

    public static String[] GRADE = {"特级", "一级", "二级", "三级"};

    public static String COMPANYADDRESS = "深圳市南山区粤海街道滨海社区高新南十道87、89、91号软件产业基地2栋C16层1604";//公司地址

    public static String[] index = new String[]{"", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十"};

    public static String QK_PASS = "1";
    public static String QK_RETURN = "2";

}
