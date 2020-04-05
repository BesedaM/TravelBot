package by.resliv.beseda.travelbot.repository;

import by.resliv.beseda.travelbot.model.City;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;


public interface CityRepositoryInterface extends MongoRepository<City, String> {

    @Query(value="{ 'name' : {$regex : ?0, $options: 'i'} }")
    City findByName(String name);
}
