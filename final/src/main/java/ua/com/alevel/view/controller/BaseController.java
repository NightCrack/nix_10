package ua.com.alevel.view.controller;


import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.view.dto.request.RequestDto;

public interface BaseController<REQ extends RequestDto, ID> {

    String findAll(Model model, WebRequest webRequest);

    ModelAndView findAllRedirect(WebRequest request, ModelMap model);

    String redirectToNewEntityPage(Model model, WebRequest request);

    String createNewEntity(REQ reqDto);

    String deleteEntity(ID id);

    String getEntityDetails(ID id, Model model);
}
