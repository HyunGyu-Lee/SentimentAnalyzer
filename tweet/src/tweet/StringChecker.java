package tweet;

public class StringChecker {

	public static boolean Checker(String str)	// true ����, false ����
	{
		if(str.contains("������")||str.contains("����")||str.contains("����")||str.contains("����")||str.contains("��")||str.contains("����")||str.contains("�Ǹ�")||str.contains("����")||str.contains("�״�")||str.contains("������"))
		{
			return false;
		}
		
		return true;
	}	
	public static String Linker(String str)
	{
		int start, end;
		String returnStr="";
		String linkedStr = "<a href=";		
		start = str.indexOf("http");
		end = str.length();
		returnStr = str.substring(0, start);
		linkedStr+=str.substring(start, end);
		linkedStr+=">";
		linkedStr+=str.substring(start, end);
		linkedStr+="</a>";
		returnStr+=linkedStr;
		
		return returnStr;
	}
	
	
}
