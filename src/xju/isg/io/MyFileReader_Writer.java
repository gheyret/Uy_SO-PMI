package xju.isg.io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Vector;

public class MyFileReader_Writer {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		Vector<String> list = new Vector<String>();
		String str = "C:\\Users\\Markos_XJU\\Desktop\\1000.txt";
		String encoding = "utf-8";
		list = readFile(str,encoding);
		
		System.out.println(list.size());
	}
	
	public static synchronized Vector<String> readFile(String inPath, String encoding) {
		Vector<String> list = new Vector<String>();
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream(inPath) , encoding));
			String line = null;
			while((line = reader.readLine()) != null)
			{
				list.add(line);
			}
			reader.close();
		}
		catch(IOException e){
			e.printStackTrace();
		}
		return list;
	}
	
	public static synchronized PrintWriter setWriter(String outfile, String encoding) {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outfile), encoding)); 
		} catch(Exception e) {
			System.err.println("new writer error!");
			e.printStackTrace();
		}
		return writer;
	}
	
}
