package com.amir.service.procurement;

import com.amir.mapper.procurement.RawMaterialVendorMapper;
import com.amir.model.common.Page;
import com.amir.model.procurement.RawMaterialVendor;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @author Leq
 * @date 12/10/2019
 */
@Service
public class RawMaterialVendorService {

    @Resource
    RawMaterialVendorMapper rawMaterialVendorMapper;

    public Page<RawMaterialVendor> page(String vendorName, String startDate, String endDate, Integer productType, Page<Object> page) {
        PageHelper.startPage(page.getPage(), page.getRp());
        List<RawMaterialVendor> vendorList = rawMaterialVendorMapper.findList(vendorName, startDate, endDate, productType);
        PageInfo<RawMaterialVendor> pageInfo = new PageInfo<>(vendorList);
        pageInfo.setList(vendorList);
        return new Page<>(pageInfo);
    }

    public RawMaterialVendor get(Integer id) {
        return rawMaterialVendorMapper.selectByPrimaryKey(id);
    }

    public void save(RawMaterialVendor rawMaterialVendor) {
        Date now = new Date();
        if (rawMaterialVendor.getId() == null) {
            rawMaterialVendor.setCreateTime(now);
            rawMaterialVendor.setUpdateTime(now);
            rawMaterialVendor.setShortNameIndex(0);
            int id = rawMaterialVendorMapper.insert(rawMaterialVendor);
            rawMaterialVendor = get(id);
        } else {
            rawMaterialVendor.setUpdateTime(now);
            rawMaterialVendorMapper.updateByPrimaryKeySelective(rawMaterialVendor);
            rawMaterialVendor = get(rawMaterialVendor.getId());
        }
    }

    public RawMaterialVendor getByVendorName(String vendorName) {
        if (vendorName == null) {
            return null;
        }
        return rawMaterialVendorMapper.getByVendorName(vendorName);
    }
}