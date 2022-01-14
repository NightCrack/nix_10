package ua.com.alevel.controller;


import org.springframework.ui.Model;
import ua.com.alevel.dto.RequestDto;

public interface BaseController<REQ extends RequestDto, ID> {

    String findAll(Model model);

    String redirectToNewEntityPage(Model model);

    String createNewEntity(REQ reqDto);

    String deleteEntity(ID id);

    String getEntityDetails(ID id, Model model);
}
