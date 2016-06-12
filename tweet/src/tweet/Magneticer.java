package tweet;

public class Magneticer {
	
	public static String calculate(String text, String[] arr)
	{
		System.out.println("현재 문장 : "+text);
		System.out.println("현재 배열의 첫번째 원소의 극성: "+getValue(arr[1]));

		return null;
	}
	
	public static String getValue(String data)
	{
		return data.split("\t")[0];
	}
	public static int getMag(String data)
	{
		return Integer.parseInt(data.split("\t")[1]);
	}
}
