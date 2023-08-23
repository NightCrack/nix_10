package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.ResponseDto;

public interface SecondDependentFacade<REQ extends RequestDto, RES extends ResponseDto, ID, FOREIGN_ID, SECOND_FOREIGN_ID> extends DependentFacade<REQ, RES, ID, FOREIGN_ID> {

    PageData<RES> findAllBySecondForeignId(WebRequest request, SECOND_FOREIGN_ID secondForeignId);
}
