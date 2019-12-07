package com.btjf.excel.easyexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.enums.CellDataTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Auther: liuyuqing
 * @Date: 2019-12-05 17:48
 * @Description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProcedureWorkShop {

    @ExcelProperty(index = 0)
    private String productNo;
    @ExcelProperty(index = 1)
    private String procedureName;
    @ExcelProperty(index = 2)
    private String price;
    @ExcelProperty(index = 3)
    private Integer sort;

}
