package ua.com.alevel.view.controller;

import org.springframework.ui.Model;
import ua.com.alevel.view.dto.request.RequestDto;

public interface DependentController<REQ extends RequestDto, ID, FOREIGN_ID> extends BaseController<REQ, ID> {

    String findAllByEntity(FOREIGN_ID foreignId, Model model);

    String redirectToNewEntityPageWithParentId(FOREIGN_ID foreignId, Model model);
}
