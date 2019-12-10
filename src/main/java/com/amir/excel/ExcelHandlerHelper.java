package com.amir.excel;

import com.amir.exception.BusinessException;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by yj on 2019/7/31.
 */
@Component
public class ExcelHandlerHelper {

    @Resource
    private PmInExcelHandler pmInExcelHandlerl;

    @Resource
    private PmExcelHandler pmExcelHandler;

    @Resource
    private ProductPmExcelHandler productPmExcelHandler;

    @Resource
    private PmPlanOutExcelHandler pmPlanOutExcelHandler;

    @Resource
    private ProductWorkshopExcelHandler productWorkshopExcelHandler;

    @Resource
    private EmpExcelHandler empExcelHandler;

    @Resource
    private OrderExcelHandler orderExcelHandler;

    @Resource
    private EmpSalaryMothlyHandler empSalaryMothlyHandler;
    @Resource
    private EmpTimeSalaryExcelHandler empTimeSalaryExcelHandler;

    @Resource
    private ThreeScoreExcelHelper threeScoreExcelHelper;

    @Resource
    private CheckWorkScoreExcelHelper checkWorkScoreExcelHelper;

    @Resource
    private OtherSubsidyExcelHandler otherSubsidyExcelHandler;

    @Resource
    private SalarySubsidyExcelHandler salarySubsidyExcelHandler;

    public BaseExcelHandler getHandler(Integer type) throws BusinessException {
        switch (type) {
            case 1:
                //材料导入
                return pmExcelHandler;
            case 2:
                //入库导入
                return pmInExcelHandlerl;
            case 3:
                return productPmExcelHandler;
            case 4:
                return pmPlanOutExcelHandler;
            case 5:
                //工序导入
                return productWorkshopExcelHandler;
            case 6:
                return empExcelHandler;
            case 7:
                return orderExcelHandler;
            case 8:
                return empSalaryMothlyHandler;
            case 9:
                return threeScoreExcelHelper;
            case 10:
                return checkWorkScoreExcelHelper;
            case 11:
                return empTimeSalaryExcelHandler;
            case 12:
                return otherSubsidyExcelHandler;
            case 13:
                return salarySubsidyExcelHandler;
            default:
                throw new BusinessException("文件类型不存在");
        }

    }
}
