
package code;

import java.awt.BorderLayout;
import java.awt.Dimension;
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
import javax.swing.JScrollPane;

public class LearnFrame extends AbstractFrame {  
    private JLabel titleLabel;  
    private JPanel titlePanel,phrasePanel;
    private JScrollPane phraseScrollPanel;

    public LearnFrame(){
	super("resource/Learn.png");
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
	    voiceButton = new JButton(new ImageIcon("resource/VoiceButton.png"));
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
		DateFormat dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
	    }
	}
    }

}
