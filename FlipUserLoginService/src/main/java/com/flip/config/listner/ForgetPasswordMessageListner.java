package com.flip.config.listner;



import java.util.Calendar;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.flip.entity.OtpEntity;
import com.flip.event.ForgetPasswordResponseEvent;
import com.flip.service.OtpService;

@Component
public class ForgetPasswordMessageListner {

	@Autowired
	private OtpService otpService;
	
    @KafkaListener(topics = "${forgetPassword.response.topic.name}", containerFactory = "forgetPasswordResponseKafkaListenerContainerFactory")
    public void greetingListener(ForgetPasswordResponseEvent forgetPasswordEvent) {
        System.out.println("Recieved ForgetPasswordResponse  message: " + forgetPasswordEvent.getOtp() + " Validity : "+forgetPasswordEvent.getValidityTime());
    
        OtpEntity otpEntity = new OtpEntity();
        
        BeanUtils.copyProperties(forgetPasswordEvent,otpEntity);
       
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MILLISECOND, forgetPasswordEvent.getValidityTime().intValue());
        
        otpEntity.setValidityOtpTime(calendar.getTime());
        otpService.saveOtp(otpEntity);
    }

}
