package com.amir.factory;

import org.springframework.stereotype.Component;

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
