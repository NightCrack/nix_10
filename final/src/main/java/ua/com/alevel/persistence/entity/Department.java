package ua.com.alevel.persistence.entity;

import ua.com.alevel.type.DepartmentType;


public final class Department extends WithIdEntity {


    private DepartmentType departmentType;
    private String name;

    public Department() {
        super();
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
