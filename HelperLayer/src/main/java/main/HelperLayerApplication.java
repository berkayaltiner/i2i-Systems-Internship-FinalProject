package main;


import helper.OracleDatabaseImp;
import connection.kafka.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.voltdb.client.ProcCallException;
import random.RandomDataGenerator;
import service.BalanceServiceImp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;

@SpringBootApplication
public class HelperLayerApplication {

	public static void main(String[] args) throws InterruptedException, SQLException {
		
		SpringApplication.run(HelperLayerApplication.class, args);
		
		try {
			RandomDataGenerator generator = new RandomDataGenerator();
			generator.startProcess();

			Consumer consumer = new Consumer(new BalanceServiceImp(new OracleDatabaseImp()));
			consumer.startConsume();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ProcCallException e) {
			e.printStackTrace();
		}


	}

}
