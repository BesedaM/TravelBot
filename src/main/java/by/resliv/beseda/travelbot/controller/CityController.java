package by.resliv.beseda.travelbot.controller;

import by.resliv.beseda.travelbot.model.City;
import by.resliv.beseda.travelbot.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/cities",
        produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<City> getCityById(@PathVariable String id) {
        ResponseEntity<City> respCity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<City> city = cityService.findById(id);
        if (city.isPresent()) {
            respCity = new ResponseEntity<>(city.get(), HttpStatus.OK);
        }
        return respCity;
    }

    @GetMapping("/")
    public ResponseEntity<List<City>> getCities() {
        List<City> cityList = cityService.findAll();
        return new ResponseEntity<>(cityList, HttpStatus.OK);
    }

    @GetMapping(value="/name/{name}", produces={"application/json; charset=UTF-8"})
    public ResponseEntity<City> getCityByName(@PathVariable String name) {
        ResponseEntity<City> respCity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<City> city = cityService.findByName(name);
        if (city.isPresent()) {
            respCity = new ResponseEntity<>(city.get(), HttpStatus.OK);
        }
        return respCity;
    }


    @PostMapping("/")
    public ResponseEntity<City> addCity(@RequestBody City city) {
        ResponseEntity<City> responseEntity;
        Optional<City> insertedCity = cityService.add(city);
        if (insertedCity.isPresent()) {
            responseEntity = new ResponseEntity<>(cityService.findByName(city.getName()).get(), HttpStatus.CREATED);
        } else {
            responseEntity = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCityData(@PathVariable String id, @RequestBody City city) {
        ResponseEntity<City> responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Optional<City> updatedCity = cityService.update(id, city);
        if (updatedCity.isPresent()) {
            responseEntity = new ResponseEntity<>(updatedCity.get(), HttpStatus.ACCEPTED);
        }
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable String id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (cityService.delete(id)) {
            status = HttpStatus.OK;
        }
        return new ResponseEntity<>(status);
    }
}
