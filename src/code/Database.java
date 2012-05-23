/**
 * 
 */
package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author charmjunewonder
 *
 */
public class Database {

    private Account selectedAccount;
    private Connection connection;

    public Database(){
	try{
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection("jdbc:sqlite:data/account_names.db");
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS account_names (Name UNIQUE);");
	    /* account names
	    ResultSet rs = statement.executeQuery("SELECT * FROM account_names;");
	    while(rs.next()){
		accountNames.add(rs.getString("Name"));
	    }
	    rs.close();*/
	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    public void createAccount(String name) throws Exception{
	Statement statement = connection.createStatement();
	statement.executeUpdate("INSERT INTO account_names VALUES (" + name + ");");
	Account a = new Account(name);
	a.loadDefaultLessons();
    }

    public void deleteAccount(int index){
	try{
	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery(
		    "SELECT * FROM account_names LIMIT 1 OFFSET "+index+";");
	    String name = result.getString("Name");
	    Account a = new Account(name);
	    a.deleteSelf();
	    statement.executeUpdate("DELETE FROM account_names WHERE Name = " + name + ";");

	}catch(Exception e){
	    System.out.println(e.toString());
	}
    }

    // just query a small amount of date, do not need a field to store the names;
    public ArrayList<String> getAllAccountNames(){
	ArrayList<String> accountNames = new ArrayList<String>();
	try{
	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery("SELECT * FROM account_names ;");
	    String name = result.getString("Name");
	    accountNames.add(name);
	}catch(Exception e){
	    System.out.println(e.toString());
	}
	return accountNames;
    }
}
