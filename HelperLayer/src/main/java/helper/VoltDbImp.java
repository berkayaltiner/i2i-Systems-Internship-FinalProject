package helper;

import org.voltdb.client.Client;
import org.voltdb.client.ClientConfig;
import org.voltdb.client.ClientFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class VoltDbImp implements DBConnectionService{
	
    Connection connection = null;
	
	@Override
	public Connection connectionDatabase() {
		try {
			Class.forName("org.voltdb.jdbc.Driver");
			
			Properties props = new Properties();
			props.setProperty("user", "interncell");
			props.setProperty("password", "i2icom");
			this.connection = DriverManager.getConnection("jdbc:voltdb://34.107.82.147:21212",props);
			return connection;
		} catch (SQLException | ClassNotFoundException e) {

		}
		return connection;
	}

	public static Client connectionVoltDB() throws IOException {
		ClientConfig clientConfig = new ClientConfig("interncell", "i2icom");
		Client client;
		client = ClientFactory.createClient(clientConfig);
		client.createConnection("34.107.82.147",21212);

		return client;

	}
}
