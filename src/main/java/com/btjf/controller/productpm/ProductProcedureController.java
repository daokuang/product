package com.btjf.controller.productpm;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.application.components.xaresult.AppXaResultHelper;
import com.btjf.application.util.XaResult;
import com.btjf.common.page.Page;
import com.btjf.constant.WorkShopProductionMapEnum;
import com.btjf.controller.base.ProductBaseController;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.model.product.Product;
import com.btjf.model.product.ProductProcedure;
import com.btjf.model.sys.SysUser;
import com.btjf.service.order.ProductionProcedureService;
import com.btjf.service.productpm.ProductProcedureService;
import com.btjf.service.productpm.ProductService;
import com.google.common.collect.Lists;
import com.heige.aikajinrong.base.exception.BusinessException;
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
 * Created by liuyq on 2019/8/5.
 * <p>
 * 产品 工序 价格
 */
@RestController
@RequestMapping(value = "/productpmprocedure")
public class ProductProcedureController extends ProductBaseController {
    private static final Logger LOGGER = Logger
            .getLogger(ProductProcedureController.class);

    @Resource
    private ProductProcedureService productProcedureService;
    @Resource
    private ProductService productProcedure;
    @Resource
    private ProductionProcedureService productionProcedureService;


    @RequestMapping(value = "/updateOrAdd", method = RequestMethod.POST)
    public XaResult<Integer> add(Integer id, String productNo, Double price, String procedureName, Integer sort) throws BusinessException {
        SysUser sysUser = getLoginUser();

        if (productNo == null || price == null || procedureName == null || sort == null)
            return XaResult.error("请填写完整信息");


        Product product = productProcedure.getByNO(productNo);
        if (null == product) {
            return XaResult.error("型号不存在");
        }

        if (null == WorkShopProductionMapEnum.get(sort)) return XaResult.error("请填写正确的序号(序号对应车间)");

        ProductProcedure productProcedure = new ProductProcedure();
        productProcedure.setCreateTime(new Date());
        productProcedure.setProductId(product.getId());
        productProcedure.setProductNo(productNo);
        productProcedure.setPrice(BigDecimal.valueOf(price));
        productProcedure.setIsDelete(0);
        productProcedure.setSort(sort);
        productProcedure.setLastModifyTime(new Date());
        productProcedure.setProcedureName(procedureName);
        productProcedure.setOperator(sysUser.getUserName());
        productProcedure.setId(id);

        return XaResult.success(productProcedureService.addOrUpdate(productProcedure, sysUser.getUserName()));
    }

    @RequestMapping(value = "getByWorkShopAndProductNo", method = RequestMethod.GET)
    public XaResult<List<WorkShopVo.Procedure>> getByWorkShopAndProductNo(String workShop, String productNo, String orderNo) {
        if (workShop == null || productNo == null) return XaResult.error("参数输入有误");


        List<WorkShopVo.Procedure> procedures = productProcedureService.getByWorkShopAndProductNo(workShop, productNo);
        if (!StringUtils.isEmpty(orderNo) && !CollectionUtils.isEmpty(procedures)) {
            procedures.stream().forEach(t -> {
                t.setNum(productionProcedureService.procedureCanAssignNum(orderNo, productNo, t.getProcedureId()));
            });
        }

        return XaResult.success(procedures);
    }

    @RequestMapping(value = "list", method = RequestMethod.GET)
    public XaResult<List<ProductProcedure>> list(String productNo, String procedureName, String price, Integer pageSize, Integer currentPage, Integer isConfirm) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<ProductProcedure> listPage = productProcedureService.listPage(productNo, procedureName, price, isConfirm, page);
        XaResult<List<ProductProcedure>> result = AppXaResultHelper.success(listPage, listPage.getRows());
        return result;
    }

    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    public XaResult<Integer> confirm(String[] ids) {
        LOGGER.info(getRequestParamsAndUrl());

        if (null == ids || Arrays.asList(ids).size() <= 0) {
            return XaResult.error("请选择要确认的记录");
        }
        List<Integer> integers = Lists.newArrayList();
        Arrays.asList(ids).stream().forEach(t -> {
            integers.add(new Integer(t));
        });
        Integer row = productProcedureService.updateConfirmStatue(integers);
        return XaResult.success(row);
    }

    @RequestMapping(value = "detail", method = RequestMethod.GET)
    public XaResult<ProductProcedure> detail(Integer id) {
        if (id == null) return XaResult.error("id必传");

        ProductProcedure productProcedure = productProcedureService.getById(id);
        return XaResult.success(productProcedure);
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(String productNo, String procedureName, String price, Integer isConfirm, HttpServletResponse response) {

        List<ProductProcedure> productProcedures = productProcedureService.list(productNo, procedureName, price, isConfirm);

        Workbook wb = new XSSFWorkbook();
        Sheet sheet = wb.createSheet();
        Row header = sheet.createRow(0);

        sheet.setColumnWidth(0, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(1, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(2, (int) ((10 + 0.72) * 256));
        sheet.setColumnWidth(3, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((20 + 0.72) * 256));
        sheet.setColumnWidth(4, (int) ((20 + 0.72) * 256));
        int j = 0;
        header.createCell(j++).setCellValue("序号");
        header.createCell(j++).setCellValue("型号");
        header.createCell(j++).setCellValue("车间名称");
        header.createCell(j++).setCellValue("工序名称");
        header.createCell(j++).setCellValue("单价");
        header.createCell(j++).setCellValue("型号合计");
        header.createCell(j++).setCellValue("确认状态");
        ProductProcedure productProcedure = null;
        if (productProcedures != null && productProcedures.size() >= 1) {
            for (int i = 0; i < productProcedures.size(); i++) {
                productProcedure = productProcedures.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(productProcedure.getSort());
                row.createCell(j++).setCellValue(productProcedure.getProductNo());
                row.createCell(j++).setCellValue(productProcedure.getWorkshop());
                row.createCell(j++).setCellValue(productProcedure.getProcedureName());
                row.createCell(j++).setCellValue(productProcedure.getPrice() + "元");
                row.createCell(j++).setCellValue(productProcedure.getSumPrice() + "元");
                row.createCell(j++).setCellValue(productProcedure.getIsConfirm() == 1 ? "已确认" : "未确认");

            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("工序单价.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("工序单价excel异常");
        }
    }

    @RequestMapping(value = "sameProductNoAdd", method = RequestMethod.POST)
    public XaResult<Integer> sameProductNoAdd(String oldProductNo, String newProductNo) throws BusinessException {
        SysUser sysUser = getLoginUser();

        if (StringUtils.isEmpty(oldProductNo) || StringUtils.isEmpty(newProductNo)) {
            return XaResult.error("输入参数不完整");
        }
        if (null == productProcedure.getByNO(oldProductNo)) {
            return XaResult.error("已有型号不存在");
        }
        if (null == productProcedure.getByNO(newProductNo)) {
            return XaResult.error("新型号不存在");
        }

        Integer integer = productProcedureService.sameProductNoAdd(oldProductNo, newProductNo, sysUser);
        return XaResult.success(integer);
    }
}
