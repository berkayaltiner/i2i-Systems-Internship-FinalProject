package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class OracleDatabaseImp implements DBConnectionService {

	Connection connection = null;
	
	@Override
	public Connection connectionDatabase() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			this.connection = DriverManager.getConnection("jdbc:oracle:thin:@35.246.237.190:49161:xe","INTERNS","INTERNS");
			return connection;
		} catch (SQLException | ClassNotFoundException e) {
			
			
		}
		return connection;
	}

}
