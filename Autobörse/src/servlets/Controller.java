package servlets;

import Objects.Inserat;
import Objects.User;

import at.favre.lib.bytes.Bytes;
import at.favre.lib.crypto.bcrypt.BCrypt;


import javax.jms.Message;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;


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

    public static User GetUser(String loginEmailAddress) throws SQLException
    {
        String query = GetAuthenticationQuery(loginEmailAddress);
        Statement userSelect = _connection.createStatement();

        ResultSet userData = userSelect.executeQuery(query);
        userData.next();
        User user = new User(userData);
        user.passwordHash = "";
        return user;
    }

    public static User GetUser(int UserId) throws SQLException
    {
        String query = GetAuthenticationQuery(UserId);
        Statement userSelect = _connection.createStatement();

        ResultSet userData = userSelect.executeQuery(query);
        userData.next();
        return new User(userData);
    }

    public static boolean UpdateUser(int userId, String UserName, String FirstName, String LastName) throws SQLException
    {
        String query = GetUpdateUserQuery(userId, UserName, FirstName, LastName);

        Statement UserUpdate = _connection.createStatement();

        //executeUpdate returned die Anzahl der geupdateten datensaetze
        //Sollte also immer 1 sein wenn ein user geupdated wird
        //Wenn es 0 ist existiert der user nicht
        return UserUpdate.executeUpdate(query) == 1;

    }

    public static boolean ResetUserPassword(User user) throws SQLException, MessagingException
    {
        return resetUserPassword(user.id, user.userName);
    }

    public static boolean ResetUserPassword(int userId, String userName) throws SQLException, MessagingException
    {
        return resetUserPassword(userId, userName);

    }

    private static boolean resetUserPassword(int UserId, String userName) throws SQLException, MessagingException
    {
        String newPassword = getRandomPassword(8);
        String passwordHash = GetPasswordHash(newPassword);

        String query = GetUpdateUserPasswordQuery(UserId);

        Statement UpdateUserPasword = _connection.createStatement();

        if(UpdateUserPasword.executeUpdate(query) == 1)
        {

            Properties prop = new Properties();
            prop.put("mail.smtp.auth", true);
            prop.put("mail.smtp.starttls.enable", "true");
            prop.put("mail.smtp.host", "MAILSERVER");
            prop.put("mail.smtp.port", "25");
            prop.put("mail.smtp.ssl.trust", "MAILSERVER");

            Session session = Session.getInstance(prop, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("MAILUSERNAME", "MAILPASSWORT");
                }
            });

            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(userName));

                message.addRecipient(javax.mail.Message.RecipientType.TO,new InternetAddress(userName));
                message.setSubject("Autobörse: Passwort zurücksetzen");

                message.setText("Ihr neues Passwort lautet: " + newPassword);

                Transport.send(message);

            }
            catch (MessagingException e)
            {
                e.printStackTrace();
                return false;

            }
            //Email
            return true;
        }
        else
        {
            //Hat nicht hingehauen
            return false;
        }
    }

    private static String GetUpdateUserPasswordQuery(int UserId)
    {
        return String.format("update users set passwordhash = \'%s\' where id = \'%s\'");

    }

    private static String getRandomPassword(int length)
    {
        String result = "";
        Random r = new Random();
        for(int i =0; i < length; i++)
        {
            result = result + r.nextInt(10);

        }

        return result;
    }

    private static String GetUpdateUserQuery(int userId, String UserName, String firstName, String LastName)
    {
        return String.format("update users set loginemailaddress = \'%s\' firstname = \'%s\' lastname = \'%s\' where " +
                "id = \'%s\'", UserName, firstName, LastName, userId);
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

    private static String GetAuthenticationQuery(int userID)
    {
        return String.format("select * from users where id = \'%s\';", userID);
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
        String query = GetInsertUserQuery(firstName, lastName, loginEmailAddress, passwordHash);

        //Logger.DebugLog("Query: " + query);

        Statement insertUser = _connection.createStatement();
        ResultSet insertResult = insertUser.executeQuery(query);
        insertResult.next();
        return new User(insertResult.getInt(1), firstName, lastName, loginEmailAddress, passwordHash);
    }

    public static ArrayList<Inserat> ReadInserateForUser(User user) throws SQLException
    {
        return ReadInserateForUser(user.id);

    }

    public static ArrayList<Inserat> ReadInserateForUser(int UserId) throws SQLException
    {
        String query = GetReadInserateForUserQuery(UserId);

        Statement ReadInseratForUser = _connection.createStatement();
        ResultSet inserate = ReadInseratForUser.executeQuery(query);
        inserate.next();

        ArrayList<Inserat> returnList = new ArrayList<>();
        while(inserate.next())
        {
            returnList.add(new Inserat(inserate));
        }

        return returnList;
    }

    public static Inserat InsertInserat(String beschreibung, String marke, int PS, float verbrauch, int groesse, int kilometerstand,
                                        String verbrauchsstoff, String bezeichnung, String ausstattung) throws SQLException
    {
        String query = GetInsertInseratQuery(beschreibung, marke, PS, verbrauch, groesse, kilometerstand, verbrauchsstoff,
                bezeichnung, ausstattung);

        Statement insertInserat = _connection.createStatement();
        ResultSet insertResult = insertInserat.executeQuery(query);
        insertResult.next();
        return new Inserat(insertResult);

    }

    private static String GetPasswordHash(String password)
    {
        return new String(BCrypt.withDefaults().hash(10, Bytes.random(16).array(), password.getBytes(StandardCharsets.UTF_8)),
                StandardCharsets.UTF_8);
    }

    private static String GetInsertUserQuery(String firstName, String lastName, String loginEmailAddress, String passwordHash)
    {
        return String.format("insert into users (firstName, lastName, loginEmailAddress, " +
                        "passwordHash) values (\'%s\', \'%s\', \'%s\', \'%s\') returning " +
                        "id;",
                firstName, lastName, loginEmailAddress, passwordHash);
    }

    private static String GetInsertInseratQuery(String beschreibung, String marke, int PS, float verbrauch, int groesse,
                                                int kilometerstand, String Verbrauchsstoff, String Bezeichnung, String Ausstattung)
    {
        return String.format("insert into inserate (Beschreibung, marke, PS, verbrauch, groesse, kilometerstand," +
                "Verbrauchsstoff, Bezeichnung, Ausstattung) values (\'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\', \'%s\'" +
                        ", \'%s\') returning id;",
                beschreibung, marke, PS, verbrauch,groesse, kilometerstand, Verbrauchsstoff, Bezeichnung, Ausstattung);
    }

    private static String GetReadInserateForUserQuery(int UserId)
    {
        return String.format("select inserate.* from user_inserate join inserate on inserate.id = user_inserate.inseratid " +
                "join users on users.id = user_inserate.userid where user_inserate.userid = \'%s\';", UserId);
    }

}


