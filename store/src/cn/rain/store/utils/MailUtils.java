package cn.rain.store.utils;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by Rain on 2016-12-12.
 * 用于java发邮件
 */
public class MailUtils {
    public static void main(String[] args) {
        //测试
        sendMail("xiaoming@rain.com", "11");
    }

    public  static  void findMail(String to, String code){
        Properties props = new Properties();
        props.setProperty("mail.host", "localhost");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zongxin@rain.com", "1991");
            }
        });
        //Message对象
        Message message = new MimeMessage(session);
        try {
            //设置发件人
            message.setFrom(new InternetAddress("zongxin@rain.com"));
            message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            message.setSubject("来自Rain商城的验证邮件");
            String url="http://localhost:80/RainStore/user?methodName=checkUserForPwd&code=" + code;
            message.setContent(url,"text/html;charset=UTF-8");

            Transport.send(message);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void sendMail(String to, String code) {
        //session对象:
        Properties props =new Properties();
        //邮件服务器在本机
        props.setProperty("mail.host", "localhost");
        //邮件服务器在外网需要下面两个配置代替，注意：163和qq等邮箱需要到对应官网注册授权码才可以发送信息
//        props.setProperty("mail.host", "smtp.163.com");
//        props.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zongxin@rain.com", "1991");
            }
        });
        //Message对象
        Message message = new MimeMessage(session);
        //设置发件人
        try {
            message.setFrom(new InternetAddress("zongxin@rain.com"));
            //设置收件人
            message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
            //设置主题
            message.setSubject("来自Rain商城的激活邮件");
            //设置内容
            String url="http://localhost:80/RainStore/user?methodName=active&code=" + code;
            message.setContent("<h1>点击链接去激账户</h1><h3><a href='" + url + "'>" + url + "</a></h3><br/><h1>如果不是本人操作请忽略此邮件</h1>",
                    "text/html;charset=UTF-8");
            //transport 对象
            Transport.send(message);

        } catch (Exception e) {
        e.printStackTrace();
        }
    }

}
