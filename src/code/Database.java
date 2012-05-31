/**
 * 
 */
package code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.commons.io.FilenameUtils;

/**
 * @author Eric
 * @version 0.1
 */

public class Database {

    private Account selectedAccount;
    private Connection connection;

    public Database(){
	try{
	    Class.forName("org.sqlite.JDBC");
	    connection = DriverManager.getConnection(
		    FilenameUtils.separatorsToSystem("jdbc:sqlite:data/account_names.db"));
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS account_names (Name UNIQUE);");
	    /* account names
	    ResultSet rs = statement.executeQuery("SELECT * FROM account_names;");
	    while(rs.next()){
		accountNames.add(rs.getString("Name"));
	    }
	    rs.close();*/
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    // use for test
    public Database(Connection connection){
	try{
	    Class.forName("org.sqlite.JDBC");
	    this.connection = connection;
	    Statement statement = connection.createStatement();
	    statement.executeUpdate("CREATE TABLE IF NOT EXISTS account_names (Name UNIQUE);");
	}catch(Exception e){
	    e.printStackTrace();
	}
    }

    public Account createAccount(String name)
	    throws SQLException, InvalidFileNameException{
	//Statement statement = connection.createStatement();
	Account a = new Account(name);
	PreparedStatement prep = connection.prepareStatement("INSERT INTO account_names VALUES (?)");
	prep.setString(1, name);
	prep.executeUpdate();

	return a;
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
	    e.printStackTrace();
	}
    }

    // just query a small amount of date, do not need a field to store the names;
    public ArrayList<String> getAllAccountNames(){
	ArrayList<String> accountNames = new ArrayList<String>();
	try{
	    Statement statement = connection.createStatement();
	    ResultSet result = statement.executeQuery("SELECT * FROM account_names ;");
	    while(result.next()){
		String name = result.getString("Name");
		accountNames.add(name);
	    }
	    result.close();
	}catch(Exception e){
	    e.printStackTrace();
	}
	return accountNames;
    }
}
