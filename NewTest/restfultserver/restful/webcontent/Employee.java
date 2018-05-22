package restful.webcontent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {
	private String ShopID;
	   private String TransactionID;
	   private String Status;
	   // Constructor mặc định này là bắt buộc nếu có thêm cấu tử khác.
	   public Employee() {
	 
	   }
	 
	   public Employee(String ShopID, String TransactionID, String Status) {
	       this.ShopID = ShopID;
	       this.TransactionID = TransactionID;
	       this.Status = Status;
	   }
	 
	   public String getEmpNo() {
	       return ShopID;
	   }
	 
	   public void setEmpNo(String ShopID) {
	       this.ShopID = ShopID;
	   }
	 
	   public String getEmpName() {
	       return TransactionID;
	   }
	 
	   public void setEmpName(String Date) {
		   this.TransactionID= Date;
	   }
	 
	   public String getPosition() {
	       return Status;
	   }
	 
	   public void setPosition(String Status) {
	       this.Status = Status;
	   }

}
