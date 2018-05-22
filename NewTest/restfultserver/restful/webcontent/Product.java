package restful.webcontent;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
//Libraries for firebase connecting base on API Firebase4J Copyright (c) 2012 Brandon Gresham
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import firebase.Error.FirebaseException;
import firebase.Error.JacksonUtilityException;
import firebase.model.FirebaseResponse;
import firebase.service.Firebase;
@Path("/Product")
public class Product {
	@GET
	@Produces("application/xml")
	@Path("/newProduct/{ProductID}/{Name}/{Price}/{Manufacture}&{Description}")
	//Using the URL http://localhost:8080/NewTest/backend/Product/newProduct/{ProductID}/{Name}/{Price}/{Manufacture}&{Description}
	//For example:	http://localhost:8080/NewTest/backend/Product/newProduct/13/Coca/300/CocaColaVietNam&SoftDrink
	//To create and saving the information of new product
	public String createProduct(@PathParam("ProductID") String proID,@PathParam("Name") String name,
	@PathParam("Price") int price,@PathParam("Manufacture") String factory,@PathParam("Description") String des) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl);
		Map<String,Object> data= new LinkedHashMap<String,Object>();
			data.put("Name",name);
			data.put("Price",price);
			data.put("Manufacture",factory);
			data.put("Description",des);
		String path="ProductID/"+proID;
		FirebaseResponse response = firebase.patch(path,data);
		String a=response.getRawBody();
		if(!a.equals("null"))
		{		return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Product Saved"+"</Status>"//
						+"</product>";}
		else {	return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Fail to saved"+"</Status>"//
						+"</product>";}}
	@GET
	@Produces("application/xml")
	@Path("/updatePrice/{ProductID}/{Price}")
	//Using the URL http://localhost:8080/NewTest/backend/Product/updatePrice/{ProductID}/{Price}
	//For example:	http://localhost:8080/NewTest/backend/Product/updatePrice/13/20
	//To updating the price of the product.
	public String updatePrice(@PathParam("ProductID") String proID,@PathParam("Price") int price) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ProductID/"+proID;
		Map<String,Object> data= new LinkedHashMap<String,Object>();
		FirebaseResponse response = firebase.get(path);
			data=response.getBody();
			data.put("Price", price);
		String a=response.getRawBody();
		if(!a.equals("null")) 
			{response=firebase.patch(path, data);
				return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Update Price Success"+"</Status>"//
						+"</product>";}
		else {	return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Fail to update"+"</Status>"//
						+"</product>";}}
	@GET
	@Produces("application/xml")
	@Path("/updateInfo/{ProductID}{Name:(/name/[^/]+?)?}{Manufacture:(/man/[^/]+?)?}{Description:(/des/[^/]+?)?}")
	//Using the URL http://localhost:8080/NewTest/backend/Product/updateInfo/{ProductID}[/name/{Name}][/man/{Manufacture}][/des/{Description}]
	//with {Name},{Manufacture},{Description}
	//are optional depend on the user.
	//For example:	http://localhost:8080/NewTest/backend/Product/updateInfo/1/name/CocaCola/des/Softdrink
	//To updating the information of the product such as name, manufacture, description
	public String updateInfo(@PathParam("ProductID") String proID,@PathParam("Name") String name,
	@PathParam("Manufacture") String factory,@PathParam("Description") String des) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ProductID/"+proID;
		Map<String,Object> data= new LinkedHashMap<String,Object>();
		FirebaseResponse response = firebase.get(path);
		data=response.getBody();
		int i=0;
		if(!name.equals("")) 
			{name=name.split("/")[2];
			 data.put("Name",name);
			 i++;}
		if(!factory.equals("")) 
			{factory = factory.split("/")[2];
			data.put("Manufacture",factory);
			i++;}
		if(!des.equals(""))
			{des = des.split("/")[2];
			data.put("Description",des);
			i++;}
		if(i>0)
			{response=firebase.put(path, data);}
		String a=response.getRawBody();
		if(!a.equals("null")) 
		{		return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Update Info Success"+"</Status>"//
						+"</product>";}
		else {	return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"No info to update"+"</Status>"//
						+"</product>";}}
	@GET
	@Produces("application/xml")
	@Path("/deleteProduct/{ProductID}")
	//Using the URL http://localhost:8080/NewTest/backend/Product/deleteProduct/{ProductID}
	//For example:	http://localhost:8080/NewTest/backend/Product/deleteProduct/13
	//To delete the product with is no business anymore.
	public String deletePro(@PathParam("ProductID") String proID) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ProductID/"+proID;
		FirebaseResponse response = firebase.delete(path);
		String a=response.getRawBody();
		if(a.equals("null")) {
				return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Deleted."+"</Status>"//
						+"</Product>";}
		else {  return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Error!!!"+"</Status>"//
						+"</Product>";}}
	@GET
	@Produces("application/xml")
	@Path("/getProduct/{ProductID}")
	//Using the URL http://localhost:8080/NewTest/backend/Product/getProduct/{ProductID}
	//For example:	http://localhost:8080/NewTest/backend/Product/getProduct/13
	//To detail of the product such as name,price, manufacture, description
	public static String getPro(@PathParam("ProductID") String proID) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
		{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ProductID/"+proID;
		Map<String,Object> data= new LinkedHashMap<String,Object>();
		FirebaseResponse response = firebase.get(path);
			data=response.getBody();
		String a=response.getRawBody();
		if(!a.equals("null")) 
		{		return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Name>"+data.get("Name")+"</Name>"//
						+"<Price>"+data.get("Price")+"</Price>"//
						+"<Manufacture>"+data.get("Manufacture")+"</Manufacture>"//
						+"<Description>"+data.get("Description")+"</Description>"//
						+"</product>";}
		else {  return "<product>"//
						+"<ProductID>"+proID+"</ProductID>"//
						+"<Status>"+"Fail to load"+"</Status>"//
						+"</product>";}}
	@GET
	@Path("/getProductID")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/getProductID
	//For example:	http://localhost:8080/NewTest/backend/Shop/getProductID
	//To getting the number of products are registered in database it also provide the already using productID for client generating the new productID
	//ensure the uniqueness of the productID
	public String getProductID()
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ProductID";
		//The basic idea of this function is get the information of all proID node in the database
		//after that using the Map<String,Object> to store the information response the database
		//and using keySet() function to get the proID of all shop in the database store the keySet in the string and return it to client
		FirebaseResponse response=firebase.get(path);
		Map<String,Object>store=new HashMap<String,Object>();
			store=response.getBody();
		String result="";
		for(String id:store.keySet())
			{result=result+"<product>"+"<ProductID>"+id+"</ProductID>"+"</product>";}
		return "<products>"+result+"</products>";}
	}
