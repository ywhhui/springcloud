package com.szcgc.cougua.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.constant.Const;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.JsonUtils;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.constant.CounterGuaranteeTypePropEnum;
import com.szcgc.cougua.feign.GuaranteeDocClient;
import com.szcgc.cougua.model.IndividualDetailInfo;
import com.szcgc.cougua.model.IndividualInfo;
import com.szcgc.cougua.model.MaterialInfo;
import com.szcgc.cougua.repository.IndividualDetailRepository;
import com.szcgc.cougua.repository.IndividualRepository;
import com.szcgc.cougua.repository.MaterialRepository;
import com.szcgc.cougua.service.IMaterialService;
import com.szcgc.cougua.utils.FileUtils;
import com.szcgc.cougua.utils.TemplateToWordUtils;
import com.szcgc.cougua.vo.AssessedReportTemplateVo;
import com.szcgc.cougua.vo.GuaranteeDocVo;
import com.szcgc.cougua.vo.ResultVo;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.dto.UploadFileDto;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 测试实时更新 自动刷新nacos配置 在应用不需要重启的情况下热加载新的外部化配置的值
 */
@Service
@RefreshScope
public class MaterialService extends BaseService<MaterialRepository, MaterialInfo, Integer> implements IMaterialService {

    private static final Log logger = LogFactory.getLog(MaterialService.class);

    @Autowired
    IFileClient fileClient;

    @Value("${report.template.path:C:\\\\file\\\\template}")
//    @Value("${ywhpath}")
    private String templatePath;

    @Autowired
    IndividualRepository individualRepository;

    @Autowired
    IndividualDetailRepository individualDetailRepository;

    @Autowired
    GuaranteeDocClient guaranteeDocClient;

