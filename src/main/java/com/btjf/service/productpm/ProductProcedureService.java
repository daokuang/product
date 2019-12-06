package com.btjf.service.productpm;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.btjf.business.common.exception.BusinessException;
import com.btjf.common.page.Page;
import com.btjf.common.utils.BeanUtil;
import com.btjf.constant.WorkShopProductionMapEnum;
import com.btjf.controller.order.vo.WorkShopVo;
import com.btjf.mapper.product.ProductProcedureMapper;
import com.btjf.mapper.product.ProductProcedureWorkshopMapper;
import com.btjf.model.product.Product;
import com.btjf.model.product.ProductProcedure;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.model.sys.SysUser;
import com.btjf.service.order.ProductionProcedureService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by liuyq on 2019/8/5.
 * 产品工序
 */
@Service
public class ProductProcedureService {

    @Resource
    private ProductProcedureMapper productProcedureMapper;

    @Resource
    private ProductProcedureWorkshopMapper productProcedureWorkshopMapper;

    @Resource
    private ProductService productService;

    @Resource
    private ProductionProcedureService productionProcedureService;

    public Integer addOrUpdate(ProductProcedure productProcedure, String optionName) {

        if (productProcedure.getId() == null) {
            ProductProcedureWorkshop productProcedureWorkshop = productProcedureWorkshopMapper.getByWorkShopAndProductNoAndName(WorkShopProductionMapEnum.get(productProcedure.getSort()).getContent(), productProcedure.getProductNo(), productProcedure.getProcedureName());
            if (productProcedureWorkshop != null) {
                throw new BusinessException(productProcedureWorkshop.getWorkshop() + "已经存在该工序");
            }
            productProcedureMapper.insertSelective(productProcedure);
        } else {
            productProcedureWorkshopMapper.deleteByProcedureId(productProcedure.getId());
            productProcedureMapper.updateByPrimaryKeySelective(productProcedure);
        }
        productProcedureWorkshopMapper.insertSelective(build(productProcedure, optionName));
        return productProcedure.getId();
    }

    public Integer add(ProductProcedure productProcedure) {
        productProcedureMapper.insertSelective(productProcedure);
        return productProcedure.getId();
    }


    public ProductProcedureWorkshop build(ProductProcedure productProcedure, String optionName) {
        ProductProcedureWorkshop productProcedureWorkshop = new ProductProcedureWorkshop();
        WorkShopProductionMapEnum workShopProductionMapEnum = WorkShopProductionMapEnum.get(productProcedure.getSort());
        productProcedureWorkshop.setOperator(optionName);
        productProcedureWorkshop.setIsDelete(0);
        productProcedureWorkshop.setCreateTime(new Date());
        productProcedureWorkshop.setWorkshop(workShopProductionMapEnum == null ? null : workShopProductionMapEnum.getContent());
        productProcedureWorkshop.setProcedureName(productProcedure.getProcedureName());
        productProcedureWorkshop.setProcedureId(productProcedure.getId());
        productProcedureWorkshop.setLastModifyTime(new Date());
        productProcedureWorkshop.setPrice(productProcedure.getPrice());
        productProcedureWorkshop.setProductId(productProcedure.getProductId());
        productProcedureWorkshop.setProductNo(productProcedure.getProductNo());
        productProcedureWorkshop.setSort(productProcedure.getSort());
        return productProcedureWorkshop;
    }

    public ProductProcedure getById(Integer id) {
        return productProcedureMapper.selectByPrimaryKey(id);
    }

    public Integer update(ProductProcedure productProcedure) {
        return productProcedureMapper.updateByPrimaryKeySelective(productProcedure);
    }

    public List<WorkShopVo.Procedure> getByWorkShopAndProductNo(String workShop, String productNo) {
        List<ProductProcedureWorkshop> productProcedures = productProcedureWorkshopMapper.getByWorkShopAndProductNo(workShop, productNo);
        return BeanUtil.convertList(productProcedures, WorkShopVo.Procedure.class);
    }

    public synchronized Integer getSort() {
        return productProcedureMapper.getMaxSort() + 1;
    }

    public Page<ProductProcedure> listPage(String productNo, String procedureName, String price, Integer isConfirm, Page page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<ProductProcedure> productProcedures = productProcedureMapper.findList(procedureName, price, productNo, isConfirm);
        Comparator<ProductProcedure> groupComparator = (o1, o2) -> {
            int diff = o1.getProductNo().compareTo(o2.getProductNo());
            return diff == 0 ? o1.getSort().compareTo(o2.getSort()) : diff;
        };
        Collections.sort(productProcedures, groupComparator);
        PageInfo pageInfo = new PageInfo(productProcedures);
        return new Page<>(pageInfo);
    }

    public List<ProductProcedure> list(String productNo, String procedureName, String price, Integer isConfirm) {
        List<ProductProcedure> productProcedures = productProcedureMapper.findList(procedureName, price, productNo, isConfirm);
        return productProcedures;
    }

    public Integer sameProductNoAdd(String oldProductNo, String newProduct, SysUser sysUser) {
        Product product = productService.getByNO(newProduct);
        List<ProductProcedure> productProcedures = productProcedureMapper.getByProductNo(oldProductNo);
        if (!CollectionUtils.isEmpty(productProcedures)) {
            for (ProductProcedure productProcedure : productProcedures) {
                if (productProcedure == null) continue;
                List<ProductProcedureWorkshop> productProcedureWorkshops = productProcedureWorkshopMapper.getWorkShop(productProcedure.getProductNo(), productProcedure.getId(), null);
                if (CollectionUtils.isEmpty(productProcedureWorkshops)) continue;
                ProductProcedureWorkshop productProcedureWorkshop = productProcedureWorkshops.get(0);

                //新增工序
                productProcedure.setOperator(sysUser.getUserName());
                productProcedure.setCreateTime(new Date());
                productProcedure.setLastModifyTime(new Date());
                productProcedure.setProductNo(newProduct);
                productProcedure.setProductId(product.getId());
                Integer productProcedureId = this.add(productProcedure);

                //新增型号工序
                productProcedureWorkshop.setProcedureId(productProcedureId);
                productProcedureWorkshop.setCreateTime(new Date());
                productProcedureWorkshop.setOperator(sysUser.getUserName());
                productProcedureWorkshop.setProductId(product.getId());
                productProcedureWorkshop.setProductNo(newProduct);
                productProcedureWorkshop.setLastModifyTime(new Date());

                productProcedureWorkshopMapper.insertSelective(productProcedureWorkshop);
            }
        }
        return productProcedures.size();
    }

    public Integer updateConfirmStatue(List<Integer> integers) {
        try {
            integers.stream().forEach(t -> {
                ProductProcedure productpm = new ProductProcedure();
                productpm.setId(t);
                productpm.setLastModifyTime(new Date());
                productpm.setIsConfirm(1);
                productProcedureMapper.updateByPrimaryKeySelective(productpm);
            });
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return integers.size();
    }

    public void delete(Integer id, SysUser sysUser) {
        ProductProcedure productProcedure = productProcedureMapper.selectByPrimaryKey(id);
        if (productProcedure == null) return;
        productProcedure.setIsDelete(1);
        productProcedure.setLastModifyTime(new Date());
        productProcedure.setOperator(sysUser.getUserName());
        productProcedureMapper.updateByPrimaryKey(productProcedure);

        //删除
        productProcedureWorkshopMapper.deleteByProcedureId(id);
    }
}
