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
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.controller.order.vo.OrderVo;
import com.btjf.controller.order.vo.ProcessDetail;
import com.btjf.model.order.OrderProduct;
import com.btjf.service.order.OrderProductService;
import com.btjf.service.order.ProductionProcedureConfirmService;
import com.btjf.service.productpm.ProductProcedureService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.btjf.util.BigDecimalUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Comparator;

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

    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;

    @Resource
    private OrderProductService orderProductService;


    @RequestMapping(value = "/workShopNum", method = RequestMethod.GET)
    public void workShopNum() {
        List<OrderVo> orderVos = orderProductService.list(null, null, null, null, null, null, null, null);

        new Thread(() -> {
            if (!CollectionUtils.isEmpty(orderVos)) {
                orderVos.stream().forEach(orderVo -> {
                    OrderProduct orderProduct = new OrderProduct();
                    orderProduct.setId(orderVo.getId());
                    orderProduct.setBlanking(BigDecimal.valueOf(BigDecimalUtil.div(productionProcedureConfirmService.getHandleNum(orderVo.getOrderNo(), "裁外壳",
                            orderVo.getProductNo()), Double.valueOf(orderVo.getMaxNum())) * 100));
                    Integer fm = productionProcedureConfirmService.getHandleNum(orderVo.getOrderNo(), "复面",
                            orderVo.getProductNo());
                    Integer fma = productionProcedureConfirmService.getHandleNum(orderVo.getOrderNo(), "复面A",
                            orderVo.getProductNo());
                    orderProduct.setFrontFm(BigDecimal.valueOf(BigDecimalUtil.div(fma > fm ? fma : fm, Double.valueOf(orderVo.getMaxNum())) * 100));
                    orderProduct.setFrontCheck(BigDecimal.valueOf(BigDecimalUtil.div(productionProcedureConfirmService.getHandleNum(orderVo.getOrderNo(), "一车间质检",
                            orderVo.getProductNo()), Double.valueOf(orderVo.getMaxNum())) * 100));

                    List<ProcessDetail> processDetails = productionProcedureConfirmService.getCompleteNum("后道-大辅工", orderVo.getOrderNo(), orderVo.getProductNo());
                    orderProduct.setBackBigAssist(BigDecimal.valueOf((double) (CollectionUtils.isEmpty(processDetails) ? 0 : processDetails.stream().max(Comparator.comparingInt(ProcessDetail::getNum)).get().getNum())));


                    List<ProcessDetail> processDetails2 = productionProcedureConfirmService.getCompleteNum("后道-中辅工", orderVo.getOrderNo(), orderVo.getProductNo());
                    orderProduct.setBackCenterAssist(BigDecimal.valueOf((double) (CollectionUtils.isEmpty(processDetails2) ? 0 : processDetails2.stream().max(Comparator.comparingInt(ProcessDetail::getNum)).get().getNum())));

                    orderProduct.setInspection(BigDecimal.valueOf(BigDecimalUtil.div(productionProcedureConfirmService.getHandleNum(orderVo.getOrderNo(), "成品验收",
                            orderVo.getProductNo()), Double.valueOf(orderVo.getMaxNum())) * 100));
                    orderProduct.setLastModifyTime(new Date());
                    orderProductService.update(orderProduct);
                });
            }
        }).start();
    }
  
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
