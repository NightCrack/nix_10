package ua.com.alevel.controller;

import org.springframework.ui.Model;
import ua.com.alevel.dto.RequestDto;

public interface DependentController<REQ extends RequestDto, ID, FOREIGN_ID> extends BaseController<REQ , ID, FOREIGN_ID>{

    String redirectToNewEntityPageWithParentId(FOREIGN_ID foreignId, Model model);
}
