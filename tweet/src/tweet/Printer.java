package tweet;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;

import java.awt.GridLayout;
import java.text.AttributedString;

import javax.swing.JScrollPane;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.PieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.Plot;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import tweet.DataManager.WordDataSet;

import javax.swing.JSplitPane;
import javax.swing.JLabel;

public class Printer extends JDialog{

	private static final long serialVersionUID = 1L;
	Statisticer st;
	DataManager manager;
	WordDataSet dataSet;
	JTextArea textArea, textArea_1, textArea_2, textArea_3;
	public Printer(DataManager manager)
	{
		this.setLocation(740, 0);
		this.manager = manager;
		st = new Statisticer(manager);
		dataSet = manager.getDataSet();
		
		setSize(1000,700);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panel.add(tabbedPane);
			
		JPanel panel_1 = new JPanel();
		panel_1.setLayout(new BorderLayout(0, 0));
		
		panel_1.add(makeAllChart());
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		
		splitPane.add(panel_1);
		JScrollPane scroll = new JScrollPane(makeInfoPanel());
		splitPane.add(scroll);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("All", null, splitPane, null);
		splitPane.setDividerLocation(590);
		
		JPanel sentence = new JPanel();
		tabbedPane.addTab("Sentence", null, sentence, null);
		sentence.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_3 = new JPanel();
		sentence.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("Positive sentence");
		panel_3.add(lblNewLabel, BorderLayout.NORTH);
		
		textArea = new JTextArea();
		JScrollPane scr1 = new JScrollPane(textArea);
		panel_3.add(scr1, BorderLayout.CENTER);
		
		JPanel panel_4 = new JPanel();
		sentence.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("Negative sentence");
		panel_4.add(lblNewLabel_1, BorderLayout.NORTH);
		
		textArea_1 = new JTextArea();
		JScrollPane scr2 = new JScrollPane(textArea_1);

		panel_4.add(scr2, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		sentence.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Neutral sentence");
		panel_5.add(lblNewLabel_2, BorderLayout.NORTH);
		
		textArea_2 = new JTextArea();
		JScrollPane scr3 = new JScrollPane(textArea_2);
		panel_5.add(scr3, BorderLayout.CENTER);
		
		JPanel json = new JPanel();
		tabbedPane.addTab("JSON", null, json, null);
		json.setLayout(new GridLayout(0, 1, 0, 0));
		
		textArea_3 = new JTextArea();
		new Thread(new Runnable(){
			public void run() {
				for(int i=0;i<manager.arr.length;i++)
				{
					textArea_3.append(manager.getJSON(i,true)+"\n");
				}
				json.add(new JScrollPane(textArea_3));
			}
			
		}).start();
		
		fillUpTextArea();
		setTitle("Stat Viewer");
		setVisible(true);
	}
	
	public ChartPanel makeAllChart()
	{
		/*  Legend => 범 례 */
		DefaultPieDataset data = new DefaultPieDataset();
		data.setValue("Positive", st.getPositive());
		data.setValue("Negative", st.getNegative());
		data.setValue("Neutral", st.getNuetral());	// Data 셋팅
		
		JFreeChart chart = ChartFactory.createPieChart3D("Statistics",data,true,true,false);  // 차트 생성
		chart.getTitle().setFont(new Font("Consolas", Font.PLAIN, 50));
		chart.getLegend().setItemFont(new Font("Consolas", Font.PLAIN, 20));
		
		PiePlot plot = (PiePlot) chart.getPlot();
		plot.setCircular(true);
		plot.setLabelGenerator(new PieSectionLabelGenerator() {
			
			@Override
			public String generateSectionLabel(PieDataset data, Comparable key) {
				String labelResult;
				labelResult = key.toString()+"("+Math.round(data.getValue(key).doubleValue()/st.getTotal()*100)+"%)";
				return labelResult;
			}
			
			@Override
			public AttributedString generateAttributedSectionLabel(PieDataset arg0,
					Comparable arg1) {
				return null;
			}
		});
		
		plot.setBackgroundPaint(Color.WHITE);
		plot.setLabelFont(new Font("굴림체",Font.PLAIN,20));
		
		plot.setSectionPaint(0, Color.GREEN);
		plot.setSectionPaint(2, Color.BLACK);
		plot.setSectionPaint(1, Color.RED);	// 플롯 설정
		
		ChartPanel cp = new ChartPanel(chart);	// 차트패널 설정
		
		return cp;
	}

	public JPanel makeInfoPanel()
	{
		
		JPanel infoPanel = new JPanel();
		infoPanel.setLayout(new BorderLayout());
		
		JTextArea ta = new JTextArea();
		ta.setFont(new Font("Consolas", Font.PLAIN, 15));
		ta.append("# Data for analyze\n");
		ta.append("Source Data: "+dataSet.getJSONFilename()+"\n");
		ta.append("Text : "+dataSet.getTextFileName()+"\n");
		ta.append("Context : "+dataSet.getContextFileName()+"\n\n\n");

		ta.append("# Stat Summary\n");
		ta.append("Total sentences      : "+(st.getN1Cnt()+st.getN2Cnt()+st.getPCnt())+"\n");
		ta.append("Positive sentences   : "+st.getPCnt()+"\n");
		ta.append("Negative sentences   : "+st.getN1Cnt()+"\n");
		ta.append("Neutral  sentences   : "+st.getN2Cnt()+"\n");
		
		infoPanel.add(ta, BorderLayout.CENTER);
		
		return infoPanel;
	}

	public void fillUpTextArea()
	{
		for(int i=0;i<st.getPosString().size();i++)
		{
			textArea.append((i+1)+" : "+st.getPosString().get(i)+"\n");
		}
		for(int i=0;i<st.getNegString().size();i++)
		{
			textArea_1.append((i+1)+" : "+st.getNegString().get(i)+"\n");
		}
		for(int i=0;i<st.getNeuString().size();i++)
		{
			textArea_2.append((i+1)+" : "+st.getNeuString().get(i)+"\n");
		}
	}
}
