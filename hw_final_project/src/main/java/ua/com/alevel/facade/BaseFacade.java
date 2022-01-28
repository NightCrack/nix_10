package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.ResponseDto;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto, ID> {

    void create(REQ req);

    void update(REQ req, ID id);

    void delete(ID id);

    RES findById(ID id);

    PageData<RES> findAll(WebRequest request);
}
