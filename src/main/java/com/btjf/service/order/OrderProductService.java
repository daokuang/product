package com.btjf.service.order;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.common.page.Page;
import com.btjf.constant.WorkShopProductionMapEnum;
import com.btjf.controller.emp.vo.MapVo;
import com.btjf.controller.order.vo.OrderVo;
import com.btjf.controller.order.vo.ProcessDetail;
import com.btjf.mapper.order.OrderProductMapper;
import com.btjf.model.order.Order;
import com.btjf.model.order.OrderProduct;
import com.btjf.model.product.Product;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.model.sys.Sysdept;
import com.btjf.service.productpm.ProductService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.btjf.service.sys.SysDeptService;
import com.btjf.util.BigDecimalUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Created by liuyq on 2019/8/4.
 */
@Transactional(readOnly = false, rollbackFor = Exception.class)
@Service
public class OrderProductService {

    @Resource
    private OrderProductMapper orderProductMapper;

    @Resource
    private ProductService productService;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private ProductWorkshopService productWorkshopService;

    @Resource
    private ProductionProcedureConfirmService productionProcedureConfirmService;

    @Resource
    private OrderService orderService;

    public OrderProduct getByOrderNoAndProductNo(String orderNo, String productNo) {
        return orderProductMapper.getByOrderNoAndProductNo(orderNo, productNo);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public Integer insert(OrderProduct orderProduct, String sysUser) {

        Integer orderID = null;
        Order order = orderService.getByNo(orderProduct.getOrderNo());
        if (null == order) {
            orderID = orderService.insert(new Order(orderProduct.getOrderNo(), new Date(), new Date(), 0));
        } else {
            orderID = order.getId();
        }

        Integer productId = null;
        //新增产品
        Product product = productService.getByNO(orderProduct.getProductNo());
        if (product == null) {
            Product productNew = new Product();
            productNew.setUnit(orderProduct.getUnit());
            productNew.setIsDelete(0);
            productNew.setName(orderProduct.getProductNo());
            productNew.setProductNo(orderProduct.getProductNo());
            productNew.setLastModifyTime(new Date());
            productNew.setCreateTime(new Date());
            productNew.setOperator(sysUser);
            productNew.setType(orderProduct.getProductType());
            productId = productService.add(productNew);
            //为所有车间添加质检工序
            List<Sysdept> sysdepts = sysDeptService.getWorkshopList();
            if (!CollectionUtils.isEmpty(sysdepts)) {
                for (Sysdept sysdept : sysdepts) {
                    if (null == sysdept) continue;
                    ProductProcedureWorkshop productProcedureWorkshop = new ProductProcedureWorkshop();
                    productProcedureWorkshop.setOperator(sysUser);
                    productProcedureWorkshop.setProductId(productId);
                    productProcedureWorkshop.setProductNo(orderProduct.getProductNo());
                    productProcedureWorkshop.setSort(WorkShopProductionMapEnum.getByName(sysdept.getDeptName()).getValue());
                    productProcedureWorkshop.setProcedureName(sysdept.getDeptName() + "质检");
                    productProcedureWorkshop.setWorkshop(sysdept.getDeptName());
                    productProcedureWorkshop.setPrice(BigDecimal.valueOf(0));
                    productProcedureWorkshop.setIsDelete(0);
                    productProcedureWorkshop.setCreateTime(new Date());
                    productProcedureWorkshop.setLastModifyTime(new Date());
                    productWorkshopService.add(productProcedureWorkshop);
                }
            }

        } else {
            product.setType(orderProduct.getPmType());
            product.setLastModifyTime(new Date());
            productId = productService.update(product);
        }

        orderProduct.setProductId(productId);
        orderProduct.setOrderId(orderID);
        orderProductMapper.insertSelective(orderProduct);
        return orderProduct.getId();
    }

    public Page<OrderVo> listPage(Integer customerId, String orderNo, String pmNo, String type, String completeStartDate, String completeStartEnd, String createStartDate, String createEndDate, Page page) {

        PageHelper.startPage(page.getPage(), page.getRp());
        List<OrderVo> orderVos = orderProductMapper.findList(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate);
        PageInfo pageInfo = new PageInfo(orderVos);
        pageInfo.setList(orderVos);
        return new Page<>(pageInfo);
    }

    public OrderProduct getByID(Integer ID) {
        return orderProductMapper.selectByPrimaryKey(ID);
    }

    public Integer deleteById(Integer Id) {
        return orderProductMapper.deleteById(Id);
    }

    public List<OrderProduct> findByOrderId(Integer orderId) {
        return orderProductMapper.findByOrderID(orderId);
    }

    public Integer update(OrderProduct orderProduct1) {
        if (null == orderProduct1) return 0;
        return orderProductMapper.updateByPrimaryKeySelective(orderProduct1);
    }

    public List<OrderVo> list(Integer customerId, String orderNo, String pmNo, String type, String completeStartDate, String completeStartEnd, String createStartDate, String createEndDate) {
        List<OrderVo> orderVos = orderProductMapper.findList(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate);
        return orderVos;
    }

    public Map<String, Integer> getCount(Integer customerId, String orderNo, String pmNo, String type, String completeStartDate, String completeStartEnd, String createStartDate, String createEndDate) {
        List<MapVo> vos = orderProductMapper.getCount(customerId, orderNo, pmNo, type, completeStartDate, completeStartEnd, createStartDate, createEndDate);
        Map map = new HashMap();
        vos.stream().filter(t -> t != null).forEach(t -> {
                    map.put(t.getType(), t.getNum());
                }
        );
        if (CollectionUtils.isEmpty(vos)) {
            map.put("生产数", 0);
        } else {
            map.put("生产数", vos.stream().map(MapVo::getNum).reduce((i, j) -> i + j).get());
        }
        return map;
    }

    public void workShopNum(String orderNo, String productNo) {
        OrderProduct orderVo = this.getByOrderNoAndProductNo(orderNo, productNo);
        if (orderVo == null) return;
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
        this.update(orderProduct);
    }
}
