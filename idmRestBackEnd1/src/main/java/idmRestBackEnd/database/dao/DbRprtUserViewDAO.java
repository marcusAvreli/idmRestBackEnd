package idmRestBackEnd.database.dao;

import idmRestBackEnd.entity.Report;
import idmRestBackEnd.entity.RprtUserView;

public interface DbRprtUserViewDAO {
	public Report getAll();
	public boolean createUpdate(RprtUserView inRprtUserView);
}
