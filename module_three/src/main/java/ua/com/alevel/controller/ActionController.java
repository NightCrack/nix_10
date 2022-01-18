package ua.com.alevel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.dto.request.ActionRequestDto;
import ua.com.alevel.dto.response.ActionResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.facade.ActionFacade;
import ua.com.alevel.service.AccountService;

import java.sql.SQLException;

@Controller
@RequestMapping("/actions")
public class ActionController extends BaseController {

    private static Long accountId = 0L;
    private final ActionFacade actionFacade;
    private final AccountService accountService;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Action type", "name", "name"),
            new HeaderName("Client name", null, null),
            new HeaderName("Account name", null, null),
            new HeaderName("Amount", "amount", "amount"),
            new HeaderName("Details", null, null)
    };

    public ActionController(ActionFacade actionFacade, AccountService accountService) {
        this.actionFacade = actionFacade;
        this.accountService = accountService;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ActionResponseDto> response = actionFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/actions/all");
        model.addAttribute("createNew", "/actions/new");
        model.addAttribute("cardHeader", "All Actions");
        return "pages/clients/clients_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "actions");
    }

    @GetMapping("/new/{id}")
    public String redirectToNewOperationPage(@PathVariable Long id, Model model) {
        accountId = id;
        System.out.println("accountId = " + accountId);
        model.addAttribute("action", new ActionRequestDto());
        return "pages/actions/actions_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("action") ActionRequestDto dto) throws SQLException {
        dto.setAccount(accountService.findById(accountId));
        actionFacade.create(dto);
        return "redirect:/accounts/details/" + accountId;
    }
}
