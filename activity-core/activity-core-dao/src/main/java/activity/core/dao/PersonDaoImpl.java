package activity.core.dao;

import activity.core.dao.actions.persons.DeletePerson;
import activity.core.dao.actions.persons.Exists;
import activity.core.dao.actions.persons.GetPerson;
import activity.core.dao.actions.persons.Save;
import activity.core.dao.actions.persons.Update;
import activity.core.dao.actions.persons.ListPerson;
import activity.core.dao.actions.persons.ListRoles;
import activity.core.dao.actions.persons.SearchPerson;
import activity.core.model.Person;
import activity.core.model.Name;
import activity.core.model.Contacts;
import activity.core.model.Roles;
import java.util.*;

public class PersonDaoImpl implements PersonDao {

    public List<Person> getPeople() {
        return HibernateUtil.perform(new ListPerson(), List.class);         
    }

    public String deletePeople(int idNum) {
        return HibernateUtil.perform(new DeletePerson(idNum), Boolean.class) ? "Deleted!" : "Unable to Delete!";
    }

    public boolean inRecord(int idNum) {
        return HibernateUtil.perform(new Exists(idNum), Boolean.class);
    }

    public String addPeople(Person person) {
        return HibernateUtil.perform(new Save(person), Boolean.class) ? "Unable to Add" : "Added";
    }

    public String updatePeople(Person person) {
        return HibernateUtil.perform(new Update(person), Boolean.class) ? "Unable to Update!" : "Updated!";
    }

    public Person getPeople(int idNum) {
        return HibernateUtil.perform(new GetPerson(idNum), Person.class);
    }

    public Roles getRole(Integer category) {
        return HibernateUtil.perform(new ListRoles(category), Roles.class);
    }
   
    public List<Person> searchPeople(String search, String listBy, String order) {
        return HibernateUtil.perform(new SearchPerson(search,listBy,order), List.class);
    }
}
