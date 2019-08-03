package com.btjf.factory;

import com.btjf.factory.Task.InsertTask;
import com.btjf.model.pm.Pm;
import com.btjf.model.product.ProductPm;
import com.btjf.service.productpm.ProductPmService;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by liuyq on 2019/7/28.
 */
public class ExcelImportFactory {

    public Integer save(List<Pm> list, boolean isCover, Class clzz){
        InsertTask<T> task = new InsertTask(list, clzz);
        task.setCover(isCover);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(task);
        return list.size();
    }

    public Integer saveProductPm(List<ProductPm> list){
        InsertTask<ProductPm> task = new InsertTask(list, ProductPmService.class);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(task);
        return list.size();
    }
}
