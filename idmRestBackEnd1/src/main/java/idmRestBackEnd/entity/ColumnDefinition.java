package idmRestBackEnd.entity;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ColumnDefinition {
	private String field;
	private String title;
	private int ordering;
	private String formatter;
	

	private FormatterParam formatterParams;
	/*private boolean filterable = false;
	private HeaderFilterFuncParams headerFilterFuncParams;
	private String sorter;
	private String sortField;
	private String filterField;
	private String type;
	
	*/
	
	private String headerFilter;
	public String isHeaderFilter() {
		return headerFilter;
	}

	public void setHeaderFilter(String headerFilter) {
		this.headerFilter = headerFilter;
	}
/*
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getFilterField() {
		return filterField;
	}

	public void setFilterField(String filterField) {
		this.filterField = filterField;
	}

	public String getSorter() {
		return sorter;
	}

	public void setSorter(String sorter) {
		this.sorter = sorter;
	}

	public HeaderFilterFuncParams getHeaderFilterFuncParams() {
		return headerFilterFuncParams;
	}

	public void setHeaderFilterFuncParams(HeaderFilterFuncParams headerFilterFuncParams) {
		this.headerFilterFuncParams = headerFilterFuncParams;
	}

	public boolean isFilterable() {
		return filterable;
	}

	public void setFilterable(boolean filterable) {
		this.filterable = filterable;
	}
*/
	public FormatterParam getFormatterParams() {
		return formatterParams;
	}

	public void setFormatterParams(FormatterParam formatterParams) {
		this.formatterParams = formatterParams;
	}

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
