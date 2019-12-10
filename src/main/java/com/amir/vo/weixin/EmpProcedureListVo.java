package com.amir.vo.weixin;

import java.util.List;

public class EmpProcedureListVo {

    private Integer id;//工序ID
    private Integer sort;//序号
    private String name;//工序名称
    private Boolean changed;//是否已调整
    private List<EmpProcedureDetailVo> list;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<EmpProcedureDetailVo> getList() {
        return list;
    }

    public void setList(List<EmpProcedureDetailVo> list) {
        this.list = list;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Boolean getChanged() {
        return changed;
    }

    public void setChanged(Boolean changed) {
        this.changed = changed;
    }
}
