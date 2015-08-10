package activity.core.dao;

import java.util.*;
import activity.core.model.Person;
import activity.core.model.Roles;
import activity.core.model.Contacts;

interface PersonDao{
    public List<Person> getPeople();
    public Roles getRole(Integer category);
    public String deletePeople(int idNum);
    public boolean inRecord(int idNum);
    public String addPeople(Person person);
    public String updatePeople(Person person);
    public Person getPeople(int idNum);
    public List<Person> searchPeople(String search, String listBy, String order);
}
