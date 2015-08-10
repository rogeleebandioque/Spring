package activity.core.dao.actions.users;

import activity.core.dao.Command;
import activity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class GetUser implements Command {

    private Session session;
    private int id;

    public GetUser(int id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        return (Users) session.get(Users.class, id);
    }
}
