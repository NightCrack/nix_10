package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.DepartmentFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.service.DepartmentService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.DepartmentRequestDto;
import ua.com.alevel.view.dto.response.DepartmentResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;

@Service
public class DepartmentFacadeImpl implements DepartmentFacade {

    private final DepartmentService departmentService;

    public DepartmentFacadeImpl(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @Override
    public void create(DepartmentRequestDto departmentRequestDto) {
//        Department department = new Department();
//        department.setDepartmentType(departmentRequestDto.getDepartmentType());
//        department.setName(departmentRequestDto.getName());
//        departmentService.create(department);
    }

    @Override
    public void update(DepartmentRequestDto departmentRequestDto, Long id) {
//        Department department = departmentService.findById(id);
//        if (department != null) {
//            department.setDepartmentType(departmentRequestDto.getDepartmentType());
//            department.setName(departmentRequestDto.getName());
//            departmentService.update(department);
//        }
    }

    @Override
    public void delete(Long id) {
        departmentService.delete(id);
    }

    @Override
    public DepartmentResponseDto findById(Long id) {
        return new DepartmentResponseDto(departmentService.findById(id));
    }

    @Override
    public PageData<DepartmentResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Department> all = departmentService.findAll(dataTableRequest);
        List<DepartmentResponseDto> items = all.getItems()
                .stream()
                .map(DepartmentResponseDto::new)
                .peek(entity -> entity
                        .setEmployeesCount(all
                                .getOtherParamMap()
                                .get(entity.getId()).get(0)))
                .toList();
        PageData<DepartmentResponseDto> pageData = (PageData<DepartmentResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }
}
