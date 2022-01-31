package ua.com.alevel.view.controller.impl;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import ua.com.alevel.service.FileTransferService;
import ua.com.alevel.view.dto.request.FileInfo;
import ua.com.alevel.view.dto.response.ResponseMessage;

import java.util.List;

@Controller
//@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/transmit")
public class FileTransferController {

    private final FileTransferService fileTransferService;

    public FileTransferController(FileTransferService fileTransferService) {
        this.fileTransferService = fileTransferService;
    }

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";
        try {
            fileTransferService.save(file);
            message = "Uploaded the file successfully: " +
                    file.getOriginalFilename();
            return ResponseEntity.status(HttpStatus
                    .OK).body(new ResponseMessage(message));
        } catch (Exception exception) {
            message = "Couldn't upload the file: " + file.getOriginalFilename() + "!";
            return ResponseEntity.status(HttpStatus
                            .EXPECTATION_FAILED)
                    .body(new ResponseMessage(message));
        }
    }

    @GetMapping("/files")
    public ResponseEntity<List<FileInfo>> getListFiles() {
        List<FileInfo> filesInfo = fileTransferService
                .loadAll()
                .map(path -> {
                    String fileName = path.getFileName().toString();
                    String url = MvcUriComponentsBuilder
                            .fromMethodName(FileTransferController
                                    .class, "getFile", fileName)
                            .build()
                            .toString();
                    return new FileInfo(fileName, url);
                }).toList();
        return ResponseEntity.status(HttpStatus.OK).body(filesInfo);
    }

    @GetMapping("/files/{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = fileTransferService.load(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders
                        .CONTENT_DISPOSITION, "attachment; filename=\"" +
                        file.getFilename() + "\"")
                .body(file);
    }
}
