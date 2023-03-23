package com.szcgc.cougua.constant;

import com.szcgc.comm.model.IbcTree;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.*;

/**
 * @Author liaohong
 * @create 2022/9/21 14:13
 */
public class GuaranteeConfigDic {

    public static final GuaranteeConfigDic INSTANCE = new GuaranteeConfigDic();

    @Schema(description = "行政区域")
    public Map<String, String> fdcDistrict = new HashMap<>(20);
    public List<IbcTree> fdcDistrictList ;

    @Schema(description = "应收账款类型")
    public Map<String, String> receiveType = new HashMap<>(20);
    public List<IbcTree> receiveTypeList ;

    @Schema(description = "专利类型")
    public Map<String, String> patentType = new HashMap<>(20);
    public List<IbcTree> patentTypeList ;

    @Schema(description = "存款性质")
    public Map<String, String> depositType = new HashMap<>(20);
    public List<IbcTree> depositTypeList ;

    @Schema(description = "账户状态")
    public Map<String, String> billType = new HashMap<>(20);
    public List<IbcTree> billTypeList ;

    @Schema(description = "评估状态")
    public Map<String, String> assessedStatus = new HashMap<>(20);
    public List<IbcTree> assessedStatusList ;

    @Schema(description = "担保类型")
    public List<IbcTree> guranteeTypeList = new ArrayList<>();

    private GuaranteeConfigDic() {

        fdcDistrict.put(GuaranteeConstant.DIST_SZ, "深圳市");
        fdcDistrict.put(GuaranteeConstant.DIST_SXQ, "市辖区");
        fdcDistrict.put(GuaranteeConstant.DIST_LH, "罗湖区");
        fdcDistrict.put(GuaranteeConstant.DIST_FT, "福田区");
        fdcDistrict.put(GuaranteeConstant.DIST_NS, "南山区");
        fdcDistrict.put(GuaranteeConstant.DIST_BA, "宝安区");
        fdcDistrict.put(GuaranteeConstant.DIST_LG, "龙岗区");
        fdcDistrict.put(GuaranteeConstant.DIST_YT, "盐田区");
        fdcDistrictList = IbcTree.of(fdcDistrict);

        receiveType.put(GuaranteeConstant.RECEIVE_ONE, "发票");
        receiveType.put(GuaranteeConstant.RECEIVE_TWO, "结算单");
        receiveType.put(GuaranteeConstant.RECEIVE_THREE, "贸易合同");
        receiveType.put(GuaranteeConstant.RECEIVE_FOUR, "其他应收账款信息");
        receiveTypeList = IbcTree.of(receiveType);

        patentType.put(GuaranteeConstant.PATENT_ONE, "发明");
        patentType.put(GuaranteeConstant.PATENT_TWO, "实用新型");
        patentType.put(GuaranteeConstant.PATENT_THREE, "外观设计");
        patentTypeList = IbcTree.of(patentType);

        depositType.put(GuaranteeConstant.ACCDE_ONE, "活期");
        depositType.put(GuaranteeConstant.ACCDE_TWO, "定期");
        depositType.put(GuaranteeConstant.ACCDE_THREE, "其他");
        depositTypeList = IbcTree.of(depositType);

        billType.put(GuaranteeConstant.ACCT_ONE, "正常");
        billType.put(GuaranteeConstant.ACCT_TWO, "逾期");
        billType.put(GuaranteeConstant.ACCT_THREE, "利息转表外");
        billType.put(GuaranteeConstant.ACCT_FOUR, "核销");
        billType.put(GuaranteeConstant.ACCT_FIVE, "结清");
        billTypeList = IbcTree.of(billType);

        assessedStatus.put(GuaranteeConstant.ASSESSED_ONE, "未评估");
        assessedStatus.put(GuaranteeConstant.ASSESSED_TWO, "已评估");
        assessedStatusList = IbcTree.of(assessedStatus);

        Arrays.stream(CounterGuaranteeOneCateEnum.values()).forEach(item ->{
            IbcTree ibcTree = IbcTree.of(item.getCnName(),item.name());
            List<IbcTree> childrens = new ArrayList<>();
            Arrays.stream(CounterGuaranteeTwoCateEnum.values()).forEach(childItem ->{
                if(item.name().equals(childItem.getCate().name())){
                    IbcTree twoTypeVo = IbcTree.of(childItem.getCnName(),childItem.name());
                    List<IbcTree> tChildrens = new ArrayList<>();
                    Arrays.stream(CounterGuaranteeThreeCateEnum.values()).forEach(tChildItem ->{
                        if(childItem.name().equals(tChildItem.getCate().name())){
                            IbcTree threeTypeVo = IbcTree.of(tChildItem.getCnName(),tChildItem.name());
                            tChildrens.add(threeTypeVo);
                        }
                    });
                    if(null != tChildrens && tChildrens.size() >0){
                        twoTypeVo.setChildren(tChildrens);
                    }
                    childrens.add(twoTypeVo);
                }
            });
            if(null != childrens && childrens.size() >0){
                ibcTree.setChildren(childrens);
            }
            guranteeTypeList.add(ibcTree);
        });

    }
}
