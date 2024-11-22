package idmRestBackEnd.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ColumnDefinition {
	private String field;
	private String title;
	private int ordering;
	//private String type;
	/*public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	*/

	public int getOrdering() {
		return ordering;
	}

	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}

	public String getFormatter() {
		return formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	private String formatter;
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
