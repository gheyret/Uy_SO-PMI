package xju.isg.so_pmi;

import java.io.PrintWriter;
import java.util.Vector;
import xju.isg.io.MyFileReader_Writer;

public class My_SO_PMI {

	/**
	 * @param args
	 * @author Markos
	 */
	public static void main(String[] args) 
	{
		//************************************** Confing ****************************************//
		String encoding = "UTF-8";
		
		String sentsPath = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\sents.txt";	// open
		String words1Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\word1_origen_PN.txt";	// open
		
		String words2_pos_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\pos_word2.txt";	// open
		String words2_neg_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Resource\\neg_word2.txt";	// open
		
		String df_words1Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\DF_Words1.txt";		// create
		
		String df_words2_pos_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\DF_pos_Words2.txt"; 	// create
		String df_words2_neg_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\DF_neg_Words2.txt";	// create
		
		String df_words1words2_pos_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\DF_pos_Words1Words2.txt";	// create
		String df_words1words2_neg_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\DF_neg_Words1Words2.txt";	// create
		
		String pmi_pos_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\PMI_pos.txt";	// create
		String pmi_neg_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\PMI_neg.txt";	// create
		
		String sum_PMI_pos_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\SUM_PMI_pos.txt";	// create
		String sum_PMI_neg_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\SUM_PMI_neg.txt";	// create
		
		String so_pmi_Path = "D:\\MyEclipseWorkSpace\\SO_PMI\\Result\\SO_PMI.txt";
		//************************************* initialize **************************************//
		Vector<String> sents = new Vector<String>();
		Vector<String> words1 = new Vector<String>();
		Vector<String> words2_pos = new Vector<String>();
		Vector<String> words2_neg = new Vector<String>();
		
		sents = MyFileReader_Writer.readFile(sentsPath, encoding);
		words1 = MyFileReader_Writer.readFile(words1Path, encoding);
		
		words2_pos = MyFileReader_Writer.readFile(words2_pos_Path, encoding);
		words2_neg = MyFileReader_Writer.readFile(words2_neg_Path, encoding);
		//************************************ Main process *************************************//
		DF_Words1(sents, words1, df_words1Path, encoding);
		
		DF_Words2(sents, words2_pos, df_words2_pos_Path, encoding);
		DF_Words2(sents, words2_neg, df_words2_neg_Path, encoding);
		
		DF_Words1Words2(sents, df_words1Path, df_words2_pos_Path, df_words1words2_pos_Path, encoding);
		DF_Words1Words2(sents, df_words1Path, df_words2_neg_Path, df_words1words2_neg_Path, encoding);
		
		PMI(sentsPath, df_words1words2_pos_Path, pmi_pos_Path , encoding);
		PMI(sentsPath, df_words1words2_neg_Path, pmi_neg_Path , encoding);
		
		SUM_PMI(pmi_pos_Path, sum_PMI_pos_Path, encoding);
		SUM_PMI(pmi_neg_Path, sum_PMI_neg_Path, encoding);
		
		SO_PMI(sum_PMI_pos_Path, sum_PMI_neg_Path, so_pmi_Path, encoding);
		
		SOP("done#############################################################################################\n");
	}
	
