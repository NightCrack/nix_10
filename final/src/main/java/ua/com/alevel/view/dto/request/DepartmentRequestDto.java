package ua.com.alevel.view.dto.request;

import ua.com.alevel.type.DepartmentType;

public final class DepartmentRequestDto extends RequestDto {

    private DepartmentType departmentType;
    private String name;

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
