package com.amir.excel;

import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpWork;
import com.amir.model.sys.Sysdept;
import com.amir.service.emp.EmpService;
import com.amir.service.emp.EmpWorkService;
import com.amir.service.sys.SysDeptService;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.utils.MD5Utils;
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

/**
 * Created by yj on 2019/7/31.
 */
@Component
//@Transactional(readOnly = false, rollbackFor = Exception.class)
public class EmpExcelHandler extends BaseExcelHandler {

    //姓名	性别	出生日期	学历	部门	人员类别	工种	固定工资	身份证号	家庭地址	进场日期	手机	开水费	夜餐费	备注

    public final static List<String> fields = Stream.of("姓名", "性别", "出生日期", "籍贯",
            "学历", "部门", "人员类别", "工种", "固定工资", "养老保险", "医疗保险", "失业保险",
            "住房公积金", "社保补贴", "电话费补贴", "身份证号", "家庭地址",
            "进厂日期", "手机", "开水费", "夜餐费", "备注").collect(Collectors.toList());

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
    protected void insert(List emps, String operator) {
        if (emps != null && emps.size() > 0) {
            for (int i = 0; i < emps.size(); i++) {
                Emp emp = (Emp) emps.get(i);
                Emp emp1 = empService.getByName(emp.getName());

                if (emp1 != null) {
                    emp.setId(emp1.getId());
                    empService.update(emp);
                } else {
                    empService.create(emp);
                }
            }
        }
    }

    @Override
    protected List create(XSSFRow row) throws BusinessException {
        List<Emp> emps = new ArrayList<>();
        Emp emp = new Emp();
        String errMsg = "";
        for (int i = 0; i < fields.size(); i++) {
            switch (i) {
                case 0:
//                    Emp emp1 = empService.getByName(getCellValue(row.getCell(i), i));
//                    if(emp1 != null){
//                        errMsg = errMsg + "第" + 1 +"列" + fields.get(0) + " 填写错误,";
//                    }
                    emp.setName(getCellValue(row.getCell(i), i));
                    break;
                case 1:
                    String sex = getCellValue(row.getCell(i), i);
                    if (!StringUtils.isEmpty(sex)) {
                        if ("男".equals(sex)) {
                            emp.setSex(1);
                        } else if ("女".equals(sex)) {
                            emp.setSex(2);
                        }
                    }

                    break;
                case 2:
                    //YYYYMMDD
                    if (isRightDateStr(getCellValue(row.getCell(i), i), "yyyyMMdd")) {
                        emp.setBirthday(getCellValue(row.getCell(i), i));
                    } else {
                        errMsg = errMsg + "第" + 3 + "列" + fields.get(2) + " 填写错误,";
                    }

                    break;
                case 3:
                    emp.setNativeSource(getCellValue(row.getCell(i), i));
                    break;
                case 4:
                    emp.setEducation(getCellValue(row.getCell(i), i));
                    break;
                case 5:
                    Sysdept dept = sysDeptService.getByName(getCellValue(row.getCell(i), i));
                    if (dept == null) {
                        errMsg = errMsg + "第" + 6 + "列" + fields.get(5) + " 填写错误,";
                    } else {
                        emp.setDeptId(dept.getId());
                    }
                    break;
                case 6:
                    String type = getCellValue(row.getCell(i), i);
                    if ("计件工".equals(type)) {
                        emp.setType(1);
                    } else if ("合同工".equals(type) || "固定工".equals(type)) {
                        emp.setType(2);
                    } else {
                        errMsg = errMsg + "第" + 7 + "列" + fields.get(6) + " 填写错误,";
                    }
                    break;
                case 7:
                    EmpWork empWork = empWorkService.getByName(getCellValue(row.getCell(i), i));
                    if (empWork == null) {
                        errMsg = errMsg + "第" + 8 + "列" + fields.get(7) + " 填写错误,";
                    } else {
                        emp.setWorkId(empWork.getId());
                    }
                    break;
                case 8:
                    String salary = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(salary)) {
                        emp.setSalary(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setSalary(BigDecimal.valueOf(Double.parseDouble(salary)));
                    }
                    break;
                case 9:
                    String ylbx = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(ylbx)) {
                        emp.setYlbx(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setYlbx(BigDecimal.valueOf(Double.parseDouble(ylbx)));
                    }
                    break;
                case 10:
                    String yiliaobx = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(yiliaobx)) {
                        emp.setYiliaobx(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setYiliaobx(BigDecimal.valueOf(Double.parseDouble(yiliaobx)));
                    }
                    break;
                case 11:
                    String sybx = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(sybx)) {
                        emp.setSybx(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setSybx(BigDecimal.valueOf(Double.parseDouble(sybx)));
                    }
                    break;
                case 12:
                    String gjj = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(gjj)) {
                        emp.setGjj(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setGjj(BigDecimal.valueOf(Double.parseDouble(gjj)));
                    }
                    break;
                case 13:
                    String sbbt = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(sbbt)) {
                        emp.setSocialSubsidy(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setSocialSubsidy(BigDecimal.valueOf(Double.parseDouble(sbbt)));
                    }
                    break;
                case 14:
                    String phonebt = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(phonebt)) {
                        emp.setPhoneSubsidy(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setPhoneSubsidy(BigDecimal.valueOf(Double.parseDouble(phonebt)));
                    }
                    break;
                case 15:
//                    String idCard = getCellValue(row.getCell(i), i);
//                    if(StringUtils.isNotEmpty(idCard)) {
//                        Emp emp2 = empService.getByIdCard(idCard);
//                        if (emp2 != null) {
//                            errMsg = errMsg + "第" + 16 + "列" + fields.get(15) + " 填写错误,";
//                        }
//                    }
                    emp.setIdCard(getCellValue(row.getCell(i), i));
                    break;
                case 16:
                    emp.setAddress(getCellValue(row.getCell(i), i));
                    break;
                case 17:
                    if (isRightDateStr(getCellValue(row.getCell(i), i), "yyyyMMdd")) {
                        emp.setEntryDate(getCellValue(row.getCell(i), i));
                    } else {
                        errMsg = errMsg + "第" + 18 + "列" + fields.get(17) + " 填写错误,";
                    }
                    break;
                case 18:
                    emp.setPhone(getCellValue(row.getCell(i), i));
                    break;
                case 19:
                    String water = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(water)) {
                        emp.setWaterSubsidy(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setWaterSubsidy(BigDecimal.valueOf(Double.parseDouble(water)));
                    }
                    break;
                case 20:
                    String food = getCellValue(row.getCell(i), i);
                    if (StringUtils.isEmpty(food)) {
                        emp.setFoodSubsidy(BigDecimal.valueOf(0.0));
                    } else {
                        emp.setFoodSubsidy(BigDecimal.valueOf(Double.parseDouble(food)));
                    }
                    break;
                case 21:
                    emp.setRemark(getCellValue(row.getCell(i), i));
                    break;

                default:
                    break;
            }
        }
        if (StringUtils.isNotEmpty(errMsg)) {
            throw new BusinessException(errMsg);
        }
        if (emp.getWorkId() == 1) {
            emp.setIsLeader(1);
        } else {
            emp.setIsLeader(2);
        }

        emp.setPassword(MD5Utils.ecodeByMD5("123456"));
        emp.setIsDelete(0);
        emp.setCreateTime(new Date());
        emp.setLastModifyTime(new Date());
        emps.add(emp);
        return emps;
    }

    private String getCellValue(XSSFCell cell, int i) {
        String value = null;
        if (cell == null && (i == 1 || i == 3 || i == 4 || (i >= 8 && i <= 16) || i >= 18)) {
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