	private static void SO_PMI(String sum_PMI_pos_Path, String sum_PMI_neg_Path, String so_pmi_Path, String encoding) 
	{
		Vector<String> pos_pmis = new Vector<String>();
		Vector<String> neg_pmis = new Vector<String>();
		pos_pmis = MyFileReader_Writer.readFile(sum_PMI_pos_Path, encoding);
		neg_pmis = MyFileReader_Writer.readFile(sum_PMI_neg_Path, encoding);
		
		Vector<String> posWords = new Vector<String>();
		Vector<String> negWords = new Vector<String>();
		Vector<String> pos_neg_Words = new Vector<String>();
		Vector<String> vec_SO_PMI = new Vector<String>();
		
		for(int i = 0; i < pos_pmis.size(); i++)
		{
			int posID = Integer.valueOf(pos_pmis.get(i).split(":")[0].split("-")[0]);
			boolean bool = true;
			for(int j = 0; j < neg_pmis.size(); j++)
			{
				int negID = Integer.valueOf(neg_pmis.get(j).split(":")[0].split("-")[0]);
				if(posID == negID)
				{
					pos_neg_Words.add(pos_pmis.get(i)+"###"+neg_pmis.get(j));
					bool = false;
					break;
				}
			}
			if(bool)
				posWords.add(pos_pmis.get(i));
		}
		
		for(int i = 0; i < neg_pmis.size(); i++)
		{
			int negID = Integer.valueOf(neg_pmis.get(i).split(":")[0].split("-")[0]);
			boolean bool = true;
			for(int j = 0; j < pos_pmis.size(); j++)
			{
				int posID = Integer.valueOf(pos_pmis.get(j).split(":")[0].split("-")[0]);
				if(negID == posID)
				{
					bool = false;
					break;
				}
			}
			if(bool)
				negWords.add(neg_pmis.get(i));
		}
		
		vec_SO_PMI.add("=================== 正面和负面 同时存在的词  ===================");
		for(int i = 0; i < pos_neg_Words.size(); i++)
		{
			String pos_id_word = pos_neg_Words.get(i).split("###")[0].split(":")[0];
			String pos_pmi = pos_neg_Words.get(i).split("###")[0].split(":")[2];
			
			String neg_id_word = pos_neg_Words.get(i).split("###")[1].split(":")[0];
			String neg_pmi = pos_neg_Words.get(i).split("###")[1].split(":")[2];
			
			double os_pmi = Double.valueOf(pos_pmi) - Double.valueOf(neg_pmi);
			String position = "";
			if(os_pmi >= 1)
				position = "正面";
			else if(os_pmi > -1 && os_pmi < 1)
				position = "中立";
			else if(os_pmi <= -1)
				position = "负面";
			else
				position = "有错误";
			
			vec_SO_PMI.add(pos_id_word + "=" + position + "=" + os_pmi);
		}
		
		vec_SO_PMI.add("======================== 正面词  =======================");
		for(int i = 0; i < posWords.size(); i++)
		{
			String pos_id_word = posWords.get(i).split(":")[0];
			double so_pmi = Double.valueOf(posWords.get(i).split(":")[2]);
			if(so_pmi > 0)
				vec_SO_PMI.add(pos_id_word + "=正面=" + so_pmi);
			else
				vec_SO_PMI.add(pos_id_word + "=错误=" + so_pmi);
				
		}
		
		vec_SO_PMI.add("======================== 负面词  =======================");
		for(int i = 0; i < negWords.size(); i++)
		{
			String neg_id_word = negWords.get(i).split(":")[0];
			double so_pmi = Double.valueOf(negWords.get(i).split(":")[2]);
			if((-1)*so_pmi < 0)
				vec_SO_PMI.add(neg_id_word + "=负面=" + (-1)*so_pmi);
			else
				vec_SO_PMI.add(neg_id_word + "=错误=" + (-1)*so_pmi);
		}
		
		PrintWriter writer = MyFileReader_Writer.setWriter(so_pmi_Path, encoding);
		for(String so_pmi : vec_SO_PMI)
			writer.println(so_pmi);
		writer.close();
	}
	
