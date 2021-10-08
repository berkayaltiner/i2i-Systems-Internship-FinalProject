package service;


import model.UsageToSendKafka;

import java.sql.SQLException;

public interface BalanceService {

	public boolean updateBalanceOracle(UsageToSendKafka balance) throws SQLException;
	public boolean insertBalanceOracle(UsageToSendKafka balance,int userID) throws SQLException;
}
