package servlets;

import Objects.User;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;


import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class Controller
{
    static Connection _connection;

    static
    {
        try
        {
            _connection = DriverManager.getConnection("jdbc:postgresql://207.154.234.136:5432/1920-Automarkt",
                    "1920-Automarkt", "caccfc046d179b6f792f841568dbb013");
        } catch (SQLException throwables)
        {
            throwables.printStackTrace();
		}
    }

    public Controller() throws SQLException
    {
    }

    public static User LoginUser(String loginEmailAddress, String password) throws SQLException
    {
        if (!UserExists(loginEmailAddress))
        {
            //Logger.DebugLog("Login: Mail not found");
            return null;
        }

        String query = GetAuthenticationQuery(loginEmailAddress);

        //Logger.DebugLog(String.format("Login Query: %s", query));

        Statement userPasswordHash = _connection.createStatement();
        ResultSet userData = userPasswordHash.executeQuery(query);
        userData.next();

        //Logger.DebugLog(String.format("Found password Hash: %s", userData.getString("passwordhash")));

        if (VerifyPassword(password, userData.getString("passwordhash")))
        {
            return new User(userData);
        } else
        {
            return null;
        }
    }

    private static boolean VerifyPassword(String inputPassword, String realPasswordHash)
    {
        return BCrypt.verifyer().verify(inputPassword.getBytes(StandardCharsets.UTF_8),
                realPasswordHash.getBytes(StandardCharsets.UTF_8)).verified;
    }

    private static String GetAuthenticationQuery(String loginEmailAddress)
    {
        return String.format("select * from users where loginEmailAddress = \'%s\';", loginEmailAddress);
    }

    private static boolean UserExists(String loginEmailAddress) throws SQLException
    {
        Statement userExists = _connection.createStatement();
        userExists.execute(String.format("select id from users where loginEmailAddress = \'%s\';", loginEmailAddress));

        return userExists.getResultSet().next();
    }

    public static User InsertUser(String firstName, String lastName, String loginEmailAddress, String password) throws SQLException
    {
        if (UserExists(loginEmailAddress))
        {
            return null;
        }

        String passwordHash = GetPasswordHash(password);
        String query = GetInsertQuery(firstName, lastName, loginEmailAddress, passwordHash);

        //Logger.DebugLog("Query: " + query);

        Statement insertUser = _connection.createStatement();
        ResultSet insertResult = insertUser.executeQuery(query);
        insertResult.next();
        return new User(insertResult.getInt(1), firstName, lastName, loginEmailAddress, passwordHash);
    }

    private static String GetPasswordHash(String password)
    {
        return new String(BCrypt.withDefaults().hash(10, Bytes.random(16).array(), password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }

    private static String GetInsertQuery(String firstName, String lastName, String loginEmailAddress, String passwordHash)
    {
        return String.format("insert into users (firstName, lastName, loginEmailAddress, " +
                        "passwordHash) values (\'%s\', \'%s\', \'%s\', \'%s\') returning " +
                        "id;",
                firstName, lastName, loginEmailAddress, passwordHash);
    }


}


