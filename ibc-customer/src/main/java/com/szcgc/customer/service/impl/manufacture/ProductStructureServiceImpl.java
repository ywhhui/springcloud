package com.szcgc.customer.service.impl.manufacture;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.customer.model.manufacture.ProductStructure;
import com.szcgc.customer.repository.manufacture.ProductStructureRepository;
import com.szcgc.customer.service.manufacture.ProductStructureService;
import com.szcgc.customer.util.CalcUtil;
import com.szcgc.customer.util.DocUtil;
import com.szcgc.customer.util.ExcelUtil;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;


/**
 * 产品结构业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-21 09:52:07
 */
@Service
public class ProductStructureServiceImpl extends BaseService<ProductStructureRepository, ProductStructure, Integer> implements ProductStructureService {

    @Override
    public List<ProductStructure> list(Integer projectId, Integer custId) {
        return repository.getProductStructureByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<ProductStructure> importData(MultipartFile file) throws Exception {
        List<ProductStructure> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), ProductStructure.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), ProductStructure.class, true);
            // 校验数据
            errorTips = ExcelUtil.verifyTable(data, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

        } finally {
            tempFile.delete();
        }
        return data;
    }

    @Override
    public void export(Integer projectId, Integer custId, String path) throws Exception {
        XWPFDocument doc = null;
        File file = null;
        FileOutputStream outputStream = null;
        String key = "$productStructure";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            List<ProductStructure> productStructures = repository.getProductStructureByProjectIdAndCustomerId(projectId, custId);

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key);
            // 插入数据
            inserInfo(table, productStructures);

            doc.write(outputStream);

        } finally {
            IoUtil.close(outputStream);
            IoUtil.close(doc);
            try {
                FileUtil.copy(file, new File(path), true);
            } finally {
                FileUtil.del(file);
            }
        }
    }


    /**
     * 把信息插入表格
     *
     * @param table             表格对象
     * @param productStructures 数据集合
     */
    private void inserInfo(XWPFTable table, List<ProductStructure> productStructures) {
        XWPFTableRow oneRow = DocUtil.getRow(table, 0);
        XWPFTableRow twoRow = DocUtil.getRow(table, 1);

        List<String> oneLevelTitle = Lists.newArrayList("年份", "主要产品名称", "销售收入（万元）", "占总销售额比例", "内外销比例", "");
        List<String> twoLevelTitle = Lists.newArrayList("年份", "主要产品名称", "销售收入（万元）", "占总销售额比例", "内销占比", "外销占比");


        for (int i = 0, len = twoLevelTitle.size(); i < len; i++) {
            XWPFTableCell oneCell = DocUtil.getCenterCell(oneRow, i);
            oneCell.setText(oneLevelTitle.get(i));

            XWPFTableCell twoCell = DocUtil.getCenterCell(twoRow, i);
            twoCell.setText(twoLevelTitle.get(i));

            // 合并表头单元格
            if (oneLevelTitle.get(i).equals(twoLevelTitle.get(i))) {
                DocUtil.mergeCell(table, 0, 1, i, i);
            }
        }
        // 合并表头单元格
        DocUtil.mergeCell(table, 0, 0, 4, 5);

        List<String> yearList = Lists.newArrayList();
        // 把数据根据年份分别存储
        Map<String, List<ProductStructure>> map = Maps.newHashMap();
        for (ProductStructure productStructure : productStructures) {
            String year = productStructure.getYear();
            if (!map.containsKey(year)) {
                yearList.add(year);
                map.put(year, Lists.newArrayList());
            }
            map.get(year).add(productStructure);
        }
        // 年份排序
        yearList.sort(Comparator.comparingInt(Integer::parseInt));

        String format = "%s年1-%s月";
        // 从第几行开始合并
        int startIdx = 2;
        // 填充数据
        for (int i = 0, len = yearList.size(); i < len; i++) {
            String key = yearList.get(i);
            ProductStructure count = ProductStructure.builder()
                    .salesRevenue(BigDecimal.ZERO)
                    .salesTotalRatio(0.0)
                    .homeSalesRatio(0.0)
                    .exportSalesRatio(0.0)
                    .build();

            // 第二个年份开始要再设置一遍标题
            if (i > 0) {
                XWPFTableRow row = DocUtil.getRow(table, startIdx);
                ProductStructure productStructure = map.get(key).get(0);
                // 如果只有两个年份第二个年份是xxxx年1-xx月,否则是xxxx年
                XWPFTableCell cell = DocUtil.getCenterCell(row, 0);
                cell.setText(i == len - 1 ? String.format(format, productStructure.getYear(),
                        productStructure.getMonth()) : productStructure.getYear().concat("年"));
                for (int j = 0, len2 = twoLevelTitle.size(); j < len2; j++) {
                    if (j == 0) {
                        continue;
                    }
                    String title = twoLevelTitle.get(j);
                    DocUtil.getCenterCell(row, j).setText(title);
                }
                startIdx++;
            }

            // 填充具体数据
            for (int j = 0, len2 = map.get(key).size(); j < len2; j++) {
                ProductStructure productStructure = map.get(key).get(j);
                XWPFTableRow row = DocUtil.getRow(table, j + startIdx);
                DocUtil.getCenterCell(row, 0).setText(productStructure.getYear().concat("年"));
                DocUtil.getCenterCell(row, 1).setText(productStructure.getProductName());
                DocUtil.getCenterCell(row, 2).setText(CalcUtil.round(productStructure.getSalesRevenue(), 2));
                DocUtil.getCenterCell(row, 3).setText(String.valueOf(productStructure.getSalesTotalRatio()).concat(Const.Symbol.PERCENT));
                DocUtil.getCenterCell(row, 4).setText(String.valueOf(productStructure.getHomeSalesRatio()).concat(Const.Symbol.PERCENT));
                DocUtil.getCenterCell(row, 5).setText(String.valueOf(productStructure.getExportSalesRatio()).concat(Const.Symbol.PERCENT));

                count.setSalesRevenue(NumberUtil.add(count.getSalesRevenue(), productStructure.getSalesRevenue()));
                count.setSalesTotalRatio(NumberUtil.add(count.getSalesTotalRatio(), productStructure.getSalesTotalRatio()));
                count.setHomeSalesRatio(NumberUtil.add(count.getHomeSalesRatio(), productStructure.getHomeSalesRatio()));
                count.setExportSalesRatio(NumberUtil.add(count.getExportSalesRatio(), productStructure.getExportSalesRatio()));
            }

            // 填充合计行
            XWPFTableRow row = table.createRow();
            DocUtil.getCenterCell(row, 0).setText(count.getYear());
            DocUtil.getCenterCell(row, 1).setText("合计");
            DocUtil.getCenterCell(row, 2).setText(CalcUtil.round(count.getSalesRevenue(), 2));
            DocUtil.getCenterCell(row, 3).setText(String.valueOf(count.getSalesTotalRatio()).concat(Const.Symbol.PERCENT));
            DocUtil.getCenterCell(row, 4).setText(String.valueOf(count.getHomeSalesRatio()).concat(Const.Symbol.PERCENT));
            DocUtil.getCenterCell(row, 5).setText(String.valueOf(count.getExportSalesRatio()).concat(Const.Symbol.PERCENT));

            if (i > 0) {
                // 合并年份列
                DocUtil.mergeCell(table, startIdx - 1, startIdx + map.get(key).size(), 0, 0);
            } else {
                // 合并年份列
                DocUtil.mergeCell(table, startIdx, startIdx + map.get(key).size(), 0, 0);
            }
            startIdx = startIdx + map.get(key).size() + 1;

        }

    }

}
