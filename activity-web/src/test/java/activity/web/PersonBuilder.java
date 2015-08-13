package activity.web;

import activity.core.model.Person;
import activity.core.model.Name;
import java.util.*;

public class PersonBuilder {

    private int id;
    private Name names;
    private String address;
    private Integer age;
    private String gender;
    private Date bday = new Date();
    private Integer grade;
    private Date date_hired = new Date();
    private String currently_employed;

    public PersonBuilder() {
    }

    public static PersonBuilder person() {
        return new PersonBuilder();
    }

    public PersonBuilder id() {
        id = 1;
        return this;
    }

    public PersonBuilder names() {
        names = new Name("TEST", "TEST");
        return this;
    }

    public PersonBuilder address() {
        address = "TEST";
        return this;
    }

    public PersonBuilder age() {
        age = 1;
        return this;
    }

    public PersonBuilder grade() {
        grade = 1;
        return this;
    }

    public PersonBuilder gender() {
        gender = "Male";
        return this;
    }

    public PersonBuilder bday() {
        bday = new Date();
        return this;
    }

    public PersonBuilder date_hired() {
        date_hired = new Date();
        return this;
    }

    public PersonBuilder currently_employed() {
        currently_employed = "No";
        return this;
    }

    public Person build() {
        return new Person(names, address, age, gender, bday, grade, date_hired, 
                currently_employed);
    }
}
