package ua.com.alevel.persistence.entity;

import java.time.Instant;


public abstract class BaseEntity {

    private Instant created;
    private Instant updated;
    private Boolean visible;

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public BaseEntity() {
        this.created = Instant.now();
        this.updated = this.created;
        this.visible = true;
    }

    public void preUpdate() {
        this.updated = Instant.now();
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public Instant getUpdated() {
        return updated;
    }

    public void setUpdated(Instant updated) {
        this.updated = updated;
    }
}
