package tools;

public class JsonUtility {

	public static String getPrettyString(String json)
	{
		StringBuilder br = new StringBuilder();
		String CRLF = "\n";
		String TAP = "\t";
		int depth = 0;

		for(int i=0;i<json.length();i++)
		{
			String target = json.substring(i,i+1);
			
			if(target.equals(":"))
			{
				br.append(" "+target+" ");
			}
			else if(target.equals("{")||target.equals("["))
			{
				depth++;
				if(depth<=4)
				{
					br.append(target).append(CRLF);
					for(int j=0;j<depth;j++)
					{
						br.append(TAP);
					}
				}
				else
				{
					br.append(target);
				}

			}
			else if(target.equals("}")||target.equals("]"))
			{
				depth--;
				br.append(target);
				


			}
			else if(target.equals(","))
			{
				if(depth<4)
				{
					br.append(" "+target+" ").append(CRLF);
					for(int j=0;j<depth;j++)
					{
						br.append(TAP);
					}
				}
				else
				{
					br.append(" "+target+" ");
				}
			}
			else
			{
				
				br.append(target);
			}
			
			
			
		}
		
		
		return br.toString();
	}
	
}
