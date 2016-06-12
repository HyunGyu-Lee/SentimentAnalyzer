package tweet;

import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import tweet.DataManager.Dictionary;

import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.GridLayout;

import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.BoxLayout;

import java.awt.Component;

import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class DictDlg extends JDialog{

	private static final long serialVersionUID = 1L;
	
	Dictionary dict;
	JTextArea textArea, textArea_1;
	private JTextField textField;
	public DictDlg(Dictionary dict)
	{
		setTitle("Dictionary");
		this.dict = dict;
		setSize(449,449);
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel_2 = new JLabel("Search");
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()==KeyEvent.VK_ENTER)
				{
					if(textField.getText().length()!=0)
					{
						String searchStr = textField.getText();
						String resultStr;
						int searchIdx = dict.search(searchStr);
						if(searchIdx==-1)
						{
							resultStr = searchStr+" is not exist in Dictionary";
						}
						else
						{
							resultStr = searchStr+"    :    "+dict.getValue().get(searchIdx);
						}
						JOptionPane.showMessageDialog(getContentPane(), resultStr);
							
					}
				}
				textField.setText("");
			}
		});
		panel_2.add(textField);
		textField.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new GridLayout(0, 2, 0, 0));
		
		JLabel lblNewLabel = new JLabel("Word");
		panel_3.add(lblNewLabel);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblNewLabel_1 = new JLabel("Value");
		panel_3.add(lblNewLabel_1);
		lblNewLabel_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		
		JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.CENTER);
		panel_1.setLayout(new GridLayout(0, 1, 0, 0));
		
		
		
		textArea = new JTextArea();
		JScrollPane s1, s2;
		s1 = new JScrollPane(textArea);
		
		panel_1.add(s1);
		
		splitData();
		setVisible(true);
	}
	
	public void splitData()
	{
		textArea.setText("");
		for(int i=0;i<dict.getWord().size();i++)
		{
			if(i==dict.getWord().size()-1)
			{
				textArea.append("\t"+dict.getWord().get(i));
				if(dict.getWord().get(i).length()>8)
				{
					textArea.append("\t");
				}
				else
				{
					textArea.append("\t\t");
				}
				textArea.append(dict.getValue().get(i)+"");
				
			}
			else
			{
				textArea.append("\t"+dict.getWord().get(i));
				if(dict.getWord().get(i).length()>8)
				{
					textArea.append("\t");
				}
				else
				{
					textArea.append("\t\t");
				}
				textArea.append(dict.getValue().get(i)+"\n");
			}
		}
	}
}
