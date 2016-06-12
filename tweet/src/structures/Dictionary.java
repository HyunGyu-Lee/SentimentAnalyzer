package structures;

import java.util.ArrayList;

public class Dictionary {

	ArrayList<DictionaryWord> dictWords = new ArrayList<DictionaryWord>();
	
	public void addDictWord(DictionaryWord dw)
	{
		dictWords.add(dw);
	}
	
	public int evaluate(String evalTarget)
	{
		//if(evalTarget.contains("/NNG")==false)return 0;
		
		if(evalTarget.split("\t").length>1)evalTarget = evalTarget.split("\t")[0];
		
		if(evalTarget.contains("/J")||evalTarget.contains("/E")||evalTarget.contains("XSN"))return 0;
		
		for(DictionaryWord dd : dictWords)
		{
			for(String xx : dd.getMorps())
			{
				if(xx.equals(evalTarget))
				{
					for(String check : dd.getMorps())
					{
						if(check.contains("/XPN"))
						{
							System.out.println("사전 기준 문장 : "+dd.getWord());
							System.out.println("\t사전 : "+xx+", 평가대상 : "+evalTarget+", 부여값 : "+dd.porarity);
							return -dd.porarity;
						}
					}
					System.out.println("사전 기준 문장 : "+dd.getWord());
					System.out.println("\t사전 : "+xx+", 평가대상 : "+evalTarget+", 부여값 : "+dd.porarity);
					return dd.porarity;
				}
			}
		}
		
		return 0;
	}
	
	public void print()
	{
		dictWords.forEach((str)->{
			System.out.println("메인 데이터 : "+str.getWord()+", "+str.getPolarity());
			str.getMorps().forEach((str2)->{
				System.out.println("\t"+str2);
			});
		});
	}
	
	
	
}
