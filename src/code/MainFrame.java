package code;

import java.awt.Color;
import java.util.*;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel; 
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Icon;
import org.apache.commons.io.FilenameUtils;

public class MainFrame extends AbstractFrame{
    private JPanel lessons;
    private JTable lessonTable;
	private JScrollPane lessonScrollPanel;
    private JButton logoutButton,testAllButton,addLessonButton,deleteLessonButton;
    private JLabel titleLabel,historyLabel,listLabel;
    private HistoryPanel historyPanel;
    private LessonPanel lessonPanel;
    private DefaultTableModel tableModel;
    private int sum_lesson;
    
    public MainFrame(){
	super(FilenameUtils.separatorsToSystem("resource/MainFrame.png"));
	sum_lesson=0;
	getExitButton().setBounds(getWidth()-90,17,30,30);
	getShrinkButton().setBounds(getWidth()-140, 22, 50, 20);
	initTitleLabel();
	initLogoutButton();
	initLessonScrollPanel();
	
	initLessonTable();
	initLessonButton();
	initHistoryPanel();
	initLessonPanel();
	initTestAllButton();
	initDefaultLabel();
    }

    /**
     * @return the logoutButton
     */
    public JButton getLogoutButton() {
        return logoutButton;
    }

    /**
     * @return the testAllButton
     */
    public JButton getTestAllButton() {
        return testAllButton;
    }

    /**
     * @return the addLessonButton
     */
    public JButton getAddLessonButton() {
        return addLessonButton;
    }

    /**
     * @return the deleteLessonButton
     */
    public JButton getDeleteLessonButton() {
        return deleteLessonButton;
    }
    
    
    private void initLogoutButton(){
    	logoutButton = new JButton(new ImageIcon("resource/LogoutButton.PNG"));
        logoutButton.setBounds(20,20,50,30);
        logoutButton.setBorderPainted(false);
        getContentPane().add(logoutButton);
    }
    
    private void initTitleLabel(){
    	titleLabel = new JLabel("English Tutor");
        titleLabel.setBounds(200,10,80,100);
        getContentPane().add(titleLabel);
    }
    
    private void initLessonScrollPanel(){
	lessonScrollPanel = new JScrollPane();
	lessonScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	lessonScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	lessonScrollPanel.setBounds(40, 80, 400, 500);
	getContentPane().add(lessonScrollPanel);
    }
    
    private void initLessonTable(){
    tableModel = new DefaultTableModel(0,3);
    lessonTable = new JTable();
    lessonTable.setModel(tableModel);
    lessonTable.setBounds(0, 0, 392, 500);
    
    lessonScrollPanel.setViewportView(lessonTable);	
    lessonTable.setRowHeight(60);
    }
    
    private void initLessonButton(){
    addLessonButton = new JButton("add");
    addLessonButton.setBounds(40, 580, 60, 30);
    addLessonButton.addMouseListener(new AddLessonButtonAdapter());
    
    deleteLessonButton = new JButton("delete");
    deleteLessonButton.setBounds(100, 580, 80, 30);
    
    getContentPane().add(addLessonButton);
    getContentPane().add(deleteLessonButton);
    }
    
    private void initTestAllButton(){
    	testAllButton = new JButton("Test All");
    	testAllButton.setBounds(185, 600, 80, 40);
    	getContentPane().add(testAllButton);
    }
    
    private void initDefaultLabel(){
    	historyLabel = new JLabel("History");
    	historyLabel.setBounds(940, 90, 50, 50);
    	historyLabel.addMouseListener(new HistoryLabelAdapter());
    	getContentPane().add(historyLabel);
    	
    	listLabel = new JLabel("List");
    	listLabel.setBounds(940, 210, 50, 50);
    	listLabel.addMouseListener(new ListLabelAdapter());
    	getContentPane().add(listLabel);
    }
    
    private void initHistoryPanel(){
    	historyPanel = new HistoryPanel();
    	//historyPanel.setOpaque(false);
    	getContentPane().add(historyPanel);
    }
    
    private void initLessonPanel(){
    	lessonPanel = new LessonPanel();
    }
    
    public void addLesson(String lessonName){
	sum_lesson++;
    
    DefaultTableModel newTableModel = new DefaultTableModel(sum_lesson,3){
    	@Override
		public boolean isCellEditable(int row, int column) {
		  return false;
		}
    };
	for(int i=0;i<sum_lesson-1;i++)
		for(int j=0;j<3;j++){
           newTableModel.setValueAt(tableModel.getValueAt(i, j), i, j);
		}
    lessonTable.setModel(newTableModel);
	tableModel = newTableModel;
    
	lessonTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/LockLabel.png")), sum_lesson-1, 0);
    lessonTable.setValueAt(lessonName, sum_lesson-1, 1);
    lessonTable.setValueAt("", sum_lesson-1, 2);
  
    lessonTable.getColumn("A").setCellEditor(lessonTable.getDefaultEditor(Icon.class));
    lessonTable.getColumn("A").setCellRenderer(lessonTable.getDefaultRenderer(Icon.class));
    
    lessonTable.getColumn("C").setCellEditor(lessonTable.getDefaultEditor(Icon.class));
    lessonTable.getColumn("C").setCellRenderer(lessonTable.getDefaultRenderer(Icon.class));

    }

    public void deleteLesson(String lessonName){

    }
    
    
    private class AddLessonButtonAdapter extends MouseAdapter{
    public void mouseClicked(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){
       addLesson("Lesson_"+(sum_lesson+1));
    }
    }
    
    public void setLearnedStatue(String lessonName){
	    for(int i=0; i<sum_lesson; i++){
    	   if (lessonTable.getValueAt(i, 1).equals(lessonName)){
    		    lessonTable.setValueAt("", sum_lesson-1, 0);
    		    lessonTable.setValueAt(lessonName, sum_lesson-1, 1);
    		    lessonTable.setValueAt(new ImageIcon(FilenameUtils.separatorsToSystem("resource/SmileLabel.png")), sum_lesson-1, 2);
    	   }
	    }
	}
    
    private class HistoryLabelAdapter extends MouseAdapter{
    	public void mouseClicked(MouseEvent e){
        }
        public void mousePressed(MouseEvent e){
           buildHistoryPanel();
        }
    }
    
    private class ListLabelAdapter extends MouseAdapter{
    	public void mouseClicked(MouseEvent e){
        }
        public void mousePressed(MouseEvent e){
           buildLessonPanel();
        }
    }
    
    private void buildHistoryPanel(){
  
    	historyPanel = new HistoryPanel();
    	if (lessonPanel != null) getContentPane().remove(lessonPanel);
    	repaint();
    	getContentPane().add(historyPanel);
    }
    
    private void buildLessonPanel(){
    	lessonPanel = new LessonPanel();
    	if (historyPanel != null) getContentPane().remove(historyPanel);
    	repaint();
    	getContentPane().add(lessonPanel);
    }
    
    
    public static void main(String args[]){
    	MainFrame test = new MainFrame();
    	test.setVisible(true);
    	test.addLesson("lesson_1");
    	test.setLearnedStatue("lesson_1");
    	test.addLesson("lesson_2");
    }
}
