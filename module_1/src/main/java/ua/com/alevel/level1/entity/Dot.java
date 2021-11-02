package ua.com.alevel.level1.entity;

public class Dot extends BaseEntity {

    @Override
    public String toString() {
        return "Dot(" + super.getCoordinateX() + super.getCoordinateY() + ") ";
    }
}
