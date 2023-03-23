package com.szcgc.customer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.account.feign.IAccountClient;
import com.szcgc.account.model.AccountInfo;
import com.szcgc.account.model.DepartmentInfo;
import com.szcgc.comm.IbcPager;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.cougua.feign.ICounterGuaranteeClient;
import com.szcgc.cougua.model.CorporationInfo;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.constant.DicTypeEnum;
import com.szcgc.customer.model.*;
import com.szcgc.customer.repository.CustomerRepository;
import com.szcgc.customer.service.*;
import com.szcgc.customer.service.manufacture.InboundChannelService;
import com.szcgc.customer.service.manufacture.ProductStructureService;
import com.szcgc.customer.service.manufacture.ReturnedMoneyService;
import com.szcgc.customer.service.manufacture.SalesRevenueService;
import com.szcgc.customer.util.CalcUtil;
import com.szcgc.customer.util.DocUtil;
import com.szcgc.customer.util.ExcelUtil;
import com.szcgc.customer.vo.CompanyVo;
import com.szcgc.customer.vo.CustomerInfoVo;
import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.feign.FinanceClient;
import com.szcgc.finance.model.analysis.BalanceSheetSimple;
import com.szcgc.finance.model.analysis.FinanceAnalysis;
import com.szcgc.finance.model.analysis.IncomeStatementSimple;
import com.szcgc.third.tyc.feign.ITycClient;
import com.szcgc.third.tyc.model.gs.GsBaseDto;
import com.szcgc.third.tyc.model.search.SearchDto;
import com.szcgc.third.tyc.model.search.SearchItemDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/17 15:59
 */
@Slf4j
@Service
public class CustomerService extends BaseService<CustomerRepository, CustomerInfo, Integer> implements ICustomerService {

    @Resource
    private ITycClient tycClient;

    @Resource
    private IAccountClient accountClient;

    @Resource
    private IGroupItemService groupItemService;

    @Resource
    private ProductStructureService productStructureService;

    @Resource
    private InboundChannelService inboundChannelService;

    @Resource
    private SalesRevenueService salesRevenueService;

    @Resource
    private ReturnedMoneyService returnedMoneyService;

    @Resource
    private EquityStructureService equityStructureService;

    @Resource
    private CustodianService custodianService;

    @Resource
    private TycCompanyService tycCompanyService;

    @Resource
    private ICounterGuaranteeClient guaranteeClient;

    @Resource
    private FinanceClient financeClient;

    @Override
    public Page<CustomerInfo> searchByName(String name, Integer cate, int pageNo, int pageSize) {
        Objects.requireNonNull(name);
        String keys = '%' + name + '%';
        if (cate == null) {
            return repository.findByNameLike(keys, dftPage(pageNo, pageSize));
        }
        return repository.findByNameLikeAndCate(keys, cate, dftPage(pageNo, pageSize));
    }

    @Override
    public List<CustomerInfo> findByName(String name) {
        return repository.findByName(Objects.requireNonNull(name));
    }

    @Override
    public String findName(int customerId) {
        if (customerId <= 0) {
            return null;
        }
        Optional<CustomerInfo> optional = find(customerId);
        if (optional.isPresent()) {
            return optional.get().getName();
        }
        return null;
    }

    @Override
    public List<CustomerInfo> searchCust(String keyword, List<Integer> custIds) {
        return repository.findByIdInAndNameContaining(custIds, keyword);
    }