	public static void SUM_PMI(String pmi_path, String sum_PMI_Path, String encoding)
	{
		PrintWriter writer = MyFileReader_Writer.setWriter(sum_PMI_Path, encoding);
		
		Vector<String> pmi = new Vector<String>();
		pmi = MyFileReader_Writer.readFile(pmi_path, encoding);
				
		int W1ID = Integer.valueOf(pmi.get(0).split(":")[0]);
		String W1 = pmi.get(0).split(":")[2];
		String W2ID = pmi.get(0).split(":")[3];
		String W2 = pmi.get(0).split(":")[5];
		double doublePMI = Double.valueOf(pmi.get(0).split(":")[6]);
		
		String line = W1ID + "-" + W1 + ":" + W2ID + "-" + W2;
		
		for(int i = 1; i < pmi.size(); i++)
		{
			String[] sub = pmi.get(i).split(":");
			int w1_id = Integer.valueOf(sub[0]);
			String w1_p = sub[1];
			String w1 = sub[2];
			String w2_id = sub[3];
			String w2_p = sub[4];
			String w2 = sub[5];
			String w1_sub_pmi = sub[6];
			String w1_fq = sub[7];
			String w1_docID = sub[8];
			
			if(W1ID == w1_id)
			{
				line += "," + w2_id + "-" + w2;
				doublePMI += Double.valueOf(w1_sub_pmi);
				if(i == (pmi.size()-1))
				{
					line += ":" + String.valueOf(doublePMI);
					writer.println(line);
				}
			}
			else
			{
				line += ":" + String.valueOf(doublePMI);
				writer.println(line);
				SOP(W1ID+"\n");
				W1ID = w1_id;
				SOP(W1ID+"\n");
				W1 = w1;
				W2ID = w2_id;
				W2 = w2;
				doublePMI = Double.valueOf(w1_sub_pmi);
				line = W1ID + "-" + W1 + ":" + W2ID + "-" + W2;
				if(i == (pmi.size()-1))
				{
					line += ":" + String.valueOf(doublePMI);
					writer.println(line);
				}
			}

		}
		writer.close();
	}
	
	public static void PMI(String sentsPaht, String df_Words1words2Path, String pmiPath, String encoding)
	{
		Vector<String> sents = new Vector<String>();
		sents = MyFileReader_Writer.readFile(sentsPaht, encoding);
		int N = sents.size();
		
		Vector<String> df_word1word2 = new Vector<String>();
		df_word1word2 = MyFileReader_Writer.readFile(df_Words1words2Path, encoding);
		
		PrintWriter writer = MyFileReader_Writer.setWriter(pmiPath, encoding);
		for(int i = 0; i < df_word1word2.size(); i++)
		{
			String[] line = df_word1word2.get(i).split(":");
			String w1_id = line[0];
			String w1_fq = line[1];
			String w1_word = line[2];
			String w2_id = line[3];
			String w2_fq = line[4];
			String w2_word = line[5];
			String w1w2_fq = line[6];
			String w1w2_doc = line[7];
			
			double p_w1 = Double.valueOf(w1_fq)/Double.valueOf(N);
			double p_w2 = Double.valueOf(w2_fq)/Double.valueOf(N);
			double p_w1w2 = Double.valueOf(w1w2_fq)/Double.valueOf(N);
			
			double pmi = log2(p_w1w2 / (p_w1 * p_w2));
			SOP(pmi+"\n");
			
			writer.println(w1_id + ":" + p_w1 + ":" + w1_word + 
					":" + w2_id + ":" + p_w2 + ":" + w2_word + 
					":" + pmi + 
					":" + w1w2_fq + ":" + w1w2_doc);
		}
		writer.close();
	}
	
	public static void DF_Words1Words2(Vector<String> sents, String df_words1Path, String df_words2Path, String df_words1words2Path, String encoding)
	{
		Vector<String> df_words1 = new Vector<String>();
		Vector<String> df_words2 = new Vector<String>();
		df_words1 = MyFileReader_Writer.readFile(df_words1Path, encoding);
		df_words2 = MyFileReader_Writer.readFile(df_words2Path, encoding);
		
		Vector<String> Pw1w2 = new Vector<String>();
		
		for (int i = 0; i < df_words1.size(); i++) 
		{
			String[] df_w1 = df_words1.get(i).split(":");
			String df_w1_id = df_w1[0];
			String df_w1_word = df_w1[1];
			String df_w1_fq = df_w1[2];
			String df_w1_doc = df_w1[3];
			String[] w1_doc = df_w1_doc.split(",");
			for (int j = 0; j < df_words2.size(); j++) 
			{
				String docs = "";
				String[] df_w2 = df_words2.get(j).split(":");
				String df_w2_id = df_w2[0];
				String df_w2_word = df_w2[1];
				String df_w2_fq = df_w2[2];
				String df_w2_doc = df_w2[3];
				String[] w2_doc = df_w2_doc.split(",");
				for (int x = 0; x < w1_doc.length; x++) 
				{
					for (int y = 0; y < w2_doc.length; y++) 
					{
						if (w1_doc[x].equals(w2_doc[y].trim())) 
						{
							if (docs == "")
								docs = w2_doc[y];
							else
								docs = docs + "," + w2_doc[y];
						}
					}
				}
				int fq = docs.split(",").length;
				if (fq > 0 && docs != "")
					Pw1w2.add(df_w1_id + ":" + df_w1_fq + ":" + df_w1_word + 
							":" + df_w2_id + ":" + df_w2_fq + ":" + df_w2_word + 
							":" + fq + ":" + docs);
			}
		}
		PrintWriter writer = MyFileReader_Writer.setWriter(df_words1words2Path, encoding);
		for(String str : Pw1w2)
		{
			writer.println(str);
		}
		writer.close();
	}
	
