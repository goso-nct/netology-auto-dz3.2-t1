package ru.netology.web.data;

import lombok.Value;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper
{
    private static String dbUrl;
    private static String dbUser;
    private static String dbPass;

    static {
        dbUrl  = System.getenv("DB_URL");
        dbUser = System.getenv("DB_USER");
        dbPass = System.getenv("DB_PASS");

        dbUrl  = dbUrl  == null ? "jdbc:mysql://localhost:3306/app" : dbUrl;
        dbUser = dbUser == null ? "app" : dbUser;
        dbPass = dbPass == null ? "pass" : dbPass;
    }

    private DataHelper() {}

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }
    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }
    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("mumu", "mumu");
    }
    public static AuthInfo getAuthInfoWithInvalidPass() {
        return new AuthInfo("vasya", "mumu");
    }

    @Value
    public static class VerificationCode {
        String code;
    }
    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode(getVerificationCodeFromDb(authInfo.login));
    }
    public static VerificationCode getInvalidVerificationCodeFor(AuthInfo authInfo) {
        // заведомо неверный код: верный + 1
        String code = getVerificationCodeFromDb(authInfo.login);
        int iCode = Integer.valueOf(code);
        code = String.valueOf(iCode + 1).substring(0, 6); // 999_999 + 1 = 100_000
        return new VerificationCode(code);
    }

    public static String getVerificationCodeFromDb(String login) {
        String code = "";
        String sqlGetLastCodeByLogin =
                "SELECT ac.code"
                + "  FROM auth_codes ac, users u"
                + " WHERE ac.user_id = u.id and u.login=?"
                + " ORDER BY ac.created DESC"
                + " LIMIT 1"
                ;
        try(
            var conn = DriverManager.getConnection(dbUrl, "app", "pass");
            var dataStmt = conn.prepareStatement(sqlGetLastCodeByLogin);
        ){
            dataStmt.setString(1, login);
            var rs = dataStmt.executeQuery();
            rs.next();
            code = rs.getString(1);
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return code;
    }

    public static void truncateAuthCode() {
        try(
            var conn = DriverManager.getConnection(dbUrl, "app", "pass");
            var dataStmt = conn.createStatement();
        ){
            dataStmt.execute("TRUNCATE auth_codes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteDbData() {
        try(
            var conn = DriverManager.getConnection(dbUrl, "app", "pass");
            var dataStmt = conn.createStatement();
        ){
            dataStmt.execute("TRUNCATE card_transactions");
            dataStmt.execute("TRUNCATE cards");
            dataStmt.execute("TRUNCATE auth_codes");
            dataStmt.execute("DELETE FROM users");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}



