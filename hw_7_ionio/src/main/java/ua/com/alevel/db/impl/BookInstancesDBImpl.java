package ua.com.alevel.db.impl;

import custom.annotations.Path;
import custom.annotations.Service;
import custom.util.ResourcesUtil;
import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.entity.BookInstance;
import ua.com.alevel.util.CSVTranslator;
import ua.com.alevel.util.FileOps;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BookInstancesDBImpl implements BookInstancesDB<Long> {

    @Path
    private static String tablePath;
    private String classId = "id";
    private List<BookInstance> bookInstances;

    @Override
    public String getPath() {
        return this.tablePath;
    }

    @Override
    public boolean create(BookInstance bookInstance) {
        Long id = Long.parseLong(ResourcesUtil.getLastId(this.getClass(), bookInstance.getClass().getSimpleName())) + 1;
        ResourcesUtil.setLastId(this.getClass(), bookInstance.getClass().getSimpleName(), String.valueOf(id));
        bookInstance.setId(id);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(Collections.singletonList(bookInstance)), true);
    }

    @Override
    public boolean update(BookInstance bookInstance) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        int entryLine = idList.indexOf(bookInstance.getId().toString()) + 1;
        bookInstances = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), BookInstance.class);
        bookInstances.addAll(entries);
        bookInstances.add(bookInstance);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), BookInstance.class);
        bookInstances.addAll(entries);
        FileOps.write(tablePath, CSVTranslator.buildOutput(Collections.singletonList(CSVTranslator.buildHeader(BookInstance.class))), false);
        return FileOps.write(tablePath, CSVTranslator.objectsToString(bookInstances), true);
    }

    @Override
    public boolean delete(Long id) {
        List<String> idList = CSVTranslator.idList(tablePath, classId);
        if (!(idList.contains(String.valueOf(id)))) {
            return false;
        }
        int entryLine = idList.indexOf(id.toString()) + 1;
        bookInstances = new ArrayList<>();
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1, entryLine), BookInstance.class);
        bookInstances.addAll(entries);
        entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, entryLine + 1), BookInstance.class);
        bookInstances.addAll(entries);
        FileOps.write(tablePath, CSVTranslator.buildOutput(Collections.singletonList(CSVTranslator.buildHeader(BookInstance.class))), false);
        if (bookInstances.size() > 0) {
            return FileOps.write(tablePath, CSVTranslator.objectsToString(bookInstances), true);
        }
        return true;
    }

    @Override
    public BookInstance findById(Long id) {
        List entries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), BookInstance.class);
        try {
            return ((List<BookInstance>) entries).stream()
                    .filter(bookInstance -> bookInstance.getId().equals(id))
                    .findFirst().get();
        } catch (Exception exception) {
            return null;
        }
    }

    @Override
    public List<BookInstance> findAll() {
        List formattedEntries = CSVTranslator
                .buildNewEntitiesFromInput(FileOps
                        .read(tablePath, 1), BookInstance.class);
        return formattedEntries;
    }
}