	public static void DF_Words2(Vector<String> sents, Vector<String> word2, String df_pos_Words2, String encoding) 
	{
		//****************************** 单独获取  words2 在所有句子中的文档频 df *******************************//
		Vector<String> word2SentsIndex = new Vector<String>();
		for(String str : word2)
			word2SentsIndex.add("");
		
		for(int i = 0; i < sents.size(); i++)
		{
			for(int j = 0; j < word2.size(); j++)
			{
				if(sents.get(i).contains(word2.get(j)))
				{
					if(word2SentsIndex.get(j)=="")
						word2SentsIndex.set(j, String.valueOf(i));
					else
						word2SentsIndex.set(j, word2SentsIndex.get(j) + "," + String.valueOf(i));
				}
			}
		}
		//****************************** 生成  P(words2) 的文本 *******************************//
		PrintWriter writer2 = MyFileReader_Writer.setWriter(df_pos_Words2, encoding);	
		for(int i = 0; i < word2SentsIndex.size(); i++)
		{
			if(word2SentsIndex.get(i) != "")
			{
				int x = word2SentsIndex.get(i).split(",").length;
				writer2.println(i + ":" + word2.get(i)+ ":" + x + ":" + word2SentsIndex.get(i));
//				SOP(i + ":" + word2.get(i)+ ":" + x + ":" + word2SentsIndex.get(i));
			}
		}
		writer2.close();
	}
	
	
	public static void DF_Words1(Vector<String> sents, Vector<String> word1, String df_Words1_Path, String encoding) 
	{
		//****************************** 单独获取 words1  在所有句子中的文档频 df *******************************//
		Vector<String> word1SentsIndex = new Vector<String>();
		for(String str : word1)
			word1SentsIndex.add("");
		
		for(int i = 0; i < sents.size(); i++)
		{
			for(int j = 0; j < word1.size(); j++)
			{
				if(sents.get(i).contains(word1.get(j)))
				{
					if(word1SentsIndex.get(j) == "")
						word1SentsIndex.set(j, String.valueOf(i));
					else
						word1SentsIndex.set(j, word1SentsIndex.get(j) + "," + String.valueOf(i)); 
				}
			}
		}
		//****************************** 生成df(words1) 的文本 *******************************//
		PrintWriter writer1 = MyFileReader_Writer.setWriter(df_Words1_Path, encoding);
		for(int i = 0; i < word1SentsIndex.size(); i++)
		{
			if(word1SentsIndex.get(i) != "")
			{
				int x = word1SentsIndex.get(i).split(",").length;
				writer1.println(i + ":" + word1.get(i)+ ":" + x + ":" + word1SentsIndex.get(i));
//				SOP(i + ":" + word1.get(i)+ ":" + x + ":" + word1SentsIndex.get(i));
			}
		}
		writer1.close();
	}	
	
	public static double log2(double num)
	{
		return (Math.log(num)/Math.log(2));
	}
	
	public static void SOP(Object obj)
	{
		System.out.print(obj);
	}
	
}

