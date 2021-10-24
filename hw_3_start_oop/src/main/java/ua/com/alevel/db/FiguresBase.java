package ua.com.alevel.db;

import ua.com.alevel.entity.Figure;

import java.util.Arrays;
import java.util.UUID;

public class FiguresBase {

    private final int arraySize = 5;
    private Figure[] figures;
    private static FiguresBase instance;

    private String generateId() {
        String id = UUID.randomUUID().toString();
            for (Figure figure : figures) {

                if (figure != null) {

                    if (figure.getId().equals(id)) {

                        return generateId();
                    }
                }
            }
        return id;
    }

    private FiguresBase() {

        figures = new Figure[arraySize];
    }

    public static FiguresBase getInstance() {

        if(instance == null) {

            instance = new FiguresBase();
        }
        return instance;
    }

    public Figure[] findAll() {

        return figures;
    }

    public void create(Figure figure) {

        figure.setId(generateId());
        int figuresAmount = 0;
        for (Figure value : figures) {

            if (value != null) {

                ++figuresAmount;
            }
        }
        if (!(figures.length > figuresAmount)) {
            figures = Arrays.copyOf(figures, (figures.length + arraySize));
        } else {

            figures[figuresAmount] = figure;
        }
    }

    public Figure findById(String id) {

        for (Figure figure : figures) {

            if (figure.getId().equals(id)) {

                return figure;
            }
        }
        System.out.println("Figure not found.");
        return null;
    }

    public void update(Figure figure) {

        Figure current = findById(figure.getId());
        current.setName(figure.getName());
        current.setShape(figure.getShape());
        current.setLength(figure.getLength());
        current.setHeight(figure.getHeight());
        current.setWidth(figure.getWidth());
        current.setVolume(figure.getVolume());

    }

    public String delete(String id) {

        for(int position = 0; position < figures.length; position++) {

            if(figures[position].getId().equals(id)) {

                for(; position < figures.length - 1; position++) {

                    figures[position] = figures[position + 1];
                }
                figures = Arrays.copyOf(figures, (figures.length - 1));
                return "Figure deleted.";
            }
        }
        return "Figure with indicated ID doesn't exist.";
    }
}
