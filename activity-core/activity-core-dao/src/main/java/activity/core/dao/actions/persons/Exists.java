package activity.core.dao.actions.persons;

import activity.core.dao.Command;
import org.hibernate.Session;

public class Exists implements Command {

    private Session session;
    private Integer id;

    public Exists(Integer id) {
        this.id = id;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public Object execute() {
        return session.createQuery("FROM Person WHERE id =" + id).list().isEmpty();
    }
}
