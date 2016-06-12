package tweet;

import java.awt.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;

import structures.Eojeol;
import structures.Morpheme;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

public class MNoun extends JDialog{

	private static final long serialVersionUID = 1L;
	JLabel mainString;
	DataManager loader;
	int currentIdx;
	JPanel northPan, centerPan, panz, colorRef;
	MNoun me;
	JScrollPane panel_3;
	tmpDialog tmpz;
	String[] s_str;
	String fileName, dictName;
	JTextArea textArea; // 명사 컨텐츠
	MyParser parser;
	
	public MNoun(String fileName, String dictName, MyParser parser)
	{	
		this.parser = parser;
		this.dictName = dictName;
		this.fileName = fileName;
		me = this;
		currentIdx=0;
		loader = null;
		tmpz = null;
		setTitle("Detail");
		
		getContentPane().setLayout(new BorderLayout(10,10));
		setSize(740,519);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this_NorthPan();
		startUp();
	}
	
	public void startUp()
	{
		loader = new DataManager(fileName, dictName);
		loader.setDictionary(parser.getDictWord(), parser.getDictValue());
		splashData();
		
	}
	
	public void splashData()
	{
		mainString.setText(loader.sentences.get(currentIdx).getText());
		textArea.setText("");
		int cnt = 0;
		int value;
		
		for(Eojeol e : loader.sentences.get(currentIdx).getEojeols())
		{
			for(Morpheme m : e.getMorps())
			{
				if(m.getPolarity()!=0)
				{
					textArea.append(m.getText()+"\t"+m.getPolarity()+"\t");
					cnt++;
				}
				
				if(cnt==3)
				{
					textArea.append("\n");
					cnt=0;
				}
			}
		}
		
		selectColor(colorRef, loader.sentences.get(currentIdx).getPolarity());
		
	}
	
