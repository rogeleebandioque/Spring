package activity.core.dao;

import org.hibernate.Session;

public interface Command {

    void setSession(Session session);
    Object execute();

}
