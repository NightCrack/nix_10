package ua.com.alevel.entity;

import ua.com.alevel.type.DepartmentType;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "departments")
public final class Department extends WithIdEntity {

    @Enumerated(EnumType.STRING)
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
