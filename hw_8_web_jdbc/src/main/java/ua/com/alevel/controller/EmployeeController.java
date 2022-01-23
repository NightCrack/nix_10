package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.employee.EmployeeRequestDto;
import ua.com.alevel.dto.employee.EmployeeResponseDto;
import ua.com.alevel.facade.EmployeeFacade;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController implements DependentController<EmployeeRequestDto, Long, Long> {

    private final EmployeeFacade employeeFacade;

    public EmployeeController(EmployeeFacade employeeFacade) {
        this.employeeFacade = employeeFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<EmployeeResponseDto> employees = employeeFacade.findAll();
        model.addAttribute("employees", employees);
        return "pages/employees/employees_all";
    }

    @Override
    @GetMapping("/departments/{departmentId}")
    public String findAllByEntity(@PathVariable Long departmentId, Model model) {
        model.addAttribute("employees", employeeFacade.findAllByDepartment(departmentId));
        return "/pages/employees/employees_all";
    }

    @Override
    public String redirectToNewEntityPage(Model model) {
        return null;
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