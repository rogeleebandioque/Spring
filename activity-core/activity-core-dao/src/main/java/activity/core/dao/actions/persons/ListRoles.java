package activity.core.dao.actions.persons;

import activity.core.dao.Command;
import activity.core.model.Person;
import activity.core.model.Roles;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class ListRoles implements Command {

    private Session session;
    private Integer category;

    public ListRoles(Integer category) {
        this.category = category;
     }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        return (Roles) session.get(Roles.class, category);
    }
}
