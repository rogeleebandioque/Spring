package activity.core.dao.actions.users;

import activity.core.dao.Command;
import activity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class DeleteUser implements Command {

    private Session session;
    private Integer id;

    public DeleteUser(Integer id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        Users user = (Users) session.get(Users.class, id);
        session.delete(user);
        return session.createQuery("FROM Users WHERE id =" + id).list().isEmpty();
    }
}
