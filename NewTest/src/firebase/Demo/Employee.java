package firebase.Demo;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
 
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class Employee {
	private String ShopID;
	   private String TransactionID;
	   private String Status;
	   // Constructor máº·c Ä‘á»‹nh nÃ y lÃ  báº¯t buá»™c náº¿u cÃ³ thÃªm cáº¥u tá»­ khÃ¡c.
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
