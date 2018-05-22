package firebase.Demo;


import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
//import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

import restful.webcontent.ShopClass;
public class ClientTest {
	public static void main(String[] args) {
		 
		 
	      // Tạo đối tượng Client
	
			 
		      ClientConfig clientConfig = new DefaultClientConfig();
		 
		      // Tạo đối tượng Client dựa trên cấu hình.
		      Client client = Client.create(clientConfig);
		      System.out.println("Starting..");
		      WebResource webResource = client.resource("http://localhost:8080/NewTest/backend/Shop/AddProduct/2/13/15");
		 
		    

		      String input1 ="<report>\r\n" + 
		      		"<id>9</id>\r\n" + 
		      		"<storeid>14</storeid>\r\n" + 
		      		"<total>11</total>\r\n" + 
		      		"<items>\r\n" + 
		      		"	<item>\r\n" + 
		      		"		<id>13</id>\r\n" + 
		      		"		<quantity>1</quantity>\r\n" + 
		      		"		<name>Cocal</name>\r\n" + 
		      		"		<price>10</price>\r\n" + 
		      		"		\r\n" + 
		      		"	</item>\r\n" + 
		      		"	<item>\r\n" + 
		      		"		<id>14</id>\r\n" + 
		      		"		<quantity>2</quantity>\r\n" + 
		      		"		<name>Cocal1</name>\r\n" + 
		      		"		<price>12</price>\r\n" + 
		      		"	</item>\r\n" + 
		      		"</items>\r\n" + 
		      		"<date>24-06-1998</date>\r\n" + 
		      		"</report>"; 
		      ClientResponse response = webResource.type("text/xml")
			   		   .get(ClientResponse.class);
		    
		
		      // Trạng thái 200 là thành công
		      if (response.getStatus() != 200) {
		          System.out.println("Failed with HTTP Error code: " + response.getStatus());
		         String error= response.getEntity(String.class);
		         System.out.println("Error: "+error);
		          return;     }
		 
		      System.out.println("Output from Server .... \n");
		 
		      ShopClass employee = (ShopClass) response.getEntity(ShopClass.class);
		 
		      System.out.println("Emp No .... " + employee.getPID()); 
		      System.out.println("Emp No .... " + employee.getID());
		      System.out.println("Emp No .... " + employee.getStatus());
		     /// System.out.println("Emp Name .... " + employee.getEmpName());
		     // System.out.println("Position .... " + employee.getPosition());
		 
		  }
	  }
	 
	

