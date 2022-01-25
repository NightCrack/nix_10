package ua.com.alevel.view.dto.response;

import ua.com.alevel.persistence.entity.Department;
import ua.com.alevel.type.DepartmentType;

public final class DepartmentResponseDto extends ResponseWithIdDto {

    private DepartmentType departmentType;
    private String name;
    private Integer employeesCount;

    public Integer getEmployeesCount() {
        return employeesCount;
    }

    public void setEmployeesCount(Integer employeesCount) {
        this.employeesCount = employeesCount;
    }

    public DepartmentResponseDto(Department department) {
        this.departmentType = department.getDepartmentType();
        this.name = department.getName();
        this.setId(department.getId());
        this.setCreated(department.getCreated());
        this.setUpdated(department.getUpdated());
        this.setVisible(department.getVisible());
    }

    public DepartmentType getDepartmentType() {
        return departmentType;
    }

    public void setDepartmentType(DepartmentType departmentType) {
        this.departmentType = departmentType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
