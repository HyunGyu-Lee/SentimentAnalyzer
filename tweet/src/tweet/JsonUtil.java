package tweet;

import com.orsoncharts.util.json.JSONObject;
import com.orsoncharts.util.json.JSONValue;
import com.orsoncharts.util.json.parser.ParseException;

public class JsonUtil {
 
    public static String getPretty(String jsonString) {
    	boolean checker = false;
    	int sw = 1;
    	try {
			JSONObject jobj = (JSONObject)JSONValue.parseWithException(jsonString);
			if(jobj.get("text").toString().contains(","))
			{
				checker = true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
        final String INDENT = "    ";
        StringBuffer prettyJsonSb = new StringBuffer();
        int indentDepth = 0;
        String targetString = null;
        for(int i=0; i<jsonString.length(); i++) {
            targetString = jsonString.substring(i, i+1);
            if(targetString.equals(":"))
            {
            	prettyJsonSb.append(" "+targetString+" ");
            }
            else if(targetString.equals("{")||targetString.equals("[")) {
                if(indentDepth>=2)
                {
                	if(indentDepth>=4)
                	{
                		prettyJsonSb.append(targetString+" ");
                		indentDepth++;
                	}
                	else
                	{
                		prettyJsonSb.append(targetString).append("\n");
                    	indentDepth++;
                        for(int j=0; j<indentDepth; j++) {
                            prettyJsonSb.append(INDENT);
                        }
                	}
                }
                else
                {
                	prettyJsonSb.append(targetString).append("\n");
                    indentDepth++;
                    for(int j=0; j<indentDepth; j++) {
                        prettyJsonSb.append(INDENT);
                    }
                }
            }
            else if(targetString.equals("}")||targetString.equals("]")) {
                if(indentDepth>=5)
                {
                    prettyJsonSb.append(" "+targetString+" ");
                	indentDepth--;
                }
                else
                {
                	prettyJsonSb.append("\n");
                    indentDepth--;
                    for(int j=0; j<indentDepth; j++) {
                        prettyJsonSb.append(INDENT);
                    }
                    prettyJsonSb.append(targetString);
                }
            }
            else if(targetString.equals(",")) {
            	 if(checker)
            	 {
            		 if(jsonString.substring(i+1,i+7).equals("\"text\""))
            		 {
                		sw=2;
           			 	prettyJsonSb.append(" "+targetString);
           			 	prettyJsonSb.append("\n");
           			 	for(int j=0; j<indentDepth; j++) {
           			 		prettyJsonSb.append(INDENT);
           			 	}

            		 }
            		 else
            		 {
            			 if(sw==2)
            			 {
            				 prettyJsonSb.append(targetString);
             			 	 sw=1;
            			 }
            			 else
            			 {
            				if(indentDepth<=4)
            				{
            					prettyJsonSb.append(" "+targetString+" ");
                 				prettyJsonSb.append("\n");
                   			 	for(int j=0; j<indentDepth; j++) {
                   			 		prettyJsonSb.append(INDENT);
                   			 	}
            				}
            				else
            				{
            					prettyJsonSb.append(" "+targetString+" ");
            				}
            			 }
            		 }
            	 }
            	 else
            	 {
            		 if(indentDepth<=4)
     				{
     					prettyJsonSb.append(" "+targetString+" ");
          				prettyJsonSb.append("\n");
            			 	for(int j=0; j<indentDepth; j++) {
            			 		prettyJsonSb.append(INDENT);
            			 	}
     				}
     				else
     				{
     					prettyJsonSb.append(" "+targetString+" ");
     				}
            		
            	 }
            	 
            }
            else {
                prettyJsonSb.append(targetString);
            }
        }

        return prettyJsonSb.toString();
    }
}