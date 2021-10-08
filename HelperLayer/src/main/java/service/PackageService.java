package service;


import model.UserPackage;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PackageService {
    
	public ArrayList<UserPackage> getAll() throws SQLException;
	public boolean addPackage(UserPackage userPackage) throws SQLException;
	
}
