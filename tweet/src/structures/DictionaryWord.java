package structures;

import java.util.ArrayList;

public class DictionaryWord
{
	String mStr = "";
	int porarity = 0;
	ArrayList<String> morps = new ArrayList<String>();
	
	public void setInput(String input)
	{
		String[] data = input.split(":")[1].split("\t");
		mStr = data[0];
		porarity = Integer.parseInt(data[1]);
	}
	
	public String getWord()
	{
		return mStr;
	}
	
	public int getPolarity()
	{
		return porarity;
	}
	
	public ArrayList<String> getMorps()
	{
		return morps;				
	}
	
	public void addMorps(String morp)
	{
		if(morp.contains("+"))
		{
			for(String target : morp.split("\\+"))
			{
				if(target.contains("VV")||target.contains("NNG")||target.contains("MAG")||target.contains("VA")||target.contains("UNK"))
				{
					if(target.split("/")[0].length()!=1)
					{
						if(target.split("\t").length>1)
						{
							target = target.split("\t")[0];
						}
						System.out.println("사전에 추가되는 데이터"+target);
						morps.add(target);
					}
				}
			}
		}
		else
		{
			if(morp.contains("VV")||morp.contains("NNG")||morp.contains("MAG")||morp.contains("VA")||morp.contains("UNK"))
			{
				if(morp.split("/")[0].length()!=1)
				{
					if(morp.split("\t").length>1)
					{
						morp = morp.split("\t")[0];
					}
					morps.add(morp);
				}
			}
		}
	}
	
}
