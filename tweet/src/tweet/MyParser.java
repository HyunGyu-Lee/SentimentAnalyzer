package tweet;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import tweet.DataManager.Dictionary;

public class MyParser {

	String orgStr;
	String input;
	String tkn;
	BufferedWriter br;
	HashMap<String, Integer> list = new HashMap<String, Integer>();
	ArrayList<String> dictWord = new ArrayList<String>();
	ArrayList<Integer> dictValue = new ArrayList<Integer>(); 
	String dicFile;
	structures.Dictionary dict;
	Workbook wb;
	
	int cnt;

	public MyParser(BufferedWriter br, String dicFile)
	{
		this.br = br;
		this.dicFile = dicFile;
		setDictionary(this.dicFile); 	// 사전 목록 셋팅
	}
	
	public MyParser(BufferedWriter br, structures.Dictionary dict)
	{
		this.br = br;
		this.dict = dict;
	}
	
	/* 사전목록 불러오는 메소드 사전파일 확장자 xls, 메소드내 스트림 오픈에서 파일명 변경가능 */
	public void setDictionary(String dicFile)
	{
		if(dicFile.contains("txt"))
		{
			try
			{
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(dicFile),"UTF-8"));
				String str = null;
				while(true)
				{
					str = br.readLine();
					if(str==null)break;

					dictWord.add(str.split("\t")[0]);
					dictValue.add(Integer.parseInt(str.split("\t")[1]));
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			Workbook wb = null;
			FileInputStream in;
			try {
				in = new FileInputStream(dicFile);
				wb = WorkbookFactory.create(in);
				in.close();
			}
			catch (InvalidFormatException | IOException e) {
				System.err.println(e);
			}
			
			Sheet sheet = wb.getSheetAt(0);

			for(int i=1;i<=sheet.getLastRowNum();i++)
			{
				if(sheet.getRow(i).getCell(0).toString().equals(""))
				{
					continue;
				}
				dictWord.add(sheet.getRow(i).getCell(0).toString());
				Double d = sheet.getRow(i).getCell(2).getNumericCellValue(); 
				dictValue.add(d.intValue());
			}
			
			
		}

	}
	
	public ArrayList<String> getDictWord()
	{
		return dictWord;
	}
	public ArrayList<Integer> getDictValue()
	{
		return dictValue;
	}
	public void setString(String orgStr)
	{
		this.orgStr = orgStr;
	}
	
	public void parseToken(String tkn)
	{
		if(tkn.contains("!")||
		   tkn.contains("@")||tkn.contains("#")||
		   tkn.contains("$")||
		   tkn.contains("%")||tkn.contains("^")||
		   tkn.contains("*")||tkn.contains("&")||
		   tkn.contains("(")||tkn.contains(")")||
		   tkn.contains("-"))return;
		int plusCnt = 0;
		String[] firstSplit;
		String[] secondSplit;
		
		for(int i=0;i<tkn.length();i++)
		{
			if(tkn.charAt(i)=='+'&&tkn.charAt(i+1)!='/')
			{
				plusCnt++;
			}
		}
		
		if(plusCnt==0)
		{
			Pattern p = Pattern.compile("\\+");
			Matcher m = p.matcher(tkn.split("\t")[0]);
			if(m.lookingAt())
			{
				return;
			}
			firstSplit = tkn.split("/");
				
			try
			{
				br.write(firstSplit[0]+"\t"+newEvaluate(tkn)+"\t"+firstSplit[1].split("\t")[0]);
				
				if(firstSplit[1].contains("NNB")||firstSplit[1].contains("NNG")||firstSplit[1].contains("NNP")||firstSplit[1].contains("NP")||firstSplit[1].contains("NR")||firstSplit[1].contains("NN")||firstSplit[1].contains("NF"))
				{
					br.write("\t"+"N");
				}
				br.newLine();
				br.flush();
			}
			catch (IOException e)
			{
				System.err.println(e);
			}
		}
		else
		{
			Pattern p = Pattern.compile("\\+");
			Matcher m = p.matcher(tkn.split("\t")[0]);
			if(m.lookingAt())
			{
				return;
			}
			firstSplit = tkn.split("\\+");
			for(int i=0;i<firstSplit.length;i++)
			{
				secondSplit = firstSplit[i].split("/");
				try
				{
					br.write(secondSplit[0]+"\t"+newEvaluate(firstSplit[i])+"\t"+secondSplit[1].split("\t")[0]);
					if(secondSplit[1].contains("NNB")||secondSplit[1].contains("NNG")||secondSplit[1].contains("NNP")||secondSplit[1].contains("NP")||secondSplit[1].contains("NR")||secondSplit[1].contains("NN")||secondSplit[1].contains("NF"))
					{
						br.write("\t"+"N");
					}
					br.newLine();
					br.flush();
				}
				catch(IOException e)
				{
					System.err.println(e);
				}
			}
		}
		
	}
	
	public void setOutput(String output)
	{
		String[] arr = output.split(": ");
		cnt = Integer.parseInt(arr[1]);
	}
	
	public int evaluate(String str)
	{
		if(str.length()==1)return 0;
			
		int wordLength = 0;
		for(int i=0;i<dictWord.size();i++)
		{
			if(dictWord.get(i).equals(str))
			{
				return dictValue.get(i);	
			}
			else if(dictWord.get(i).contains(str))
			{
				for(int j=i+1;j<dictWord.size();j++)
				{
					if(dictWord.get(j).equals(str))
					{
						return dictValue.get(j);	
					}
				}
				wordLength = dictWord.get(i).length();
				
				if(wordLength==1&&str.length()==1)
				{
					return dictValue.get(i);
				}
				
				if(str.equals("아")!=true||str.equals("다")!=true)
				{
					return dictValue.get(i);
				}

				return dictValue.get(i);
			}
		}

		return 0;
	}

	public int newEvaluate(String evalTarget)
	{
		return dict.evaluate(evalTarget);
	}
}
