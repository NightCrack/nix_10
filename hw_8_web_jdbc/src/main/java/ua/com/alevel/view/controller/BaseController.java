package ua.com.alevel.view.controller;


import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.view.dto.request.RequestDto;

public interface BaseController<REQ extends RequestDto, ID> {

    String findAll(WebRequest webRequest, Model model);

    ModelAndView findAllRedirect(WebRequest request, ModelMap model);

    String redirectToNewEntityPage(WebRequest request, Model model);

    String redirectToEditPage(WebRequest request, ID id, Model model);

    String updateEntity(REQ reqDto);

    String createNewEntity(REQ reqDto);

    String deleteEntity(ID id);

    String getEntityDetails(WebRequest request, ID id, Model model);
}
