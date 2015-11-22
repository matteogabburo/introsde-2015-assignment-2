package introsde.rest.client;

import java.net.URI;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Response;
import javax.ws.rs.client.Entity;

import java.io.*;
import java.util.StringTokenizer;

import org.glassfish.jersey.client.ClientConfig;

public class Assignment2Client {

	static String URI = "http://127.0.1.1:5700/assignment2/";

	static String LOGNAME_XML = "log/client-server-xml.log";
	static String LOGNAME_JSON = "log/client-server-json.log";

	public static void main(String[] args) throws IOException {
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget service = client.target(getBaseURI());

		int firstPerson = 1;
		int lastPerson = 3; //TODO DINAMICO 

		String r1 = "person";
		String r2 = "person/1";
		String r3 = "person/1";
		String r4 = "person";
		String r5 = "person/555";//Look at r4_body_json for 555
		String r6 = "person/measureTypes";
		String r7 = "person/";

		String r3_body_json = "{"
		+"\"idPerson\": 1,"
		+"\"lastname\": \"Chan\","
		+"\"firstname\": \"Melchiorre\","
		+"\"birthdate\": \"02/09/1978\""
		+"}";

		String r3_body_xml = "<person>"
		+"<birthdate>02/09/1978</birthdate>"
		+"<firstname>Armando</firstname>"
		+"<idPerson>1</idPerson>"
		+"<lastname>Chan</lastname>"
		+"</person>";

		String r4_body_json =     " {"
		+"\"idPerson\" : 555,"
		+" \"firstname\"     : \"Chuck\","
		+"\"lastname\"      : \"Norris\","
		+"\"birthdate\"     : \"1945/01/01\","
		+"\"measureType\" :" 
		+"["
		+"         		    {"
		+"                    \"idMeasureType\": 88,"
		+"                    \"idPerson\": 555,"
		+"                    \"mid\": 1,"
		+"                    \"type\": \"weight\","
		+"                    \"value\": 78.9,"
		+"                    \"date\": \"02/09/1978\""
		+"                  },"
		+"                  {"
		+"                    \"idMeasureType\": 89,"
		+"                    \"idPerson\": 555,"
		+"                    \"mid\": 1,"
		+"                    \"type\": \"height\","
		+"                    \"value\": 172.0,"
		+"                    \"date\": \"02/09/1978\""
		+"                  }"
		+"]"
		+"}";

		String xmlResp = "";
		String jsonResp = "";

		Assignment2Client r = new Assignment2Client();

		System.out.println("\n\n============================== REQUEST 1 ==========================\n");
		xmlResp += r.sendR1_xml(service, r1);
		jsonResp += r.sendR1_json(service, r1);
		System.out.println("\n\n============================== REQUEST 2 ==========================\n");
		xmlResp += r.sendR2_xml(service, r2);
		jsonResp += r.sendR2_json(service, r2);
		System.out.println("\n\n============================== REQUEST 3 ==========================\n");
		//Changefirstname
		//String r3_body = r.getPerson(service, r2);		
		jsonResp += r.sendR3_json(service, r3, r3_body_json);
		xmlResp += r.sendR3_xml(service, r3, r3_body_xml);
		System.out.println("\n\n============================== REQUEST 4 ==========================\n");
		jsonResp += r.sendR4_json(service, r4, r4_body_json);
		//xmlResp += r.sendR4_xml(service, r4, r4_body_xml);
		System.out.println("\n\n============================== REQUEST 5 ==========================\n");
		jsonResp += r.sendR5_json(service, r5);
		//xmlResp += r.sendR4_xml(service, r4, r4_body_xml);
		System.out.println("\n\n============================== REQUEST 6 ==========================\n");

		String measure_types[] = new String[0];
		jsonResp += r.sendR9(service, r6, measure_types);
		System.out.println("\n\n============================== REQUEST 7 ==========================\n");
		jsonResp += r.sendR6_json(service, r7, firstPerson, lastPerson, measure_types);

		r.makeLogs(xmlResp, jsonResp);
	}

	private static URI getBaseURI() {
		return UriBuilder.fromUri(Assignment2Client.URI).build();
	}

	private void makeLogs(String xmlResp, String jsonResp) throws IOException {
		FileWriter logxml;
		logxml = new FileWriter(this.LOGNAME_XML);

		FileWriter logjson;
		logjson = new FileWriter(this.LOGNAME_JSON);

		BufferedWriter b;

		System.out.println("Writing xml logs...");
		b=new BufferedWriter (logxml);
		b.write(xmlResp);
		b.flush();

		System.out.println("Writing json logs...");
		b=new BufferedWriter (logjson);
		b.write(jsonResp);
		b.flush();
	}

