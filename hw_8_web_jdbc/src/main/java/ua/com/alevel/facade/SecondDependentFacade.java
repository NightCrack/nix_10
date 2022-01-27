package ua.com.alevel.facade;

import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.ResponseDto;

import java.util.List;

public interface SecondDependentFacade<REQ extends RequestDto, RES extends ResponseDto, ID, FOREIGN_ID, SECOND_FOREIGN_ID> extends DependentFacade<REQ, RES, ID, FOREIGN_ID> {

    List<RES> findAllBySecondForeignId(SECOND_FOREIGN_ID secondForeignId);
}
