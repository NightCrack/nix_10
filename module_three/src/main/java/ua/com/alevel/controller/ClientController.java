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
import ua.com.alevel.dto.request.ClientRequestDto;
import ua.com.alevel.dto.response.ClientResponseDto;
import ua.com.alevel.dto.response.PageData;
import ua.com.alevel.facade.ActionFacade;
import ua.com.alevel.facade.ClientFacade;

import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/clients")
public class ClientController extends BaseController {

    private final ClientFacade clientFacade;
    private final ActionFacade actionFacade;
    private final HeaderName[] columnNames = new HeaderName[]{
            new HeaderName("#", null, null),
            new HeaderName("Name", "full_name", "fullName"),
            new HeaderName("Email", "email", "email"),
            new HeaderName("Amount of accounts", null, null),
            new HeaderName("Details", null, null),
            new HeaderName("Update", null, null),
            new HeaderName("Delete", null, null)
    };
    private Long idToUpdate = 0L;

    public ClientController(ClientFacade clientFacade, ActionFacade actionFacade) {
        this.clientFacade = clientFacade;
        this.actionFacade = actionFacade;
    }

    @GetMapping
    public String findAll(Model model, WebRequest request) {
        PageData<ClientResponseDto> response = clientFacade.findAll(request);
        initDataTable(response, columnNames, model);
        model.addAttribute("createUrl", "/clients/all");
        model.addAttribute("createNew", "/clients/new");
        model.addAttribute("cardHeader", "All Clients");
        return "pages/clients/clients_all";
    }

    @PostMapping("/all")
    public ModelAndView findAllRedirect(WebRequest request, ModelMap model) {
        return findAllRedirect(request, model, "clients");
    }

    @GetMapping("/new")
    public String redirectToNewUserPage(Model model) {
        model.addAttribute("client", new ClientRequestDto());
        return "pages/clients/clients_new";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute("client") ClientRequestDto dto) {
        try {
            clientFacade.create(dto);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "redirect:/clients";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        clientFacade.delete(id);
        return "redirect:/clients";
    }

    @GetMapping("/update/{id}")
    public String update(@ModelAttribute("client") ClientRequestDto dto, @PathVariable Long id) {
        idToUpdate = id;
        return "pages/clients/clients_update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute("client") ClientRequestDto dto) {
        clientFacade.update(dto, idToUpdate);
        return "redirect:/clients";
    }

    @GetMapping("/details/{id}")
    public String findById(@PathVariable Long id, Model model) {
        model.addAttribute("client", clientFacade.findById(id));
        model.addAttribute("accounts", clientFacade.findAllForeignEntriesById(id));
        return "pages/clients/clients_details";
    }

    @GetMapping("/download/{clientId}")
    public ResponseEntity<InputStreamResource> getAnExtract(@PathVariable Long clientId) {
        String time = Instant.now().atZone(ZoneOffset.ofHours(+2))
                .format(DateTimeFormatter.ofPattern("MMM-d-yy"));
        String output = actionFacade.writeOutBySecondForeignId(clientId);
        MediaType mediaType = MediaType.parseMediaType("application/octet-stream");
        ResponseEntity<InputStreamResource> returnValue = null;
        try {
            InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(output.getBytes()));
            returnValue = ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" +
                            clientFacade.findById(clientId).getName() + " (" +
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
