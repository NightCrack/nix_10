package ua.com.alevel.persistence.datatable;

import ua.com.alevel.persistence.entity.BaseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DataTableResponse<ENTITY extends BaseEntity> {

    private List<ENTITY> items;
    private int itemsSize;
    private Map<Object, List<Integer>> otherParamMap;
    private int currentPage;
    private int currentSize;
    private String sort;
    private String order;

    public DataTableResponse() {
        this.items = Collections.emptyList();
        this.otherParamMap = Collections.emptyMap();
        this.itemsSize = 0;
    }

    public List<ENTITY> getItems() {
        return items;
    }

    public void setItems(List<ENTITY> items) {
        this.items = items;
    }

    public int getItemsSize() {
        return itemsSize;
    }

    public void setItemsSize(int itemsSize) {
        this.itemsSize = itemsSize;
    }

    public Map<Object, List<Integer>> getOtherParamMap() {
        return otherParamMap;
    }

    public void setOtherParamMap(Map<Object, List<Integer>> otherParamMap) {
        this.otherParamMap = otherParamMap;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCurrentSize() {
        return currentSize;
    }

    public void setCurrentSize(int currentSize) {
        this.currentSize = currentSize;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
