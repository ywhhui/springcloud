package com.szcgc.finance.model.base;

import com.szcgc.finance.annotation.Excel;
import com.szcgc.finance.model.FactorInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 现金流量表
 *
 * @author chenjiaming
 * @date 2022-9-22 21:51:16
 */
@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "现金流量表")
@EqualsAndHashCode(callSuper = false)
@Table(name = "t_finance_cash_flow_statement", schema = "gmis_customer")
public class CashFlowStatement extends FactorInfo {

    @Schema(description = "年月", required = true)
    private String date;

    @Excel
    @Schema(description = "销售商品、提供劳务收到的现金")
    private BigDecimal c01;

    @Excel
    @Schema(description = "收到的税费返还")
    private BigDecimal c02;

    @Excel
    @Schema(description = "收到的其他与经营活动有关的现金")
    private BigDecimal c03;

    @Excel(formula = "SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    经营活动产生的现金流入小计")
    private BigDecimal c04;

    @Excel
    @Schema(description = "购买商品、接受劳务支付的现金")
    private BigDecimal c05;

    @Excel
    @Schema(description = "支付给职工以及为职工支付的现金")
    private BigDecimal c06;

    @Excel
    @Schema(description = "支付的各项税费")
    private BigDecimal c07;

    @Excel
    @Schema(description = "支付的其他与经营活动有关的现金")
    private BigDecimal c08;

    @Excel(formula = "SUM(${colIdx}${rowIdx-4}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    经营活动产生的现金流出小计")
    private BigDecimal c09;

