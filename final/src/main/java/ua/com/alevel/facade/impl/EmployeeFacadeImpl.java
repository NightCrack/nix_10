package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.facade.EmployeeFacade;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Author;
import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.persistence.entity.Employee;
import ua.com.alevel.service.DepartmentService;
import ua.com.alevel.service.EmployeeService;
import ua.com.alevel.util.WebRequestUtil;
import ua.com.alevel.util.WebResponseUtil;
import ua.com.alevel.view.dto.request.EmployeeRequestDto;
import ua.com.alevel.view.dto.request.PageAndSizeData;
import ua.com.alevel.view.dto.request.SortData;
import ua.com.alevel.view.dto.response.AuthorResponseDto;
import ua.com.alevel.view.dto.response.EmployeeResponseDto;
import ua.com.alevel.view.dto.response.PageData;

import java.util.List;

@Service
public class EmployeeFacadeImpl implements EmployeeFacade {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;

    public EmployeeFacadeImpl(EmployeeService employeeService, DepartmentService departmentService) {
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Override
    public void create(EmployeeRequestDto employeeRequestDto) {
//        Department department = departmentService.findById(employeeRequestDto.getDepartmentId());
//        if (department != null) {
//            Employee employee = new Employee();
//            employee.setDepartment(department);
//            employee.setFirstName(employeeRequestDto.getFirstName());
//            employee.setLastName(employeeRequestDto.getLastName());
//            employee.setAge(employeeRequestDto.getAge());
//            employeeService.create(employee);
//        }
    }

    @Override
    public void update(EmployeeRequestDto employeeRequestDto, Long id) {
//        Employee employee = employeeService.findById(id);
//        if (employee != null) {
//            employee.setFirstName(employeeRequestDto.getFirstName());
//            employee.setLastName(employeeRequestDto.getLastName());
//            employee.setAge(employeeRequestDto.getAge());
//            employeeService.update(employee);
//        }
    }

    @Override
    public void delete(Long id) {
        employeeService.delete(id);
    }

    @Override
    public EmployeeResponseDto findById(Long id) {
        return new EmployeeResponseDto(employeeService.findById(id));
    }

    @Override
    public PageData<EmployeeResponseDto> findAll(WebRequest request) {
        DataTableRequest dataTableRequest = WebRequestUtil.initDataTableRequest(request);
        DataTableResponse<Employee> all = employeeService.findAll(dataTableRequest);
        List<EmployeeResponseDto> items = all.getItems()
                .stream()
                .map(EmployeeResponseDto::new)
                .toList();
        PageData<EmployeeResponseDto> pageData = (PageData<EmployeeResponseDto>) WebResponseUtil.initPageData(all);
        pageData.setItems(items);
        return pageData;
    }

    @Override
    public List<EmployeeResponseDto> findAllByForeignId(Long departmentId) {
        return generateDtoListByEntities(employeeService.findAllByForeignId(departmentId));
    }

    private List<EmployeeResponseDto> generateDtoListByEntities(List<Employee> list) {
        return list.stream()
                .map(EmployeeResponseDto::new)
                .toList();
    }
}
