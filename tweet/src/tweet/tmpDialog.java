package tweet;

import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.border.LineBorder;

import structures.Morpheme;
import structures.Relation;
import structures.Sentence;

import java.awt.Color;

public class tmpDialog extends JDialog{


	private static final long serialVersionUID = 1L;
	
	DataManager loader;
	int currentIdx;
	int column;
	JPanel c_center;
	@SuppressWarnings("rawtypes")
	JComboBox[] comboBox;
	JLabel[] label;
	JPanel magnetic;
	int popup[];
	HashMap<Integer, Integer> mapTable;
	Sentence sentence;
	
	public tmpDialog(DataManager loader, int currentIdx)
	{
		sentence = loader.sentences.get(currentIdx);
		mapTable = new HashMap<Integer, Integer>();
		popup = new int[column];
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("\uB370\uC774\uD130\uC218\uC815");
		this.setLocation(740, 0);
		this.loader = loader;
		this.currentIdx = currentIdx;
		column = sentence.portable().length;

		JPanel center = new JPanel();
		getContentPane().add(center, BorderLayout.CENTER);
		center.setLayout(new BorderLayout(0, 0));
		
		c_center = new JPanel();
		center.add(c_center, BorderLayout.CENTER);
		c_center.setLayout(new GridLayout(0,2));
		JPanel c_east = new JPanel();
		center.add(c_east, BorderLayout.EAST);
		
		JPanel south = new JPanel();
		getContentPane().add(south, BorderLayout.SOUTH);
		south.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		south.add(panel);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.add(panel_1);
		
		JLabel lblNewLabel = new JLabel("\uADF9  \uC131");
		panel_1.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("굴림체",0,12));
		
		magnetic = new JPanel();
		panel.add(magnetic);
		
