package introsde.rest.ehealth.resources;

import introsde.rest.ehealth.model.MeasureType;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.PathParam;
import javax.ws.rs.Path;

@Stateless // only used if the the application is deployed in a Java EE container
@LocalBean // only used if the the application is deployed in a Java EE container
public class MeasureTypeCollectionResource {
    @Context
    UriInfo uriInfo;
    @Context
    Request request;
    int id;//id from person
    String type;

    EntityManager entityManager; // only used if the application is deployed in a Java EE container

    public MeasureTypeCollectionResource(UriInfo uriInfo, Request request,int id, String type, EntityManager em) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.type = type;
        this.entityManager = em;
    }

    public MeasureTypeCollectionResource(UriInfo uriInfo, Request request,int id, String type) {
        this.uriInfo = uriInfo;
        this.request = request;
        this.id = id;
        this.type = type;
    }

    //Methods
    
    @GET
    @Produces({MediaType.TEXT_XML,  MediaType.APPLICATION_JSON , MediaType.APPLICATION_XML })
    public List<MeasureType> getTypes() {
        System.out.println("Getting list of types...");
        List<MeasureType> types = MeasureType.getMeasureTypeFromPersonIdByType(id ,type);
        return types;
    }

    @Path("{idMeasure}")
    @GET
    public List<MeasureType> getPerson(@PathParam("idMeasure") int mid) {
        List<MeasureType> types = MeasureType.getMeasureTypeFromPersonIdByTypeAndMid(id ,type, mid);
        return types;
    }

/*
    public Person getMeasureTypesFromType(String type) {
        System.out.println("Reading MeasureType from DB with type: "+type);

        // this will work within a Java EE container, where not DAO will be needed
        //Person person = entityManager.find(Person.class, personId); 

        Person person = Person.getPersonById(type);
        System.out.println("MeasureType: "+person.toString());
        return person;
    }*/


}