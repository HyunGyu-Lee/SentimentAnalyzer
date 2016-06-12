package tweet;

import java.io.*;

public class XMLParser {
	
	DataManager loader;
	BufferedWriter br;
	String filename;
	
	public XMLParser(DataManager loader, String filename)
	{
		this.loader = loader;
		this.filename = filename;
		
		try
		{
			br = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename+".xml"),"UTF-8"));
		}
		catch(IOException e)
		{
			System.err.println("XML 파일이름을 다시 설정하세요");
		}
		
		dataToXML();
		
	}
	
	public void dataToXML()
	{
			try {
				br.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				br.newLine();
				br.flush();
				br.write("<Data>");
				br.newLine();
				br.flush();
			
				for(int i=0;i<loader.arr.length;i++)
				{
					
					br.write("\t<Sentense>");
					br.newLine();
					br.flush();
					
					br.write("\t\t<No> "+(i+1)+" </No>");
					br.newLine();
					br.flush();
					
					br.write("\t\t<Text> "+loader.getData(i)+" </Text>");
					br.newLine();
					br.flush();
					
					br.write("\t\t<Word> ");
					br.newLine();
					br.flush();
					
					if(loader.getData1(i)==null)
					{
						br.write("null ");
						br.flush();
					}
					else
					{
						br.write("\t\t\t<Key> ");
						br.flush();
						
						for(int j=0;j<loader.getData1(i).length-1;j++)
						{
							br.write(loader.getData1(i)[j+1].split("\t")[0]+" ");
							br.flush();
						}
						br.write(" </Key>");
						br.flush();
						br.newLine();

						br.write("\t\t\t<Value> ");
						br.flush();
						
						for(int j=0;j<loader.getData1(i).length-1;j++)
						{
							br.write(loader.getData1(i)[j+1].split("\t")[1]+" ");
							br.flush();				
						}
						br.write("</Value>");
						br.newLine();
						br.flush();				
					}
					br.write("\t\t</Word>");
					br.newLine();
					br.flush();
					
					br.write("\t</Sentense>");
					br.newLine();
					br.flush();
				}
				
				br.write("</Data>");
				br.flush();
				br.close();
			}
			catch (IOException e)
			{
				System.err.println("XML 데이터 파싱 에러");
			}
	}
}
