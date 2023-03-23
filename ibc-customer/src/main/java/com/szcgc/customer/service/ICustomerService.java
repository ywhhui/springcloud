package com.szcgc.customer.service;

import com.szcgc.comm.IbcPager;
import com.szcgc.comm.IbcService;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.customer.vo.CompanyVo;
import com.szcgc.customer.vo.CustomerInfoVo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ICustomerService extends IbcService<CustomerInfo, Integer> {

    Page<CustomerInfo> searchByName(String name, Integer cate, int pageNo, int pageSize);

    List<CustomerInfo> findByName(String name);

    String findName(int customerId);

    List<CustomerInfo> searchCust(String keyword, List<Integer> custIds);

    /**
     * 大数据模糊搜索企业
     *
     * @param keyword 关键字
     * @return 企业简略信息集合
     */
    List<CompanyVo> search(String keyword);

    /**
     * 根据id获取大数据企业信息
     *
     * @param name 企业名称
     * @return 企业信息
     */
    CustomerInfo detailBigdata(String name);

    /**
     * 获取客户分页列表
     *
     * @param custName  客户名称
     * @param custodian 管护人
     * @param page      当前页
     * @param size      每页显示行数
     * @return 客户分页列表
     */
    IbcPager<CustomerInfoVo> list(String custName, String custodian, int page, int size);

    /**
     * 导出数据到评审报告
     *
     * @param accountId 当前用户id
     * @param projectId 项目id
     * @param custId    客户id
     * @param path      模板路径
     * @throws Exception
     */
    void export(Integer accountId, Integer projectId, Integer custId, String path) throws Exception;

    /**
     * 根据统一社会信用代码/证件号获取数据
     *
     * @param idNo 统一社会信用代码/证件号
     * @return 客户数据
     */
    CustomerInfo findByIdNo(String idNo);
}
