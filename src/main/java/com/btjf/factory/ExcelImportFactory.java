package com.btjf.factory;

import com.btjf.factory.Task.InsertTask;
import com.btjf.model.emp.EmpSalaryMothlyPojo;
import com.btjf.model.pm.Pm;
import com.btjf.model.product.ProductPm;
import com.btjf.model.product.ProductProcedureWorkshop;
import com.btjf.service.pm.PmService;
import com.btjf.service.productpm.ProductPmService;
import com.btjf.service.productpm.ProductWorkshopService;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * Created by liuyq on 2019/7/28.
 */
@Component
public class ExcelImportFactory {

   /* public Integer savePm(List<Pm> list, Boolean isCover){
        InsertTask<T> task = new InsertTask(list, PmService.class);
        task.setCover(isCover);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(task);
        return list.size();
    }

    public Integer saveProductPm(List<EmpSalaryMothlyPojo> list){
        InsertTask<ProductPm> task = new InsertTask(list, ProductPmService.class);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(task);
        return list.size();
    }

    public Integer saveProductWorkshop(List<EmpSalaryMothlyPojo> list){
        InsertTask<ProductPm> task = new InsertTask(list, ProductWorkshopService.class);
        ForkJoinPool forkJoinPool = new ForkJoinPool(4);
        forkJoinPool.submit(task);
        return list.size();
    }*/
}
