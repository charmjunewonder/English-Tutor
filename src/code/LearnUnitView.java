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
import javax.swing.ScrollPaneConstants;
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
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.GridBagLayout;

import java.util.ArrayList;

public class LearnUnitView extends JFrame {

	private JPanel contentPane;
	private ArrayList<JLabel> englishLabels;
	private ArrayList<JLabel> chineseLabels;
	private ArrayList<JButton> soundButtons;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LearnUnitView frame = new LearnUnitView(10);
					frame.setVisible(true);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LearnUnitView(int numOfColumn) {		
		
		chineseLabels = new ArrayList<JLabel>();	
		englishLabels = new ArrayList<JLabel>();	
		soundButtons = new ArrayList<JButton>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		SpringLayout sl_contentPane = new SpringLayout();
		contentPane.setLayout(sl_contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		sl_contentPane.putConstraint(SpringLayout.NORTH, scrollPane, 22, SpringLayout.NORTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.WEST, scrollPane, 43, SpringLayout.WEST, contentPane);
		sl_contentPane.putConstraint(SpringLayout.SOUTH, scrollPane, -26, SpringLayout.SOUTH, contentPane);
		sl_contentPane.putConstraint(SpringLayout.EAST, scrollPane, -31, SpringLayout.EAST, contentPane);
		contentPane.add(scrollPane);
		
		JPanel panel = new JPanel();
		//panel.setBounds(0, 0, 450, 500);
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		for(int i = 0; i < numOfColumn; ++i){
			JLabel chineseLabel = new JLabel("New label");
			chineseLabel.setBounds(25, 25*i, 61, 16);
			panel.add(chineseLabel);
			chineseLabels.add(chineseLabel);
			
			JLabel englishLabel = new JLabel("New label");
			englishLabel.setBounds(123, 25*i, 61, 16);
			panel.add(englishLabel);
			englishLabels.add(englishLabel);
			
			JButton soundButton = new JButton("Sound");
			soundButton.setBounds(197, 25*i, 117, 29);
			panel.add(soundButton);
			soundButtons.add(soundButton);
		}

	}
	
}
