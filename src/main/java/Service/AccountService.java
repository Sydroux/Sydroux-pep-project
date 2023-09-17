package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    public Account registerAccount(Account account) {
        return accountDAO.registerAccount(account);
    }

    public Account loginAttempt(Account account) {
        return accountDAO.getAccountByUsernamePassword(account);
    }

    public Account checkForUser(int id) {
        return accountDAO.getAccountById(id);
    }
}
