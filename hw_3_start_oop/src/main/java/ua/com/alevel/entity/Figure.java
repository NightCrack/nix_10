package ua.com.alevel.entity;

import java.math.BigDecimal;

public class Figure {

    private String name;
    private String shape;
    private BigDecimal length;
    private BigDecimal height;
    private BigDecimal width;
    private BigDecimal volume;
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

    public BigDecimal getLength() {
        return length;
    }

    public void setLength(BigDecimal length) {
        this.length = length;
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWidth() {
        return width;
    }

    public void setWidth(BigDecimal width) {
        this.width = width;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "name='" + name + '\'' +
                ", shape='" + shape + '\'' +
                ", length=" + length +
                ", height=" + height +
                ", width=" + width +
                ", volume=" + volume +
                ", id='" + id + '\'' +
                '}';
    }
}
