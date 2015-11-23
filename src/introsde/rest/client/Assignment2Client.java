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

	static String URI = "https://shrouded-hollows-4174.herokuapp.com/assignment2/";

	static String LOGNAME_XML = "client-server-xml.log";
	static String LOGNAME_JSON = "client-server-json.log";

	public String[] measure_types;

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
		String r6 = "measureTypes";
		String r7 = "person/";
		String r8 = "person/1/height/1";
		String deleteR9  = "measureTypes/65";

		//r9 sequence
		String r9_1 = "person/1/height";
		String r9_2 = "person/1/height";//POST, remember to add measureType.count

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

		String r4_body_xml ="<person>"
		+"<birthdate>1945/01/01</birthdate>"
		+"<firstname>Chuck</firstname>"
		+"<idPerson>555</idPerson>"
		+"<lastname>Norris</lastname>"
		+"<measureType>"
		+"<healthProfile>"
		+"<date>02/09/1978</date>"
		+"<idMeasureType>88</idMeasureType>"
		+"<idPerson>555</idPerson>"
		+"<mid>1</mid>"
		+"<type>weight</type>"
		+"<value>78.9</value>"
		+"</healthProfile>"
		+"<healthProfile>"
		+"<date>02/09/1978</date>"
		+"<idMeasureType>89</idMeasureType>"
		+"<idPerson>555</idPerson>"
		+"<mid>1</mid>"
		+"<type>height</type>"
		+"<value>172.0</value>"
		+"</healthProfile>"
		+"</measureType>"
		+"</person>";

		String r9_body_xml =     "<healthProfile>"
		+   "<date>09/12/2011</date>"
		+   "<idMeasureType>65</idMeasureType>"
		+   "<idPerson>1</idPerson>"
		+   "<mid>8</mid>"
		+   "<type>height</type>"
		+   "<value>12.0</value>"
		+"</healthProfile>";

		String r9_body_json = "{"
		+"\"date\": \"09/12/2011\","
		+"\"idMeasureType\": 65,"
		+"\"idPerson\": 1,"
		+"\"mid\": 8,"
		+"\"type\": \"height\","
		+"\"value\": \"12.0\""
		+"}";

		String xmlResp = "";
		String jsonResp = "";


		System.out.println("URL SERVER : " + Assignment2Client.URI);


		Assignment2Client r = new Assignment2Client();

		//JSON
		System.out.println("\n\n============================== REQUEST 1 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 1 ==========================\n";
		jsonResp += r.sendR1_json(service, r1);
		System.out.println("\n\n============================== REQUEST 2 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 2 ==========================\n";
		jsonResp += r.sendR2_json(service, r2);
		System.out.println("\n\n============================== REQUEST 3 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 3 ==========================\n";
		//Changefirstname
		//String r3_body = r.getPerson(service, r2);		
		jsonResp += r.sendR3_json(service, r3, r3_body_json);
		System.out.println("\n\n============================== REQUEST 4 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 4 ==========================\n";
		jsonResp += r.sendR4_json(service, r4, r4_body_json);
		System.out.println("\n\n============================== REQUEST 5 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 5 ==========================\n";
		jsonResp += r.sendR5_json(service, r5);
		System.out.println("\n\n============================== REQUEST 6 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 6 ==========================\n";
		jsonResp += r.sendR9_json(service, r6);
		System.out.println("\n\n============================== REQUEST 7 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 7 ==========================\n";
		jsonResp += r.sendR6_json(service, r7, firstPerson, lastPerson);
		System.out.println("\n\n============================== REQUEST 8 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 8 ==========================\n";
		jsonResp += r.sendR7_json(service, r8);
		System.out.println("\n\n============================== REQUEST 9 ==========================\n");
		jsonResp += "\n\n============================== REQUEST 9 ==========================\n";
		jsonResp += r.sendR6AndR8_json_r9(service, r9_1,r9_2, r9_body_json);
		r.deleteR9(service, deleteR9);

		//XML
		System.out.println("\n\n============================== REQUEST 1 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 1 ==========================\n";
		xmlResp += r.sendR1_xml(service, r1);
		System.out.println("\n\n============================== REQUEST 2 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 2 ==========================\n";
		xmlResp += r.sendR2_xml(service, r2);
		System.out.println("\n\n============================== REQUEST 3 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 3 ==========================\n";
		xmlResp += r.sendR3_xml(service, r3, r3_body_xml);
		System.out.println("\n\n============================== REQUEST 4 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 4 ==========================\n";
		xmlResp += r.sendR4_xml(service, r4, r4_body_xml);
		System.out.println("\n\n============================== REQUEST 5 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 5 ==========================\n";
		xmlResp += r.sendR5_xml(service, r5);
		System.out.println("\n\n============================== REQUEST 6 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 6 ==========================\n";
		xmlResp += r.sendR9_xml(service, r6);
		System.out.println("\n\n============================== REQUEST 7 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 7 ==========================\n";
		xmlResp += r.sendR6_xml(service, r7, firstPerson, lastPerson);
		System.out.println("\n\n============================== REQUEST 8 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 8 ==========================\n";
		xmlResp += r.sendR7_xml(service, r8);
		System.out.println("\n\n============================== REQUEST 9 ==========================\n");
		xmlResp += "\n\n============================== REQUEST 9 ==========================\n";
		xmlResp += r.sendR6AndR8_xml_r9(service, r9_1,r9_2, r9_body_xml);
		r.deleteR9(service, deleteR9);

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

	private int countOccourence(String text, String token)
	{
		int counter = 0;
		int lastIndex = 0;

		while(lastIndex != -1){

			lastIndex = text.indexOf(token,lastIndex);

			if(lastIndex != -1){
				counter ++;
				lastIndex += token.length();
			}
		}

		return counter;
	}

	private String countPerson(String body) 
	{
		//Count Person in the response, if there are much than 2 people the 
		//result is OK, else the result is ERROR
		String result = "";

		String token = "firstname"; //beacause thi is a field present only in Person
		int counter = countOccourence(body, token);
		
		if(counter > 2)
			result = "OK";
		else
			result = "ERROR";
		
		return result;
	}

	private int countTypes(String body) 
	{
		String token = "</type"; //for xml
		String token1 = ",";

		int counter = countOccourence(body, token);
		if(counter == 0)
			counter = countOccourence(body, token1) + 1;

		return counter;
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

	private String makeStringInfo(int number, String httpMethod, String url, String type, 
		String contentType, String result, int status, String body)
	{
		String res = "";

		res += "\n\nRequest #"+number+": "+httpMethod+" "+url+" Accept: "+type+" content-type: "+contentType;
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

		printableResponse = makeStringInfo(2, "GET", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
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

		printableResponse = makeStringInfo(2, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
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
		if(status == 201 || status == 202 || status == 200)
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
		if(status == 201  || status == 202 || status == 200)
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

	private String sendR4_xml(WebTarget service, String path, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(path).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(req));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(4, "POST", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
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

		if(status == 404 || status == 200 || status == 204)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(5, "DELETE", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}


	private void deleteR9(WebTarget service, String req)
	{
		Response response;
		response = service.path(req).request().accept(MediaType.APPLICATION_JSON).delete();
	}


	private String sendR5_xml(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_XML).delete();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 404 || status == 200 || status == 204)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(5, "DELETE", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}

	private String sendR9_xml(WebTarget service, String req)
	{
		Response response;
		String printableResponse;
		int status;
		String body = "";
		String result;

		//Accept: applicationjson
		response = service.path(req).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		int counter = countTypes(body);

		if(counter > 2)
			result = "OK";
		else
			result = "ERROR";

		this.measure_types = new String[counter];

		//parse body
		String token1 = "<type>";
		String token2 = "</type>";

		int pos1;
		int pos2;

		String tmpBody;
		for(int i = 0; i < counter; i++)
		{
			pos1 = body.indexOf(token1);
			tmpBody = body.substring(pos1+token1.length());
			pos2 = tmpBody.indexOf(token2) + body.length() - tmpBody.length();

			this.measure_types[i] = body.substring(pos1 + token1.length(), pos2);

			if( tmpBody.indexOf(token2)+token2.length() < tmpBody.length())
			{
				body = tmpBody.substring( tmpBody.indexOf(token2)+token2.length());
			}
		}


		printableResponse = makeStringInfo(6, "GET", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}
	
	private String sendR9_json(WebTarget service, String req)
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

		this.measure_types = new String[counter];

		//parse body
		String token1 = "\"";
		String token2 = "\"";

		int pos1;
		int pos2;

		//modify body for parsing
		body = body.substring(body.indexOf("[") + "[".length(), body.indexOf("]"));

		String tmpBody;
		for(int i = 0; i < counter; i++)
		{
			pos1 = body.indexOf(token1);
			tmpBody = body.substring(pos1+token1.length());
			pos2 = tmpBody.indexOf(token2) + body.length() - tmpBody.length();

			this.measure_types[i] = body.substring(pos1 + token1.length(), pos2);

			if( tmpBody.indexOf(token2)+token2.length() < tmpBody.length())
			{
				body = tmpBody.substring( tmpBody.indexOf(token2)+token2.length());
			}
		}

		printableResponse = makeStringInfo(6, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);

		return printableResponse;
	}

	private String sendR6_json(WebTarget service, String req, int firstname, int lastname)
	{
		Response response;
		String printableResponse = "";
		int status;
		String body = "";
		String result;

		for(int i = 0; i < this.measure_types.length; i++)
		{
			response = service.path(req +firstname+"/"+this.measure_types[i]).request().accept(MediaType.APPLICATION_JSON).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			if(status == 200)
				result = "OK";
			else
				result = "ERROR";

			printableResponse += makeStringInfo(7, "GET", Assignment2Client.URI + req+firstname+"/"+this.measure_types[i], "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		for(int i = 0; i < this.measure_types.length; i++)
		{
			response = service.path(req +lastname+"/"+this.measure_types[i]).request().accept(MediaType.APPLICATION_JSON).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			if(status == 200)
				result = "OK";
			else
				result = "ERROR";

			printableResponse += makeStringInfo(7, "GET", Assignment2Client.URI + req+lastname+"/"+this.measure_types[i], "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		//TODO INSERIRE CONTROLLO PER OK SU TUTTE LE RICHIESTE


		return printableResponse;
	}

	private String sendR6_xml(WebTarget service, String req, int firstname, int lastname)
	{
		Response response;
		String printableResponse = "";
		int status;
		String body = "";
		String result;

		for(int i = 0; i < this.measure_types.length; i++)
		{
			response = service.path(req +firstname+"/"+this.measure_types[i]).request().accept(MediaType.APPLICATION_XML).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			if(status == 200)
				result = "OK";
			else
				result = "ERROR";

			printableResponse += makeStringInfo(1, "GET", Assignment2Client.URI + req +firstname+"/"+this.measure_types[i], "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		for(int i = 0; i < this.measure_types.length; i++)
		{
			response = service.path(req +lastname+"/"+this.measure_types[i]).request().accept(MediaType.APPLICATION_XML).get();
			status = response.getStatus();
			body = response.readEntity(String.class);
			
			if(status == 200)
				result = "OK";
			else
				result = "ERROR";

			printableResponse += makeStringInfo(1, "GET", Assignment2Client.URI + req +lastname+"/"+this.measure_types[i], "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
			System.out.println(printableResponse);
		}

		//TODO INSERIRE CONTROLLO PER OK SU TUTTE LE RICHIESTE


		return printableResponse;
	}

	private String sendR7_xml(WebTarget service, String req)
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

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(8, "GET", Assignment2Client.URI + req, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
		System.out.println(printableResponse);
		

		return printableResponse;
	}

	private String sendR7_json(WebTarget service, String req)
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

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(8, "GET", Assignment2Client.URI + req, "APPLICATION/JSON", "APPLICATION/JSON",result ,status ,body);
		System.out.println(printableResponse);
		

		return printableResponse;
	}

	private String sendR6AndR8_xml_r9(WebTarget service, String req1, String req2, String req2_body)
	{
		Response response;
		String printableResponse;
		int status;
		String body = ""; 
		String result;

		//Accept: applicationxml
		response = service.path(req1).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(9, "GET", Assignment2Client.URI + req1, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
		System.out.println(printableResponse);
		

		int counter = countOccourence(body, "idMeasureType");

		//Accept: application xml
		response = service.path(req2).request().accept(MediaType.APPLICATION_XML).post(Entity.xml(req2_body));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		printableResponse += makeStringInfo(9, "POST", Assignment2Client.URI + req2, "APPLICATION/XML", "APPLICATION/XML" ,result ,status ,body);
		System.out.println(printableResponse);


		//Accept: applicationxml
		response = service.path(req1).request().accept(MediaType.APPLICATION_XML).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse += makeStringInfo(9, "GET", Assignment2Client.URI + req1, "APPLICATION/XML", "APPLICATION/XML",result ,status ,body);
		System.out.println(printableResponse);
		

		counter = countOccourence(body, "idMeasureType");

		return printableResponse;
	}

	private String sendR6AndR8_json_r9(WebTarget service, String req1, String req2, String req2_body)
	{
		Response response;
		String printableResponse;
		int status;
		String body = ""; 
		String result;

		//Accept: applicationjson
		response = service.path(req1).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse = makeStringInfo(9, "GET", Assignment2Client.URI + req1, "APPLICATION/JSON", "APPLICATION/JSON",result ,status ,body);
		System.out.println(printableResponse);
		

		int counter = countOccourence(body, "idMeasureType");

		//Accept: application json
		response = service.path(req2).request().accept(MediaType.APPLICATION_JSON).post(Entity.json(req2_body));
		status = response.getStatus();
		body = response.readEntity(String.class);

		Assignment2Client r = new Assignment2Client();

		if(status == 202 || status == 200 || status == 201)
			result = "OK";
		else
			result = "ERROR";

		printableResponse += makeStringInfo(9, "POST", Assignment2Client.URI + req2, "APPLICATION/JSON", "APPLICATION/JSON" ,result ,status ,body);
		System.out.println(printableResponse);


		//Accept: applicationjson
		response = service.path(req1).request().accept(MediaType.APPLICATION_JSON).get();
		status = response.getStatus();
		body = response.readEntity(String.class);

		if(status == 200)
			result = "OK";
		else
			result = "ERROR";

		printableResponse += makeStringInfo(9, "GET", Assignment2Client.URI + req1, "APPLICATION/JSON", "APPLICATION/JSON",result ,status ,body);
		System.out.println(printableResponse);
		

		counter = countOccourence(body, "idMeasureType");

		return printableResponse;
	}




}