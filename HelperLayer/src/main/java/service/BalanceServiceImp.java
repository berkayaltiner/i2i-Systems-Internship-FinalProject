package service;


import helper.DBConnectionService;
import helper.VoltDbImp;
import model.UsageToSendKafka;
import org.voltdb.client.ProcCallException;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;


public class BalanceServiceImp implements BalanceService {

	
	private CallableStatement callableStatement = null;
	private DBConnectionService connectionService;
	
	public BalanceServiceImp(DBConnectionService connectionService)
	{
		this.connectionService = connectionService;
	}
	
	@Override
	public boolean updateBalanceOracle(UsageToSendKafka balance) throws SQLException {
		Connection connection = connectionService.connectionDatabase();
		boolean result = false;

		try {
			callableStatement = connection.prepareCall("{call update_balance(?,?,?,?,?)}");
			callableStatement.setString(1, balance.getMsisdn());
			callableStatement.setInt(2, balance.getPartitionKey());
			callableStatement.setInt(3, balance.getPackageId());
			callableStatement.setInt(4, balance.getUsedAmount());
			callableStatement.setDate(5, new Date(new java.util.Date(balance.getLastUpdate()).getTime()));
			result = callableStatement.execute();
		} catch (Exception e) {
			System.out.println(e);
		}
		finally {
			callableStatement.close();
			connection.close();
		}
		
		if (result) return true;
		else return false;
		
		
	}


	@Override
	public boolean insertBalanceOracle(UsageToSendKafka balance,int userID) throws SQLException {
		Connection connection = connectionService.connectionDatabase();
        boolean result = false;
		
		try {
			System.out.println("DEĞERLER : " + balance.getMsisdn() + " " + userID);
			callableStatement = connection.prepareCall("{call init_balance(?,?,?,?,?,?,?,?,?)}");
			callableStatement.setInt(1, balance.getBalKey());
			callableStatement.setString(2, balance.getMsisdn());
			callableStatement.setInt(3, balance.getPartitionKey());
			callableStatement.setInt(4, balance.getPackageId());
			callableStatement.setInt(5, balance.getUsedAmount());
			callableStatement.setDate(6, new Date(balance.getStartDate().getTime()));
			callableStatement.setDate(7, new Date(balance.getEndDate().getTime()));
			callableStatement.setDate(8, new Date(balance.getStartDate().getTime()));
			callableStatement.setInt(9, userID);
			
			result = callableStatement.execute();


		} catch (Exception e) {

			System.out.println(e);
			
		}finally {
			callableStatement.close();
			connection.close();
		}
		
		if (result) return true;
		else return false;

	}

	public void insertBalanceVoltDB(String procedureName, int balancePartitionKey, String balanceMsisdn, int balanceKey, int balancePackageId, int usedAmount,
			long startTime, long endTime) throws IOException, ProcCallException {

		VoltDbImp.connectionVoltDB().callProcedure(procedureName,balancePartitionKey,balanceMsisdn,Long.valueOf(balanceKey),balancePackageId,usedAmount,startTime,endTime);
	}

	

	

}
