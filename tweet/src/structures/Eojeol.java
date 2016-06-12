package structures;

import java.util.ArrayList;
import java.util.List;

import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;

public class Eojeol {
	
	private String text; 			// ���� ����
	private List<Morpheme> morps;	// ���� �� ���¼� ���
	private int id;					// ���� �ε���
	
	public Eojeol(String text)
	{
		this.text = text;
		morps = new ArrayList<Morpheme>();
	}

	public String[] portableEojeols()
	{
		String[] portable = new String[morps.size()];
		
		for(int i=0;i<portable.length;i++)
		{
			portable[i] = morps.get(i).potableMorp();
		}
		
		return portable;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public List<Morpheme> getMorps() {
		return morps;
	}

	public void addMorp(Morpheme morp) {
		morps.add(morp);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getJSONObject()
	{
		JSONObject jObj = new JSONObject();
		
		jObj.put("lex", text);
		jObj.put("id", id);
		
		JSONArray jArr = new JSONArray();
		
		for(Morpheme m : morps)
		{
			jArr.add(m.getJSONObject());
		}
		
		jObj.put("morps", jArr);
		
		return jObj;
	}
	
}
