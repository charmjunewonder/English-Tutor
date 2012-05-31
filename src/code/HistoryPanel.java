package code;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FilenameUtils;

import java.awt.Color;

public class HistoryPanel extends JPanel{
     private JLabel titleLabel; 
	 private JTable historyTable;
     private JScrollPane historyScrollPanel;
	 private DefaultTableModel tableModel;
	 private int sum_history;
	 
	 public HistoryPanel(){
    	 super();
    	 setBounds(500,50,400,600);
    	 setBackground(new Color(0,0,0,0));
    	 setOpaque(false);
    	 setLayout(null);
    	 initTitleLabel();
    	 initHistoryScrollPanel();
    	 initHistoryTable();
    	 sum_history=0;
     }
     
	 private void initTitleLabel(){
		 titleLabel = new JLabel("History");
		 titleLabel.setBounds(180, 10, 80, 30);
		 add(titleLabel);
	 }
	 
	 private void initHistoryScrollPanel(){
		 historyScrollPanel = new JScrollPane();
		 historyScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	     historyScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		 historyScrollPanel.setBounds(0, 50, 400, 550);
	     add(historyScrollPanel);
	 }
	 
	 private void initHistoryTable(){
		 
		 tableModel = new DefaultTableModel(0,3);
		 historyTable = new JTable();
		 historyTable.setModel(tableModel);
		 
		 historyTable.setBounds(0, 0, 400, 550);
		 historyScrollPanel.setViewportView(historyTable);
	 }
	 
	 public void addHistory(String time,int mark){
		 sum_history++;
		    
		 DefaultTableModel newTableModel = new DefaultTableModel(sum_history,3){
	  		   @Override
			   public boolean isCellEditable(int row, int column) {
			      return false;
			   }
		 };
		 for(int i=0;i<sum_history-1;i++)
			for(int j=0;j<3;j++){
		        newTableModel.setValueAt(tableModel.getValueAt(i, j), i, j);
			}
		 
		 historyTable.setModel(newTableModel);
		 tableModel = newTableModel;
		    
		 historyTable.setValueAt(time, sum_history-1, 0);
		 historyTable.setValueAt(mark, sum_history-1, 1);
		 if (mark<=60) {
			 historyTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/Red.png")), sum_history-1, 2);
		 } else if (mark<=80) {
			 historyTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/Yellow.png")), sum_history-1, 2);
		 } else {
			 historyTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/Green.png")), sum_history-1, 2);
		 }
		
			    
		 historyTable.getColumn("C").setCellEditor(historyTable.getDefaultEditor(Icon.class));
		 historyTable.getColumn("C").setCellRenderer(historyTable.getDefaultRenderer(Icon.class));	    
	 }
	 
	 public JLabel getTitleLabel(){
		 return titleLabel;
	 }
	 
	 public JTable getHistoryTable(){
		 return historyTable;
	 }
	 
     public static void main(String args[]){
    	 MainFrame test = new MainFrame();
    	 HistoryPanel p = new HistoryPanel();
    	 test.getContentPane().add(p);
    	 p.addHistory("05/20", 60);
    	 p.addHistory("05/21", 70);
    	 p.addHistory("06/01", 95);
    	 test.setVisible(true);
     }
}
