package com.amir.model;


import com.amir.enums.PlatformSourceEnum;

import java.io.Serializable;

public class Page2 implements Serializable {
    private static final long serialVersionUID = 4512602932002080027L;
    public Integer totalPages = null;
    private Integer currentPage = 0;
    private Integer pageRecords = 10;
    private Integer totalRecords = null;
    private Integer startRecord = null;
    private Integer nextPage = null;
    private Integer previousPage = null;
    private boolean hasNextPage = false;
    private boolean hasPreviousPage = false;

    public Page2() {
    }

    public Page2(Integer pageRecords) {
        this.pageRecords = pageRecords;
    }

    public Page2(Integer pageRecords, Integer currentPage) {
        if (pageRecords != null) {
            this.pageRecords = pageRecords;
        }

        if (currentPage != null) {
            this.currentPage = currentPage;
        }

    }

    public Page2(Integer pageRecords, Integer currentPage, Integer totalRecords) {
        this.reset(pageRecords, currentPage, totalRecords);
    }

    public Page2(Page page, PlatformSourceEnum platformSource) {
        if (platformSource != null) {
            switch (platformSource) {
                case APP:
                    this.reset(page);
                    break;
                case WEB:
                    this.reset(page.getRp(), page.getPage(), (new Long(page.getTotal())).intValue());
            }
        }

    }

    public Page2(org.springframework.data.domain.Page page) {
        this.currentPage = page.getNumber();
        this.pageRecords = page.getSize();
        if (page.getTotalPages() == 0 && page.getTotalElements() > 0L) {
            int num = page.getSize() % this.pageRecords;
            if (num > 0) {
                this.totalPages = page.getSize() / this.pageRecords + 1;
            } else {
                this.totalPages = page.getSize() / this.pageRecords;
            }
        } else {
            this.totalPages = page.getTotalPages();
        }

        this.totalRecords = (new Long(page.getTotalElements())).intValue();
        if (this.currentPage + 1 >= this.totalPages) {
            this.hasNextPage = false;
            this.nextPage = this.currentPage;
        } else {
            this.hasNextPage = true;
            this.nextPage = this.currentPage + 1;
        }

        if (this.currentPage <= 0) {
            this.hasPreviousPage = false;
            this.currentPage = 0;
            this.previousPage = 0;
        } else {
            this.hasPreviousPage = true;
            this.previousPage = this.currentPage - 1;
        }

    }

    public Page2(org.springframework.data.domain.Page page, boolean isCheOk) {
        int num;
        if (isCheOk) {
            this.currentPage = page.getNumber() + 1;
            this.pageRecords = page.getSize();
            if (page.getTotalPages() == 0 && page.getTotalElements() > 0L) {
                num = page.getSize() % this.pageRecords;
                if (num > 0) {
                    this.totalPages = page.getSize() / this.pageRecords + 1;
                } else {
                    this.totalPages = page.getSize() / this.pageRecords;
                }
            } else {
                this.totalPages = page.getTotalPages();
            }

            this.totalRecords = (new Long(page.getTotalElements())).intValue();
            if (this.currentPage >= this.totalPages) {
                this.hasNextPage = false;
                this.nextPage = this.currentPage;
            } else {
                this.hasNextPage = true;
                this.nextPage = this.currentPage + 1;
            }

            if (this.currentPage <= 1) {
                this.hasPreviousPage = false;
                this.currentPage = 1;
                this.previousPage = 1;
            } else {
                this.hasPreviousPage = true;
                this.previousPage = this.currentPage - 1;
            }
        } else {
            this.currentPage = page.getNumber();
            this.pageRecords = page.getSize();
            if (page.getTotalPages() == 0 && page.getTotalElements() > 0L) {
                num = page.getSize() % this.pageRecords;
                if (num > 0) {
                    this.totalPages = page.getSize() / this.pageRecords + 1;
                } else {
                    this.totalPages = page.getSize() / this.pageRecords;
                }
            } else {
                this.totalPages = page.getTotalPages();
            }

            this.totalRecords = (new Long(page.getTotalElements())).intValue();
            if (this.currentPage + 1 >= this.totalPages) {
                this.hasNextPage = false;
                this.nextPage = this.currentPage;
            } else {
                this.hasNextPage = true;
                this.nextPage = this.currentPage + 1;
            }

            if (this.currentPage <= 0) {
                this.hasPreviousPage = false;
                this.currentPage = 0;
                this.previousPage = 0;
            } else {
                this.hasPreviousPage = true;
                this.previousPage = this.currentPage - 1;
            }
        }

    }

