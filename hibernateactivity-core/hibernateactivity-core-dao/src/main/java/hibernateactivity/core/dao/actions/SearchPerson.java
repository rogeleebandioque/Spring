package hibernateactivity.core.dao.actions;

import hibernateactivity.core.dao.Command;
import hibernateactivity.core.model.Person;
import org.hibernate.Session;
import java.util.*;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;


public class SearchPerson implements Command {

    private Session session;
    private String search;
    private String listBy;
    private String order;

    public SearchPerson(String search, String listBy, String order) {
        this.search = search;
        this.listBy = listBy;
        this.order = order;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public Object execute() {
        String hql = null;
        List<Person> persons = null;
        listBy = listBy.replace(" ", "_").toLowerCase();
        Criteria cr = session.createCriteria(Person.class);
        cr.add(Restrictions.like("names.last_name", search, MatchMode.ANYWHERE));

        if(!listBy.equals("last_name")) {
            
            if(order.equals("desc")) {
                cr.addOrder(Order.desc(listBy));
            } else {
                cr.addOrder(Order.asc(listBy));
            }
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            persons = cr.list();
        } else {
            if(order.equals("desc")) {
                cr.addOrder(Order.desc("names.last_name"));
            } else {
                cr.addOrder(Order.asc("names.last_name"));
            }
            cr.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            persons = cr.list();
        }
        return persons;
    }
}
