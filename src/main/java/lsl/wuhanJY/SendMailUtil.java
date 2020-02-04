package lsl.wuhanJY;

import jodd.mail.Email;
import jodd.mail.MailServer;
import jodd.mail.SendMailSession;
import jodd.mail.SmtpServer;

public class SendMailUtil {

	public static boolean sendMail(String hostMailAddress, String passwd, String... receiveMailAddress) {

		String contents = GetData.getAllData();
		Email email = Email.create().from("Elliot Lee", hostMailAddress).to(receiveMailAddress).subject("武汉加油")
				.textMessage(contents).priority(1);

		SmtpServer smtpServer = MailServer.create().ssl(true).host("smtp.qq.com").port(465)
				.auth(hostMailAddress, passwd).buildSmtpMailServer();

		SendMailSession session = smtpServer.createSession();
		session.open();
		session.sendMail(email);
		session.close();

		System.out.println("Send Done");
		return true;

	}
}
