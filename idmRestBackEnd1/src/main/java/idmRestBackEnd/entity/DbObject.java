package idmRestBackEnd.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DbObject {

	@XmlElement(nillable = true, name = "type")
	private String object_type;
	
	@XmlElement(nillable = true, name = "name")
	private String object_name;
	public String getObject_type() {
		return object_type;
	}
	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}
	public String getObject_name() {
		return object_name;
	}
	public void setObject_name(String object_name) {
		this.object_name = object_name;
	}
	
	
}
