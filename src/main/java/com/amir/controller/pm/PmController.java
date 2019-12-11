package com.amir.controller.pm;

import com.amir.controller.base.ProductBaseController;
import com.amir.model.common.AppXaResultHelper;
import com.amir.model.common.Page;
import com.amir.model.common.XaResult;
import com.amir.model.pm.Pm;
import com.amir.model.pm.PmRequstPojo;
import com.amir.model.sys.SysUser;
import com.amir.service.pm.PmService;
import com.amir.util.DateUtil;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/7/28.
 */
@RestController
@RequestMapping(value = "/pm")
@Api(value = "PmControllerProduct", description = "材料管理", position = 1)
public class PmController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(PmController.class);

    @Resource
    private PmService pmService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<Pm>> findList(@ApiParam("编号") String pmNo, @Param("name") String name, @ApiParam("颜色") String colour, @ApiParam("规格")
            String norms, @Param("材质") String material, @Param("称呼") String callStr
            , @ApiParam("类型") String type, Integer pageSize, Integer currentPage) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        PmRequstPojo pmRequstPojo = new PmRequstPojo();
        pmRequstPojo.setPmNo(pmNo);
        pmRequstPojo.setName(name);
        pmRequstPojo.setType(type);
        pmRequstPojo.setCallStr(callStr);
        pmRequstPojo.setColour(colour);
        pmRequstPojo.setMaterial(material);
        pmRequstPojo.setNorms(norms);
        Page<Pm> listPage = pmService.findListPage(pmRequstPojo, page);
        List<Pm> pms = listPage.getRows();
        pms.stream().filter(t -> t != null).forEach(t -> {
            if (t.getCreateTime() != null) {
                t.setCreateTimeStr(DateUtil.dateToString(t.getCreateTime(), DateUtil.ymdFormat));
            }
        });
        XaResult<List<Pm>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public XaResult<Integer> deleteByID(@ApiParam("id数组") String[] ids) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (null == ids || Arrays.asList(ids).size() <= 0) {
            return XaResult.error("请选择要删除的记录");
        }
        List<Integer> integers = Lists.newArrayList();
        Arrays.asList(ids).stream().forEach(t -> {
            integers.add(new Integer(t));
        });
        int rows = pmService.deleteByID(integers);
        return XaResult.success(rows);
    }

    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public XaResult<Integer> addOrUpdate(@ApiParam("id") Integer id, @ApiParam("编号") String pmNo, @ApiParam("名称")
            String name, @ApiParam("颜色") String colour, @ApiParam("规格")
                                                 String norms, @Param("材质") String material, @Param("称呼") String callStr, @ApiParam("类型") String type, @ApiParam("单位") String unit,
                                         @ApiParam("备注") String remark) {
        LOGGER.info(getRequestParamsAndUrl());

        SysUser sysUser = getLoginUser();
        StringBuffer nameStr = new StringBuffer();
        if (!StringUtils.isEmpty(colour)) {
            nameStr.append(colour).append("-");
        }
        if (!StringUtils.isEmpty(norms)) {
            nameStr.append(norms).append("-");
        }
        if (!StringUtils.isEmpty(material)) {
            nameStr.append(material).append("-");
        }
        if (!StringUtils.isEmpty(callStr)) {
            nameStr.append(callStr).append("-");
        }
        if (nameStr.toString().endsWith("-")) {
            name = nameStr.toString().substring(0, nameStr.toString().length() - 1);
        } else {
            name = nameStr.toString();
        }
        if (null != id) { //更新
            Pm pm = pmService.getByID(id);
            pm.setLastModifyTime(new Date());
            pm.setPmNo(pmNo);
            pm.setType(type);
            pm.setUnit(unit);
            pm.setRemark(remark);
            pm.setCallStr(callStr);
            pm.setColour(colour);
            pm.setNorms(norms);
            pm.setMaterial(material);
            pm.setOperator(sysUser.getUserName());

            id = pmService.updateByID(pm);
        } else {
            Pm pm = new Pm();
            pm.setLastModifyTime(new Date());
            if (null != pmService.getByNo(pmNo)) {
                return XaResult.error("已有相同物料编号，请修改");
            }
            pm.setPmNo(pmNo);
            pm.setName(name);
            pm.setType(type);
            pm.setUnit(unit);
            pm.setCallStr(callStr);
            pm.setColour(colour);
            pm.setNorms(norms);
            pm.setMaterial(material);
            pm.setRemark(remark);
            pm.setCreateTime(new Date());
            pm.setOperator(sysUser.getUserName());
            pm.setNum(BigDecimal.ZERO);
            pm.setIsDelete(0);
            id = pmService.insert(pm);
        }

        return XaResult.success(id);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<Pm> getDetail(@ApiParam("id") Integer id) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());
        if (id == null) {
            return XaResult.error("请选中项");
        }

        Pm pm = pmService.getByID(id);
        return XaResult.success(pm);
    }

    /**
     * 导出列表数据
     *
     * @param pmNo
     * @param name
     * @param type
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportPm(@ApiParam("编号") String pmNo, @ApiParam("名称") String name
            , @ApiParam("类型") String type, HttpServletResponse response, @ApiParam("颜色") String colour, @ApiParam("规格")
                                 String norms, @Param("材质") String material, @Param("称呼") String call) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        PmRequstPojo pmRequstPojo = new PmRequstPojo();
        pmRequstPojo.setPmNo(pmNo);
        pmRequstPojo.setName(name);
        pmRequstPojo.setType(type);
        pmRequstPojo.setCallStr(call);
        pmRequstPojo.setColour(colour);
        pmRequstPojo.setMaterial(material);
        pmRequstPojo.setNorms(norms);
        List<Pm> pms = pmService.findList(pmRequstPojo);


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
        int j = 0;
        header.createCell(j++).setCellValue("物料编号");
        header.createCell(j++).setCellValue("物料名称");
        header.createCell(j++).setCellValue("类别");
        header.createCell(j++).setCellValue("物料单位");
        header.createCell(j++).setCellValue("备注");
        header.createCell(j++).setCellValue("添加人");
        header.createCell(j++).setCellValue("添加日期");
        Pm pm = null;
        if (pms != null && pms.size() >= 1) {
            for (int i = 0; i < pms.size(); i++) {
                pm = pms.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(pm.getPmNo());
                row.createCell(j++).setCellValue(pm.getName());
                row.createCell(j++).setCellValue(pm.getType());
                row.createCell(j++).setCellValue(pm.getUnit());
                row.createCell(j++).setCellValue(pm.getRemark());
                row.createCell(j++).setCellValue(pm.getOperator());
                row.createCell(j++).setCellValue(DateUtil.dateToString(pm.getCreateTime(), DateUtil.ymdFormat));
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("物料明细.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("物料导出excel异常");
        }
    }


    @RequestMapping(value = "/exportTemplate", method = RequestMethod.GET)
    public void exportTemplate(HttpServletRequest request, HttpServletResponse response) {
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
        int j = 0;
        header.createCell(j++).setCellValue("物料编号");
        header.createCell(j++).setCellValue("颜色");
        header.createCell(j++).setCellValue("规格");
        header.createCell(j++).setCellValue("材质");
        header.createCell(j++).setCellValue("称呼");
        header.createCell(j++).setCellValue("类别");
        header.createCell(j++).setCellValue("单位");
        header.createCell(j++).setCellValue("备注");

        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("物料明细模板.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("物料明细模板异常");
        }
    }
}


