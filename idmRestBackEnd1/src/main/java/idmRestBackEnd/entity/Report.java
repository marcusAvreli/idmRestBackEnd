package idmRestBackEnd.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class Report {
	@XmlElement(nillable = true, name = "columns")
	private List<ColumnDefinition> columns; 
	@XmlElement(nillable = true, name = "data")
	private List<Map<String,Object>> data;
	public List<ColumnDefinition> getColumns() {
		return columns;
	}
	public void setColumns(List<ColumnDefinition> columns) {
		this.columns = columns;
	}
	public List<Map<String,Object>> getData() {
		return data;
	}
	public void setData(List<Map<String,Object>> data) {
		this.data = data;
	}
	
	public void addRow(Map<String,Object> inDataMap) {
		if(this.data == null) {
			this.data = new ArrayList<Map<String,Object>>();
		}
		this.data.add(inDataMap);
	}
	public void addColumn(ColumnDefinition cf) {
		if(this.columns == null) {
			this.columns = new ArrayList<ColumnDefinition>();
		}
		this.columns.add(cf);
	}
	
	public static boolean isEmpty(Report inReport) {
		boolean isEmpty = false;
		if(null != inReport) {
			List<Map<String,Object>> datum = inReport.getData();
			if(null == datum) {
				isEmpty = true;
			}
		}
		return isEmpty;
	}
	
	
}
