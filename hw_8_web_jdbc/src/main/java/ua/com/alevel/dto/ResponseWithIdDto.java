package ua.com.alevel.dto;

public abstract class ResponseWithIdDto extends ResponseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
