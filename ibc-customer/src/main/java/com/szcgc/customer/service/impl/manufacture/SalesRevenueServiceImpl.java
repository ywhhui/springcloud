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
import com.szcgc.customer.model.manufacture.SalesRevenue;
import com.szcgc.customer.repository.manufacture.SalesRevenueRepository;
import com.szcgc.customer.service.manufacture.SalesRevenueService;
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
 * 销售收入业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-22 16:45:57
 */
@Service
public class SalesRevenueServiceImpl extends BaseService<SalesRevenueRepository, SalesRevenue, Integer> implements SalesRevenueService {

    @Override
    public List<SalesRevenue> list(Integer projectId, Integer custId) {
        return repository.getSalesRevenueByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<SalesRevenue> importData(MultipartFile file) throws Exception {
        List<SalesRevenue> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), SalesRevenue.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), SalesRevenue.class, true);
            // 校验数据
            errorTips = ExcelUtil.verifyTable(data, true);
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
        String key = "$salesRevenue";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            List<SalesRevenue> salesRevenues = repository.getSalesRevenueByProjectIdAndCustomerId(projectId, custId);

            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key, 10000);
            // 插入数据
            inserInfo(table, salesRevenues);

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
     * @param table         表格对象
     * @param salesRevenues 数据集合
     */
    private void inserInfo(XWPFTable table, List<SalesRevenue> salesRevenues) {
        XWPFTableRow oneRow = DocUtil.getRow(table, 0);

        List<String> oneLevelTitle = Lists.newArrayList("销售收入", "1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月",
                "10月", "11月", "12月", "合计", "月均");

        for (int i = 0, len = oneLevelTitle.size(); i < len; i++) {
            XWPFTableCell oneCell = DocUtil.getCenterCell(oneRow, i);
            oneCell.setText(oneLevelTitle.get(i));
        }

        List<String> yearList = Lists.newArrayList();
        // 把数据根据年份分别存储
        Map<String, List<SalesRevenue>> map = Maps.newHashMap();
        for (SalesRevenue salesRevenue : salesRevenues) {
            String year = salesRevenue.getYear();
            if (!map.containsKey(year)) {
                yearList.add(year);
                map.put(year, Lists.newArrayList());
            }
            map.get(year).add(salesRevenue);
        }
        // 年份排序
        yearList.sort(Comparator.comparingInt(Integer::parseInt));

        // 从第几行开始
        int startIdx = 1;
        // 填充数据
        for (int i = 0, len = yearList.size(); i < len; i++) {
            String key = yearList.get(i);
            List<SalesRevenue> salesRevenueList = map.get(key);
            // 填充具体数据
            for (int j = 0, len2 = salesRevenueList.size(); j < len2; j++) {
                SalesRevenue salesRevenue = salesRevenueList.get(j);
                XWPFTableRow row = DocUtil.getRow(table, j + startIdx);
                DocUtil.getCenterCell(row, 0).setText(salesRevenue.getYear().concat("年"));
                DocUtil.getCenterCell(row, 1).setText(CalcUtil.round(salesRevenue.getOne(), 2));
                DocUtil.getCenterCell(row, 2).setText(CalcUtil.round(salesRevenue.getTwo(), 2));
                DocUtil.getCenterCell(row, 3).setText(CalcUtil.round(salesRevenue.getThree(), 2));
                DocUtil.getCenterCell(row, 4).setText(CalcUtil.round(salesRevenue.getFour(), 2));
                DocUtil.getCenterCell(row, 5).setText(CalcUtil.round(salesRevenue.getFive(), 2));
                DocUtil.getCenterCell(row, 6).setText(CalcUtil.round(salesRevenue.getSix(), 2));
                DocUtil.getCenterCell(row, 7).setText(CalcUtil.round(salesRevenue.getSeven(), 2));
                DocUtil.getCenterCell(row, 8).setText(CalcUtil.round(salesRevenue.getEight(), 2));
                DocUtil.getCenterCell(row, 9).setText(CalcUtil.round(salesRevenue.getNight(), 2));
                DocUtil.getCenterCell(row, 10).setText(CalcUtil.round(salesRevenue.getTen(), 2));
                DocUtil.getCenterCell(row, 11).setText(CalcUtil.round(salesRevenue.getEleven(), 2));
                DocUtil.getCenterCell(row, 12).setText(CalcUtil.round(salesRevenue.getTwelve(), 2));

                BigDecimal count = NumberUtil.add(salesRevenue.getOne(), salesRevenue.getTwo(), salesRevenue.getThree(), salesRevenue.getFour(),
                        salesRevenue.getFive(), salesRevenue.getSix(), salesRevenue.getSeven(), salesRevenue.getEight(), salesRevenue.getNight(),
                        salesRevenue.getTen(), salesRevenue.getEleven(), salesRevenue.getTwelve());
                DocUtil.getCenterCell(row, 13).setText(CalcUtil.round(count, 2));
                DocUtil.getCenterCell(row, 14).setText(CalcUtil.div(count, new BigDecimal(12)));
            }
            startIdx = startIdx + salesRevenueList.size();
        }

    }
}
