package ua.com.alevel.util;

import ua.com.alevel.data.CustomData;
import ua.com.alevel.db.impl.CitiesImpl;
import ua.com.alevel.entity.City;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GenerateCity {

    public void generate(String path, int citiesNumber, int routesNumber) {
        CitiesImpl cities = new CitiesImpl();
        FileOps.write(path, citiesNumber + System.lineSeparator(), false);
        for (int index = 0; index < citiesNumber; index++) {
            buildCity(cities);
        }
        StringBuilder toWrite = new StringBuilder();
        cities.find().forEach(city -> toWrite.append(city.toString()));
        FileOps.write(path, toWrite.toString(), true);
        FileOps.write(path, routesNumber + System.lineSeparator(), true);
        for (int index = 0; index < routesNumber; index++) {
            FileOps.write(path, generateRoute(cities) + System.lineSeparator(), true);
        }
    }

    private String generateRoute(CitiesImpl cities) {
        List<String> existing = new ArrayList<>();
        cities.find().forEach(city -> existing.add(city.getName()));
        StringBuilder returnString = new StringBuilder();
        int firstCity = (int) (Math.random() * (existing.size() - 1));
        int lastCity = 0;
        boolean flag = true;
        while (flag) {
            flag = false;
            lastCity = (int) (Math.random() * (existing.size() - 1));
            if (lastCity == firstCity) {
                flag = true;
            }
        }
        returnString.append(existing.get(firstCity)).append(" ").append(existing.get(lastCity));
        return returnString.toString();
    }

    private Long uniqueNeighbour(List<City> existingCities, Map<Long, BigDecimal> travelCost) {
        List<Long> presentRoutes = new ArrayList<>(travelCost.keySet());
        List<Long> cityIndexes = new ArrayList<>();
        existingCities.forEach(city -> cityIndexes.add(city.getId()));
        int random = (int) (Math.round(Math.random() * (existingCities.size() - 1)));
        boolean flag = true;
        while (flag) {
            flag = false;
            for (Long presentRoute : presentRoutes) {
                if (cityIndexes.get(random).equals(presentRoute)) {
                    random = (int) (Math.round(Math.random() * (existingCities.size() - 1)));
                    flag = true;
                    break;
                }
            }
        }
        return cityIndexes.get(random);
    }

    private void buildCity(CitiesImpl cities) {
        City city = new City();
        ArrayList<Long> possibleNeighbours = new ArrayList<>();
        if (cities.find() != null) {
            cities.find().forEach(existing -> possibleNeighbours.add(existing.getId()));
        }
        city.setName(CustomData.lastNamePool()[(int) (Math.random() * (CustomData.lastNamePool().length - 1))]);
        int neighbourCount = (int) (Math.round(Math.random() * possibleNeighbours.size()));
        city.setNeighbourCount(neighbourCount);
        LinkedHashMap<Long, BigDecimal> travelCost = new LinkedHashMap<>();
        for (int index = 0; index < neighbourCount; index++) {
            travelCost.put(uniqueNeighbour(cities.find(), travelCost), new BigDecimal((int) (Math.random() * 9 + 1)));
        }
        city.setTravelCost(travelCost);
        cities.create(city);
        if (travelCost.size() != 0) {
            List<Long> routes = new ArrayList<>(travelCost.keySet());
            for (Long route : routes) {
                City updatedCity = cities.find(route);
                Long key = cities.find().get(cities.find().size() - 1).getId();
                BigDecimal value = travelCost.get(route);
                updatedCity.getTravelCost().put(key, value);
                updatedCity.setNeighbourCount(updatedCity.getNeighbourCount() + 1);
                cities.update(updatedCity);
            }
        }
    }
}
