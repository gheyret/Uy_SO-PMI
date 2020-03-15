package xju.isg.processor;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import xju.isg.io.MyFileReader_Writer;

public class test {

	/** 
	 * @param args 
	 * @throws IOException 
	 */ 
	public static void main(String[] args) throws IOException 
	{
		HashMap<String, Integer> hm = new HashMap<String, Integer>(); 
		Vector<String> wordFq = new Vector<String>(); 
		wordFq = MyFileReader_Writer.readFile("词干提取后.txt", "utf-8"); 
		PrintWriter pw = MyFileReader_Writer.setWriter("save.txt", "utf-8");  
		
		for(int i = 0; i < wordFq.size(); i++) 
		{
			System.out.println();
			String word = wordFq.get(i).split("\t")[0].trim(); 
			Integer fq = Integer.valueOf(wordFq.get(i).split("\t")[1].trim()); 
//			System.out.println("word="+word+"\t\t"+"fq="+fq);
			
			if(hm.containsKey(word))
			{
				fq = hm.get(word) + fq;
				hm.put(word, fq);
				System.err.println(word+":"+fq);
			}
			else
				hm.put(word, fq);
		}
		
		Map<String, Integer> newMap = sortMapByValues(hm);
		for(Entry<String,Integer> entry : newMap.entrySet()) {
            System.out.println(entry.getValue() + " - " + entry.getKey());
            pw.println(entry.getKey() + "\t\t" + entry.getValue());
		}
		pw.close();
	}
	
	private static Map<String, Integer> sortMapByValues(Map<String, Integer> aMap) {

        Set<Entry<String,Integer>> mapEntries = aMap.entrySet();
        // used linked list to sort, because insertion of elements in linked list is faster than an array list. 
        List<Entry<String,Integer>> aList = new LinkedList<Entry<String,Integer>>(mapEntries);
        // sorting the List
        Collections.sort(aList, new Comparator<Entry<String,Integer>>() {
            @Override
            public int compare(Entry<String, Integer> ele1,
                    Entry<String, Integer> ele2) {
                return ele2.getValue().compareTo(ele1.getValue());
            }
        });
        // Storing the list into Linked HashMap to preserve the order of insertion. 
        Map<String,Integer> aMap2 = new LinkedHashMap<String, Integer>();
        for(Entry<String,Integer> entry: aList) {
            aMap2.put(entry.getKey(), entry.getValue());
        }
        // printing values after soring of map
//        System.out.println("Value " + " - " + "Key");
//        for(Entry<String,Integer> entry : aMap2.entrySet()) {
//            System.out.println(entry.getValue() + " - " + entry.getKey());
//        }
        return aMap2;
    }
}



//*************************  筛选维吾尔字符  ************************//
//String filePath1 = "test/tb_Content.txt";
//String filePath2 = "test/tb_Commnet1.txt";
//String savePath1 = "test/cont.txt";
//String savePath2 = "test/comm.txt";
//
//String rule = "[^ئمنبۈغشزلكۆقجىخەگافدژسھپوڭۇيترېۋچ !؟،؛\\.]+";
//
//Vector<String> lines = new Vector<String>();
//lines = MyFileReader_Writer.readFile(filePath1, "UTF-8");
//
//PrintWriter pw = MyFileReader_Writer.setWriter(savePath1, "UTF-8");
//for(String str : lines)
//{
//	String content = str.replaceAll(rule, "").trim();
//	if(content.length() > 20)
//		pw.println(content);
//}
//pw.close();