package idmRestBackEnd.entity;

public class RprtField {
	private String name;
	private String rprt_name;
	private String rprt_id;
	private String id;
	
	private String description;
	private int disabled;
	private int not_visible;
	private String display_iw;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRprt_name() {
		return rprt_name;
	}
	public void setRprt_name(String rprt_name) {
		this.rprt_name = rprt_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getDisabled() {
		return disabled;
	}
	public void setDisabled(int disabled) {
		this.disabled = disabled;
	}
	public int getNot_visible() {
		return not_visible;
	}
	public void setNot_visible(int not_visible) {
		this.not_visible = not_visible;
	}
	public String getDisplay_iw() {
		return display_iw;
	}
	public void setDisplay_iw(String display_iw) {
		this.display_iw = display_iw;
	}
	public String getRprt_id() {
		return rprt_id;
	}
	public void setRprt_id(String rprt_id) {
		this.rprt_id = rprt_id;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
}
