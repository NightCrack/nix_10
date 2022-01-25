package ua.com.alevel.view.dto.response;

import ua.com.alevel.view.dto.BaseDto;

import java.time.Instant;

public abstract class ResponseDto extends BaseDto {
    private Instant created;
    private Instant updated;
    private Boolean visible;

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

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }
}
