package com.szcgc.finance.service.impl;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import com.szcgc.finance.constant.CompareTypeEnum;
import com.szcgc.finance.constant.QuantitativeAnalysisEnum;
import com.szcgc.finance.dto.FinanceDto;
import com.szcgc.finance.model.CreditRating;
import com.szcgc.finance.model.FieldDetails;
import com.szcgc.finance.model.analysis.*;
import com.szcgc.finance.model.base.BalanceSheet;
import com.szcgc.finance.model.base.CashFlowStatement;
import com.szcgc.finance.model.base.IncomeStatement;
import com.szcgc.finance.service.CreditRatingService;
import com.szcgc.finance.service.FinanceInfoService;
import com.szcgc.finance.service.analysis.*;
import com.szcgc.finance.service.base.BalanceSheetService;
import com.szcgc.finance.service.base.CashFlowStatementService;
import com.szcgc.finance.service.base.IncomeStatementService;
import com.szcgc.finance.util.CalcUtil;
import com.szcgc.finance.util.ExcelUtil;
import com.szcgc.finance.util.ReflectCacheUtil;
import com.szcgc.finance.vo.CodeMapAndListVo;
import com.szcgc.finance.vo.CreditRatingVo;
import com.szcgc.finance.vo.QuantitativeAnalysisVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 财务管理业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-23 15:05:45
 */
@Slf4j
@Service
public class FinanceInfoInfoServiceImpl implements FinanceInfoService {

    @Resource
    private BalanceSheetService balanceSheetService;

    @Resource
    private CashFlowStatementService cashFlowStatementService;

    @Resource
    private IncomeStatementService incomeStatementService;

    @Resource
    private BalanceSheetSimpleService balanceSheetSimpleService;

    @Resource
    private CashFlowStatementSimpleService cashFlowStatementSimpleService;

    @Resource
    private IncomeStatementSimpleService incomeStatementSimpleService;

    @Resource
    private BaseFinanceDataSimpleService baseFinanceDataSimpleService;

    @Resource
    private FinanceAnalysisService financeAnalysisService;

    @Resource
    private FinanceAnalysisSimpleService financeAnalysisSimpleService;

    @Resource
    private IFileClient fileClient;

