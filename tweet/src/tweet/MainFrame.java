package tweet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTextArea;

import org.apache.poi.ss.usermodel.Workbook;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Font;

import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTextArea fileName;
	private JTextArea dictFile;
	String fName, dFile;
	JTextArea textArea;
	String sent;
	MNoun analyzer;
	private JTextArea textField;
	public MainFrame(Boolean flag)
	{
		fName = null;
		analyzer=null;
		dFile = null;
		setTitle("\uBB38\uC7A5\uAC10\uC131\uBD84\uC11D\uAE30");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(762,639);
		
		JPanel panel_10 = new JPanel();
		getContentPane().add(panel_10, BorderLayout.NORTH);
		panel_10.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_10.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_9 = new JPanel();
		panel_3.add(panel_9, BorderLayout.NORTH);
			
		panel_9.setLayout(new BorderLayout(0, 0));
		JLabel lblNewLabel_1 = new JLabel("FILE Analyze (Click here to see HELP)");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				new FHelpDlg();
			}
		});
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Consolas", Font.PLAIN, 29));
		panel_9.add(lblNewLabel_1, BorderLayout.NORTH);
		
		
		JPanel panel = new JPanel();
		panel_3.add(panel);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel(" Filename   ");
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 20));
		panel_1.add(lblNewLabel, BorderLayout.WEST);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.add(panel_14, BorderLayout.CENTER);
		panel_14.setLayout(new GridLayout(0, 1, 0, 0));
		
	
		fileName = new JTextArea();
		panel_14.add(fileName);
		
		fileName.setColumns(10);
		fileName.setEditable(false);
		
		JButton btnNewButton_8 = new JButton("Select");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fDlg = new FileDialog(MainFrame.this);
				fDlg.setDirectory("c:\\resource\\");
				fDlg.show();
				fileName.setText(fDlg.getFile());
			}
		});
		btnNewButton_8.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_1.add(btnNewButton_8, BorderLayout.EAST);
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JLabel lblDictionary = new JLabel(" Dictionary ");
		lblDictionary.setFont(new Font("Consolas", Font.PLAIN, 20));
		panel_2.add(lblDictionary, BorderLayout.WEST);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.add(panel_15, BorderLayout.CENTER);
		panel_15.setLayout(new GridLayout(1, 0, 0, 0));
		
		dictFile = new JTextArea();	
		dictFile.setText("dict.xls");
		panel_15.add(dictFile);
		dictFile.setColumns(10);
		dictFile.setEditable(false);		
		
		JButton btnNewButton_9 = new JButton("Select");
		btnNewButton_9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileDialog fDlg = new FileDialog(MainFrame.this);
				fDlg.setDirectory("c:\\resource\\");
				fDlg.show();
				dictFile.setText(fDlg.getFile());
			}
		});
		btnNewButton_9.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_2.add(btnNewButton_9, BorderLayout.EAST);
		
		JButton btnNewButton = new JButton("Analyze");
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 15));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(fileName.getText().length()==0||fileName.getText().length()==0)
				{
					JOptionPane.showMessageDialog(getContentPane(), "Please check the file name","Warning",JOptionPane.ERROR_MESSAGE);					
				}
				else
				{
					fName = "c:\\resource\\";
					dFile = "c:\\resource\\";
					fName += fileName.getText();
					dFile += dictFile.getText();
					
					if(new File(fName).isFile()==false)
					{
						JOptionPane.showMessageDialog(getContentPane(), "Please check the file name","Warning",JOptionPane.ERROR_MESSAGE);
						return;
					}
					else if(new File(dFile).isFile()==false)
					{
						JOptionPane.showMessageDialog(getContentPane(), "Please check the dictionary file name","Warning",JOptionPane.ERROR_MESSAGE);
						return;
					}
					new Thread(new Runnable(){
						public void run()
						{
							analyze();
						}
					}).start();
					
					fileName.setText("");
					dictFile.setText("");
				}
			}
		});
		panel_3.add(btnNewButton, BorderLayout.EAST);
		
		JPanel panel_11 = new JPanel();
		panel_10.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JButton btnNewButton_6 = new JButton("Analyze");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(textField.getText().length()==0)
				{
					JOptionPane.showMessageDialog(getContentPane(), "Please input sentence more than 1 word","Warning",JOptionPane.ERROR_MESSAGE);
					return;
				}
				new Thread(new Runnable(){
					public void run()
					{
						sAnalyze();
					}
				}).start();	
			}
		});
		btnNewButton_6.setFont(new Font("Consolas", Font.PLAIN, 15));
		panel_11.add(btnNewButton_6, BorderLayout.EAST);
		
		JPanel panel_13 = new JPanel();
		panel_11.add(panel_13, BorderLayout.CENTER);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_13.add(panel_12);
		panel_12.setLayout(new BorderLayout(0, 0));
		
		JLabel lblSentence = new JLabel(" Sentence   ");
		lblSentence.setFont(new Font("Consolas", Font.PLAIN, 20));
		panel_12.add(lblSentence, BorderLayout.WEST);
		
		textField = new JTextArea();
		textField.setColumns(10);
		JScrollPane scr = new JScrollPane(textField);
		panel_12.add(scr);
		
		JLabel lblSentenceAnalyzeclick = new JLabel("Sentence Analyze (Click here to see HELP)");
		lblSentenceAnalyzeclick.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				new FHelpDlg();
			}
		});
		lblSentenceAnalyzeclick.setHorizontalAlignment(SwingConstants.CENTER);
		lblSentenceAnalyzeclick.setFont(new Font("Consolas", Font.PLAIN, 29));
		panel_11.add(lblSentenceAnalyzeclick, BorderLayout.NORTH);
		
		
		JPanel panel_5 = new JPanel();
		getContentPane().add(panel_5, BorderLayout.SOUTH);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnNewButton_1 = new JButton("Detail & Modify");
		btnNewButton_1.setFont(new Font("Consolas", Font.PLAIN, 18));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(analyzer==null)
				{
					JOptionPane.showMessageDialog(getContentPane(), "There is no data to show","Warning",JOptionPane.ERROR_MESSAGE);
				}
				else
				{
					analyzer.setVisible(true);
				}
			}
		});
		panel_5.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Show stats");
		btnNewButton_2.setFont(new Font("Consolas", Font.PLAIN, 18));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(analyzer!=null)
				{
					new Thread(new Runnable(){
						public void run()
						{
							new Printer(analyzer.getManager());	
						}
					}).start();
				}
				else
				{
					JOptionPane.showMessageDialog(getContentPane(), "There is no data to show","Warning",JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		panel_5.add(btnNewButton_2);
		
		JPanel panel_8 = new JPanel();
		getContentPane().add(panel_8, BorderLayout.CENTER);
		panel_8.setLayout(null);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(220, 39, 1, 1);
		panel_8.add(panel_4);
		panel_4.setLayout(null);
					
					JPanel panel_6 = new JPanel();
					panel_6.setBounds(635, 20, 100, 350);
					panel_8.add(panel_6);
					panel_6.setLayout(new GridLayout(0, 1, 0, 0));
					
					JButton btnNewButton_3 = new JButton(".txt");
					btnNewButton_3.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(fName!=null)
							{
								Runtime rt = Runtime.getRuntime();
								try
								{
									rt.exec("notepad.exe "+(fName+".txt"));
								}
								catch (IOException er)
								{
									er.printStackTrace();
								}
							}
						}
					});
					btnNewButton_3.setFont(new Font("Consolas", Font.PLAIN, 15));
					panel_6.add(btnNewButton_3);
					
					JButton btnNewButton_4 = new JButton(".ma");
					btnNewButton_4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(fName!=null)
							{
								Runtime rt = Runtime.getRuntime();
								try
								{
									rt.exec("notepad.exe "+fName+".txt.ma");
								}
								catch (IOException er)
								{
									er.printStackTrace();
								}
							}
						}
					});
					btnNewButton_4.setFont(new Font("Consolas", Font.PLAIN, 15));
					panel_6.add(btnNewButton_4);
					
					JButton btnNewButton_5 = new JButton(".context");
					btnNewButton_5.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							if(fName!=null)
							{
								Runtime rt = Runtime.getRuntime();
								try
								{
									rt.exec("notepad.exe "+fName+".txt.ma.context");
								}
								catch (IOException er)
								{
									er.printStackTrace();
								}
							}
						}
					});
					btnNewButton_5.setFont(new Font("Consolas", Font.PLAIN, 15));
					panel_6.add(btnNewButton_5);
					
					JButton btnNewButton_7 = new JButton("dict");
					btnNewButton_7.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							if(analyzer!=null)
							{
								new DictDlg(analyzer.getManager().getDictionary());
							}
							else
							{
								JOptionPane.showMessageDialog(getContentPane(), "Please try again after the analysis","Warning",JOptionPane.ERROR_MESSAGE);
							}
						}
					});
					btnNewButton_7.setFont(new Font("Consolas", Font.PLAIN, 15));
					panel_6.add(btnNewButton_7);
					
							JPanel panel_7 = new JPanel();
							textArea = new JTextArea();
							textArea.setFont(new Font("굴림체", Font.PLAIN, 13));
							panel_7.add(textArea);
							
								JScrollPane scroll = new JScrollPane(panel_7);
								scroll.setBounds(12, 20, 610, 350);
								panel_8.add(scroll);
								panel_7.setLayout(new GridLayout(0, 1, 0, 0));
		setVisible(flag);
	}

	public void dotGenerate(boolean flag)
	{
		int dotIdx = 0;
		int len = 0;
		BufferedReader fIn;
		BufferedWriter fOut;
		String fileName="";
		String outputFileName="";
		if(flag==true)
		{
			fileName=fName+".text";
			outputFileName=fName+".txt";
		}
		else
		{
			fileName="c:\\resource\\tmp.json.text";
			outputFileName="c:\\resource\\tmp.json.txt";
		}
		
		try
		{
			fIn = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
			fOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName),"UTF-8"));
			
			String str="";
			
			while(true)
			{
				str = fIn.readLine();
				if(str==null)break;
				
				if(str.contains(":"))
				{
					str = str.replace(":", "");
				}
				
				if(str.contains("["))
				{
					str = str.replaceAll("[\\[\\]]", "");
				}
				
				int chk = 0;
				for(int j=0;j<str.length();j++)
				{
					if(str.charAt(j)==' ')
					{
						chk++;
					}
				}
				
				if(chk==str.length())
				{
					continue;
				}
				
				if(str.contains("."))
				{
					dotIdx = str.indexOf(".");
					len = str.length();
					if(dotIdx!=len-1)
					{
						if((str.charAt(str.indexOf(".")+1)=='.'||str.charAt(str.indexOf(".")-1)=='.'))
						{
							fOut.write(str);
							fOut.newLine();
							fOut.flush();
						}
						else
						{
							String[] tmp = str.split("\\.");
							for(int i=0;i<tmp.length;i++)
							{
								int chk2 = 0;
								for(int j=0;j<tmp[i].length();j++)
								{
									if(tmp[i].charAt(j)==' ')
									{
										chk2++;
									}
								}
								
								if(chk2==tmp[i].length())
								{
									continue;
								}
								
								if(!tmp[i].equals("\n"))
								{
									fOut.write(tmp[i]);
									fOut.newLine();
									fOut.flush();
								}
							}
						}
					}
				}
				else
				{
					fOut.write(str);
					fOut.newLine();
					fOut.flush();
				}
			}
		}
		catch(IOException e)
		{
			System.err.println(e);
		}
	}
	public void analyze()
	{
		textArea.setEditable(true);
		textArea.setText("");
		ArrayList<String> list = new ArrayList<String>();
		JSONObject jObj;
		long start, finish;
		String fileName="";
		String outputFileName;
		String str="";
		String outStr="";
		String[] tmp;
		String dicName;
		BufferedReader fIn;
		BufferedWriter fOut;
		
		fileName += fName;
		dicName = dFile;
		
		outputFileName = fileName+".text";
		/* json -> text 추출 
		 * RT(리트윗), URL, #(해쉬태그), @사용자아이디 제거 한 순수 text만 저장
		 * */
		try
		{
			fIn = new BufferedReader(new InputStreamReader(new FileInputStream(fileName)));
			fOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName),"UTF-8"));
			
			while(true)
			{
				str = fIn.readLine();
				
				if(str==null)break;
				if(str.contains("{\"")==false)continue;
				if(str.contains("RT"))continue;		  
				jObj = (JSONObject)JSONValue.parse(str.toString());
				str="";
				
				outStr = jObj.get("text").toString();
				outStr = outStr.replaceAll("\n", "");
				
				if(outStr.contains("http"))	// http 제거
				{
					tmp = outStr.split(" ");
					for(int i=0;i<tmp.length;i++)
					{
						if(tmp[i].contains("http"))
						{
							tmp[i] = "";
						}
					}
					outStr="";
					for(int i=0;i<tmp.length;i++)
					{
						outStr+=tmp[i]+" ";
					}
				}
				
				if(outStr.contains("#"))  // 해쉬태그 제거
				{
					tmp = outStr.split(" ");
					for(int i=0;i<tmp.length;i++)
					{
						if(tmp[i].contains("#"))
						{
							tmp[i] = "";
						}
					}
					outStr="";
					for(int i=0;i<tmp.length;i++)
					{
						outStr+=tmp[i]+" ";
					}
				}
				
				if(outStr.contains("@"))  // 사용자id 제거
				{
					tmp = outStr.split(" ");
					for(int i=0;i<tmp.length;i++)
					{
						if(tmp[i].contains("@"))
						{
							tmp[i] = "";
						}
					}
					outStr="";
					for(int i=0;i<tmp.length;i++)
					{
						outStr+=tmp[i]+" ";
					}
				}
				if(outStr.equals("!")||outStr.equals("@")||
						outStr.equals("#")||outStr.equals("$")||
						outStr.equals("%")||outStr.equals("^")||
						   outStr.equals("*")||outStr.equals("&")||
						   outStr.equals("(")||outStr.equals(")")||
						   outStr.equals("-"))continue;

				list.add(outStr);
				fOut.write(outStr);
				fOut.newLine();
				fOut.flush();

				outStr = "";
			}			
			fOut.flush();
			fIn.close();
			fOut.close();
		}
		catch(IOException e){System.err.println(e);return;}
		finally
		{
			textArea.append("========================================================="+"\n");
			textArea.append("Start extracting text from source data\n"+"\n");
			textArea.append("Input : "+fileName+"\n");
			textArea.append("Output : "+fName+"\n");
			textArea.append("Finish\n"+"\n");
		}
		dotGenerate(true);
		textArea.append("Start Morpheme analysis\n"+"\n");
		textArea.append("Input : "+outputFileName+"\n");
		textArea.append("Output : "+outputFileName+".ma"+"\n");
		new cmdTest(textArea, fileName);
		textArea.append("Finish\n"+"\n");
		textArea.append("Start the sentence sensitivity determination"+"\n");
		textArea.append("Input  : "+outputFileName+".ma"+"\n");
		textArea.append("Output : "+outputFileName+"ma.nounContext"+"\n");
		textArea.append("          "+outputFileName+"ma.context"+"\n");
		
		start = System.currentTimeMillis();
		outputFileName = fileName+".txt";
		NounExtraction extractor = new NounExtraction(outputFileName+".ma",dicName);
		extractor.extraction();
		
		finish = System.currentTimeMillis();
		textArea.append("Runtime : "+(finish-start)/1000+"sec"+"\n");
		textArea.append("Finish"+"\n");
		textArea.append("=========================================================");
		analyzer = new MNoun(fName, dicName, extractor.getParser());
		textArea.setEditable(false);
	}
	
	public void sAnalyze()
	{
		textArea.setEditable(true);
		textArea.setText("");
		long start, finish;
		
		String outputFileName;
		String dicName;
		
		String sentence="";
		String[] outBuff;
		BufferedWriter fOut;
	
		dicName = "c:\\resource\\dict.xls";
		outputFileName = "c:\\resource\\tmp.json.text";
	
		sentence = textField.getText();
		System.out.println("입력한 문장 : "+sentence);
		textField.setText("");
		outBuff = sentence.split("\n");
			try
		{
			fOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFileName),"UTF-8"));
			for(int i=0;i<outBuff.length;i++)
			{
				fOut.write(outBuff[i]);
				fOut.newLine();
				fOut.flush();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
		dotGenerate(false);
		textArea.append("Start Morpheme analysis\n"+"\n");
		textArea.append("Input : "+outputFileName+"\n");
		textArea.append("Output : "+outputFileName+".ma"+"\n");
		new cmdTest(textArea, "c:\\resource\\tmp.json");
		textArea.append("Finish\n"+"\n");
		outputFileName = "c:\\resource\\tmp.json.txt";
		textArea.append("Start the sentence sensitivity determination"+"\n");
		textArea.append("Input  : "+outputFileName+".ma"+"\n");
		textArea.append("Output : "+outputFileName+"ma.nounContext"+"\n");
		textArea.append("          "+outputFileName+"ma.context"+"\n");
		start = System.currentTimeMillis();
		NounExtraction extractor = new NounExtraction(outputFileName+".ma",dicName);
		extractor.extraction();
		finish = System.currentTimeMillis();
		textArea.append("Runtime : "+(finish-start)/1000+"sec"+"\n");
		textArea.append("Finish"+"\n");
		analyzer = new MNoun("c:\\resource\\tmp.json",dicName,extractor.getParser());
		textArea.append("=========================================================\n");
		textArea.append("Result\n");
		textArea.append("Sentence\t\tValue\t\tEmotion\n");
		String[] tmp = analyzer.getManager().getDataSet().getSentences();
		for(int i=0;i<tmp.length;i++)
		{
			textArea.append(tmp[i]);
			if(tmp[i].length()<5)
			{
				textArea.append("\t");
			}
			textArea.append("\t\t"+analyzer.getManager().getDataSet().getSentenceValue(i)+"\t\t");
			if(analyzer.getManager().getDataSet().getSentenceValue(i)>0)
			{
				textArea.append("Positive\n");
			}
			else if(analyzer.getManager().getDataSet().getSentenceValue(i)==0)
			{
				textArea.append("Neutral\n");
			}
			else
			{
				textArea.append("Negative\n");
			}
		}
		textArea.append("=========================================================\n");
		
		textArea.setEditable(false);
		
		
	}
}
