package xju.isg.processor;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Vector;

import xju.isg.io.MyFileReader_Writer;

public class CorpusProcessor {

	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{
		String encoding = "UTF-8";
		String words1Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\word1_origen_PN.txt";
		String savePath = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\word1_origen_PN-1.txt";
		
		PrintWriter writer = setWriter(savePath, encoding);
		
		Vector<String> vec = new Vector<String>();
		vec = MyFileReader_Writer.readFile(words1Path, encoding);
		
		ArrayList<String> al = new ArrayList<String>();
		int i = 0;
		for(String str : vec)
		{
			if(!al.contains(str))
			{
				++i;
				al.add(str);
				writer.println(str);
				System.out.println(str);
			}
		}
		System.out.println(vec.size()+"\n"+ i + "\n"+ String.valueOf(vec.size()-i));
		
		writer.close();
		System.out.println("file write complete.");
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
