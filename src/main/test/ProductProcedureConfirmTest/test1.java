package ProductProcedureConfirmTest;

import com.amir.service.emp.EmpSalaryMonthlyService;
import org.junit.Test;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.List;

/**
 * Created by liuyq on 2019/8/22.
 */
public class test1 {
    @Test
    public void test() {
        FileSystemXmlApplicationContext f = new FileSystemXmlApplicationContext("classpath:applicationContext.xml");

        EmpSalaryMonthlyService empSalaryMonthlyService = (EmpSalaryMonthlyService) f.getBean("empSalaryMonthlyService");

        List<String> empSalaryMonthlyServiceYearMonth = empSalaryMonthlyService.getYearMonth();
        System.out.println(empSalaryMonthlyServiceYearMonth);
    }

}
