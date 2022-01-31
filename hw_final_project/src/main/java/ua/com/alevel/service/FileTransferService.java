package ua.com.alevel.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileTransferService {

    public void init();

    public void save(MultipartFile multipartFile);

    public Resource load(String filename);

    public Stream<Path> loadAll();
}
