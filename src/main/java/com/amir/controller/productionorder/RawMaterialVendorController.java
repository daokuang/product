package com.amir.controller.productionorder;

import com.amir.controller.base.ProductBaseController;
import com.amir.service.procurement.RawMaterialVendorService;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Leq on 2019/12/10.
 * <p>
 * 原料厂商
 */
@RestController
@RequestMapping(value = "vendor")
public class RawMaterialVendorController extends ProductBaseController {
    private static final Logger LOGGER = Logger
            .getLogger(RawMaterialVendorController.class);

    @Resource
    private RawMaterialVendorService rawMaterialVendorService;
}
