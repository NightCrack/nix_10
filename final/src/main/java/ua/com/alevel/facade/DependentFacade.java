package ua.com.alevel.facade;

import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.RequestDto;
import ua.com.alevel.view.dto.response.PageData;
import ua.com.alevel.view.dto.response.ResponseDto;

public interface DependentFacade<REQ extends RequestDto, RES extends ResponseDto, ID, FOREIGN_ID> extends BaseFacade<REQ, RES, ID> {

    PageData<RES> findAllByForeignId(WebRequest request, FOREIGN_ID foreignId);
}
