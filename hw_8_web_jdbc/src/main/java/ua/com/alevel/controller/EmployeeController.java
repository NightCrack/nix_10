package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.dto.employee.EmployeeRequestDto;
import ua.com.alevel.dto.employee.EmployeeResponseDto;
import ua.com.alevel.facade.EmployeeFacade;

import java.util.List;

@Controller
@RequestMapping("/employees")
public class EmployeeController implements BaseController<EmployeeRequestDto, Long> {

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
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("employee", new EmployeeRequestDto());
        return "pages/employees/employees_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("employee") EmployeeRequestDto dto) {
        employeeFacade.create(dto);
        return "redirect:/employees";
    }

    @Override
    public String deleteEntity(Long aLong) {
        return null;
    }

    @Override
    public String getEntityDetails(Long aLong, Model model) {
        return null;
    }
}
