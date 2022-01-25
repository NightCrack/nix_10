package ua.com.alevel.view.controller.impl;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.view.controller.DependentController;
import ua.com.alevel.view.dto.request.EmployeeRequestDto;
import ua.com.alevel.view.dto.response.EmployeeResponseDto;
import ua.com.alevel.facade.EmployeeFacade;
import ua.com.alevel.view.dto.response.PageData;

@Controller
@RequestMapping("/employees")
public class EmployeeController extends BaseControllerImpl<EmployeeRequestDto, Long> implements DependentController<EmployeeRequestDto, Long, Long> {

    private final EmployeeFacade employeeFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("First name", "firstName", "first_name"),
            new HeaderName("Last name", "lastName", "last_name"),
            new HeaderName("Age", null, null),
            new HeaderName("Department ID", "department", "department_id"),
            new HeaderName("Details", null, null),
            new HeaderName("Delete", null, null)
    };

    public EmployeeController(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<EmployeeResponseDto> response = employeeFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/employees/all");
        model.addAttribute("createNew", "/employees/new");
        model.addAttribute("cardHeader", "Employees");
        return "pages/employees/employees_all";
    }

    @Override
    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "employees");
    }

    @Override
    @GetMapping("/departments/{departmentId}")
    public String findAllByEntity(@PathVariable Long departmentId, Model model) {
        model.addAttribute("employees", employeeFacade.findAllByForeignId(departmentId));
        return "/pages/employees/employees_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model, WebRequest request) {
//        model.addAttribute("employee", new EmployeeRequestDto());
//        model.addAttribute("departments", departmentFacade.findAll());
        return "pages/employees/employees_new";
    }

    @Override
    @GetMapping("/new/{departmentId}")
    public String redirectToNewEntityPageWithParentId(@PathVariable Long departmentId, Model model) {
        model.addAttribute("employee", new EmployeeRequestDto(departmentId));
        return "pages/employees/employees_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("employee") EmployeeRequestDto dto) {
        employeeFacade.create(dto);
        return "redirect:/employees";
    }

    @Override
    @GetMapping("/delete/{id}")
    public String deleteEntity(@PathVariable Long id) {
        employeeFacade.delete(id);
        return "redirect:/employees";

    }

    @Override
    @GetMapping("/details/{id}")
    public String getEntityDetails(@PathVariable Long id, Model model) {
        EmployeeResponseDto dto = employeeFacade.findById(id);
        model.addAttribute("employee", dto);
        return "pages/employees/employees_details";
    }
}
