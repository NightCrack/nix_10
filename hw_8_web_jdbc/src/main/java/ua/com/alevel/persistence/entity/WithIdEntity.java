package ua.com.alevel.persistence.entity;


import java.io.Serializable;

public abstract sealed

class WithIdEntity extends BaseEntity implements Serializable permits Author, BookInstance, Genre {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
