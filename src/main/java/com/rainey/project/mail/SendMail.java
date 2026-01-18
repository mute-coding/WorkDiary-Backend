package com.rainey.project.mail;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.rainey.project.excel.GenerateExcel;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SendMail {
	@Autowired
	private GenerateExcel generateExcel;
	@Autowired
	private JavaMailSender javaMailSender;
	
	public String now() {
	    LocalDateTime now = LocalDateTime.now();
	    return now.format(DateTimeFormatter.ofPattern("yyyy/MM/dd-HHmmss"));
	}
    public void sendMail() throws IOException, MessagingException {
    	
    	byte[] excelResource = generateExcel.workbookToByte();
    	MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    	MimeMessageHelper mimeMessageHelper = 
    			new MimeMessageHelper(mimeMessage,true,"UTF-8");
    	
    	mimeMessageHelper.setFrom("mutemute1127@gmail.com");
    	mimeMessageHelper.setTo("mutemute1127@gmail.com");
    	mimeMessageHelper.setSubject("工作日誌");
    	mimeMessageHelper.setText("本周工作日誌", true);

    	mimeMessageHelper.addAttachment(
                "工作日誌"+now()+".xlsx",
                new ByteArrayResource(excelResource)
        );

    	javaMailSender.send(mimeMessage);
    }
}