	private String countPerson(String body) 
	{
		//Count Person in the response, if there are much than 2 people the 
		//result is OK, else the result is ERROR
		String result = "";

		String token = "firstname"; //beacause thi is a field present only in Person
		int counter = 0;
		StringTokenizer st = new StringTokenizer(body, token);

		counter = st.countTokens();

		if(counter > 2)
			result = "OK";
		else
			result = "ERROR";
		
		return result;
	}

	private int countTypes(String body) 
	{
		String token = "<type"; //beacause thi is a field present only in Person
		int counter = 0;
		StringTokenizer st = new StringTokenizer(body, token);

		counter = st.countTokens();
		
		return counter;
	}

	private String makeStringInfo(int number, String httpMethod, String url, String type, 
		String contentType, String result, int status, String body)
	{
		String res = "";

		res += "Request #"+number+": "+httpMethod+" "+url+" Accept: "+type+" content-type: "+contentType;
		res += "\n=> Result: "+ result;
		res += "\n=> HTTP Status: "+ status;
		res += "\n\n";
		res += body;

		return res;
	}

	private String sendR1_xml(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = ""; 
		String result;

		//Accept: applicationxml
		response = service.path(req).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		result = countPerson(body);

		printableResponse = makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
		System.out.println(printableResponse);
		

		return printableResponse;
	}

	private String sendR1_json(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		result = countPerson(body);

		printableResponse = makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}
	
	private String sendR2_xml(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = ""; 
		String result;

		//Accept: applicationxml
		response = service.path(req).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
		System.out.println(printableResponse);
		

		return printableResponse;
	}

	private String sendR2_json(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}
	

	private String getPerson(WebTarget service, String req)
	{
		Response response;
		String body = "";
		
		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).get();
		body = response.readEntity(String.class);

		return body;
	}


	private String sendR3_json(WebTarget service, String path, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson

		//Response response =  service.request().put(Entity.json(friend));

		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).put(Entity.json(req));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		//DA CAMBIARE ======================================================================
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";
		//==================================================================================

		printableResponse = makeStringInfo(3, "PUT", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}


	private String sendR3_xml(WebTarget service, String path, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson

		//Response response =  service.request().put(Entity.json(friend));

		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).put(Entity.xml(req));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		//DA CAMBIARE ======================================================================
		if(status == 202 || status == 200)
			result = "OK";
		else
			result = "ERROR";
		//==================================================================================

		printableResponse = makeStringInfo(3, "PUT", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}

	private String sendR4_json(WebTarget service, String path, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(path).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(req));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(4, "POST", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}

	private String sendR5_json(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).delete();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 404 || status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(1, "DELETE", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}

	private String sendR9(WebTarget service, String req, String[] measure_types)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		int counter = countTypes(body);
		if(counter > 2)
			result = "OK";
		else
			result = "ERROR";

		measure_types = new String[counter];

		//parse body
		String token1 = "<type>";
		String token2 = "</type>";

		int pos1;
		int pos2;
		for(int i = 0; i < counter; i++)
		{
			pos1 = body.indexOf(token1);
			pos2 = body.indexOf(token2);

			measure_types[i] = body.substring(pos1 + token1.length(), pos2);
		}

		printableResponse = makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}
	
	private String sendR6_json(WebTarget service, String req, int firstname, int lastname, String[] measure_types)
	{
		Response response;
		String printableResponse = "";
		int status;
		String body = "";
		String result;

		result = countPerson(body);

		for(int i = 0; i < measure_types.length; i++)
		{
			response = service.path(req +firstname+"/"+measure_types[i]).request().accept(MediaType.APPLICATION_JSON).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			printableResponse += makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		for(int i = 0; i < measure_types.length; i++)
		{
			response = service.path(req +lastname+"/"+measure_types[i]).request().accept(MediaType.APPLICATION_JSON).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			printableResponse += makeStringInfo(1, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		//TODO INSERIRE CONTROLLO PER OK SU TUTTE LE RICHIESTE
		return printableResponse;
	}

	/*private void sendR3(WebTarget service)
	{}
	private void sendR4(WebTarget service)
	{}
	private void sendR5(WebTarget service)
	{}
	private void sendR9(WebTarget service)
	{}*/


}