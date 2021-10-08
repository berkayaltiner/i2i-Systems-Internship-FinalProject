package service;


import helper.DBConnectionService;
import model.User;

import java.sql.*;
import java.util.ArrayList;

public class UserServiceImp implements UserService {

	 private CallableStatement callableStatement = null;
	 private ResultSet resultSet = null;
	 private DBConnectionService connectionService=null;
	 
	 public UserServiceImp(DBConnectionService connectionService)
	 {
		this.connectionService = connectionService;
	 }
	
	
	
	@Override
	public ArrayList<User> getAll() throws SQLException {
		ArrayList<User> list = new ArrayList<User>();
		User user;
		Connection connection = connectionService.connectionDatabase();
		
		try {
			callableStatement = connection.prepareCall("{call getAllUsers()}");
			boolean hasResult = callableStatement.execute();
			if (hasResult) {
				resultSet = callableStatement.getResultSet();
				while(resultSet.next())
				{
					user = new User();
					user.setUserId(resultSet.getInt("USER_ID"));
					user.setMsisdn(resultSet.getString("MSISDN"));
					user.setName(resultSet.getString("USER_NAME"));
					user.setLastName(resultSet.getString("USER_SURNAME"));
					user.setEmail(resultSet.getString("USER_MAIL"));
					user.setPassword(resultSet.getString("PASSWORD"));
					user.setPassChangeCode(resultSet.getInt("PASSCHANGECODE"));
					list.add(user);
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally 
		{
			callableStatement.close();
			connection.close();
		}

		return list;
		
	}
	
	
    @Override
    public boolean addUser(User user) throws SQLException {
    	Connection connection = connectionService.connectionDatabase();
    	boolean result = false;
    	try {
    		
			callableStatement = connection.prepareCall("{call insert_user(?,?,?,?,?,?,?)}");

			callableStatement.setString(1, user.getMsisdn());
			callableStatement.setString(2, user.getName());
			callableStatement.setString(3, user.getLastName());
			callableStatement.setString(4, user.getEmail());
			callableStatement.setString(5, user.getPassword());
			callableStatement.setInt(6, user.getPassChangeCode());
			callableStatement.setInt(7, user.getUserId());
			result = callableStatement.execute();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		}finally {
			
			callableStatement.close();
			connection.close();
			
		}
    	
    	
    	if (result) return true;
    	else return false;
    	
    	
    }

	@Override
	public int userId() throws SQLException {
		Connection connection = connectionService.connectionDatabase();

		int userId = 0;
		try {
			callableStatement = connection.prepareCall("{ ? = call GET_NEXT_USER_ID()}");
			callableStatement.registerOutParameter(1, Types.INTEGER);
			callableStatement.execute();

			userId = callableStatement.getInt(1);
		}catch (Exception e)
		{
			System.out.println(e);
		}finally {
			callableStatement.close();
			connection.close();
		}

		return userId;
	}
}
