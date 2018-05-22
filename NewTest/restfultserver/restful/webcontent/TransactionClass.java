package restful.webcontent;
import java.lang.reflect.Field;
import java.util.List;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement(name = "Transaction")
//@XmlAccessorType(XmlAccessType.FIELD)
public class TransactionClass {
	private String storeid;
	private String id;
	private String date;
	private String time;
	private List<Item> Items;
	private String total;
	private String status;
	 public TransactionClass() {
		 }
	 
public void setStoreID(String storeid)
{this.storeid=storeid;}
public void setStatus(String status)
{this.status=status;}
@XmlElement(name = "status")
public String getStatus()
	{return status;}
public void setdate(String date)
{this.date=date;}

public void settime(String time)
{this.time=time;}
public void setID(String reportID)
{this.id=reportID;}
@XmlElement(name = "id")
public String getID()
	{return id;}
public void settotal(String total)
{this.total=total;}
@XmlElement(name = "total")
public String gettotal()
	{return total;}
@XmlElement(name = "storeid")
public String getStoreID()
	{return storeid;}
@XmlElement(name = "date")
public String getdate()
	{return date;}
@XmlElement(name = "time")
public String gettime()
	{return time;}
@XmlElementWrapper(name="Items")
@XmlElement(name = "Item")
public List<Item> getItems() {
    return Items;
}

public void setItems(List<Item> Items) {
    this.Items = Items;
}}


