package com.servpro.restcontroller;

import java.io.IOException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.servpro.entity.OtpUtil;
import com.servpro.model.Message;
import com.servpro.repository.OtpRepository;

@RestController
@RequestMapping("/api")
public class OtpRestController {
	
	@Autowired
	OtpRepository otpRepository;
	
	
	@PostMapping("/{mobilenumber}")
	public ResponseEntity<Object> sendOtp(@PathVariable("mobilenumber") String mobilenumber){
	if(mobilenumber !=null && (mobilenumber.trim().length()) >= 13)	{
		RestTemplate restTemp = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> entity = new HttpEntity<>(headers);
		
		Random random = new Random();
		String otp = String.format("%4d", random.nextInt(10000));
		System.out.println(otp);
		
		ResponseEntity<String> response = restTemp.exchange("https://japi.instaalerts.zone/httpapi/QueryStringReceiver?ver=1.0&key=2NQAf25Mmg9JYFYbKYYy4w==&encrpt=0&dest="+mobilenumber+"&send=PRVSYS&text=your otp for servepro is"+otp,HttpMethod.POST,entity, String.class);
		
		String body = response.getBody();
		System.out.println(body);
/*		ObjectMapper mapper = new ObjectMapper();
		MessageDetails readValue = null;*/
	
		if(response.getStatusCodeValue() == 200) {
			System.out.println(response.getStatusCodeValue());
			OtpUtil otpUtilDetails = otpRepository.findByMobilenumber(mobilenumber);
			if(otpUtilDetails !=null) {
				otpUtilDetails.setOtp(otp);
				otpUtilDetails.setExpiryTime(System.currentTimeMillis()+ 600000);
				otpRepository.save(otpUtilDetails);
			}
			else {
				OtpUtil otpSave = new OtpUtil();
				otpSave.setOtp(otp);
				otpSave.setMobilenumber(mobilenumber);
				otpSave.setExpiryTime(System.currentTimeMillis()+ 600000);
				otpRepository.save(otpSave);
			}
			
			return new ResponseEntity<Object>(new Message("your otp sent ","success"),HttpStatus.OK);
			
		}
		else
		{
			return new ResponseEntity<Object>(new Message("invalid credentials provided","error"),HttpStatus.NOT_FOUND);
		}
	}
	else {
		System.out.println(mobilenumber.trim().length());
		return new ResponseEntity<Object>(new Message("please provide valid mobilenumber","error"),HttpStatus.NOT_FOUND);
	  }
	}
	
	@GetMapping("/{mobilenumber}/{otp}")
	public ResponseEntity<Object> verityOtp(@PathVariable("mobilenumber") String mobilenumber,
			@PathVariable("otp") String otp) {

		if (mobilenumber != null && (mobilenumber.trim().length()) >= 10) {
			if (otp != null) {
				OtpUtil otputilDetails = otpRepository.getByMobilenumber(mobilenumber);
				if (otputilDetails != null) {
					if (otputilDetails.getExpiryTime() >= System.currentTimeMillis()) {
						if (otputilDetails.getOtp().equals(otp)) {

							return new ResponseEntity<Object>(new Message("otp verified successfully", "success"),
									HttpStatus.OK);
						} else {
							return new ResponseEntity<Object>(new Message("Invalid otp provided", "error"),
									HttpStatus.NOT_FOUND);
						}
					} else {
						return new ResponseEntity<Object>(new Message("Time Expired resend otp", "error"),
								HttpStatus.NOT_FOUND);
					}
				}
				else {
					 return new ResponseEntity<Object>(new Message("No details found with mobilenubmer "+mobilenumber+" pelase resend otp","error"),HttpStatus.NOT_FOUND);
				}
			}
			else {
			return new ResponseEntity<Object>(new Message("please provide otp", "error"), HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<Object>(new Message("please provide valid mobilenumber", "error"),
					HttpStatus.NOT_FOUND);
		}

	}
}
	
