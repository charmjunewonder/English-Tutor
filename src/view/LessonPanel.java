package view;

import javax.swing.BorderFactory;
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

import model.Phrase;

import org.apache.commons.io.FilenameUtils;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LessonPanel extends JPanel {
	private JButton learnLessonButton, testLessonButton, deletePhraseButton,
			addPhraseButton;
	private JTextField chineseTextField, englishTextField;
	private DefaultTableModel tableModel;
	private JScrollPane phraseScrollPanel;
	private JTable phraseTable;
	private JLabel chineseLabel, englishLabel;
	private int sum_phrase;

	private static LessonPanel lessonPanel;

	public static LessonPanel getLessonPanel() {
		if (lessonPanel == null) {
			synchronized (LessonPanel.class) {
				if (lessonPanel == null) {
					lessonPanel = new LessonPanel();
				}
			}
		}
		return lessonPanel;
	}

	private LessonPanel() {
		super();
		setLayout(null);
		setBackground(new Color(0, 0, 0, 0));
		setOpaque(false);
		setBounds(500, 40, 400, 600);
		initLearnLessonButton();
		initTestLessonButton();
		initPhraseScrollPanel();
		initPhraseTable();
		initAddPhrase();
		initDeletePhraseButton();
		sum_phrase = 0;
	}

	private void initLearnLessonButton() {
		learnLessonButton = new JButton("Learning");
		learnLessonButton.setBounds(0, 0, 100, 30);
		add(learnLessonButton);
	}

	private void initTestLessonButton() {
		testLessonButton = new JButton("Test");
		testLessonButton.setBounds(110, 0, 70, 30);
		add(testLessonButton);
	}

	private void initPhraseScrollPanel() {
		phraseScrollPanel = new JScrollPane();

		phraseScrollPanel
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		phraseScrollPanel
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

		phraseScrollPanel.getViewport().setOpaque(false);
		phraseScrollPanel.setOpaque(false);
		phraseScrollPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1,
				Color.white));

		phraseScrollPanel.setBounds(15, 50, 400, 450);
		add(phraseScrollPanel);
	}

	private void initPhraseTable() {
		String[] columnNames = new String[5];
		columnNames[0] = "����";
		columnNames[1] = "English";
		columnNames[2] = "Sound";
		columnNames[3] = "Date";
		columnNames[4] = "Accuracy";

		tableModel = new DefaultTableModel(0, 5);
		tableModel.setColumnIdentifiers(columnNames);
		phraseTable = new JTable();
		phraseTable.setModel(tableModel);
		phraseTable.setRowHeight(25);
		phraseTable.setBounds(0, 0, 350, 450);

		phraseTable.setOpaque(false);
		phraseTable.setBackground(new Color(0, true));
		phraseTable.setIntercellSpacing(new Dimension(0, 0));
		phraseTable.setShowVerticalLines(false);
		phraseTable.setShowHorizontalLines(false);

		phraseScrollPanel.setViewportView(phraseTable);
	}

	private void initAddPhrase() {
		chineseLabel = new JLabel("����");
		chineseLabel.setBounds(30, 530, 60, 30);
		add(chineseLabel);

		englishLabel = new JLabel("English");
		englishLabel.setBounds(30, 560, 60, 30);
		add(englishLabel);

		chineseTextField = new JTextField();
		chineseTextField.setBounds(90, 530, 250, 30);
		add(chineseTextField);

		englishTextField = new JTextField();
		englishTextField.setBounds(90, 560, 250, 30);
		add(englishTextField);

		addPhraseButton = new JButton("Add");
		addPhraseButton.setBounds(340, 560, 60, 30);
		add(addPhraseButton);
	}

	private void initDeletePhraseButton() {
		deletePhraseButton = new JButton("Delete");
		deletePhraseButton.setBounds(0, 500, 70, 30);
		add(deletePhraseButton);
	}

	public void addPhrase(Phrase p) {
		String chinese = p.getChinese();
		String english = p.getEnglish();
		String date = p.getLastReviewTime();
		String accuracy = "" + p.getAccuracy();
		sum_phrase++;

		String[] columnNames = new String[5];
		columnNames[0] = "����";
		columnNames[1] = "English";
		columnNames[2] = "Sound";
		columnNames[3] = "Date";
		columnNames[4] = "Accuracy";

		DefaultTableModel newTableModel = new DefaultTableModel(sum_phrase, 5) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		newTableModel.setColumnIdentifiers(columnNames);

		for (int i = 0; i < sum_phrase - 1; i++)
			for (int j = 0; j < 5; j++) {
				newTableModel.setValueAt(tableModel.getValueAt(i, j), i, j);
			}

		phraseTable.setModel(newTableModel);
		tableModel = newTableModel;
		phraseTable.setValueAt(chinese, sum_phrase - 1, 0);
		phraseTable.setValueAt(english, sum_phrase - 1, 1);

		Image i = Toolkit.getDefaultToolkit()
				.getImage("resource/VoiceButton.png")
				.getScaledInstance(25, 25, Image.SCALE_DEFAULT);

		if (p.getAudio() != null)
			phraseTable.setValueAt(
					new ImageIcon(FilenameUtils
							.separatorsToSystem("resource/VoiceButton.png")),
					sum_phrase - 1, 2);
		else
			phraseTable.setValueAt(new ImageIcon(), sum_phrase - 1, 2);
		phraseTable.setValueAt(date, sum_phrase - 1, 3);
		phraseTable.setValueAt(accuracy, sum_phrase - 1, 4);

		phraseTable.getColumn("Sound").setCellEditor(
				phraseTable.getDefaultEditor(Icon.class));
		phraseTable.getColumn("Sound").setCellRenderer(
				phraseTable.getDefaultRenderer(Icon.class));

	}

	public void deletePhrase(int row) {
		((DefaultTableModel) phraseTable.getModel()).removeRow(row);
		sum_phrase--;
	}

	public void clearPhraseTabelContent() {
		DefaultTableModel mtl = (DefaultTableModel) phraseTable.getModel();
		int row = mtl.getRowCount();
		while (--row >= 0) {
			mtl.removeRow(row);
		}
		sum_phrase = 0;
	}

	public void clearEnglishChineseTextField() {
		chineseTextField.setText("");
		englishTextField.setText("");
	}

	/**
	 * @return the phraseTable
	 */
	public JTable getPhraseTable() {
		return phraseTable;
	}

	public JTextField getChineseTextField() {
		return chineseTextField;
	}

	public JTextField getEnglishTextField() {
		return englishTextField;
	}

	public JButton getLearnButton() {
		return learnLessonButton;
	}

	public JButton getTestButton() {
		return testLessonButton;
	}

	public JButton getAddButton() {
		return addPhraseButton;
	}

	public JButton getDeleteButton() {
		return deletePhraseButton;
	}

	public static void main(String args[]) {
		MainFrame test = new MainFrame();
		LessonPanel p = new LessonPanel();
		test.getContentPane().add(p);
		test.setVisible(true);
	}
}