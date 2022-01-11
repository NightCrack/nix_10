package ua.com.alevel.db.impl;


import custom.annotations.Autowired;
import custom.annotations.Path;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import ua.com.alevel.db.GenresDB;
import ua.com.alevel.entity.Genre;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.FileOps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class GenresDBImpl implements GenresDB<Long> {

    @Path
    private static String tablePath;
    @Autowired
    private GenresDBImpl instance;
    private List<Genre> genres;
    private String classId = "id";

    public GenresDBImpl getInstance() {
        if (instance == null) {
            instance = new GenresDBImpl();
        }
        return instance;
    }

    @Override
    public String getPath() {
        return this.tablePath;
    }

    @Override
    public boolean create(Genre genre) {
        Long id = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), genre.getClass().getSimpleName())) + 1;
        ResourcesUtil.setLastId(this.getClass(), genre.getClass().getSimpleName(), String.valueOf(id));
        genre.setId(id);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(Collections.singletonList(genre)), true);
    }

    @Override
    public boolean update(Genre genre) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        int entryLine = idList.indexOf(genre.getId().toString()) + 1;
        genres = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Genre.class);
        genres.addAll((List<Genre>) entries);
        genres.add(genre);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Genre.class);
        genres.addAll((List<Genre>) entries);
        FileOps.write(tablePath, CSVTranslator
                .buildOutput(Collections.singletonList(CSVTranslator
                        .buildHeader(Genre.class))), false);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(genres), true);
    }

    @Override
    public boolean delete(Long id) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        if (!idList.contains(String.valueOf(id))) {
            return false;
        }
        int entryLine = idList.indexOf(id.toString()) + 1;
        genres = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), Genre.class);
        genres.addAll(entries);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), Genre.class);
        genres.addAll(entries);
        FileOps.write(tablePath, CSVTranslator
                .buildOutput(Collections
                        .singletonList(CSVTranslator
                                .buildHeader(Genre.class))), false);
        if (genres.size() > 0) {
            return FileOps.write(tablePath, CSVTranslator.objectsToString(genres), true);
        }
        return true;
    }

    @Override
    public Genre findById(Long id) {
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Genre.class);
        try {
            return ((List<Genre>) entries).stream()
                    .filter(genre -> genre.getId().equals(id))
                    .findFirst().get();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<Genre> findAll() {
        List formattedEntries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), Genre.class);
        return formattedEntries;
    }
}
