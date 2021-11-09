package ua.com.alevel.entity;

import ua.com.alevel.db.impl.AuthorsImpl;
import ua.com.alevel.db.impl.BookInstancesImpl;
import ua.com.alevel.db.impl.GenresImpl;
import ua.com.alevel.util.CustomUtil;

import java.util.Arrays;

public class Book extends BaseEntity {

    private final int arraySize = 10;
    private String title;
    private Integer authorId;
    private String summary;
    private String isbn;
    private Integer genreId;
    private Integer[] instancesIds = new Integer[arraySize];

    public void addInstance(Integer instanceId) {
        int instancesIdsAmount = 0;
        for (Integer value : instancesIds) {
            if (value != null) {
                instancesIdsAmount++;
            }
        }
        if (!(instancesIds.length > instancesIdsAmount)) {
            instancesIds = Arrays.copyOf(instancesIds, instancesIds.length + arraySize);
        }
        instancesIds[instancesIdsAmount] = instanceId;
    }

    public boolean removeInstance(Integer instanceId) {
        for (int position = 0; position < instancesIds.length; position++) {
            if (instancesIds[position] == instanceId) {
                for (; position < instancesIds.length - 1; position++) {
                    instancesIds[position] = instancesIds[position + 1];
                }
                instancesIds = Arrays.copyOf(instancesIds, (instancesIds.length - 1));
                return true;
            }
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Integer[] getInstancesIds() {
        return instancesIds;
    }

    public void setInstancesIds(Integer[] instancesIds) {
        this.instancesIds = instancesIds;
    }

    private String[] bookInstances() {
        String[] bookInstances = new String[0];
        for (int index = 0; index < this.instancesIds.length; index++) {
            if (this.instancesIds[index] != null) {
                bookInstances = Arrays.copyOf(bookInstances, bookInstances.length + 1);
                bookInstances[bookInstances.length - 1] = BookInstancesImpl.getInstance().findById(this.instancesIds[index]).toStringShort();
            }
        }
        return bookInstances;
    }

    public String toStringBrief() {
        return title;
    }

    public String toStringBriefWithName() {
        return "(ISBN: " + this.getIsbn() + ") \"" +
                title +
                "\", by " +
                AuthorsImpl.getInstance().findById(this.authorId).getFullName();
    }

    @Override
    public String toString() {
        return '\"' +
                title + '\"' +
                ", by " +
                AuthorsImpl.getInstance().findById(this.authorId).getFullName() +
                '.' +
                System.lineSeparator() + System.lineSeparator() +
                summary +
                System.lineSeparator() + System.lineSeparator() +
                "ISBN: " + isbn +
                System.lineSeparator() + System.lineSeparator() +
                "Genre: " +
                GenresImpl.getInstance().findById(this.genreId).getName() +
                System.lineSeparator() +
                "Instances: " + System.lineSeparator() +
                CustomUtil.composeStringArray(bookInstances());
    }
}
