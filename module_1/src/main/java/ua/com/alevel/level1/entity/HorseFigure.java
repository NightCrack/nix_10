package ua.com.alevel.level1.entity;

import ua.com.alevel.level1.util.CoordinateEncoder;

public class HorseFigure extends BaseEntity {

    private String horseCoordinateX;

    public String getHorseCoordinateX() {
        return CoordinateEncoder.alphabeticalCoordinateGeneration(super.getCoordinateX());
    }

    public void setHorseCoordinateX(String horseCoordinateX) {
        this.horseCoordinateX = horseCoordinateX;
        super.setCoordinateX(CoordinateEncoder.alphabeticalCoordinateDecoding(horseCoordinateX));
    }

    @Override
    public String toString() {
        return "Square(" + horseCoordinateX + super.getCoordinateY() + ") ";
    }
}