    @Resource
    private CreditRatingService creditRatingService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String importData(Integer projectId, Integer custId, MultipartFile file, int accountId, List<String> title) throws Exception {
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验财务表表头
            errorTips = ExcelUtil.verifyFinanceHead(new FileInputStream(tempFile), title, FinanceDto.class);
            if (StrUtil.isNotBlank(errorTips)) {
                return errorTips;
            }
            // 获取数据
            List<FinanceDto> financeData = ExcelUtil.getFinanceData(new FileInputStream(tempFile), FinanceDto.class, "date");

            if (CollectionUtil.isEmpty(financeData)) {
                return ExcelUtil.BANK_MODEL_ERROR;
            }

            // 保存资产负债表数据
            List<BalanceSheet> balanceSheets = financeData.stream().map(obj -> {
                BalanceSheet balanceSheet = obj.getBalanceSheet();
                balanceSheet.setProjectId(projectId);
                balanceSheet.setCustomerId(custId);
                balanceSheet.setCreateBy(accountId);
                balanceSheet.setUpdateBy(accountId);
                BalanceSheet existsBalanceSheet = balanceSheetService.findByProjectIdAndCustomerIdAndDate(projectId, custId, balanceSheet.getDate());
                if (ObjectUtil.isNotEmpty(existsBalanceSheet)) {
                    balanceSheet.setId(existsBalanceSheet.getId());
                }
                return balanceSheet;
            }).collect(Collectors.toList());
            balanceSheetService.batchInsert(balanceSheets);

            // 保存损益表数据
            List<IncomeStatement> incomeStatements = financeData.stream().map(obj -> {
                IncomeStatement incomeStatement = obj.getIncomeStatement();
                incomeStatement.setProjectId(projectId);
                incomeStatement.setCustomerId(custId);
                incomeStatement.setCreateBy(accountId);
                incomeStatement.setUpdateBy(accountId);
                IncomeStatement existsIncomeStatement = incomeStatementService.findByProjectIdAndCustomerIdAndDate(projectId, custId, incomeStatement.getDate());
                if (ObjectUtil.isNotEmpty(existsIncomeStatement)) {
                    incomeStatement.setId(existsIncomeStatement.getId());
                }
                return incomeStatement;
            }).collect(Collectors.toList());
            incomeStatementService.batchInsert(incomeStatements);

            // 保存现金流量表数据
            List<CashFlowStatement> cashFlowStatements = financeData.stream().map(obj -> {
                CashFlowStatement cashFlowStatement = obj.getCashFlowStatement();
                cashFlowStatement.setProjectId(projectId);
                cashFlowStatement.setCustomerId(custId);
                cashFlowStatement.setCreateBy(accountId);
                cashFlowStatement.setUpdateBy(accountId);
                CashFlowStatement existsIncomeStatement = cashFlowStatementService.findByProjectIdAndCustomerIdAndDate(projectId, custId, cashFlowStatement.getDate());
                if (ObjectUtil.isNotEmpty(existsIncomeStatement)) {
                    cashFlowStatement.setId(existsIncomeStatement.getId());
                }
                return cashFlowStatement;
            }).collect(Collectors.toList());
            cashFlowStatementService.batchInsert(cashFlowStatements);

        } finally {
            tempFile.delete();
        }
        return errorTips;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void analysis(Integer id1, Integer id2, Integer id3, Integer id4, int accountId) {
        // 财务分析主表对象
        FinanceAnalysis financeAnalysis = FinanceAnalysis.builder()
                .analysisName(String.format("财务分析_%s", System.currentTimeMillis()))
                .build();

        // 获取原始表数据
        List<BalanceSheet> balanceSheets = Lists.newArrayList();
        List<IncomeStatement> incomeStatements = Lists.newArrayList();
        List<CashFlowStatement> cashFlowStatements = Lists.newArrayList();
        for (Integer id : Lists.newArrayList(id1, id2, id3, id4)) {
            if (ObjectUtil.isNull(id)) {
                balanceSheets.add(BalanceSheet.builder().build());
                incomeStatements.add(IncomeStatement.builder().build());
                cashFlowStatements.add(CashFlowStatement.builder().build());
                continue;
            }
            BalanceSheet balanceSheet = balanceSheetService.find(id).orElse(null);
            if (ObjectUtil.isNull(balanceSheet)) {
                balanceSheets.add(BalanceSheet.builder().build());
                incomeStatements.add(IncomeStatement.builder().build());
                cashFlowStatements.add(CashFlowStatement.builder().build());
                continue;
            }
            if (ObjectUtil.isNull(financeAnalysis.getProjectId())) {
                financeAnalysis.setProjectId(balanceSheet.getProjectId());
                financeAnalysis.setCustomerId(balanceSheet.getCustomerId());
                financeAnalysis.setCreateBy(accountId);
                financeAnalysis.setUpdateBy(accountId);
            }
            balanceSheets.add(balanceSheet);
            incomeStatements.add(incomeStatementService.findByProjectIdAndCustomerIdAndDate(balanceSheet.getProjectId(),
                    balanceSheet.getCustomerId(), balanceSheet.getDate()));
            cashFlowStatements.add(cashFlowStatementService.findByProjectIdAndCustomerIdAndDate(balanceSheet.getProjectId(),
                    balanceSheet.getCustomerId(), balanceSheet.getDate()));
        }

        // 保存财务分析主表
        financeAnalysisService.insert(financeAnalysis);
        // 生成基本财务简表
        baseFinanceDataSimpleService.createSimpleTable(balanceSheets, incomeStatements, financeAnalysis.getId(), accountId);
        // 生成财务分析简表
        financeAnalysisSimpleService.createSimpleTable(balanceSheets, incomeStatements, cashFlowStatements, financeAnalysis.getId(), accountId);
        // 生成资产负债简表
        balanceSheetSimpleService.createSimpleTable(balanceSheets, financeAnalysis.getId(), accountId);
        // 生成损益简表
        incomeStatementSimpleService.createSimpleTable(incomeStatements, financeAnalysis.getId(), accountId);
        // 生成现金流量简表
        cashFlowStatementSimpleService.createSimpleTable(cashFlowStatements, financeAnalysis.getId(), accountId);

        // 删除资信评分数据
        creditRatingService.deleteByProjectIdAndCustId(financeAnalysis.getProjectId(), financeAnalysis.getCustomerId());
    }

    @Override
    public FinanceAnalysis detail(Integer projectId, Integer custId) {
        FinanceAnalysis analysis = financeAnalysisService.findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(projectId, custId);

        if (ObjectUtil.isNull(analysis)) {
            return FinanceAnalysis.builder().build();
        }

        analysis.setBaseFinanceDatumSimples(baseFinanceDataSimpleService.findByMainId(analysis.getId()));
        analysis.setFinanceAnalysisSimples(financeAnalysisSimpleService.findByMainId(analysis.getId()));
        analysis.setBalanceSheetSimples(balanceSheetSimpleService.findByMainId(analysis.getId()));
        analysis.setIncomeStatementSimples(incomeStatementSimpleService.findByMainId(analysis.getId()));
        analysis.setCashFlowStatementSimples(cashFlowStatementSimpleService.findByMainId(analysis.getId()));

        CalcUtil.tranUnit(analysis.getBaseFinanceDatumSimples());
        CalcUtil.tranUnit(analysis.getFinanceAnalysisSimples());
        CalcUtil.tranUnit(analysis.getBalanceSheetSimples());
        CalcUtil.tranUnit(analysis.getIncomeStatementSimples());
        CalcUtil.tranUnit(analysis.getCashFlowStatementSimples());

        return analysis;
    }

