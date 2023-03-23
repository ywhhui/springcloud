### API

Input

```

POST /report {file: FILE}

```

字段映射关系

所有表格数据字段默认为string

```
out_table_columns = {
    "房产抵押": {
        "权利人": "rightHolder",
        "房地产证号": 'certificateId',
        "所在市/区": 'location',
        '行政区域': 'location',
        '名称': 'name',
        '面积（平方米）': 'area',
        '面积（㎡）': 'area',
        '建成日期': 'dateBuilt',
        '房产': 'realEstate',
        '房地产': 'realEstate',
        '市场价\n（万元）': 'marketPrice',
        '我司评估价': 'evalutedPrice',
        '购置日期': 'dateOfPurchase',
        '是否红本在手': 'fullOwnership',
        '备注': 'remark'
    },
    "客户情况": {
        '统计年份': 'year',
        '统计月份': 'month',
        '客户名称': 'clientName',
        '销售额（万元）': 'salesAmount',
        '销售额占比（%）': 'salePercentage',
        '结算周期': 'billingCycle',
        '类型': 'category',
        '产品': 'product',
        '终端客户': 'endClient',
    },
    "产品结构表": {
        '统计年份': 'year',
        '统计月份': 'month',
        "主要产品名称": 'primaryProduct',
        '销售收入（万元）': 'salesIncome',
        '占总销售额比例': 'salesPercentage',
        '占总销售额比例（%）': 'salesPercentage',
        '内销占比（%）': 'domesticSalesPercentage',
        '外销占比（%）': 'exportSalesPercentage'
    
    },
    "供应商信息": {
        '统计年份': 'year',
        '统计月份': 'month',
        "供应商名称": 'supplierName',
        '供应产品': 'product',
        '采购额（万元）': 'purchaseAmount',
        '结算周期': 'billingCycle'
    },
    "销售收入": {
        '统计年份': 'year',
        '统计月份': 'month',
        '一月金额(万元)': 'jan',
        '二月金额(万元)': 'feb',
        '三月金额(万元)': 'mar',
        '四月金额(万元)': 'apr',
        '五月金额(万元)': 'may',
        '六月金额(万元)': 'jun',
        '七月金额(万元)': 'jul',
        '八月金额(万元)': 'aug',
        '九月金额(万元)': 'sep',
        '十月金额(万元)': 'oct',
        '十一月金额(万元)': 'nov',
        '十二月金额(万元)': 'dec',
        '合计': 'total',
        '月均': 'monthlyAverage',
    },
    "回款核实": {
        '统计年份': 'year',
        '统计月份': 'month',
        '户名': 'account',
        '结算行': 'bank',
        '一月金额(万元)': 'jan',
        '二月金额(万元)': 'feb',
        '三月金额(万元)': 'mar',
        '四月金额(万元)': 'apr',
        '五月金额(万元)': 'may',
        '六月金额(万元)': 'jun',
        '七月金额(万元)': 'jul',
        '八月金额(万元)': 'aug',
        '九月金额(万元)': 'sep',
        '十月金额(万元)': 'oct',
        '十一月金额(万元)': 'nov',
        '十二月金额(万元)': 'dec',
        '合计': 'total',
        '月均': 'monthlyAverage'
    }
}

```

Output

房产抵押 counterGuarantee.mortgage

['rightHolder', 'certificateId', 'location', 'location', 'name', 'area', 'area', 'dateBuilt', 'realEstate', 'realEstate', 'marketPrice', 'evalutedPrice', 'dateOfPurchase', 'fullOwnership', 'remark']

客户情况 reportData.sales

['year', 'month', 'clientName', 'salesAmount', 'salePercentage', 'billingCycle', 'category', 'product', 'endClient']

产品结构表 reportData.products

['year', 'month', 'primaryProduct', 'salesIncome', 'salesPercentage', 'salesPercentage', 'domesticSalesPercentage', 'exportSalesPercentage']

供应商信息 reportData.suppliers

['year', 'month', 'primaryProduct', 'product', 'purchaseAmount', 'billingCycle']

销售收入 reportData.salesIncome

['year', 'month', 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec', 'total', 'monthlyAverage']

回款核实 reportData.receivedPayments

['year', 'month', 'account', 'bank', 'jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec', 'total', 'monthlyAverage']

备注：

1. 月份均视为1月到第n月的累计值
2. schema为表格数据的元信息，但表格数据输出时默认为String类型

```json
{
  "data": {
    "counterGuarantee": {
      "mortgage": [
        {
          "权利人": "陈艺芳",
          "房地产证号": "粤（2018）深圳市不动产权第0018395号",
          "行政区域": "南山区",
          "名称": "沙河西路西博海名苑2栋16B",
          "面积（平方米）": "140",
          "购置日期": "2018",
          "市场价（万元）": "指导价1848万",
          "是否红本在手": "按揭",
          "备注": "余额559"
        }
      ]
    },
    "reportData": {
      "sales": [
        {
          "统计年份": "2020",
          "客户名称": "深圳市亚辉龙生物科技股份有限公司",
          "销售额（万元）": "609 ",
          "销售额占比（%）": 23.0,
          "结算周期": "月结30天",
          "备注": "688575.SH",
          "统计月份": 12
        }
      ],
      "products": [
        {
          "统计年份": "2019",
          "主要产品名称": "泵",
          "销售收入（万元）": "893 ",
          "占总销售额比例（%）": 57.0,
          "内销占比（%）": 100.0,
          "外销占比（%）": "",
          "统计月份": 12
        }
      ],
      "suppliers": [
        {
          "统计年份": "2020",
          "供应商名称": "深圳市艾森尔科技有限公司",
          "供应产品": "气泵",
          "采购额（万元）": "693 ",
          "结算周期": "月结30",
          "统计月份": 12
        }
      ],
      "salesIncome": [
        {
          "统计年份": "2020",
          "一月金额(万元)": "186",
          "二月金额(万元)": "103",
          "三月金额(万元)": "410",
          "四月金额(万元)": "564",
          "五月金额(万元)": "615",
          "六月金额(万元)": "711",
          "七月金额(万元)": "578",
          "八月金额(万元)": "358",
          "九月金额(万元)": "292",
          "十月金额(万元)": "184",
          "十一月金额(万元)": "317",
          "十二月金额(万元)": "384",
          "合计": "4707",
          "月均": "392",
          "统计月份": 12
        }
      ],
      "receivedPayments": [
        {
          "户名": "恒永达",
          "结算行": "招行",
          "一月金额(万元)": "159 ",
          "二月金额(万元)": "121 ",
          "三月金额(万元)": "328 ",
          "四月金额(万元)": "589 ",
          "五月金额(万元)": "822 ",
          "六月金额(万元)": "709 ",
          "七月金额(万元)": "598 ",
          "八月金额(万元)": "424 ",
          "九月金额(万元)": "362 ",
          "十月金额(万元)": "258 ",
          "十一月金额(万元)": "279 ",
          "十二月金额(万元)": "309 ",
          "合计": "4959 ",
          "月均": "413 ",
          "统计年份": "2020",
          "统计月份": 12
        }
      ]
    }
  },
  "message": "ok",
  "success": true
}
```
