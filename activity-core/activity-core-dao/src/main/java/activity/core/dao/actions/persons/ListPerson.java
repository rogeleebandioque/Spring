package activity.core.dao.actions.persons;

import activity.core.dao.Command;
import activity.core.model.Person;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class ListPerson implements Command {

    private Session session;

    public ListPerson() {
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        List<Person> persons = null;  
        Criteria cr = session.createCriteria(Person.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        persons = cr.list();
        return persons;
    }
}
