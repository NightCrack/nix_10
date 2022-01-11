package ua.com.alevel.entity;

import java.io.Serializable;

public abstract sealed class WithIDEntity extends BaseEntity implements Serializable permits Author, BookInstance, Genre {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
