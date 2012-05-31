package code;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.io.FilenameUtils;

import java.awt.Color;

public class LessonPanel extends JPanel {
    private JButton learnLessonButton,testLessonButton,deletePhraseButton,addPhraseButton;
	private JTextField chineseTextField,englishTextField;
    private DefaultTableModel tableModel;
	private JScrollPane phraseScrollPanel;
    private JTable phraseTable;
	private JLabel chineseLabel,englishLabel;
    private int sum_phrase;
	
	public LessonPanel(){
    	super();
    	setLayout(null);
    	setBackground(new Color(0,0,0,0));
    	setOpaque(false);
    	setBounds(500,40,400,600);
        initLearnLessonButton();
        initTestLessonButton();
        initPhraseScrollPanel();
        initPhraseTable();
        initAddPhrase();
        initDeletePhraseButton();
        sum_phrase = 0;
	}
	
	private void initLearnLessonButton(){
		learnLessonButton = new JButton("Learning");
		learnLessonButton.setBounds(0, 0, 100, 30);
		add(learnLessonButton);
	}
	
	private void initTestLessonButton(){
		testLessonButton = new JButton("Test");
		testLessonButton.setBounds(110, 0, 70, 30);
		add(testLessonButton);
	}
	
	private void initPhraseScrollPanel(){
		phraseScrollPanel = new JScrollPane();
		phraseScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    phraseScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		phraseScrollPanel.setBounds(0, 50, 400, 450);
	    add(phraseScrollPanel);
	}
	
	private void initPhraseTable(){
		 tableModel = new DefaultTableModel(0, 5);
		 phraseTable = new JTable();
		 phraseTable.setModel(tableModel);
		 phraseTable.setRowHeight(40);
		 phraseTable.setBounds(0, 0, 400, 450);
		 phraseScrollPanel.setViewportView(phraseTable);
	}
	
	private void initAddPhrase(){
		 chineseLabel = new JLabel("中文");
		 chineseLabel.setBounds(30, 530, 60, 30);
		 add(chineseLabel);
		 
		 englishLabel = new JLabel("English");
		 englishLabel.setBounds(30, 560, 60, 30);
	     add(englishLabel);
	     
	     chineseTextField = new JTextField();
	     chineseTextField.setBounds(90,530,250,30);
	     add(chineseTextField);
	     
	     englishTextField = new JTextField();
	     englishTextField.setBounds(90,560,250,30);
	     add(englishTextField);
	     
	     addPhraseButton = new JButton("Add");
	     addPhraseButton.setBounds(340,560,60,30);
	     add(addPhraseButton);
	} 
	
	private void initDeletePhraseButton(){
		deletePhraseButton = new JButton("Delete");
		deletePhraseButton.setBounds(0, 500, 70, 30);
		add(deletePhraseButton);
	}
	
	public void addPhrase(String chinese, String english, String date, String accuracy){
		 sum_phrase++;
		    
		 DefaultTableModel newTableModel = new DefaultTableModel(sum_phrase,5);
		 for(int i=0;i<sum_phrase-1;i++)
			for(int j=0;j<5;j++){
		        newTableModel.setValueAt(tableModel.getValueAt(i, j), i, j);
			}
		 
		 phraseTable.setModel(newTableModel);
		 tableModel = newTableModel;
		    
		 phraseTable.setValueAt(chinese, sum_phrase-1, 0);
		 phraseTable.setValueAt(english, sum_phrase-1, 1);
		 phraseTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/VoiceButton.png")), sum_phrase-1, 2);
		 phraseTable.setValueAt(date, sum_phrase-1, 3);
	     phraseTable.setValueAt(accuracy, sum_phrase-1, 4);
		 
		 phraseTable.getColumn("C").setCellEditor(phraseTable.getDefaultEditor(Icon.class));
		 phraseTable.getColumn("C").setCellRenderer(phraseTable.getDefaultRenderer(Icon.class));	 
	}
	
	public JTextField getChineseTextField(){
		return chineseTextField;
	}
	
	public JTextField getEnglishTextField(){
		return englishTextField;
	}
	
	public JButton getLearnButton(){
		return learnLessonButton;
	}
	
	public JButton getTestButton(){
		return testLessonButton;
	}
	
	public JButton getAddButton(){
		return addPhraseButton;
	}
	
	public JButton getDeleteButton(){
		return deletePhraseButton;
	}
	
	public static void main(String args[]){
	    MainFrame test = new MainFrame();
   	    LessonPanel p = new LessonPanel();
   	    test.getContentPane().add(p);
   	    p.addPhrase("你好", "hi", "05/21", "80%");
   	    p.addPhrase("你好衰", "hello", "05/21", "100%");
   	    test.setVisible(true);
    }
}