    @Excel(formula = "${colIdx}${rowIdx-6}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "经营活动产生的现金流量净额")
    private BigDecimal c10;

    @Excel
    @Schema(description = "收回投资所收到的现金")
    private BigDecimal c11;

    @Excel
    @Schema(description = "取得投资收益所收到的现金")
    private BigDecimal c12;

    @Excel
    @Schema(description = "处置固定资产、无形资产和其他长期资产而收回的现金净额")
    private BigDecimal c13;

    @Excel
    @Schema(description = "收到的其他与投资活动有关的现金")
    private BigDecimal c14;

    @Excel(formula = "SUM(${colIdx}${rowIdx-4}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    投资活动产生的现金流入小计")
    private BigDecimal c15;

    @Excel
    @Schema(description = "购建固定资产、无形资产和其他长期资产所支付的现金")
    private BigDecimal c16;

    @Excel
    @Schema(description = "投资所支付的现金")
    private BigDecimal c17;

    @Excel
    @Schema(description = "支付的其它与投资活动有关的现金")
    private BigDecimal c18;

    @Excel(formula = "SUM(${colIdx}${rowIdx-3}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    投资活动产生的现金流出小计")
    private BigDecimal c19;

    @Excel(formula = "${colIdx}${rowIdx-5}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "投资活动产生的现金流量净额")
    private BigDecimal c20;

    @Excel
    @Schema(description = "吸收投资所收到的现金")
    private BigDecimal c21;

    @Excel
    @Schema(description = "  其中：子公司吸收少数股东权益收到的现金")
    private BigDecimal c22;

    @Excel
    @Schema(description = "借款所收到的现金")
    private BigDecimal c23;

    @Excel
    @Schema(description = "收到的其他与筹资活动有关的现金")
    private BigDecimal c24;

    @Excel(formula = "SUM(${colIdx}${rowIdx-4}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    筹资活动产生的现金流入小计")
    private BigDecimal c25;

    @Excel
    @Schema(description = "偿还债务所支付的现金")
    private BigDecimal c26;

    @Excel
    @Schema(description = "分配股利、利润或偿还利息所支付的现金")
    private BigDecimal c27;

    @Excel
    @Schema(description = "  其中：子公司支付少数股东的股利")
    private BigDecimal c28;

    @Excel
    @Schema(description = "减少注册资本所支付的现金")
    private BigDecimal c29;

    @Excel
    @Schema(description = "  其中：子公司依法减资支付给少数股东的现金")
    private BigDecimal c30;

    @Excel
    @Schema(description = "支付的其他与筹资活动有关的现金")
    private BigDecimal c31;

    @Excel(formula = "SUM(${colIdx}${rowIdx-6}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    筹资活动产生的现金流出小计")
    private BigDecimal c32;

    @Excel(formula = "${colIdx}${rowIdx-8}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "筹资活动产生的现金流量净额")
    private BigDecimal c33;

    @Excel
    @Schema(description = "汇率变动对现金的影响额")
    private BigDecimal c34;

    @Excel(formula = "${colIdx}${rowIdx-25}+${colIdx}${rowIdx-15}+${colIdx}${rowIdx-2}+${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "现金及现金等价净增加额")
    private BigDecimal c35;

    @Excel
    @Schema(description = "债务转为资本")
    private BigDecimal c36;

    @Excel
    @Schema(description = "一年内到期的可转换公司债券")
    private BigDecimal c37;

    @Excel
    @Schema(description = "融资租赁固定资产")
    private BigDecimal c38;

    @Excel
    @Schema(description = "净利润")
    private BigDecimal c39;

    @Excel
    @Schema(description = "少数股东权益")
    private BigDecimal c40;

    @Excel
    @Schema(description = "加：计提的资产减值准备")
    private BigDecimal c41;

    @Excel
    @Schema(description = "    固定资产折旧")
    private BigDecimal c42;

    @Excel
    @Schema(description = "    无形资产摊销")
    private BigDecimal c43;

    @Excel
    @Schema(description = "    长期待摊费用摊销")
    private BigDecimal c44;

    @Excel
    @Schema(description = "    待摊费用减少（减：增加）")
    private BigDecimal c45;

    @Excel
    @Schema(description = "    预提费用增加（减：减少）")
    private BigDecimal c46;

    @Excel
    @Schema(description = "    处置固定资产、无形资产和其他长期资产的损失（减：收益）")
    private BigDecimal c47;

    @Excel
    @Schema(description = "    固定资产报废损失")
    private BigDecimal c48;

    @Excel
    @Schema(description = "    财务费用")
    private BigDecimal c49;

    @Excel
    @Schema(description = "    投资损失（减：收益）")
    private BigDecimal c50;

    @Excel
    @Schema(description = "    递延税款贷项（减：借项）")
    private BigDecimal c51;

    @Excel
    @Schema(description = "    存货的减少（减：增加）")
    private BigDecimal c52;

    @Excel
    @Schema(description = "    经营性应收项目的减少（减：增加）")
    private BigDecimal c53;

    @Excel
    @Schema(description = "    经营性应付项目的增加（减：减少）")
    private BigDecimal c54;

    @Excel
    @Schema(description = "    其他")
    private BigDecimal c55;

    @Excel(formula = "SUM(${colIdx}${rowIdx-17}:${colIdx}${rowIdx-1})", edit = false)
    @Schema(description = "    经营活动产生的现金流量净额")
    private BigDecimal c56;

    @Excel
    @Schema(description = "现金的期末余额")
    private BigDecimal c57;

    @Excel
    @Schema(description = "减：现金的期初余额")
    private BigDecimal c58;

    @Excel
    @Schema(description = "加：现金等价物的期末余额")
    private BigDecimal c59;

    @Excel
    @Schema(description = "减：现金等价物的期初余额")
    private BigDecimal c60;

    @Excel(formula = "${colIdx}${rowIdx-4}-${colIdx}${rowIdx-3}+${colIdx}${rowIdx-2}-${colIdx}${rowIdx-1}", edit = false)
    @Schema(description = "现金及现金等价物增加额")
    private BigDecimal c61;
//
//    public static void main(String[] args) {
//        XSSFWorkbook workbook = null;
//        try {
//            String format = "@Excel\n" +
//                    "    @Schema(description = \"%s\")\n" +
//                    "    private String %s;\n\n";
//
//            StringBuilder finalStr = new StringBuilder("");
//            workbook = new XSSFWorkbook(new FileInputStream("D:/Desktop/财务报表数据及勾稽关系20220324.xlsx"));
//            XSSFSheet sheet = workbook.getSheetAt(11);
//            for (int i = 0; i < 26; i++) {
//                XSSFRow row = sheet.getRow(i);
//                String col1Value = row.getCell(6).getStringCellValue();
//                String col0Value = row.getCell(7).getStringCellValue().toLowerCase();
//                finalStr.append(String.format(format, col1Value, col0Value));
//            }
//            System.out.println(finalStr.toString());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            IoUtil.close(workbook);
//        }
//    }


}