    @Override
    public List<CompanyVo> search(String keyword) {
        SearchDto searchDto = tycClient.search(keyword, 1, 20);
        log.info("大数据搜索关键字为:{},结果为:{}", keyword, JsonUtils.toJSONString(searchDto));
        if (ObjectUtil.isNull(searchDto) || CollectionUtil.isEmpty(searchDto.items)) {
            return Lists.newArrayList();
        }

        // 统一社会信用代码集合
        List<String> ids = searchDto.items.stream().map(obj -> obj.creditCode).collect(Collectors.toList());
        // 去库里查在库的公司数据
        List<CustomerInfo> customerInfos = repository.findByIdNoIn(ids);
        // 统一社会信用代码为key,客户id为value
        Map<String, Integer> tidCustIdMap = customerInfos.stream().collect(Collectors.toMap(CustomerInfo::getIdNo, CustomerInfo::getId, (o1, o2) -> o2));
        // 根据客户id获取管户信息
        List<Custodian> custodians = custodianService.findByCustomerIdIn(Lists.newArrayList(tidCustIdMap.values()));
        // 以客户id为key,管户人id为value
        Map<Integer, Integer> custIdCustodianMap = custodians.stream().collect(Collectors.toMap(Custodian::getCustomerId, Custodian::getId));

        List<CompanyVo> list = Lists.newArrayList();
        for (SearchItemDto obj : searchDto.items) {
            list.add(CompanyVo.builder()
                    .custId(tidCustIdMap.get(obj.creditCode))
                    .name(obj.name)
                    .custodian(custIdCustodianMap.get(tidCustIdMap.get(obj.creditCode)))
                    .build());
        }

        return list;
    }

    @Override
    public CustomerInfo detailBigdata(String name) {
        GsBaseDto gsBaseDto = tycClient.gs(name);
        log.info("大数据获取工商信息关键字为:{},结果为:{}", name, JsonUtils.toJSONString(gsBaseDto));

        if (ObjectUtil.isNull(gsBaseDto)) {
            return CustomerInfo.builder().build();
        }

        TycCompany company = JsonUtils.parseObject(JsonUtils.toJSONString(gsBaseDto), TycCompany.class);
        company.setLegal(gsBaseDto.legalPersonName);
        company.setIdNo(gsBaseDto.creditCode);
        company.setPhone(gsBaseDto.phoneNumber);
        company.setTid(gsBaseDto.id);
        // 保存天眼查企业数据
        tycCompanyService.insert(company);

        CustomerInfo customerInfo = CustomerInfo.builder().build();
        customerInfo.setName(company.getName());
        customerInfo.setCate(DicEnum.CUST_TYPE_COMPANY.getValue());
        customerInfo.setIdNo(company.getIdNo());
        customerInfo.setLegal(company.getLegal());
        customerInfo.setPhone(company.getPhone());

        return customerInfo;
    }

    @Override
    public IbcPager<CustomerInfoVo> list(String custName, String custodian, int pnum, int psize) {
        custName = StrUtil.nullToDefault(custName, "");
        custodian = StrUtil.nullToDefault(custodian, "");
        Page<CustomerInfo> page;
        if (StrUtil.isNotBlank(custodian)) {
            List<Custodian> custodians = custodianService.findByCustodianNameContaining(custodian);
            List<Integer> custIds = custodians.stream().map(Custodian::getCustomerId).collect(Collectors.toList());
            page = repository.findByNameContainingAndIdInOrderByUpdateAtDesc(custName, custIds, dftPage(pnum, psize));
        } else {
            page = repository.findByNameContainingOrderByUpdateAtDesc(custName, dftPage(pnum, psize));
        }

        List<CustomerInfoVo> customerInfoVos = page.getContent().stream().map(obj -> {
            CustomerInfoVo customerInfoVo = new CustomerInfoVo();
            BeanUtil.copyProperties(obj, customerInfoVo);

            // 翻译核心企业
            GroupItemInfo groupItemInfo = groupItemService.findByCustomerId(obj.getId());
            if (ObjectUtil.isNotNull(groupItemInfo)) {
                CustomerInfo coreCustomerInfo = find(groupItemInfo.getMainId()).orElse(new CustomerInfo());
                customerInfoVo.setCoreCompany(coreCustomerInfo.getName());
            }
            // 还有在保金额未获取等待宏哥接口

            // 翻译管户数据
            Custodian custodianData = custodianService.findTop1ByCustomerId(obj.getId());
            if (ObjectUtil.isNotNull(custodianData)) {
                AccountInfo accountInfo = Optional.ofNullable(accountClient.findAccount(custodianData.getCustodianId())).orElse(new AccountInfo());
                customerInfoVo.setCustodianId(custodianData.getCustodianId());
                customerInfoVo.setCustodianName(accountInfo.getRealName());
                DepartmentInfo departmentInfo = Optional.ofNullable(accountClient.findDepart(accountInfo.getDepartmentId())).orElse(new DepartmentInfo());
                customerInfoVo.setCustodianDeptId(departmentInfo.getId());
                customerInfoVo.setCustodianDeptName(departmentInfo.getName());
            }

            return customerInfoVo;
        }).collect(Collectors.toList());

        return new IbcPager(customerInfoVos, (int) page.getTotalElements(), page.getTotalPages());

    }

