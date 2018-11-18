package com.parinati.greetings.service;

import java.util.Properties;

import javax.mail.Flags.Flag;
import javax.mail.Folder;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.MimeMessage;

public class SaveDraft {

	public Session connect(String from, String password) {
		Properties props = new Properties();
		props.setProperty("mail.store.protocol", "imaps");
		// get Session
		Session session = null;
		try {
			session = Session.getDefaultInstance(props, null);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return session;
	}

	public void saveDraftMessage(MimeMessage draftMessage, Session session) throws MessagingException {
		try {
			Store imapsStore = session.getStore("imaps");
			imapsStore.connect("imap.gmail.com", "xyz@gmail.com", "password");
			Folder draftsMailBoxFolder = imapsStore.getFolder("[Gmail]/Drafts");// [Gmail]/Drafts
			draftsMailBoxFolder.open(Folder.READ_WRITE);
			draftMessage.setFlag(Flag.DRAFT, true);
			MimeMessage draftMessages[] = { draftMessage };
			draftsMailBoxFolder.appendMessages(draftMessages);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}
