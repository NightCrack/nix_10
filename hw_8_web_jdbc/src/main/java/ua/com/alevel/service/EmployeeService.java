package ua.com.alevel.service;

import ua.com.alevel.entity.Employee;

import java.util.List;

public interface EmployeeService extends BaseService<Employee, Long> {

    List<Employee> findAllByDepartment(Long departmentId);

}
