package code;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class EnsureFrame extends AbstractFrame{
   private JButton firstButton,secondButton,thridButton;
   private JLabel statementLabel;
   private JPanel buttonPanel;
   private BorderLayout ensureFrameLayout;
   
   public EnsureFrame(){
	    super(FilenameUtils.separatorsToSystem("ensureFrame.png"));
	    initLayout();
	    initTextLabel();
	    initButtonPanel();
   }
   
   private void initLayout(){
	    ensureFrameLayout = new BorderLayout();
	    getMainPanel().setLayout(ensureFrameLayout);
   }
   
   private void initTextLabel(){
	    statementLabel = new JLabel("Do you really want to quit the tutor?");
	    statementLabel.setOpaque(false);
        getMainPanel().add(statementLabel,BorderLayout.CENTER);
   }
   
   private void initButtonPanel(){
	    buttonPanel = new JPanel();
	    buttonPanel.setOpaque(false);
	    
	    firstButton = new JButton("NO");
	    secondButton = new JButton("Yes,not save");
	    thridButton = new JButton("Yes,save it");
	    
	    buttonPanel.add(firstButton);
	    buttonPanel.add(secondButton);
	    buttonPanel.add(thridButton);
	    
	    getMainPanel().add(buttonPanel,BorderLayout.SOUTH);
   }
   
   private JLabel getStatementLabel(){
	    return statementLabel;
   }
   
   private JButton getFirstButton(){
	    return firstButton;
   }
   
   private JButton getSecondButton(){
	    return secondButton;
   }
   
   private JButton getThridButton(){
	    return thridButton;
   }
   
   public static void main(String args[]){
        EnsureFrame test = new EnsureFrame();
        test.setVisible(true);
   }
}
