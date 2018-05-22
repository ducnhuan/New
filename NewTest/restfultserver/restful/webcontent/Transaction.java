package restful.webcontent;

import java.util.List;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import firebase.Error.FirebaseException;
import firebase.Error.JacksonUtilityException;
import firebase.model.FirebaseResponse;
import firebase.service.Firebase;


@Path("/Transaction")
public class Transaction {
	@POST
	@Path("/newTransaction")
	@Consumes("text/xml")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Transaction/newTransaction
	//For example:	http://localhost:8080/NewTest/backend/Transaction/newTransaction
	//To post XML file content the transaction and server saved it in the database
	public String createTransaction(String xmlString )
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException,JAXBException
	{	//Parsing the XML String from the client to the Object base on the TransactionClass
		//to get the data, structure and store the transaction in to database
		//it also using updataAmount() function to update the amount of the product in each shop base on transaction
		StringReader reader = new StringReader(xmlString);
		JAXBContext jaxbContext = JAXBContext.newInstance(TransactionClass.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		TransactionClass elementsOut = (TransactionClass) jaxbUnmarshaller.unmarshal(reader);
		HashMap<String, Object>aa= new LinkedHashMap<String,Object>();
		HashMap<String, Object> data= new LinkedHashMap<String,Object>();
		for (Item re : elementsOut.getItems()) 
			{HashMap<String,Object> neu=new LinkedHashMap<String,Object>();
			neu.put("quantity",re.getAmount());
			neu.put("name",re.getName());
			neu.put("price",re.getPrice());
			aa.put(re.getId(),neu);
			updateAmount(elementsOut.getStoreID(),re.getId(),re.getAmount());}
		data.put("Items", aa);
		data.put("Time",elementsOut.gettime());
		data.put("Total",elementsOut.gettotal());
		System.out.println(data.toString());
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="Transaction/"+elementsOut.getStoreID()+"/"+elementsOut.getdate()+"/"+elementsOut.getID();
		FirebaseResponse response = firebase.patch(path, data);
		String a=response.getRawBody();
		if(!a.equals("null")) 
		   {return "<Transaction>"//
				    +"<storeid>"+elementsOut.getStoreID()+"</storeid>"//
				    +"<id>"+elementsOut.getID()+"</id>"//
				    +"<status>"+"Transaction Saved"+"</status>"//
				    +"</Transaction>";}
	else 
		   {return "<Transaction>"//
				    +"<storeid>"+elementsOut.getStoreID()+"</storeid>"//
				    +"<id>"+elementsOut.getID()+"</id>"//
				    +"<status>"+"Transaction fail to save"+"</status>"//
				    +"</Transaction>";}
}

	@GET
	@Path("/LoadTransaction/{ShopID}/{Date}/{TransactionID}")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Transaction/LoadTransaction/{ShopID}/{Date}/{TransactionID}
	//For example:	http://localhost:8080/NewTest/backend/Transaction/LoadTransaction/1/24-06-2018/1
	//To get the transaction which is stored in the database
	public String loadTransaction(@PathParam("ShopID") String shopID,@PathParam("TransactionID") String transID,@PathParam("Date") String date)
			throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException,JAXBException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if(firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="Transaction/"+shopID+"/"+date+"/"+transID;
		FirebaseResponse response=firebase.get(path);
		Map<String,Object> data=new HashMap<String,Object>();
		data=response.getBody();
		System.out.println(response.getRawBody());
		TransactionClass res=new TransactionClass();
		res.settime(String.valueOf(data.get("Time")));
		res.settotal(String.valueOf(data.get("Total")));
		res.setStoreID(shopID);
		res.setID(transID);
		HashMap<String,Object> items=new HashMap<String,Object>();
		items= (HashMap<String,Object>) data.get("Items");
		List<Item> temp=new ArrayList<Item>();
		for(String id:items.keySet())
			{Item templ= new Item();
			Map<String,Object> datat=(Map<String, Object>) items.get(id);
			System.out.println(items.get(id));
			templ.setAmount((int)datat.get("quantity"));
			templ.setName(datat.get("name").toString());
			templ.setPrice((int)datat.get("price"));
			templ.setId(id);
			temp.add(templ);}
		res.setItems(temp);
		System.out.println(res);	
		JAXBContext jaxbContext = JAXBContext.newInstance(TransactionClass.class);
		Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
		jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter sw = new StringWriter();
		jaxbMarshaller.marshal(res, sw);
		String xmlString = sw.toString();
		System.out.println(xmlString);
		String a=response.getRawBody();
		if(!a.equals(null)) {
		    return  xmlString;}
	else {	return "<Transaction>"//
			+"<id>"+transID+"</id>"//
			+"<items>"+null+"</items>"//
			+"<storeid>"+shopID+"</storeid>"//
			+"<time>"+null+"</time>"
			+"<date>"+date+"</date>"
			+"<total>"+null+"</total>"
			+"</Transaction>";}}
	public void updateAmount(String shopID, String proID,int change)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID/"+shopID+"/ProductID/"+proID+"/Amount";
		FirebaseResponse response=firebase.get(path);
		String value=response.getRawBody();
		int val=Integer.valueOf(value);
		int change1=(int)change;
		Map<String,Object> temp=new HashMap<String,Object>();
		temp.put("Amount",val-change1);
		path="ShopID/"+shopID+"/ProductID/"+proID;
		response=firebase.patch(path, temp);}
	@GET
	@Path("/getTransactionID/{ShopID}/{Date}")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Transaction/getTransactionID/{ShopID}/{Date}
	//For example:	http://localhost:8080/NewTest/backend/Transaction/getTransactionID/14/24-08-2018
	//To getting the number of transaction are registered in database in this date it also provide the already using transactionID for client generating the new TransactionID
	//ensure the uniqueness of the TransactionID
	public String getTransactionID(@PathParam("ShopID") String shopID,@PathParam("Date") String date)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="Transaction/"+shopID+"/"+date;
		//The basic idea of this function is get the information of all Transaction node in the database
		//after that using the Map<String,Object> to store the information response the database
		//and using keySet() function to get the TransactionID of all shop in the database store the keySet in the string and return it to client
		FirebaseResponse response=firebase.get(path);
		Map<String,Object>store=new HashMap<String,Object>();
			store=response.getBody();
		String result="";
		for(String id:store.keySet())
			{result="<Transaction>"+"<id>"+id+"</id>";}
		result=result+"</Transaction>";
		String a=response.getRawBody();
		System.out.println(response.getRawBody().equals("null"));
		if(!a.equals("null")) {
		return result;}
		else { 
			System.out.println("nukll");
			return result="<Transaction>"+"<id>"+"null"+"</id>"+"</Transaction>";}
}}