    @Override
    public void export(HttpServletResponse response, Integer projectId, Integer custId) throws Exception {
        FinanceAnalysis analysis = detail(projectId, custId);
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            // 创建基本财务数据sheet页
            createBaseFinanceDataSheet(workbook, analysis.getBaseFinanceDatumSimples());
            // 创建财务分析sheet页
            createFinanceAnalysisSheet(workbook, analysis.getFinanceAnalysisSimples());
            // 创建资产负债表sheet页
            createBalanceSheet(workbook, analysis.getBalanceSheetSimples());
            // 创建损益简表sheet页
            createIncomeStatementSheet(workbook, analysis.getIncomeStatementSimples());
            // 创建现金流量简表sheet页
            createCashFlowStatementSheet(workbook, analysis.getCashFlowStatementSimples());
            ExcelUtil.setResponse(response, analysis.getAnalysisName());
            workbook.write(response.getOutputStream());
        } finally {
            IoUtil.close(workbook);
        }
    }

    @Override
    public void exportReport(Integer accountId, Integer projectId, Integer custId, String path) throws Exception {
        ClassPathResource classPathResource = new ClassPathResource(path);
        File file = null;
        XWPFDocument workbook = null;
        FileOutputStream fos = null;
        try {
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            workbook = new XWPFDocument(classPathResource.getInputStream());
            fos = new FileOutputStream(file);
            workbook.write(fos);
            IoUtil.close(fos);
            // 财务分析数据
            FinanceAnalysis financeAnalysis = financeAnalysisService.findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(projectId, custId);
            if (ObjectUtil.isNull(financeAnalysis)) {
                financeAnalysis = FinanceAnalysis.builder().build();
                financeAnalysis.setId(0);
            }
            // 导出基本财务简表数据
            baseFinanceDataSimpleService.export(financeAnalysis.getId(), file.getAbsolutePath());
            // 导出基本财务指标分析简表数据
            financeAnalysisSimpleService.export(financeAnalysis.getId(), file.getAbsolutePath());
            // 导出资产负债简表数据
            balanceSheetSimpleService.export(financeAnalysis.getId(), file.getAbsolutePath());
            // 导出损益简表数据
            incomeStatementSimpleService.export(financeAnalysis.getId(), file.getAbsolutePath());

            UploadFileDto fdcUploadPath = new UploadFileDto();
            String fileName = FileCateEnum.Bg_Ptywpsbg.getCnName().concat(String.valueOf(System.currentTimeMillis())).concat(Const.Suffix.DOCX);
            String content = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
            fdcUploadPath.setProjectId(projectId);
            fdcUploadPath.setAccountId(accountId);
            fdcUploadPath.setName(fileName);
            fdcUploadPath.setContent(content);
            fdcUploadPath.setCate(FileCateEnum.Bg_Ptywpsbg);
            fdcUploadPath.setRemarks(FileCateEnum.Bg_Ptywpsbg.getCnName());

            log.info("调用文件服务器请求参数为:accountId:{},projectId:{},name:{},cate:{},remarks:{}", fdcUploadPath.getAccountId(),
                    fdcUploadPath.getProjectId(), fdcUploadPath.getName(), fdcUploadPath.getCate(), fdcUploadPath.getRemarks());
            FileInfo fileInfo = fileClient.upload(fdcUploadPath);
            log.info("调用文件服务器返回结果为:{}", JsonUtils.toJSONString(fileInfo));

        } finally {
            IoUtil.close(fos);
            IoUtil.close(workbook);
            FileUtil.del(file);
        }

    }

    @Override
    public Map<String, CodeMapAndListVo> financetable() throws Exception {
        List<Class> classList = Lists.newArrayList(BalanceSheet.class, CashFlowStatement.class, IncomeStatement.class,
                FinanceAnalysisSimple.class, CreditRating.class);
        Map<String, CodeMapAndListVo> map = Maps.newHashMap();
        for (Class cls : classList) {

            List<FieldDetails> fieldDetailsList = ExcelUtil.getExcelFieldList(cls);

            List<String> codeList = Lists.newArrayList();
            Map<String, String> codeMap = Maps.newHashMap();
            for (FieldDetails fieldDetails : fieldDetailsList) {
                codeList.add(fieldDetails.getField().getName());
                codeMap.put(fieldDetails.getField().getName(), fieldDetails.getExcelName());
            }
            map.put(StrUtil.lowerFirst(cls.getSimpleName()), CodeMapAndListVo.builder()
                    .codeList(codeList)
                    .codeMap(codeMap)
                    .build());

        }

        return map;
    }

    @Override
    public List<CreditRatingVo> financeAnalysis(Integer projectId, Integer custId) {
        FinanceAnalysis financeAnalysis = financeAnalysisService.findTop1ByProjectIdAndCustomerIdOrderByCreateAtDesc(projectId, custId);
        if (ObjectUtil.isNull(financeAnalysis)) {
            return Lists.newArrayList();
        }

        List<FinanceAnalysisSimple> list = financeAnalysisSimpleService.findByMainId(financeAnalysis.getId());
        // 保留日期不为空的数据
        if (CollectionUtil.isNotEmpty(list)) {
            list = list.stream().filter(obj -> ObjectUtil.isNotNull(obj.getDate())).collect(Collectors.toList());
        }
        // 转换单位为万元
        CalcUtil.tranUnit(list);

        List<CreditRatingVo> res = Lists.newArrayList();
        list.forEach(obj -> {
            CreditRatingVo creditRatingVo = CreditRatingVo.builder()
                    .financeAnalysisSimple(obj)
                    .quantitativeAnalysisVo(getQuantitativeAnalysis(obj))
                    .build();
            res.add(creditRatingVo);
        });

        return res;
    }

    /**
     * 根据财务分析简表数据获取定量评分数据
     *
     * @param financeAnalysisSimple 财务分析简表数据
     * @return 定量评分数据
     */
    private QuantitativeAnalysisVo getQuantitativeAnalysis(FinanceAnalysisSimple financeAnalysisSimple) {
        QuantitativeAnalysisVo res = QuantitativeAnalysisVo.builder().build();
        if (ObjectUtil.isNull(financeAnalysisSimple)) {
            return res;
        }

        List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getFieldList(res.getClass());
        fieldDetailsList.forEach(obj -> {
            // 获取反射字段对象
            Field field = obj.getField();
            // 获取字段名
            String fieldName = field.getName();
            // 获取财务分析简表中该字段值
            String val = (String) ReflectUtil.getFieldValue(financeAnalysisSimple, fieldName);
            // 获取字段评分枚举
            QuantitativeAnalysisEnum quantitativeAnalysisEnum = QuantitativeAnalysisEnum.valueOf(fieldName.toUpperCase());
            // 将字符串类型的值转换成浮点型
            double dbVal = Double.parseDouble(extractNumber(val));
            // 获取分数
            int score = getScore(quantitativeAnalysisEnum.getScoringCriteria(), quantitativeAnalysisEnum.getCompareTypeEnum(), dbVal);
            // 设置值
            ReflectUtil.setFieldValue(res, field, String.valueOf(score));
        });


        return res;
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

    /**
     * 获取分数
     *
     * @param scoringCriteria 分数区域集合
     * @param compareType     升序还是降序
     * @param comparaKey      比较值
     * @return 分数
     */
    public int getScore(double[] scoringCriteria, CompareTypeEnum compareType, double comparaKey) {
        int score = 0;
        int lowerBound = 0;
        int upperBound = scoringCriteria.length - 1;
        int curIn;
        if (comparaKey < scoringCriteria[lowerBound]) {
            return compareType.equals(CompareTypeEnum.Ascending) ? 0 : 10;
        }
        if (comparaKey >= scoringCriteria[upperBound]) {
            return compareType.equals(CompareTypeEnum.Ascending) ? 10 : 0;
        }
        while (true) {
            curIn = (lowerBound + upperBound) / 2;
            // 找到了
            if (lowerBound == curIn) {
                score = upperBound;
                break;
            } else if (comparaKey >= scoringCriteria[curIn]) {
                lowerBound = curIn;
            } else {
                upperBound = curIn;
            }
        }
        return compareType.equals(CompareTypeEnum.Ascending) ? score : (10 - score);
    }

    @Override
    public CreditRating qualitative(Integer projectId, Integer custId, String date) {
        return creditRatingService.findByProjectIdAndCustIdAndDate(projectId, custId, date);
    }

    @Override
    public void saveQualitative(CreditRating creditRating) {
        creditRatingService.saveQualitative(creditRating);
    }

    /**
     * 创建现金流量表sheet页
     *
     * @param workbook                 excel对象
     * @param cashFlowStatementSimples 现金流量表数据集合
     */
    private void createCashFlowStatementSheet(XSSFWorkbook workbook, List<CashFlowStatementSimple> cashFlowStatementSimples) {
        String sheetAndTitleName = "现金流量表";
        XSSFSheet sheet = workbook.createSheet(sheetAndTitleName);

        int lastColIdx = 6;
        // 添加大标题行
        addBigTitleRow(workbook, sheet, sheetAndTitleName, lastColIdx);
        // 添加单位行
        addUnitRow(workbook, sheet, lastColIdx);
        // 设置列宽
        setColWidth(sheet, lastColIdx);

        // 添加合并标题
        addTitleRow(workbook, sheet, 2, 0, 2, 3, 0, 0, "科目");
        addTitleRow(workbook, sheet, 2, 1, 2, 2, 1, 3, "前年");
        addTitleRow(workbook, sheet, 2, 4, 2, 2, 4, 6, "去年");

        // 添加小标题
        List<String> title = Lists.newArrayList("", "流入量", "流出量", "净额", "流入量", "流出量", "净额");
        addTitleRow(workbook, sheet, title, 3);

        // 添加科目列
        List<String> subject = Lists.newArrayList("经营活动产生的现金流量", "投资活动产生的现金流量", "融资活动产生的现金流量", "合  计");
        for (int i = 0, len = subject.size(); i < len; i++) {
            addContent(workbook, sheet, 4 + i, 0, subject.get(i), true);
        }

        // 添加内容
        for (int i = 0; i < 2; i++) {
            int colAdd = i * 3;
            CashFlowStatementSimple ca = cashFlowStatementSimples.get(i);
            addContent(workbook, sheet, 4, 1 + colAdd, ca.getManageIn());
            addContent(workbook, sheet, 4, 2 + colAdd, ca.getManageOut());
            addContent(workbook, sheet, 4, 3 + colAdd, ca.getManageNet());
            addContent(workbook, sheet, 5, 1 + colAdd, ca.getInvestIn());
            addContent(workbook, sheet, 5, 2 + colAdd, ca.getInvestOut());
            addContent(workbook, sheet, 5, 3 + colAdd, ca.getInvestNet());
            addContent(workbook, sheet, 6, 1 + colAdd, ca.getFinancingIn());
            addContent(workbook, sheet, 6, 2 + colAdd, ca.getFinancingOut());
            addContent(workbook, sheet, 6, 3 + colAdd, ca.getFinancingNet());
            addContent(workbook, sheet, 7, 1 + colAdd, ca.getTotalIn());
            addContent(workbook, sheet, 7, 2 + colAdd, ca.getTotalOut());
            addContent(workbook, sheet, 7, 3 + colAdd, ca.getTotalNet());
        }

    }

    /**
     * 添加内容
     *
     * @param workbook excel对象
     * @param sheet    sheet对象
     * @param rowIdx   行下标
     * @param colIdx   列下标
     * @param content  内容
     */
    private void addContent(XSSFWorkbook workbook, XSSFSheet sheet, int rowIdx, int colIdx, String content) {
        addContent(workbook, sheet, rowIdx, colIdx, content, false);
    }

    /**
     * 添加内容
     *
     * @param workbook excel对象
     * @param sheet    sheet对象
     * @param rowIdx   行下标
     * @param colIdx   列下标
     * @param content  内容
     * @param subject  是否科目列
     */
    private void addContent(XSSFWorkbook workbook, XSSFSheet sheet, int rowIdx, int colIdx, String content, boolean subject) {
        XSSFRow row = ExcelUtil.createRow(sheet, rowIdx);
        XSSFCell cell = row.createCell(colIdx);
        XSSFCellStyle style = ExcelUtil.getStyle(workbook);
        if (subject) {
            style = ExcelUtil.getStyle(true, true, workbook);
        }
        cell.setCellStyle(style);
        cell.setCellValue(content);
    }

    /**
     * 添加合并标题行
     *
     * @param workbook excel对象
     * @param sheet    sheet对象
     * @param rowIdx   标题行下标
     * @param colIdx   标题列下标
     * @param firstRow 合并开始行
     * @param lastRow  合并结束行
     * @param firstCol 合并开始列
     * @param lastCol  合并结束列
     * @param content  标题内容
     */
    private void addTitleRow(XSSFWorkbook workbook, XSSFSheet sheet, int rowIdx, int colIdx, int firstRow, int lastRow, int firstCol, int lastCol, String content) {
        XSSFRow titleRow = ExcelUtil.createRow(sheet, rowIdx);
        XSSFCell titleCell = titleRow.createCell(colIdx);
        XSSFCellStyle titleStyle = ExcelUtil.getStyle(true, new int[]{204, 255, 255}, true, workbook);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(content);
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, firstCol, lastCol);
        sheet.addMergedRegion(region);
        // 给合并后的区域设置边框
        ExcelUtil.setBorderStyle(region, sheet);
    }

    /**
     * 创建损益表sheet页
     *
     * @param workbook               excel对象
     * @param incomeStatementSimples 损益表数据集合
     */
    private void createIncomeStatementSheet(XSSFWorkbook workbook, List<IncomeStatementSimple> incomeStatementSimples) {
        String sheetAndTitleName = "损益表";
        XSSFSheet sheet = workbook.createSheet(sheetAndTitleName);

        int lastColIdx = 4;
        // 添加大标题行
        addBigTitleRow(workbook, sheet, sheetAndTitleName, lastColIdx);
        // 添加单位行
        addUnitRow(workbook, sheet, lastColIdx);
        // 第二行标题
        List<String> title = Lists.newArrayList("科目", incomeStatementSimples.get(0).getDate(),
                incomeStatementSimples.get(1).getDate(), incomeStatementSimples.get(2).getDate(), incomeStatementSimples.get(3).getDate());
        // 添加小标题行
        addTitleRow(workbook, sheet, title);
        // 设置列宽
        setColWidth(sheet, lastColIdx);

        List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getExcelFieldList(incomeStatementSimples.get(0).getClass());
        for (int i = 0, len = fieldDetailsList.size(); i < len; i++) {
            XSSFRow row = sheet.createRow(i + 3);
            for (int j = 0; j <= lastColIdx; j++) {
                XSSFCell cell = row.createCell(j);

                FieldDetails fieldDetails = fieldDetailsList.get(i);
                // 第一列
                if (j == 0) {
                    XSSFCellStyle style = ExcelUtil.getStyle(true, true, workbook);
                    cell.setCellStyle(style);
                    cell.setCellValue(fieldDetails.getExcelName());
                    continue;
                }

                // 其他列
                IncomeStatementSimple baseFinanceDataSimple = incomeStatementSimples.get(j - 1);
                cell.setCellStyle(ExcelUtil.getStyle(workbook));
                cell.setCellValue((String) ReflectUtil.getFieldValue(baseFinanceDataSimple, fieldDetails.getField()));
            }
        }
    }

    /**
     * 创建资产负债表sheet页
     *
     * @param workbook            excel对象
     * @param balanceSheetSimples 资产负债表数据集合
     */
    private void createBalanceSheet(XSSFWorkbook workbook, List<BalanceSheetSimple> balanceSheetSimples) {
        String sheetAndTitleName = "资产负债表";
        XSSFSheet sheet = workbook.createSheet(sheetAndTitleName);

        int lastColIdx = 9;
        // 添加大标题行
        addBigTitleRow(workbook, sheet, sheetAndTitleName, lastColIdx);
        // 添加单位行
        addUnitRow(workbook, sheet, lastColIdx);
        // 第二行标题
        List<String> title = Lists.newArrayList("科目", balanceSheetSimples.get(0).getDate(), balanceSheetSimples.get(1).getDate(),
                balanceSheetSimples.get(2).getDate(), balanceSheetSimples.get(3).getDate());
        title.addAll(CollectionUtil.newCopyOnWriteArrayList(title));
        // 添加小标题行
        addTitleRow(workbook, sheet, title);
        // 设置列宽
        setColWidth(sheet, lastColIdx);

        // 填充内容
        List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getExcelFieldList(balanceSheetSimples.get(0).getClass());
        for (int i = 0, len = (fieldDetailsList.size() + 1) / 2; i < len; i++) {
            XSSFRow row = ExcelUtil.createRow(sheet, i + 3);
            for (int j = 0; j < (lastColIdx + 1) / 2; j++) {
                XSSFCell cell = row.createCell(j);
                FieldDetails fieldDetails = fieldDetailsList.get(i);

                XSSFCell cell2 = row.createCell(j + (lastColIdx + 1) / 2);
                FieldDetails fieldDetails2 = null;

                if (i < 6) {
                    fieldDetails2 = fieldDetailsList.get(i + (fieldDetailsList.size() + 1) / 2);
                } else if (i > 6) {
                    fieldDetails2 = fieldDetailsList.get(i + (fieldDetailsList.size() + 1) / 2 - 1);
                }

                // 第标题列内容
                if (j == 0) {
                    cell.setCellValue(fieldDetails.getExcelName());
                    cell.setCellStyle(ExcelUtil.getStyle(true, true, workbook));

                    if (ObjectUtil.isNotNull(fieldDetails2)) {
                        cell2.setCellValue(fieldDetails2.getExcelName());
                        cell2.setCellStyle(ExcelUtil.getStyle(true, true, workbook));
                    }
                    continue;
                }

                // 其他列内容
                BalanceSheetSimple financeAnalysisSimple = balanceSheetSimples.get(j - 1);
                cell.setCellStyle(ExcelUtil.getStyle(workbook));
                cell.setCellValue((String) ReflectUtil.getFieldValue(financeAnalysisSimple, fieldDetails.getField()));

                cell2.setCellStyle(ExcelUtil.getStyle(workbook));
                if (ObjectUtil.isNotNull(fieldDetails2)) {
                    cell2.setCellValue((String) ReflectUtil.getFieldValue(financeAnalysisSimple, fieldDetails2.getField()));
                }
            }
        }
    }

    /**
     * 创建财务分析sheet页
     *
     * @param workbook               excel对象
     * @param financeAnalysisSimples 财务分析数据集合
     */
    private void createFinanceAnalysisSheet(XSSFWorkbook workbook, List<FinanceAnalysisSimple> financeAnalysisSimples) {
        String sheetAndTitleName = "财务分析";
        XSSFSheet sheet = workbook.createSheet(sheetAndTitleName);

        int lastColIdx = 6;
        // 添加大标题行
        addBigTitleRow(workbook, sheet, sheetAndTitleName, lastColIdx);
        // 添加单位行
        addUnitRow(workbook, sheet, lastColIdx);
        // 第二行标题
        List<String> title = Lists.newArrayList("", "序号", "科目", "前年", "去年", "变化[+  -]%", "今年1期");
        // 添加小标题行
        addTitleRow(workbook, sheet, title);
        // 设置列宽
        setColWidth(sheet, lastColIdx);

        // 设置第一列内容
        setOneColContent(sheet, workbook, 3, 12, "偿债\n能力");
        setOneColContent(sheet, workbook, 13, 18, "经营\n能力");
        setOneColContent(sheet, workbook, 19, 22, "成长\n能力");

        // 填充内容
        List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getExcelFieldList(financeAnalysisSimples.get(0).getClass());
        for (int i = 0, len = fieldDetailsList.size(); i < len; i++) {
            XSSFRow row = ExcelUtil.createRow(sheet, i + 3);
            for (int j = 0; j < lastColIdx; j++) {
                XSSFCell cell = row.createCell(j + 1);
                // 第二列内容
                if (j == 0) {
                    cell.setCellStyle(ExcelUtil.getStyle(true, workbook));
                    cell.setCellValue(i + 1);
                    continue;
                }

                FieldDetails fieldDetails = fieldDetailsList.get(i);
                // 第三列内容
                if (j == 1) {
                    cell.setCellValue(fieldDetails.getExcelName());
                    cell.setCellStyle(ExcelUtil.getStyle(workbook));
                    continue;
                }

                // 其他列内容
                FinanceAnalysisSimple financeAnalysisSimple = financeAnalysisSimples.get(j - 2);
                cell.setCellStyle(ExcelUtil.getStyle(workbook));
                cell.setCellValue((String) ReflectUtil.getFieldValue(financeAnalysisSimple, fieldDetails.getField()));
            }
        }

        sheet.setColumnWidth(0, 4 * 255);
        sheet.setColumnWidth(1, 4 * 255);
    }

    /**
     * 设置列宽
     *
     * @param sheet      sheet对象
     * @param lastColIdx 最后一列下标
     */
    private void setColWidth(XSSFSheet sheet, int lastColIdx) {
        // 设置列宽
        for (int i = 0; i <= lastColIdx; i++) {
            sheet.setColumnWidth(i, 14 * 255);
        }
    }

    /**
     * 添加小标题行
     *
     * @param workbook excel对象
     * @param sheet    sheet对象
     * @param title    标题数组
     */
    private void addTitleRow(XSSFWorkbook workbook, XSSFSheet sheet, List<String> title) {
        addTitleRow(workbook, sheet, title, 2);
    }

    /**
     * 添加小标题行
     *
     * @param workbook excel对象
     * @param sheet    sheet对象
     * @param title    标题数组
     * @param rownum   行下标
     */
    private void addTitleRow(XSSFWorkbook workbook, XSSFSheet sheet, List<String> title, int rownum) {
        XSSFRow titleRow2 = sheet.createRow(rownum);
        for (int i = 0, len = title.size(); i < len; i++) {
            XSSFCell cell = titleRow2.createCell(i);
            XSSFCellStyle style = ExcelUtil.getStyle(true, new int[]{204, 255, 255}, true, workbook);
            cell.setCellStyle(style);
            cell.setCellValue(title.get(i));
        }
    }

    /**
     * 添加单位行
     *
     * @param workbook   excel对象
     * @param sheet      sheet对象
     * @param lastColIdx 最后一列下标
     */
    private void addUnitRow(XSSFWorkbook workbook, XSSFSheet sheet, int lastColIdx) {
        XSSFRow unitRow = sheet.createRow(1);
        XSSFCell unitCell = unitRow.createCell(0);
        XSSFCellStyle unitStyle = ExcelUtil.getStyle(false, IndexedColors.WHITE.index, IndexedColors.BLUE.index, workbook);
        unitStyle.setAlignment(HorizontalAlignment.RIGHT);
        unitCell.setCellValue("单位:人民币万元");
        unitCell.setCellStyle(unitStyle);
        CellRangeAddress unitRegion = new CellRangeAddress(1, 1, 0, lastColIdx);
        sheet.addMergedRegion(unitRegion);
        ExcelUtil.setBorderStyle(unitRegion, sheet);
    }

    /**
     * 设置第一行大标题行
     *
     * @param workbook     excel对象
     * @param sheet        sheel对象
     * @param titleContent 标题内容
     * @param lastColIdx   最后一列下标
     */
    private void addBigTitleRow(XSSFWorkbook workbook, XSSFSheet sheet, String titleContent, int lastColIdx) {
        XSSFRow titleRow = sheet.createRow(0);
        XSSFCell titleCell = titleRow.createCell(0);
        XSSFCellStyle titleStyle = ExcelUtil.getStyle(true, true, 16, workbook);
        titleCell.setCellStyle(titleStyle);
        titleCell.setCellValue(titleContent);
        CellRangeAddress region = new CellRangeAddress(0, 0, 0, lastColIdx);
        sheet.addMergedRegion(region);
        // 给合并后的区域设置边框
        ExcelUtil.setBorderStyle(region, sheet);
    }

    /**
     * 设置第一列内容
     *
     * @param sheet    sheet对象
     * @param workbook excel对象
     * @param firstRow 合并列从第几行开始
     * @param lastRow  合并列从第几行结束
     * @param content  内容
     */
    private void setOneColContent(XSSFSheet sheet, XSSFWorkbook workbook, int firstRow, int lastRow, String content) {
        XSSFRow row = sheet.createRow(firstRow);
        XSSFCell cell = row.createCell(0);
        cell.setCellValue(content);
        XSSFCellStyle style = ExcelUtil.getStyle(true, true, workbook);
        // 设置自动换行
        style.setWrapText(true);
        cell.setCellStyle(style);
        CellRangeAddress region = new CellRangeAddress(firstRow, lastRow, 0, 0);
        sheet.addMergedRegion(region);
        // 给合并后的区域设置边框
        ExcelUtil.setBorderStyle(region, sheet);
    }

    /**
     * 创建基本财务数据sheet页
     *
     * @param workbook                excel对象
     * @param baseFinanceDatumSimples 基本财务数据集合
     */
    private void createBaseFinanceDataSheet(XSSFWorkbook workbook, List<BaseFinanceDataSimple> baseFinanceDatumSimples) {
        String sheetAndTitleName = "基本财务数据";
        XSSFSheet sheet = workbook.createSheet(sheetAndTitleName);

        int lastColIdx = 4;
        // 添加大标题行
        addBigTitleRow(workbook, sheet, sheetAndTitleName, lastColIdx);
        // 添加单位行
        addUnitRow(workbook, sheet, lastColIdx);
        // 第二行标题
        List<String> title = Lists.newArrayList("科目", baseFinanceDatumSimples.get(0).getDate(),
                baseFinanceDatumSimples.get(1).getDate(), baseFinanceDatumSimples.get(2).getDate(), baseFinanceDatumSimples.get(3).getDate());
        // 添加小标题行
        addTitleRow(workbook, sheet, title);
        // 设置列宽
        setColWidth(sheet, lastColIdx);

        List<FieldDetails> fieldDetailsList = ReflectCacheUtil.getExcelFieldList(baseFinanceDatumSimples.get(0).getClass());
        for (int i = 0, len = fieldDetailsList.size(); i < len; i++) {
            XSSFRow row = sheet.createRow(i + 3);
            for (int j = 0; j <= lastColIdx; j++) {
                XSSFCell cell = row.createCell(j);

                FieldDetails fieldDetails = fieldDetailsList.get(i);
                // 第一列
                if (j == 0) {
                    XSSFCellStyle style = ExcelUtil.getStyle(true, true, workbook);
                    cell.setCellStyle(style);
                    cell.setCellValue(fieldDetails.getExcelName());
                    continue;
                }

                // 其他列
                BaseFinanceDataSimple baseFinanceDataSimple = baseFinanceDatumSimples.get(j - 1);
                cell.setCellStyle(ExcelUtil.getStyle(workbook));
                cell.setCellValue((String) ReflectUtil.getFieldValue(baseFinanceDataSimple, fieldDetails.getField()));
            }
        }

    }

}
