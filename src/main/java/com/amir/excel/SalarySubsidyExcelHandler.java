package com.amir.excel;

import com.alibaba.druid.util.StringUtils;
import com.amir.constant.SubsidyTypeEnum;
import com.amir.exception.BusinessException;
import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpSubsibyMonthly;
import com.amir.model.emp.EmpWork;
import com.amir.model.sys.Sysdept;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.EmpSubsibyMonthlyService;
import com.amir.service.emp.EmpWorkService;
import com.amir.service.sys.SysDeptService;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/9/15.
 */
@Service
public class SalarySubsidyExcelHandler extends BaseExcelHandler {

    public final static List<String> fields = Stream.of("月份", "票据单号", "补贴姓名",
            "补贴原因", "金额", "备注").collect(Collectors.toList());

    @Resource
    private EmpSubsibyMonthlyService empSubsibyMonthlyService;

    @Resource
    private EmpService empService;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private EmpWorkService empWorkService;

    @Override
    public List<String> execute(MultipartFile file, Boolean isCover, String operator) throws Exception {
        return checkLayout(file, fields, operator);
    }

    @Override
    protected void insert(List list, String operator) {
        list.stream().filter(t -> t != null).forEach(t -> {
            EmpSubsibyMonthly empSubsibyMonthly = (EmpSubsibyMonthly) t;
            empSubsibyMonthly.setDrawTime(new Date());
            empSubsibyMonthly.setDrawer(operator);
            empSubsibyMonthlyService.save(empSubsibyMonthly);
        });
    }

    @Override
    protected List create(XSSFRow row) throws Exception {
        List<EmpSubsibyMonthly> empSubsibyMonthlies = new ArrayList<>();
        EmpSubsibyMonthly empSubsibyMonthly = new EmpSubsibyMonthly();
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
                    String yearMonth = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(yearMonth)) {
                        throw new BusinessException("月份不能未空");
                    }
                    if (isRightDateStr(yearMonth, "yyyy-MM")) {
                        empSubsibyMonthly.setYearMonth(yearMonth);
                    } else {
                        throw new BusinessException("月份格式有误,应为YYYY-MM");
                    }
                    break;
                case 1:
                    String billNo = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(billNo)) {
                        throw new BusinessException("票据单号为空");
                    }
                    if (null != empSubsibyMonthlyService.getByNoAndType(billNo, SubsidyTypeEnum.SALARY.getValue())) {
                        throw new BusinessException("票据单号已经存在");
                    }
                    empSubsibyMonthly.setBillNo(billNo);
                    empSubsibyMonthly.setType(SubsidyTypeEnum.SALARY.getValue());
                    break;
                case 2:
                    String empName = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(empName)) {
                        throw new BusinessException("补贴名称为空");
                    }
                    Emp emp = empService.getByName(empName);
                    if (emp == null) throw new BusinessException("名称为：" + getCellValue(row.getCell(i)) + "的员工不存在");
                    Sysdept sysdept = sysDeptService.get(emp.getDeptId());
                    empSubsibyMonthly.setDeptName(sysdept != null ? sysdept.getDeptName() : null);
                    EmpWork empWork = empWorkService.getByID(emp.getWorkId());
                    empSubsibyMonthly.setWorkName(empWork != null ? empWork.getName() : null);
                    empSubsibyMonthly.setEmpId(emp.getId());
                    empSubsibyMonthly.setDeptId(sysdept.getId());
                    empSubsibyMonthly.setEmpName(empName);
                    break;
                case 3:
                    String reason = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(reason)) {
                        throw new BusinessException("补贴原因为空");
                    }
                    empSubsibyMonthly.setReason(reason);
                    break;
                case 4:
                    String price = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(price)) {
                        throw new BusinessException("补贴金额为空");
                    }
                    if (!NumberUtils.isNumber(price)) throw new BusinessException("补贴金额错误");
                    empSubsibyMonthly.setMoney(new BigDecimal(price).setScale(2, BigDecimal.ROUND_HALF_EVEN));
                    break;
                case 5:
                    empSubsibyMonthly.setRemark(getCellValue(row.getCell(i), i));
                    empSubsibyMonthly.setCreateTime(new Date());
                    empSubsibyMonthly.setLastModifyTime(new Date());
                    empSubsibyMonthly.setIsDelete(0);
                    empSubsibyMonthly.setIsConfirm(0);
                    break;

            }
        }
        empSubsibyMonthlies.add(empSubsibyMonthly);
        return empSubsibyMonthlies;
    }


    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 6)) {
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
