package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import org.apache.commons.io.FilenameUtils;


public class EnsureFrame extends AbstractFrame {
	public static final int CANCEL_OPTION = 23947;
	public static final int YES_OPTION = 79237;
	public static final int NO_OPTION = 83874;

	private JButton firstButton, secondButton, thirdButton;
	private JLabel statementLabel;
	private JPanel buttonPanel;

	private static int optionValue;
	private static boolean isClicked;

	private static EnsureFrame frame;

	public EnsureFrame() {
		super(FilenameUtils.separatorsToSystem("resource/EnsureFrame2.png"));
		initTextLabel();
		initButtonPanel();
		removeExitAndShrinkButton();
	}

	public static EnsureFrame getEnsureFrame() {
		// TODO
		if (frame == null) {
			frame = new EnsureFrame();
		}
		return frame;
	}

	public static void showMessageDialog(String message) {
		final EnsureFrame ensure = EnsureFrame.getEnsureFrame();
		ensure.getThirdButton().setText("OK");
		ensure.getSecondButton().setText("");
		ensure.getFirstButton().setText("");
		ensure.getStatementLabel().setText(message);
		ensure.setVisible(true);
		ensure.getThirdButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ensure.setVisible(false);
				ensure.dispose();
			}
		});
	}

	public static int showConfirmDialog(String message) {
		final EnsureFrame ensure = EnsureFrame.getEnsureFrame();
		ensure.getThirdButton().setText("Save");
		ensure.getSecondButton().setText("Cancel");
		ensure.getFirstButton().setText("Don't save");
		ensure.getStatementLabel().setText(message);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				ensure.setVisible(true);
			}
		});
		ensure.setVisible(true);
		ensure.getThirdButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionValue = YES_OPTION;
				isClicked = true;
				ensure.setVisible(false);
				ensure.dispose();
			}
		});
		ensure.getSecondButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionValue = CANCEL_OPTION;
				isClicked = true;
				ensure.setVisible(false);
				ensure.dispose();
			}
		});
		ensure.getFirstButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				optionValue = NO_OPTION;
				isClicked = true;
				ensure.setVisible(false);
				ensure.dispose();
			}
		});
		while (!isClicked) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {

			}
		}
		return optionValue;
	}

	private void initTextLabel() {
		statementLabel = new JLabel();
		statementLabel.setOpaque(false);
		statementLabel.setBounds(300, 315, 360, 40);
		statementLabel.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(statementLabel);
	}

	private void initButtonPanel() {
		buttonPanel = new JPanel();
		buttonPanel.setOpaque(false);

		firstButton = new JButton();
		secondButton = new JButton();
		thirdButton = new JButton();

		firstButton.setBorderPainted(false);
		secondButton.setBorderPainted(false);
		thirdButton.setBorderPainted(false);
		firstButton.setContentAreaFilled(false);
		secondButton.setContentAreaFilled(false);
		thirdButton.setContentAreaFilled(false);

		buttonPanel.add(firstButton);
		buttonPanel.add(secondButton);
		buttonPanel.add(thirdButton);

		buttonPanel.setBounds(270, 400, 400, 150);
		getContentPane().add(buttonPanel);
	}

	private JLabel getStatementLabel() {
		return statementLabel;
	}

	private JButton getFirstButton() {
		return firstButton;
	}

	private JButton getSecondButton() {
		return secondButton;
	}

	private JButton getThirdButton() {
		return thirdButton;
	}

	public static void main(String args[]) {
		int a = EnsureFrame
				.showConfirmDialog("Do you want to save the changes ?");
		System.out.println(a);
	}
}
