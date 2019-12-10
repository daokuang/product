package com.amir.excel.easyexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;
import com.amir.constant.WorkShopProductionMapEnum;
import com.amir.model.product.ProductProcedureWorkshop;
import com.amir.service.productpm.ProductService;
import com.amir.service.productpm.ProductWorkshopService;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Auther: liuyuqing
 * @Date: 2019-12-05 17:15
 * @Description:
 */
// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class ProductProcedureWorkshopListener extends AnalysisEventListener<ProcedureWorkShop> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductProcedureWorkshopListener.class);


    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    List<ProcedureWorkShop> list = new ArrayList<ProcedureWorkShop>();
    private ProductWorkshopService productWorkshopService;
    private ProductService productService;


    public ProductProcedureWorkshopListener(ProductWorkshopService productWorkshopService, ProductService productService) {
        this.productWorkshopService = productWorkshopService;
        this.productService = productService;
    }

    /**
     * 这个每一条数据解析都会来调用
     * <p>
     * one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     */
    @Override
    public void invoke(ProcedureWorkShop productProcedureWorkshop, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}", JSON.toJSONString(productProcedureWorkshop));

        ContentModel contentModel = ResponseContent.currentProxy();
        List<String> responseList = contentModel.getResponse();
        if (responseList == null) responseList = Lists.newArrayList();
        List<String> current = this.CheckObject(productProcedureWorkshop, analysisContext.readRowHolder().getRowIndex());
        if (CollectionUtils.isNotEmpty(current)) {
            responseList.addAll(current);
            contentModel.setResponse(responseList);
            ResponseContent.setCurrentProxy(contentModel);
        }
        list.add(productProcedureWorkshop);
        // if (list.size() >= BATCH_COUNT) {
        //    saveData();
        // 存储完成清理 list
        //   list.clear();
        //}


    }

    /**
     * 所有数据解析完成了 都会来调用
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        //最后来保存
        saveData();
        LOGGER.info("所有数据解析完成！");
    }


    /**
     * 加上存储数据库
     */
    private void saveData() {
        LOGGER.info("{}条数据，开始存储数据库！", list.size());
        List<ProductProcedureWorkshop> productProcedureWorkshops = Lists.newArrayList();
        ContentModel contentModel = ResponseContent.currentProxy();
        contentModel.setSize(contentModel.getSize() + list.size());
        list.forEach(t -> {
            ProductProcedureWorkshop productProcedureWorkshop = new ProductProcedureWorkshop();
            productProcedureWorkshop.setCreateTime(new Date());
            productProcedureWorkshop.setProcedureName(t.getProcedureName());
            productProcedureWorkshop.setIsDelete(0);
            productProcedureWorkshop.setLastModifyTime(new Date());
            productProcedureWorkshop.setProductNo(t.getProductNo());
            productProcedureWorkshop.setPrice(new BigDecimal(t.getPrice().trim()));
            productProcedureWorkshop.setSort(new Integer(t.getSort()));
            productProcedureWorkshop.setOperator(contentModel.getOperator());
            productProcedureWorkshops.add(productProcedureWorkshop);
        });
        if (contentModel.getResponse().size() == 0) {
            productWorkshopService.saveList(productProcedureWorkshops);
        }
        ResponseContent.setCurrentProxy(contentModel);
        LOGGER.info("存储数据库成功！");
    }

    public List<String> CheckObject(ProcedureWorkShop procedureWorkShop, Integer currentRow) {
        List<String> respone = Lists.newArrayList();
        if (procedureWorkShop.getProductNo().contains(".0")) {
            procedureWorkShop.setProductNo(procedureWorkShop.getProductNo().replace(".0", ""));
        }

        if (null == productService.getByNO(procedureWorkShop.getProductNo())) {
            respone.add("第" + currentRow + "行" + procedureWorkShop.getProductNo() + "型号不存在");
        }
        if (StringUtils.isEmpty(procedureWorkShop.getProcedureName())) {
            respone.add("第" + currentRow + "行" + "工序名称未填写");
        }


        if (StringUtils.isEmpty(procedureWorkShop.getPrice())) {
            respone.add("第" + currentRow + "行" + "工序价格未填写");
        }
        if (!NumberUtils.isNumber(procedureWorkShop.getPrice())) {
            respone.add("第" + currentRow + "行" + "工序价格错误");

        }
        if (null == procedureWorkShop.getSort()) {
            respone.add("第" + currentRow + "行" + "工序序号未填写");
        }
        try {
            if (null == WorkShopProductionMapEnum.get(new Integer(procedureWorkShop.getSort()))) {
                respone.add("第" + currentRow + "行" + "请填写正确的序号(序号对应车间)");
            }
        } catch (Exception e) {
            respone.add("第" + currentRow + "行" + "请填写正确的序号(序号对应车间)");
        }

        return respone;
    }
}
