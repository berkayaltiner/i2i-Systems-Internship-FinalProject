package service;



import helper.DBConnectionService;
import model.UserPackage;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PackageServiceImp implements PackageService {

	private CallableStatement callableStatement = null;
	private ResultSet resultSet = null;
	private DBConnectionService connectionService = null;

	public PackageServiceImp(DBConnectionService connectionService) {
		this.connectionService = connectionService;
	}

	@Override
	public ArrayList<UserPackage> getAll() throws SQLException {
		ArrayList<UserPackage> list = new ArrayList<UserPackage>();
		UserPackage userPackage;
		Connection connection = connectionService.connectionDatabase();

		try {
			callableStatement = connection.prepareCall("{call getAllPackages()}");
			boolean hasResult = callableStatement.execute();
			if (hasResult) {
				resultSet = callableStatement.getResultSet();
				while (resultSet.next()) {
					userPackage = new UserPackage();
					userPackage.setId(resultSet.getInt("PACKAGE_ID"));
					userPackage.setName(resultSet.getString("PACKAGE_NAME"));
					userPackage.setType(resultSet.getString("PACKAGE_TYPE").charAt(0));
					userPackage.setLimit(resultSet.getInt("PACKAGE_LIMIT"));
					userPackage.setBusinessZone(resultSet.getString("BUSINESS_ZONE"));
					userPackage.setVisibility(resultSet.getString("VISIBILITY").charAt(0));
					list.add(userPackage);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			connection.close();
			callableStatement.close();
		}

		return list;

	}

	@Override
	public boolean addPackage(UserPackage userPackage) throws SQLException {
		Connection connection = connectionService.connectionDatabase();
        boolean result = false;
		try {

			callableStatement = connection.prepareCall("{call insert_package(?,?,?,?,?)}");
			callableStatement.setInt(1, userPackage.getId());
			callableStatement.setString(2, userPackage.getName());
			callableStatement.setString(3, String.valueOf(userPackage.getType()));
			callableStatement.setInt(4, userPackage.getLimit());
			callableStatement.setString(5, userPackage.getBusinessZone());
			// visibility procedureden default 'Y' yap.
			result = callableStatement.execute();

		} catch (SQLException e) {

			e.printStackTrace();

		} finally {

			callableStatement.close();
			connection.close();

		}
		
		if (result) return true;
		else return false;

	}

}
