//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.org.rapid_framework.page;

import common.util.SqlUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class PageRequest<T> implements Serializable {
    public static final int DEFAULT_PAGE_SIZE = 10;
    /** @deprecated */
    @Deprecated
    private T filters;
    private int pageNumber;
    private int pageSize;
    private String sortColumns;

    public PageRequest() {
        this.pageSize = 10;
    }


    public PageRequest(int pageNumber, int pageSize) {
        this(pageNumber, pageSize, null);
    }


    public PageRequest(int pageNumber, int pageSize, String sortColumns) {
        this(pageNumber, pageSize, null, sortColumns);
    }

    /** @deprecated */
    @Deprecated
    public PageRequest(int pageNumber, int pageSize, T filters, String sortColumns) {
        this.pageSize = 10;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.setFilters(filters);
        this.setSortColumns(sortColumns);
    }

    /** @deprecated */
    @Deprecated
    public T getFilters() {
        return this.filters;
    }

    /** @deprecated */
    @Deprecated
    public void setFilters(T filters) {
        this.filters = filters;
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortColumns() {
        return this.sortColumns;
    }

    public void setSortColumns(String sortColumns) {
        this.checkSortColumnsSqlInjection(sortColumns);
        if(sortColumns != null && sortColumns.length() > 200) {
            throw new IllegalArgumentException("sortColumns.length() <= 200 must be true");
        } else {
            this.sortColumns = sortColumns;
        }
    }

    public List<SortInfo> getSortInfos() {
        return Collections.unmodifiableList(SortInfo.parseSortColumns(this.sortColumns));
    }

    private void checkSortColumnsSqlInjection(String sortColumns) {
        if(sortColumns != null) {
            if(!SqlUtil.isOrderByIllegal(sortColumns)) {
                throw new IllegalArgumentException("sortColumns:" + sortColumns + " has SQL Injection risk");
            }
        }
    }
}
