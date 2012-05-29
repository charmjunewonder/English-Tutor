
package code;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstractFrame extends JFrame{
    private int lastx,lasty;
    private ImageIcon backgroundImage;
    private JButton exitButton,shrinkButton;
    private JPanel titlePanel,buttonPanel,leftPanel,rightPanel,mainPanel;
    private BorderLayout titleLayout,frameLayout;
    private Cursor cursor;

    protected AbstractFrame(String filePath){
	super();
	setUndecorated(true);
	setBackground(new Color(0,0,0,0));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	addMouseMotionListener(new FrameMouseListener());

	lastx = lasty = -1;
	backgroundImage = null;

	frameLayout = new BorderLayout();
	setLayout(frameLayout);
	initPanels();

	initTitlePanel();
	add(titlePanel,BorderLayout.NORTH);

	initCursor();
	setBackgroundImage(filePath);
	setLocationRelativeTo(null);
    }

    private void initPanels(){
	mainPanel = new JPanel();
	leftPanel = new JPanel();
	rightPanel = new JPanel();
	mainPanel.setOpaque(false);
	leftPanel.setOpaque(false);
	rightPanel.setOpaque(false);
	add(mainPanel,BorderLayout.CENTER);
	add(leftPanel,BorderLayout.WEST);
	add(rightPanel,BorderLayout.EAST);
    }

    protected JPanel getMainPanel(){
	return mainPanel;
    }

    private void initCursor(){
	ImageIcon icon = new ImageIcon(FilenameUtils.separatorsToSystem("resource/cursor.png"));
	cursor = Toolkit.getDefaultToolkit().createCustomCursor(
		icon.getImage(),
		new Point(0,icon.getIconHeight()-1),"cursor"
		);
	setCursor(cursor);
    }

    private void initTitlePanel(){
	exitButton = new JButton(new ImageIcon(FilenameUtils.separatorsToSystem("resource/x_white.png")));
	exitButton.addMouseListener(new ExitButtonAdapter());
	exitButton.setPreferredSize(new Dimension(30,30));
	exitButton.setToolTipText("Exit");
	exitButton.setBorderPainted(false);
	exitButton.setOpaque(false);

	shrinkButton = new JButton(new ImageIcon(FilenameUtils.separatorsToSystem("resource/shrink_white.png")));
	shrinkButton.addMouseListener(new ShrinkButtonAdapter());
	shrinkButton.setPreferredSize(new Dimension(50,20));
	shrinkButton.setToolTipText("Shrink");
	shrinkButton.setBorderPainted(false);
	shrinkButton.setOpaque(false);

	buttonPanel = new JPanel();
	buttonPanel.add(shrinkButton);
	buttonPanel.add(exitButton);
	buttonPanel.setOpaque(false);

	titleLayout = new BorderLayout();
	titlePanel = new JPanel();
	titlePanel.setLayout(titleLayout);
	titlePanel.add(buttonPanel,BorderLayout.EAST);
	titlePanel.setOpaque(false);
    }

    private class FrameMouseListener implements MouseMotionListener{

	public void mouseDragged(MouseEvent e){
	    if (lastx == -1 && lasty == -1) {
		lastx = e.getLocationOnScreen().x;
		lasty = e.getLocationOnScreen().y;
	    } else {
		setLocation(getX()+(e.getLocationOnScreen().x-lastx),getY()+(e.getLocationOnScreen().y-lasty));
		lastx = e.getLocationOnScreen().x;
		lasty = e.getLocationOnScreen().y;
	    }
	}

	public void mouseMoved(MouseEvent e){
	    lastx = e.getLocationOnScreen().x;
	    lasty = e.getLocationOnScreen().y;
	}
    }

    private class ShrinkButtonAdapter extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
	    setExtendedState(JFrame.ICONIFIED);
	}
	public void mouseEntered(MouseEvent e){
	    shrinkButton.setIcon(new ImageIcon(FilenameUtils.separatorsToSystem("resource/shrink_green.png")));
	}
	public void mouseExited(MouseEvent e){
	    shrinkButton.setIcon(new ImageIcon(FilenameUtils.separatorsToSystem("resource/shrink_white.png")));
	}
    }

    private class ExitButtonAdapter extends MouseAdapter{
	public void mouseClicked(MouseEvent e){
	    dispose(); 
	}
	public void mouseEntered(MouseEvent e){
	    exitButton.setIcon(new ImageIcon(FilenameUtils.separatorsToSystem("resource/x_red.png")));
	}
	public void mouseExited(MouseEvent e){
	    exitButton.setIcon(new ImageIcon(FilenameUtils.separatorsToSystem("resource/x_white.png")));
	}
    }

    private JButton getExitButton(){
	return exitButton;
    }

    private JButton getShrinkButton(){
	return shrinkButton;
    }

    private void setBackgroundImage(String filePath){
	JPanel backgroundPanel = (JPanel)getContentPane();
	backgroundImage = new ImageIcon(filePath);

	JLabel backgroundLabel = new JLabel(backgroundImage);
	backgroundLabel.setBounds(0, 0, backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
	getLayeredPane().add(backgroundLabel, new Integer(Integer.MIN_VALUE));

	backgroundPanel.setOpaque(false);

	setSize(backgroundImage.getIconWidth(), backgroundImage.getIconHeight());
    }
}
