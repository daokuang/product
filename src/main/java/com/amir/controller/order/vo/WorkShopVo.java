package com.amir.controller.order.vo;

import com.amir.model.emp.Emp;
import com.amir.model.order.ProductionProcedure;
import com.amir.model.product.ProductProcedureWorkshop;
import com.amir.model.sys.Sysdept;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liuyq on 2019/8/8.
 */
public class WorkShopVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String workshop;//车间

    private Integer workshopId;

    /**
     * 车间主任
     */
    private List<Leader> leaderList;

    /**
     * 工序
     */
    private List<Procedure> procedures;

    public WorkShopVo(Sysdept sysdept, List<Emp> emps, List<ProductProcedureWorkshop> productProcedureWorkshops) {
        if (sysdept != null) {
            this.workshop = sysdept.getDeptName();
            this.workshopId = sysdept.getId();
            if (!CollectionUtils.isEmpty(emps)) {
                this.leaderList = Leader.transfor(emps);
            }
            if (!CollectionUtils.isEmpty(productProcedureWorkshops)) {
                this.procedures = Procedure.workshopTransfor(productProcedureWorkshops);
            }
        }
    }

    public String getWorkshop() {
        return workshop;
    }

    public void setWorkshop(String workshop) {
        this.workshop = workshop;
    }

    public Integer getWorkshopId() {
        return workshopId;
    }

    public void setWorkshopId(Integer workshopId) {
        this.workshopId = workshopId;
    }

    public List<Leader> getLeaderList() {
        return leaderList;
    }

    public void setLeaderList(List<Leader> leaderList) {
        this.leaderList = leaderList;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    public static class Leader {

        private Integer id;

        private String workshopDirector;

        public Leader(Emp emp) {
            this.id = emp.getId();
            this.workshopDirector = emp.getName();
        }

        public static List<Leader> transfor(List<Emp> emps) {
            List<Leader> leaders = Lists.newArrayList();
            if (emps != null) {
                for (Emp emp : emps) {
                    leaders.add(new Leader(emp));
                }
            }
            return leaders;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getWorkshopDirector() {
            return workshopDirector;
        }

        public void setWorkshopDirector(String workshopDirector) {
            this.workshopDirector = workshopDirector;
        }
    }


    /**
     * 工序
     */
    public static class Procedure {
        private Integer procedureId;

        private String procedureName;

        private Integer sort;

        private Integer num;

        public Procedure() {
        }

        public Procedure(ProductProcedureWorkshop productProcedureWorkshop) {
            this.procedureId = productProcedureWorkshop.getProcedureId();
            this.procedureName = productProcedureWorkshop.getProcedureName();
            this.sort = productProcedureWorkshop.getSort();
            this.num = productProcedureWorkshop.getNum();
        }

        public Procedure(ProductionProcedure productionProcedure) {
            this.procedureId = productionProcedure.getProcedureId();
            this.procedureName = productionProcedure.getProcedureName();
            this.sort = productionProcedure.getSort();
            this.num = productionProcedure.getAssignNum();
        }

        public static List<Procedure> workshopTransfor(List<ProductProcedureWorkshop> productProcedureWorkshops) {
            List<Procedure> procedures = Lists.newArrayList();
            if (productProcedureWorkshops != null) {
                for (ProductProcedureWorkshop productProcedureWorkshop : productProcedureWorkshops) {
                    procedures.add(new Procedure(productProcedureWorkshop));
                }
            }
            return procedures;
        }

        public static List<Procedure> productionProcedureTransfor(List<ProductionProcedure> productionProcedures) {
            List<Procedure> procedures = Lists.newArrayList();
            if (productionProcedures != null) {
                for (ProductionProcedure productionProcedure : productionProcedures) {
                    procedures.add(new Procedure(productionProcedure));
                }
            }
            return procedures;
        }

        public Integer getProcedureId() {
            return procedureId;
        }

        public void setProcedureId(Integer procedureId) {
            this.procedureId = procedureId;
        }

        public String getProcedureName() {
            return procedureName;
        }

        public void setProcedureName(String procedureName) {
            this.procedureName = procedureName;
        }

        public Integer getNum() {
            return num;
        }

        public void setNum(Integer num) {
            this.num = num;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }
    }
}
