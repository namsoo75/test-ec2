package com.itad.qcheck;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private JavaMailSender mailSender;
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		//logger.info("Welcome home! The client locale is {}.", locale);
		
		//Date date = new Date();
		//DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		//String formattedDate = dateFormat.format(date);
		
		//model.addAttribute("serverTime", formattedDate );
		
		return "index";
	}
	
	@RequestMapping(value = "/question/mail", method = RequestMethod.POST)
	public ResponseEntity<Void> homeQuestionMail(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("questionText") String questionText) {
		
		ResponseEntity<Void> entity = null;
		
		logger.info(name);
		logger.info(email);
		logger.info(questionText);
		
    	try{
            
    		/*
		MimeMessage message = mailSender.createMimeMessage();
		MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");
		messageHelper.setTo("mycallboy@naver.com");
		messageHelper.setText(text);
		messageHelper.setFrom(email, "문의메일");
		messageHelper.setSubject("title");	// 메일제목은 생략이 가능하다
		*/
    		
    		SimpleMailMessage message = new SimpleMailMessage();

    	    message.setFrom("tech_itad@itadtechnology.kr");

    	    //message.setTo("mycallboy@naver.com");
    	    message.setTo("tech_itad@itadtechnology.kr");

    	    message.setSubject(name + "님의 문의 메일 입니다.");

    	    message.setText("이름:[" + name + "] 이메일:[" + email + "]" + " 내용:[" + questionText + "]");

    		
    	    mailSender.send(message);
		
		
    	    entity = new ResponseEntity<>( HttpStatus.OK);
		
		
    	} catch (Exception e){
    		e.printStackTrace();
    		
    		 entity = new ResponseEntity<>( HttpStatus.BAD_REQUEST );
    	}
 
        return entity;
	}
	
}
