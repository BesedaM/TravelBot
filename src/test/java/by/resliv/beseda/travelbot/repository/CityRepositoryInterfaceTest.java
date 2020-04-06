package by.resliv.beseda.travelbot.repository;

import by.resliv.beseda.travelbot.model.City;
import by.resliv.beseda.travelbot.testconfig.MongoTestConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {MongoTestConfig.class})
public class CityRepositoryInterfaceTest {

    @Autowired
    private CityRepositoryInterface repository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @After
    public void cleanDatabase(){
        mongoTemplate.dropCollection("cities");
    }

    @Test
    public void testFindByName01_UpperCase() {
        String name01 = "Minsk";
        List<String> text = new ArrayList<>();
        text.add("Minsk");
        City minsk01 = new City(name01, text);
        repository.insert(minsk01);

        City city = repository.findByName("Minsk");
        Assert.assertEquals(name01, city.getName());
    }

    @Test
    public void testFindByName01_LowerCase() {
        String name01 = "Minsk";
        List<String> text = new ArrayList<>();
        text.add("Minsk");
        City minsk01 = new City(name01, text);
        repository.insert(minsk01);

        City city = repository.findByName("minsk");
        Assert.assertEquals(name01, city.getName());
    }


}
