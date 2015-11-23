package introsde.rest.ehealth.model;

import java.io.Serializable;
import java.util.List;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import introsde.rest.ehealth.dao.LifeCoachDao;


/**
 * The persistent class for the "MeasureType" database table.
 * 
 */
@Entity
@Table(name = "MeasureType")
@XmlRootElement(name = "MeasureTypes")
public class MeasureTypeList implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
