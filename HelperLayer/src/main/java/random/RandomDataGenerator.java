package random;

import hazelcast.Member;
import helper.OracleDatabaseImp;
import helper.SHA256Imp;
import model.UsageToSendKafka;
import model.User;
import org.voltdb.client.ProcCallException;
import service.BalanceServiceImp;
import service.UserServiceImp;

import java.io.IOException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

public class RandomDataGenerator {

	final static String lexicon = "ABCDEFGHIJKLMNOPQRSTUVWXYZ12345674890";

	final static Random rand = new Random();
	// consider using a Map<String,Boolean> to say whether the identifier is being
	// used or not
	final static Set<String> identifiers = new HashSet<String>();


	public RandomDataGenerator() {}

	public void startProcess() throws SQLException, UnknownHostException, IOException, ProcCallException, InterruptedException {
		ArrayList<Date> startList = new ArrayList<Date>();
		ArrayList<Date> endList = new ArrayList<Date>();
		ArrayList<User> userList = new ArrayList<User>();

		for (int i = 1;i < 10; i++) {

			Date startDate = new Date();
			long leftLimit = 84600000L * 7;
			long rightLimit = 84600000L * 30;
			long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
			Date endDate = new Date(startDate.getTime() + generatedLong);
			startList.add(startDate);
			endList.add(endDate);

			String packageTypes = "dvs";
            int userId=0;
			UserServiceImp userServiceImp = new UserServiceImp(new OracleDatabaseImp());
			userId = userServiceImp.userId();

			System.out.println("OLUŞTURULURKEN : " + userId);

			String password = randomIdentifier();

			User user = new User(userId,createRandomNumber(12), randomIdentifier(), randomIdentifier(),
					randomIdentifier() + "@gmail.com", SHA256Imp.getSHA256(password) ,(new Random().nextInt(500000)));
			userList.add(user);
			userServiceImp.addUser(user);

			//UserPackage userPackage = new UserPackage(i,randomIdentifier(), packageTypes.charAt(new Random().nextInt(3)),
			//		new Random().nextInt(4096), randomIdentifier(), 'Y');
			//PackageServiceImp packageServiceImp = new PackageServiceImp(new OracleDatabaseImp());
			//packageServiceImp.addPackage(userPackage);



		}

		
		insertBalanceRandom(startList, endList, userList);

	}
public void insertBalanceRandom(ArrayList<Date> startList,ArrayList<Date> endList,ArrayList<User> userList) throws SQLException, UnknownHostException, IOException, ProcCallException {

	for (int i = 1; i < 10; i++) {
		UsageToSendKafka balance = new UsageToSendKafka();
		balance.setBalKey(i);
		balance.setMsisdn(userList.get(i - 1).getMsisdn());
		balance.setPartitionKey(i);
		balance.setPackageId(new Random().nextInt(19));
		balance.setUsedAmount(getRandomNumberInRange(1, 50));
		balance.setStartDate(startList.get(i - 1));
		balance.setEndDate(endList.get(i - 1));
		balance.setLastUpdate(new Date().getTime());


		Member hazelcastMember = new Member();

		BalanceServiceImp balanceServiceImp = new BalanceServiceImp(new OracleDatabaseImp());

		System.out.println("DENEME : " + userList.get(i-1).getUserId());
		balanceServiceImp.insertBalanceOracle(balance, userList.get(i - 1).getUserId());

		balanceServiceImp.insertBalanceVoltDB("Insert", i, balance.getMsisdn(), balance.getBalKey(), balance.getPackageId(), balance.getUsedAmount(), startList.get(i - 1).getTime(), endList.get(i - 1).getTime());

		hazelcastMember.putHazelcast(i, balance.getMsisdn());
		System.out.println(balance.getMsisdn());
	}


}
	
	public String randomIdentifier() {
		StringBuilder builder = new StringBuilder();
		while (builder.toString().length() == 0) {
			int length = rand.nextInt(5) + 5;
			for (int i = 0; i < length; i++) {
				builder.append(lexicon.charAt(rand.nextInt(lexicon.length())));
			}
			if (identifiers.contains(builder.toString())) {
				builder = new StringBuilder();
			}
		}
		return builder.toString();
	}
	
	private static int getRandomNumberInRange(int min, int max) {

        if (min > max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


	public final static String createRandomNumber(long len) {
		if (len > 18)
			throw new IllegalStateException("To many digits");
		long tLen = (long) Math.pow(10, len - 1) * 9;

		long number = (long) (Math.random() * tLen) + (long) Math.pow(10, len - 1) * 1;

		String tVal = number + "";
		if (tVal.length() != len) {
			throw new IllegalStateException("The random number '" + tVal + "' is not '" + len + "' digits");
		}
		return tVal;
	}

}
