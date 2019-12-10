package com.amir.controller.emp;


import com.amir.controller.emp.vo.EmpSubsidyVo;
import com.amir.excel.BaseExcelHandler;
import com.amir.exception.BusinessException;
import com.amir.model.AppXaResultHelper;
import com.amir.model.Page;
import com.amir.model.XaResult;
import com.amir.model.emp.Emp;
import com.amir.model.salary.SalaryMonthly;
import com.amir.service.emp.EmpService;
import com.amir.service.salary.SalaryMonthlyService;
import com.amir.util.BigDecimalUtil;
import com.amir.util.MD5Utils;
import com.amir.util.SalaryHandler;
import com.amir.vo.weixin.EmpVo;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;


/**
 * Created by Administrator on 2018/7/3 0003.
 */
@Api(value = "EmpController", description = "用户管理", position = 1)
@RequestMapping(value = "/emp/")
@RestController("empController")
public class EmpController {

    @Resource
    private EmpService empService;

    @Resource
    private SalaryMonthlyService salaryMonthlyService;


    /**
     * 详情
     *
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<Emp> detail(Integer id) {
        if (id == null) {
            return XaResult.error("id不能为空");
        }
        Emp emp = empService.getByID(id);
        if (emp == null) {
            return XaResult.error("该员工不存在");
        }
        emp.setPassword(null);
        return XaResult.success(emp);
    }


    /**
     * 添加、修改
     *
     * @return
     */
    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public XaResult<String> addOrUpdate(Integer id, String name, String phone,
                                        Integer deptId, Integer sex, Integer workId, Integer type,
                                        String nativeSource, String nation, String birthday,
                                        String idCard, String entryDate, String school,
                                        String subject, String education, String linkMan,
                                        String tel, String address, Double salary,
                                        Double ylbx, Double sybx, Double yiliaobx,
                                        Double gjj, Double phoneSubsidy, Double waterSubsidy,
                                        Double foodSubsidy, Double socialSubsidy,
                                        String remark
    ) {
        if (StringUtils.isEmpty(name)) {
            return XaResult.error("姓名不能为空");
        }
        if (deptId == null) {
            return XaResult.error("部门不能为空");
        }
        if (workId == null) {
            return XaResult.error("工种不能为空");
        }
        if (type == null || type < 1 || type > 2) {
            return XaResult.error("人员类别错误");
        }
        if (StringUtils.isEmpty(birthday)) {
            return XaResult.error("出生年月不能为空");
        }
        if (StringUtils.isEmpty(entryDate)) {
            return XaResult.error("进场日期不能为空");
        }
        if (id == null) {
            Emp emp1 = empService.getByName(name);
            if (emp1 != null) {
                return XaResult.error("系统中该姓名：" + name + " 已存在");
            }
        }
        if (salary == null) salary = 0.0;
        if (ylbx == null) ylbx = 0.0;
        if (sybx == null) sybx = 0.0;
        if (yiliaobx == null) yiliaobx = 0.0;
        if (gjj == null) gjj = 0.0;
        if (phoneSubsidy == null) phoneSubsidy = 0.0;
        if (waterSubsidy == null) waterSubsidy = 0.0;
        if (foodSubsidy == null) foodSubsidy = 0.0;
        if (socialSubsidy == null) socialSubsidy = 0.0;


        Emp emp = new Emp();
        emp.setId(id);
        emp.setName(name);
        emp.setPhone(phone);
        emp.setDeptId(deptId);
        emp.setWorkId(workId);
        emp.setSex(sex);
        emp.setType(type);
        if (workId == 1) {
            emp.setIsLeader(1);
        } else {
            emp.setIsLeader(2);
        }
        emp.setNativeSource(nativeSource);
        emp.setNation(nation);
        emp.setBirthday(birthday);
        emp.setIdCard(idCard);
        emp.setEntryDate(entryDate);
        emp.setSchool(school);
        emp.setSubject(subject);
        emp.setEducation(education);
        emp.setLinkMan(linkMan);
        emp.setTel(tel);
        emp.setAddress(address);
        emp.setSalary(BigDecimal.valueOf(salary));
        emp.setYlbx(BigDecimal.valueOf(ylbx));
        emp.setSybx(BigDecimal.valueOf(sybx));
        emp.setYiliaobx(BigDecimal.valueOf(yiliaobx));
        emp.setGjj(BigDecimal.valueOf(gjj));
        emp.setWaterSubsidy(BigDecimal.valueOf(waterSubsidy));
        emp.setFoodSubsidy(BigDecimal.valueOf(foodSubsidy));
        emp.setPhoneSubsidy(BigDecimal.valueOf(phoneSubsidy));
        emp.setSocialSubsidy(BigDecimal.valueOf(socialSubsidy));
        emp.setRemark(remark);

        if (id == null) {
            emp.setPassword(MD5Utils.ecodeByMD5("123456"));
            emp.setIsDelete(0);
            emp.setCreateTime(new Date());
            emp.setLastModifyTime(new Date());
            empService.create(emp);
        } else {
            emp.setLastModifyTime(new Date());
            empService.update(emp);
        }

        return XaResult.success();
    }


    @RequestMapping(value = "list", method = RequestMethod.GET)
    public XaResult<List<EmpVo>> list(String name, Integer deptId, String nativeSource,
                                      String startEntryDate, String endEntryDate,
                                      Integer pageSize, Integer currentPage) throws BusinessException {

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);

