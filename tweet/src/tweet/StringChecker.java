package tweet;

public class StringChecker {

	public static boolean Checker(String str)	// true 긍정, false 부정
	{
		if(str.contains("쓰레기")||str.contains("안좋")||str.contains("버려")||str.contains("않좋")||str.contains("않")||str.contains("별로")||str.contains("실망")||str.contains("이하")||str.contains("그닥")||str.contains("무쓸모"))
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
