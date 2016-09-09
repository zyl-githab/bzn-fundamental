
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.bzn.fundamental.email.utils.MailUtil;

public class MailSendTest {

	public static void main(String[] args) {
		String subject = "中国共产党章程";
		String content = "Hi,All:<br/>&nbsp;&nbsp;&nbsp;&nbsp;在干什么啊？";
		String toAddress = "fengliii123@126.com";
		String attachFileName = "ss";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("D:\\emdata\\document\\药库20151230(1).xlsx");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 不带附件
		MailUtil.sendEmail(subject, content, toAddress);

		// 带附件
		MailUtil.sendEmail(subject, content, toAddress, fis, attachFileName);
	}
}
