package com.szcgc.cougua.service.impl;

import com.alibaba.nacos.common.utils.CollectionUtils;
import com.szcgc.comm.IbcResult;
import com.szcgc.comm.service.BaseService;
import com.szcgc.comm.util.StringUtils;
import com.szcgc.cougua.constant.CounterGuaranteeTypeCateEnum;
import com.szcgc.cougua.model.IndividualDetailInfo;
import com.szcgc.cougua.repository.IndividualDetailRepository;
import com.szcgc.cougua.service.IIndividualDetailService;
import com.szcgc.file.constant.FileCateEnum;
import com.szcgc.file.feign.IFileClient;
import com.szcgc.file.model.FileInfo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2022/9/17 16:41
 */
@Service
public class IndividualDetailService extends BaseService<IndividualDetailRepository, IndividualDetailInfo, Integer> implements IIndividualDetailService {

    private static final Log logger = LogFactory.getLog(IndividualDetailService.class);

    @Autowired
    IFileClient fileClient;

    @Override
    public List<IndividualDetailInfo> findByProjectIdAndIndividualId(Integer projectId,Integer individualId ) {
        List<IndividualDetailInfo> result = repository.findByProjectIdAndIndividualId(projectId,individualId);
        if(CollectionUtils.isNotEmpty(result)){
            result = result.stream().sorted(Comparator.comparing(IndividualDetailInfo::getId).reversed()).collect(Collectors.toList());
            for (IndividualDetailInfo detailInfo:result) {
                fileIdToPath(detailInfo);
            }
        }
        return result;
    }

    @Override
    public IndividualDetailInfo insert(IndividualDetailInfo entity) {
        //反担保文件相关
        fileOperation(entity);
        IndividualDetailInfo info = repository.save(Objects.requireNonNull(entity));
        return info;
    }

    /**
     * upload 反担保相关文件新增
     * @param entity
     */
    private void fileOperation(IndividualDetailInfo entity) {
        StringBuilder fileIdList = new StringBuilder();
        String filePathList = entity.getFileList();
        logger.info("IndividualDetailInfo fileOperation filePathList:"+filePathList);
        try {
            //材料类型
            FileCateEnum cate = null;
            if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.FDCDY)){
                cate = FileCateEnum.Cl_Fczgr;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.JSYDSYQ)){
                cate = FileCateEnum.Cl_Tudigr;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.ZLQUAN)){
                cate = FileCateEnum.Cl_Zlwjgr;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.ZZQUAN)){
                cate = FileCateEnum.Cl_Zzqlgr;
            }else if(entity.getCate().equals(CounterGuaranteeTypeCateEnum.SB)){
                cate = FileCateEnum.Cl_Sbzcgr;
            }
            //先删再添加 根据fileId删除
            if(null != cate){
                if(0 != entity.getId()){
                    Optional<IndividualDetailInfo> info = repository.findById(entity.getId());
                    if(!StringUtils.isEmpty(info.get().getFileList())){
                        for (String fileId:info.get().getFileList().split(",")) {
                            fileClient.del(Integer.valueOf(fileId));
                        }
                    }
                }
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
                        logger.info("IndividualDetailInfo path change fileIdList"+fileIdList);
                        entity.setFileList(fileIdList.toString());
                    }
                }
            }
        } catch (Exception e) {
            logger.info("个人添加反担保物时同步文件相关异常 error!"+e);
        }
    }

    @Override
    public IndividualDetailInfo update(IndividualDetailInfo entity) {
        Optional<IndividualDetailInfo> oMaterialInfo = find(entity.getId());
        IndividualDetailInfo oraMaterialInfo = oMaterialInfo.get();
        if(null == entity.getOneCate()){
            entity.setOneCate(oraMaterialInfo.getOneCate());
        }
        if(null == entity.getTwoCate()){
            entity.setTwoCate(oraMaterialInfo.getTwoCate());
        }
        entity.setCreateAt(oraMaterialInfo.getCreateAt());
        entity.setCreateBy(oraMaterialInfo.getCreateBy());
        //更新对应文件
        fileOperation(entity);
        return repository.save(entity);
    }

    @Override
    public Optional<IndividualDetailInfo> find(int id) {
        Optional<IndividualDetailInfo> info = repository.findById(id);
        fileIdToPath(info.get());
        return info;
    }

    private void fileIdToPath(IndividualDetailInfo detailInfo) {
        StringBuilder filePath = new StringBuilder();
        try {
            if(!StringUtils.isBlank(detailInfo.getFileList())){
                Map<Integer, String> pathByIds = fileClient.findPathByIds(detailInfo.getFileList());
                pathByIds.values().stream().forEach(str->{
                    filePath.append(str).append(",");
                });
                filePath.deleteCharAt(filePath.length()-1);
                detailInfo.setFileList(filePath.toString());
            }
        } catch (Exception e) {
            logger.info("individualDetailInfo fileIdToPath error!"+e);
        }
    }
}
