package com.amir.excel;

import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpTimesalaryMonthly;
import com.amir.model.emp.EmpWork;
import com.amir.model.sys.Sysdept;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.EmpTimeSalaryService;
import com.amir.service.emp.EmpWorkService;
import com.amir.service.sys.SysDeptService;
import com.btjf.business.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class EmpTimeSalaryExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("月份", "姓名", "票据编号", "工作内容", "单价",
            "数量", "单位", "金额", "备注").collect(Collectors.toList());

    @Resource
    private EmpTimeSalaryService empTimeSalaryService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private EmpWorkService empWorkService;
    @Resource
    private EmpService empService;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                EmpTimesalaryMonthly empTimesalaryMonthly = (EmpTimesalaryMonthly) list.get(i);
                EmpTimesalaryMonthly emp1 = empTimeSalaryService.findByBillNo(empTimesalaryMonthly.getBillNo());

                if (emp1 != null) {
                    continue;
                } else {
                    Emp emp = empService.getByName(empTimesalaryMonthly.getEmpName());
                    if (emp != null) {
                        Sysdept sysdept = sysDeptService.get(emp.getDeptId());
                        EmpWork empWork = empWorkService.getByID(emp.getWorkId());
                        empTimesalaryMonthly.setEmpId(emp.getId());
                        empTimesalaryMonthly.setDeptId(emp.getDeptId());
                        empTimesalaryMonthly.setDeptName(sysdept == null ? "" : sysdept.getDeptName());
                        empTimesalaryMonthly.setWorkName(empWork == null ? "" : empWork.getName());
                    }
                    empTimesalaryMonthly.setDrawer(operator);
                    empTimesalaryMonthly.setDrawTime(new Date());
                    empTimesalaryMonthly.setCreateTime(new Date());
                    empTimesalaryMonthly.setLastModifyTime(new Date());
                    empTimesalaryMonthly.setIsDelete(0);
                    empTimesalaryMonthly.setIsConfirm(0);
                    empTimeSalaryService.create(empTimesalaryMonthly);
                }

            }
        }
    }

    @Override
    protected List create(XSSFRow row) throws Exception {
        List<EmpTimesalaryMonthly> empTimesalaryMonthlies = new ArrayList<>();
        EmpTimesalaryMonthly empTimesalaryMonthly = new EmpTimesalaryMonthly();
        String errMsg = "";
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    //YYYY-MM
                    if (isRightDateStr(getCellValue(row.getCell(i), i), "yyyy-MM")) {
                        empTimesalaryMonthly.setYearMonth(getCellValue(row.getCell(i), i));
                    } else {
                        errMsg = errMsg + "第" + 1 + "列" + fields.get(0) + " 填写错误,";
                    }

                    break;
                case 1:
                    empTimesalaryMonthly.setEmpName(getCellValue(row.getCell(i), i));
                    break;
                case 2:
                    empTimesalaryMonthly.setBillNo(getCellValue(row.getCell(i), i));
                    break;
                case 3:
                    empTimesalaryMonthly.setContent(getCellValue(row.getCell(i), i));
                    break;
                case 4:
                    empTimesalaryMonthly.setPrice(BigDecimal.valueOf(Double.parseDouble(getCellValue(row.getCell(i), i))));
                    break;
                case 5:
                    empTimesalaryMonthly.setNum(BigDecimal.valueOf(Double.parseDouble(getCellValue(row.getCell(i), i))));
                    break;
                case 6:
                    empTimesalaryMonthly.setUnit(getCellValue(row.getCell(i), i));
                    break;
                case 7:
                    empTimesalaryMonthly.setMoney(BigDecimal.valueOf(Double.parseDouble(getCellValue(row.getCell(i), i))));
                    break;
                case 8:
                    empTimesalaryMonthly.setRemark(getCellValue(row.getCell(i), i));
                    break;
                default:
                    break;
            }
        }
        EmpTimesalaryMonthly emp1 = empTimeSalaryService.findByBillNo(empTimesalaryMonthly.getBillNo());
        if (emp1 != null) {
            errMsg = errMsg + "第" + 3 + "列" + fields.get(2) + "重复，无法新增";
        }
        if (StringUtils.isNotEmpty(errMsg)) {
            throw new BusinessException(errMsg);
        }
        empTimesalaryMonthlies.add(empTimesalaryMonthly);
        return empTimesalaryMonthlies;
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 8)) {
            //备注列 允许为空
            return null;
        }
        try {
            value = getCellValue(cell);
        } catch (Exception e) {
            throw new BusinessException("第" + (i + 1) + "列" + fields.get(i) + " 填写错误");
        }
        if (value.equals("非法字符") || value.equals("未知类型")) {
            throw new BusinessException("第" + (i + 1) + "列" + fields.get(i) + " 填写错误");
        }
        return value;
    }
}
