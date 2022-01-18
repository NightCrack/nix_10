package ua.com.alevel.datatable;

import ua.com.alevel.entity.BaseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataTableResponse<ENTITY extends BaseEntity> {

    private List<ENTITY> entries;
    private long entriesNumber;
    private Map<Object, Object> optParams;
    private int pageNumber;
    private int pageSize;
    private String sortingCriteria;
    private String order;

    public DataTableResponse() {
        entries = new ArrayList<>();
        optParams = new HashMap<>();
        entriesNumber = 0;
    }

    public List<ENTITY> getEntries() {
        return entries;
    }

    public void setEntries(List<ENTITY> entries) {
        this.entries = entries;
    }

    public long getEntriesNumber() {
        return entriesNumber;
    }

    public void setEntriesNumber(long entriesNumber) {
        this.entriesNumber = entriesNumber;
    }

    public Map<Object, Object> getOptParams() {
        return optParams;
    }

    public void setOptParams(Map<Object, Object> optParams) {
        this.optParams = optParams;
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
}
