package ua.hav.dao;

import ua.hav.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Юля on 13.08.2016.
 */
public class UserDao extends Dao<User> {

    private static final String LOGIN = "SELECT id FROM users WHERE login=? AND password=?";

    UserDao(Class clazz) {
            super(clazz);
    }


    public synchronized boolean login(String login, String password) {
        boolean result = false;
//        if (getClazz() == User.class) {
            try (Connection cn = DAOFactory.getConnection()){
                PreparedStatement st = cn.prepareStatement(LOGIN);
                st.setString(1, login);
                st.setString(2, password);
                ResultSet rs = st.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
//        }
        return result;
    }
}
