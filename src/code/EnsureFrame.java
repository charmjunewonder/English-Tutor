package code;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.commons.io.FilenameUtils;

public class EnsureFrame extends AbstractFrame{
   private JButton firstButton,secondButton,thridButton;
   private JLabel statementLabel;
   private JPanel buttonPanel;
   
   public EnsureFrame(){
	    super(FilenameUtils.separatorsToSystem("ensureFrame.png"));
	    initTextLabel();
	    initButtonPanel();
   }

   private void initTextLabel(){
	    statementLabel = new JLabel("Do you really want to quit the tutor?");
	    statementLabel.setOpaque(false);
        statementLabel.setBounds(10,10,100,10);
        getContentPane().add(statementLabel);
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
	    
	    buttonPanel.setBounds(10, 150, 10, 150);
	    getContentPane().add(buttonPanel);
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
