package hibernateactivity.core.dao.actions;

import hibernateactivity.core.dao.Command;
import hibernateactivity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class ListUser implements Command {

    private Session session;

    public ListUser() {
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        List<Users> users = null;  
        Criteria cr = session.createCriteria(Users.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        users = cr.list();
        return users;
    }
}