        Page<EmpVo> empPage = empService.getPage(name, deptId, nativeSource, startEntryDate, endEntryDate, page);

        XaResult<List<EmpVo>> result = AppXaResultHelper.success(empPage, empPage.getRows());
        return result;
    }

    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void export(String name, Integer deptId, String nativeSource,
                       String startEntryDate, String endEntryDate, HttpServletResponse response) throws BusinessException {


        List<EmpVo> empVos = empService.getList(name, deptId, nativeSource, startEntryDate, endEntryDate);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(2, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(3, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(5, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(6, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(7, (int) ((15 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((15 + 0.72) * 256));
        int j = 0;
        header.createCell(j++).setCellValue("序号");
        header.createCell(j++).setCellValue("姓名");
        header.createCell(j++).setCellValue("性别");
        header.createCell(j++).setCellValue("部门");
        header.createCell(j++).setCellValue("人员类别");
        header.createCell(j++).setCellValue("工种");
        header.createCell(j++).setCellValue("学历");
        header.createCell(j++).setCellValue("籍贯");
        header.createCell(j++).setCellValue("身份证号码");
        header.createCell(j++).setCellValue("出生日期");
        header.createCell(j++).setCellValue("入职日期");
        EmpVo empVo = null;
        if (empVos != null && empVos.size() >= 1) {
            for (int i = 0; i < empVos.size(); i++) {
                empVo = empVos.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(empVo.getId());
                row.createCell(j++).setCellValue(empVo.getName());
                row.createCell(j++).setCellValue(empVo.getSex());
                row.createCell(j++).setCellValue(empVo.getDeptName());
                row.createCell(j++).setCellValue(empVo.getType());
                row.createCell(j++).setCellValue(empVo.getWorkName());
                row.createCell(j++).setCellValue(empVo.getEducation());
                row.createCell(j++).setCellValue(empVo.getNativeSource());
                row.createCell(j++).setCellValue(empVo.getIdCard());
                row.createCell(j++).setCellValue(empVo.getBirthday());
                row.createCell(j++).setCellValue(empVo.getEntryDate());
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("员工.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 社保扣款 || 工龄补贴
     *
     * @param empName
     * @param deptName
     * @param pageSize
     * @param currentPage
     * @param type
     * @param yearMonth
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/socialOrWorkAgeSubsidyList", method = RequestMethod.GET)
    public XaResult<List<EmpSubsidyVo>> socialOrWorkAgeSubsidy(String empName, String deptName, Integer pageSize, Integer currentPage,
                                                               Integer type, String yearMonth) throws BusinessException {

        if (type == null) {
            return XaResult.error("type不为null");
        }

        if (type != null && (type != 1 && type != 2)) {
            return XaResult.error("类型错误");
        }

        SalaryMonthly salaryMonthly = null;
        if (type == 2) {
            if (StringUtils.isEmpty(yearMonth)) {
                return XaResult.error("年月不能为空，格式为yyyy-MM");
            }
            if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
                return XaResult.error("年月格式不符，请更正为yyyy-MM");
            }

            salaryMonthly = salaryMonthlyService.getByYearMonth(yearMonth);
            if (salaryMonthly == null) {
                return XaResult.error("记录不存在");
            }
            if (salaryMonthly.getIsMore() == null) {
                return XaResult.error("该月份产值未设置");
            }
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);

        Page<EmpSubsidyVo> empSubsidyVoPage = empService.getByNameAndDeptNamePage(empName, deptName, type, page);
        List<EmpSubsidyVo> empSubsidyVos = empSubsidyVoPage.getRows();
        if (!CollectionUtils.isEmpty(empSubsidyVos)) {
            for (EmpSubsidyVo empSubsidyVo : empSubsidyVos) {
                if (type == 1) { //社保补贴
                    empSubsidyVo.setSumDeduction(BigDecimal.valueOf(
                            BigDecimalUtil.add(empSubsidyVo.getGjj() != null ? empSubsidyVo.getGjj().doubleValue() : 0.0,
                                    empSubsidyVo.getSybx() != null ? empSubsidyVo.getSybx().doubleValue() : 0.0,
                                    empSubsidyVo.getYiliaobx() != null ? empSubsidyVo.getYiliaobx().doubleValue() : 0.0,
                                    empSubsidyVo.getYlbx() != null ? empSubsidyVo.getYlbx().doubleValue() : 0.0)));
                } else { //工龄补贴
                    Integer months = SalaryHandler.getMonths(empSubsidyVo.getEntryDate());
                    empSubsidyVo.setWorkAge(BigDecimalUtil.div(months, 12.0, 2) + "年");
                    empSubsidyVo.setAgeSubsidy(BigDecimal.valueOf(SalaryHandler.getWorkYearSubsidy(months, salaryMonthly.getIsMore())));
                }
            }
        }
        XaResult<List<EmpSubsidyVo>> result = AppXaResultHelper.success(empSubsidyVoPage, empSubsidyVoPage.getRows());
        return result;
    }

    @RequestMapping(value = "/empList", method = RequestMethod.GET)
    public XaResult<List<EmpVo>> getEmpList(String empName) {
        List<EmpVo> empVos = empService.getList(empName, null, null, null, null);
        return XaResult.success(empVos);
    }
}
