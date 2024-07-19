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

    public Account userRegistration(Account account) {
        return accountDAO.userRegistration(account);
    }

    public Account login(Account account) {
        return accountDAO.login(account);
    }
}
