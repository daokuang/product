package com.amir.controller.productpm;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.amir.constant.WorkShopProductionMapEnum;
import com.amir.controller.base.ProductBaseController;
import com.amir.controller.order.vo.WorkShopVo;
import com.amir.controller.productpm.vo.ProductWorkShopVo;
import com.amir.model.product.ProductProcedureWorkshop;
import com.amir.model.sys.SysUser;
import com.amir.service.productpm.ProductWorkshopService;
import com.btjf.application.components.xaresult.AppXaResultHelper;
import com.btjf.application.util.XaResult;
import com.btjf.common.page.Page;
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
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/8/5.
 * 型号工序车间
 */
@RestController
@RequestMapping(value = "/productworkshop")
public class ProductWorkshopController extends ProductBaseController {


    private static final Logger LOGGER = Logger
            .getLogger(ProductWorkshopController.class);

    @Resource
    private ProductWorkshopService productWorkshopService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public XaResult<List<ProductWorkShopVo>> getList(Integer pageSize, Integer currentPage, String type, String productNo) {

        getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());

        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page page = new Page(pageSize, currentPage);
        Page<ProductWorkShopVo> productWorkShopVoPage = productWorkshopService.getListPage(page, type, productNo);
        return AppXaResultHelper.success(productWorkShopVoPage, productWorkShopVoPage.getRows());
    }

    @RequestMapping(value = "/getByWorkshopName", method = RequestMethod.GET)
    public XaResult<List<WorkShopVo.Procedure>> getByWorkshopName(String workshopName, String productNo) throws BusinessException {
        if (StringUtils.isEmpty(workshopName)) return XaResult.success();
        List<WorkShopVo.Procedure> result = Lists.newArrayList();
        List<ProductProcedureWorkshop> productProcedureWorkshops = productWorkshopService.findByWorkshopNameAndProductNo(workshopName, productNo);
        if (!CollectionUtils.isEmpty(productProcedureWorkshops)) {
            for (ProductProcedureWorkshop productProcedureWorkshop : productProcedureWorkshops) {
                result.add(new WorkShopVo.Procedure(productProcedureWorkshop));
            }
            return XaResult.success(result);
        } else {
            return XaResult.success();
        }

    }

    @RequestMapping(value = "/getWorkShopByProductNo", method = RequestMethod.GET)
    public XaResult<List<ProductProcedureWorkshop>> getWorkShop(String productNo) {
        if (productNo == null) return XaResult.error("产品型号不能为空");

        return XaResult.success(productWorkshopService.getWorkShop(productNo, null));
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public XaResult<ProductProcedureWorkshop> detail(Integer id) throws BusinessException {
        if (id == null) return XaResult.error("id 不能为null");

        ProductProcedureWorkshop productProcedureWorkshop = productWorkshopService.getById(id);
        if (null == productProcedureWorkshop) return XaResult.error("该型号的工序不存在");
        productProcedureWorkshop.setIsInspect("质检".equals(productProcedureWorkshop.getProcedureName()) ? true : false);
        return XaResult.success(productProcedureWorkshop);
    }

    @RequestMapping(value = "updateOrAdd", method = RequestMethod.POST)
    public XaResult<Integer> updateOrAdd(Integer id, String workShop, String procedureName, Double price, Integer sort, String productNo) throws BusinessException {

        SysUser sysUser = getLoginUser();
        LOGGER.info(getRequestParamsAndUrl());
        if (StringUtils.isEmpty(workShop) || StringUtils.isEmpty(procedureName) || price == null || null == sort || StringUtils.isEmpty(productNo)) {
            return XaResult.error("请输入完整信息");
        }

        if (null == WorkShopProductionMapEnum.get(sort)) return XaResult.error("请填写正确的序号(序号对应车间)");
        ProductProcedureWorkshop productProcedureWorkshop = new ProductProcedureWorkshop();
        productProcedureWorkshop.setWorkshop(workShop);
        productProcedureWorkshop.setProcedureName(procedureName);
        productProcedureWorkshop.setPrice(BigDecimal.valueOf(price));
        productProcedureWorkshop.setSort(sort);
        productProcedureWorkshop.setId(id);
        productProcedureWorkshop.setProductNo(productNo);
        productProcedureWorkshop.setOperator(sysUser.getUserName());
        if (id == null) { //新增
            productProcedureWorkshop.setCreateTime(new Date());
            productProcedureWorkshop.setLastModifyTime(new Date());
            productProcedureWorkshop.setIsDelete(0);
            ProductProcedureWorkshop productProcedureWorkshop1 = productWorkshopService.getByWorkShopAndProductNoAndName(productProcedureWorkshop.getWorkshop(), productProcedureWorkshop.getProductNo(), productProcedureWorkshop.getProcedureName());
            if (productProcedureWorkshop1 != null) {
                return XaResult.error(productProcedureWorkshop1.getWorkshop() + "已经存在该工序");
            }

            id = productWorkshopService.add(productProcedureWorkshop);
        } else {
            id = productWorkshopService.udpate(productProcedureWorkshop);
        }
        return XaResult.success(id);
    }

    @RequestMapping(value = "deleteById", method = RequestMethod.POST)
    public XaResult<Integer> delete(Integer id) throws BusinessException {
        if (id == null) return XaResult.error("id 不能为null");

        return XaResult.success(productWorkshopService.deleteById(id));
    }


    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void export(HttpServletResponse response, String type, String productNo) {
        LOGGER.info(getRequestParamsAndUrl());
        List<ProductWorkShopVo> productionOrderVos = productWorkshopService.getList(type, productNo);
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
        sheet.setColumnWidth(10, (int) ((15 + 0.72) * 256));
        int j = 0;


        header.createCell(j++).setCellValue("产品型号");
        header.createCell(j++).setCellValue("产品类别");
        header.createCell(j++).setCellValue("下料车间");
        header.createCell(j++).setCellValue("外协");
        header.createCell(j++).setCellValue("一车间");
        header.createCell(j++).setCellValue("后道车间-车工");
        header.createCell(j++).setCellValue("后道车间-中辅工");
        header.createCell(j++).setCellValue("后道车间-大辅工");
        header.createCell(j++).setCellValue("外协质检");
        header.createCell(j++).setCellValue("包装车间");
        ProductWorkShopVo productWorkShopVo = null;
        if (productionOrderVos != null && productionOrderVos.size() >= 1) {
            for (int i = 0; i < productionOrderVos.size(); i++) {
                productWorkShopVo = productionOrderVos.get(i);
                Row row = sheet.createRow(i + 1);
                j = 0;
                row.createCell(j++).setCellValue(productWorkShopVo.getProductNo());
                row.createCell(j++).setCellValue(productWorkShopVo.getType());
                row.createCell(j++).setCellValue(productWorkShopVo.getBlanking() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getAssist() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getGroundFloor() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getBackAssist() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getBackCenterAssist() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getBackBigAssist() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getInspection() + "道工序");
                row.createCell(j++).setCellValue(productWorkShopVo.getPacking() + "道工序");
            }
        }
        try {
            sheet.setForceFormulaRecalculation(true);
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode("型号工序.xlsx", "UTF-8"));
            response.setDateHeader("Expires", 0);
            response.setHeader("Connection", "close");
            response.setHeader("Content-Type", "application/vnd.ms-excel");
            wb.write(response.getOutputStream());
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("型号工序导出excel异常");
        }
    }
}
