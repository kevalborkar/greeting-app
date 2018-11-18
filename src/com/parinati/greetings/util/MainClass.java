package com.parinati.greetings.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.parinati.greetings.entity.Message;
import com.parinati.greetings.entity.Receipients;
import com.parinati.greetings.service.SaveDraft;

public class MainClass {

	public static void main(String[] args) {
		Message msgObj = new Message();
		HashMap<Integer, Receipients> recptMap = new HashMap<Integer, Receipients>();
		List<Receipients> recptObj = new ArrayList<Receipients>();
		SaveDraft saveDraftObj = new SaveDraft();
		xlsReader read = new xlsReader(
				"F:\\Jspiders\\eclipse_progs\\JavaMail\\tester\\resources\\GreetingsApplication.xlsx");
		msgObj.setMessage(read.getData(0, 0, 1));
		msgObj.setSubject(read.getData(0, 1, 1));

		Sheet sheet = read.wb.getSheetAt(0);
		int rowCount = read.getRowCount("Sheet1");
		for (int rownum = 6; rownum < rowCount + 1; rownum++) {
			Row row = sheet.getRow(rownum);
			int colnum = 0;
			while (colnum < row.getLastCellNum()) {

				// TODO:

				// recptObj.read.getData(0, rownum, colnum);
				colnum++;
			}
			System.out.print(row.getCell(colnum).getStringCellValue());

		}

		try {
			Session session = saveDraftObj.connect("xyz@gmail.com", "password");
			MimeMessage draftMessage = new MimeMessage(session);

			draftMessage.setSubject(msgObj.getSubject());
			draftMessage.setContent("Dear <br / >" + msgObj.getMessage(), "text/html");

			saveDraftObj.saveDraftMessage(draftMessage, session);
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
