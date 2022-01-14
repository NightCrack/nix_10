package ua.com.alevel.dto;

import java.util.Date;

public abstract class ResponseDto extends BaseDto {
    private Date created;
    private Date updated;

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
