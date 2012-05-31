package code;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.apache.commons.io.FilenameUtils;

import java.awt.Color;
import java.awt.Component;

public class HistoryPanel extends JPanel{
    private JLabel titleLabel; 
    private JTable historyTable;
    private JScrollPane historyScrollPanel;
    private DefaultTableModel tableModel;
    private int sum_history;

    private static HistoryPanel historyPanel;

    public static HistoryPanel getHistoryPanel(){
	if (historyPanel == null) {
	    synchronized (HistoryPanel.class) {
		if (historyPanel == null) {
		    historyPanel = new HistoryPanel();
		}
	    }
	}
	return historyPanel;
    }
    
    private HistoryPanel(){
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
    String[] columnNames = new String[3];
	columnNames[0] = "Date";
	columnNames[1] = "Mark";
	columnNames[2] = "Color";
	
	
	tableModel = new DefaultTableModel(0,3);
	tableModel.setColumnIdentifiers(columnNames);
	historyTable = new JTable(){
		/*    
		public Component prepareRenderer(
			    TableCellRenderer renderer, int row, int column)
		    {
			Component c = super.prepareRenderer(renderer, row, column);
			JComponent jc = (JComponent)c;
			jc.setBorder(BorderFactory.createMatteBorder(0,0,3,0,Color.white));


			if (isRowSelected(row)){
			}
			else{
			}

			return c;
		    }
		  *//*  
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }*/
	};
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
	
    String[] columnNames = new String[3];
	columnNames[0] = "Date";
	columnNames[1] = "Mark";
	columnNames[2] = "Color";
	
	newTableModel.setColumnIdentifiers(columnNames);
	
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


	historyTable.getColumn("Color").setCellEditor(historyTable.getDefaultEditor(Icon.class));
	historyTable.getColumn("Color").setCellRenderer(historyTable.getDefaultRenderer(Icon.class));	    
    }

    public void clearHistoryTableContent(){
	DefaultTableModel mtl = (DefaultTableModel)historyTable.getModel();
	int row = mtl.getRowCount();
	while(--row >= 0){
	    mtl.removeRow(row);
	}
	sum_history = 0;
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
