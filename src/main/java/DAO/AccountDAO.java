package DAO;

import java.sql.*;
import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {

    private static boolean accountIsExist(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        if (account.getPassword().length() >= 4 && !account.getUsername().isEmpty()) {
            try {
                String sqlCheck = "select * from account where username = ?";
                PreparedStatement psAccountIsExist = connection.prepareStatement(sqlCheck);
                psAccountIsExist.setString(1, account.getUsername());
                ResultSet rs = psAccountIsExist.executeQuery();
                if (rs.next()) {
                    return false;
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return true;
    }

    public Account userRegistration(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        if (accountIsExist(account)) {
            try {
                String sql = "insert into account (username,password) values(?,?)";
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPassword());
                ps.executeUpdate();
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    account.setAccount_id(generatedKeys.getInt(1));
                }
                return account;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return null;
    }

    public Account login(Account account) {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username = ? and password = ?";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Account(
                        rs.getInt("account_id"),
                        rs.getString("username"),
                        rs.getString("password"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
