package code;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;

import org.apache.commons.io.FilenameUtils;

public class LearnFrame extends AbstractFrame {
	private JLabel titleLabel;
	private JPanel phrasePanel;
	private JButton finishButton;
	private JScrollPane phraseScrollPanel;
	private int sum, currentPhraseIndex, dimensionX, dimensionY;

	public LearnFrame() {
		super(FilenameUtils.separatorsToSystem("resource/Learn.png"));
		initTitleLabel();
		initPhraseScrollPanel();
		initFinishButton();
		sum = 0;
	}

	private void initTitleLabel() {
		titleLabel = new JLabel("Learning");
		titleLabel.setBounds(200, 40, 200, 50);
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));
		getContentPane().add(titleLabel);
	}

	private void initFinishButton() {
		finishButton = new JButton("Finish");
		finishButton.setBounds(489, 598, 80, 40);
		getContentPane().add(finishButton);
	}

	public JButton getFinishButton() {
		return finishButton;
	}

	private void initPhraseScrollPanel() {
		phrasePanel = new JPanel();
		dimensionY = 500;
		dimensionX = 0;
		phrasePanel.setLayout(null);
		phrasePanel.setOpaque(false);

		phraseScrollPanel = new JScrollPane();
		phraseScrollPanel
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		phraseScrollPanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		phraseScrollPanel.setVisible(true);
		phraseScrollPanel.getViewport().setOpaque(false);
		phraseScrollPanel.setOpaque(false);
		phraseScrollPanel.setBorder(null);
		phraseScrollPanel.setViewportView(phrasePanel);

		phraseScrollPanel.setBounds(42, 107, 500, 450);
		getContentPane().add(phraseScrollPanel);
	}

	/**
	 * @param p
	 */
	public void addPhraseItem(Phrase p) {
		PhraseItemPanel newPhrase = new PhraseItemPanel(p);
		newPhrase.setOpaque(false);
		newPhrase.setBounds(0, currentPhraseIndex++ * 25, 500, 30);
		phrasePanel.add(newPhrase);
		dimensionX += 25;
		phrasePanel.setPreferredSize(new Dimension(dimensionY, dimensionX));
	}

	private class PhraseItemPanel extends JPanel {
		private JLabel chineseLabel, englishLabel;
		private JButton voiceButton;
		private Phrase phrase;

		public PhraseItemPanel(Phrase p) {
			super();
			setLayout(null);
			phrase = p;

			chineseLabel = new JLabel(p.getChinese());
			chineseLabel.setBounds(0, 0, 235, 25);
			chineseLabel.setHorizontalAlignment(SwingConstants.CENTER);
			chineseLabel.addMouseListener(new ChineseLabelAdapter());

			Image i = Toolkit.getDefaultToolkit()
					.getImage("resource/VoiceButton.png")
					.getScaledInstance(25, 25, Image.SCALE_DEFAULT);
			voiceButton = new JButton(new ImageIcon(Toolkit.getDefaultToolkit()
					.getImage("resource/VoiceButton.png")));
			voiceButton.setBounds(235, 0, 30, 25);
			voiceButton.setOpaque(false);
			voiceButton.setBackground(new Color(0, 0, 0, 0));
			voiceButton.setBorderPainted(false);
			voiceButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					SoundEngine.playSound(phrase.getAudio());
				}
			});

			englishLabel = new JLabel(p.getEnglish());
			englishLabel.setHorizontalAlignment(SwingConstants.CENTER);
			englishLabel.setBounds(265, 0, 235, 25);
			englishLabel.setVisible(false);

			add(englishLabel);
			add(voiceButton);
			add(chineseLabel);
		}

		private class ChineseLabelAdapter extends MouseAdapter {
			public void mouseClicked(MouseEvent e) {
				englishLabel.setVisible(true);
				DateFormat dateFormat = new SimpleDateFormat("/MM/dd/yy");
				Date date = new Date();
				String dateString = dateFormat.format(date);
				phrase.setLastReviewTime(dateString);
			}
		}
	}

	public static void main(String args[]) {
		LearnFrame test = new LearnFrame();
		test.addPhraseItem(new Phrase("English", "Chinese", "101.mp3"));
		test.addPhraseItem(new Phrase("AAAAA", "BBBB", "102.mp3"));
		test.setVisible(true);
	}

}
