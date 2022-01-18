package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.dto.ResponseDto;
import ua.com.alevel.dto.response.PageData;

import java.sql.SQLException;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto> {

    void create(REQ req) throws SQLException;

    void update(REQ req, Long id);

    void delete(Long id);

    RES findById(Long id);

    PageData<RES> findAll(WebRequest request);
}
