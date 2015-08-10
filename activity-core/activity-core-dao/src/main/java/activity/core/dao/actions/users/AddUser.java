package activity.core.dao.actions.users;

import activity.core.dao.Command;
import activity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class AddUser implements Command {

    private Session session;
    private Users user;

    public AddUser(Users user) {
        this.user = user;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        session.save(user);
        return session.createQuery("FROM Users WHERE id =" + user.getId()).list().isEmpty();
    }
}
