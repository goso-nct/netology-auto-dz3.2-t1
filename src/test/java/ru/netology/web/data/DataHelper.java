package ru.netology.web.data;

import lombok.Value;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper
{
    private DataHelper() {}

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherAuthInfo(AuthInfo original) {
        return new AuthInfo("petya", "123qwerty");
    }

    public static AuthInfo getInvalidAuthInfo() {
        return new AuthInfo("mumu", "qwerty");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        String code = "12345";
        String sqlSelect = "select code " +
                "from auth_codes ac, users u " +
                "where ac.user_id = u.id and u.login='vasya'";

        try(
            var conn = DriverManager.getConnection("jdbc:mysql://192.168.8.10:3306/app", "app", "pass");
            var dataStmt = conn.createStatement();
            )
        {
            var rs = dataStmt.executeQuery(sqlSelect);
            while(rs.next()) {
                code = rs.getString("code");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return new VerificationCode(code);
    }

    public static String validCards[] = {"5559 0000 0000 0001", "5559 0000 0000 0002" };
    public static String invalidCard = "5559 0000 0000 0003";
}
