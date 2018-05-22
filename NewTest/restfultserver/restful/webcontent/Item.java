package restful.webcontent;
//This class to store the infomation of the items for the server to parsing XML which pass from the client to Object to store Transaction in the database
import java.lang.reflect.Field;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement(name = "Item")
public class Item {
    String id;
    int quantity;
    String name;
    int price;
    @XmlElement(name = "id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    @XmlElement(name = "quantity")
    public int getAmount() {
        return quantity;
    }

    public void setAmount(int quantity) {
        this.quantity = quantity;
    }
    @XmlElement(name = "price")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}