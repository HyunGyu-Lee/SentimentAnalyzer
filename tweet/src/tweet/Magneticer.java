package tweet;

public class Magneticer {
	
	public static String calculate(String text, String[] arr)
	{
		System.out.println("���� ���� : "+text);
		System.out.println("���� �迭�� ù��° ������ �ؼ�: "+getValue(arr[1]));

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
