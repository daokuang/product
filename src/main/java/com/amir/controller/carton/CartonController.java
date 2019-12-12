package com.amir.controller.carton;

import com.amir.model.carton.ShippingMarkImage;
import com.amir.model.common.AppXaResultHelper;
import com.amir.model.common.Page;
import com.amir.model.common.XaResult;
import com.amir.model.procurement.RawMaterialVendor;
import com.amir.service.carton.ShippingMarkService;
import com.amir.service.procurement.RawMaterialVendorService;
import com.amir.vo.ShippingMarkImageResultVo;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author Leq
 * @date 12/11/2019
 */
@RestController
@RequestMapping(value = "carton")
@Api(value = "CartonController", description = "客户管理", position = 1)
public class CartonController {

    @Resource
    private RawMaterialVendorService rawMaterialVendorService;
    @Resource
    private ShippingMarkService shippingMarkService;

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

    /**
     * 批量上传唛头图片
     */
    @PostMapping("shipping-mark/image/upload")
    public XaResult<List<ShippingMarkImageResultVo>> shippingMarkImageUpload(MultipartHttpServletRequest request) {
        List<MultipartFile> shippingMarkImageFileList = request.getFiles("shippingMarkImages");
        return XaResult.success(shippingMarkService.imageUpload(shippingMarkImageFileList));
    }

    /**
     * 获取唛头图片
     */
    @GetMapping("shipping-mark/show/{fileId}")
    public void getShippingMarkImage(@PathVariable int fileId, HttpServletResponse response) throws IOException {
        ShippingMarkImage shippingMarkImage = shippingMarkService.getById(fileId);
        if (shippingMarkImage != null) {
            File file = new File(shippingMarkService.getShippingMarkImageWholePath() + shippingMarkImage.getFileStoreName());

            response.setHeader("Content-Disposition", "inline;filename=" + new String((shippingMarkImage.getFileName() + "." + shippingMarkImage.getFileType()).getBytes(StandardCharsets.UTF_8), "ISO8859-1"));

            response.setContentType(shippingMarkImage.getContentType());
            OutputStream outputStream = response.getOutputStream();
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, outputStream);
            outputStream.flush();
        }
    }
}