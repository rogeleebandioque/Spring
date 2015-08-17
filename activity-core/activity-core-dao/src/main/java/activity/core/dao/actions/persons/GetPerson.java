package activity.core.dao.actions.persons;

import activity.core.dao.Command;
import activity.core.model.Person;
import org.hibernate.Session;

public class GetPerson implements Command {

    private Session session;
    private Integer id;

    public GetPerson(Integer id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        return (Person) session.get(Person.class, id);
    }
}
