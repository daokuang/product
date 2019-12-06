package com.btjf.controller.productionorder.vo;

import com.btjf.controller.order.vo.WorkShopVo;

import java.io.Serializable;
import java.util.List;

public class BatchAssignVo implements Serializable {

    private static final long serialVersionUID = -4001637681051002224L;
    private String workshop;//车间

    private String workshopDirector;

    private Integer printCount;

    private String printTime;

    private String codeUrl;

    private String printer;

    private String productionNo;

    private List<BatchAssignOrder> batchAssignOrders;


    public static class BatchAssignOrder {
        private String orderNo;

        private String productNo;

        private List<WorkShopVo.Procedure> procedures;

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getProductNo() {
            return productNo;
        }

        public void setProductNo(String productNo) {
            this.productNo = productNo;
        }

        public List<WorkShopVo.Procedure> getProcedures() {
            return procedures;
        }

        public void setProcedures(List<WorkShopVo.Procedure> procedures) {
            this.procedures = procedures;
        }
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

    public List<BatchAssignOrder> getBatchAssignOrders() {
        return batchAssignOrders;
    }

    public void setBatchAssignOrders(List<BatchAssignOrder> batchAssignOrders) {
        this.batchAssignOrders = batchAssignOrders;
    }

    public String getProductionNo() {
        return productionNo;
    }

    public void setProductionNo(String productionNo) {
        this.productionNo = productionNo;
    }
}
