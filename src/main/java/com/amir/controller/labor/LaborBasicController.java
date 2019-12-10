package com.amir.controller.labor;


import com.amir.controller.base.ProductBaseController;
import com.amir.excel.BaseExcelHandler;
import com.amir.model.AppXaResultHelper;
import com.amir.model.Page;
import com.amir.model.XaResult;
import com.amir.model.emp.*;
import com.amir.model.order.ProductionLuo;
import com.amir.model.order.ProductionProcedureConfirm;
import com.amir.model.salary.SalaryMonthly;
import com.amir.model.sys.Sysdept;
import com.amir.service.emp.*;
import com.amir.service.order.ProductionLuoService;
import com.amir.service.order.ProductionProcedureConfirmService;
import com.amir.service.order.ProductionProcedureScanService;
import com.amir.service.salary.SalaryMonthlyService;
import com.amir.service.sys.SysDeptService;
import com.amir.util.BigDecimalUtil;
import com.amir.util.DateUtil;
import com.amir.vo.EmpSubsidyVo;
import com.amir.vo.EmpTimesalaryMonthlyVo;
import com.amir.vo.ProcedureYieldVo;
import com.amir.vo.SubsidyVo;
import com.google.common.collect.Maps;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Created by Administrator on 2018/7/3 0003.
 */
