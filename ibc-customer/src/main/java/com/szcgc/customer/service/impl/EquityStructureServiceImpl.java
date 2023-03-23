package com.szcgc.customer.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.exception.BaseException;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.customer.model.CustomerInfo;
import com.szcgc.customer.model.EquityStructure;
import com.szcgc.customer.repository.CustomerRepository;
import com.szcgc.customer.repository.EquityStructureRepository;
import com.szcgc.customer.service.EquityStructureService;
import com.szcgc.customer.util.CalcUtil;
import com.szcgc.customer.util.DocUtil;
import com.szcgc.customer.util.ExcelUtil;
import com.szcgc.third.tyc.feign.ITycClient;
import com.szcgc.third.tyc.model.holder.HolderCapitalDto;
import com.szcgc.third.tyc.model.holder.HolderDto;
import com.szcgc.third.tyc.model.holder.HolderItemDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 股权结构业务实现类
 *
 * @author chenjiaming
 * @date 2022-9-29 18:09:53
 */
@Slf4j
@Service
public class EquityStructureServiceImpl extends BaseService<EquityStructureRepository, EquityStructure, Integer> implements EquityStructureService {

    @Resource
    private ITycClient tycClient;

    @Resource
    private CustomerRepository customerRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<EquityStructure> list(Integer accountId, Integer projectId, Integer custId) {
        List<EquityStructure> equityStructures = repository.getByProjectIdAndCustomerId(projectId, custId);

        // 有数据直接返回
        if (CollectionUtil.isNotEmpty(equityStructures)) {
            return equityStructures;
        }

        // 没有数据查询大数据,先根据客户id获取客户信息
        CustomerInfo customerInfo = customerRepository.findById(custId).orElse(CustomerInfo.builder().name("").build());
        // 根据客户名称查股东信息
        HolderDto holderDto = tycClient.holder(customerInfo.getName(), 1, 999);
        log.info("根据客户名称查股东信息结果为:{}", JsonUtils.toJSONString(holderDto));
        if (ObjectUtil.isEmpty(holderDto)) {
            return Lists.newArrayList();
        }

        List<HolderItemDto> items = holderDto.items;

        Map<String, EquityStructure> map = Maps.newHashMap();
        for (HolderItemDto item : items) {

            if (ObjectUtil.isNull(item)) {
                continue;
            }

            // 认缴
            if (CollectionUtil.isNotEmpty(item.capital)) {
                for (HolderCapitalDto obj : item.capital) {
                    if (!map.containsKey(item.name)) {
                        EquityStructure equityStructure = EquityStructure.builder()
                                .name(item.name)
                                .alias(item.alias)
                                .type(item.type)
                                .capital(obj.amomon)
                                .paymet(obj.paymet)
                                .time(obj.time)
                                .percent(obj.percent)
                                .build();
                        equityStructure.setProjectId(projectId);
                        equityStructure.setCustomerId(custId);
                        equityStructure.setCreateBy(custId);
                        equityStructure.setUpdateBy(custId);
                        map.put(item.name, equityStructure);
                        continue;
                    }
                    EquityStructure equityStructure = map.get(item.name);
                    // 提取数字
                    String num = extractNumber(obj.amomon);
                    // 提取单位
                    String unit = obj.amomon.replace(num, "");
                    equityStructure.setCapital(CalcUtil.add(extractNumber(equityStructure.getCapital()), extractNumber(obj.amomon)).concat(unit));
                    // 提取数字
                    num = extractNumber(obj.percent);
                    // 提取单位
                    unit = obj.percent.replace(num, "");
                    equityStructure.setCapital(CalcUtil.add(extractNumber(equityStructure.getPercent()), extractNumber(obj.percent)).concat(unit));
                }
            }

            // 实缴
            if (CollectionUtil.isNotEmpty(item.capitalActl)) {
                for (HolderCapitalDto obj : item.capitalActl) {
                    if (!map.containsKey(item.name)) {
                        EquityStructure equityStructure = EquityStructure.builder()
                                .name(item.name)
                                .alias(item.alias)
                                .type(item.type)
                                .capitalActl(obj.amomon)
                                .paymet(obj.paymet)
                                .time(obj.time)
                                .percent(obj.percent)
                                .build();
                        equityStructure.setProjectId(projectId);
                        equityStructure.setCustomerId(custId);
                        continue;
                    }
                    EquityStructure equityStructure = map.get(item.name);
                    // 提取数字
                    String num = extractNumber(obj.amomon);
                    // 提取单位
                    String unit = obj.amomon.replace(num, "");
                    equityStructure.setCapitalActl(CalcUtil.add(extractNumber(equityStructure.getCapitalActl()), extractNumber(obj.amomon)).concat(unit));
                }
            }

            equityStructures = Lists.newArrayList(map.values());
        }

        repository.saveAll(equityStructures);

        return equityStructures;
    }

    @Override
    public List<EquityStructure> importData(MultipartFile file) throws Exception {
        List<EquityStructure> data = null;
        String errorTips = "";
        File tempFile = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.XLSX);
        try {
            file.transferTo(tempFile);
            // 校验表头
            errorTips = ExcelUtil.verifyHead(new FileInputStream(tempFile), EquityStructure.class, true);
            if (StrUtil.isNotBlank(errorTips)) {
                throw new BaseException(errorTips);
            }
            // 获取数据
            data = ExcelUtil.getData(new FileInputStream(tempFile), EquityStructure.class, true);
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
        String key = "$equityStructure";
        try {
            doc = new XWPFDocument(new FileInputStream(path));
            file = File.createTempFile(String.valueOf(System.currentTimeMillis()), Const.Suffix.DOCX);
            outputStream = new FileOutputStream(file);
            // 查数据
            List<EquityStructure> equityStructures = repository.getByProjectIdAndCustomerId(projectId, custId);

            // 在key值处插入表格
            XWPFTable table = DocUtil.insertTableByKey(doc, key);

            // 获取表格数据
            List<List<String>> dataList = getTableData(equityStructures);

            // 添加表格数据
            DocUtil.addTableContent(table, dataList);

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
     * 获取表格数据
     *
     * @param equityStructures 数据集合
     * @return 表格数据集合
     */
    private List<List<String>> getTableData(List<EquityStructure> equityStructures) {
        List<List<String>> dataList = Lists.newArrayList();

        // 表头
        List<String> titleList = Lists.newArrayList("序号", "股东名称", "出资金额(万元)", "出资方式", "股权比例", "备注");
        dataList.add(titleList);

        BigDecimal count = BigDecimal.ZERO;
        for (int i = 0, len = equityStructures.size(); i < len; i++) {
            EquityStructure equityStructure = equityStructures.get(i);
            if (ObjectUtil.isNull(equityStructure)) {
                continue;
            }
            List<String> valueList = Lists.newArrayList(String.valueOf(i + 1), equityStructure.getName(), equityStructure.getCapitalActl(),
                    equityStructure.getPaymet(), equityStructure.getPercent(), equityStructure.getRemarks());
            dataList.add(valueList);
            count = NumberUtil.add(count, new BigDecimal(extractNumber(equityStructure.getCapitalActl())));
        }
        dataList.add(Lists.newArrayList("合计", "", CalcUtil.round(count, 2), "", "", ""));

        return dataList;

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
