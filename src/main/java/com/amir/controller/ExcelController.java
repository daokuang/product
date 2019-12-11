package com.amir.controller;

import com.amir.controller.base.ProductBaseController;
import com.amir.excel.ExcelHandlerHelper;
import com.amir.excel.easyexcel.ProcedureWorkshopHelper;
import com.amir.model.common.XaResult;
import com.wordnik.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;


/**
 * Created by Administrator on 2018/7/3 0003.
 */
@Api(value = "ExcelController", description = "Excel文件导入", position = 1)
@RequestMapping(value = "/excel")
@RestController("excelController")
public class ExcelController extends ProductBaseController {

    @Resource
    private ExcelHandlerHelper excelHandlerHelper;

    @Resource
    private ProcedureWorkshopHelper procedureWorkshopHelper;


    /**
     * 获取数据
     *
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public XaResult<List<String>> importExcel(MultipartFile file, Integer fileType, Boolean isCover) throws Exception {
        if (fileType == null) {
            return XaResult.error("数据类型为空");
        }
        if (file == null || file.isEmpty()) {
            return XaResult.error("请选择上传的文件");
        } else {
            if (fileType == 5) {
                return XaResult.success(procedureWorkshopHelper.execute(file, getLoginUser().getUserName()));
            }
            return XaResult.success(excelHandlerHelper.getHandler(fileType).execute(file, isCover, getLoginUser().getUserName()));
        }
    }


    /**
     * 下载模板文件
     *
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public XaResult<String> importExcel(Integer fileType) throws Exception {
        if (fileType == null) {
            return XaResult.error("数据类型为空");
        }
        String filename = null;
        switch (fileType) {
            case 1:
                filename = "批量新增仓库材料.xlsx";
                break;
            case 2:
                filename = "仓库材料批量入库.xlsx";
                break;
            case 3:
                filename = "批量新增型号耗料.xlsx";
                break;
            case 4:
                filename = "计划外出库批量导入.xlsx";
                break;
            case 5:
                filename = "批量导入工序模板.xlsx";
                break;
            case 6:
                filename = "员工批量导入.xlsx";
                break;
            case 7:
                filename = "批量新增订单模板.xlsx";
                break;
            case 8:
                filename = "YYYY-MM考勤数据导入模板.xlsx";
                break;
            case 9:
                filename = "YYYY-MM3个分导入模板.xlsx";
                break;
            case 10:
                filename = "YYYY-MM考勤分导入.xlsx";
                break;
            case 11:
                filename = "计时工资批量导入.xlsx";
                break;
            case 12:
                filename = "批量导入其他扣款.xlsx";
                break;
            case 13:
                filename = "批量新增工资补贴.xlsx";
                break;
        }
        if (StringUtils.isEmpty(filename)) {
            return XaResult.error("模板不存在");
        }
        String url = "/download/" + filename;
        return XaResult.success(url);

    }


}
