package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.ehealth.dao.LifeCoachDao;

@Entity  // indicates that this class is an entity to persist in DB
@Table(name="Person") // to whole table must be persisted 
@NamedQuery(name="Person.findAll", query="SELECT p FROM Person p")
@XmlRootElement
public class Person implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id // defines this attributed as the one that identifies the entity
    @GeneratedValue(generator="sqlite_person")
    @TableGenerator(name="sqlite_person", table="sqlite_sequence",
        pkColumnName="name", valueColumnName="seq",
        pkColumnValue="Person")

    @Column(name="idPerson")
    private int idPerson;
    @Column(name="lastname")
    private String lastname;
    @Column(name="firstname")
    private String firstname;
    @Temporal(TemporalType.DATE) // defines the precision of the date attribute
    @Column(name="birthdate")
    private Date birthdate; 
    
    // mappedBy must be equal to the name of the attribute in MeasureType that maps this relation
    @OneToMany(mappedBy="person", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private List<MeasureType> measureType;
    
    @XmlElementWrapper(name = "healthProfile")
    public List<MeasureType> getMeasureType() {return MeasureType.getLastMeasures(this.idPerson);}

    // add below all the getters and setters of all the private attributes
    // getters
    public int getIdPerson(){return idPerson;}

    public String getLastname(){return lastname;}
    public String getFirstname(){return firstname;}
    public String getBirthdate(){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        // Get the date today using Calendar object.
        return df.format(birthdate);
    }
    // setters
    public void setIdPerson(int idPerson){this.idPerson = idPerson;}
    public void setLastname(String lastname){this.lastname = lastname;}
    public void setFirstname(String firstname){this.firstname = firstname;}
    public void setMeasureType(List<MeasureType> measureType){this.measureType = measureType;}
    public void setBirthdate(String bd) throws ParseException{
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        Date date = format.parse(bd);
        this.birthdate = date;
    }

    public static Person getPersonById(int personId) {
        EntityManager em = LifeCoachDao.instance.createEntityManager();
        Person p = em.find(Person.class, personId);
        LifeCoachDao.instance.closeConnections(em);
        return p;
    }

    public static List<Person> getAll() {
      EntityManager em = LifeCoachDao.instance.createEntityManager();
      
      List<Person> list = em.createNamedQuery("Person.findAll", Person.class)
      .getResultList();
      
      LifeCoachDao.instance.closeConnections(em);
      return list;
  }


  public static int getMaxId()
    {
        MeasureTypeList res = new MeasureTypeList();

        EntityManager em = LifeCoachDao.instance.createEntityManager();     
        Integer id = em.createQuery("SELECT MAX(m.idPerson) FROM Person m", Integer.class).getSingleResult();
        LifeCoachDao.instance.closeConnections(em);

        return id.intValue();
    }

  public static Person savePerson(Person p) {
    EntityManager em = LifeCoachDao.instance.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    em.persist(p);
    tx.commit();
    LifeCoachDao.instance.closeConnections(em);
    return p;
} 

public static Person updatePerson(Person p) {
    EntityManager em = LifeCoachDao.instance.createEntityManager(); 
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    p=em.merge(p);
    tx.commit();
    LifeCoachDao.instance.closeConnections(em);
    return p;
}

public static void removePerson(Person p) {
    EntityManager em = LifeCoachDao.instance.createEntityManager();
    EntityTransaction tx = em.getTransaction();
    tx.begin();
    p=em.merge(p);
    em.remove(p);
    tx.commit();
    LifeCoachDao.instance.closeConnections(em);
}

}