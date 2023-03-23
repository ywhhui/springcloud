package com.szcgc.finance.vo;

import com.szcgc.comm.util.StringUtils;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @Author: chenxinli
 * @Date: 2020/12/14 18:21
 * @Description:
 */
public class PostAnalysisVo {

    @Schema(description = "科目")
    public String cate;
    @Schema(description = "去年同期")
    public String cnt_last;
    @Schema(description = "本次检查")
    public String cnt_current;
    @Schema(description = "增减")
    public String ratio;


    public PostAnalysisVo(String cate, String cnt_last, String cnt_current){
        this.cate = cate;
        this.cnt_last = cnt_last;
        this.cnt_current = cnt_current;

        if(StringUtils.isEmpty(cnt_last) ||"0".equals(cnt_last)){
            ratio = "/";
        }else{
            BigDecimal current = new BigDecimal(cnt_current);
            BigDecimal last = new BigDecimal(cnt_last);
            BigDecimal difference =current.subtract(last);
            BigDecimal temp1 = BigDecimal.ZERO;
            if(BigDecimal.ZERO.compareTo(difference)!=0 || BigDecimal.ZERO.compareTo(last)!=0){
                 temp1 = difference.divide(last, 2, RoundingMode.HALF_UP);
            }
            BigDecimal temp2 = temp1.multiply(new BigDecimal(100));
            BigDecimal temp3 =temp2.setScale(0,RoundingMode.HALF_UP);
            ratio = temp3.toString()+"%";
        }

    }
}
