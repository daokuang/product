package com.amir.vo;

import com.amir.model.order.OrderProduct;
import com.amir.model.pm.PmOutBill;
import com.amir.model.pm.PmOutBillDetail;
import com.amir.model.product.ProductPm;
import com.amir.util.DateUtil;

import java.util.ArrayList;
import java.util.List;

public class PmOutBillDetailVo {

    private Integer id;//领料单ID
    private String orderNo;
    private String productNo;
    private String billNo;
    private String groupNmae;//领用组
    private String pmType;//材料类别
    private String pmCheckItem;//检测项目
    private String operator;//开票人
    private Integer num;//订单总数
    private String billDate;
    private String workshop;//车间
    private Integer assignedNum;//分配数
    private Integer maxNum;//上限数
    private String url;//二维码地址
    private List<BillPmVo> list;

    public PmOutBillDetailVo() {
    }

    public PmOutBillDetailVo(PmOutBill pmOutBill, OrderProduct orderProduct, List<PmOutBillDetail> plist,
                             List<ProductPm> pplist) {
        this.id = pmOutBill.getId();
        this.orderNo = pmOutBill.getOrderNo();
        this.productNo = pmOutBill.getProductNo();
        this.billNo = pmOutBill.getBillNo();
        this.groupNmae = pmOutBill.getGroupNmae();
        this.pmType = pmOutBill.getPmType();
        this.pmCheckItem = pmOutBill.getPmCheckItem();
        this.operator = pmOutBill.getOperator();
        this.workshop = pmOutBill.getWorkshop();
        this.num = orderProduct.getNum();
        this.assignedNum = pmOutBill.getDistributionNum();
        this.maxNum = orderProduct.getMaxNum();
        this.url = "/wx/work/getConfirmList?orderId=" + orderProduct.getId() + "&orderNo=" + orderProduct.getOrderNo()
                + "&productNo=" + orderProduct.getProductNo() + "&billNo=" + pmOutBill.getBillNo();
        this.billDate = DateUtil.dateToString(pmOutBill.getCreateTime(), DateUtil.ymdFormat);
        if (plist != null && plist.size() > 0) {
            this.list = new ArrayList<>();
            for (int i = 0; i < plist.size(); i++) {
                for (ProductPm pm : pplist) {
                    if (pm.getPmNo().equals(plist.get(i).getPmNo())) {
                        BillPmVo vo = new BillPmVo(plist.get(i), maxNum, pm);
                        this.list.add(vo);
                    }
                }
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public String getBillNo() {
        return billNo;
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo;
    }

    public String getGroupNmae() {
        return groupNmae;
    }

    public void setGroupNmae(String groupNmae) {
        this.groupNmae = groupNmae;
    }

    public String getPmType() {
        return pmType;
    }

    public void setPmType(String pmType) {
        this.pmType = pmType;
    }

    public String getPmCheckItem() {
        return pmCheckItem;
    }

    public void setPmCheckItem(String pmCheckItem) {
        this.pmCheckItem = pmCheckItem;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBillDate() {
        return billDate;
    }

    public void setBillDate(String billDate) {
        this.billDate = billDate;
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public Integer getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(Integer maxNum) {
        this.maxNum = maxNum;
    }

    public List<BillPmVo> getList() {
        return list;
    }

    public void setList(List<BillPmVo> list) {
        this.list = list;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getAssignedNum() {
        return assignedNum;
    }

    public void setAssignedNum(Integer assignedNum) {
        this.assignedNum = assignedNum;
    }
}
