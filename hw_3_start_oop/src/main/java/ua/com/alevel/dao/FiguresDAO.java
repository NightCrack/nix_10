package ua.com.alevel.dao;

import ua.com.alevel.db.FiguresBase;
import ua.com.alevel.entity.Figure;

public class FiguresDAO {

    public Figure[] findAll() {

        return FiguresBase.getInstance().findAll();
    }

    public void create (Figure figure) {

        FiguresBase.getInstance().create(figure);
    }

    public Figure findById(String id) {

        return FiguresBase.getInstance().findById(id);
    }

    public void update (Figure figure) {

        FiguresBase.getInstance().update(figure);
    }

    public void delete(String id) {

        FiguresBase.getInstance().delete(id);
    }
}
