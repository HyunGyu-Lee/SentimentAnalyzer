package tweet;

import java.util.ArrayList;

public class Statisticer {
	
	/*
	 * manager : 통계를 낼 데이터셋
	 * postive, negative, neutral : 데이터셋의 긍정, 부정, 중립 합은 100% 나와야함
	 * posString, negString, neuString : 긍정, 부정, 중립 문장 목록
	 * #각 극성마다 극성값 더 세세하게 나눠서보여주면좋을듯
	 * */
	DataManager manager;
	double positive, negative, neutral;
	int p,n1,n2;
	ArrayList<String> posString, negString, neuString;
	
	public Statisticer(DataManager manager)
	{
		this.manager = manager;
		positive = 0;
		negative = 0;
		neutral = 0;
		p = 0;
		n1 = 0;
		n2 = 0;
		posString = new ArrayList<String>();
		negString = new ArrayList<String>();
		neuString = new ArrayList<String>();
		work();
	}
	
	public void work()
	{
		for(int i=0;i<=manager.ss_arr.size()-1;i++)
		{
			if(manager.getTextValue(i)>0)
			{
				positive++;
				p++;
				posString.add(manager.getData(i));
			}
			else if(manager.getTextValue(i)==0)
			{
				neutral++;
				n2++;
				neuString.add(manager.getData(i));
			}
			else
			{
				negative++;
				n1++;
				negString.add(manager.getData(i));
			}
		}
	}
	
	public double getPositive()
	{
		return positive/(positive+neutral+negative);
	}
	public double getNuetral()
	{
		return neutral/(positive+neutral+negative);
	}
	public double getNegative()
	{
		return negative/(positive+neutral+negative);
	}
	public double getTotal()
	{
		return getPositive()+getNuetral()+getNegative();
	}

	public ArrayList<String> getPosString()
	{
		return posString;
	}
	public ArrayList<String> getNeuString()
	{
		return neuString;
	}
	public ArrayList<String> getNegString()
	{
		return negString;
	}

	public int getPCnt()
	{
		return p;
	}
	public int getN1Cnt()
	{
		return n1;
	}
	public int getN2Cnt()
	{
		return n2;
	}
}
