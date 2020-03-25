package com.flip.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.mail.BodyPart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private Environment env;
		
	public void sendEmail(String recipient,String subject,String otp,String validityTime) {

		Properties props = System.getProperties();
		props.put("mail.smtp.starttls.enable",env.getProperty("mail.smtp.starttls.enable"));
		props.put("mail.smtp.host",env.getProperty("mail.smtp.host"));
		props.put("mail.smtp.user",env.getProperty("mail.smtp.user"));
		props.put("mail.smtp.password",env.getProperty("mail.smtp.password"));
		props.put("mail.smtp.port",env.getProperty("mail.smtp.port"));
		props.put("mail.smtp.auth",env.getProperty("mail.smtp.auth"));
	
		Session session = Session.getDefaultInstance(props);
		MimeMessage message= new MimeMessage(session);
		try {
		message.setSubject(subject);
		message.setSender(new InternetAddress(props.getProperty("mail.smtp.user")));
		message.setFrom(props.getProperty("mail.smtp.user"));
		message.setRecipient(RecipientType.TO, new InternetAddress(recipient));
		
		MimeMultipart multipart = new MimeMultipart();
		BodyPart messageBodyPart = new MimeBodyPart();
		
		Map<String, String> input = new HashMap<String, String>();
	
		long validityMinutes = TimeUnit.MILLISECONDS.toMinutes(Long.parseLong(validityTime));
		System.out.println("minutes : "+validityMinutes);
		input.put("OTP", otp);
		input.put("ValidityTime", String.valueOf(validityMinutes));
		
		String htmlText = readEmailFromHtml("/Users/pankajkumarmittal/Desktop/New Start/Notification/Template/ChangePassEmail.html",input);
		System.out.println("htmlText : "+htmlText);
		
		messageBodyPart.setContent(htmlText, "text/html");
		
		
		multipart.addBodyPart(messageBodyPart);
//		messageBodyPart = new MimeBodyPart();
//		String filename = "/Users/pankajkumarmittal/Desktop/New Start/Notification/Template/text.txt";
//        DataSource source = new FileDataSource(filename);
//        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setFileName("file");
//        
//        
//	
//		multipart.addBodyPart(messageBodyPart);
//		
//		messageBodyPart = new MimeBodyPart();
//		messageBodyPart.setContent(htmlText, "text/html");
//		filename = "/Users/pankajkumarmittal/Desktop/New Start/Notification/Template/pic.jpeg";
//        source = new FileDataSource(filename);
//        messageBodyPart.setDataHandler(new DataHandler(source));
//        messageBodyPart.setFileName("Image");
//		
//        multipart.addBodyPart(messageBodyPart);
        
		message.setContent(multipart);
		
		Transport transport = session.getTransport("smtp"); 
		transport.connect(props.getProperty("mail.smtp.host"), props.getProperty("mail.smtp.user"), props.getProperty("mail.smtp.password"));
		transport.sendMessage(message, message.getAllRecipients());
		
//		forgetPasswordResponseEvent.setEmail(recipient);
//		forgetPasswordResponseEvent.setOtp(otp);
//		forgetPasswordResponseEvent.setValidityTime(Long.valueOf(validityTime));
//		
//		forgetPasswordProducer.sendMessage(forgetPasswordResponseEvent);
		
		
		}catch(Exception exception) {
			exception.printStackTrace();
		}
	}
	
	
	private String readEmailFromHtml(String fileName,Map<String, String> input) {
		
		String content = readContentFromFile(fileName);
		System.out.println("content : "+content);
		try {
		for(Entry<String,String> entry:input.entrySet()) {
			content = content.replace("{"+entry.getKey().trim()+"}", entry.getValue().trim());
		}
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return content;
		
	}
	
	
		private String readContentFromFile(String fileName) {
		
		    StringBuffer contents = new StringBuffer();
		    
		    try {
		      //use buffering, reading one line at a time
		      BufferedReader reader =  new BufferedReader(new FileReader(fileName));
		      try {
		        String line = null; 
		        while (( line = reader.readLine()) != null){
		          contents.append(line);
		          contents.append("\n");
		        }
		      }
		      finally {
		          reader.close();
		      }
		    }
		    catch (IOException ex){
		      ex.printStackTrace();
		    }
		    return contents.toString();
		}
		
		
	}


