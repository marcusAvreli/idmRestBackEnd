package idmRestBackEnd.entity;

public class RprtUserView {
	private String id;
	private String rprt_id;
	private String name;
	private String display_name;
	private String description;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRprt_id() {
		return rprt_id;
	}
	public void setRprt_id(String rprt_id) {
		this.rprt_id = rprt_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplay_name() {
		return display_name;
	}
	public void setDisplay_name(String display_name) {
		this.display_name = display_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
