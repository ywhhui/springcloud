package com.szcgc.project.vo.system;

import com.szcgc.project.constant.BusinessTypeCateEnum;
import com.szcgc.project.constant.BusinessTypeCateTopEnum;
import com.szcgc.project.constant.BusinessTypeEnum;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author liaohong
 * @create 2020/9/24 14:58
 */
public class BusinessTypeVo {

    public static final BusinessTypeVo INSTANCE = new BusinessTypeVo();

    public Map<String, BTDetailVo> map;
    public List<BTTreeVo> list;
    public Map<String, String> cateNames;

    public static class BTDetailVo {
        public String value;
        public String label;
        public int topCate;
        public boolean isCreditItem;
        public String[] rFiles;
        public String[] oFiles;

        public static BTDetailVo cvt(BusinessTypeEnum type) {
            BTDetailVo vo = new BTDetailVo();
            vo.label = type.getCate().getTop().getCnName() + "-" + type.getCate().getCnName() + "-" + type.getCnName();
            vo.value = type.name();
            vo.topCate = type.getCate().getTop().ordinal();
            vo.isCreditItem = type.isCreditItem();
            vo.rFiles = type.getRequireFiles();
            vo.oFiles = type.getOptionalFiles();
            return vo;
        }
    }

    public static class BTTreeVo {
        public String value;
        public String label;
        public List<BTTreeItemVo> children;
    }

    public static class BTTreeItemVo {
        public List<BTTreeItemDetailVo> children;
        public String value;
        public String label;

        public static BTTreeItemVo cvt(BusinessTypeCateEnum type) {
            BTTreeItemVo vo = new BTTreeItemVo();
            vo.label = type.getCnName();
            vo.value = type.name();
            return vo;
        }
    }

    public static class BTTreeItemDetailVo {

        public String label;
        public String value;

        public static BTTreeItemDetailVo cvt(BusinessTypeEnum type) {
            BTTreeItemDetailVo vo = new BTTreeItemDetailVo();
            vo.label = type.getCnName();
            vo.value = type.name();
            return vo;
        }

    }

    private BusinessTypeVo() {
        map = new HashMap<>(BusinessTypeEnum.values().length);
        Arrays.stream(BusinessTypeEnum.values()).forEach(type -> map.put(type.name(), BTDetailVo.cvt(type)));

        list = new ArrayList<>(BusinessTypeCateTopEnum.values().length);
        cateNames = new HashMap<>(BusinessTypeEnum.values().length);
        Arrays.stream(BusinessTypeCateTopEnum.values()).forEach(cate -> {
            List<BTTreeItemVo> listItem = new ArrayList<>();
            Arrays.stream(BusinessTypeCateEnum.values()).forEach(cateItem -> {
                if (cateItem.getTop() == cate) {
                    BTTreeItemVo voItem = new BTTreeItemVo();
                    voItem.label = cateItem.getCnName();
                    voItem.value = cateItem.name();
                    if(cateItem.getChildren().size()>0){
                        voItem.children = cateItem.getChildren().stream().filter(item -> !item.isDeprecated()).map(BTTreeItemDetailVo::cvt).collect(Collectors.toList());
                    }else{
                        voItem.children=null;
                    }
                    listItem.add(voItem);
                }
            });

            BTTreeVo vo = new BTTreeVo();
            vo.label = cate.getCnName();
            vo.value = cate.name();
            vo.children = listItem;
            list.add(vo);

            cateNames.put(cate.name(), cate.getCnName());
        });
/*        Arrays.stream(BusinessTypeCateTopEnum.values()).filter(item -> !item.isDeprecated()).forEach(cate -> {
            BTTreeVo vo = new BTTreeVo();
            vo.label = cate.getCnName();
            vo.value = cate.name();
            vo.children = cate.getChildren().stream().filter(item -> !item.isDeprecated()).map(BusinessTypeVo.BTTreeItemVo::cvt).collect(Collectors.toList());
            list.add(vo);
            cateNames.put(cate.name(), cate.getCnName());
        });*/
    }

}
