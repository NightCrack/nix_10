package ua.com.alevel.view.controller;

import org.springframework.ui.Model;
import org.springframework.web.context.request.WebRequest;
import ua.com.alevel.view.dto.request.RequestDto;

public interface SecondDependentController<REQ extends RequestDto, ID, FOREIGN_ID, SECOND_FOREIGN_ID> extends DependentController<REQ, ID, FOREIGN_ID> {

    String findAllBySecondEntity(WebRequest request, SECOND_FOREIGN_ID foreignId, Model model);

    String redirectToNewEntityPageWithSecondParentId(SECOND_FOREIGN_ID foreignId, Model model);
}
