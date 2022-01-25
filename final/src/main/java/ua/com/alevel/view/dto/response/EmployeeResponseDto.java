package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.persistence.entity.Employee;

public final class EmployeeResponseDto extends ResponseWithIdDto {

    private String firstName;
    private String lastName;
    private Integer age;
    private Department department;

    public EmployeeResponseDto() {
    }

    public EmployeeResponseDto(Employee employee) {
        firstName = employee.getFirstName();
        lastName = employee.getLastName();
        age = employee.getAge();
        if (employee.getDepartment() != null) {
            this.department = employee.getDepartment();
        }
        this.setId(employee.getId());
        this.setCreated(employee.getCreated());
        this.setUpdated(employee.getUpdated());
        this.setVisible(employee.getVisible());
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
