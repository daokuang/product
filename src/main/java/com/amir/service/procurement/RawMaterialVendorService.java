package com.amir.service.procurement;

import com.amir.mapper.procurement.RawMaterialVendorMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Leq
 * @date 12/10/2019
 */
@Service
public class RawMaterialVendorService {

    @Resource
    RawMaterialVendorMapper rawMaterialVendorMapper;

}