@Api(value = "LaborBasicController", description = "劳资管理基础接口", position = 1)
@RequestMapping(value = "/labor/")
@RestController("laborBasicController")
public class LaborBasicController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(LaborBasicController.class);

    @Resource
    private SalaryMonthlyService salaryMonthlyService;
    @Resource
    private EmpService empService;
    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;
    @Resource
    private EmpTimeSalaryService empTimeSalaryService;
    @Resource
    private SysDeptService sysDeptService;
    @Resource
    private EmpWorkService empWorkService;
    @Resource
    private EmpSalaryMonthlyService empSalaryMothlyService;
    @Resource
    private ScoreService scoreService;
    @Resource
    private SummarySalaryMonthlyService summarySalaryMonthlyService;
    @Resource
    private ProductionProcedureScanService productionProcedureScanService;
    @Resource
    private ProductionLuoService productionLuoService;

    /**
     * 工资月度 新增 修改
     *
     * @return
     */
    @RequestMapping(value = "/salary/addOrUpdate", method = RequestMethod.POST)
    public XaResult<String> addOrUpdate(Integer id, String yearMonth, Integer expectWorkDay,
                                        Integer realityWorkDay, Double basicSalary, Double hourlyWage) {
        if (StringUtils.isEmpty(yearMonth)) {
            return XaResult.error("年月不能为空");
        }
        if (!BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (expectWorkDay == null || expectWorkDay <= 0) {
            return XaResult.error("工作日必须大于0");
        }
        if (realityWorkDay == null || realityWorkDay <= 0) {
            return XaResult.error("正常工作天数必须大于0");
        }
        if (basicSalary == null || basicSalary <= 0) {
            return XaResult.error("基本工资必须大于0");
        }
        if (hourlyWage == null || hourlyWage <= 0) {
            return XaResult.error("时薪必须大于0");
        }
        if (id == null) {
            SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(yearMonth);
            if (salaryMonthly == null) {
                salaryMonthly = new SalaryMonthly();
            } else {
                return XaResult.error("该月份已经设置工资，请勿重复提交");
            }

            salaryMonthly.setBasicSalary(BigDecimal.valueOf(basicSalary));
            salaryMonthly.setExpectWorkDay(expectWorkDay);
            salaryMonthly.setRealityWorkDay(realityWorkDay);
            salaryMonthly.setHourlyWage(BigDecimal.valueOf(hourlyWage));
            salaryMonthly.setYearMonth(yearMonth);
            salaryMonthly.setCreateTime(new Date());
            salaryMonthly.setLastModifyTime(new Date());
            salaryMonthly.setIsDelete(0);
            salaryMonthlyService.create(salaryMonthly);
        } else {
            SalaryMonthly salaryMonthly = salaryMonthlyService.get(id);
            salaryMonthly.setBasicSalary(BigDecimal.valueOf(basicSalary));
            salaryMonthly.setExpectWorkDay(expectWorkDay);
            salaryMonthly.setRealityWorkDay(realityWorkDay);
            salaryMonthly.setHourlyWage(BigDecimal.valueOf(hourlyWage));
            salaryMonthly.setYearMonth(yearMonth);
            salaryMonthly.setLastModifyTime(new Date());
            salaryMonthlyService.update(salaryMonthly);
        }


        return XaResult.success();
    }

    /**
     * 工资月度 结算
     *
     * @return
     */
    @RequestMapping(value = "/salary/setValue", method = RequestMethod.POST)
    public XaResult<String> setValue(String yearMonth, Integer isMore, Double twoSideRate, Double assistRate) {
        if (StringUtils.isEmpty(yearMonth)) {
            return XaResult.error("年月不能为空");
        }
        if (!BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (isMore == null) {
            return XaResult.error("产值不能为空");
        }
        if (assistRate == null) assistRate = BigDecimal.ZERO.doubleValue();
        if (twoSideRate == null) twoSideRate = BigDecimal.ZERO.doubleValue();
        SalaryMonthly salaryMonthly = salaryMonthlyService.getByYearMonth(yearMonth);
        if (salaryMonthly == null) {
            return XaResult.error("请先设置该月工资");
        } else {
            salaryMonthly.setIsMore(isMore);
            salaryMonthly.setLastModifyTime(new Date());
            salaryMonthly.setAssistRate(BigDecimal.valueOf(assistRate));
            salaryMonthly.setTwoSideRate(BigDecimal.valueOf(twoSideRate));
            salaryMonthlyService.update(salaryMonthly);

        }
        return XaResult.success();
    }


    /**
     * 工资月度 列表
     *
     * @return
     */
    @RequestMapping(value = "/salary/list", method = RequestMethod.GET)
    public XaResult<List<SalaryMonthly>> salaryList(Integer pageSize, Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<SalaryMonthly> listPage = salaryMonthlyService.list(page);
        XaResult<List<SalaryMonthly>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }


    /**
     * 工资月度 结算
     *
     * @return
     */
    @RequestMapping(value = "/salary/settlement", method = RequestMethod.POST)
    public XaResult<String> settlement(Integer id) {
        if (id == null || id <= 0) {
            return XaResult.error("id 不能为空");
        }
        SalaryMonthly salaryMonthly = salaryMonthlyService.get(id);
        if (salaryMonthly == null) {
            return XaResult.error("记录不存在");
        }
        if (salaryMonthly.getIsMore() == null) {
            return XaResult.error("该月份产值未设置");
        }
        if (!DateUtil.isAfter(DateUtil.string2Date(salaryMonthly.getYearMonth(), DateUtil.ymFormat),
                DateUtil.dateBefore(new Date(), 5, 3))) {
            return XaResult.error("三个月前的信息不允许再度结算");
        }
        List<Score> scoreList = scoreService.getList(salaryMonthly.getYearMonth(), null, null);
        if (scoreList == null || scoreList.size() < 1) {
            return XaResult.error("该月份考勤分或3个分未导入");
        }
        List<EmpSalaryMonthly> empSalaryMonthlyList = empSalaryMothlyService.getList(salaryMonthly.getYearMonth(), null, null, null);
        if (empSalaryMonthlyList == null || empSalaryMonthlyList.size() < 1) {
            return XaResult.error("该月份考勤信息未导入");
        }
        empSalaryMothlyService.calculation(salaryMonthly.getYearMonth(), null, null, null);
        return XaResult.success();
    }

    /**
     * 话费补贴 列表
     *
     * @return
     */
    @RequestMapping(value = "/phoneSubsidy/list", method = RequestMethod.GET)
    public XaResult<List<EmpSubsidyVo>> phoneSubsidyList(String name, Integer deptId,
                                                         Integer pageSize, Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<EmpSubsidyVo> listPage = empService.phoneSubsidyList(name, deptId, page);
        XaResult<List<EmpSubsidyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    /**
     * 社保补贴 列表
     *
     * @return
     */
    @RequestMapping(value = "/socialSubsidy/list", method = RequestMethod.GET)
    public XaResult<List<EmpSubsidyVo>> socialSubsidyList(String name, Integer deptId,
                                                          Integer pageSize, Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<EmpSubsidyVo> listPage = empService.socialSubsidyList(name, deptId, page);
        XaResult<List<EmpSubsidyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }


    /**
     * 工序 产量 列表
     *
     * @return
     */
    @RequestMapping(value = "/yield/list", method = RequestMethod.GET)
    public XaResult<List<ProcedureYieldVo>> yieldList(String name, Integer deptId, Integer workId,
                                                      String orderNo, String productNo, String procedureName, String yearMonth,
                                                      String startDate, String endDate, Integer pageSize, Integer currentPage, Integer confirmed) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<ProcedureYieldVo> listPage = productionProcedureConfirmService.yieldList(name, deptId, workId,
                orderNo, productNo, procedureName, yearMonth, startDate, endDate, page, confirmed);
        List<ProcedureYieldVo> procedureYieldVos = listPage.getRows();

        procedureYieldVos.forEach(t -> {
            if (t.getConfirmed() == 1) {
                String productionNo = t.getProductionNo();
                Integer luoId = null;
                if (!StringUtils.isBlank(productionNo) && productionNo.contains("-")) {
                    String[] productionNos = productionNo.split("-");
                    ProductionLuo productionLuo = productionLuoService.getByProductionNoAndSort(productionNos[0], new Integer(productionNos[1]));
                    if (productionLuo != null) {
                        luoId = productionLuo.getId();
                    }
                }
                ProductionProcedureConfirm productionProcedureConfirm = productionProcedureConfirmService.getType2(t.getOrderNo(), t.getProcedureName(), t.getProductNo(), productionNo, luoId);
                t.setConfirmedMoney(productionProcedureConfirm == null ? t.getMoney() : productionProcedureConfirm.getMoney().doubleValue());
                t.setMoney(t.getConfirmedMoney());
            } else {
                t.setConfirmedMoney(Double.valueOf("0"));
            }
        });

        //Double confirmedMoney = procedureYieldVos.stream().filter(t -> t.getConfirmed() == 1).map(ProcedureYieldVo::getMoney).reduce(BigDecimal.ZERO.doubleValue(), BigDecimalUtil::add);
        //Double notConfirmedMoney = procedureYieldVos.stream().filter(t -> t.getConfirmed() == 0).map(ProcedureYieldVo::getMoney).reduce(BigDecimal.ZERO.doubleValue(), BigDecimalUtil::add);

        Double confirmedMoney = BigDecimal.ZERO.doubleValue();
        Double notConfirmedMoney = BigDecimal.ZERO.doubleValue();
        if (confirmed == null || confirmed == 1) {
            confirmedMoney = productionProcedureConfirmService.getAllConfirmed(name, deptId, workId,
                    orderNo, productNo, procedureName, yearMonth, startDate, endDate);

        }

        if (confirmed == null || confirmed == 0) {
            notConfirmedMoney = productionProcedureScanService.getAllUnConfirm(name, deptId, workId,
                    orderNo, productNo, procedureName, yearMonth, startDate, endDate);
        }

        Map<String, Object> map = Maps.newHashMap();
        map.put("confirmedMoney", confirmedMoney);
        map.put("notConfirmedMoney", notConfirmedMoney);
        map.put("totalMoney", BigDecimalUtil.add(notConfirmedMoney, confirmedMoney));
        XaResult<List<ProcedureYieldVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        result.setMap(map);
        return result;
    }

    /**
     * 新增\修改 计时工资
     *
     * @return
     */
    @RequestMapping(value = "/hourlywage/addOrUpdate", method = RequestMethod.POST)
    public XaResult<String> hourlywageAdd(Integer id, String yearMonth, Integer empId,
                                          String billNo, String content, Double price,
                                          Double num, String remark, String unit) {
        if (StringUtils.isEmpty(yearMonth)) {
            return XaResult.error("工资年月不能为空");
        }
        if (!BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (empId == null) {
            return XaResult.error("员工ID不能为空");
        }
        if (StringUtils.isEmpty(billNo)) {
            return XaResult.error("单据编号不能为空");
        }
        if (StringUtils.isEmpty(content)) {
            return XaResult.error("工作内容不能为空");
        }
        if (StringUtils.isEmpty(unit)) {
            return XaResult.error("单位不能为空");
        }
        if (price == null || price <= 0) {
            return XaResult.error("单价必须大于0");
        }
        if (num == null || num <= 0) {
            return XaResult.error("数量必须大于0");
        }
        if (id != null) {
            EmpTimesalaryMonthly empTimesalaryMonthly = empTimeSalaryService.get(id);
            if (empTimesalaryMonthly == null) {
                return XaResult.error("修改记录不存在");
            }
            if (empTimesalaryMonthly.getIsConfirm() == 1) {
                return XaResult.error("该记录已确认，不能修改");
            }
            Emp emp = empService.getByID(empId);
            if (emp == null) {
                return XaResult.error("该员工不存在");
            }
            Sysdept sysdept = sysDeptService.get(emp.getDeptId());
            EmpWork empWork = empWorkService.getByID(emp.getWorkId());
            empTimesalaryMonthly.setYearMonth(yearMonth);
            empTimesalaryMonthly.setEmpId(empId);
            empTimesalaryMonthly.setEmpName(emp.getName());
            empTimesalaryMonthly.setBillNo(billNo);
            empTimesalaryMonthly.setContent(content);
            empTimesalaryMonthly.setPrice(BigDecimal.valueOf(price));
            empTimesalaryMonthly.setNum(BigDecimal.valueOf(num));
            empTimesalaryMonthly.setMoney(empTimesalaryMonthly.getPrice().multiply(empTimesalaryMonthly.getNum()));
            empTimesalaryMonthly.setDeptId(emp.getDeptId());
            empTimesalaryMonthly.setDeptName(sysdept == null ? "" : sysdept.getDeptName());
            empTimesalaryMonthly.setDrawer(getLoginUser().getUserName());
            empTimesalaryMonthly.setDrawTime(new Date());
            empTimesalaryMonthly.setLastModifyTime(new Date());
            empTimesalaryMonthly.setRemark(remark);
            empTimesalaryMonthly.setUnit(unit);
            empTimesalaryMonthly.setWorkName(empWork == null ? "" : empWork.getName());
            empTimeSalaryService.update(empTimesalaryMonthly);
        } else {
            EmpTimesalaryMonthly empTimesalaryMonthly = empTimeSalaryService.findByBillNo(billNo);
            if (empTimesalaryMonthly != null) {
                return XaResult.error("单据编号重复，请修改");
            }
            Emp emp = empService.getByID(empId);
            if (emp == null) {
                return XaResult.error("该员工不存在");
            }
            Sysdept sysdept = sysDeptService.get(emp.getDeptId());
            EmpWork empWork = empWorkService.getByID(emp.getWorkId());

            empTimesalaryMonthly = new EmpTimesalaryMonthly();
            empTimesalaryMonthly.setYearMonth(yearMonth);
            empTimesalaryMonthly.setEmpId(empId);
            empTimesalaryMonthly.setEmpName(emp.getName());
            empTimesalaryMonthly.setBillNo(billNo);
            empTimesalaryMonthly.setContent(content);
            empTimesalaryMonthly.setPrice(BigDecimal.valueOf(price));
            empTimesalaryMonthly.setNum(BigDecimal.valueOf(num));
            empTimesalaryMonthly.setMoney(empTimesalaryMonthly.getPrice().multiply(empTimesalaryMonthly.getNum()));
            empTimesalaryMonthly.setDeptId(emp.getDeptId());
            empTimesalaryMonthly.setDeptName(sysdept == null ? "" : sysdept.getDeptName());
            empTimesalaryMonthly.setDrawer(getLoginUser().getUserName());
            empTimesalaryMonthly.setDrawTime(new Date());
            empTimesalaryMonthly.setCreateTime(new Date());
            empTimesalaryMonthly.setLastModifyTime(new Date());
            empTimesalaryMonthly.setRemark(remark);
            empTimesalaryMonthly.setUnit(unit);
            empTimesalaryMonthly.setIsDelete(0);
            empTimesalaryMonthly.setIsConfirm(0);
            empTimesalaryMonthly.setWorkName(empWork == null ? "" : empWork.getName());
            empTimeSalaryService.create(empTimesalaryMonthly);
        }
        return XaResult.success();
    }

    /**
     * 确认 计时工资
     *
     * @return
     */
    @RequestMapping(value = "/hourlywage/confirm", method = RequestMethod.POST)
    public XaResult<String> hourlywageAdd(Integer[] ids) {
        if (ids == null || ids.length < 1) {
            return XaResult.error("ID不能为空");
        }
        empTimeSalaryService.confirm(ids);
        return XaResult.success();
    }


    /**
     * 计时工资 列表
     *
     * @return
     */
    @RequestMapping(value = "/hourlywage/list", method = RequestMethod.GET)
    public XaResult<List<EmpTimesalaryMonthlyVo>> hourlywageList(String yearMonth, String empName, String deptName, String billNo,
                                                                 Integer isConfirm, Integer pageSize, Integer currentPage) {
        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<EmpTimesalaryMonthlyVo> listPage = empTimeSalaryService.findList(yearMonth, empName, deptName, billNo, isConfirm, page);
        XaResult<List<EmpTimesalaryMonthlyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    /**
     * 计时工资 列表
     *
     * @return
     */
    @RequestMapping(value = "/hourlywage/except", method = RequestMethod.GET)
    public void except(String yearMonth, String empName, String deptName, String billNo,
                       Integer isConfirm, HttpServletResponse response) {
        List<EmpTimesalaryMonthlyVo> list = empTimeSalaryService.findExceptList(yearMonth, empName, deptName, billNo, isConfirm);
        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);
        List<String> fields = Stream.of("工资月份", "人员姓名", "所在部门", "工种", "票据编号", "工作内容", "单价",
                "计算数量", "所得金额", "开票人", "开票时间", "备注", "是否确认").collect(Collectors.toList());
        for (int i = 0; i < fields.size(); i++) {
            header.createCell(i).setCellValue(fields.get(i));
        }

        EmpTimesalaryMonthlyVo pm = null;
        if (list != null && list.size() >= 1) {
            for (int i = 0; i < list.size(); i++) {
                pm = list.get(i);
                Row row = sheet.createRow(i + 1);
                int j = 0;
                row.createCell(j++).setCellValue(pm.getYearMonth());
                row.createCell(j++).setCellValue(pm.getEmpName());
                row.createCell(j++).setCellValue(pm.getDeptName());
                row.createCell(j++).setCellValue(pm.getWorkName());
                row.createCell(j++).setCellValue(pm.getBillNo());
                row.createCell(j++).setCellValue(pm.getContent());
                row.createCell(j++).setCellValue(pm.getPrice().toString());
                row.createCell(j++).setCellValue(pm.getNum().toString() + pm.getUnit());
                row.createCell(j++).setCellValue(pm.getMoney().toString());
                row.createCell(j++).setCellValue(pm.getDrawer());
                row.createCell(j++).setCellValue(pm.getDrawTime());
                row.createCell(j++).setCellValue(pm.getRemark());
                row.createCell(j++).setCellValue(pm.getIsConfirm().equals(1) ? "已确认" : "未确认");
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("计时工资导出.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("计时工资导出excel异常");
        }

    }


    /**
     * 车工出勤补贴 列表
     *
     * @return
     */
    @RequestMapping(value = "/latheWorkerSubsidy/list", method = RequestMethod.GET)
    public XaResult<List<SubsidyVo>> latheWorkerSubsidyList(String yearMonth, String empName, String deptName,
                                                            Integer pageSize, Integer currentPage) {
        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<SubsidyVo> listPage = summarySalaryMonthlyService.findLatheWorkerSubsidyList(yearMonth, empName, deptName, page);
        XaResult<List<SubsidyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    /**
     * 新车工出勤补贴 列表
     *
     * @return
     */
    @RequestMapping(value = "/newLatheWorkerSubsidy/list", method = RequestMethod.GET)
    public XaResult<List<SubsidyVo>> newLatheWorkerSubsidyList(String yearMonth, String empName, String deptName,
                                                               Integer pageSize, Integer currentPage) {
        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<SubsidyVo> listPage = summarySalaryMonthlyService.findNewLatheWorkerSubsidyList(yearMonth, empName, deptName, page);
        XaResult<List<SubsidyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    /**
     * 计件补贴 列表
     *
     * @return
     */
    @RequestMapping(value = "/percentSubsidy/list", method = RequestMethod.GET)
    public XaResult<List<SubsidyVo>> percentSubsidyList(String yearMonth, String empName, String deptName,
                                                        Integer pageSize, Integer currentPage) {
        if (yearMonth != null && !BaseExcelHandler.isRightDateStr(yearMonth, "yyyy-MM")) {
            return XaResult.error("年月格式不符，请更正为yyyy-MM");
        }
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<SubsidyVo> listPage = summarySalaryMonthlyService.findPercentSubsidyList(yearMonth, empName, deptName, page);
        XaResult<List<SubsidyVo>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }
}
