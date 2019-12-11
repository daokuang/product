package com.amir.model.common;


import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

public class Page<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private int rp = 10;
    private int page = 1;
    private List<T> rows;
    private long total;
    private int firstPage;
    private int prePage;
    private int nextPage;
    private int lastPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;

    public Page() {
    }

    public Page(int rp, int page) {
        this.rp = rp > 100 ? 100 : rp;
        this.page = page;
    }

    public Page(PageInfo<T> pageInfo) {
        this.rp = pageInfo.getPageSize();
        this.rows = pageInfo.getList();
        this.page = pageInfo.getPageNum();
        this.total = pageInfo.getTotal();
        this.firstPage = pageInfo.getFirstPage();
        this.prePage = pageInfo.getPrePage();
        this.nextPage = pageInfo.getNextPage();
        this.lastPage = pageInfo.getLastPage();
        this.isFirstPage = pageInfo.isIsFirstPage();
        this.isLastPage = pageInfo.isIsLastPage();
        this.hasPreviousPage = pageInfo.isHasPreviousPage();
        this.hasNextPage = pageInfo.isHasNextPage();
    }

    public Page(Integer rp, Integer page, List<T> rows, Long total, Integer firstPage, Integer prePage, Integer nextPage, Integer lastPage, boolean isFirstPage, boolean isLastPage, boolean hasPreviousPage, boolean hasNextPage) {
        this.rp = rp == null ? 0 : rp;
        this.page = page == null ? 0 : page;
        this.rows = rows;
        this.total = total == null ? 0L : total;
        this.firstPage = firstPage == null ? 0 : firstPage;
        this.prePage = prePage == null ? 0 : prePage;
        this.nextPage = nextPage == null ? 0 : nextPage;
        this.lastPage = lastPage == null ? 0 : lastPage;
        this.isFirstPage = isFirstPage;
        this.isLastPage = isLastPage;
        this.hasPreviousPage = hasPreviousPage;
        this.hasNextPage = hasNextPage;
    }

    public Page(int rp, int page, List<T> rows, long total) {
        this.rp = rp;
        this.rows = rows;
        this.page = page;
        this.total = total;
    }

    public Page(Page<T> page) {
        this.rp = page.rp;
        this.rows = page.rows;
        this.page = page.page;
        this.total = page.total;
    }

    public int getRp() {
        return this.rp;
    }

    public void setRp(int rp) {
        this.rp = rp;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getRows() {
        return this.rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return this.total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getFirstResult() {
        return (this.page - 1) * this.rp;
    }

    public boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isLastPage() {
        return this.isLastPage;
    }

    public boolean isFirstPage() {
        return this.isFirstPage;
    }

    public int getLastPage() {
        return this.lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getNextPage() {
        return this.nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public int getPrePage() {
        return this.prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getFirstPage() {
        return this.firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public boolean getIsFirstPage() {
        return this.isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean getIsLastPage() {
        return this.isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }
}