		JButton setBtn = new JButton("SET");
		south.add(setBtn);
		setBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*for(int i=0;i<column;i++)
				{
					loader.setData2_Value(currentIdx, mapTable.get(i), (Integer)comboBox[i].getSelectedItem());
					if(loader.getRefElement(currentIdx).get(i)!=null)loader.setData1_Value(currentIdx, loader.getRefElement(currentIdx).get(i), (Integer)comboBox[i].getSelectedItem());
				}*/
				JOptionPane.showMessageDialog(getContentPane(), "적용완료");
				setMagnetic();
			}
		});
		
		make_c_center();
		labelEvent();
		setSize(428+column*2,418+column*2);
		setVisible(true);	
	}
	
	public void make_c_center()
	{
		comboBox = new JComboBox[column];
		label = new JLabel[column];
		JPanel[] pan = new JPanel[column];
		/* label -> 단어 목록 
		 * comboBox -> 극성목록
		 * */
		String[] visible = translater(sentence.portable());

		for(int i=0;i<column;i++)
		{
			//System.out.println(i);
			pan[i] = new JPanel(new GridLayout(0,2));
			comboBox[i] = new JComboBox<Integer>(new Integer[]{2,1,0,-1,-2});
			comboBox[i].setSelectedItem(Integer.parseInt(visible[i].split("\t")[1]));
			comboBox[i].setFont(new Font("Consolus",0,15));
			comboBox[i].addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					
					String addStr="";
					for(int i=0;i<comboBox.length;i++)
					{
						if(comboBox[i].equals(e.getSource()))
						{
							addStr = label[i].getText()+" "+comboBox[i].getSelectedItem();
							sentence.finder(mapTable.get(i)).setPolarity((int)comboBox[i].getSelectedItem());
							if(JOptionPane.showConfirmDialog(getContentPane(), "사전에 추가하시겠습니까?\n"+addStr)==JOptionPane.OK_OPTION)
							{
								if(loader.getDictionary().search(addStr.split(" ")[0])==-1)
								{
									/*loader.addDictionary(addStr.split(" ")[0], Integer.parseInt(addStr.split(" ")[1]));
									loader.saveDictionary2();*/
								}
								else
								{/*
									loader.getDictionary().setValue(addStr.split(" ")[0], Integer.parseInt(addStr.split(" ")[1]));
									loader.addDictionary(addStr.split(" ")[0], Integer.parseInt(addStr.split(" ")[1]));
									loader.saveDictionary2();*/
								}
							}
							
						}
					}
				}
			});

			label[i] = new JLabel(visible[i].split("\t")[0]);
			label[i].setFont(new Font("굴림체",0,12));
			label[i].setHorizontalAlignment(SwingConstants.CENTER);
			pan[i].add(label[i]); pan[i].add(comboBox[i]);
			
			c_center.add(pan[i]);
		}
		setMagnetic();
	}
	
	public String[] translater(String[] logical)
	{
		String[] arr = logical;
		String[] translated = new String[arr.length];
		int key = arr.length/2;
		int tKey = key;
		switch(arr.length%2)
		{
		case 0 : 
			tKey-=1;
			for(int i=0 ; i<arr.length ; i++)
			{
				if(i==0||i==arr.length-1)
				{
					translated[i] = arr[i];
					mapTable.put(i, i);
					continue;
				}
				if(i<key)
				{
					translated[i+i] = arr[i];
					mapTable.put(i+i, i);
				}
				if(i>=key)
				{
					if(tKey<0)tKey=0;
					int idx = i-(tKey--);
					translated[idx] = arr[i];
					mapTable.put(idx, i);
				}
			}
			break;
		case 1 :
			for(int i=0 ; i<arr.length ; i++)
			{
				if(i==0)
				{
					translated[i] = arr[i];
					mapTable.put(i, i);
					continue;
				}
				if(i<=key)
				{
					translated[i+i] = arr[i];
					mapTable.put(i+i, i);
				}
				
				if(i>key)
				{
					if(tKey<0)tKey=0;
					int idx = i-(tKey--);
					translated[idx] = arr[i];
					mapTable.put(idx, i);
				}
			}
			break;
		}
		return translated;
	}
	
	public static String[] reducer(String[] original)
	{
		String[] arr = new String[original.length-1];

		for(int i=0;i<arr.length;i++)
		{
			arr[i] = original[i+1];
		}
		return arr;
	}	

	/* 클릭시 나오는 팝업메뉴 */
	class PopupTest extends JPopupMenu
	{
		private static final long serialVersionUID = 1L;
		JMenuItem[] items;
		String[] contents;
		String first;
		PopupTest(String[] contents, String first, int start)
		{
			int i;
			this.first = first;
			this.contents = contents;
			items = new JMenuItem[column];
			StringBuilder addString = new StringBuilder();
			for(i=start;i<column;i++)
			{
				items[i] = new JMenuItem(contents[i].split("\t")[0]);
				items[i].setFont(new Font("굴림체",Font.PLAIN,15));
				add(items[i]);
			}
			
			for(i=start;i<column;i++)
			{
				items[i].addActionListener(new ActionListener(){
				
				public void actionPerformed(ActionEvent e) {
						JMenuItem a = (JMenuItem) e.getSource();
						Morpheme m1 = null, m2 = null;
						for(int j=start;j<items.length;j++)
						{
							if(items[j].equals(a))
							{
								m1 = sentence.finder(start-1);
								m2 = sentence.finder(j);
								System.out.println(sentence.finder(start-1).getText());
								System.out.println(sentence.finder(j).getGroupId()+", "+sentence.finder(j).getId());
								addString.append(first).append(",").append(a.getText()).append(",").append(start-1).append(",").append(j);
								break;
							}
						}
						int value;
						String magnetic = "";
						while(true)
						{		
							magnetic += JOptionPane.showInputDialog("극성을 입력하세요 (-2 ~ 2)");
							if(magnetic.contains("null")||magnetic.length()==0)
							{
								return;
							}
							
							value = Integer.parseInt(magnetic);
							
							if(value<=2&&value>=-2)
							{
								break;
							}
						}
						addString.append(",").append(value);
						
						sentence.addRelations(new Relation(m1, m2, value));
						//loader.addRelation(currentIdx, addString.toString());
						
						JOptionPane.showMessageDialog(getContentPane(), "다음과 같이 의존관계가 추가됩니다.\n"+addString);
						/*loader.addDictionary(first+" "+a.getText(), value);
						loader.saveDictionary2();*/
				}					
			});
		}
	}
}
	
	public void labelEvent()
	{
		for(int i=0;i<column;i++)
		{
			label[i].addMouseListener(new MouseAdapter(){
				public void mousePressed(MouseEvent e)
				{
					JLabel la = (JLabel)e.getSource();
					int start=0;
					for(int i=0;i<column;i++)
					{
						if(label[i].equals(la))
						{
							start = i;
							break;
						}
					}
					new PopupTest(sentence.portable(),((JLabel)e.getSource()).getText(), mapTable.get(start)+1).show(e.getComponent(),e.getX(),e.getY());
				}
			});
		}
	}
	
	public void setMagnetic()
	{
		int mag = 0;
		for(int i=0;i<column;i++)
		{
			mag+=(Integer)comboBox[i].getSelectedItem();
		}

		if(mag>=4)
		{
			magnetic.setBackground(Color.GREEN);	
		}
		else if(mag>0&&mag<4)
		{
			magnetic.setBackground(Color.GREEN.brighter());
		}
		else if(mag==0)
		{
			magnetic.setBackground(Color.BLACK);
		}
		else if(mag<0&&mag>-4)
		{
			magnetic.setBackground(Color.RED.brighter());
		}
		else
		{
			magnetic.setBackground(Color.RED);			
		}		
	}

	
}
