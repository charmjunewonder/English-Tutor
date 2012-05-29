package code;

public class TestAbstractFrame extends AbstractFrame{
      public TestAbstractFrame(String filePath){
    	  super(filePath);
      }
      
      public static void main(String args[]){
    	  TestAbstractFrame  test = new TestAbstractFrame("resource/ResultFrame.png"); 
    	  test.setVisible(true);
      }
}
