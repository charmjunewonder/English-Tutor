
package code;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LearnFrame extends AbstractFrame {  
	  private JLabel titleLabel;  
	  private JPanel titlePanel,phrasePanel;
	  private JScrollPane phraseScrollPanel;
	  
	  public LearnFrame(){
		  super("LearnFrame.png");
		  getMainPanel().setLayout(new BorderLayout());
		  initTitleLabel();
	      initPhraseScrollPanel();
	  }  
	  
	  private void initTitleLabel(){
		  titlePanel = new JPanel();
		  titlePanel.setOpaque(false);
		  titlePanel.add(new JLabel());
		  titleLabel = new JLabel("Learning");
		  titlePanel.add(titleLabel);
		  titlePanel.add(new JLabel());
		  getMainPanel().add(titlePanel,BorderLayout.NORTH);
	  }
	  
	  private void initPhraseScrollPanel(){
		  phraseScrollPanel = new JScrollPane();
		  phraseScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		  phraseScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		  phraseScrollPanel.setPreferredSize(new Dimension(250,250));
		  phraseScrollPanel.setVisible(true);
		  phraseScrollPanel.getViewport().setOpaque(false);
		  phraseScrollPanel.setOpaque(false);
		  
		  phrasePanel = new JPanel();
		  phrasePanel.setLayout(new GridLayout(10,1));
		  phraseScrollPanel.setViewportView(phrasePanel);
		  
		  JPanel northPanel = new JPanel();
		  northPanel.setOpaque(false);
		  JPanel southPanel = new JPanel();
		  southPanel.setOpaque(false);
		  southPanel.add(new JLabel("  "));
		  southPanel.add(new JLabel("  "));
		  JPanel eastPanel = new JPanel();
		  eastPanel.setOpaque(false);
		  JPanel westPanel = new JPanel();
		  westPanel.setOpaque(false);
		  
		  getMainPanel().add(northPanel);
		  getMainPanel().add(southPanel,BorderLayout.SOUTH);
		  getMainPanel().add(eastPanel,BorderLayout.EAST);
		  getMainPanel().add(westPanel,BorderLayout.WEST);
		  getMainPanel().add(phraseScrollPanel,BorderLayout.CENTER);
	  }
	  
	  public void addPhraseItem(String chinese,String english){
		  PhraseItemPanel newPhrase = new PhraseItemPanel(chinese,english);
		  newPhrase.setOpaque(false);
		  phrasePanel.add(newPhrase);
	  }
	  
	  private class PhraseItemPanel extends JPanel{
		  private JLabel chineseLabel,englishLabel;
		  private JButton voiceButton;
		  
		  public PhraseItemPanel(String c,String e){
			  super();
			  setLayout(new GridLayout(1,5));
			  voiceButton = new JButton(new ImageIcon("VoiceButton.png"));
			  chineseLabel = new JLabel(c);
			  chineseLabel.setVisible(false);
			  englishLabel = new JLabel(e);
			  englishLabel.addMouseListener(new EnglishLabelAdapter());
			  
			  add(englishLabel);
			  add(new JLabel());
			  add(voiceButton);
			  add(new JLabel());
			  add(chineseLabel);
		  }
		  
		  private class EnglishLabelAdapter extends MouseAdapter{
			  public void mouseClicked(MouseEvent e){
				  chineseLabel.setVisible(true);
			  }
			  public void mousePressed(MouseEvent e){
				  chineseLabel.setVisible(true);
			  }
		  }
	  }
	  
	  public static void main(String args[]){
    	   LearnFrame test = new LearnFrame();
    	   test.setVisible(true);
    	   test.addPhraseItem("ÎÒ°®Äã", "I fuck you");
    	   test.addPhraseItem("½²ºÍÐ³","Watch AV");
      }
}
