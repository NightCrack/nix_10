package ua.com.alevel.util;

import ua.com.alevel.datatable.DataTableRequest;
import ua.com.alevel.datatable.DataTableResponse;
import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.entity.BaseEntity;

public final class WebResponseUtil {

    private WebResponseUtil() {
    }

    public static PageData<? extends ResponseDto> initPageData(
            DataTableResponse<? extends BaseEntity> tableResponse) {
        PageData<? extends ResponseDto> pageData = new PageData<>();
        pageData.setCurrentPage(tableResponse.getPageNumber());
        pageData.setPageSize(tableResponse.getPageSize());
        pageData.setOrder(tableResponse.getOrder());
        pageData.setSort(tableResponse.getSortingCriteria());
        pageData.setItemsSize(tableResponse.getEntriesNumber());
        pageData.initPaginationState();
        return pageData;
    }

    public static void initDataTableResponse(DataTableRequest request, DataTableResponse<? extends BaseEntity> dataTableResponse, long count) {
        dataTableResponse.setEntriesNumber(count);
        dataTableResponse.setPageNumber(request.getPageNumber());
        dataTableResponse.setPageSize(request.getPageSize());
        dataTableResponse.setOrder(request.getOrder());
        dataTableResponse.setSortingCriteria(request.getSortingCriteria());
    }
}
