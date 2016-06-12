package tweet;

import java.io.*;
import java.util.ArrayList;

public class NounExtraction {
	
	String filePath;
	String outputPath; // 명사만 추출된 파일
	String outputPath2; // 형태소분석된 모든 내용
	String dicName;
	
	BufferedReader in;
	BufferedWriter out;
	BufferedWriter out2;
	
	String mainInputString = "";
	MyParser parser2;
	public NounExtraction(String filePath, String dicName)
	{
		this.dicName = dicName;
		this.filePath = filePath;
		this.outputPath = filePath+".nounContext";
		this.outputPath2 = filePath+".context";
		
		try
		{
			in = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"UTF-8"));
			out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath),"UTF-8"));
			out2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputPath2),"UTF-8"));
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
	
	public void extraction()
	{
		int tik = 0;	// 0 : 초기, 1 : 작업시작, 2 : 작업완료->초기복귀
		//parser2 = new MyParser(out2, dicName);
		DictionaryProcessor dp = new DictionaryProcessor();
		dp.process();
		parser2 = new MyParser(out2, dp.getDictionary());
		String checker = "";
		try
		{
			String re;
			while(true)
			{
				re = in.readLine();
				if(re==null)break;
				
				if(re.contains("INPUT"))
				{
					checker = re;
					tik = 1;
					continue;
				}
				if(re.contains("TOKEN"))
				{
					out2.write("fToken: ");
					out2.newLine();
					out2.write(re);
					out2.newLine();
					out2.flush();
					continue;
				}
				if(re.contains("NUM_OUTPUT"))
				{
					if(Integer.parseInt(re.split(":")[1].split(" ")[1])>1)
					{
						ArrayList<String> tmp = new ArrayList<String>();
						while(true)
						{
							re = in.readLine();

							if(re.equals(""))
							{
								break;
							}
							
							tmp.add(re);
						}
						parser2.parseToken(compare(tmp));
					}
					continue;
				}

				if(tik==1&&re.contains("==========================="))
				{
					tik = 0;
					out.write("line");
					out.newLine();
					out.flush();
					
					out2.write("line");
					out2.newLine();
					out2.flush();
				}
				
				if(tik==1)
				{
					if(checker.split(":")[1].equals(" "))
					{
						while(true)
						{
							if(in.readLine().contains("==========================="))
							{
								break;
							}
						}
						continue;
					}
					if(re.length()!=0)
					{
						parser2.parseToken(re);
					}
				}
			}
			out.write("line");
			out.newLine();
			out.flush();
			
			out2.write("line");
			out2.newLine();
			out2.flush();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
	
	public String compare(ArrayList<String> tmp)
	{
		String prior = "";
		prior = tmp.get(0);

		for(int i=1;i<tmp.size();i++)
		{
			if(Integer.parseInt(prior.split("\t")[1])<Integer.parseInt(tmp.get(i).split("\t")[1]))
			{
				prior = tmp.get(i);
			}
		}
		
		return prior;
	}
	
	public MyParser getParser()
	{
		return parser2;
	}
}
