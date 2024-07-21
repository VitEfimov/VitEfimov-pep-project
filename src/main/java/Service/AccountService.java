package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {

    public AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accauntDAO) {
        this.accountDAO = accauntDAO;
    }

    public Account signIn(Account account) {
        return accountDAO.signIn(account);
    }

    public Account logIn(Account account) {
        return accountDAO.logIn(account);
    }
}
