package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.OneToMany;
import java.util.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Table(name = "MeasureType")
@NamedQuery(name = "MeasureType.findAll", query = "SELECT l FROM MeasureType l")
//@NamedQuery(name = "MeasureType.findMeasureTypeFromPersonIdByType", query = "SELECT l FROM MeasureType l WHERE idPerson = 1 AND type = \"height\"")
@XmlRootElement(name="healthProfile")
public class MeasureType implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measuretype")
	@TableGenerator(name="sqlite_measuretype",
		table="sqlite_sequence", 
		pkColumnName="name", 
		valueColumnName="seq", 
		pkColumnValue="idMeasureType")
	@Column(name = "idMeasureType")
	private int idMeasureType;

	@Column(name = "idPerson")
	private int idPerson;

	@Column(name = "type")
	private String type;

	@Column(name = "value")
	private float value;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;


	@ManyToOne
	@JoinColumn(name="idPerson",referencedColumnName="idPerson", insertable=false, updatable=false)
	private Person person;

	public MeasureType() {}

	//Getter and setter
	public int getIdMeasureType() {return this.idMeasureType;}
	public void setIdMeasureType(int idMeasureType) {this.idMeasureType = idMeasureType;}
	public String getType() {return this.type;}
	public void setType(String type) {this.type = type;}
	public void setIdPerson(int idPerson){this.idPerson = idPerson;}
	public int getIdPerson(){return this.idPerson;}

	public Date getDate() {return this.date;}
	public void setDate(Date date) {this.date = date;}
	public float getValue() {return this.value;}
	public void setValue(float value) {this.value = value;}

	// we make this transient for JAXB to avoid and infinite loop on serialization
	@XmlTransient
	public Person getPerson() {return person;}
	public void setPerson(Person person) {this.person = person;}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static MeasureType getMeasureTypeById(int idMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		MeasureType p = em.find(MeasureType.class, idMeasure);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	public static List<MeasureType> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<MeasureType> list = em.createNamedQuery("MeasureType.findAll", MeasureType.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}
	//***********************************************************************************************
	//Return a list contents all the Measures that have type equal than the the type in the request
	public static List<MeasureType> getMeasureTypeFromPersonIdByType(int id, String type) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();

		//Query for this request
		List<MeasureType> measures = em.createQuery("SELECT l FROM MeasureType l WHERE l.idPerson = "+id+" AND l.type = \"" + type + "\""
													 , MeasureType.class).getResultList();

		LifeCoachDao.instance.closeConnections(em);
		return measures;
	}
    //***********************************************************************************************
	public static MeasureType saveMeasureType(MeasureType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static MeasureType updateMeasureType(MeasureType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static void removeMeasureType(MeasureType p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
