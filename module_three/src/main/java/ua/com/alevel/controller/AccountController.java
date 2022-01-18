package ua.com.alevel.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import ua.com.alevel.dto.request.AccountRequestDto;
import ua.com.alevel.dto.response.AccountResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.facade.AccountFacade;
import ua.com.alevel.facade.ActionFacade;
import ua.com.alevel.facade.ClientFacade;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/accounts")
public class AccountController extends BaseController {
    private final ClientFacade clientFacade;
    private final AccountFacade accountFacade;
    private final ActionFacade actionFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("ID", "id", "id"),
            new HeaderName("Name", "account_name", "accountName"),
            new HeaderName("Balance", "balance", "balance"),
            new HeaderName("Client's email", null, null),
            new HeaderName("Details", null, null),
            new HeaderName("Interact", null, null),
            new HeaderName("Delete", null, null)
    };
    private Long idToUpdate = 0L;
    private Long idToCreate = 0L;

    public AccountController(ClientFacade clientFacade, AccountFacade accountFacade, ActionFacade actionFacade) {
        this.clientFacade = clientFacade;
        this.accountFacade = accountFacade;
        this.actionFacade = actionFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<AccountResponseDto> response = accountFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/accounts/all");
        model.addAttribute("createNew", "/accounts/new");
        model.addAttribute("cardHeader", "All Accounts");
        return "pages/accounts/accounts_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "accounts");
    }

    @GetMapping("/new/{id}")
    public String redirectToNewAccountPage(@PathVariable Long id, Model model, WebRequest request) {
        idToCreate = id;
        model.addAttribute("account", new AccountRequestDto());
        model.addAttribute("clients", clientFacade.findAll(request));
        return "pages/accounts/accounts_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("account") AccountRequestDto dto) {
        dto.setClientId(idToCreate);
        System.out.println("dto = " + dto);
        try {
            accountFacade.create(dto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/accounts";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        accountFacade.delete(id);
        return "redirect:/accounts";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("account") AccountRequestDto dto) {
        accountFacade.update(dto, idToUpdate);
        return "redirect:/accounts";
    }

    @GetMapping("/details/{id}")
    public String redirectToNewAuthorPage(@PathVariable Long id, Model model) {
        model.addAttribute("account", accountFacade.findById(id));
        model.addAttribute("client", accountFacade.findForeignEntityById(id));
        return "pages/accounts/accounts_details";
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<InputStreamResource> getAnExtract(@PathVariable Long id) {
        String time = Instant.now().atZone(ZoneOffset.ofHours(+2))
                .format(DateTimeFormatter.ofPattern("MMM-d-yy"));
        String output = actionFacade.writeOutByForeignId(id);
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        ResponseEntity<InputStreamResource> returnValue = null;
        try {
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(output.getBytes()));
            returnValue = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                            accountFacade.findById(id).getAccountName() + " (" +
                            time +
                            ").csv")
                    .contentType(mediaType)
                    .contentLength(output.length())
                    .body(resource);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnValue;
    }
}
