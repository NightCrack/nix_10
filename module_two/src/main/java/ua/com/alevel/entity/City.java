package ua.com.alevel.entity;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

public final class City extends BaseEntity {

    private String name;
    private Integer neighbourCount;
    private LinkedHashMap<Long, BigDecimal> travelCost = new LinkedHashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getNeighbourCount() {
        return neighbourCount;
    }

    public void setNeighbourCount(Integer neighbourCount) {
        this.neighbourCount = neighbourCount;
    }

    public LinkedHashMap<Long, BigDecimal> getTravelCost() {
        return travelCost;
    }

    public void setTravelCost(LinkedHashMap<Long, BigDecimal> travelCost) {
        this.travelCost = travelCost;
    }

    private String neighboursToString(LinkedHashMap<Long, BigDecimal> neighbours) {
        if (neighbours != null) {
            StringBuilder returnValue = new StringBuilder();
            for (Map.Entry<Long, BigDecimal> neighbour :
                    neighbours.entrySet()) {
                returnValue
                        .append(neighbour.getKey())
                        .append(" ")
                        .append(neighbour.getValue())
                        .append(System.lineSeparator());
            }
            return returnValue.toString();
        }
        return null;
    }

    @Override
    public String toString() {
        return name +
                System.lineSeparator() + neighbourCount +
                System.lineSeparator() + neighboursToString(travelCost);
    }
}
