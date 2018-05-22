package restful.webcontent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Shop")
public class ShopClass {
	private String id;
	private String pass;
	private String location;
	private String status;
	private String pid;
	private String quantity;
	private  int income;
	private Products products;
	public ShopClass() {
		
	}
	
	@XmlElement(name = "id")
	   public String getID() {
	       return id;
	   }
	
	@XmlElement(name = "income")
	   public int getIncome() {
	       return income;
	   }
//	@XmlElementWrapper(name = "products")
	@XmlElement(name = "product")
	   public Products getProducts() {
	       return products;
	   }
	@XmlElement(name = "location")
	   public String getlocation() {
	       return location;
	   }
	@XmlElement(name = "pid")
	   public String getPID() {
	       return pid;
	   }
	@XmlElement(name = "pass")
	   public String getpass() {
	       return pass;
	   }
	@XmlElement(name = "status")
	   public String getStatus() {
	       return status; 
	   }
	@XmlElement(name = "quantity")
	   public String getQuantity() {
	       return quantity;
	   }
	public void setID(String id)
	{this.id=id;}
	public void setIncome(int income)
	{this.income=income;}
	public void setProducts(Products products)
	{this.products=products;}
	public void setpass(String pass)
	{this.pass=pass;}
	public void setlocation(String location)
	{this.location=location;}
	public void setStatus(String status)
	{this.status=status;}
	public void setPID(String pid)
	{this.pid=pid;}
	public void setQuantity(String quantity)
	{this.quantity=quantity;}
}

