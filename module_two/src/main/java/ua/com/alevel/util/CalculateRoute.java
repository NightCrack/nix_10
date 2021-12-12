package ua.com.alevel.util;

import ua.com.alevel.db.impl.CitiesImpl;
import ua.com.alevel.entity.City;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CalculateRoute {

    public static List<Number> calculate(String path) throws IOException {
        CitiesImpl cities = new CitiesImpl();
        List<String> input = FileOps.read(path);
        int citiesAmount = Integer.parseInt(input.get(0));
        int citiesNumberLine = 1;
        int cityNameLine = 1;
        int neighboursNumberLine = 1;
        int currentLine = citiesNumberLine;
        for (int indexA = 0; indexA < citiesAmount; indexA++) {
            int neighboursCount = Integer.parseInt(input.get(currentLine + cityNameLine));
            City city = new City();
            city.setName(input.get(currentLine));
            city.setNeighbourCount(neighboursCount);
            currentLine += (cityNameLine + neighboursNumberLine);
            LinkedHashMap<Long, BigDecimal> travelCost = new LinkedHashMap<>();
            city.setTravelCost(travelCost);
            for (int indexB = currentLine; indexB < (currentLine + neighboursCount); indexB++) {
                String[] parsedTravelCost = input.get(indexB).split(" ");
                travelCost.put(Long.valueOf(parsedTravelCost[0]), new BigDecimal(parsedTravelCost[1]));
            }
            currentLine += neighboursCount;
            cities.create(city);
        }
//        String check = input.get(currentLine);
        int routesAmount = Integer.parseInt(input.get(currentLine));
        int routesAmountLine = 1;
        currentLine += routesAmountLine;
        List<Number> returnList = new ArrayList<>();
        for (int index = currentLine; index < (currentLine + routesAmount); index++) {
            returnList.add(findPrice(cities, input.get(index)));
        }
        return returnList;
    }

    private static Number findPrice(CitiesImpl cities, String route) {
        String[] cityNames = route.split(" ");
        City from = null;
        City to = null;
        for (City city : cities.find()) {
            if (cityNames[0].equals(city.getName())) {
                from = city;
            } else if (cityNames[1].equals(city.getName())) {
                to = city;
            }
        }
        List<LinkedHashMap<Long, BigDecimal>> routes = possibleRotes(cities, from, to, new LinkedHashMap<>());
        int[] costs = new int[routes.size()];
        int index = 0;
        for (LinkedHashMap<Long, BigDecimal> possibleRote : routes) {
            for (Map.Entry<Long, BigDecimal> stop : possibleRote.entrySet()) {
                costs[index] += stop.getValue().intValue();
            }
            index++;
        }
        int returnValue = costs[0];
        for (index = 0; index < costs.length; index++) {
            if (returnValue > costs[index]) {
                returnValue = costs[index];
            }
        }
        return returnValue;
    }

    private static List<LinkedHashMap<Long, BigDecimal>> possibleRotes(CitiesImpl cities, City from, City to, LinkedHashMap<Long, BigDecimal> route) {
        List<LinkedHashMap<Long, BigDecimal>> routes = new ArrayList<>();
        for (Map.Entry<Long, BigDecimal> neighbour : from.getTravelCost().entrySet()) {
            if (!route.containsKey(neighbour.getKey())) {
                LinkedHashMap<Long, BigDecimal> newRoute = new LinkedHashMap<>(route);
                newRoute.put(neighbour.getKey(), neighbour.getValue());
                if (neighbour.getKey().equals(to.getId())) {
                    routes.add(newRoute);
                } else {
                    routes.addAll(possibleRotes(cities, cities.find(neighbour.getKey()), to, newRoute));
                }
            }
        }
        return routes;
    }
}
