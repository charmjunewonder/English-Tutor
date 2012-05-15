package code;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import net.miginfocom.swing.MigLayout;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.factories.FormFactory;
import java.awt.Color;
import javax.swing.JTextField;

import java.util.ArrayList;

public class LearnUnitView extends JFrame {

	private JPanel contentPane;
	private JLabel englishLabel;
	private JLabel chineseLabel;
	private JButton nextButton;
	private JButton previousButton;
	private JButton soundButton;
	
	private SoundEngine soundEngine;

	/**
	 * Launch the application.
	 *
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LearnUnitView frame = new LearnUnitView();
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}/

	/**
	 * Create the frame.
	 */
	public LearnUnitView() {
		soundEngine = new SoundEngine();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		nextButton = new JButton("Next");
		sl_contentPane.putConstraint(SpringLayout.SOUTH, nextButton, -10, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, nextButton, -10, SpringLayout.EAST, contentPane);
		contentPane.add(nextButton);
		
		previousButton = new JButton("Nothing");
		sl_contentPane.putConstraint(SpringLayout.WEST, previousButton, 10, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, previousButton, 0, SpringLayout.SOUTH, nextButton);
		contentPane.add(previousButton);
		
		soundButton = new JButton("Sound");
		sl_contentPane.putConstraint(SpringLayout.NORTH, soundButton, 38, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, soundButton, 0, SpringLayout.EAST, nextButton);
		contentPane.add(soundButton);
		
		englishLabel = new JLabel("English");
		englishLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_contentPane.putConstraint(SpringLayout.NORTH, englishLabel, 0, SpringLayout.NORTH, soundButton);
		sl_contentPane.putConstraint(SpringLayout.WEST, englishLabel, 186, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, englishLabel, -81, SpringLayout.WEST, soundButton);
		contentPane.add(englishLabel);
		
		chineseLabel = new JLabel("Chinese");
		chineseLabel.setHorizontalAlignment(SwingConstants.CENTER);
		sl_contentPane.putConstraint(SpringLayout.NORTH, chineseLabel, 84, SpringLayout.SOUTH, englishLabel);
		sl_contentPane.putConstraint(SpringLayout.WEST, chineseLabel, 186, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, chineseLabel, -204, SpringLayout.EAST, contentPane);
		contentPane.add(chineseLabel);
	}
	
	public void updatePhrase(Phrase p){
		chineseLabel.setText(p.chinese);
		englishLabel.setText(p.english);
		playSound(p);
	}
	
	public void playSound(Phrase p){
		soundEngine.playSound(p.audio);
	}
	
	/**
	 * @return the englishLabel
	 */
	public JLabel getEnglishLabel() {
		return englishLabel;
	}

	/**
	 * @return the chineseLabel
	 */
	public JLabel getChineseLabel() {
		return chineseLabel;
	}

	/**
	 * @return the nextButton
	 */
	public JButton getNextButton() {
		return nextButton;
	}

	/**
	 * @return the previousButton
	 */
	public JButton getPreviousButton() {
		return previousButton;
	}

	/**
	 * @return the soundButton
	 */
	public JButton getSoundButton() {
		return soundButton;
	}
}
