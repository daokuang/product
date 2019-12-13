package com.amir.service.carton;

import com.amir.constant.SysConstant;
import com.amir.mapper.carton.ShippingMarkImageMapper;
import com.amir.mapper.carton.ShippingMarkMapper;
import com.amir.model.carton.ShippingMark;
import com.amir.model.carton.ShippingMarkImage;
import com.amir.util.DateUtil;
import com.amir.vo.ShippingMarkImageResultVo;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Leq
 * @date 12/12/2019
 */
@Service
public class ShippingMarkService {
    private final String IMAGE_PATH = "files/shipping-mark/images/";

    @Resource
    private ShippingMarkImageMapper shippingMarkImageMapper;
    @Resource
    private ShippingMarkMapper shippingMarkMapper;

    @Value("${amir.file.path:}")
    private String amirFilePath;

    public void save(ShippingMark shippingMark) {
        if (shippingMark.getId() == null) {
            shippingMarkMapper.insert(shippingMark);
        } else {
            shippingMarkMapper.updateByPrimaryKeySelective(shippingMark);
        }
    }

    public ShippingMark getByCustomerAndNoProduct(Integer customerId) {
        List<ShippingMark> shippingMarkList = shippingMarkMapper.getByCustomerAndNoProduct(customerId);
        if (shippingMarkList.isEmpty()) {
            return null;
        }
        if (shippingMarkList.size() > 1) {
            shippingMarkList.sort(Comparator.comparing(ShippingMark::getUpdateTime).reversed());
            for (int i = 1; i < shippingMarkList.size(); i++) {
                shippingMarkMapper.deleteByPrimaryKey(shippingMarkList.get(0).getId());
            }
        }
        return shippingMarkList.get(0);
    }

    public ShippingMark getByCustomerAndProduct(Integer customerId, String productNo) {
        List<ShippingMark> shippingMarkList = shippingMarkMapper.getByCustomerAndProduct(customerId, productNo);
        if (shippingMarkList.isEmpty()) {
            return null;
        }
        if (shippingMarkList.size() > 1) {
            shippingMarkList.sort(Comparator.comparing(ShippingMark::getUpdateTime).reversed());
            for (int i = 1; i < shippingMarkList.size(); i++) {
                shippingMarkMapper.deleteByPrimaryKey(shippingMarkList.get(0).getId());
            }
        }
        return shippingMarkList.get(0);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<ShippingMarkImageResultVo> imageUpload(List<MultipartFile> shippingMarkImageFileList) {
        List<ShippingMarkImageResultVo> resultVoList = new ArrayList<>();
        int i = 0;
        Date now = new Date();
        String nowStr = DateUtil.dateToString(now, DateUtil.ymdhmsNoPFormat);

        String wholePath = getShippingMarkImageWholePath();

        File pathDir = new File(wholePath);
        if (pathDir.exists()) {
            if (!pathDir.isDirectory()) {
                pathDir.delete();
                pathDir.mkdirs();
            }
        } else {
            pathDir.mkdirs();
        }

        for (MultipartFile shippingMarkImageFile : shippingMarkImageFileList) {
            ShippingMarkImageResultVo resultVo = new ShippingMarkImageResultVo();
            resultVoList.add(resultVo);
            resultVo.setFileName(shippingMarkImageFile.getOriginalFilename());
            resultVo.setSuccess(true);
            if (!SysConstant.SHIPPING_MARK_IMAGE_SUPPORT.contains(shippingMarkImageFile.getContentType().toLowerCase())) {
                resultVo.setSuccess(false);
                resultVo.setReason("图片格式错误");
                continue;
            }
            //检查图片大小，限制在500KB以内
            if (shippingMarkImageFile.getSize() / 1024.0 > 500) {
                resultVo.setSuccess(false);
                resultVo.setReason("图片太大");
                continue;
            }

            try {
                String fileNameOnly = shippingMarkImageFile.getOriginalFilename();
                String fileType;
                if (fileNameOnly.contains(".")) {
                    fileType = fileNameOnly.substring(fileNameOnly.lastIndexOf(".") + 1);
                    fileNameOnly = fileNameOnly.substring(0, fileNameOnly.lastIndexOf("."));
                } else {
                    fileType = MediaType.parseMediaType(shippingMarkImageFile.getContentType()).getSubtype();
                }

                String fileStoreName = nowStr + (i++) + RandomStringUtils.random(5, true, false);

                FileOutputStream storeFile = new FileOutputStream(wholePath + fileStoreName);
                InputStream in = shippingMarkImageFile.getInputStream();
                int index;
                byte[] bytes = new byte[1024];
                while ((index = in.read(bytes)) != -1) {
                    storeFile.write(bytes, 0, index);
                    storeFile.flush();
                }
                storeFile.close();
                in.close();

                ShippingMarkImage shippingMarkImage = getByFileName(fileNameOnly);
                if (shippingMarkImage != null) {
                    //删除服务器上旧的照片数据
                    new File(wholePath + shippingMarkImage.getFileStoreName()).delete();
                    shippingMarkImage.setFileType(fileType);
                    shippingMarkImage.setContentType(shippingMarkImageFile.getContentType());
                    shippingMarkImage.setFileStoreName(fileStoreName);
                    shippingMarkImage.setUpdateTime(now);
                    shippingMarkImageMapper.updateByPrimaryKey(shippingMarkImage);
                } else {
                    shippingMarkImage = new ShippingMarkImage();
                    shippingMarkImage.setFileName(fileNameOnly);
                    shippingMarkImage.setFileType(fileType);
                    shippingMarkImage.setContentType(shippingMarkImageFile.getContentType());
                    shippingMarkImage.setFileStoreName(fileStoreName);
                    shippingMarkImage.setCreateTime(now);
                    shippingMarkImage.setUpdateTime(now);
                    shippingMarkImageMapper.insertSelective(shippingMarkImage);
                }
            } catch (Exception e) {
                resultVo.setSuccess(false);
                resultVo.setReason("系统异常");
                e.printStackTrace();
            }
        }
        return resultVoList;
    }

    public ShippingMarkImage getById(int fileId) {
        return shippingMarkImageMapper.selectByPrimaryKey(fileId);
    }

    public ShippingMarkImage getByFileName(String fileName) {
        return shippingMarkImageMapper.getByFileName(fileName);
    }

    public String getShippingMarkImageWholePath() {
        String wholePath = IMAGE_PATH;
        if (StringUtils.isNotBlank(amirFilePath)) {
            if (amirFilePath.endsWith("/")) {
                wholePath = amirFilePath + IMAGE_PATH;
            } else {
                wholePath = amirFilePath + "/" + IMAGE_PATH;
            }
        }
        return wholePath;
    }

}
