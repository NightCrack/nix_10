package ua.com.alevel.service;

import ua.com.alevel.dao.FiguresDAO;
import ua.com.alevel.entity.Figure;

public class FiguresService {

    private final FiguresDAO figuresDAO = new FiguresDAO();

    public Figure[] findAll() {

        return figuresDAO.findAll();
    }

    public void create(Figure figure) {

        figuresDAO.create(figure);
    }

    public Figure findById(String id) {

        return figuresDAO.findById(id);
    }

    public void update(Figure figure) {

        figuresDAO.update(figure);
    }

    public void delete(String id) {

        figuresDAO.delete(id);
    }
}
