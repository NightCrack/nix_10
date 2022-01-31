package ua.com.alevel.service.impl;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ua.com.alevel.service.FileTransferService;
import ua.com.alevel.util.FileOps;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public class FileTransferServiceImpl implements FileTransferService {

    private final Path root = Path.of(System.getProperty("user.dir") + FileSystems.getDefault().getSeparator() + "uploads");

    @Override
    public void init() {
        FileOps.createDirs(root.toString());
    }

    @Override
    public void save(MultipartFile multipartFile) {
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = this.root.resolve(multipartFile.getOriginalFilename());
            Files.copy(inputStream, filePath);
        } catch (IOException exception) {
            throw new RuntimeException("Could not store the file. Error: " +
                    exception.getMessage());
        }
    }

    @Override
    public Resource load(String filename) {
        try {
            Path filePath = root.resolve(filename);
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException exception) {
            System.out.println("Error: " + exception.getMessage());
        }
        return null;
    }

    @Override
    public Stream<Path> loadAll() {
        try (Stream<Path> files = Files.walk(this.root)) {
            return files.filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException exception) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
