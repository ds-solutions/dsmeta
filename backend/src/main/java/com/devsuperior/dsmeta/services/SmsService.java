package com.devsuperior.dsmeta.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.entities.Sale;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class SmsService {

	@Value("${twilio.sid}")
	private String twilioSid;
	@Value("${twilio.key}")
	private String twilioKey;
	@Value("${twilio.phone.from}")
	private String twilioPhoneFrom;
	@Value("${twilio.phone.to}")
	private String twilioPhoneTo;
	
	@Autowired
	private SaleService service;
	
	public void sendSms(Long id) {
		
		Sale sale = service.findById(id);
		
		String date = sale.getDate().getMonthValue()+"/"+sale.getDate().getYear();
		
		String amount = String.format("%.2f", sale.getAmount());
		
		String msg = sale.getSellerName()+ " foi destaque em "+ date +" por ter vendido o total de R$ " + amount;
		
		Twilio.init(twilioSid, twilioKey);
		
		PhoneNumber to = new PhoneNumber(twilioPhoneTo);
		PhoneNumber from = new PhoneNumber(twilioPhoneFrom);
		
		Message message = Message.creator(to, from, msg).create();
		
		System.out.println("Testando envio de sms " + message.getSid());
	}
	
}
