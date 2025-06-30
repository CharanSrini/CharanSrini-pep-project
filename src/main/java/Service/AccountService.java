package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;
    
    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }

    public Account userRegistration(Account newUser){
        if(newUser.getUsername() != "" && newUser.getPassword().length() >= 4){
            return accountDAO.userRegistration(newUser);
        }
        return null;
    }

    public Account login(Account user){
        return accountDAO.login(user);
    }
}