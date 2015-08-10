package activity.core.dao;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    public static <T> T perform(Command command, Class<T> returnClass) {
        session = sessionFactory.getCurrentSession();
        
        Object returnObject = null;
        command.setSession(session);
        returnObject = command.execute();
        return returnClass.cast(returnObject);
    }

}
