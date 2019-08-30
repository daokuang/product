package com.btjf.service.productpm;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.common.page.Page;
import com.btjf.common.utils.BeanUtil;
import com.btjf.constant.WorkShopProductionMapEnum;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.controller.productpm.vo.ProductWorkShopVo;
import com.btjf.mapper.product.ProductMapper;
import com.btjf.mapper.product.ProductProcedureWorkshopMapper;
import com.btjf.model.product.Product;
import com.btjf.model.product.ProductProcedure;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import com.heige.aikajinrong.base.exception.BusinessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/8/5.
 * 产品工序车间
 */
@Service
public class ProductWorkshopService {

    @Resource
    private ProductProcedureWorkshopMapper productProcedureWorkshopMapper;
    @Resource
    private ProductMapper productMapper;
    @Resource
    private ProductProcedureService productProcedureService;

    public Page<ProductWorkShopVo> getListPage(Page page, String type, String productNo) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<Product> pmList = productMapper.getList(type, productNo);
        List<ProductWorkShopVo> productWorkShopVos = build(pmList);
        PageInfo pageInfo = new PageInfo(productWorkShopVos);
        pageInfo.setList(productWorkShopVos);

        return new Page<>(pageInfo);
    }

    public List<ProductWorkShopVo> getList(String type, String productNo) {
        List<Product> pmList = productMapper.getList(type, productNo);
        List<ProductWorkShopVo> productWorkShopVos = build(pmList);
        return productWorkShopVos;
    }


    public List<ProductProcedureWorkshop> findByWorkshopName(String name) {
        if (StringUtils.isEmpty(name)) return null;

        return productProcedureWorkshopMapper.findByWorkshopName(name);
    }

    public List<ProductProcedureWorkshop> getWorkShop(String productNo) {
        return productProcedureWorkshopMapper.getWorkShop(productNo, null);
    }

    public ProductProcedureWorkshop getById(Integer id) {
        return productProcedureWorkshopMapper.selectByPrimaryKey(id);
    }

    public List<ProductWorkShopVo> build(List<Product> pmList) {
        List<ProductWorkShopVo> productWorkShopVos = Lists.newArrayList();
        if (!CollectionUtils.isEmpty(pmList)) {
            for (Product t : pmList) {
                ProductWorkShopVo productWorkShopVo = new ProductWorkShopVo(t);
                productWorkShopVo.setAssist(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("外协质检", t.getProductNo()));
                productWorkShopVo.setBlanking(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("下料车间", t.getProductNo()));
                productWorkShopVo.setGroundFloor(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("一车间", t.getProductNo()));
                productWorkShopVo.setBackAssist(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("后道车间-车工", t.getProductNo()));
                productWorkShopVo.setBackCenterAssist(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("后道车间-中辅工", t.getProductNo()));
                productWorkShopVo.setBackBigAssist(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("后道车间-大辅工", t.getProductNo()));
                productWorkShopVo.setInspection(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("质检部-成品质检", t.getProductNo()));
                productWorkShopVo.setPacking(productProcedureWorkshopMapper.getNumByWorkShopNameAndPID("包装车间", t.getProductNo()));
                productWorkShopVo.setProductNo(t.getProductNo());
                productWorkShopVo.setType(t.getType());
                productWorkShopVos.add(productWorkShopVo);
            }
        }
        return productWorkShopVos;
    }

    public Integer add(ProductProcedureWorkshop productProcedureWorkshop) {
        ProductProcedure productProcedure = new ProductProcedure();
        productProcedure.setCreateTime(new Date());
        productProcedure.setIsDelete(0);
        productProcedure.setOperator(productProcedureWorkshop.getOperator());
        productProcedure.setLastModifyTime(new Date());
        productProcedure.setProcedureName(productProcedureWorkshop.getProcedureName());
        productProcedure.setProductNo(productProcedureWorkshop.getProductNo());
        productProcedure.setPrice(productProcedureWorkshop.getPrice());
        productProcedure.setSort(productProcedureWorkshop.getSort());
        Integer productId = productProcedureService.add(productProcedure);
        productProcedureWorkshop.setProcedureId(productId);
        productProcedureWorkshopMapper.insertSelective(productProcedureWorkshop);
        return productProcedureWorkshop.getId();
    }

    public Integer udpate(ProductProcedureWorkshop productProcedureWorkshop) {
        ProductProcedureWorkshop productProcedureWorkshop1 = productProcedureWorkshopMapper.selectByPrimaryKey(productProcedureWorkshop.getId());
        productProcedureWorkshopMapper.updateByPrimaryKeySelective(productProcedureWorkshop);
        ProductProcedure productProcedure = productProcedureService.getById(productProcedureWorkshop1.getProcedureId());
        if (productProcedure != null) {
            productProcedure.setSort(productProcedureWorkshop.getSort());
            productProcedure.setPrice(productProcedureWorkshop.getPrice());
            productProcedure.setLastModifyTime(new Date());
            productProcedure.setProcedureName(productProcedureWorkshop.getProcedureName());
            productProcedureService.update(productProcedure);
        }

        return productProcedureWorkshop.getId();
    }

    public void saveList(List<ProductProcedureWorkshop> list) {
        if (!CollectionUtils.isEmpty(list)) {
            list.stream().filter(t -> t != null).forEach(t -> {
                Product product = productMapper.getByNo(t.getProductNo());
                ProductProcedure productProcedure = new ProductProcedure();
                productProcedure.setProductId(product.getId());
                productProcedure.setProductNo(t.getProductNo());
                productProcedure.setSort(t.getSort());
                productProcedure.setPrice(t.getPrice());
                productProcedure.setOperator(t.getOperator() == null ? "系统" : t.getOperator());
                productProcedure.setIsDelete(0);
                productProcedure.setProcedureName(t.getProcedureName());
                productProcedure.setCreateTime(new Date());
                productProcedure.setLastModifyTime(new Date());

                Integer productProcedureId = productProcedureService.add(productProcedure);
                t.setOperator(t.getOperator() == null ? "系统" : t.getOperator());
                t.setCreateTime(new Date());
                t.setLastModifyTime(new Date());
                t.setProcedureId(productProcedureId);
                t.setIsDelete(0);
                t.setWorkshop(WorkShopProductionMapEnum.get(t.getSort()).getContent());
                productProcedureWorkshopMapper.insertSelective(t);
            });
        }
    }

    public Integer deleteById(Integer id) throws BusinessException {
        ProductProcedureWorkshop productProcedureWorkshop = this.getById(id);
        if (null == productProcedureWorkshop) throw new BusinessException("要删除的记录不存在");
        if ("质检".equals(productProcedureWorkshop.getProcedureName())) {
            throw new BusinessException("无法删除质检工序");
        }
        productProcedureWorkshop.setIsDelete(1);
        return productProcedureWorkshopMapper.updateByPrimaryKeySelective(productProcedureWorkshop);
    }

    public List<WorkShopVo.Procedure> getBySort(List<Integer> integers) {
        List<ProductProcedureWorkshop> productProcedureWorkshops = productProcedureWorkshopMapper.getBySort(integers);
        if (CollectionUtils.isEmpty(productProcedureWorkshops)) return null;
        return BeanUtil.convertList(productProcedureWorkshops, WorkShopVo.Procedure.class);
    }

    public ProductProcedureWorkshop getInspactPriceByWorkShapAndProductNo(String deptName, String productNo) {
        return productProcedureWorkshopMapper.getInspactPriceByWorkShapAndProductNo(deptName, productNo);
    }
}
