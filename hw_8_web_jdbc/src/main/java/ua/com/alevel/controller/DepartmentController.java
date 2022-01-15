package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.alevel.dto.department.DepartmentRequestDto;
import ua.com.alevel.dto.department.DepartmentResponseDto;
import ua.com.alevel.facade.DepartmentFacade;
import ua.com.alevel.type.DepartmentType;

import java.util.List;

@Controller
@RequestMapping("/departments")
public class DepartmentController implements BaseController<DepartmentRequestDto, Long, Object> {

    private final DepartmentFacade departmentFacade;

    public DepartmentController(DepartmentFacade departmentFacade) {
        this.departmentFacade = departmentFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<DepartmentResponseDto> departments = departmentFacade.findAll();
        model.addAttribute("departments", departments);
        return "pages/departments/departments_all";
    }

    @Override
    public String findAllByEntity(Object o, Model model) {
        return null;
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
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
