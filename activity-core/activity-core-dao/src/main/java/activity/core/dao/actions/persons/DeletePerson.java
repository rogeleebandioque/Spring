package activity.core.dao.actions.persons;

import activity.core.dao.Command;
import activity.core.model.Person;
import org.hibernate.Session;

public class DeletePerson implements Command {

    private Session session;
    private Integer id;

    public DeletePerson(Integer id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        Person person = (Person) session.get(Person.class, id);
        session.delete(person);
        return session.createQuery("FROM Person WHERE id =" + id).list().isEmpty();
    }
}
