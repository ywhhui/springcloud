package com.szcgc.project.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.szcgc.comm.constant.Const;
import com.szcgc.project.constant.RepayInterestEnum;
import com.szcgc.project.constant.RepayLoanEnum;
import com.szcgc.project.dto.RepayTrailDto;
import com.szcgc.project.service.IRepayTrialService;
import com.szcgc.project.vo.repay.RepayTrialPlanVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.CharEncoding;
import org.apache.commons.compress.utils.Lists;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RepayTrialService implements IRepayTrialService {

    private final DateTimeFormatter YYYY_MM_DD_FORMAT = DateTimeFormatter.ofPattern(Const.DateFormat.YYYY_MM_DD);

    private final DateTimeFormatter YMD_FORMAT = DateTimeFormatter.ofPattern(Const.DateFormat.YMD);

    private final Integer YEAR = 360;

    private final Integer ONE_HUNDRED = 100;

    private final Integer MONTH = 30;

    /**
     * 小数位数
     */
    private final Integer PLACE = 2;

    @Override
    public List<RepayTrialPlanVo> calc(RepayTrailDto repayTrailDto) {
        repayTrailDto.setAmount(NumberUtil.round(repayTrailDto.getAmount(), PLACE));

        // 重新试算
        if (CollectionUtil.isNotEmpty(repayTrailDto.getList())) {
            List<RepayTrialPlanVo> list = reCalc(repayTrailDto);
            // 计算服务费
            list.add(0, getServiceAmount(repayTrailDto));
            return list;
        }

        // 等额本息需要特殊计算
        if (RepayLoanEnum.C10.getValue().equals(repayTrailDto.getRepayLoan())) {
            List<RepayTrialPlanVo> list = calcAcpi(repayTrailDto);
            // 计算服务费
            list.add(0, getServiceAmount(repayTrailDto));
            return list;
        }

        List<RepayTrialPlanVo> list = Lists.newArrayList();
        // 到期一次性还本
        if (RepayLoanEnum.C01.getValue().equals(repayTrailDto.getRepayLoan())) {
            list.add(getC01(repayTrailDto));
            // 计算服务费
            list.add(0, getServiceAmount(repayTrailDto));
            return list;
        }
        // 先计算本金
        list = calcCapital(repayTrailDto);
        // 再计算利息
        list = calcAccrual(repayTrailDto, list);
        // 计算服务费
        list.add(0, getServiceAmount(repayTrailDto));
        return list;
    }

    @Override
    public void export(List<String> titleList, List<List<String>> valueList, HttpServletResponse response) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook();
        try {
            XSSFSheet sheet = workbook.createSheet("业务台账列表");

            // 设置表头数据
            XSSFRow titleRow = sheet.createRow(0);
            XSSFCellStyle titleStyle = getStyle(true, new int[]{128, 128, 128}, IndexedColors.WHITE.index, true, 10, workbook);
            for (int i = 0, len = titleList.size(); i < len; i++) {
                XSSFCell titleCell = titleRow.createCell(i);
                titleCell.setCellStyle(titleStyle);
                titleRow.getCell(i).setCellValue(titleList.get(i));
            }

            // 设置内容
            XSSFCellStyle style = getStyle(true, IndexedColors.WHITE.index, IndexedColors.BLACK.index, false, 10, workbook);
            for (int i = 0, len = valueList.size(); i < len; i++) {
                List<String> values = valueList.get(i);
                XSSFRow row = sheet.createRow(i + 1);
                for (int j = 0, len2 = titleList.size(); j < len2; j++) {
                    XSSFCell cell = row.createCell(j);
                    cell.setCellStyle(style);
                    row.getCell(j).setCellValue(values.get(j));
                }
            }

            // 设置列宽
            for (int i = 0, len = titleList.size(); i < len; i++) {
                sheet.setColumnWidth(i, 16 * 255);
            }

            response.setContentType("application/vnd.ms-excel;charset=utf-8");
            response.setCharacterEncoding(CharEncoding.UTF_8);
            String fileName = "还款计划".concat(Const.Symbol.UNDERLINE).concat(String.valueOf(System.currentTimeMillis())).concat(Const.Suffix.XLSX);
            response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, CharEncoding.UTF_8));

            workbook.write(response.getOutputStream());
        } finally {
            IoUtil.close(workbook);
        }
    }

    /**
     * 根据rgb数组生成颜色对象
     *
     * @param rgb rgb数组
     * @return 颜色对象
     */
    public XSSFColor getDefinedColor(int[] rgb) {
        return new XSSFColor(new java.awt.Color(rgb[0], rgb[1], rgb[2]), new DefaultIndexedColorMap());
    }

    /**
     * 获取样式
     *
     * @param center    是否居中
     * @param backColor 背景颜色
     * @param color     字体颜色
     * @param bold      是否加粗
     * @param fontSize  字体大小
     * @param workbook  excel对象
     * @return 样式对象
     */
    public XSSFCellStyle getStyle(boolean center, Object backColor, short color, boolean bold, int fontSize, XSSFWorkbook workbook) {
        XSSFCellStyle style = workbook.createCellStyle();
        if (center) {
            // 水平居中
            style.setAlignment(HorizontalAlignment.CENTER);
            // 垂直居中
            style.setVerticalAlignment(VerticalAlignment.CENTER);
        }
        // 背景颜色
        if (backColor instanceof Short) {
            style.setFillForegroundColor((short) backColor);
        } else if (backColor instanceof int[]) {
            style.setFillForegroundColor(getDefinedColor((int[]) backColor));
        }
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        //设置边框 上 右 下 左
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);

        Font font = workbook.createFont();
        font.setFontName("微软雅黑");
        font.setColor(color);
        font.setBold(bold);
        font.setFontHeight((short) (fontSize * 20));
        style.setFont(font);

        style.setLocked(false);

        return style;
    }

    /**
     * 获取到期一次性还本
     *
     * @param repayTrailDto 试算数据
     * @return 一次性还本计划
     */
    private RepayTrialPlanVo getC01(RepayTrailDto repayTrailDto) {
        LocalDate startDate = LocalDate.parse(repayTrailDto.getStartDate(), YYYY_MM_DD_FORMAT);
        LocalDate endDate = LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT);
        // 日利率
        BigDecimal dayRate = NumberUtil.div(NumberUtil.div(repayTrailDto.getRate(), ONE_HUNDRED), YEAR);
        BigDecimal shouldRepayAccrual = NumberUtil.round(NumberUtil.mul(repayTrailDto.getAmount(), ChronoUnit.DAYS.between(startDate, endDate), dayRate), PLACE);
        RepayTrialPlanVo repayTrialPlanVo = new RepayTrialPlanVo();
        repayTrialPlanVo.setDate(repayTrailDto.getEndDate());
        repayTrialPlanVo.setPeriod(1);
        repayTrialPlanVo.setShouldRepayCapital(repayTrailDto.getAmount());
        repayTrialPlanVo.setShouldRepayAccrual(shouldRepayAccrual);
        repayTrialPlanVo.setShouldRepayServiceAmount(BigDecimal.ZERO);
        repayTrialPlanVo.setShouldRepayCount(NumberUtil.round(NumberUtil.add(shouldRepayAccrual, repayTrialPlanVo.getShouldRepayCapital()), PLACE));
        repayTrialPlanVo.setResidueCapital(BigDecimal.ZERO);
        repayTrialPlanVo.setCreateIncome(getCreateIncome(repayTrailDto, shouldRepayAccrual));
        return repayTrialPlanVo;
    }

    /**
     * 重新试算
     *
     * @param repayTrailDto 试算信息
     * @return 试算还款计划
     */
    private List<RepayTrialPlanVo> reCalc(RepayTrailDto repayTrailDto) {
        List<RepayTrialPlanVo> list = Lists.newArrayList();
        LocalDate endDate = LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT);
        // 日利率
        BigDecimal dayRate = NumberUtil.div(NumberUtil.div(repayTrailDto.getRate(), ONE_HUNDRED), YEAR);
        // 等额本金、等额本息不允许重新试算
        if (RepayLoanEnum.C09.getValue().equals(repayTrailDto.getRepayLoan()) || RepayLoanEnum.C10.getValue().equals(repayTrailDto.getRepayLoan())) {
            return list;
        }
        List<RepayTrialPlanVo> existsList = repayTrailDto.getList();
        // 排除期次为0以及没有还款日期的数据
        existsList = existsList.stream().filter(obj -> (ObjectUtil.isNull(obj.getPeriod()) || obj.getPeriod() != 0)
                && StrUtil.isNotBlank(obj.getDate())).collect(Collectors.toList());
        // 把集合根据日期升序排序一遍
        existsList.sort(Comparator.comparingLong(obj -> Long.parseLong(LocalDate.parse(obj.getDate(), YYYY_MM_DD_FORMAT).format(YMD_FORMAT))));
        for (int i = 0, len = existsList.size(); i < len; i++) {
            RepayTrialPlanVo existsObj = existsList.get(i);
            existsObj.setPeriod(i + 1);
            // 上一期剩余本金
            BigDecimal lastResidueCapital = repayTrailDto.getAmount();
            String currDate = existsObj.getDate();
            String lastDate = repayTrailDto.getStartDate();
            if (i > 0) {
                lastResidueCapital = list.get(i - 1).getResidueCapital();
                lastDate = list.get(i - 1).getDate();
            }

            // 距离上一期的天数
            long diffDay = ChronoUnit.DAYS.between(LocalDate.parse(lastDate, YYYY_MM_DD_FORMAT), LocalDate.parse(currDate, YYYY_MM_DD_FORMAT));
            // 重新计算剩余应还、利息、创收、合计
            existsObj.setResidueCapital(NumberUtil.round(NumberUtil.sub(lastResidueCapital, existsObj.getShouldRepayCapital()), PLACE));
            // 利息
            BigDecimal shouldRepayAccrual = NumberUtil.round(NumberUtil.mul(dayRate, diffDay, lastResidueCapital), PLACE);
            // 手工账单利息以已存在的为准
            if (ObjectUtil.isNotNull(existsObj.getHandmadeBill()) && existsObj.getHandmadeBill()) {
                shouldRepayAccrual = existsObj.getShouldRepayAccrual();
            }
            existsObj.setShouldRepayAccrual(shouldRepayAccrual);
            existsObj.setCreateIncome(getCreateIncome(repayTrailDto, existsObj.getShouldRepayAccrual()));
            existsObj.setShouldRepayCount(NumberUtil.round(NumberUtil.add(existsObj.getShouldRepayCapital(), existsObj.getShouldRepayAccrual()), PLACE));
            existsObj.setShouldRepayServiceAmount(BigDecimal.ZERO);

            // 剩余本金小于0时重新计算应还并设置剩余本金为0
            if (existsObj.getResidueCapital().doubleValue() < 0) {
                existsObj.setShouldRepayCapital(lastResidueCapital);
                existsObj.setShouldRepayAccrual(shouldRepayAccrual);
                existsObj.setCreateIncome(getCreateIncome(repayTrailDto, existsObj.getShouldRepayAccrual()));
                existsObj.setShouldRepayCount(NumberUtil.round(NumberUtil.add(existsObj.getShouldRepayCapital(), existsObj.getShouldRepayAccrual()), PLACE));
                existsObj.setResidueCapital(BigDecimal.ZERO);
                list.add(existsObj);
                break;
            }

            if (i == len - 1) {
                // 最后一期还没还完,而且最后一期时间等于到期时间
                if (repayTrailDto.getEndDate().equals(existsObj.getDate())) {
                    existsObj.setShouldRepayCapital(lastResidueCapital);
                    existsObj.setResidueCapital(BigDecimal.ZERO);
                    existsObj.setShouldRepayCount(NumberUtil.round(NumberUtil.add(lastResidueCapital, existsObj.getShouldRepayAccrual()), PLACE));
                    existsObj.setCreateIncome(getCreateIncome(repayTrailDto, existsObj.getShouldRepayAccrual()));
                } else {
                    // 最后一期没还完,而且最后一期时间不等于到期时间增加一期
                    list.add(existsObj);

                    diffDay = ChronoUnit.DAYS.between(LocalDate.parse(existsObj.getDate(), YYYY_MM_DD_FORMAT),
                            LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT));

                    RepayTrialPlanVo repayTrialPlanVo = RepayTrialPlanVo.builder()
                            .date(repayTrailDto.getEndDate())
                            .period(i + 2)
                            .shouldRepayCapital(existsObj.getResidueCapital())
                            .shouldRepayAccrual(NumberUtil.round(NumberUtil.mul(diffDay, existsObj.getResidueCapital(), dayRate), PLACE))
                            .shouldRepayServiceAmount(BigDecimal.ZERO)
                            .residueCapital(BigDecimal.ZERO)
                            .build();
                    repayTrialPlanVo.setShouldRepayCount(NumberUtil.round(NumberUtil.add(repayTrialPlanVo.getResidueCapital(),
                            repayTrialPlanVo.getShouldRepayAccrual()), PLACE));
                    repayTrialPlanVo.setCreateIncome(getCreateIncome(repayTrailDto, repayTrialPlanVo.getShouldRepayAccrual()));
                    list.add(repayTrialPlanVo);
                    break;
                }
            }

            list.add(existsObj);
        }

        return list;
    }

    /**
     * 获取服务费期次
     *
     * @param repayTrailDto 试算信息
     * @return 服务费期次
     */
    private RepayTrialPlanVo getServiceAmount(RepayTrailDto repayTrailDto) {
        // 服务费
        BigDecimal serviceAmount = BigDecimal.ZERO;
        if (ObjectUtil.isNull(repayTrailDto.getServiceRate()) || BigDecimal.ZERO.equals(repayTrailDto.getServiceRate())) {
            if (ObjectUtil.isNotNull(repayTrailDto.getServiceAmount())) {
                serviceAmount = repayTrailDto.getServiceAmount();
            }
        } else {
            serviceAmount = NumberUtil.mul(NumberUtil.div(repayTrailDto.getServiceRate(), ONE_HUNDRED), repayTrailDto.getAmount());
        }
        serviceAmount = NumberUtil.round(serviceAmount, PLACE);

        // 服务费期次
        return RepayTrialPlanVo.builder()
                .date(repayTrailDto.getStartDate())
                .period(0)
                .shouldRepayCapital(BigDecimal.ZERO)
                .shouldRepayAccrual(BigDecimal.ZERO)
                .shouldRepayServiceAmount(serviceAmount)
                .shouldRepayCount(serviceAmount)
                .residueCapital(repayTrailDto.getAmount())
                .createIncome(BigDecimal.ZERO)
                .build();
    }

    /**
     * 计算等额本息
     *
     * @param repayTrailDto 试算信息
     * @return 试算还款计划
     */
    private List<RepayTrialPlanVo> calcAcpi(RepayTrailDto repayTrailDto) {
        List<RepayTrialPlanVo> list = Lists.newArrayList();
        LocalDate startDate = LocalDate.parse(repayTrailDto.getStartDate(), YYYY_MM_DD_FORMAT);
        LocalDate endDate = LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT);
        // 每期间隔月数
        int addMonth = getAddMonth2(repayTrailDto.getRepayLoan());
        // 期数
        int period = getPeriod(startDate, endDate, addMonth, repayTrailDto.getRepayDate());
        // 首期还款日
        LocalDate firstDate = getFirstDate(startDate, addMonth, repayTrailDto.getRepayDate());
        // 每期应还本金
        BigDecimal eachPeriodCapital = getEachPeriodCapital(repayTrailDto, period);
        // 日利率
        BigDecimal dayRate = NumberUtil.div(NumberUtil.div(repayTrailDto.getRate(), ONE_HUNDRED), YEAR);
        for (int i = 0; i < period; i++) {
            // 获取当期还款日
            String date = getRepayDate(firstDate, i + 1, addMonth, repayTrailDto.getRepayDate());
            // 上期剩余本金
            BigDecimal lastResidueCapital;
            if (i == 0) {
                lastResidueCapital = repayTrailDto.getAmount();
            } else {
                lastResidueCapital = list.get(i - 1).getResidueCapital();
            }
            // 利息=剩余本金*日利率*30
            BigDecimal shouldRepayAccrual = NumberUtil.round(NumberUtil.mul(lastResidueCapital, MONTH, dayRate), PLACE);
            // 应还本金
            BigDecimal shouldRepayCapital = NumberUtil.round(NumberUtil.sub(eachPeriodCapital, shouldRepayAccrual), PLACE);

            RepayTrialPlanVo repayTrialPlanVo = RepayTrialPlanVo.builder()
                    .date(date)
                    .period(i + 1)
                    .shouldRepayCapital(shouldRepayCapital)
                    .shouldRepayAccrual(shouldRepayAccrual)
                    .shouldRepayServiceAmount(BigDecimal.ZERO)
                    .shouldRepayCount(eachPeriodCapital)
                    .residueCapital(NumberUtil.round(NumberUtil.sub(lastResidueCapital, shouldRepayCapital), PLACE))
                    .createIncome(getCreateIncome(repayTrailDto, shouldRepayAccrual))
                    .build();
            // 最后一期还款日大于结束日期用结束日期为最后一期还款日
            if (i == period - 1 && ChronoUnit.DAYS.between(LocalDate.parse(date, YYYY_MM_DD_FORMAT), endDate) < 0) {
                repayTrialPlanVo.setDate(repayTrailDto.getEndDate());
            }

            // 最后一期还没还完的话
            if (i == period - 1 && repayTrialPlanVo.getResidueCapital().doubleValue() > 0) {
                repayTrialPlanVo.setShouldRepayCapital(lastResidueCapital);
                repayTrialPlanVo.setShouldRepayAccrual(BigDecimal.ZERO);
                repayTrialPlanVo.setResidueCapital(BigDecimal.ZERO);
                repayTrialPlanVo.setShouldRepayCount(lastResidueCapital);
                repayTrialPlanVo.setCreateIncome(getCreateIncome(repayTrailDto, repayTrialPlanVo.getShouldRepayAccrual()));
            }

            // 剩余本金小于0时重新计算应还并设置剩余本金为0
            if (repayTrialPlanVo.getResidueCapital().doubleValue() < 0) {
                repayTrialPlanVo.setShouldRepayCapital(lastResidueCapital);
                repayTrialPlanVo.setShouldRepayAccrual(getNum(NumberUtil.sub(eachPeriodCapital, lastResidueCapital)));
                repayTrialPlanVo.setShouldRepayCount(eachPeriodCapital);
                if (i != period - 1) {
                    repayTrialPlanVo.setShouldRepayAccrual(shouldRepayAccrual);
                    repayTrialPlanVo.setShouldRepayCount(NumberUtil.add(lastResidueCapital, shouldRepayAccrual));
                }
                repayTrialPlanVo.setResidueCapital(BigDecimal.ZERO);
                repayTrialPlanVo.setCreateIncome(getCreateIncome(repayTrailDto, repayTrialPlanVo.getShouldRepayAccrual()));
                list.add(repayTrialPlanVo);
                break;
            }

            list.add(repayTrialPlanVo);
        }

        return list;
    }

    private BigDecimal getNum(BigDecimal num) {
        if (ObjectUtil.isNull(num) || num.doubleValue() < 0) {
            return BigDecimal.ZERO;
        }
        return num;
    }

    /**
     * 获取创收
     *
     * @param repayTrailDto      试算信息
     * @param shouldRepayAccrual 应还利息
     * @return 创收
     */
    private BigDecimal getCreateIncome(RepayTrailDto repayTrailDto, BigDecimal shouldRepayAccrual) {
        // 创收=(年利率-资金成本)/年利率*利息
        return NumberUtil.div(NumberUtil.mul(NumberUtil.sub(repayTrailDto.getRate(), repayTrailDto.getCapitalRegister()),
                shouldRepayAccrual), repayTrailDto.getRate(), PLACE);
    }

    /**
     * 计算利息
     *
     * @param repayTrailDto 试算信息
     * @param list          带本金的还款计划
     * @return 带利息的还款计划
     */
    private List<RepayTrialPlanVo> calcAccrual(RepayTrailDto repayTrailDto, List<RepayTrialPlanVo> list) {
        if (CollectionUtil.isEmpty(list)) {
            return Lists.newArrayList();
        }

        // 将带有本金信息的还款计划转成以还款日期为key，对象为value的map
        Map<String, RepayTrialPlanVo> map = list.stream().collect(Collectors.toMap(RepayTrialPlanVo::getDate, obj -> obj));

        LocalDate startDate = LocalDate.parse(repayTrailDto.getStartDate(), YYYY_MM_DD_FORMAT);
        LocalDate endDate = LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT);
        // 日利率
        BigDecimal dayRate = NumberUtil.div(NumberUtil.div(repayTrailDto.getRate(), ONE_HUNDRED), YEAR);

        List<RepayTrialPlanVo> resList = Lists.newArrayList();
        // 每期相隔月数
        int addMonth = getAddMonth(repayTrailDto.getRepayInterest());
        // 期次数
        int period = getPeriod(startDate, endDate, addMonth, repayTrailDto.getRepayDate());
        // 首期还款日
        LocalDate firstDate = getFirstDate(startDate, addMonth, repayTrailDto.getRepayDate());
        for (int i = 0; i < period; i++) {
            // 获取还款日期
            String date = getRepayDate(firstDate, i + 1, addMonth, repayTrailDto.getRepayDate());
            // 最后一期还款日大于结束日期则用结束日期当左后一起还款日
            if (i == period - 1 && ChronoUnit.DAYS.between(endDate, LocalDate.parse(date, YYYY_MM_DD_FORMAT)) > 0) {
                date = repayTrailDto.getEndDate();
            }
            // 包含本金数据的对象
            RepayTrialPlanVo capitalObj = getObjByDate(map, date, repayTrailDto);
            // 上一期还款日
            String lastDate;
            if (i == 0) {
                lastDate = repayTrailDto.getStartDate();
            } else {
                lastDate = resList.get(i - 1).getDate();
            }
            // 上期包含本金数据的对象
            RepayTrialPlanVo lastCapitalObj = getObjByDate(map, lastDate, repayTrailDto);
            // 当期还款日与上一期还款日相差天数
            long diffDay = ChronoUnit.DAYS.between(LocalDate.parse(lastDate, YYYY_MM_DD_FORMAT), LocalDate.parse(date, YYYY_MM_DD_FORMAT));
            // 等额本金每个周期为30天
            if (i != 0 && i != period - 1 && RepayInterestEnum.I08.getValue().equals(repayTrailDto.getRepayInterest())) {
                diffDay = MONTH;
            }
            // 利息=剩余本金*日利率*天数
            BigDecimal shouldRepayAccrual = NumberUtil.round(NumberUtil.mul(lastCapitalObj.getResidueCapital(), diffDay, dayRate), PLACE);

            RepayTrialPlanVo repayTrialPlanVo = RepayTrialPlanVo.builder()
                    .date(date)
                    .period(i + 1)
                    .shouldRepayAccrual(shouldRepayAccrual)
                    .shouldRepayCapital(capitalObj.getShouldRepayCapital())
                    .residueCapital(capitalObj.getResidueCapital())
                    .shouldRepayServiceAmount(BigDecimal.ZERO)
                    .shouldRepayCount(NumberUtil.round(NumberUtil.add(capitalObj.getShouldRepayCapital(), shouldRepayAccrual), PLACE))
                    .createIncome(getCreateIncome(repayTrailDto, shouldRepayAccrual))
                    .build();
            resList.add(repayTrialPlanVo);

            // 本期剩余应还小于等于0跳出循环
            if (capitalObj.getResidueCapital().doubleValue() <= 0) {
                break;
            }
        }

        return resList;
    }

    private int getAddMonth(String interest) {
        if (StrUtil.isBlank(interest)) {
            return 1;
        }
        switch (RepayInterestEnum.valueOf(interest)) {
            // 按月还息、等额本金
            case I03:
            case I08:
            case I99:
                return 1;
            // 按季还息
            case I04:
                return 3;
            // 按半年还息
            case I05:
                return 6;
            // 按年还息
            case I07:
                return 12;
            default: {
                log.error("未知还息方案");
                return 1;
            }
        }
    }

    private int getAddMonth2(String repayLoan) {
        if (StrUtil.isBlank(repayLoan)) {
            return 1;
        }
        switch (RepayLoanEnum.valueOf(repayLoan)) {
            // 按月等额还本、等额本金、等额本息
            case C03:
            case C10:
            case C09:
            case C99:
                return 1;
            // 按季等额还本
            case C05:
                return 3;
            // 按半年等额还本
            case C07:
                return 6;
            // 按年等额还本
            case C08:
                return 12;
            default: {
                log.error("未知还本方案");
                return 1;
            }
        }
    }

    /**
     * 获取含有本金数据的对象
     *
     * @param map           含有本金数据对象map
     * @param date          还款日期
     * @param repayTrailDto 试算信息
     * @return 含有本金数据的对象
     */
    private RepayTrialPlanVo getObjByDate(Map<String, RepayTrialPlanVo> map, String date, RepayTrailDto repayTrailDto) {
        // 没有应还本金数据自己造
        if (!map.containsKey(date)) {
            LocalDate lastDate = LocalDate.parse(date, YYYY_MM_DD_FORMAT).plusMonths(-1);
            // 没有还款日期拿还款所在月最后一天当还款日
            if (ObjectUtil.isNull(repayTrailDto.getRepayDate()) || repayTrailDto.getRepayDate() < 0) {
                lastDate = lastDate.with(TemporalAdjusters.lastDayOfMonth());
            }
            LocalDate startDate = LocalDate.parse(repayTrailDto.getStartDate(), YYYY_MM_DD_FORMAT);

            map.put(date, RepayTrialPlanVo.builder()
                    .shouldRepayCapital(BigDecimal.ZERO)
                    .residueCapital(repayTrailDto.getAmount())
                    .build());

            // 上月在还款开始结束时间
            if (ChronoUnit.DAYS.between(startDate, lastDate) >= 0) {
                // 尝试获取上一月数据
                RepayTrialPlanVo repayTrialPlanVo = getObjByDate(map, lastDate.format(YYYY_MM_DD_FORMAT), repayTrailDto);
                map.get(date).setShouldRepayCount(repayTrialPlanVo.getShouldRepayCapital());
                map.get(date).setResidueCapital(repayTrialPlanVo.getResidueCapital());
            }
        }
        return map.get(date);
    }

    /**
     * 计算本金
     *
     * @param repayTrailDto 试算信息
     * @return 带本金的还款计划
     */
    private List<RepayTrialPlanVo> calcCapital(RepayTrailDto repayTrailDto) {
        List<RepayTrialPlanVo> list = Lists.newArrayList();

        LocalDate startDate = LocalDate.parse(repayTrailDto.getStartDate(), YYYY_MM_DD_FORMAT);
        LocalDate endDate = LocalDate.parse(repayTrailDto.getEndDate(), YYYY_MM_DD_FORMAT);
        // 每期相隔月数
        int addMonth = getAddMonth2(repayTrailDto.getRepayLoan());
        // 期次数
        int period = getPeriod(startDate, endDate, addMonth, repayTrailDto.getRepayDate());
        // 首期还款日
        LocalDate firstDate = getFirstDate(startDate, addMonth, repayTrailDto.getRepayDate());
        // 每期应还本金
        BigDecimal eachPeriodCapital = getEachPeriodCapital(repayTrailDto, period);
        for (int i = 0; i < period; i++) {
            // 获取还款日期
            String date = getRepayDate(firstDate, i + 1, addMonth, repayTrailDto.getRepayDate());
            BigDecimal lastResidueCapital;
            if (i == 0) {
                lastResidueCapital = repayTrailDto.getAmount();
            } else {
                lastResidueCapital = list.get(i - 1).getResidueCapital();
            }
            RepayTrialPlanVo repayTrialPlanVo = RepayTrialPlanVo.builder()
                    .date(date)
                    .shouldRepayCapital(eachPeriodCapital)
                    .residueCapital(NumberUtil.round(NumberUtil.sub(lastResidueCapital, eachPeriodCapital), PLACE))
                    .build();

            // 最后一期还款日大于结束日期用结束日期为最后一期还款日
            if (i == period - 1 && ChronoUnit.DAYS.between(LocalDate.parse(date, YYYY_MM_DD_FORMAT), endDate) < 0) {
                repayTrialPlanVo.setDate(repayTrailDto.getEndDate());
            }

            // 最后一期还没还完的话
            if (i == period - 1 && repayTrialPlanVo.getResidueCapital().doubleValue() > 0) {
                repayTrialPlanVo.setShouldRepayCapital(lastResidueCapital);
                repayTrialPlanVo.setResidueCapital(BigDecimal.ZERO);
            }

            // 剩余本金小于0时重新计算应还并设置剩余本金为0
            if (repayTrialPlanVo.getResidueCapital().doubleValue() < 0) {
                repayTrialPlanVo.setShouldRepayCapital(NumberUtil.round(NumberUtil.sub(repayTrailDto.getAmount(), NumberUtil.mul(i, eachPeriodCapital)), PLACE));
                repayTrialPlanVo.setResidueCapital(BigDecimal.ZERO);
                list.add(repayTrialPlanVo);
                break;
            }

            list.add(repayTrialPlanVo);
        }
        return list;
    }

    /**
     * 获取每期还款日
     *
     * @param firstDate 首期还款日
     * @param n         第n期
     * @param addMonth  每期间隔数
     * @param repayDate 还款日
     * @return 指定期次的还款日
     */
    private String getRepayDate(LocalDate firstDate, int n, int addMonth, Integer repayDate) {
        String date = firstDate.plusMonths((n - 1) * addMonth).format(YYYY_MM_DD_FORMAT);
        // 没有输入还款日取还款所在月最后一天
        if (repayDate == null || repayDate <= 0) {
            date = LocalDate.parse(date, YYYY_MM_DD_FORMAT).with(TemporalAdjusters.lastDayOfMonth()).format(YYYY_MM_DD_FORMAT);
        }
        return date;
    }

    /**
     * 获取每期还款额度
     *
     * @param repayTrailDto 试算信息
     * @param period        期数
     * @return 每期还款额度
     */
    private BigDecimal getEachPeriodCapital(RepayTrailDto repayTrailDto, int period) {
        if (ObjectUtil.isNotNull(repayTrailDto.getEachRepayAmount()) && repayTrailDto.getEachRepayAmount().doubleValue() > 0) {
            return NumberUtil.round(repayTrailDto.getEachRepayAmount(), PLACE);
        }
        // 等额本息每期还款额度需要特殊计算计算
        if (RepayLoanEnum.C10.getValue().equals(repayTrailDto.getRepayLoan())) {
            // 月利率
            BigDecimal monthRate = NumberUtil.mul(NumberUtil.div(NumberUtil.div(repayTrailDto.getRate(), ONE_HUNDRED), YEAR), MONTH);
            // (1+月利率)^期数
            BigDecimal monthRateAddOne = NumberUtil.pow(NumberUtil.add(1, monthRate), period);
            // 月均还款 = 贷款本金×月利率×(1+月利率)^期数÷[(1+月利率)^期数-1]
            BigDecimal res = NumberUtil.div(NumberUtil.mul(repayTrailDto.getAmount(), monthRate, monthRateAddOne), NumberUtil.sub(monthRateAddOne, 1));
            return NumberUtil.round(res, PLACE);
        }
        return NumberUtil.div(repayTrailDto.getAmount(), period, PLACE);
    }

    /**
     * 获取首期还款日
     *
     * @param startDate 开始时间
     * @param addMonth  每期间隔数
     * @param repayDate 还款日
     * @return 首期还款日
     */
    private LocalDate getFirstDate(LocalDate startDate, int addMonth, Integer repayDate) {
        LocalDate localDate;
        if (repayDate != null && repayDate > 0) {
            localDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), repayDate);
        } else {
            // 没传还款日则每月最后一天是还款日
            localDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
        }
        // 如果本月还款日在开始时间之后,且每期间隔数为1(代表是每月还的类型)就从当前日期开始为首个还款日
        if (ChronoUnit.DAYS.between(startDate, localDate) > 0 && addMonth == 1) {
            return localDate;
        }
        return localDate.plusMonths(addMonth);
    }

    /**
     * 计算一共有多少期
     *
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param addMonth  间隔月数
     * @param repayDate 还款日
     * @return 期数
     */
    private int getPeriod(LocalDate startDate, LocalDate endDate, int addMonth, Integer repayDate) {
        int period = 1;
        // 首期还款日
        LocalDate localDate = getFirstDate(startDate, addMonth, repayDate);
        // 计算一共需要多少期
        while (ChronoUnit.DAYS.between(startDate, localDate) > 0 && ChronoUnit.DAYS.between(localDate, endDate) > 0) {
            localDate = localDate.plusMonths(addMonth);
            period++;
        }
        return period;
    }


}
