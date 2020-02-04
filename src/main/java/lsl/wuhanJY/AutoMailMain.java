package lsl.wuhanJY;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class AutoMailMain {

	private static String hostMail;
	private static String authorizationCode;
	private static String mailListString;
	private static String intervalTime;
	private static String[] mailLists;

	public static void main(String[] args) throws Exception {

		Properties prop = new Properties();
		FileInputStream fis = new FileInputStream("preference");
		prop.load(fis);

		hostMail = prop.getProperty("hostMail");
		authorizationCode = prop.getProperty("authorizationCode");
		mailListString = prop.getProperty("mailLists");
		intervalTime = prop.getProperty("intervalTime");

		mailLists = mailListString.split(";");

		System.out.println(hostMail);
		System.out.println(authorizationCode);
		for (String s : mailLists) {
			System.out.println(s);
		}
		System.out.println(intervalTime);

		ScheduledExecutorService schedule = Executors.newScheduledThreadPool(1);
		schedule.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("Timers");
				SendMailUtil.sendMail(hostMail, authorizationCode, mailLists);
			}
		}, 0, StringToInt(intervalTime), TimeUnit.HOURS);

	}

	private static int StringToInt(String valueString) {
		return Integer.valueOf(valueString);
	}
}
