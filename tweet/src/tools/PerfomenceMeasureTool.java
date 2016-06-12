package tools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PerfomenceMeasureTool {

	public static int positive = 0;
	public static int negative = 0;
	public static int neutural = 0;

	public static int differ = 0;
	
	public static ArrayList<PolaString> lines;
	public static ArrayList<JSONObject> jObjs;
	
	static class PolaString
	{
		String str, polarity;
		
		public PolaString(String str, String polarity)
		{
			this.str = str;
			this.polarity = polarity;
		}
		
		public String getStr()
		{
			return str;
		}
		
		public String getPolarity()
		{
			return polarity;
		}
	}
	
	public static void getAccuracy(String path)
	{
		try
		{
			try {
				init(path);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("��Ȯ�� ������ �����մϴ�.");
		System.out.println("������ 1, �ƴϸ� 2�� �Է��ϼ���");
		
		for(int i=0;i<lines.size();i++)
		{
			float a = (float) (((float)lines.size()-(float)differ)/(float)lines.size()*100.0);
			System.out.println("���� ��Ȯ�� : "+a+"%");
			System.out.println("���� ���� ("+(i+1)+"/"+lines.size()+") : "+lines.get(i).getStr());
			System.out.print("�ý��� : "+lines.get(i).getPolarity()+", ����� >> ");
			switch(sc.nextInt())
			{
			case 1 : break;
			case 2 : differ++; break;
			}
		}
		System.out.println("�Ǵܰ�� (�����/�ý���)");
		System.out.println("�ý��۰� ����ڰ� �Ǵ��� �� �� ���̳��� ���� : "+differ);
	}

	public static void getMorpsAccuracy(String path, int j)
	{
		Scanner sc = new Scanner(System.in);
		try {
			init(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("�˻繮�� : "+lines.get(j).getStr()+", ���� : "+lines.get(j).getPolarity());
		System.out.println("������ 1, �ƴϸ� 2");
		
		JSONArray jArr = (JSONArray) jObjs.get(j).get("morps");
		int i=0;
		for(Object obj : jArr)
		{
			float a = (float) (((float)jArr.size()-(float)differ)/(float)jArr.size()*100.0);
			System.out.println((i+1)+"/"+jArr.size()+" ���� ��Ȯ�� : "+a+"%");
			System.out.println("�� �ܾ� : "+((JSONObject)obj).get("morpheme")+", �ؼ��� : "+((JSONObject)obj).get("polarity"));
			i++;
			
			switch(sc.nextInt())
			{
			case 2 : differ++;
			}
		}
		float a = (float) (((float)jArr.size()-(float)differ)/(float)jArr.size()*100.0);
		System.out.println("���� ��Ȯ�� : "+a+"%");
	}

	private static void init(String path) throws IOException, ParseException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\resource\\"+path)));
		lines = new ArrayList<PolaString>();
		jObjs = new ArrayList<JSONObject>();
		while(true)
		{
			String line = br.readLine();
			if(line==null)break;
			JSONObject obj = (JSONObject) JSONValue.parseWithException(line);
			jObjs.add(obj);
			lines.add(new PolaString((String)obj.get("text"), (String)obj.get("sentiment")));
			switch((String)obj.get("sentiment"))
			{
			case "positive" : positive++; break;
			case "negative" : negative++; break;
			case "neutural" : neutural++; break;
			}
		}
		
		br.close();
	}
	
}
