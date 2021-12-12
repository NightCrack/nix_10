package ua.com.alevel.db.impl;

import ua.com.alevel.db.CitiesInstance;
import ua.com.alevel.entity.City;
import ua.com.alevel.util.Factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class CitiesImpl implements CitiesInstance<Long> {

    private final AtomicInteger count = new AtomicInteger(0);
    ArrayList<City> cities = new ArrayList<>();

    public CitiesImpl() {
    }

    @Override
    public boolean create(City city) {
        city.setId((long) count.incrementAndGet());
        return cities.add(city);
    }

    @Override
    public boolean update(City city) {
        City current = find(city.getId());
        for (int index = 0; index < City.class.getDeclaredFields().length; index++) {
            try {
                Field field = City.class.getDeclaredFields()[index];
                Method setMethod = Factory.getSet(city, field, false);
                Method getMethod = Factory.getSet(city, field, true);
                setMethod.invoke(current, getMethod.invoke(city));
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean delete(Long id) {
        cities.forEach(city -> city.getTravelCost().remove(id));
        return cities.removeIf(city -> city.getId().equals(id));
    }

    @Override
    public City find(Long id) {
        for (City city : cities) {
            if (city.getId().equals(id)) {
                return city;
            }
        }
        return null;
    }

    @Override
    public ArrayList<City> find() {
        return cities;
    }


}
