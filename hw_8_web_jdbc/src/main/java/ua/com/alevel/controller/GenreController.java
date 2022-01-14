package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.alevel.dto.genre.GenreRequestDto;
import ua.com.alevel.dto.genre.GenreResponseDto;
import ua.com.alevel.facade.GenreFacade;
import ua.com.alevel.type.GenreType;

import java.util.List;

@Controller
@RequestMapping("/genres")
public class GenreController implements BaseController<GenreRequestDto, Long> {

    private final GenreFacade genreFacade;

    public GenreController(GenreFacade genreFacade) {
        this.genreFacade = genreFacade;
    }

    @Override
    @GetMapping
    public String findAll(Model model) {
        List<GenreResponseDto> genres = genreFacade.findAll();
        model.addAttribute("genres", genres);
        return "pages/genres/genres_all";
    }

    @Override
    @GetMapping("/new")
    public String redirectToNewEntityPage(Model model) {
        model.addAttribute("genre", new GenreRequestDto());
        model.addAttribute("types", GenreType.values());
        return "pages/genres/genres_new";
    }

    @Override
    @PostMapping("/new")
    public String createNewEntity(@ModelAttribute("genre") GenreRequestDto dto) {
        genreFacade.create(dto);
        return "redirect:/genres";
    }

    @Override
    public String deleteEntity(Long aLong) {
        return null;
    }

    @Override
    public String getEntityDetails(Long aLong, Model model) {
        return null;
    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteDepartment(@PathVariable Long id) {
//        departmentFacade.delete(id);
//        return "redirect:/departments";
//
//    }
//
//    @GetMapping("/details/{id}")
//    public String getDepartmentDetails(@PathVariable Long id, Model model) {
//        DepartmentResponseDto dto = departmentFacade.findById(id);
//        model.addAttribute("department",dto);
//        return "pages/departments/departments_details";
//
//    }
}
