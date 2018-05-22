package restful.webcontent;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Product")
public class ProductClass {
	 	String Name;
		String ProductID;
		String Status;
	    int Price;
	    String Description;
	    String Manufacture;
	    @XmlElement(name = "ProductID")
	    public String getProductID() {
	        return ProductID;
	    }

	    public void setProductID(String ProductID) {
	        this.ProductID = ProductID;
	    }
	    @XmlElement(name = "Status")
	    public String getStatus() {
	        return Status;
	    }

	    public void setStatus(String Status) {
	        this.Status= Status;
	    }
	    @XmlElement(name = "Name")
	    public String getName() {
	        return Name;
	    }

	    public void setName(String Name) {
	        this.Name = Name;
	    }
	    @XmlElement(name = "Description")
	    public String getDescription() {
	        return Description;
	    }

	    public void setDescription(String Description) {
	        this.Description = Description;
	    }
	    @XmlElement(name = "Price")
	    public int getPrice() {
	        return Price;
	    }

	    public void setPrice(int Price) {
	        this.Price = Price;
	    }
	    @XmlElement(name = "Manufacture")
	    public String getManufacture() {
	        return Manufacture;
	    }

	    public void setManufacture(String Manufacture) {
	        this.Manufacture = Manufacture;
	    }

}
