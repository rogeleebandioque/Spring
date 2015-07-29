package hibernateactivity.core.dao.actions;

import hibernateactivity.core.dao.Command;
import hibernateactivity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class GetUserByUsername implements Command {

    private Session session;
    private String username;

    public GetUserByUsername(String username) {
        this.username = username;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        return (Users) session.get(Users.class, username);
    }
}
