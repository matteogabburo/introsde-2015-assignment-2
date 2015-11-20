package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;
import java.util.Date;
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
import javax.persistence.TemporalType;


/**
 * The persistent class for the "Measure" database table.
 * 
 */
@Entity
@Table(name = "Measure")
@NamedQuery(name = "Measure.findAll", query = "SELECT l FROM Measure l")
@XmlRootElement(name="Measures")
public class Measure implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(generator="sqlite_measure")
	@TableGenerator(name="sqlite_measure", table="sqlite_sequence", 
					pkColumnName="name", 
					valueColumnName="seq", 
					pkColumnValue="idMeasure")
	@Column(name = "idMeasure")
	private int idMeasure;

	@Column(name = "value")
	private float value;
	
	@Temporal(TemporalType.DATE)
	@Column(name="date")
	private Date date;

	@ManyToOne
	@JoinColumn(name="idMeasureType",referencedColumnName="idMeasureType", insertable=false, updatable=false)
	private MeasureType measureType;

	public Measure() {}

	//Getter and setter
	public int getIdMeasure() {return this.idMeasure;}
	public void setIdMeasure(int idMeasure) {this.idMeasure = idMeasure;}
	public Date getDate() {return this.date;}
	public void setDate(Date date) {this.date = date;}
	public float getValue() {return this.value;}
	public void setValue(float value) {this.value = value;}
	public MeasureType getMeasureType() {return this.measureType;}
	public void setMeasureType(MeasureType param) {this.measureType = param;}
	
	// Database operations
	// Notice that, for this example, we create and destroy and entityManager on each operation. 
	// How would you change the DAO to not having to create the entity manager every time? 
	public static Measure getMeasureTypeById(int idMeasure) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		Measure p = em.find(Measure.class, idMeasure);
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	public static List<Measure> getAll() {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		List<Measure> list = em.createNamedQuery("MeasureType.findAll", Measure.class).getResultList();
		LifeCoachDao.instance.closeConnections(em);
		return list;
	}
	
	public static Measure saveMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		em.persist(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static Measure updateMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
		return p;
	}
	
	public static void removeMeasure(Measure p) {
		EntityManager em = LifeCoachDao.instance.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		tx.begin();
		p=em.merge(p);
		em.remove(p);
		tx.commit();
		LifeCoachDao.instance.closeConnections(em);
	}
}
