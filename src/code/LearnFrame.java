
package code;

import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import org.apache.commons.io.FilenameUtils;

public class LearnFrame extends AbstractFrame {  
    private JLabel titleLabel;  
    private JPanel phrasePanel;
    private JButton finishButton;
    private JScrollPane phraseScrollPanel;
    private int sum;

    public LearnFrame(){
	super(FilenameUtils.separatorsToSystem("resource/Learn.png"));
	initTitleLabel();
	initPhraseScrollPanel();
	initFinishButton();
	sum=0;
    }  

    private void initTitleLabel(){
	titleLabel = new JLabel("Learning");
	titleLabel.setBounds(235,52,96,31);
	getContentPane().add(titleLabel);
    }

    private void initFinishButton(){
	finishButton = new JButton("Finish");
	finishButton.setBounds(489, 598, 50, 50);
	getContentPane().add(finishButton);
    }

    private void initPhraseScrollPanel(){
	phrasePanel = new JPanel();
	phrasePanel.setLayout(new GridLayout(10,1));
	phrasePanel.setOpaque(false);


	phraseScrollPanel = new JScrollPane();
	phraseScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	phraseScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
	phraseScrollPanel.setVisible(true);
	phraseScrollPanel.getViewport().setOpaque(false);
	phraseScrollPanel.setOpaque(false);
	phraseScrollPanel.setBorder(null);
	phraseScrollPanel.setViewportView(phrasePanel);

	phraseScrollPanel.setBounds(52, 107, 483, 450);
	getContentPane().add(phraseScrollPanel);
    }

    public void addPhraseItem(Phrase p){
	PhraseItemPanel newPhrase = new PhraseItemPanel(p);
	newPhrase.setOpaque(false);
	phrasePanel.add(newPhrase);
    }

    private class PhraseItemPanel extends JPanel{
	private JLabel chineseLabel,englishLabel;
	private JButton voiceButton;
	private Phrase phrase;

	public PhraseItemPanel(Phrase p){
	    super();
	    setLayout(new GridLayout(1,5));
	    phrase = p;
	    voiceButton = new JButton(new ImageIcon(FilenameUtils.separatorsToSystem("resource/VoiceButton.png")));
	    chineseLabel = new JLabel(p.getChinese());
	    chineseLabel.setVisible(false);
	    englishLabel = new JLabel(p.getEnglish());
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
		DateFormat dateFormat = new SimpleDateFormat("/MM/dd/yy");
		Date date = new Date();
		String dateString = dateFormat.format(date);
		phrase.setLastReviewTime(dateString);
	    }
	}
    }

    public static void main(String args[]){
	LearnFrame test = new LearnFrame();
	test.addPhraseItem(new Phrase("English","Chinese","101.mp3"));
	test.addPhraseItem(new Phrase("AAAAA","BBBB","102.mp3"));
	test.setVisible(true);
    }

}
