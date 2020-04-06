package by.resliv.beseda.travelbot.service;

import by.resliv.beseda.travelbot.model.City;
import by.resliv.beseda.travelbot.repository.CityRepositoryInterface;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {

    private static List<City> cities=new ArrayList<>();
    private static City minsk;
    private static City kiev;

    @Mock
    private CityRepositoryInterface repository;

    @InjectMocks
    private CityService service;

    @BeforeClass
    public static void init() {
        List<String> description = new ArrayList<>();
        description.add("Всем привет!");
        description.add("Это мой дом!");
        minsk = new City("Minsk", description);
        kiev = new City("Киев", description);
        cities.add(minsk);
        cities.add(kiev);
    }

    //test FindAll

    @Test
    public void testFindAll() {
        Mockito.when(repository.findAll(Sort.by(Sort.Direction.ASC, "name"))).thenReturn(cities);
        List<City> cityList = service.findAll();
        Assert.assertEquals(cities, cityList);
    }


    //test FindById

    @Test
    public void testFindById_entityIsPresent() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(minsk));
        Optional<City> opt = service.findById("1111");
        Assert.assertEquals(minsk, opt.get());
    }

    @Test
    public void testFindById_entityNotPresent() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        Optional<City> opt = service.findById("1111");
        Assert.assertTrue(opt.isEmpty());
    }


    //test Add

    @Test
    public void testAdd_noSuchEntity() {
        Mockito.when(repository.findByName(anyString())).thenReturn(null);
        service.add(minsk);
        Mockito.verify(repository, times(1)).insert(any(City.class));
    }

    @Test
    public void testAdd_entityExists() {
        Mockito.when(repository.findByName(anyString())).thenReturn(minsk);
        service.add(minsk);
        Mockito.verify(repository, never()).insert(any(City.class));
    }

    @Test
    public void testAdd_nullEntity() {
        service.add(null);
        Mockito.verify(repository, never()).findByName(anyString());
        Mockito.verify(repository, never()).insert(any(City.class));
    }

    //test Update

    @Test
    public void testUpdate_ok() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(minsk));
        service.update("10", minsk);
        Mockito.verify(repository, atMostOnce()).save(any(City.class));
    }

    @Test
    public void testUpdate_noSuchEntity() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        service.update("10", minsk);
        Mockito.verify(repository, never()).save(any(City.class));
    }

    @Test
    public void testUpdate_noTextPassedToUpdate() {
        City noDescription = new City();
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(minsk));
        service.update("10", noDescription);
        Mockito.verify(repository, never()).save(any(City.class));
    }

    //test Delete

    @Test
    public void testDelete_entityExists() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.of(minsk));
        service.delete("10");
        Mockito.verify(repository, atMostOnce()).delete(any(City.class));
    }

    @Test
    public void testDelete_noSuchEntity() {
        Mockito.when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
        service.delete("10");
        Mockito.verify(repository, never()).delete(any(City.class));
    }


    //test FindByName

    @Test
    public void testFindByName_entityExists() {
        Mockito.when(repository.findByName(anyString())).thenReturn(minsk);
        Optional<City> opt = service.findByName("minsk");
        Assert.assertTrue(opt.isPresent());
    }

    public void testFindByName_entityDoesntExist() {
        Mockito.when(repository.findByName(anyString())).thenReturn(null);
        Optional<City> opt = service.findByName("minsk");
        Assert.assertTrue(opt.isEmpty());
    }

}
