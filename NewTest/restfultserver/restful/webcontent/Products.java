package restful.webcontent;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
@XmlAccessorType(XmlAccessType.FIELD)
public class Products {
    @XmlElement(name = "product")

    private List<ProductClass> products = null;

	public List<ProductClass> getListProducts() {
		return products;
	}

	public void setProducts(List<ProductClass> products) {
		this.products = products;
	}



}