package com.amir.controller.productionorder.vo;

import com.amir.controller.order.vo.WorkShopVo;
import com.amir.model.order.OrderProduct;
import com.amir.model.order.ProductionOrder;
import com.amir.model.order.ProductionProcedure;
import com.amir.model.product.ProductProcedure;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by liuyq on 2019/8/9.
 */
public class ProductionOrderDetailVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String orderNo;

    private String productionNo;

    private String customerName;

    private String productNo;

    private Integer assignNum;

    private String unit;

    private Integer num;

    private Integer maxNum;

    private String productType;

    private String workshop;

    private String workshopDirector;

    private Integer luoNum;

    private Integer isLuo;

    private Integer printCount;

    private String printTime;

    private String codeUrl;

    private String printer;

    private List<WorkShopVo.Procedure> procedures;

    public ProductionOrderDetailVo() {
    }

    public ProductionOrderDetailVo(ProductionOrder productionOrder,
                                   List<ProductionProcedure> productionProcedures,
                                   Map<Integer, ProductProcedure> productProcedureMap,
                                   OrderProduct orderProduct) {
        if (productionOrder != null) {
            this.orderNo = productionOrder.getOrderNo();
            this.assignNum = productionOrder.getAssignNum();
            this.codeUrl = productionOrder.getCodeUrl();
            this.isLuo = productionOrder.getIsLuo();
            this.luoNum = productionOrder.getLuoNum();
            this.maxNum = productionOrder.getMaxNum();
            this.printCount = productionOrder.getPrintCount() == 0 ? 1 : productionOrder.getPrintCount() + 1;
            this.workshop = productionOrder.getWorkshop();
            this.workshopDirector = productionOrder.getWorkshopDirector();
            this.productNo = productionOrder.getProductNo();
            this.productionNo = productionOrder.getProductionNo();
            this.codeUrl = productionOrder.getCodeUrl();
        }

        if (!CollectionUtils.isEmpty(productionProcedures)) {
            this.procedures = WorkShopVo.Procedure.productionProcedureTransfer(productionProcedures, productProcedureMap);
        }
        if (orderProduct != null) {
            this.unit = orderProduct.getUnit();
            this.customerName = orderProduct.getCustomerName();
            this.productType = orderProduct.getProductType();
            this.num = orderProduct.getNum();
        }

    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductNo() {
        return productNo;
    }

    public void setProductNo(String productNo) {
        this.productNo = productNo;
    }

    public Integer getAssignNum() {
        return assignNum;
    }

    public void setAssignNum(Integer assignNum) {
        this.assignNum = assignNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public String getWorkshopDirector() {
        return workshopDirector;
    }

    public void setWorkshopDirector(String workshopDirector) {
        this.workshopDirector = workshopDirector;
    }

    public List<WorkShopVo.Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<WorkShopVo.Procedure> procedures) {
        this.procedures = procedures;
    }

    public Integer getLuoNum() {
        return luoNum;
    }

    public void setLuoNum(Integer luoNum) {
        this.luoNum = luoNum;
    }

    public Integer getIsLuo() {
        return isLuo;
    }

    public void setIsLuo(Integer isLuo) {
        this.isLuo = isLuo;
    }

    public Integer getPrintCount() {
        return printCount;
    }

    public void setPrintCount(Integer printCount) {
        this.printCount = printCount;
    }

    public String getPrintTime() {
        return printTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

    public String getPrinter() {
        return printer;
    }

    public void setPrinter(String printer) {
        this.printer = printer;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
