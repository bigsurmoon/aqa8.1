package ru.netology.data;

import com.github.javafaker.Faker;
import com.mysql.cj.x.protobuf.MysqlxCrud;
import lombok.SneakyThrows;
import lombok.Value;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.DriverManager;
import java.sql.SQLException;

public class DataHelper {

    public DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }


    @Value
    public static class VerificationCode {
        private String code;
    }

    @SneakyThrows
    public static VerificationCode getVerificationCodeFor(String login) {
        var codesSQL = "SELECT code FROM auth_codes ORDER BY created DESC;";
        var runner = new QueryRunner();
        String code;
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app_db", "kaas", "1911"
                );
        ) {
            code = runner.query(conn, codesSQL, new ScalarHandler<>());
        }
        return new VerificationCode(code);
    }

    @SneakyThrows
    public static void clearData() {
        var runner = new QueryRunner();
        try (
                var conn = DriverManager.getConnection(
                        "jdbc:mysql://localhost:3306/app_db", "kaas", "1911"
                );
        ) {
            runner.execute(conn, "DELETE FROM auth_codes;");
            runner.execute(conn, "DELETE FROM card_transactions;");
            runner.execute(conn, "DELETE FROM cards;");
            runner.execute(conn, "DELETE FROM users;");
        }
    }
}