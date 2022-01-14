package ua.com.alevel.dto.employee;

import ua.com.alevel.dto.ResponseWithIdDto;
import ua.com.alevel.entity.Department;
import ua.com.alevel.entity.Employee;

public final class EmployeeResponseDto extends ResponseWithIdDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private Department department;

    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(Employee employee) {
        this.firstName = employee.getFirstName();
        this.lastName = employee.getLastName();
        this.age = employee.getAge();
        if (employee.getDepartment() != null) {
            this.department = employee.getDepartment();
        }
        super.setId(employee.getId());
        super.setCreated(employee.getCreated());
        super.setUpdated(employee.getUpdated());
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
}
