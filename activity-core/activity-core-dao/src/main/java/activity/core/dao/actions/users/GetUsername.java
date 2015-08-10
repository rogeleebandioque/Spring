package activity.core.dao.actions.users;

import activity.core.dao.Command;
import activity.core.model.Users;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class GetUsername implements Command {

    private Session session;
    private String username;

    public GetUsername(String username) {
        this.username = username;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        Criteria cr = session.createCriteria(Users.class);
        cr.add(Restrictions.eq("username", username));
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List<Users> user = cr.list();
        return (Users) user.get(0);
    }
}
