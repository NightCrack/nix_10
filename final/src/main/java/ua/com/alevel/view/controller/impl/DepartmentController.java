package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.view.controller.BaseController;
import ua.com.alevel.view.dto.request.DepartmentRequestDto;
import ua.com.alevel.view.dto.response.DepartmentResponseDto;
import ua.com.alevel.facade.DepartmentFacade;
import ua.com.alevel.type.DepartmentType;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/departments")
public class DepartmentController extends BaseControllerImpl<DepartmentRequestDto, Long> implements BaseController<DepartmentRequestDto, Long> {

    private final DepartmentFacade departmentFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Department name", "name", "name"),
            new HeaderName("Type", "departmentType", "department_type"),
            new HeaderName("Employees number", "employeesNumber", "employees"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public DepartmentController(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<DepartmentResponseDto> response = departmentFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/departments/all");
        model.addAttribute("createNew", "/departments/new");
        model.addAttribute("cardHeader", "Departments");
        return "pages/departments/departments_all";
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "departments");
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model, WebRequest request) {
        model.addAttribute("department", new DepartmentRequestDto());
        model.addAttribute("types", DepartmentType.values());
        return "pages/departments/departments_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("department") DepartmentRequestDto dto) {
        departmentFacade.create(dto);
        return "redirect:/departments";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        departmentFacade.delete(id);
        return "redirect:/departments";

    }

    @Override
    @GetMapping("/details/{id}")
    public String getEntityDetails(@PathVariable Long id, Model model) {
        DepartmentResponseDto dto = departmentFacade.findById(id);
        model.addAttribute("department", dto);
        return "pages/departments/departments_details";

    }
}
