package com.amir.controller.emp;

import com.alibaba.druid.util.StringUtils;
import com.amir.constant.SubsidyTypeEnum;
import com.amir.controller.base.ProductBaseController;
import com.amir.controller.emp.vo.EmpSubsibyMonthlyVo;
import com.amir.exception.BusinessException;
import com.amir.model.common.AppXaResultHelper;
import com.amir.model.common.Page;
import com.amir.model.common.XaResult;
import com.amir.model.emp.EmpSubsibyMonthly;
import com.amir.service.emp.EmpSubsibyMonthlyService;
import com.wordnik.swagger.annotations.Api;
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
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/9/14.
 */
@Api(value = "EmpController", description = "补贴，扣款", position = 1)
@RequestMapping(value = "/empSubsidyMonthly/")
@RestController("empSubsidyMonthlyController")
public class EmpSubsidyMonthlyController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(EmpSubsidyMonthlyController.class);
    @Resource
    private EmpSubsibyMonthlyService empSubsibyMonthlyService;

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public XaResult<Integer> addOrUpdate(Integer id, String yearMonth, String billNo, String empName,
                                         Integer empId, Integer deptId, String deptName, String workName,
                                         String remark, Double money, String reason, String drawer, Integer type) {
        if (StringUtils.isEmpty(yearMonth)) {
            return XaResult.error("月份不能为空");
        }
        if (empId == null || deptId == null || StringUtils.isEmpty(empName)
                || StringUtils.isEmpty(deptName) || StringUtils.isEmpty(workName)) {
            return XaResult.error("扣款人/补贴人信息不完整");
        }

        if (StringUtils.isEmpty(billNo)) {
            return XaResult.error("票据编号为空");
        }

        if (StringUtils.isEmpty(reason)) {
            return XaResult.error("扣款原因/补贴事项为空");
        }

        if (StringUtils.isEmpty(drawer)) {
            return XaResult.error("开票人为空");
        }

        if (type == null) return XaResult.error("类型不能为空");

        if (!type.equals(4) && !type.equals(9)) {
            return XaResult.error("类型不支持");
        }

        EmpSubsibyMonthly empSubsibyMonthly = new EmpSubsibyMonthly();
        empSubsibyMonthly.setDeptName(deptName);
        empSubsibyMonthly.setId(id);
        empSubsibyMonthly.setMoney(money == null ? BigDecimal.ZERO : BigDecimal.valueOf(money));
        empSubsibyMonthly.setIsDelete(0);
        empSubsibyMonthly.setLastModifyTime(new Date());
        empSubsibyMonthly.setEmpId(empId);
        empSubsibyMonthly.setEmpName(empName);
        empSubsibyMonthly.setBillNo(billNo);
        empSubsibyMonthly.setType(SubsidyTypeEnum.getByValue(type).getValue());
        empSubsibyMonthly.setWorkName(workName);
        empSubsibyMonthly.setDeptId(deptId);
        empSubsibyMonthly.setDrawer(drawer);
        empSubsibyMonthly.setDrawTime(new Date());
        empSubsibyMonthly.setRemark(remark);
        empSubsibyMonthly.setReason(reason);
        empSubsibyMonthly.setYearMonth(yearMonth);


        EmpSubsibyMonthly empSubsibyMonthly1 = empSubsibyMonthlyService.getByNoAndType(billNo, type);
        //新增
        if (null == id) {
            if (null != empSubsibyMonthly1) {
                return XaResult.error("单据编号已经存在");
            }
            empSubsibyMonthly.setCreateTime(new Date());
            empSubsibyMonthly.setIsConfirm(0);
            id = empSubsibyMonthlyService.save(empSubsibyMonthly);
        } else {
            if (empSubsibyMonthly1 == null) {
                return XaResult.error("记录不存在");
            }
            empSubsibyMonthly.setCreateTime(empSubsibyMonthly1.getCreateTime());
            empSubsibyMonthly.setIsConfirm(empSubsibyMonthly1.getIsConfirm());
            Integer record = empSubsibyMonthlyService.update(empSubsibyMonthly);
            if (record < 0) {
                return XaResult.error("更新失败");
            }
        }
        return XaResult.success(id);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<EmpSubsibyMonthly> detail(Integer id) {
        if (null == id) {
            return XaResult.error("id不能为空");
        }

        EmpSubsibyMonthly empSubsibyMonthly = empSubsibyMonthlyService.getByID(id);
        if (null == empSubsibyMonthly) {
            return XaResult.error("记录不存在");
        }
        return XaResult.success(empSubsibyMonthly);
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<EmpSubsibyMonthlyVo>> list(String yearMonth, String empName, String billNo, String deptName, Integer isConfirm,
                                                    Integer type, Integer pageSize, Integer currentPage) {

        if (type == null) {
            return XaResult.error("type必传");
        }

        if (type != null) {
            if (null == SubsidyTypeEnum.getByValue(type)) {
                return XaResult.error("类型错误");
            }
        }

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<EmpSubsibyMonthlyVo> pageList = empSubsibyMonthlyService.listPage(yearMonth, empName, billNo, deptName, isConfirm, type, page);

        XaResult<List<EmpSubsibyMonthlyVo>> result = AppXaResultHelper.success(pageList, pageList.getRows());
        return result;
    }


    @RequestMapping(value = "/conform", method = RequestMethod.POST)
    public XaResult confirm(String[] ids) {
        if (null == ids || Arrays.asList(ids).size() <= 0) {
            return XaResult.error("请选择要确认的记录");
        }
        Integer row = empSubsibyMonthlyService.confirm(Arrays.asList(ids));
        return XaResult.success(row);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportOther(String yearMonth, String empName, String billNo, String deptName, Integer type, Integer isConfirm, HttpServletResponse response) {

        if (type == null) {
            throw new BusinessException("type必传");
        }

        if (type != null) {
            if (null == SubsidyTypeEnum.getByValue(type)) {
                throw new BusinessException("类型错误");
            }
        }

        List<EmpSubsibyMonthlyVo> empSubsibyMonthlyVos = empSubsibyMonthlyService.list(yearMonth, empName, billNo, deptName, isConfirm, SubsidyTypeEnum.getByValue(type).getValue());

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
        sheet.setColumnWidth(9, (int) ((15 + 0.72) * 256));

        int j = 0;
        header.createCell(j++).setCellValue("工资月份");
        header.createCell(j++).setCellValue("人员名称");
        header.createCell(j++).setCellValue("所在部门");
        header.createCell(j++).setCellValue("单据编号");
        String fileName = null;
        switch (type) {
            case 9:
                header.createCell(j++).setCellValue("扣款原因");
                header.createCell(j++).setCellValue("扣款金额");
                fileName = "其他扣款.xlsx";
                break;
            case 4:
                header.createCell(j++).setCellValue("补贴事项");
                header.createCell(j++).setCellValue("金额");
                fileName = "工资补贴.xlsx";
                break;

        }
        header.createCell(j++).setCellValue("开票人");
        header.createCell(j++).setCellValue("开票时间");
        header.createCell(j++).setCellValue("备注");
        header.createCell(j++).setCellValue("是否确认");
        EmpSubsibyMonthlyVo empSubsibyMonthlyVo = null;
        if (empSubsibyMonthlyVos != null && empSubsibyMonthlyVos.size() >= 1) {
            for (int i = 0; i < empSubsibyMonthlyVos.size(); i++) {
                empSubsibyMonthlyVo = empSubsibyMonthlyVos.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getYearMonth());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getEmpName());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getDeptName());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getBillNo());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getReason());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getMoney().toString());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getDrawer());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getDrawTime());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getRemark());
                row.createCell(j++).setCellValue(empSubsibyMonthlyVo.getIsConfirm() == 1 ? "已确认" : "未确认");
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error(fileName + "导出excel异常");
        }
    }
}