    public void reset(Page page) {
        if (page == null) {
            this.currentPage = this.getCurrentPage();
            this.pageRecords = this.getPageRecords();
            this.totalPages = 0;
            this.totalRecords = 0;
            this.startRecord = 0;
            this.nextPage = 0;
            this.previousPage = 0;
            this.hasNextPage = this.isHasNextPage();
            this.hasPreviousPage = this.isHasPreviousPage();
        } else {
            this.pageRecords = page.getRp() == 0 ? 10 : page.getRp();
            this.currentPage = page.getPage() - 1 < 0 ? 0 : page.getPage() - 1;
            Integer totalRecords = (new Long(page.getTotal())).intValue();
            this.totalRecords = totalRecords;
            if (totalRecords % this.pageRecords == 0) {
                this.totalPages = totalRecords / this.pageRecords;
            } else {
                this.totalPages = totalRecords / this.pageRecords + 1;
            }

            if (this.currentPage + 1 >= this.totalPages) {
                this.hasNextPage = false;
            } else {
                this.hasNextPage = true;
            }

            if (this.currentPage <= 0) {
                this.hasPreviousPage = false;
            } else {
                this.hasPreviousPage = true;
            }

            this.startRecord = this.currentPage * this.pageRecords;
            this.nextPage = this.currentPage + 1;
            if (this.totalPages <= 1) {
                this.nextPage = 0;
            } else if (this.nextPage >= this.totalPages) {
                this.nextPage = this.totalPages;
            }

            this.previousPage = this.currentPage - 1;
            if (this.previousPage <= 0) {
                this.previousPage = 0;
            }
        }

    }

    public void reset(Integer totalRecords) {
        this.reset(this.pageRecords, this.currentPage, totalRecords);
    }

    public void reset(Integer pageRecords, Integer currentPage, Integer totalRecords) {
        this.pageRecords = pageRecords == null ? this.pageRecords : pageRecords;
        this.currentPage = currentPage == null ? this.currentPage : currentPage;
        this.totalRecords = totalRecords;
        if (totalRecords % this.pageRecords == 0) {
            this.totalPages = totalRecords / this.pageRecords;
        } else {
            this.totalPages = totalRecords / this.pageRecords + 1;
        }

        if (this.currentPage + 1 >= this.totalPages) {
            this.hasNextPage = false;
        } else {
            this.hasNextPage = true;
        }

        if (this.currentPage <= 1) {
            this.hasPreviousPage = false;
            this.currentPage = 1;
        } else {
            this.hasPreviousPage = true;
        }

        this.startRecord = (this.currentPage - 1) * this.pageRecords;
        this.nextPage = this.currentPage + 1;
        if (this.nextPage >= this.totalPages) {
            this.nextPage = this.totalPages;
        }

        this.previousPage = this.currentPage - 1;
        if (this.previousPage <= 1) {
            this.previousPage = 1;
        }

    }

    public Boolean isHasNextPage() {
        return this.hasNextPage;
    }

    public Boolean isHasPreviousPage() {
        return this.hasPreviousPage;
    }

    public Integer getNextPage() {
        return this.nextPage;
    }

    public Integer getPreviousPage() {
        return this.previousPage;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        if (currentPage != null) {
            this.currentPage = currentPage;
        }

    }

    public Integer getPageRecords() {
        return this.pageRecords;
    }

    public void setPageRecords(Integer pageRecords) {
        if (pageRecords != null) {
            this.pageRecords = pageRecords;
        }

    }

    public Integer getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getTotalRecords() {
        return this.totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    public Integer getStartRecord() {
        if (this.startRecord == null && this.currentPage != null) {
            return this.currentPage * this.pageRecords;
        } else {
            return this.currentPage == null ? 0 : this.startRecord;
        }
    }

    public void setStartRecord(Integer startRecord) {
        this.startRecord = startRecord;
    }
}
