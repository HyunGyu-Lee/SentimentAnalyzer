package tweet;

import tweet.DataManager.Dictionary;

public class AAAAA {

	public static void main(String[] args) {
		MyParser m = new MyParser(null,"c:\\resource\\dict.xls");
		DataManager am = new DataManager("c:\\resource\\test.json",null);
		am.setDictionary(m.getDictWord(),m.getDictValue());
		Dictionary dict = am.getDictionary();
		
		System.out.println(m.evaluate("°ª"));
	}

}
