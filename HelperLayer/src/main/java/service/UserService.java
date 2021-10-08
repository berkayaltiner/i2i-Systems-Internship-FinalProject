package service;

import model.User;

import java.sql.SQLException;
import java.util.ArrayList;

public interface UserService {
	
	public ArrayList<User> getAll () throws SQLException;
	public boolean addUser (User user) throws SQLException;
	public int userId () throws SQLException;

}
