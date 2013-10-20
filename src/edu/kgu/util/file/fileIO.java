package edu.kgu.util.file;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.kgu.log.LogLogger;

public class fileIO {
	public static boolean fileWrite(String file, ArrayList<String> context,boolean append) {
		boolean rtn = false;
		
		try {
			FileWriter fw = new FileWriter(file,append);
			
			BufferedWriter bw = new BufferedWriter(fw); 

			bw.flush(); 
			
			for(int i = 0; i < context.size(); i++) {
				bw.write(context.get(i));
				bw.newLine(); 
			}
			bw.flush();
			bw.close();
			fw.close();
			
			rtn = true;
		} catch (IOException e) {
			LogLogger.error(e);
		}
		
		return rtn;	
	}
	
	public static ArrayList<String> fileRead(String file) {
		ArrayList<String> rtn = new ArrayList<String>();
		
		try {
			FileReader fr;
			fr = new FileReader(new File(file));
			BufferedReader br = new BufferedReader(fr);
			String line = "";
			while((line = br.readLine()) != null) {
				rtn.add(line);
			}
			br.close();
			fr.close();
		} catch (IOException e) {
			LogLogger.error(e);
		}
		
		
		return rtn;
	}
}
