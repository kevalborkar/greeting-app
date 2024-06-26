package com.parinati.greetings.util;

import java.util.HashMap;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.parinati.greetings.entity.Message;
import com.parinati.greetings.entity.Receipients;
import com.parinati.greetings.service.SaveDraft;

public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] args) {
		logger.info("Mails Start");
		ReadProperties readProperties = new ReadProperties();
		HashMap<String, String> mailProperties = (HashMap<String, String>) readProperties.readProperties();

		Message msgObj = new Message();
		SaveDraft saveDraftObj = new SaveDraft();
		xlsReader read = new xlsReader(mailProperties.get("filePath"));
		msgObj.setMessage(read.getData(0, 0, 1));
		msgObj.setSubject(read.getData(0, 1, 1));
		msgObj.setSignature(read.getData(0, 2, 1));

		Receipients recptObj = null;
		try {
			Session session = saveDraftObj.connect(mailProperties.get("senderEmail"),
					mailProperties.get("senderPassword"));

			int rownum = 4;
			while (true) {
				try {
					recptObj = new Receipients();
					recptObj.setRecptName(read.getData(0, rownum, 1));
					recptObj.setRecptTitle(read.getData(0, rownum, 2));
					recptObj.setRecptEmail(read.getData(0, rownum, 3));
					MimeMessage draftMessage = new MimeMessage(session);
					String to = recptObj.getRecptEmail();
					InternetAddress[] address = InternetAddress.parse(to, true);
					draftMessage = new MimeMessage(session);
					draftMessage.setRecipients(javax.mail.Message.RecipientType.TO, address);
					draftMessage.setSubject(msgObj.getSubject());
					String fontFamily = mailProperties.get("fontFamily")!=null && mailProperties.get("fontFamily").length()>0?mailProperties.get("fontFamily"):"georgia";
					String fontSize = mailProperties.get("fontSize")!=null && mailProperties.get("fontSize").length()>0?mailProperties.get("fontSize"):"12";
					String message="";
					message+="<html>"
							+"<body >"
								+"<p style='"+"font-family:"+fontFamily+"; font-size:"+fontSize+"px;'>"
								+ "Dear " + recptObj.getRecptTitle() + " " + recptObj.getRecptName()+","
								+ "<br /><br />" + msgObj.getMessage() 
								+ "<br /><br />" + msgObj.getSignature()
								+ "</p>"
							+ "</body >"
							+ "</html>";
					draftMessage.setContent(message, "text/html");
					
					saveDraftObj.saveDraftMessage(draftMessage, session, mailProperties);
					logger.info("Mails created for : " + to);
					rownum++;

				} catch (NullPointerException e) {
					logger.info("Mails Saved");
					break;

				}

			}
			System.out.println("Messages Saved in Draft Folder..");
		} catch (MessagingException e) {
			e.printStackTrace();
			
		}
	}

}
