package ua.com.alevel.db.impl;

import custom.annotations.Path;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import ua.com.alevel.db.AuthorsDB;
import ua.com.alevel.entity.Author;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.FileOps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AuthorsDBImpl implements AuthorsDB<Long> {

    @Path
    private static String tablePath;
    private List<Author> authors;
    private String classId = "id";

    @Override
    public String getPath() {
        return this.tablePath;
    }

    @Override
    public boolean create(Author author) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        Long id = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), author.getClass().getSimpleName())) + 1;
        ResourcesUtil.setLastId(this.getClass(), author.getClass().getSimpleName(), String.valueOf(id));
        author.setId(id);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(Collections.singletonList(author)), true);
    }

    @Override
    public boolean update(Author author) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        int entryLine = idList.indexOf(author.getId().toString()) + 1;
        authors = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Author.class);
        authors.addAll(entries);
        authors.add(author);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Author.class);
        authors.addAll(entries);
        FileOps.write(tablePath, CSVTranslator.buildOutput(Collections.singletonList(CSVTranslator.buildHeader(Author.class))), false);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(authors), true);
    }

    @Override
    public boolean delete(Long id) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        if (!idList.contains(String.valueOf(id))) {
            return false;
        }
        int entryLine = idList.indexOf(id.toString()) + 1;
        authors = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Author.class);
        authors.addAll(entries);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Author.class);
        authors.addAll(entries);
        FileOps.write(tablePath, CSVTranslator.buildOutput(Collections.singletonList(CSVTranslator.buildHeader(Author.class))), false);
        if (authors.size() > 0) {
            return FileOps.write(tablePath, CSVTranslator.objectsToString(authors), true);
        }
        return true;
    }

    @Override
    public Author findById(Long id) {
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Author.class);
        try {
            return ((List<Author>) entries).stream()
                    .filter(author -> author.getId().equals(id))
                    .findFirst().get();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<Author> findAll() {
        List formattedEntries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Author.class);
        return formattedEntries;
    }
}
