package by.resliv.beseda.travelbot.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@EqualsAndHashCode
@Document(collection = "cities")
public class City {

    @Id
    private String id;

    private String name;
    private List<String> text = new ArrayList<>();

    public City() {
    }

    public City(String name, List<String> info) {
        this.name = name;
        this.text = info;
    }

    public City(String id, String name, List<String> info) {
        this.id = id;
        this.name = name;
        this.text = info;
    }

    @Override
    public String toString() {
        return name + " : " + text.toString();
    }

}
