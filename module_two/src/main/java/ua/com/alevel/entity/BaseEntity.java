package ua.com.alevel.entity;

public abstract sealed class BaseEntity permits City {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
