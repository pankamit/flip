package com.flip.config.listner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flip.config.producer.ForgetPasswordProducer;
import com.flip.event.ForgetPasswordEvent;
import com.flip.event.ForgetPasswordResponseEvent;
import com.flip.service.EmailService;
import com.flip.service.OtpGenerationService;

@Component
public class ForgetPasswordMessageListner {

	@Autowired
	private ForgetPasswordProducer forgetPasswordProducer;
	
	@Autowired
	private ForgetPasswordResponseEvent forgetPasswordResponseEvent;
	
	@Value(value = "${opt.valid.time}")
	private String validityTime; 
	
	@Value(value = "${opt.subject}")
	private String subject; 
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private OtpGenerationService otpGenerationService;
	
    @KafkaListener(topics = "${forgetPassword.request.topic.name}", containerFactory = "forgetPasswordRequestKafkaListenerContainerFactory")
    public void greetingListener(ForgetPasswordEvent forgetPasswordEvent) {
        System.out.println("Recieved ExamCenter message: " + forgetPasswordEvent.getEmail());
        String otp = otpGenerationService.generateOTP();
        emailService.sendEmail(forgetPasswordEvent.getEmail(), subject, otp,validityTime);
        
        sendForgetPasswordResponse(forgetPasswordEvent.getEmail(),otp);
    }
    
    private void sendForgetPasswordResponse(String email,String otp){
    	forgetPasswordResponseEvent.setEmail(email);
		forgetPasswordResponseEvent.setOtp(otp);
		forgetPasswordResponseEvent.setValidityTime(Integer.valueOf(validityTime));
		
		forgetPasswordProducer.sendMessage(forgetPasswordResponseEvent);
    }

}
