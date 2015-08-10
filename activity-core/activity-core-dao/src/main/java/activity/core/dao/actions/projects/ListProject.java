package activity.core.dao.actions.projects;

import activity.core.dao.Command;
import activity.core.model.Projects;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

public class ListProject implements Command {

    private Session session;

    public ListProject() {
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        List<Projects> projects = null;  
        Criteria cr = session.createCriteria(Projects.class);
        cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        projects = cr.list();
        return projects;
    }
}
