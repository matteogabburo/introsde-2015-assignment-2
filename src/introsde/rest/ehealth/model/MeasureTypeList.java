package introsde.rest.ehealth.model;

import introsde.rest.ehealth.dao.LifeCoachDao;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

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
import java.util.Iterator;

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
@XmlRootElement(name = "MeasureTypes")
public class MeasureTypeList implements Serializable {
	
	@Column(name = "type")
	private List<String> type;

	public List<String> getType() {return this.type;}
	public void setType(List<String> type) {this.type = type;}

	public static MeasureTypeList getTypeList()
	{
		
		MeasureTypeList res = new MeasureTypeList();

		EntityManager em = LifeCoachDao.instance.createEntityManager();		
		res.setType(em.createQuery("SELECT DISTINCT m.type FROM MeasureType m", String.class).getResultList());
		LifeCoachDao.instance.closeConnections(em);

		return res;
	}


}
