package ua.com.alevel.service.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.persistence.dao.DepartmentsDAO;
import ua.com.alevel.persistence.dao.EmployeeDAO;
import ua.com.alevel.persistence.dao.impl.CustomResultSet;
import ua.com.alevel.persistence.datatable.DataTableRequest;
import ua.com.alevel.persistence.datatable.DataTableResponse;
import ua.com.alevel.persistence.entity.Employee;
import ua.com.alevel.service.EmployeeService;
import ua.com.alevel.util.WebResponseUtil;

import java.util.Collections;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDAO employeeDAO;
    private final DepartmentsDAO departmentsDAO;

    public EmployeeServiceImpl(EmployeeDAO employeeDAO, DepartmentsDAO departmentsDAO) {
        this.employeeDAO = employeeDAO;
        this.departmentsDAO = departmentsDAO;
    }

    @Override
    public void create(CustomResultSet<Employee> employee) {
        employeeDAO.create(employee);
    }

    @Override
    public void update(Employee employee) {
//        if (employeeDAO.existsById(employee.getId()))
//            employeeDAO.update(employee);
    }

    @Override
    public void delete(Long id) {
//        if (employeeDAO.existsById(id)) {
//            employeeDAO.delete(id);
//        }
    }

    @Override
    public Employee findById(Long id) {
        return employeeDAO.findById(id);
    }

    @Override
    public DataTableResponse<Employee> findAll(DataTableRequest request) {
        DataTableResponse<Employee> dataTableResponse = employeeDAO.findAll(request);
        int count = employeeDAO.count();
        WebResponseUtil.initDataTableResponse(request,dataTableResponse,count);
        return dataTableResponse;
    }

    @Override
    public void deleteAllByForeignId(Long departmentId) {
//        if (departmentsDAO.existsById(departmentId)) {
//            employeeDAO.deleteAllByForeignId(departmentId);
//        }
    }

    @Override
    public List<Employee> findAllByForeignId(Long departmentId) {
//        if (departmentsDAO.existsById(departmentId)) {
//            return employeeDAO.findAllByForeignId(departmentId);
//        }
        return Collections.emptyList();
    }
}
