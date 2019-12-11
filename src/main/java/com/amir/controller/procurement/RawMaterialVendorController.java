package com.amir.controller.procurement;

import com.amir.model.common.AppXaResultHelper;
import com.amir.model.common.Page;
import com.amir.model.common.XaResult;
import com.amir.model.procurement.RawMaterialVendor;
import com.amir.service.procurement.RawMaterialVendorService;
import com.wordnik.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Leq
 * @date 12/11/2019
 */
@RestController
@RequestMapping(value = "vendor/raw-material")
@Api(value = "RawMaterialVendorController", description = "原料厂商管理", position = 1)
public class RawMaterialVendorController {

    @Resource
    private RawMaterialVendorService rawMaterialVendorService;

    @GetMapping("list")
    public XaResult<List<RawMaterialVendor>> list(String vendorName,
                                                  String startDate,
                                                  String endDate,
                                                  Integer productType,
                                                  Integer pageSize,
                                                  Integer currentPage) {
        if (currentPage == null || currentPage < 1) {
            currentPage = 1;
        }
        if (pageSize == null || pageSize < 1) {
            pageSize = 25;
        }
        Page<RawMaterialVendor> listPage = rawMaterialVendorService.page(vendorName, startDate, endDate, productType, new Page<>(pageSize, currentPage));
        return AppXaResultHelper.success(listPage, listPage.getRows());
    }

    @GetMapping("detail")
    public XaResult<RawMaterialVendor> detail(Integer id) {
        if (id == null) {
            return XaResult.error("id必填");
        }

        RawMaterialVendor rawMaterialVendor = rawMaterialVendorService.get(id);
        if (rawMaterialVendor == null) {
            return XaResult.error("该原料厂商不存在");
        } else {
            return XaResult.success(rawMaterialVendor);
        }
    }

    @PostMapping("/save")
    public XaResult<Object> save(RawMaterialVendor rawMaterialVendor) {
        RawMaterialVendor existRawMaterialVendor = rawMaterialVendorService.getByVendorName(rawMaterialVendor.getVendorName());
        if (existRawMaterialVendor != null && !existRawMaterialVendor.getId().equals(rawMaterialVendor.getId())) {
            return XaResult.error("该原料厂商已存在！");
        }
        rawMaterialVendorService.save(rawMaterialVendor);
        return XaResult.success();
    }
}