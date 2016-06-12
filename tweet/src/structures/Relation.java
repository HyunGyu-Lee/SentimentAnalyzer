package structures;

import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.json.JSONObject;

public class Relation {

	private ArrayList<String> words;
	private List<ArrayList<Integer>> location;
	private int id;
	private int polarity;
	
	public Relation(Morpheme m1, Morpheme m2, int polarity)
	{
		words = new ArrayList<String>();
		location = new ArrayList<ArrayList<Integer>>();
		
		ArrayList<Integer> index1 = new ArrayList<Integer>();
		ArrayList<Integer> index2 = new ArrayList<Integer>();

		words.add(m1.getText());
		words.add(m2.getText());
		
		index1.add(m1.getGroupId());
		index1.add(m1.getId());
		
		index2.add(m2.getGroupId());
		index2.add(m2.getId());
		
		location.add(index1);
		location.add(index2);
		
		this.polarity = polarity;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public ArrayList<String> getWords()
	{
		return words;
	}
	
	public List<ArrayList<Integer>> getLocation()
	{
		return location;
	}
	
	public JSONObject getJSONObject()
	{
		JSONObject jObj = new JSONObject();
		
		jObj.put("words", words);
		jObj.put("location", location);
		jObj.put("polarity", polarity);
		jObj.put("id", id);
		
		return jObj;
	}
	
}
