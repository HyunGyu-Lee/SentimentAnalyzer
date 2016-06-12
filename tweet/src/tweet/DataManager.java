package tweet;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.orsoncharts.util.json.JSONArray;
import com.orsoncharts.util.json.JSONObject;

import structures.Eojeol;
import structures.Morpheme;
import structures.Sentence;

public class DataManager {
	
	ArrayList<Sentence> sentences;
	ArrayList<String> dictWord;
	ArrayList<Integer> dictValue;
	String filename;
	BufferedReader m_br;
	BufferedReader s_br;
	BufferedReader ss_br;
	int cnt;
	String[] arr;
	ArrayList<HashMap<Integer, Integer>> refTable;
	HashMap<Integer, Integer> refElement;
	HashMap<Integer, String[]> s_arr;
	ArrayList<String[]> ss_arr;
	ArrayList<String> addTable;
	Dictionary dict;
	ArrayList<ArrayList<String>> relations;

	public DataManager(String filename, String dictName)
	{	
		sentences = new ArrayList<Sentence>();
		relations = new ArrayList<ArrayList<String>>();
		addTable = new ArrayList<String>();
		m_br=null;
		cnt = 0;
		arr = null;
		s_arr = new HashMap<Integer, String[]>();
		ss_arr = new ArrayList<String[]>();
		refElement = new HashMap<Integer, Integer>();
		refTable = new ArrayList<HashMap<Integer, Integer>>();
		this.filename = filename + ".txt";

		try
		{
			m_br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename),"UTF-8"));
			s_br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename+".ma.nounContext"),"UTF-8"));
			ss_br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename+".ma.context"),"UTF-8"));
			while(m_br.readLine()!=null)
			{
				cnt++;	
			}
			m_br = new BufferedReader(new InputStreamReader(new FileInputStream(this.filename),"UTF-8"));
		}catch(IOException e)
		{
			System.err.println(e);
			return;
		}
		readFile();
		newData();
		ibelievecurrentcomputingpower();
	}
	
	/* .text파일 읽어오기 */
	public void readFile()
	{
		String str;
		arr = new String[cnt];
		
		try
		{
			for(int i=0;i<cnt;i++)
			{
				str = m_br.readLine();
				arr[i] = str;
				relations.add(new ArrayList<String>());
			}
		}catch(IOException e){}
	}

	/* noun, context 둘 다 읽어오기 */
	public void newData()
	{
		int i=0, j=0, k=0;;

		String str;
		String[] s_arr_e = null;
		String[] ss_arr_e = null;
		String t_str="";
		String tt_str="";

		try
		{
			while(true)
			{
				str = ss_br.readLine();
				
				if(str==null)break;
				if(str.contains(",")||str.contains(":")||str.contains("..")||str.contains("\"")||str.contains("'")||str.contains("·")||str.contains("[")||str.contains("]"))continue;
				
				if(str.contains("line"))
				{
					t_str="";
					tt_str="";
					s_arr.put(i, s_arr_e);
					ss_arr.add(ss_arr_e);
					refTable.add(refElement);
					refElement = new HashMap<Integer,Integer>();
					i++;
					j = 0;
					k = 0;
				}
				else
				{
					if(str.split("\t").length==3)
					{
						t_str = t_str + "," +str.split("\t")[0] + "\t" + str.split("\t")[1] + "\t" + str.split("\t")[2];
						tt_str = tt_str + "," +str.split("\t")[0] + "\t" + str.split("\t")[1] + "\t" + str.split("\t")[2];
						s_arr_e = t_str.split(",");
						ss_arr_e = tt_str.split(",");
						refElement.put(j, k);
						k++;
					}
					else
					{
						tt_str = tt_str +","+ str;
						ss_arr_e = tt_str.split(",");
					}
					j++;
				}
				
				
				
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}	
	}
	
	/* text배열의 i번째 문장 반환 */
	public String getData(int i)
	{
		return arr[i];
	}
	
	/* 데이터의 총 길이 */
	public int getDataLength()
	{
		return arr.length;
	}
	
	/* nounContext배열의 i번째 String[]을 반환 */
	public String[] getData1(int i)
	{
		return s_arr.get(i);
	}

	public String[] getData2(int i)
	{
		return ss_arr.get(i);
	}

	public void setData2(int i, String[] data)
	{
		ss_arr.set(i, data);
	}
	
	// currentInx가 i인 문장의 명사목록의 j번째 단어의 극성을 반환
	public int getData1_Value(int i, int j)
	{
		return Integer.parseInt(s_arr.get(i)[j+1].split("\t")[1]);
	}

	// currentInx가 i인 문장의 단어목록의 j번째 단어의 극성을 반환	
	public int getData2_Value(int i, int j)
	{
		return Integer.parseInt(ss_arr.get(i)[j+1].split("\t")[1]);
	}
	
	/* 문장 극성*/
	public int getTextValue(int currentIdx)
	{
		int result = 0;
		int value = 0;
		String[] check = {"가장","진짜","완전","제일"};
		String[] check2 = {"안","않","못"};
		for(int i=0;i<ss_arr.get(currentIdx).length-1;i++)
		{
			value = getData2_Value(currentIdx,i);
			/* 강조 */
			for(int j=0;j<check.length;j++)
			{
				if(i!=0&&(ss_arr.get(currentIdx)[i-1].equals(check[j])||ss_arr.get(currentIdx)[i-1].contains(check[j])))
				{
					if(check[j].contains(ss_arr.get(currentIdx)[i])==false)
					{
						value = value * 2;
					}
				}
			}
			/* 반전 */
			for(int k=0;k<check2.length;k++)
			{
				if(i!=0&&(ss_arr.get(currentIdx)[i-1].equals(check2[k])||ss_arr.get(currentIdx)[i-1].contains(check2[k])))
				{
					if(check2[k].contains(ss_arr.get(currentIdx)[i])==false)
					{
						value = value * -1;
					}
				}
			}
			
			result = result + value;
		}
		
		return result;	
	}

	/* nounContext배열의 i번째 String[]의 j번째 원소의 극성값을 value로 변경 */
	public void setData1_Value(int i, int j, int value)
	{
		String arr[];
		arr = s_arr.get(i);
		arr[j+1] = arr[j+1].split("\t")[0] +"\t"+ String.valueOf(value);
		s_arr.replace(i, arr);
	}
	
	/* context배열의 i번째 String[]의 j번째 원소의 극성값을 value로 변경 */
	public void setData2_Value(int i, int j, int value)
	{
		String arr[];
		arr = ss_arr.get(i);
		arr[j+1] = arr[j+1].split("\t")[0] +"\t"+ String.valueOf(value);
		ss_arr.set(i, arr);
		if(getRefElement(i).get(j)!=null)
		{
			
		}
	}

	/* i는 문장의 인덱스, refElement는 그 문장안에 context데이터와 nounContext데이터의 연관성을 정수로 표현한 HashMap
	 * getRefElement(i).get(idx) -> context배열의 idx 번째 데이터가 nounContext배열의 몇번째 있는지를 알 수 있음
	 *  */
	public HashMap<Integer,Integer> getRefElement(int i)
	{
		return refTable.get(i);
	}
	
	/* 변경사항 파일로 저장, 같은 디렉토리에 생성되며 파일명뒤에 _ 붙어있음 */
	public void backup()
	{
		try
		{
			BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename.split("\\.")[0]+"_analyze_pretty.json"),"UTF-8"));
			BufferedWriter bw3 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename.split("\\.")[0]+"_analyze.json")));
			
			for(int i=0;i<sentences.size();i++)
			{
				bw2.write(JsonUtil.getPretty(sentences.get(i).toJSONString()));
				bw2.newLine();
				bw2.flush();
				bw3.write(sentences.get(i).toJSONString());
				bw3.newLine();
				bw3.flush();
			}
			
			saveDictionary();
			saveDictionary2();
			bw2.close();
			bw3.close();
		}
		catch (IOException e)
		{
			System.err.println(e);
		}
		
	}
	
	/* 사전목록 저장 메소드 - backup메소드 실행시 자동실행 */
	public void saveDictionary()
	{
		try {
			BufferedWriter bbz = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("C:\\resource\\dict.txt"),"UTF-8"));
			for(int i=0;i<dictWord.size();i++)
			{
				bbz.write(dictWord.get(i)+"\t"+dictValue.get(i));
				bbz.newLine();
				bbz.flush();
			}
			bbz.flush();
			bbz.close();
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}

	public void saveDictionary2()
	{
		Workbook wb = null;
	
		wb = new HSSFWorkbook();
		
		Sheet sheet1 = wb.createSheet("sheet1");
		sheet1.setColumnWidth(0, 10000);
	    sheet1.setColumnWidth(9, 10000);
	    CellStyle cellStyle = wb.createCellStyle();
	    cellStyle.setWrapText(true);
	    
	    Row row = null;
	    Cell cell = null;
	    
	    row = sheet1.createRow(0);
	    cell = row.createCell(0);
	    cell.setCellValue("단어");
	    cell.setCellStyle(cellStyle);
	    cell = row.createCell(1);
	    cell.setCellValue("감성");
	    cell.setCellStyle(cellStyle);
	    cell = row.createCell(2);
	    cell.setCellValue("평가");
	    cell.setCellStyle(cellStyle);
	    
	    for(int i=0;i<dictWord.size();i++)
	    {
	    	row = sheet1.createRow(i+1);
	    	cell = row.createCell(0);
		    cell.setCellValue(dictWord.get(i));
		    cell.setCellStyle(cellStyle);
	    	cell = row.createCell(2);
		    cell.setCellValue(dictValue.get(i));
		    cell.setCellStyle(cellStyle);
	    }
	    
	    File file = new File("c:\\resource\\dict.xls");
	    try
	    {
			FileOutputStream fileOut = new FileOutputStream(file);
			wb.write(fileOut);
			fileOut.close();
		}
	    catch (IOException e) {
			System.err.println(e);
		}
	    
	}
	
	/* 사전목록에 추가하는 메소드 */
	public void addDictionary(String word, int value)
	{
		dictWord.add(word);
		dictValue.add(value);
	}

	public String getJSON(int i, boolean pretty)
	{
		WordDataSet dataSet = getDataSet();
		JSONObject jobj = new JSONObject();
		jobj.put("sentence_no", i);
		jobj.put("text", dataSet.getSentences()[i]);
		
		if(dataSet.getSentenceValue(i)>0)
		{
			jobj.put("sentiment", "positive");
		}
		else if(dataSet.getSentenceValue(i)==0)
		{
			jobj.put("sentiment", "neutural");			
		}
		else
		{
			jobj.put("sentiment", "negative");
		}
		jobj.put("senti_value", dataSet.getSentenceValue(i));
		JSONArray arr = new JSONArray();

		for(int k=0;k<dataSet.getMorps(i).size();k++)
		{
			String aa = dataSet.getMorps(i).get(k);
			if(aa.split("\t").length<3)break;
		
			JSONObject jj = new JSONObject();
			jj.put("id", k);
			jj.put("morpheme", aa.split("\t")[0]);
			jj.put("polarity", aa.split("\t")[1]);
			jj.put("type", aa.split("\t")[2]);
			arr.add(jj);
		}
		jobj.put("morps", arr);

		JSONArray arr2 = new JSONArray();
		JSONObject sub;
		ArrayList<String> words;
		ArrayList<Integer> location;
		   
		for(int j=0;j<relations.get(i).size();j++)
		{
			words = new ArrayList<String>();
			location = new ArrayList<Integer>();
			sub = new JSONObject();
			String targetString = relations.get(i).get(j);
			words.add(targetString.split(",")[0]);
			words.add(targetString.split(",")[1]);
			location.add(Integer.parseInt(targetString.split(",")[2]));
			location.add(Integer.parseInt(targetString.split(",")[3]));
			sub.put("id",j);
			sub.put("words",words);
			sub.put("location",location);
			sub.put("polarity",targetString.split(",")[4]);
			arr2.add(sub);
		}
		   
		jobj.put("relations",arr2);

		if(pretty==true) 
		{
			return JsonUtil.getPretty(jobj.toString());
		}
		else
		{
			return jobj.toString();
		}
	}

	public void addRelation(int i, String relation)
	{
		relations.get(i).add(relation);
	}
	
	class WordDataSet
	{
		String jsonFileName;
		String textFileName;
		String contextFileName;
		String[] sentences;
		
		public WordDataSet(String textFileName, String contextFileName)
		{
			this.textFileName = textFileName;
			this.contextFileName = contextFileName;
			sentences = arr;
		}
		
		public String getJSONFilename()
		{
			return filename.split("text")[0];
		}
		public String getTextFileName()
		{
			return textFileName;
		}
		public String getContextFileName()
		{
			return contextFileName;
		}
	
		public String[] getSentences()
		{
			return sentences;
		}
		public int getSentenceValue(int idx)
		{
			return getTextValue(idx);
		}
		public ArrayList<String> getMorps(int i)
		{
			ArrayList<String> v = new ArrayList<String>();
			
			for(int j=1;j<ss_arr.get(i).length;j++)
			{
				v.add(ss_arr.get(i)[j]);
			}
			
			return v;
		}
	}

	public class Dictionary
	{
		ArrayList<String> dictWord;
		ArrayList<Integer> dictValue;
		
		public Dictionary(ArrayList<String> dictWord, ArrayList<Integer> dictValue)
		{
			this.dictWord = dictWord;
			this.dictValue = dictValue;
		}
		
		
		public void setValue(String word, int value)
		{
			dictValue.set(search(word), value);
		}
		
		public ArrayList<String> getWord()
		{
			return dictWord;
		}
		
		public ArrayList<Integer> getValue()
		{
			return dictValue;
		}
	
		public void printAllData()
		{
			for(int i=0;i<dictWord.size();i++)
			{
				System.out.println("단어 : "+dictWord.get(i)+"\t값 : "+dictValue.get(i));
			}
		}
		
		public int search(String str)
		{
			for(int i=0;i<dictWord.size();i++)
			{
				if(dictWord.get(i).equals(str)==true)
				{
					return i;			
				}
			}
			return -1;
		}
	}
	
	public void setDictionary(ArrayList<String> dictWord, ArrayList<Integer> dictValue)
	{
		dict = new Dictionary(dictWord, dictValue);
		this.dictWord = dict.getWord();
		this.dictValue = dict.getValue();
		
	}
	
	public Dictionary getDictionary()
	{
		return new Dictionary(dictWord,dictValue);
	}
	
	public WordDataSet getDataSet()
	{
		return new WordDataSet(filename, filename+".ma.context");
	}

	public void ibelievecurrentcomputingpower()
	{
		// 본문이 arr, ss_arr이 형태소들, 현재 둘 간 인덱스는 일치
		// 내가 context파일에 토큰정보를 집어넣어도 인덱스는 변화 없다
		// tmp대화상자 규칙깨지않으려면 newData()에서 처리를 끝내고 ss_arr에 토큰값 제외한 형태소들을 넣어주면 됨
		int index = 0;
		String context = filename+".ma.context";
		boolean isMorp = false;
		int morpCount = 0;
		WordDataSet collabo = getDataSet();
		int eojeolCount = 0;
		Eojeol e = new Eojeol("");
		Sentence sentence = new Sentence(collabo.getSentences()[index]);
		try
		{
			BufferedReader buf = new BufferedReader(new InputStreamReader(new FileInputStream(context),"UTF-8"));
			
			String processing = "";
			
			while(true)
			{
				processing = buf.readLine();
				if(processing==null)break;
				
				if(processing.equals("line"))
				{
					/* 다음 문장 처리 준비 */
					if(morpCount!=0)
					{
						e.setId(eojeolCount);
						sentence.addEojeol(e);
						eojeolCount++;
					}
					e = new Eojeol("");
					morpCount = 0;
					isMorp = false;
					sentence.setId(index);
					sentence.setPolarity(collabo.getSentenceValue(index));
					sentences.add(sentence);
					eojeolCount = 0;
					index++;
					if(index==collabo.getSentences().length)return;
					sentence = new Sentence(collabo.getSentences()[index]);
					continue;
				}
				else if(processing.contains("TOKEN"))
				{
					/* 어절임을 인식하고 처리한 후
					 * isMorp를 형태소처리 스위치로 바꿈
					 * isMorp가 true면 형태소 처리, 아니면 continue
					 *  */
					
					e.setText(processing.split(": ")[1]);
					isMorp = true;
					continue;
				}
				else if(processing.contains("fToken"))
				{
					if(morpCount!=0)
					{
						e.setId(eojeolCount);
						sentence.addEojeol(e);
						eojeolCount++;
					}
					e = new Eojeol("");
					morpCount = 0;
					isMorp = false;
					continue;
				}
				
				if(isMorp)
				{
					/* morpCount를 세서 없는 친구는 어절에서 제외시킴
					 * 본문     극성     품사
					 * */
					String[] infos = processing.split("\t");
					
					if(infos.length<3)continue;
					
					Morpheme m = new Morpheme(infos[0]);
					m.setEGroupId(eojeolCount);
					m.setId(morpCount);
					m.setPolarity(Integer.parseInt(infos[1]));
					m.setType(infos[2]);
					e.addMorp(m);
					morpCount++;
				}
			}
		}
		catch(Exception e1)
		{
			e1.printStackTrace();
		}
		
	}
}
