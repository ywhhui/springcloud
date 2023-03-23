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
import com.szcgc.comm.util.UsccUtil;
import com.szcgc.customer.constant.DicEnum;
import com.szcgc.customer.model.manufacture.InboundChannel;
import com.szcgc.customer.repository.manufacture.InboundChannelRepository;
import com.szcgc.customer.service.manufacture.InboundChannelService;
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
 * 进货渠道业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-22 09:45:08
 */
@Service
public class InboundChannelServiceImpl extends BaseService<InboundChannelRepository, InboundChannel, Integer> implements InboundChannelService {

    @Override
    public List<InboundChannel> list(Integer projectId, Integer custId) {
        return repository.getInboundChannelByProjectIdAndCustomerId(projectId, custId);
    }

    @Override
    public List<InboundChannel> importData(MultipartFile file) throws Exception {
        List<InboundChannel> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), InboundChannel.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), InboundChannel.class, true);
            // 校验数据
            errorTips = ExcelUtil.verifyTable(data, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }

            for (int i = 0, len = data.size(); i < len; i++) {
                InboundChannel obj = data.get(i);
                String err = "";
                if (DicEnum.CUST_TYPE_PERSON.getValue().intValue() == obj.getSupplierType()) {
                    // 个人校验身份证
                    err = UsccUtil.isIdentityCode(obj.getUniqueNo());
                } else if (DicEnum.CUST_TYPE_COMPANY.getValue().intValue() == obj.getSupplierType()) {
                    // 企业校验统一社会信用代码
                    err = UsccUtil.checkUscc(obj.getUniqueNo());
                }
                if (StrUtil.isNotBlank(err)) {
                    errorTips = errorTips.concat(String.format("第[%s]行%s\n", i + 3, err));
                }
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
        String key = "$inboundChannel";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            List<InboundChannel> inboundChannels = repository.getInboundChannelByProjectIdAndCustomerId(projectId, custId);
            // 在指定key处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key);
            // 插入数据
            inserInfo(table, inboundChannels);

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
     * @param table           表格对象
     * @param inboundChannels 数据集合
     */
    private void inserInfo(XWPFTable table, List<InboundChannel> inboundChannels) {
        XWPFTableRow oneRow = DocUtil.getRow(table, 0);

        List<String> oneLevelTitle = Lists.newArrayList("年份", "供应商名称", "供应产品", "采购额（万元）", "结算周期");

        for (int i = 0, len = oneLevelTitle.size(); i < len; i++) {
            XWPFTableCell oneCell = DocUtil.getCenterCell(oneRow, i);
            oneCell.setText(oneLevelTitle.get(i));
        }

        List<String> yearList = Lists.newArrayList();
        // 把数据根据年份分别存储
        Map<String, List<InboundChannel>> map = Maps.newHashMap();
        for (InboundChannel inboundChannel : inboundChannels) {
            String year = inboundChannel.getYear();
            if (!map.containsKey(year)) {
                yearList.add(year);
                map.put(year, Lists.newArrayList());
            }
            map.get(year).add(inboundChannel);
        }
        // 年份排序
        yearList.sort(Comparator.comparingInt(Integer::parseInt));

        String format = "%s年1-%s月";
        // 从第几行开始合并
        int startIdx = 1;
        // 填充数据
        for (int i = 0, len = yearList.size(); i < len; i++) {
            String key = yearList.get(i);
            InboundChannel count = InboundChannel.builder()
                    .purchaseAmount(BigDecimal.ZERO)
                    .build();

            // 第二个年份开始要再设置一遍标题
            if (i > 0) {
                XWPFTableRow row = DocUtil.getRow(table, startIdx);
                InboundChannel inboundChannel = map.get(key).get(0);
                // 如果只有两个年份第二个年份是xxxx年1-xx月,否则是xxxx年
                XWPFTableCell cell = DocUtil.getCenterCell(row, 0);
                cell.setText(i == len - 1 ? String.format(format, inboundChannel.getYear(),
                        inboundChannel.getMonth()) : inboundChannel.getYear().concat("年"));
                for (int j = 0, len2 = oneLevelTitle.size(); j < len2; j++) {
                    if (j == 0) {
                        continue;
                    }
                    String title = oneLevelTitle.get(j);
                    DocUtil.getCenterCell(row, j).setText(title);
                }
                startIdx++;
            }

            // 填充具体数据
            for (int j = 0, len2 = map.get(key).size(); j < len2; j++) {
                InboundChannel inboundChannel = map.get(key).get(j);
                XWPFTableRow row = DocUtil.getRow(table, j + startIdx);
                DocUtil.getCenterCell(row, 0).setText(inboundChannel.getYear().concat("年"));
                DocUtil.getCenterCell(row, 1).setText(inboundChannel.getSupplierName());
                DocUtil.getCenterCell(row, 2).setText(inboundChannel.getSupplyProducts());
                DocUtil.getCenterCell(row, 3).setText(CalcUtil.round(inboundChannel.getPurchaseAmount(), 2));
                DocUtil.getCenterCell(row, 4).setText(inboundChannel.getSettlementInterval());

                count.setPurchaseAmount(NumberUtil.add(count.getPurchaseAmount(), inboundChannel.getPurchaseAmount()));
            }

            // 填充合计行
            XWPFTableRow row = table.createRow();
            DocUtil.getCenterCell(row, 0).setText(count.getYear());
            DocUtil.getCenterCell(row, 1).setText("合计");
            DocUtil.getCenterCell(row, 2).setText("");
            DocUtil.getCenterCell(row, 3).setText(CalcUtil.round(count.getPurchaseAmount(), 2));
            DocUtil.getCenterCell(row, 4).setText("");

            if (i > 0) {
                // 合并年份列
                DocUtil.mergeCell(table, startIdx - 1, startIdx + map.get(key).size(), 0, 0);
            } else {
                // 合并年份列
                DocUtil.mergeCell(table, startIdx, startIdx + map.get(key).size(), 0, 0);
            }
            startIdx = startIdx + map.get(key).size() + 1;

        }

        XWPFTableRow row = DocUtil.getRow(table, startIdx);
        DocUtil.setRowHeight(row, 1000);

        XWPFTableCell cell = DocUtil.getCenterCell(row, 0);
        cell.setText("供应渠道总体评价");
        DocUtil.getCenterCell(row, 1);
        DocUtil.getCenterCell(row, 2);
        DocUtil.getCenterCell(row, 3);
        DocUtil.getCenterCell(row, 4);
        DocUtil.mergeCell(table, startIdx, startIdx, 1, 4);


    }

}
