package com.btjf.excel.easyexcel;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.excel.EasyExcel;
import com.btjf.excel.BaseExcelHandler;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.service.productpm.ProductService;
import com.btjf.service.productpm.ProductWorkshopService;
import com.google.common.collect.Lists;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * @Auther: liuyuqing
 * @Date: 2019-12-05 17:31
 * @Description:
 */
@Service
public class ProcedureWorkshopHelper {

    @Resource
    private ProductWorkshopService productWorkshopService;
    @Resource
    private ProductService productService;

    public List<String> execute(MultipartFile file, String operator) throws Exception {
        List<String> response = Lists.newArrayList();
        try {
            InputStream inputStream = file.getInputStream();
            ResponseContent.setCurrentProxy(ContentModel.builder().operator(operator).response(Lists.newArrayList()).size(0).build());
            EasyExcel.read(inputStream, ProcedureWorkShop.class, new ProductProcedureWorkshopListener(productWorkshopService, productService)).sheet().doRead();
            ContentModel contentModel = ResponseContent.currentProxy();
            List<String> result = contentModel.getResponse();
            if (CollectionUtils.isNotEmpty(result)) {
                response.add("导入失败，以下数据请修改后再重新上传");
                response.addAll(result);
            } else {
                response.add("提交成功！新增导入" + contentModel.getSize() + "条数据！");
            }
        } finally {
            ResponseContent.remove();
        }

        return response;
    }
}
