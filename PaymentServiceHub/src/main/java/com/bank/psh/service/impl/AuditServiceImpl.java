package com.bank.psh.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.bank.psh.service.AuditService;

@Component
public class AuditServiceImpl implements AuditService {

	@Override
	public void logAudit(String message) {
		File file = new File("BankAudit.txt");

		try {

			if (!file.exists()){
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(message);
			bw.newLine();
			bw.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
