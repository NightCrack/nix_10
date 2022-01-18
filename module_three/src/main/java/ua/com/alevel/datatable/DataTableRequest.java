package ua.com.alevel.datatable;

import java.util.HashMap;
import java.util.Map;

public class DataTableRequest {

    private String sortingCriteria;
    private String order;
    private int pageNumber;
    private int pageSize;
    private Map<String, Object> queryParam;

    public DataTableRequest() {
        queryParam = new HashMap<>();
    }

    public String getSortingCriteria() {
        return sortingCriteria;
    }

    public void setSortingCriteria(String sortingCriteria) {
        this.sortingCriteria = sortingCriteria;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Map<String, Object> getQueryParam() {
        return queryParam;
    }

    public void setQueryParam(Map<String, Object> queryParam) {
        this.queryParam = queryParam;
    }
}
