package com.btjf.controller;

import com.btjf.model.product.ProductProcedure;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.service.order.ProductionProcedureService;
import com.btjf.service.productpm.ProductProcedureService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.heige.aikajinrong.base.exception.BusinessException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import scala.collection.parallel.ParIterableLike;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Auther: liuyuqing
 * @Date: 2019-11-30 14:03
 * @Description:
 */
@RestController
@RequestMapping("backDoor")
public class BackDoorController {

    @Resource
    private ProductProcedureService productProcedureService;

    @Resource
    private ProductWorkshopService productWorkshopService;

    /**
     * 删除重复工序
     */
    @RequestMapping("deleteProducton")
    public void deleteProducton() {
        List<ProductProcedureWorkshop> productProcedureWorkshopList = productWorkshopService.getNeetDelete();
        productProcedureWorkshopList.forEach(t -> {
            if (t.getNum() > 1) {
                ProductProcedureWorkshop productProcedureWorkshop = productWorkshopService.getByWorkShopAndProductNoAndName(t.getWorkshop(), t.getProductNo(), t.getProcedureName());
                try {
                    productWorkshopService.deleteById(productProcedureWorkshop.getId());
                } catch (BusinessException e) {
                    e.printStackTrace();
                }
                ProductProcedure productProcedure = productProcedureService.getById(productProcedureWorkshop.getProcedureId());
                productProcedure.setIsDelete(1);
                productProcedure.setLastModifyTime(new Date());
                productProcedureService.update(productProcedure);

            }
        });
    }
}
