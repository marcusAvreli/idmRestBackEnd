package idmRestBackEnd.entity;

public class RprtUserViewColumn {
	private String field_id;
	private String id;
	private String uv_id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUv_id() {
		return uv_id;
	}
	public void setUv_id(String uv_id) {
		this.uv_id = uv_id;
	}
	private int ordering;
	public String getField_id() {
		return field_id;
	}
	public void setField_id(String field_id) {
		this.field_id = field_id;
	}
	public int getOrdering() {
		return ordering;
	}
	public void setOrdering(int ordering) {
		this.ordering = ordering;
	}
	
	
}
