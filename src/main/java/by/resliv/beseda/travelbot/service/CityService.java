package by.resliv.beseda.travelbot.service;

import by.resliv.beseda.travelbot.model.City;
import by.resliv.beseda.travelbot.repository.CityRepositoryInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    @Autowired
    private CityRepositoryInterface cityRepository;

    public List<City> findAll() {
        return cityRepository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }


    public Optional<City> findById(String id) {
        return cityRepository.findById(id);
    }


    public Optional<City> findByName(String name) {
        return Optional.ofNullable(cityRepository.findByName(name));
    }


    public Optional<City> add(City city) {
        City newCity = null;
        if (city != null && city.getName() != null) {
            City existentCity = cityRepository.findByName(city.getName());
            if (existentCity == null) {
                newCity = cityRepository.insert(city);
            }
        }
        return Optional.ofNullable(newCity);
    }


    public Optional<City> update(String id, City city) {
        Optional<City> oldCity = cityRepository.findById(id);
        if (oldCity.isPresent() && city.getText() != null) {
            oldCity.get().setText(city.getText());
            cityRepository.save(oldCity.get());
        }
        return oldCity;
    }


    public boolean delete(String id) {
        boolean entityDeleted = true;
        Optional<City> city = cityRepository.findById(id);
        if (city.isPresent()) {
            cityRepository.delete(city.get());
        } else {
            entityDeleted = false;
        }
        return entityDeleted;
    }

}
