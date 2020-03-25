package com.flip.service;

import org.springframework.stereotype.Service;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	 public static final String ACCOUNT_SID = "AC8337ed722ff6ef02b9c30d0c4ca887ec";
	 public static final String AUTH_TOKEN = "ff48d53a3b4e162f49b939a75da0b4fc";
	 public static final String TWILIO_NUMBER = "+19526496548";
	
	public String sendSms() {

		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new PhoneNumber("+919172162345"),
                new PhoneNumber(TWILIO_NUMBER),
                "Sample Twilio SMS using Java 2")
                .create();
        System.out.println(message.getSid() + " : " + message.getStatus());
        return message.getSid();
	}
	
	public void sendSmsAsynchronous() {

		Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
		ResourceSet<Message> messages = Message.reader().read();
        for (Message message : messages) {
            System.out.println(message.getSid() + " : " + message.getStatus());
        }
	}
	
	public String checkStatusOfSms(String sid) {

		Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
		ResourceSet<Message> messages = Message.reader().read();
        for (Message message : messages) {
        	if(message.getSid().equals(sid)) {
            System.out.println(message.getSid() + " : " + message.getStatus());
        	return message.getStatus().name();
        	}
        }
        return null;
	}
	
	
	
	}


