package ua.com.alevel.entity;

public abstract class BaseEntity {
    private Integer id;

    public Integer getId() {
        if (id != null) {
            return id;
        }
        return null;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
