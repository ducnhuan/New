package restful.webcontent;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.Consumes;
//Libraries for Restfult server 
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
//Libraries for firebase connecting base on API Firebase4J Copyright (c) 2012 Brandon Gresham
import firebase.Error.FirebaseException;
import firebase.Error.JacksonUtilityException;
import firebase.model.FirebaseResponse;
import firebase.service.Firebase;
import restful.webcontent.Product;


@Path("/Shop")
public class Shop {
	@POST
	@Path("/newShop")
	@Consumes("text/xml")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/newShop
	//For example:	http://localhost:8080/NewTest/backend/Shop/newShop
	//To create and saving the information of new shop 
	public String CreateShop(String xmlString) 
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException, JAXBException
	{	System.out.println("Starting..");
		StringReader reader = new StringReader(xmlString);
		JAXBContext jaxbContext = JAXBContext.newInstance(ShopClass.class);
		Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		ShopClass elementsOut = (ShopClass) jaxbUnmarshaller.unmarshal(reader);
		StringBuffer password=encrypt(elementsOut.getpass(),7);
		//Connect to the data base
		//Using string firebase_baseUrl to store the URL relative to the cloud database
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty())
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		//Using the Map to store the data and after that patch the data the the path with relative to the shop information
		Map<String,Object> dataShop= new LinkedHashMap<String,Object>();
			dataShop.put("Location" , elementsOut.getlocation());
			dataShop.put("Password",password);
		String path="ShopID/"+elementsOut.getID();
		FirebaseResponse response = firebase.patch(path,dataShop);
		String a=response.getRawBody();
		//Using the response.getRawBody() to check that the data is add to the database success or not
		//Base on that return the response XML string the the client
		if(!a.equals("null")) {
				return "<Shop>"//
						+"<id>"+elementsOut.getID()+"</id>"//
						+"<status>"+"New Shop is created"+"</status>"//
						+"</Shop>";}
		else {return "<Shop>"//
					 +"<id>"+elementsOut.getID()+"</id>"//
					 +"<status>"+"Fail to create"+"</status>"//
					 +"</Shop>";}}
	@GET
	@Produces("application/xml")
	@Path("/AddProduct/{ShopID}/{ProductID}/{Amount}")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/AddProduct/{ShopID}/{ProductID}/{Amount}
	//For example:	http://localhost:8080/NewTest/backend/Shop/AddProduct/1/1/15
	//To add the product and it amount to existing shop in the database 
	public String addProduct(@PathParam("ShopID") String shopID,@PathParam("ProductID") String proID, @PathParam("Amount") int Amount)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		Map<String,Object> dataShop= new LinkedHashMap<String,Object>();
			dataShop.put("Amount",Amount);
		String path="ShopID/"+shopID+"/ProductID/"+proID;
		//This path direct to the product part at the shop information which will perform the amount of the product the shop is have in real time
		FirebaseResponse response=firebase.patch(path, dataShop);
		System.out.println("Result of add Product:"+response);
		String a=response.getRawBody();
		//This function the server will return to the client the status of the addition operation 
		if(!a.equals("null")) {
				return "<Shop>"//
						+"<id>"+shopID+"</id>"//
						+"<pid>"+proID+"</pid>"//
						+"<status>"+"New Product is added"+"</status>"//
						+"</Shop>";}
		else {	return "<Shop>"//
						+"<id>"+shopID+"</id>"//
						+"<pid>"+proID+"</pid>"//
						+"<status>"+"Fail to add product"+"</status>"//
						+"</Shop>";}}
	@GET
	@Produces("application/xml")
	@Path("/updateAmount/{ShopID}/{ProductID}/{Amount}/{Type}")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/updateAmount/{ShopID}/{ProductID}/{Amount}/{Type}
	//For example:	http://localhost:8080/NewTest/backend/Shop/updateAmount/1/1/15/add
	//To adding the 15 products to the amount of product having ProductID in the URL
	//For example:	http://localhost:8080/NewTest/backend/Shop/updateAmount/1/1/15/sale
	//To decrease 15 products to the amount of product having ProductID in the URL (not necessary because the amount will be decrease automatically 
	//when adding the new transaction  ) 
	public String updateAmount(@PathParam("ShopID") String shopID,@PathParam("ProductID") String proID, @PathParam("Amount") int change,@PathParam("Type") String type)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID/"+shopID+"/ProductID/"+proID+"/Amount";
		FirebaseResponse response=firebase.get(path);
		String value=response.getRawBody();
		int val=Integer.valueOf(value);
		int change1=(int)change;
		Map<String,Object> temp=new HashMap<String,Object>();
		if(type.equals("add"))
			{temp.put("Amount",val+change1);}
		else 
			{temp.put("Amount",val-change1);}
		path="ShopID/"+shopID+"/ProductID/"+proID;
		response=firebase.patch(path, temp);
		String a=response.getRawBody();
		if(!a.equals("null")) {
			    return "<Shop>"//
			    		+"<id>"+shopID+"</id>"//
			    		+"<pid>"+proID+"</pid>"//
			    		+"<status>"+"Amount value has been updated."+"</status>"//
			    		+"</Shop>";}
		else {  return "<Shop>"//
						+"<id>"+shopID+"</id>"//
						+"<pid>"+proID+"</pid>"//
						+"<status>"+"Fail to update amount value"+"</status>"//
						+"</Shop>";}}
	@GET
	@Path("/getShopID")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/getShopID
	//For example:	http://localhost:8080/NewTest/backend/Shop/getShopID
	//To getting the number of shop are registered in database it also provide the already using shopID for client generating the new ShopID
	//ensure the uniqueness of the ShopID
	public String getShopID()
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID";
		//The basic idea of this function is get the information of all ShopID node in the database
		//after that using the Map<String,Object> to store the information response the database
		//and using keySet() function to get the shopID of all shop in the database store the keySet in the string and return it to client
		FirebaseResponse response=firebase.get(path);
		Map<String,Object>store=new HashMap<String,Object>();
			store=response.getBody();
		String result="";
		for(String id:store.keySet())
			{result=result+"<Shop>"+"<id>"+id+"</id>"+"</Shop>";}
		return "<Shops>"+result+"</Shops>";}
	@GET
	@Produces("application/xml")
	@Path("/GetAmountSpec/{ShopID}/{ProductID}")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/GetAmountSpec/{ShopID}/{ProductID}
	//For example:	http://localhost:8080/NewTest/backend/Shop/GetAmountSpec/1/2
	//To getting the amount of the product in specific shop in real time 
	public String getAmountProduct(@PathParam("ShopID") String shopID,@PathParam("ProductID") String proID)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException 
	{	System.out.println("Starting..");
		String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID/"+shopID+"/ProductID/"+proID+"/Amount";
		//This path is direct to the Amount node which content the amount information of the product in the shop
		FirebaseResponse response=firebase.get(path);
		String a=response.getRawBody();
		System.out.println(a);
		if(!a.equals("null"))
		{	    return "<Shop>"//
			    		+"<id>"+shopID+"</id>"//
			    		+"<pid>"+proID+"</pid>"//
			    		+"<quantity>"+a+"</quantity>"//
			    		+"</Shop>";}
		else {  return "<Shop>"//
					    +"<id>"+shopID+"</id>"//
					    +"<pid>"+proID+"</pid>"//
					    +"<Status>"+"Fail to load amount"+"</Status>"//
					    +"</Shop>";}}
	@GET
	@Produces("application/xml")
	@Path("/GetAmount/{ShopID}")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/GetAmount/{ShopID}
	//For example:	http://localhost:8080/NewTest/backend/Shop/GetAmount/1
	//To getting the amount of the products in specific shop in real time this will help the manager to decide which product 
	//should be order from the manufacture 
	public String getAmount(@PathParam("ShopID") String shopID)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID/"+shopID+"/ProductID/";
		//This path is point to the node content the ID and amount of the products of this shop
		//The idea is getting the data and using the Map<String,Object> to stored the data of products 
		//after that using Map<String,Integer> to store the data of each product in the list and 
		//called the function getPro() from class product to get information about the manufacture, name of the product ID.
		FirebaseResponse response=firebase.get(path);
		Map<String,Object> answer=new LinkedHashMap<String,Object>();
		Map<String,Integer> data=new LinkedHashMap<String,Integer>();
			answer=response.getBody();
		for(String id:answer.keySet()) 
			{	Map<String,Integer>temp=new LinkedHashMap<String,Integer>();
				temp=(Map<String,Integer>) answer.get(id);
				data.put(id,temp.get("Amount"));}
		String store="";
		for (String id: data.keySet())
		{		String temp;
				temp=Product.getPro(id);
				try {
					DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
					InputSource src = new InputSource();
					src.setCharacterStream(new StringReader(temp)); 
					System.out.println(temp);
					Document doc = builder.parse(src);
					String name = doc.getElementsByTagName("Name").item(0).getTextContent();
					String factory = doc.getElementsByTagName("Manufacture").item(0).getTextContent();
					String price=doc.getElementsByTagName("Price").item(0).getTextContent();
					store=store+"<product>"+"<Name>"+name+"</Name>"//
							+"<Manufacture>"+factory+"</Manufacture>"//
							+"<Price>"+price+"</Price>"//
							+"<quantity>"+data.get(id)+"</quantity>" //
							+"<ProductID>"+id+"</ProductID>"//
							+"</product>";}
				catch(Exception e) {System.out.print(e);}}
		System.out.println(store);
		return "<products>"+store+"</products>";}
	@GET
	@Produces("application/xml")
	@Path("/GetIncome/{ShopID}/{Date}")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/GetIncome/{ShopID}/{Date}
		//For example:	http://localhost:8080/NewTest/backend/Shop/GetIncome/1/24-07-1997
		//For the manager to track the income of a shop in a date 
	public String getIncome(@PathParam("ShopID") String shopID,@PathParam("Date") String date)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty()) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="Transaction/"+shopID+"/"+date;
		//Idea is that we get all transaction of this shop in a day after that we get the total of each shop and sum it to get the total income of the shop in a day.
		FirebaseResponse response=firebase.get(path);
		Map<String,Object> transactions=new HashMap<String,Object>();
			transactions=response.getBody();
			System.out.println(response.getRawBody());
		int income=0;
		System.out.println(transactions.keySet());
		for(String i:transactions.keySet()) 
		{	Map<String,Object> transaction=new HashMap<String,Object>();
			transaction=(Map<String,Object>)transactions.get(i);
			System.out.println(Integer.valueOf(transaction.get("Total").toString()));
			income=income+Integer.valueOf(transaction.get("Total").toString());}
		String a=response.getRawBody();
		if(!a.equals("null")) {
				return "<Shop>"//
						+"<id>"+shopID+"</id>"//
						+"<income>"+income+"</income>"//
						+"</Shop>";}
		else {	return "<Shop>"//
						+"<id>"+shopID+"</id>"//
						+"<income>"+null+"</income>"//
						+"</Shop>";}}
	public static StringBuffer encrypt (String text,int s)
		{StringBuffer result= new StringBuffer();
			for (int i=0; i<text.length(); i++)
	        {if (Character.isUpperCase(text.charAt(i)))
	            {char ch = (char)(((int)text.charAt(i) +s - 65) % 26 + 65);
	                result.append(ch);}
	            else
	            {char ch = (char)(((int)text.charAt(i) +s - 97) % 26 + 97);
	                result.append(ch);}}
	        return result;}
	@GET
	@Path("/getPass/{ShopID}")
	@Produces("application/xml")
	//Using the URL http://localhost:8080/NewTest/backend/Shop/getPass/{ShopID}
	//For example:	http://localhost:8080/NewTest/backend/Shop/getPass/1
	//Getting the password of this shop in encryption format
	public String getPassword(@PathParam("ShopID") String shopID)
	throws FirebaseException, JsonParseException, JsonMappingException, IOException, JacksonUtilityException
	{	String firebase_baseUrl = "https://neutest-2aa2b.firebaseio.com/";
		if( firebase_baseUrl == null || firebase_baseUrl.trim().isEmpty() ) 
			{throw new IllegalArgumentException( "Program-argument 'baseUrl' not found but required" );}
		Firebase firebase = new Firebase( firebase_baseUrl );
		String path="ShopID/"+shopID;
		FirebaseResponse response=firebase.get(path);
		Map<String,Object> neu=new LinkedHashMap<String,Object>();
		neu=response.getBody();
		String result="";
		System.out.println(response.getBody().getClass());
		result="<pass>"+neu.get("Password")+"</pass>";
		return "<Shop>"+result+"</Shop>";}



}



