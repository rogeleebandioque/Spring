package activity.core.model;
import javax.persistence.*;

@Entity
@Table(name="Contacts")
public class Contacts {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="contact_generator")    
    @SequenceGenerator(name="contact_generator", sequenceName="contact_generator", allocationSize=1)
    @Column(name="contact_id")
    private int contact_id;

    @Column(name="type")
    private String type;

    @Column(name="contact")
    private String contact;

    public Contacts() {}

    public Contacts(String contact, String type) {
        this.contact = contact;
        this.type = type;    
    }
    
    public int getContact_id() {
        return this.contact_id;    
    }

    public void setContact_id(int contact_id) {
        this.contact_id = contact_id;
    }
    
    public String getContact() {
        return this.contact;    
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!this.getClass().equals(obj.getClass())) {
            return false;
        }
        
        Contacts obj2 = (Contacts)obj;

        if((this.contact_id == obj2.getContact_id()) && (this.contact.equals(obj2.getContact()))) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int tmp = 0;
        tmp = ( contact_id + contact ).hashCode();
        return tmp;
    }   


}
