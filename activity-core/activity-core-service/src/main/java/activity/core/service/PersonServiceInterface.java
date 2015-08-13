package activity.core.service;

import activity.core.model.Person;
import activity.core.model.Roles;
import java.util.List;

public interface PersonServiceInterface {
    public List<Person> getPerson();
    public String deletePersons(int idNum);
    public boolean searchPersons(int idNum);
    public String addPersons(Person person);
    public String updatePersons(Person person);
    public Person getPersons(int idNum);
    public Roles getByRole(Integer category);
    public List<Person> searchPerson(String searchQ, String listBy, String order);
}
