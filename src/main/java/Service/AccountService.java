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

    public Account registerUser(Account account) {
        return accountDAO.registerUser(account);
    }

    public Account loginUser(Account account) {
        return accountDAO.loginUser(account);
    }
}
