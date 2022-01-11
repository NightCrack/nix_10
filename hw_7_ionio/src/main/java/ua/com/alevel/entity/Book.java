package ua.com.alevel.entity;

import custom.annotations.Entity;
import custom.annotations.SetClass;
import ua.com.alevel.db.AuthorsDB;
import ua.com.alevel.db.BookInstancesDB;
import ua.com.alevel.db.GenresDB;

import java.util.ArrayList;
import java.util.List;

@Entity
public final class Book extends BaseEntity {

    @SetClass
    private transient static Class bookInstancesDB;
    @SetClass
    private transient static Class authorsDB;
    @SetClass
    private transient static Class genresDB;

    private List<Long> instancesIds = new ArrayList<>();
    private Long genreId;
    private String summary;
    private Long authorId;
    private String name;
    private String isbn;

    public List<Long> getInstancesIds() {
        return instancesIds;
    }

    public void setInstancesIds(List<Long> instancesIds) {
        this.instancesIds = instancesIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
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

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    public Book addInstance(Long instanceId) {
        this.instancesIds.add(instanceId);
        return this;
    }

    public boolean removeInstance(Long instanceId) {
        return this.instancesIds.remove(instanceId);
    }

    private String bookInstances() {
        StringBuilder returnValue = new StringBuilder();
        try {
            for (Long instance : instancesIds) {
                if (instance != null && instance != 0) {
                    returnValue.append(System.lineSeparator())
                            .append(bookInstanceName(instance).toStringShort());
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return returnValue.toString();
    }

    private BookInstance bookInstanceName(Long instance) {
        try {
            return (BookInstance) ((BookInstancesDB) (bookInstancesDB
                    .getDeclaredConstructor()
                    .newInstance()))
                    .findById(instance);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Author findAuthor() {
        try {
            return (Author) ((AuthorsDB) (authorsDB
                    .getDeclaredConstructor()
                    .newInstance()))
                    .findById(this.authorId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    private Genre findGenre() {
        try {
            return (Genre) ((GenresDB) (genresDB
                    .getDeclaredConstructor()
                    .newInstance()))
                    .findById(this.genreId);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }


    public String toStringBrief() {
        return "(ISBN: " + this.getIsbn() + ") \"" +
                name +
                "\", by " + findAuthor().getName();
    }

    @Override
    public String toString() {
        return System.lineSeparator() +
                '\"' +
                name + '\"' +
                ", by " +
                findAuthor().getName() +
                '.' +
                System.lineSeparator() +
                summary +
                System.lineSeparator() +
                "ISBN: " + isbn +
                System.lineSeparator() +
                "Genre: " +
                findGenre().getName() +
                System.lineSeparator() +
                "Instances: " +
                bookInstances();
    }
}
