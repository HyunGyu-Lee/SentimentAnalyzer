package tweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

import structures.Dictionary;
import structures.DictionaryWord;

public class DictionaryProcessor {

	Dictionary dict = new Dictionary();
	String url = "c:\\\\resource\\\\dict.txt";
	
	public DictionaryProcessor()
	{
		//createBacthFile();
		//handleBacthFile();
	}
	
	public Dictionary getDictionary()
	{
		return dict;
	}
	
	public void createBacthFile()
	{
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("c:\\resource\\tweet.bat")));
			bw.write("cd c:\\\\resource\\\\cnuma\\\\cnuma\\\\py_ver");
			bw.newLine();
			bw.write("ma.bat "+url);
			bw.newLine();
			bw.write("pause");
			bw.flush();
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void handleBacthFile()
	{
		try
		{		
			Process process = Runtime.getRuntime().exec("c:\\resource\\tweet.bat");
			
			BufferedReader consoleIn = new BufferedReader(new InputStreamReader(process.getInputStream()));

			String a;
			
			while(true)
			{
				a = consoleIn.readLine();
				if(a==null)break;
			}
		
			process.destroy();
			
		}catch(IOException e){System.err.println(e);}
	}
	
	public void process()
	{
		BufferedReader br = null;
		boolean ignoreFlag = false;
		int controlFlag = 0;	//
		try
		{
			br = new BufferedReader(new InputStreamReader(new FileInputStream("c:\\resource\\dict.txt.ma"),"UTF-8"));
		}
		catch (FileNotFoundException|UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			DictionaryWord dw = null;
			while(true)
			{
				String targetLine = br.readLine();
				if(targetLine==null)break;
				
				if(targetLine.contains("==========================="))
				{
					if(controlFlag!=0)
					{
						dict.addDictWord(dw);
					}
					dw = new DictionaryWord();
					ignoreFlag = false;
					controlFlag=1;
				}
				
				if(ignoreFlag)
				{
					continue;
				}
				
				if(targetLine.contains("\t/SW\t"))
				{
					ignoreFlag = true;
					continue;
				}
				
				if(targetLine.contains("INPUT:"))
				{
					dw.setInput(targetLine);
				}
				else if(targetLine.contains("TOKEN:")&&!targetLine.equals("TOKEN: \t"))
				{
				}
				else if(targetLine.contains("NUM_OUTPUT:"))
				{
					int cnt = Integer.parseInt(targetLine.split(":")[1].replaceAll(" ", ""));
					String eq = "test\t0";
					if(cnt==1)
					{
						eq = br.readLine();
					}
					else
					{
						for(int i=0;i<cnt;i++)
						{
							String selection = br.readLine();
							
							if(Integer.parseInt(eq.split("\t")[1])<Integer.parseInt(selection.split("\t")[1]))eq = selection;
						}
					}
					dw.addMorps(eq);
				}
	
			}
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	
	
	
}
