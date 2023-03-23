package com.szcgc.customer.constant;

import com.szcgc.comm.model.IbcTree;
import io.swagger.v3.oas.annotations.media.Schema;
import org.apache.commons.compress.utils.Lists;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 客户模块字典
 *
 * @author chenjiaming
 * @date 2022-9-30 11:48:47
 */
public class CustomerConfigDic {

    public static final CustomerConfigDic INSTANCE = new CustomerConfigDic();

    @Schema(description = "客户类型")
    public Map<String, String> custType = new HashMap<>(3);
    public List<IbcTree> custTypeList = Lists.newArrayList();

    @Schema(description = "是否类型")
    public Map<String, String> whether = new HashMap<>(3);
    public List<IbcTree> whetherList = Lists.newArrayList();

    @Schema(description = "证件类型")
    public Map<String, String> certificateType = new HashMap<>(5);
    public List<IbcTree> certificateTypeList = Lists.newArrayList();

    @Schema(description = "国籍")
    public Map<String, String> nationality = new HashMap<>(21);
    public List<IbcTree> nationalityList = Lists.newArrayList();

    @Schema(description = "股东类型")
    public Map<String, String> shareholderType = new HashMap<>(3);
    public List<IbcTree> shareholderTypeList = Lists.newArrayList();

    private CustomerConfigDic() {

        Arrays.stream(DicEnum.values()).forEach(item -> {
            if (item.getType().equals(DicTypeEnum.NATIONALITY)) {
                nationality.put(String.valueOf(item.getValue()), item.getName());
                nationalityList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().equals(DicTypeEnum.CERTIFICATE_TYPE)) {
                certificateType.put(String.valueOf(item.getValue()), item.getName());
                certificateTypeList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().equals(DicTypeEnum.WHETHER)) {
                whether.put(String.valueOf(item.getValue()), item.getName());
                whetherList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().equals(DicTypeEnum.CUST_TYPE)) {
                custType.put(String.valueOf(item.getValue()), item.getName());
                custTypeList.add(IbcTree.of(item.getValue(), item.getName()));
            }
            if (item.getType().equals(DicTypeEnum.SHAREHOLDER_TYPE)) {
                shareholderType.put(String.valueOf(item.getValue()), item.getName());
                shareholderTypeList.add(IbcTree.of(item.getValue(), item.getName()));
            }
        });
    }
}