    @Override
    public void export(Integer accountId, Integer projectId, Integer custId, String path) throws Exception {
        String copyPath = "d:\\test.docx";
        FileUtil.copy(path, copyPath, true);

        // 导出产品结构
        productStructureService.export(projectId, custId, copyPath);
        // 导出进货渠道
        inboundChannelService.export(projectId, custId, copyPath);
        // 导出销售收入
        salesRevenueService.export(projectId, custId, copyPath);
        // 导出回款核实
        returnedMoneyService.export(projectId, custId, copyPath);
        // 导出股权结构
        equityStructureService.export(projectId, custId, copyPath);

        // 导出担保企业信息
        exportCorporation(accountId, projectId, copyPath);

    }

    @Override
    public CustomerInfo findByIdNo(String idNo) {
        return repository.findByIdNo(idNo);
    }

    /**
     * 导出担保企业
     *
     * @param accountId 当前用户id
     * @param projectId 项目id
     * @param path      报告路径
     */
    private void exportCorporation(Integer accountId, Integer projectId, String path) throws Exception {

        String key = "$enterpriseGuarantee";

        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);

            XmlCursor cursor = DocUtil.getCursor(doc, key);
            // 企业保证数据
            List<CorporationInfo> corporationInfos = guaranteeClient.corporationList(projectId);
            for (int i = 0, len = corporationInfos.size(); i < len; i++) {
                CorporationInfo obj = corporationInfos.get(i);

                // 客户数据
                CustomerInfo customerInfo = find(obj.getCustomerId()).orElse(new CustomerInfo());
                // 天眼查公司数据
                TycCompany tycCompany = tycCompanyService.findTop1ByIdNoOrderByCreateAtDesc(obj.getIdNo());
                // 导出到评审报告中
                cursor = exportCompany(doc, cursor, tycCompany, customerInfo);

                // 股权结构数据
                List<EquityStructure> equityStructures = equityStructureService.list(accountId, projectId, obj.getCustomerId());
                // 导出到评审报告中
                cursor = exportEquityStructure(doc, cursor, equityStructures);

                // 财务分析数据
                FinanceAnalysis financeAnalysis = financeClient.financeAnalysis(projectId, obj.getCustomerId());
                // 导出到评审报告中
                cursor = exportBalanceSheet(doc, cursor, financeAnalysis.getBalanceSheetSimples());
                // 导出到评审报告中
                cursor = exportIncomeStatement(doc, cursor, financeAnalysis.getIncomeStatementSimples());

                // 插入一个新段落,光标往下移动
                cursor = doc.insertNewParagraph(cursor).getCTP().newCursor();
                cursor.toNextSibling();

            }
            doc.write(outputStream);
        } finally {
            IoUtil.close(doc);
            IoUtil.close(outputStream);
            try {
                FileUtil.copy(file, new File(path), true);
            } finally {
                FileUtil.del(file);
            }
        }

    }

    /**
     * 导出损益表数据
     *
     * @param doc                    文档对象
     * @param cursor                 光标对象
     * @param incomeStatementSimples 损益表数据
     * @return 光标对象
     */
    private XmlCursor exportIncomeStatement(XWPFDocument doc, XmlCursor cursor, List<IncomeStatementSimple> incomeStatementSimples) {
        // 在指定光标处创建段落并设置相应格式的内容
        cursor = DocUtil.setText(doc, cursor, "损益表", true, ParagraphAlignment.CENTER, 14);
        cursor = DocUtil.setText(doc, cursor, "单位：万元", false, ParagraphAlignment.RIGHT, 9);

        if (CollectionUtil.isEmpty(incomeStatementSimples)) {
            incomeStatementSimples = Lists.newArrayList(IncomeStatementSimple.builder().build());
        }

        // 在指定key处插入表格
        XWPFTable table = DocUtil.insertTableByCursor(doc, cursor, 8000);
        // 获取表格数据
        List<List<String>> dataList = getIncomeTableData(incomeStatementSimples);
        // 给表格增加数据
        DocUtil.addTableContent(table, dataList);

        // 设置表头背景色
        XWPFTableRow titleRow = DocUtil.getRow(table, 0);
        for (int i = 0, len = dataList.get(0).size(); i < len; i++) {
            DocUtil.getCell(titleRow, i).setColor("CCFFFF");
        }

        // 设置字体加粗
        for (int i = 0, len = dataList.size(); i < len; i++) {
            XWPFTableRow row = DocUtil.getRow(table, i);
            List<String> valueList = dataList.get(i);

            for (int j = 0, len2 = valueList.size(); j < len2; j++) {
                XWPFTableCell cell = DocUtil.getCell(row, j);
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                if (i == 0 || j == 0) {
                    run.setBold(true);
                }
            }
        }

        cursor = table.getCTTbl().newCursor();
        cursor.toNextSibling();
        return cursor;
    }

    /**
     * 获取损益表表格数据
     *
     * @param incomeStatementSimples 损益表数据
     * @return 损益表表格数据
     */
    private List<List<String>> getIncomeTableData(List<IncomeStatementSimple> incomeStatementSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, IncomeStatementSimple> map = Maps.newHashMap();
        for (IncomeStatementSimple incomeStatementSimple : incomeStatementSimples) {
            Integer period = incomeStatementSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, incomeStatementSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));
        // 先删除再新增调整顺序
        periodList.remove(com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue());
        periodList.add(com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue());

        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        list.add("科目");
        String titleFormat = "%s年%s月";
        String titleFormat2 = "%s年同期";
        String titleFormat3 = "%s年";
        // 添加标题
        for (int i = 0, len = periodList.size(); i < len; i++) {
            Integer key = periodList.get(i);
            IncomeStatementSimple incomeStatementSimple = map.get(key);
            if (ObjectUtil.isNull(incomeStatementSimple) || StrUtil.isBlank(incomeStatementSimple.getDate())) {
                list.add("");
                continue;
            }
            String[] arr = incomeStatementSimple.getDate().split(Const.Symbol.SUBTRACT);
            // 去年同期
            if (com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue().intValue() == key) {
                list.add(String.format(titleFormat2, arr[0]));
                continue;
            }
            // 今年
            if (com.szcgc.finance.constant.DicEnum.THIS_YEAR.getValue().intValue() == key) {
                list.add(String.format(titleFormat, arr[0], arr[1]));
                continue;
            }

            list.add(String.format(titleFormat3, arr[0]));
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getFieldList(IncomeStatementSimple.class);
        fieldDetails = fieldDetails.stream().filter(obj -> ObjectUtil.isNotNull(obj.getField().getAnnotation(Excel.class))).collect(Collectors.toList());
        for (FieldDetails fieldDetail : fieldDetails) {
            List<String> valueList = Lists.newArrayList();
            valueList.add(getExcelName(fieldDetail.getSchema(), fieldDetail.getField().getAnnotation(Excel.class)));
            for (int i = 0, len = periodList.size(); i < len; i++) {
                Integer key = periodList.get(i);

                IncomeStatementSimple incomeStatementSimple = map.get(key);
                if (ObjectUtil.isNull(incomeStatementSimple)) {
                    valueList.add("");
                    continue;
                }
                valueList.add((String) ReflectUtil.getFieldValue(incomeStatementSimple, fieldDetail.getField()));
            }
            dataList.add(valueList);
        }
        return dataList;
    }

    public String getExcelName(Schema schema, Excel excel) {
        if (schema != null && StrUtil.isNotBlank(schema.description())) {
            return schema.description();
        }

        return ObjectUtil.isNotNull(excel) ? excel.name() : "";
    }

    /**
     * 导出资产负债表数据
     *
     * @param doc                    文档对象
     * @param cursor                 光标对象
     * @param baseFinanceDataSimples 资产负债表数据
     * @return 光标对象
     */
    private XmlCursor exportBalanceSheet(XWPFDocument doc, XmlCursor cursor, List<BalanceSheetSimple> baseFinanceDataSimples) {
        // 在指定光标处创建段落并设置相应格式的内容
        cursor = DocUtil.setText(doc, cursor, "资产负债表", true, ParagraphAlignment.CENTER, 14);
        cursor = DocUtil.setText(doc, cursor, "单位：万元", false, ParagraphAlignment.RIGHT, 9);

        if (CollectionUtil.isEmpty(baseFinanceDataSimples)) {
            baseFinanceDataSimples = Lists.newArrayList(BalanceSheetSimple.builder().build());
        }

        // 在指定光标处插入表格
        XWPFTable table = DocUtil.insertTableByCursor(doc, cursor, 10000);
        // 获取表格数据
        List<List<String>> dataList = getTableData(baseFinanceDataSimples);
        // 给表格增加数据
        DocUtil.addTableContent(table, dataList);

        // 设置表头背景色
        XWPFTableRow titleRow = DocUtil.getRow(table, 0);
        for (int i = 0, len = dataList.get(0).size(); i < len; i++) {
            DocUtil.getCell(titleRow, i).setColor("CCFFFF");
        }

        // 设置字体加粗
        for (int i = 0, len = dataList.size(); i < len; i++) {
            XWPFTableRow row = DocUtil.getRow(table, i);
            List<String> valueList = dataList.get(i);

            for (int j = 0, len2 = valueList.size(); j < len2; j++) {
                XWPFTableCell cell = DocUtil.getCell(row, j);
                XWPFRun run = cell.getParagraphs().get(0).getRuns().get(0);
                if (i == 0 || j == 0 || j == len2 / 2) {
                    run.setBold(true);
                }
            }
        }

        cursor = table.getCTTbl().newCursor();
        cursor.toNextSibling();
        return cursor;
    }

    /**
     * 获取表格数据
     *
     * @param balanceSheetSimples 数据集合
     */
    private List<List<String>> getTableData(List<BalanceSheetSimple> balanceSheetSimples) {
        List<Integer> periodList = Lists.newArrayList();
        // 把数据根据期次分别存储
        Map<Integer, BalanceSheetSimple> map = Maps.newHashMap();
        for (BalanceSheetSimple balanceSheetSimple : balanceSheetSimples) {
            Integer period = balanceSheetSimple.getPeriod();
            if (!map.containsKey(period)) {
                periodList.add(period);
                map.put(period, balanceSheetSimple);
            }
        }
        periodList.sort(Comparator.comparingInt(Integer::intValue));
        // 先删除再新增调整顺序
        periodList.remove(com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue());
        periodList.add(com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue());

        // 添加标题
        List<List<String>> dataList = Lists.newArrayList();
        List<String> list = Lists.newArrayList();
        String titleFormat = "%s年%s月";
        String titleFormat2 = "%s年同期";
        String titleFormat3 = "%s年";
        for (int i = 0; i < 2; i++) {
            list.add("科目");
            for (int j = 0, len = periodList.size(); j < len; j++) {
                BalanceSheetSimple balanceSheetSimple = map.get(periodList.get(j));
                if (ObjectUtil.isNull(balanceSheetSimple) || StrUtil.isBlank(balanceSheetSimple.getDate())) {
                    list.add("");
                    continue;
                }
                String[] arr = balanceSheetSimple.getDate().split(Const.Symbol.SUBTRACT);
                if (com.szcgc.finance.constant.DicEnum.THIS_YEAR.getValue().intValue() == balanceSheetSimple.getPeriod()) {
                    list.add(String.format(titleFormat, arr[0], arr[1]));
                } else if (com.szcgc.finance.constant.DicEnum.SAME_PERIOD_LAST_YEAR.getValue().intValue() == balanceSheetSimple.getPeriod()) {
                    list.add(String.format(titleFormat2, arr[0]));
                } else {
                    list.add(String.format(titleFormat3, arr[0]));
                }

            }
        }

        dataList.add(list);

        // 添加内容数据
        List<FieldDetails> fieldDetails = ExcelUtil.getFieldList(BalanceSheetSimple.class);
        fieldDetails = fieldDetails.stream().filter(obj -> ObjectUtil.isNotNull(obj.getField().getAnnotation(Excel.class))).collect(Collectors.toList());
        for (int i = 0, len = (fieldDetails.size() + 1) / 2; i < len; i++) {
            List<String> valueList = Lists.newArrayList();
            FieldDetails fieldDetails1 = fieldDetails.get(i);
            FieldDetails fieldDetails2 = null;

            if (i < 6) {
                fieldDetails2 = fieldDetails.get(i + (fieldDetails.size() + 1) / 2);
            } else if (i > 6) {
                fieldDetails2 = fieldDetails.get(i + (fieldDetails.size() + 1) / 2 - 1);
            }
            FieldDetails fieldDetail = fieldDetails1;
            for (int k = 0; k < 2; k++) {
                if (k == 1) {
                    fieldDetail = fieldDetails2;
                }
                valueList.add(ObjectUtil.isNull(fieldDetail) ? "" : getExcelName(fieldDetail.getSchema(), fieldDetail.getField().getAnnotation(Excel.class)));
                for (int j = 0, len2 = periodList.size(); j < len2; j++) {
                    Integer key = periodList.get(j);

                    BalanceSheetSimple balanceSheetSimple = map.get(key);
                    if (ObjectUtil.isNull(balanceSheetSimple)) {
                        valueList.add("");
                        continue;
                    }
                    valueList.add(ObjectUtil.isNull(fieldDetail) ? "" : (String) ReflectUtil.getFieldValue(balanceSheetSimple, fieldDetail.getField()));
                }
            }
            dataList.add(valueList);
        }
        return dataList;
    }

    /**
     * 导出股权结构数据
     *
     * @param doc              文档对象
     * @param cursor           光标对象
     * @param equityStructures 股权结构数据
     */
    private XmlCursor exportEquityStructure(XWPFDocument doc, XmlCursor cursor, List<EquityStructure> equityStructures) {
        // 在指定光标处创建段落并设置相应格式的内容
        cursor = DocUtil.setText(doc, cursor, "股权结构", true);

        // 构建表格数据
        List<List<String>> dataList = Lists.newArrayList();
        dataList.add(Lists.newArrayList("序号", "股东名称", "出资金额(万元)", "出资方式", "股权比例", "备注"));
        BigDecimal count = BigDecimal.ZERO;
        for (int i = 0, len = equityStructures.size(); i < len; i++) {
            EquityStructure obj = equityStructures.get(i);
            dataList.add(Lists.newArrayList(String.valueOf(i + 1), obj.getName(), obj.getCapitalActl(), obj.getPaymet(), obj.getPercent(), obj.getRemarks()));
            // 提取数字进行合计
            String num = extractNumber(obj.getCapitalActl());
            count = NumberUtil.add(CalcUtil.round(count, 6), num);
        }
        dataList.add(Lists.newArrayList("合计", "", CalcUtil.round(count, 2), "", "", ""));

        // 在指定光标处插入表格
        XWPFTable table = DocUtil.insertTableByCursor(doc, cursor);
        // 给表格添加内容
        DocUtil.addTableContent(table, dataList);

        cursor = table.getCTTbl().newCursor();
        cursor.toNextSibling();
        return cursor;
    }

    /**
     * 导出企业数据
     *
     * @param doc          文档对象
     * @param cursor       光标对象
     * @param tycCompany   天眼查企业数据对象
     * @param customerInfo 客户信息
     */
    private XmlCursor exportCompany(XWPFDocument doc, XmlCursor cursor, TycCompany tycCompany, CustomerInfo customerInfo) {
        // 在指定光标处创建段落并设置相应格式的内容
        cursor = DocUtil.setText(doc, cursor, "反担保企业基本信息", true);

        // 构建表格数据
        List<List<String>> dataList = Lists.newArrayList();
        dataList.add(Lists.newArrayList("企业名称", customerInfo.getName()));
        dataList.add(Lists.newArrayList("统一社会信用代码", customerInfo.getIdNo()));
        dataList.add(Lists.newArrayList("法定代表人", customerInfo.getLegal(), "国籍",
                DicEnum.getNameByValueAndType(customerInfo.getLegalNationality(), DicTypeEnum.NATIONALITY)));
        dataList.add(Lists.newArrayList("", "", "证件号码", customerInfo.getLegalCertificate()));
        dataList.add(Lists.newArrayList("成立日期", DateUtil.format(new Date(tycCompany.getEstiblishTime()), "yyyy-MM")));
        dataList.add(Lists.newArrayList("注册地址", tycCompany.getRegLocation()));
        dataList.add(Lists.newArrayList("注册资本（币种、金额）", tycCompany.getRegCapital(), "实缴资本（币种、金额）", tycCompany.getActualCapital()));
        dataList.add(Lists.newArrayList("主营业务", tycCompany.getBusinessScope()));
        dataList.add(Lists.newArrayList("技术创新情况", ""));

        // 在指定光标处插入表格
        XWPFTable table = DocUtil.insertTableByCursor(doc, cursor);
        // 给表格添加内容
        DocUtil.addTableContent(table, dataList);
        // 合并单元格
        DocUtil.mergeCell(table, 0, 0, 1, 3);
        DocUtil.mergeCell(table, 1, 1, 1, 3);
        DocUtil.mergeCell(table, 2, 3, 0, 0);
        DocUtil.mergeCell(table, 2, 3, 1, 1);
        DocUtil.mergeCell(table, 4, 4, 1, 3);
        DocUtil.mergeCell(table, 5, 5, 1, 3);
        DocUtil.mergeCell(table, 7, 7, 1, 3);
        DocUtil.mergeCell(table, 8, 8, 1, 3);

        cursor = table.getCTTbl().newCursor();
        cursor.toNextSibling();
        return cursor;
    }

    /**
     * 提取数字
     *
     * @param numStr 包含数字的字符串
     * @return 数字字符串
     */
    public String extractNumber(String numStr) {
        if (StrUtil.isBlank(numStr)) {
            return Const.Number.ZERO;
        }
        Pattern pattern = Pattern.compile(Const.Regex.NUMBER);
        Matcher matcher = pattern.matcher(numStr);
        // 返回第一个匹配值
        if (matcher.find()) {
            return matcher.group();
        }
        return Const.Number.ZERO;
    }

}
