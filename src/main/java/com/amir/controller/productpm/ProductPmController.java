package com.amir.controller.productpm;

import com.alibaba.druid.util.StringUtils;
import com.amir.controller.base.ProductBaseController;
import com.amir.model.AppXaResultHelper;
import com.amir.model.Page;
import com.amir.model.XaResult;
import com.amir.model.pm.Pm;
import com.amir.model.product.ProductPm;
import com.amir.model.sys.SysUser;
import com.amir.service.dictionary.DictionaryService;
import com.amir.service.pm.PmService;
import com.amir.service.productpm.ProductPmService;
import com.amir.util.BigDecimalUtil;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiParam;
import org.apache.commons.lang.math.NumberUtils;
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
 * Created by liuyq on 2019/7/29.
 */
@RestController
@RequestMapping(value = "/productpm")
@Api(value = "ProductPmController", description = "耗料管理", position = 1)
public class ProductPmController extends ProductBaseController {

    private static final Logger LOGGER = Logger
            .getLogger(ProductPmController.class);

    @Resource
    private ProductPmService productPmService;

    @Resource
    private PmService pmService;

    @Resource
    private DictionaryService dictionaryService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<ProductPm>> findList(@ApiParam("编号") String productNo, @ApiParam("物料编号") String pmNo
            , @ApiParam("1已确认  0 未确认") Integer status, Integer pageSize, Integer currentPage) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<ProductPm> listPage = productPmService.findListPage(productNo, pmNo, status, page);
        XaResult<List<ProductPm>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    @RequestMapping(value = "/modelConfige", method = RequestMethod.POST)
    public XaResult<Integer> modelConfige(String[] ids) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (null == ids || Arrays.asList(ids).size() <= 0) {
            return XaResult.error("请选择要确认的记录");
        }
        List<Integer> integers = Lists.newArrayList();
        Arrays.asList(ids).stream().forEach(t -> {
            integers.add(new Integer(t));
        });
        Integer row = productPmService.updateStatue(integers);
        return XaResult.success(row);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public XaResult<Integer> delete(Integer[] ids) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (null == ids || Arrays.asList(ids).size() <= 0) {
            return XaResult.error("请选择要删除的记录");
        }
        List<Integer> integers = Lists.newArrayList();
        Arrays.asList(ids).stream().forEach(t -> {
            integers.add(new Integer(t));
        });
        Integer row = productPmService.delete(integers);
        return XaResult.success(row);
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<ProductPm> detail(Integer id) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (id == null) {
            return XaResult.error("请选择要查看的记录");
        }
        ProductPm productpm = productPmService.getByID(id);
        if (null == productpm) {
            return XaResult.error("查看的记录不存在");
        }
        return XaResult.success(productpm);
    }


    @RequestMapping(value = "/addOrUpdate", method = RequestMethod.POST)
    public XaResult<Integer> updateOrAdd(Integer id, String productNo, String pmNo, String num,
                                         String unit, String type, String remark, Integer status, String sequence) {
        SysUser sysUser = getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        Pm pm = pmService.getByNo(pmNo);
        if (null == pm) {
            return XaResult.error("物料编号填写错误，请修改");
        }

        if (!StringUtils.isNumber(num)) {
            return XaResult.error("数量填写有误！");
        }

        if (!NumberUtils.isDigits(sequence)) {
            return XaResult.error("序号填写有误！");
        }
        ProductPm productPm = new ProductPm();
        productPm.setIsDelete(0);
        productPm.setLastModifyTime(new Date());
        productPm.setStatus(status);
        productPm.setOperator(sysUser.getUserName());
        productPm.setProductNo(productNo);
        productPm.setPmName(pm.getName());
        productPm.setPmId(pm.getId());
        productPm.setPmNo(pm.getPmNo());
        productPm.setCreateTime(new Date());
        if (null == dictionaryService.getListByNameAndType(unit, 2)) {
            return XaResult.error("单位填写错误");
        }
        productPm.setUnit(unit);
        productPm.setType(type);
        productPm.setRemark(remark);
        productPm.setNum(BigDecimalUtil.getBigDecimal(num));
        productPm.setUnitNum(BigDecimal.valueOf(BigDecimalUtil.div(1d, productPm.getNum().doubleValue())));
        productPm.setSequence(new Integer(sequence));

        if (null != status) {
            if (status != 0 && status != 1) {
                return XaResult.error("是否确认类型错误");
            }
        } else {
            productPm.setStatus(0);
        }

        if (id != null) {
            productPm.setId(id);
            productPmService.update(productPm);
        } else {
            if (null != productPmService.getByNoAndPmNo(productNo, pmNo)) {
                return XaResult.error("该型号已经存在该物料");
            }
            id = productPmService.add(productPm);
        }
        return XaResult.success(id);
    }


    /**
     * 相同型号新增
     */
    @RequestMapping(value = "updateSameModel", method = RequestMethod.POST)
    public XaResult<Integer> updateSameModel(@ApiParam("oldModel") String oldModel, @ApiParam("newModel") String newModel) {
        if (StringUtils.isEmpty(oldModel) || StringUtils.isEmpty(newModel)) {
            return XaResult.error("参数输入有误");
        }
        Integer rows = productPmService.addBySameModel(oldModel, newModel);
        return XaResult.success(rows);
    }

    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportPm(@ApiParam("编号") String productNo, @ApiParam("名称") String pmNo
            , @ApiParam("1已确认  0 未确认") Integer status, HttpServletResponse response) {
        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        List<ProductPm> pms = productPmService.findList(productNo, pmNo, status);


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
        sheet.setColumnWidth(7, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(8, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(9, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(10, (int) ((20 + 0.72) * 256));
        int j = 0;
        header.createCell(j++).setCellValue("序号");
        header.createCell(j++).setCellValue("型号");
        header.createCell(j++).setCellValue("物料编号");
        header.createCell(j++).setCellValue("物料名称");
        header.createCell(j++).setCellValue("数量(耗料/双)");
        header.createCell(j++).setCellValue("单位");
        header.createCell(j++).setCellValue("数量(双/单位)");
        header.createCell(j++).setCellValue("单位");
        header.createCell(j++).setCellValue("类别");
        header.createCell(j++).setCellValue("备注");
        header.createCell(j++).setCellValue("确认状态");
        ProductPm pm = null;
        if (pms != null && pms.size() >= 1) {
            for (int i = 0; i < pms.size(); i++) {
                pm = pms.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(pm.getSequence());
                row.createCell(j++).setCellValue(pm.getProductNo());
                row.createCell(j++).setCellValue(pm.getPmNo());
                row.createCell(j++).setCellValue(pm.getPmName());
                row.createCell(j++).setCellValue(pm.getNum().toString());
                row.createCell(j++).setCellValue(pm.getUnit());
                row.createCell(j++).setCellValue(pm.getUnitNum().toString());
                row.createCell(j++).setCellValue(pm.getUnit());
                row.createCell(j++).setCellValue(pm.getType());
                row.createCell(j++).setCellValue(pm.getRemark());
                row.createCell(j++).setCellValue(pm.getStatus() == 1 ? "已确认" : "未确认");
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("型号耗料管理.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("型号耗料管理导出excel异常");
        }
    }
}
