package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.ResponseDto;

import java.util.List;

public interface DependentFacade<REQ extends RequestDto, RES extends ResponseDto, ID, FOREIGN_ID> extends BaseFacade<REQ, RES, ID> {

    List<RES> findAllByForeignId(FOREIGN_ID foreignId);
}
