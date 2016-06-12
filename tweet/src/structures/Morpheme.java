package structures;

import com.orsoncharts.util.json.JSONObject;

public class Morpheme {
	
	private int e_id;		// �Ҽ� ���� ���̵�
	private String text;	// ���¼� ����
	private String type;	// ���¼� ǰ��
	private int polarity;	// ���¼� �ؼ�
	private int id;			// ���¼� ���̵�
	private boolean emphasis;	// �������� ���� ����
	private boolean negative;	// �������� ���� ����
	
	public Morpheme(String text)
	{
		this.text = text;
		polarity = 0;
		emphasis = false;
		negative = false;
	}

	public void setEGroupId(int id)
	{
		e_id = id;
	}
	
	public int getGroupId()
	{
		return e_id;
	}
	public boolean isEmphasis()
	{
		return emphasis;
	}
	
	public boolean isNegative()
	{
		return negative;
	}
	
	public void setEmphasis(boolean flag)
	{
		emphasis = flag;
	}
	
	public void setNegative(boolean flag)
	{
		negative = flag;
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getPolarity() {
		
		if(emphasis)polarity*=2;
		if(negative)polarity*=-1;
		
		return polarity;
	}

	public String potableMorp()
	{
		return text+"\t"+polarity+"\t"+type;
	}
	
	public void setPolarity(int polarity) {
		this.polarity = polarity;
	}

	public String getType()
	{
		return type;
	}
	
	public void setType(String type)
	{
		this.type = type;
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
		
		jObj.put("morpheme", text);
		jObj.put("id", id);
		jObj.put("type", type);
		jObj.put("polarity", polarity);
		
		return jObj;
	}
	
}
