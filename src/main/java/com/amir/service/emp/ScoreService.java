package com.amir.service.emp;

import com.amir.constant.SubsidyTypeEnum;
import com.amir.exception.BusinessException;
import com.amir.mapper.emp.ScoreMapper;
import com.amir.model.emp.Emp;
import com.amir.model.emp.EmpSalaryMonthly;
import com.amir.model.emp.EmpSubsibyMonthly;
import com.amir.model.emp.Score;
import com.amir.service.sys.SysDeptService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by liuyq on 2019/9/10.
 */
@Service
public class ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private EmpSalaryMonthlyService empSalaryMonthlyService;

    @Resource
    private EmpService empService;

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private EmpWorkService empWorkService;

    @Resource
    private EmpSubsibyMonthlyService empSubsibyMonthlyService;

    public Score getByNameAndYearMonth(String name, String yearMonth) {
        return scoreMapper.getByNameAndYearMonth(name, yearMonth);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    public Integer saveOrUpate(Score score) {
        if (score == null) return 0;
        Score score1 = this.getByNameAndYearMonth(score.getEmpName(), score.getYearMonth());
        if (null != score1) {
            if (null == score.getCheckworkScore()) { //导入的是另外三个分
                score1.setFiveScore(score.getFiveScore());
                score1.setCoordinationScore(score.getCoordinationScore());
                score1.setQualityScore(score.getQualityScore());
            } else {
                score1.setCheckworkScore(score.getCheckworkScore());
            }
            scoreMapper.updateByPrimaryKeySelective(score1);
            saveSubsidy(score1);
        } else {
            scoreMapper.insertSelective(score);
        }
        return score.getId();
    }

    public Integer saveSubsidy(Score score) {
        if (score.getQualityScore() != null && score.getCheckworkScore() != null && score.getCoordinationScore() != null
                && score.getFiveScore() != null) {
            //平均值
            BigDecimal avgSorce = BigDecimal.valueOf(Stream.of(score.getCheckworkScore(),
                    score.getQualityScore(),
                    score.getCoordinationScore(),
                    score.getFiveScore())
                    .mapToDouble(BigDecimal::doubleValue).average().getAsDouble());
            Score score1 = new Score();
            score1.setId(score.getId());
            score1.setScore(avgSorce);
            scoreMapper.updateByPrimaryKeySelective(score1);

            //更新考勤表里的分
            EmpSalaryMonthly empSalaryMonthly = empSalaryMonthlyService.getByYearMonthAndName(score.getYearMonth(), score.getEmpName());
            if (null != empSalaryMonthly) {
                EmpSalaryMonthly empSalaryMonthly1 = new EmpSalaryMonthly();
                empSalaryMonthly1.setId(empSalaryMonthly.getId());
                empSalaryMonthly1.setScore(avgSorce);
                empSalaryMonthly1.setLastModifyTime(new Date());
                empSalaryMonthlyService.update(empSalaryMonthly1);
            }
            //todo 各种补贴
            //固定住房补贴
            EmpSubsibyMonthly empSubsibyMonthly = new EmpSubsibyMonthly();
            Emp emp = empService.getByName(score.getEmpName());
            if (emp == null) throw new BusinessException("生成补贴时，用户不存在");
            empSubsibyMonthly.setCreateTime(new Date());
            empSubsibyMonthly.setLastModifyTime(new Date());
            empSubsibyMonthly.setDeptId(emp.getDeptId());
            empSubsibyMonthly.setDeptName(sysDeptService.get(emp.getDeptId()).getDeptName());
            empSubsibyMonthly.setEmpId(emp.getId());
            empSubsibyMonthly.setEmpName(emp.getName());
            empSubsibyMonthly.setYearMonth(score.getYearMonth());
            empSubsibyMonthly.setIsDelete(0);
            empSubsibyMonthly.setMoney(avgSorce);
            empSubsibyMonthly.setWorkName(empWorkService.getByID(emp.getWorkId()).getName());
            empSubsibyMonthly.setType(SubsidyTypeEnum.FIX_HOUS.getValue());
            empSubsibyMonthly.setIsConfirm(0);
            empSubsibyMonthlyService.save(empSubsibyMonthly);
        }
        return null;
    }

    public List<Score> getList(String yearMonth, String empName, String deptName) {
        return scoreMapper.getList(yearMonth, empName, deptName);
    }
}
