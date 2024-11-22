package idmRestBackEnd.database.dao;

import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtField;

public interface DbRprtColumnDAO {
	public Report getByReportName(String inRprtColumn);
	public Report getByReportId(String inRprtId);
	public boolean create(RprtField inField);
}