	public void this_NorthPan()
	{
		northPan = new JPanel();
		northPan.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		getContentPane().add(northPan,"North");
		northPan.setLayout(new GridLayout(0, 1, 0, 0));

		JLabel d = new JLabel("Modifyer");
		d.setFont(new Font("Consolas", Font.PLAIN, 30));
		northPan.add(d);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(42, 10, 150, 35);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Current Sentense");
		lblNewLabel.setBounds(0, 0, 150, 35);
		panel_2.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(42, 55, 655, 57);
		panel.add(panel_1);
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel_1.add(scrollPane);
		
		mainString = new JLabel("");
		scrollPane.setViewportView(mainString);
		mainString.setFont(new Font("굴림", Font.PLAIN, 12));
		
		JButton prevBtn = new JButton("Prev");
		prevBtn.setFont(new Font("Consolas", Font.PLAIN, 12));
		
		prevBtn.setBounds(240, 122, 65, 38);
		panel.add(prevBtn);
		
		JButton btnNext = new JButton("Next");
		btnNext.setFont(new Font("Consolas", Font.PLAIN, 12));
		/* PREV버튼 누를시 */
		prevBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loader==null)
				{
					JOptionPane.showMessageDialog(getContentPane(), "로드된 데이터가 없습니다.");
					return;
				}
				currentIdx--;
				if(currentIdx<0)
				{
					JOptionPane.showMessageDialog(me, "첫번째 문장입니다.", "경 고",JOptionPane.WARNING_MESSAGE);
					currentIdx = 0;
				}
				else
				{
					splashData();
				}
			}
		});
		/* NEXT버튼 누를시 */
		btnNext.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loader==null)
				{
					JOptionPane.showMessageDialog(getContentPane(), "로드된 데이터가 없습니다.");
					return;
				}
				
				currentIdx++;
				if(loader.getDataLength()==currentIdx)
				{
					JOptionPane.showMessageDialog(getContentPane(), "마지막 문장입니다.");
					currentIdx--;
					return;
				}
				splashData();
			}
		});
		btnNext.setBounds(421, 122, 65, 38);
		panel.add(btnNext);
		textArea = new JTextArea();
		
		panz = new JPanel();
		
		JLabel lblNewLabel_1 = new JLabel("단어      극성    ㅣ   단어       극성   ㅣ   단어       극성");
		lblNewLabel_1.setFont(new Font("굴림체",5,15));
		JScrollPane scr = new JScrollPane(textArea);
		panz.setLayout(new BorderLayout(0, 0));
		panz.add(lblNewLabel_1, BorderLayout.NORTH);
		panz.add(scr);
		
		
		panel_3 = new JScrollPane(panz);
		panz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel_3.setBounds(42, 186, 655, 98);
		
		panel.add(panel_3);
		
		JButton btnNewButton_1 = new JButton("Tagging");
		btnNewButton_1.setFont(new Font("Consolas", Font.PLAIN, 14));
		/*다이알로그 띄우기*/
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(loader==null)return;
				tmpz = new tmpDialog(loader, currentIdx);				
			}
		});
		btnNewButton_1.setBounds(317, 122, 97, 38);
		panel.add(btnNewButton_1);
		
		/* Backup 버튼 클릭시 */
		JButton btnNewButton_2 = new JButton("Backup");
		btnNewButton_2.setFont(new Font("Consolas", Font.PLAIN, 14));
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//new XMLParser(loader, userList,fileName);
				if(loader==null)
				{
					JOptionPane.showMessageDialog(getContentPane(), "백업할 데이터가 없습니다.");
					return;
				}
				new XMLParser(loader, fileName);
				loader.backup();
				JOptionPane.showMessageDialog(getContentPane(), "백업완료");
			}
		});
		btnNewButton_2.setBounds(240, 294, 214, 39);
		panel.add(btnNewButton_2);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(585, 10, 112, 33);
		panel.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Sentiment");
		lblNewLabel_2.setFont(new Font("Consolas", Font.PLAIN, 11));
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(lblNewLabel_2);
		
		colorRef = new JPanel();
		panel_4.add(colorRef);
		
		/* refresh버튼 클릭시 */
		JButton btnNewButton = new JButton("Refresh");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refresh();
			}
		});
		btnNewButton.setFont(new Font("Consolas", Font.PLAIN, 12));
		btnNewButton.setBounds(608, 153, 89, 23);
		panel.add(btnNewButton);
		
		JMenuBar menuBar = new JMenuBar();
		getContentPane().add(menuBar, BorderLayout.NORTH);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Load");
		/* 파일에서 데이터 불러오는 부분 */
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileName = "c:\\resource\\"+JOptionPane.showInputDialog("불러올 파일명을 입력하세요(확장자 제외)");
				if(fileName.contains("null"))return;
				if(!(new File(fileName+".json.text").isFile()))
				{
					JOptionPane.showMessageDialog(getContentPane(), "파일명을 확인하세요.");
					return;
				}
				loader = new DataManager(fileName,dictName);
				mainString.setText(loader.getData(currentIdx));
				s_str = loader.getData1(currentIdx);
				textArea.setText("");
				for(int i=1;i<s_str.length;i++)
				{
					textArea.append(s_str[i]+"\t");
					if(i%3==0)textArea.append("\n");
				}
				selectColor(colorRef, loader.getTextValue(currentIdx));
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Help");
		mntmNewMenuItem_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new HelpDlg();
			}
		});
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("XML");
		menuBar.add(mnNewMenu_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Open XML");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Runtime rt = Runtime.getRuntime();
				try
				{
					rt.exec("notepad.exe "+fileName+".xml");
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenu mnNewMenu_2 = new JMenu("View");
		menuBar.add(mnNewMenu_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Open Viewer");
		mntmNewMenuItem_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new Printer(loader);
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_3);
		
	}
	
	public void refresh()
	{
		if(loader==null)
		{
			return;
		}
		splashData();
	}
	
	public void selectColor(Component c,int value)
	{
		if(value<0)
		{
			c.setBackground(Color.RED);
		}
		else if(value==0)
		{
			c.setBackground(Color.BLACK);
		}
		else if(value>0)
		{
			c.setBackground(Color.GREEN);
		}
	}
	
	public void this_CenterPan()
	{
		centerPan = new JPanel();
		
		getContentPane().add(centerPan,"Center");
	}

	public DataManager getManager()
	{
		return loader;
	}
}
