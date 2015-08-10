package activity.core.dao.actions.projects;

import activity.core.dao.Command;
import activity.core.model.Projects;
import org.hibernate.Hibernate;
import org.hibernate.Session;


public class GetProject implements Command {

    private Session session;
    private Integer id;

    public GetProject(Integer id) {
        this.id = id;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        return (Projects) session.get(Projects.class, id);
    }
}
