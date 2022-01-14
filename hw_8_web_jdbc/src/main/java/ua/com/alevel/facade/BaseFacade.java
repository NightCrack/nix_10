package ua.com.alevel.facade;

import ua.com.alevel.dto.RequestDto;
import ua.com.alevel.dto.ResponseDto;

import java.util.List;

public interface BaseFacade<REQ extends RequestDto, RES extends ResponseDto, ID> {

    void create(REQ req);

    void update(REQ req, ID id);

    void delete(ID id);

    RES findById(ID id);

    List<RES> findAll();
}
