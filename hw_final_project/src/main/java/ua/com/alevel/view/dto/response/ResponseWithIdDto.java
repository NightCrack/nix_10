package ua.com.alevel.view.dto.response;

public abstract class ResponseWithIdDto extends ResponseDto {

    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
