package code;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author Eric
 * @version 0.1
 */

public class LearnEnglishSystem {
    private ArrayList<Account> accounts;
    private Set<String> accountNames;

    public LearnEnglishSystem(){
    }

    private void addAccount(Account a){
	accounts.add(a);
    }

    public void createNewAccount(String accountName){
	if (accountNames.add(accountName)){
	    Account a = new Account(accountName);
	    accounts.add(a);
	}
    }
    public static void main(String[] args) {

    }
}