    @Override
    public List<MaterialInfo> findByProjectId(Integer projectId) {
        List<MaterialInfo> result = repository.findByProjectId(projectId);
        //倒叙  文件id转path
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(MaterialInfo::getId).reversed()).collect(Collectors.toList());
            for (MaterialInfo materialInfo:result) {
                fileIdToPath(materialInfo);
            }
        }
        return result;
    }

    /**
     *  文件fileId转path
     * @param materialInfo
     */
    private void fileIdToPath(MaterialInfo materialInfo) {
        StringBuilder filePath = new StringBuilder();
        try {
            if(!StringUtils.isBlank(materialInfo.getFileList())){
                Map<Integer, String> pathByIds = fileClient.findPathByIds(materialInfo.getFileList());
                pathByIds.values().stream().forEach(str->{
                    filePath.append(str).append(",");
                });
                filePath.deleteCharAt(filePath.length()-1);
                materialInfo.setFileList(filePath.toString());
            }
        } catch (Exception e) {
            logger.info("fileIdToPath error!"+e);
        }
    }

    @Override
    public Optional<MaterialInfo> find(int id) {
        Optional<MaterialInfo> info = repository.findById(id);
        //文件fileId转path
        fileIdToPath(info.get());
        return info;
    }

    @Override
    public List<MaterialInfo> searchByProjectId(Integer projectId) {
        List<MaterialInfo> result = findByProjectId(projectId);
        //过滤出需要评估的反担保物
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().filter(v->v.getCate().equals(CounterGuaranteeTypeCateEnum.FDCDY)
                    || v.getCate().equals(CounterGuaranteeTypeCateEnum.JSYDSYQ)
                    || v.getCate().equals(CounterGuaranteeTypeCateEnum.GUPIAO)).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public MaterialInfo insert(MaterialInfo entity) {
        //反担保文件相关
        fileOperation(entity);
        MaterialInfo info = repository.save(Objects.requireNonNull(entity));
        return info;
    }

    /**
     * upload 反担保相关文件新增
     * @param entity
     */
    private void fileOperation(MaterialInfo entity) {
        StringBuilder fileIdList = new StringBuilder();
        String filePathList = entity.getFileList();
        logger.info("fileOperation filePathList:"+filePathList);
        try {
            //材料类型
            FileCateEnum cate = null;
            if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.FDCDY)){
                cate = FileCateEnum.Cl_Fcz;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.JSYDSYQ)){
                cate = FileCateEnum.Cl_Tudi;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.ZLQUAN)){
                cate = FileCateEnum.Cl_Zlwj;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.ZZQUAN)){
                cate = FileCateEnum.Cl_Zzql;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.SB)){
                cate = FileCateEnum.Cl_Sbzc;
            }
            //先删再添加 根据fileId删除
            if(null != cate){
                if(0 != entity.getId()){
                    Optional<MaterialInfo> info = repository.findById(entity.getId());
                    if(!StringUtils.isEmpty(info.get().getFileList())){
                        for (String fileId:info.get().getFileList().split(",")) {
                            fileClient.del(Integer.valueOf(fileId));
                        }
                    }
                }
                //文件path转fileId
                if(!StringUtils.isEmpty(filePathList)){
                    String[] splitStr = filePathList.split(",");
                    for (int i = 0; i < splitStr.length; i++) {
                        FileInfo file = new FileInfo();
                        file.setCate(cate);
                        file.setCateName(cate.getCnName());
                        file.setProjectId(entity.getProjectId());
                        file.setPath(splitStr[i]);
                        String fileName = file.getPath().substring(file.getPath().lastIndexOf("/")+1);
                        file.setName(fileName);
                        file.setCreateAt(LocalDateTime.now());
                        file.setCreateBy(entity.getCreateBy());
                        file.setDelTag(Byte.valueOf("0"));
                        IbcResult<Integer> addResult = fileClient.add(file);
                        if(null != addResult && addResult.isOk()){
                            if(fileIdList.length()>0){
                                fileIdList.append(",");
                            }
                            fileIdList.append(addResult.getValue());
                        }
                    }
                    if(null != fileIdList){
                        logger.info("path change fileIdList"+fileIdList);
                        entity.setFileList(fileIdList.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.info("添加反担保物时同步文件相关异常 error!"+e);
        }
    }

    @Override
    public MaterialInfo update(MaterialInfo entity) {
        entity.setAssessedDate(LocalDateTime.now());
        Optional<MaterialInfo> oMaterialInfo = find(entity.getId());
        MaterialInfo oraMaterialInfo = oMaterialInfo.get();
        if(null == entity.getOneCate()){
            entity.setOneCate(oraMaterialInfo.getOneCate());
        }
        if(null == entity.getTwoCate()){
            entity.setTwoCate(oraMaterialInfo.getTwoCate());
        }
        //同步列表统计相关数据
        if(entity.getProps().containsKey(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate) || entity.getProps().containsKey(CounterGuaranteeTypePropEnum.jWarrantyArt)
                 || entity.getProps().containsKey(CounterGuaranteeTypePropEnum.gpWarrantyArt) ){
            String result = "0";
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate);
            }
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.jWarrantyArt))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.jWarrantyArt);
            }
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.gpWarrantyArt))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.gpWarrantyArt);
            }
            entity.setGuaranteeLimit(new BigDecimal(StringUtils.isEmpty(result)?"0":result).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        if(entity.getProps().containsKey(CounterGuaranteeTypePropEnum.fAssessValueOfRate) || entity.getProps().containsKey(CounterGuaranteeTypePropEnum.jAssessValue)
                 || entity.getProps().containsKey(CounterGuaranteeTypePropEnum.jgpAssessValue)  ){
            String result = "0";
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.fAssessValueOfRate))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.fAssessValueOfRate);
            }
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.jAssessValue))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.jAssessValue);
            }
            if(!StringUtils.isBlank(entity.getProps().get(CounterGuaranteeTypePropEnum.jgpAssessValue))){
                result =entity.getProps().get(CounterGuaranteeTypePropEnum.jgpAssessValue);
            }
            entity.setAssessedValue(new BigDecimal(StringUtils.isEmpty(result)?"0":result).setScale(2, BigDecimal.ROUND_HALF_UP));
        }
        entity.setCreateAt(oraMaterialInfo.getCreateAt());
        entity.setCreateBy(oraMaterialInfo.getCreateBy());
        //更新对应文件
        fileOperation(entity);
        return repository.save(entity);
    }

    @Override
    public List<FileInfo> templateReportAdd(int accountId,List<AssessedReportTemplateVo> reportTemplateVos) throws IOException {
        List<FileInfo> result = new ArrayList<>();
        List<UploadFileDto> pathList = addWordByTemplate(accountId,templatePath,reportTemplateVos);
        for (UploadFileDto fileDto:pathList) {
            //增加返回文件全地址 或者文件id给前端
            FileInfo upload = fileClient.upload(fileDto);
            logger.info("file返回 upload!"+ JsonUtils.toJSONString(upload));
            result.add(upload);
        }
        return result;
    }

    /**
     * 根据模板生成word
     * @param accountId
     * @param templatePath
     * @param reportTemplateVos
     * @return
     * @throws IOException
     */
    private List<UploadFileDto> addWordByTemplate(int accountId,String templatePath,List<AssessedReportTemplateVo> reportTemplateVos) throws IOException {
        List<UploadFileDto> pathList = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(reportTemplateVos)){
            for (AssessedReportTemplateVo reportTemplateVo :reportTemplateVos) {
                String fdcTemplatePath = "";
                Map<String, String> insertTextMap = new HashMap<>();
                ArrayList<String[]> addList = new ArrayList<>();
                ArrayList<String[]> addTableList = new ArrayList<>();
                if(reportTemplateVo.getCate().equals(CounterGuaranteeTypeCateEnum.FDCDY)){
                    fdcTemplatePath = templatePath +File.separator +CounterGuaranteeTypeCateEnum.FDCDY + ".docx";
                    //初始化统计列的 tab字段值
                    for (EnumMap<CounterGuaranteeTypePropEnum, String> enumStringEnumMap:reportTemplateVo.getProps()) {
                        String[] fdcStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAddress),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAreaMeter),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcCompleteDate),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcOriginalValue))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcOriginalValue),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOfRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOfRate),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOneOfRate),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcRemarks)};
                        addList.add(fdcStr);
                        String[] fdcTableStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAddress),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAreaMeter),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcCompleteDate),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcOriginalValue))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcOriginalValue),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOneNotRate),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcRemarks)};
                        addTableList.add(fdcTableStr);
                    }
                    //初始化段落标记对应值
                    TemplateToWordUtils.getFdcMap(reportTemplateVo, insertTextMap);
                    TemplateToWordUtils.getSumTable(addList);
                    TemplateToWordUtils.getSumTable(addTableList);
                }else if(reportTemplateVo.getCate().equals(CounterGuaranteeTypeCateEnum.JSYDSYQ)){
                    fdcTemplatePath = templatePath +File.separator +CounterGuaranteeTypeCateEnum.JSYDSYQ + ".docx";
                    //初始化统计列的 tab字段值
                    for (EnumMap<CounterGuaranteeTypePropEnum, String> enumStringEnumMap:reportTemplateVo.getProps()) {
                        String[] tdStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqAddress),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqAreaMeter),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqAreaMeters),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqYear),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqOriginalValue))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqOriginalValue),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jAssessValue))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jAssessValue),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jWarrantyArt))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jWarrantyArt),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jPriceOne), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jsqRemarks)};
                        addList.add(tdStr);
                    }
                    //初始化段落标记对应值
                    TemplateToWordUtils.getTdMap(reportTemplateVo, insertTextMap);
                    if(CollectionUtils.isNotEmpty(addList)){
                        TemplateToWordUtils.getJsqSumTable(addList);
                    }
                }else if(reportTemplateVo.getCate().equals(CounterGuaranteeTypeCateEnum.GUPIAO)){
                    fdcTemplatePath = templatePath +File.separator +CounterGuaranteeTypeCateEnum.GUPIAO + ".docx";
                    //初始化统计列的 tab字段值
                    for (EnumMap<CounterGuaranteeTypePropEnum, String> enumStringEnumMap:reportTemplateVo.getProps()) {
                        BigDecimal sNum = new BigDecimal(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpStockNum));
                        BigDecimal sPrice = new BigDecimal(StringUtils.isEmpty(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpPriceOne))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpPriceOne));
                        BigDecimal multiply = null;
                        if(null != sNum || null != sPrice){
                            multiply = sNum.multiply(sPrice);
                        }
                        String[] stockStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpStockName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpStockCode), "",
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpStockUnitPriceAmt),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpStockNum),
                                null== multiply?"0":multiply.toString(),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jgpAssessValue))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.jgpAssessValue),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpWarrantyArt))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.gpWarrantyArt)};
                        addList.add(stockStr);
                    }
                    //初始化段落标记对应值
                    TemplateToWordUtils.getStockMap(reportTemplateVo, insertTextMap);
                    if(CollectionUtils.isNotEmpty(addList)){
                        TemplateToWordUtils.getStockSumTable(addList);
                    }
                }
                //各种类型对应的模板路径
                FileInputStream fileInputStream = new FileInputStream(fdcTemplatePath);
                //临时文件
                String fileName = TemplateToWordUtils.makeName();
                File outFile= File.createTempFile(fileName, Const.Suffix.DOCX);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                TemplateToWordUtils.generateWord(fileInputStream,fileOutputStream,insertTextMap,addList,addTableList);
                //增加返回文件全地址 或者文件id给前端
                UploadFileDto file = new UploadFileDto();
                String content = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(outFile.getAbsolutePath())));
                file.setProjectId(reportTemplateVo.getProjectId());
                file.setAccountId(accountId);
                file.setName(fileName+".docx");
                file.setContent(content);
                file.setCate(FileCateEnum.Bg_Fdbwbg);
                file.setRemarks(reportTemplateVo.getCate().getCnName());
                pathList.add(file);
            }
        }
        return pathList;
    }

    /**
     * 评审报告相关数据
     * @param accountId
     * @param projectId
     * @throws IOException
     */
    @Override
    public void exportReportTemplate(int accountId, Integer projectId) throws IOException {
        List<MaterialInfo> byProjectId = findByProjectId(projectId);
        if(CollectionUtils.isNotEmpty(byProjectId)){
            ArrayList<String[]> fdcAddTableList = new ArrayList<>();
            for (MaterialInfo reportTemplateVo :byProjectId) {
                Map<String, String> insertTextMap = new HashMap<>();
                //项目下所有房地产抵押模板
                if(reportTemplateVo.getCate().equals(CounterGuaranteeTypeCateEnum.FDCDY)){
                    //初始化统计列的 tab字段值
                    EnumMap<CounterGuaranteeTypePropEnum, String> enumStringEnumMap = reportTemplateVo.getProps();
                    if (null !=enumStringEnumMap) {
                        String[] fdcTableStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAddress),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAreaMeter),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcCompleteDate),
                                ObjectUtils.isEmpty(reportTemplateVo.getOriginalValue())?"0":reportTemplateVo.getOriginalValue().toString(),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate),
                                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate))?"0":enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate),
                                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOneNotRate),enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcRemarks)};
                        fdcAddTableList.add(fdcTableStr);
                    }
                }
            }
            TemplateToWordUtils.getSumTable(fdcAddTableList);
            String  reportTemplatePath = templatePath+File.separator  +"普通类评审报告模板.docx";
            //各种类型对应的模板路径
            FileInputStream fileInputStream = new FileInputStream(reportTemplatePath);
            //临时文件
            String fileName = TemplateToWordUtils.makeName();
            File outFile= File.createTempFile(fileName, Const.Suffix.DOCX);
            FileOutputStream fileOutputStream = new FileOutputStream(outFile);
            TemplateToWordUtils.generateWord(fileInputStream,fileOutputStream,null,null,fdcAddTableList);
            //增加返回文件全地址 或者文件id给前端
            UploadFileDto file = new UploadFileDto();
            String content = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(outFile.getAbsolutePath())));
            file.setProjectId(projectId);
            file.setAccountId(accountId);
            file.setName(fileName+".docx");
            file.setContent(content);
            file.setCate(FileCateEnum.Bg_Ptywpsbg);
            //增加返回文件全地址 或者文件id给前端
            FileInfo upload = fileClient.upload(file);
            logger.info("export report file返回 upload!"+ JsonUtils.toJSONString(upload));
        }

    }

    /**
     * 评审报告相关数据（插入形式的）
     * @param accountId
     * @param projectId
     * @throws IOException
     */
    @Override
    public void createReportTemplate(int accountId, Integer projectId) throws IOException {
        //个人保证和反担保措施的反担保物信息
        List<MaterialInfo> byProjectId = repository.findByProjectIdAndCate(projectId,CounterGuaranteeTypeCateEnum.FDCDY);
        ArrayList<String[]> fdcAddTableList = new ArrayList<>();
        ArrayList<String[]> fdcTableList = new ArrayList<>();
        //个人保证的
        List<IndividualDetailInfo> individualDetailInfos = individualDetailRepository.findByProjectIdAndCate(projectId,CounterGuaranteeTypeCateEnum.FDCDY);
        ArrayList<String[]> individualFdc = new ArrayList<>();
        ArrayList<String[]> individualFdcRate = new ArrayList<>();
        //段落标识 个人保证基本信息
        List<IndividualInfo> individualInfos = individualRepository.findByProjectId(projectId);
        Map<String, String> insertTextMap = new HashMap<>();
        TemplateToWordUtils.getIndividualMap(individualInfos,insertTextMap);
        if(CollectionUtils.isNotEmpty(byProjectId)) {
            for (MaterialInfo reportTemplateVo : byProjectId) {
                getFdcTabData(fdcAddTableList, fdcTableList, reportTemplateVo.getOriginalValue(),reportTemplateVo.getProps());
            }
        }
        if(CollectionUtils.isNotEmpty(individualDetailInfos)) {
            for (IndividualDetailInfo reportTemplateVo : individualDetailInfos) {
                getFdcTabData(individualFdc, individualFdcRate, reportTemplateVo.getOriginalValue(),reportTemplateVo.getProps());
            }
        }
        TemplateToWordUtils.getSumTable(fdcAddTableList);
        TemplateToWordUtils.getSumTable(fdcTableList);
        TemplateToWordUtils.getSumTable(individualFdc);
        TemplateToWordUtils.getSumTable(individualFdcRate);
        String  reportTemplatePath = templatePath+File.separator  +"普通类评审报告模板.docx";
        //评审报告模板路径
        FileInputStream fileInputStream = new FileInputStream(reportTemplatePath);
        //临时文件
        String fileName = TemplateToWordUtils.makeName();
        File outFile= File.createTempFile(fileName, Const.Suffix.DOCX);
        FileOutputStream fileOutputStream = new FileOutputStream(outFile);
        //获取docx解析对象
        XWPFDocument xwpfDocument = new XWPFDocument(fileInputStream);
        //根据插入表格的标识 创建表格 初始化表格数据 插入房地产 税前的
        String fdcKey = "$guaranteeFdc";
        initTabData(fdcAddTableList, xwpfDocument, fdcKey);
        //插入房地产 税后的
        String fdcKeyRate = "$guaranteeFdcRate";
        initTabData(fdcTableList, xwpfDocument, fdcKeyRate);
        //个人保证里面的插入房地产 税前的
        String fdcKeyIndividual = "$guaranteeFdcIndividual";
        initTabData(individualFdc, xwpfDocument, fdcKeyIndividual);
        //个人保证插入房地产 税后的
        String fdcKeyRateIndividual = "$guaranteeFdcRateIndividual";
        initTabData(individualFdcRate, xwpfDocument, fdcKeyRateIndividual);
        //个人保证基本信息 填充
        // 处理所有文段数据，除了表格
        TemplateToWordUtils.handleParagraphs(xwpfDocument, insertTextMap);
        // 写出数据
        xwpfDocument.write(fileOutputStream);
        fileOutputStream.close();
        //增加返回文件全地址 或者文件id给前端
        UploadFileDto file = new UploadFileDto();
        String content = Base64.getEncoder().encodeToString(Files.readAllBytes(Paths.get(outFile.getAbsolutePath())));
        file.setProjectId(projectId);
        file.setAccountId(accountId);
        file.setName(fileName+".docx");
        file.setContent(content);
        file.setCate(FileCateEnum.Bg_Ptywpsbg);
        //增加返回文件全地址 或者文件id给前端
        FileInfo upload = fileClient.upload(file);
        logger.info("createReportTemplate report file返回 upload!"+ JsonUtils.toJSONString(upload));

    }

    private void initTabData(ArrayList<String[]> fdcAddTableList, XWPFDocument xwpfDocument, String fdcKey) {
        XWPFTable tableByFdcKey = TemplateToWordUtils.createTableByKey(fdcKey, 10000, xwpfDocument);
        TemplateToWordUtils.initTableRow(tableByFdcKey,fdcKey);
        TemplateToWordUtils.handleTableData(tableByFdcKey,fdcAddTableList);
    }

    private void getFdcTabData(ArrayList<String[]> individualFdc, ArrayList<String[]> individualFdcRate, BigDecimal originalValue,  EnumMap<CounterGuaranteeTypePropEnum, String> enumStringEnumMap) {
        //项目下所有房地产抵押数据  税前的
        String[] fdcTableStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAddress),
                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAreaMeter), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcCompleteDate),
                ObjectUtils.isEmpty(originalValue) ? "0" : originalValue.toString(),
                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate)) ? "0" : enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueNotRate),
                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate)) ? "0" : enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtNotRate),
                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOneNotRate), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcRemarks)};
        individualFdc.add(fdcTableStr);
        //税后的
        String[] fdcStr = new String[]{enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcEstateNo), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcObligeeName), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAddress),
                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcAreaMeter), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcCompleteDate),
                ObjectUtils.isEmpty(originalValue) ? "0" : originalValue.toString(),
                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOfRate)) ? "0" : enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOfRate),
                com.alibaba.nacos.common.utils.StringUtils.isBlank(enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate)) ? "0" : enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fWarrantyArtOfRate),
                enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fAssessValueOneOfRate), enumStringEnumMap.get(CounterGuaranteeTypePropEnum.fdcRemarks)};
        individualFdcRate.add(fdcStr);
    }

    @Override
    public void getReports(Integer projectId) {
        List<FileInfo> byProject = fileClient.findByProject(projectId, FileCateEnum.Bg_Ptywpsbg);
        //反担保房地产
        List<MaterialInfo> byProjectId = repository.findByProjectIdAndCate(projectId,CounterGuaranteeTypeCateEnum.FDCDY);
        if(CollectionUtils.isNotEmpty(byProject) && CollectionUtils.isNotEmpty(byProjectId)){
            List<String>  fdcEstateNos = byProjectId.stream().map(v->v.getProps().get(CounterGuaranteeTypePropEnum.fdcEstateNo)).collect(Collectors.toList());
            logger.info("getReports report  fdcEstateNos!"+ fdcEstateNos);
            //根据项目id获取最新评审报告
            FileInfo fileInfo =byProject.get(byProject.size()-1);
            MultipartFile file = null;
            try {
                file = FileUtils.urlToMultipartFile(fileInfo.getPath(), fileInfo.getName());
                //解析报告里面的房地产
                ResultVo<GuaranteeDocVo>  resultStr= guaranteeDocClient.guaranteeDoc(file);
                logger.info("getReports report file返回 resultStr!"+ resultStr);
                if(null !=resultStr.getData() && resultStr.isSuccess()){
                    List<Map<String, String>> mortgages = resultStr.getData().getCounterGuarantee().getMortgage();
                    if(CollectionUtils.isNotEmpty(mortgages)){
                        for (Map<String, String> map:mortgages) {
                            String fdcEstateNo = map.get("房地产证号");
                            logger.info("getReports  fdcEstateNo!"+ fdcEstateNo);
                            //是否房产数据一直
                            if(fdcEstateNos.contains(fdcEstateNo)){
                                boolean result = true;
                            }
                        }
                    }
                }

            } catch (Exception e) {
                logger.info("getReports error!",e);
            }
        }
    }
}
