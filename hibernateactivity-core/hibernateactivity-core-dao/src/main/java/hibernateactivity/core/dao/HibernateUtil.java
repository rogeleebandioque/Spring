package hibernateactivity.core.dao;

import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static Session session;
    private static Transaction transaction;
    

    public void setSessionFactory(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }
    
    public static <T> T perform(Command command, Class<T> returnClass) {
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();
        
        Object returnObject = null;
        
        try {
            command.setSession(session);
            returnObject = command.execute();
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            System.err.println("Unable to perform transaction.");
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        return returnClass.cast(returnObject);
    }


}
