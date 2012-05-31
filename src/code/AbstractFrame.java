
package code;

import java.awt.Image;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.apache.commons.io.FilenameUtils;

public abstract class AbstractFrame extends JFrame{
    private int lastx,lasty;
    private ImageIcon backgroundImageIcon;
    protected JButton exitButton,shrinkButton;
    private ImagePanel contentPane;
    private Cursor cursor;
    protected ImageIcon exitImage, exitEnteredImage, shrinkImage, shrinkEnteredImage;

    protected AbstractFrame(String filePath){
	super();
	exitImage = new ImageIcon(FilenameUtils.separatorsToSystem("resource/x_black.png"));
	exitEnteredImage = new ImageIcon(FilenameUtils.separatorsToSystem("resource/x_red.png"));
	shrinkImage = new ImageIcon(FilenameUtils.separatorsToSystem("resource/shrink_black.png"));
	shrinkEnteredImage = new ImageIcon(FilenameUtils.separatorsToSystem("resource/shrink_green.png"));
	initialize(filePath);
    }

    protected AbstractFrame(String filePath, int exitWidth, int exitHeight, int shrinkWidth, int shrinkHeight){
	super();
	exitImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/x_black.png").
		getScaledInstance(exitWidth, exitHeight, Image.SCALE_DEFAULT));
	exitEnteredImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/x_red.png").
		getScaledInstance(exitWidth, exitHeight, Image.SCALE_DEFAULT));
	shrinkImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/shrink_black.png").
		getScaledInstance(shrinkWidth, shrinkHeight, Image.SCALE_DEFAULT));
	shrinkEnteredImage = new ImageIcon(
		Toolkit.getDefaultToolkit().getImage("resource/shrink_green.png").
		getScaledInstance(shrinkWidth, shrinkHeight, Image.SCALE_DEFAULT));
	initialize(filePath);
    }

    private void initialize(String filePath){
	setUndecorated(true);
	setBackground(new Color(0, true));
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	addMouseMotionListener(new FrameMouseListener());

	lastx = lasty = -1;

	initBackgroundImage(filePath);
	initBounds();
	initContentPane();
	initTitlePanel();
	initCursor();
	setLocationRelativeTo(null);
    }

    private void initBackgroundImage(String filePath){
	backgroundImageIcon = new ImageIcon(filePath);
    }

    private void initBounds(){
	setBounds(0,0,backgroundImageIcon.getIconWidth(),backgroundImageIcon.getIconHeight());

    }

    private void initContentPane(){
	contentPane = new ImagePanel(backgroundImageIcon.getImage());
	contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
	setContentPane(contentPane);
	contentPane.setLayout(null);
    }

    private void initCursor(){
	ImageIcon icon = new ImageIcon(FilenameUtils.separatorsToSystem("resource/cursor.png"));
	cursor = Toolkit.getDefaultToolkit().createCustomCursor(
		icon.getImage(),
		new Point(0,0),"cursor"
		);
	setCursor(cursor);
    }

    protected void removeExitAndShrinkButton(){
	contentPane.remove(exitButton);
	contentPane.remove(shrinkButton);
    }

    protected void setButtonSize(int exitWidth, int exitHeight, int shrinkWidth, int shrinkHeight){

    }

    protected void initTitlePanel(){
	exitButton = new JButton(exitImage);
	exitButton.addMouseListener(new ExitButtonAdapter());
	exitButton.setToolTipText("Exit");
	exitButton.setBorderPainted(false);
	exitButton.setBackground(new Color(0,0,0,0));
	exitButton.setOpaque(false);
	exitButton.setBounds(getWidth()-50,17,30,30);
	exitButton.setBorderPainted(false);
	contentPane.add(exitButton);

	shrinkButton = new JButton(shrinkImage);
	shrinkButton.addMouseListener(new ShrinkButtonAdapter());
	shrinkButton.setToolTipText("Shrink");
	shrinkButton.setBorderPainted(false);
	shrinkButton.setBackground(new Color(0,0,0,0));
	shrinkButton.setOpaque(false);
	shrinkButton.setBounds(getWidth()-100, 22, 50, 20);
	shrinkButton.setBorderPainted(false);
	contentPane.add(shrinkButton);
    }

    protected void addExitingReturnToMainController(){
	exitButton.addActionListener(new ActionListener(){
	    public void actionPerformed(ActionEvent e){
		MainController main = MainController.getMainController();
		main.getLessonController().initAllPhrases();
		main.setVisible(true);
	    }
	});
    }

    /**
     * @param exitImage the exitImage to set
     */
    public void setExitImage(ImageIcon exitImage) {
	this.exitImage = exitImage;
    }

    /**
     * @param exitEnteredImage the exitEnteredImage to set
     */
    public void setExitEnteredImage(ImageIcon exitEnteredImage) {
	this.exitEnteredImage = exitEnteredImage;
    }

    /**
     * @param shrinkImage the shrinkImage to set
     */
    public void setShrinkImage(ImageIcon shrinkImage) {
	this.shrinkImage = shrinkImage;
    }

    /**
     * @param shrinkEnteredImage the shrinkEnteredImage to set
     */
    public void setShrinkEnteredImage(ImageIcon shrinkEnteredImage) {
	this.shrinkEnteredImage = shrinkEnteredImage;
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
	    shrinkButton.setIcon(shrinkEnteredImage);
	}

	public void mouseExited(MouseEvent e){
	    shrinkButton.setIcon(shrinkImage);
	}

    }

    private class ExitButtonAdapter extends MouseAdapter{

	public void mouseClicked(MouseEvent e){
	    setVisible(false);
	    dispose(); 
	}

	public void mouseEntered(MouseEvent e){
	    exitButton.setIcon(exitEnteredImage);
	}

	public void mouseExited(MouseEvent e){
	    exitButton.setIcon(exitImage);
	}

    }

    protected JButton getExitButton(){
	return exitButton;
    }

    protected JButton getShrinkButton(){
	return shrinkButton;
    }

    private class ImagePanel extends JPanel {
	private Image image;
	public ImagePanel(Image image) {
	    this.image = image;
	}
	@Override
	protected void paintComponent(Graphics g) {
	    g.drawImage(image, 0, 0, null);
	}
    }
}
