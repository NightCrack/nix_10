package ua.com.alevel.facade.impl;

import org.springframework.stereotype.Service;
import ua.com.alevel.dto.employee.EmployeeRequestDto;
import ua.com.alevel.dto.employee.EmployeeResponseDto;
import ua.com.alevel.entity.Department;
import ua.com.alevel.entity.Employee;
import ua.com.alevel.facade.EmployeeFacade;
import ua.com.alevel.service.DepartmentService;
import ua.com.alevel.service.EmployeeService;

import java.util.List;
import java.util.Optional;

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
        Optional<Department> optionalDepartment = departmentService.findById(employeeRequestDto.getDepartmentId());
        if (optionalDepartment.isPresent()) {
            Employee employee = new Employee();
            employee.setDepartment(optionalDepartment.get());
            employee.setFirstName(employeeRequestDto.getFirstName());
            employee.setLastName(employeeRequestDto.getLastName());
            employee.setAge(employeeRequestDto.getAge());
            employeeService.create(employee);
        }
    }

    @Override
    public void update(EmployeeRequestDto employeeRequestDto, Long id) {
        Optional<Employee> employeeOptional = employeeService.findById(id);
        if (employeeOptional.isPresent()) {
            Employee employee = employeeOptional.get();
            employee.setFirstName(employeeRequestDto.getFirstName());
            employee.setLastName(employeeRequestDto.getLastName());
            employee.setAge(employeeRequestDto.getAge());
            employeeService.update(employee);
        }
    }

    @Override
    public void delete(Long id) {
        employeeService.delete(id);
    }

    @Override
    public EmployeeResponseDto findById(Long id) {
        Optional<Employee> employeeOptional = employeeService.findById(id);
        return employeeOptional.map(EmployeeResponseDto::new).orElse(null);
    }

    @Override
    public List<EmployeeResponseDto> findAll() {
        return generateDtoListByEntities(employeeService.findAll());
    }

    @Override
    public List<EmployeeResponseDto> findAllByDepartment(Long departmentId) {
        return generateDtoListByEntities(employeeService.findAllByDepartment(departmentId));
    }

    private List<EmployeeResponseDto> generateDtoListByEntities(List<Employee> list) {
        return list.stream()
                .map(EmployeeResponseDto::new)
                .toList();
    }
}
