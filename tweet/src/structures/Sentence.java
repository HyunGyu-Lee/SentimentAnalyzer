package structures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.simple.JSONArray;

import com.orsoncharts.util.json.JSONObject;

public class Sentence {
	
	private String text;				// 본문
	private List<Eojeol> eojeols;		// 문장이 내 어절 목록
	private List<Relation> relations;	// 문장 내 의존관계
	private int polarity;				// 문장 극성
	private int id;						// 문장 인덱스
	
	public HashMap<Integer, ArrayList<Integer>> portableMapper;
	
	public Sentence(String text)
	{
		this.text = text;
		eojeols = new ArrayList<Eojeol>();
		relations = new ArrayList<Relation>();
		polarity = 0;
	}

	public void addRelations(Relation r)
	{
		relations.add(r);
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Eojeol> getEojeols() {
		return eojeols;
	}

	public void addEojeol(Eojeol eojeol) {
		eojeols.add(eojeol);
	}

	public int getPolarity() {

		int v = 0;
		
		for(Eojeol e : eojeols)
		{
			for(int i=0; i<e.getMorps().size();i++)
			{
				if(i+1!=e.getMorps().size())
				{
					checkEmphasis(e.getMorps().get(i), e.getMorps().get(i+1));
					checkNegative(e.getMorps().get(i), e.getMorps().get(i+1));
				}
				
				v+=e.getMorps().get(i).getPolarity();
			}
		}
		
		return v;
		
	}
	
	public Morpheme finder(int wIndex)
	{
		for(int i=0;i<portableMapper.size();i++)
		{
			for(int j=0 ; j<portableMapper.get(i).size(); j++)
			{
				if(wIndex==portableMapper.get(i).get(j))
				{
					return eojeols.get(i).getMorps().get(j);
				}
			}
		}
		return null;
	}
	
	public String[] portable()
	{
		portableMapper = new HashMap<Integer, ArrayList<Integer>>();
		int ESize = 0;
		ArrayList<String> tmp = new ArrayList<String>();
		ArrayList<Integer> specEIndex;
		for(int i=0;i<eojeols.size();i++)
		{
			specEIndex = new ArrayList<Integer>();
			
			for(String s : eojeols.get(i).portableEojeols())
			{
				
				
				if(s.split("\t")[2].equals("SW")||s.split("\t")[2].equals("E"))continue;
				specEIndex.add(ESize++);
				tmp.add(s);
			}
			
			portableMapper.put(i, specEIndex);
		}
		
		String[] real = new String[tmp.size()];
		
		for(int i=0;i<real.length;i++)
		{
			real[i] = tmp.get(i);
		}
		return real;
	}
	
	public void checkNegative(Morpheme tester, Morpheme target)
	{
		String[] checkers = {"안","않","못"};
		
		for(String checker : checkers)
		{
			if(tester.equals(checker))
			{
				target.setNegative(true);
			}
		}
		
	}
	
	public void checkEmphasis(Morpheme tester, Morpheme target)
	{
		String[] checkers = {"가장","진짜","완전","제일"};

		for(String checker : checkers)
		{
			if(tester.equals(checker))
			{
				target.setNegative(true);
			}
		}
	}
	
	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public String toJSONString()
	{
		//String json = "";
		JSONObject jObj = new JSONObject();
		
		jObj.put("text", text);
		jObj.put("id", id);
		
		if(polarity>0)
		{
			jObj.put("sentiment","positive");
		}
		else if(polarity==0)
		{
			jObj.put("sentiment","neutral");
		}
		else if(polarity<0)
		{
			jObj.put("sentiment","negative");
		}
		
		JSONArray jArr = new JSONArray();
		
		for(Eojeol e : eojeols)
		{
			jArr.add(e.getJSONObject());
		}
		
		JSONArray jArr1 = new JSONArray();
		
		for(int i=0;i<relations.size();i++)
		{
			relations.get(i).setId(i);
			jArr1.add(relations.get(i).getJSONObject());
		}
		
		jObj.put("eojeols", jArr);
		jObj.put("relations", jArr1);
		jObj.put("sentence_polarity", polarity);
		
		return jObj.toJSONString();
	}
}
