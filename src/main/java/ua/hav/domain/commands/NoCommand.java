package ua.hav.domain.commands;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
public class NoCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("------ No Command !!! ------");
        System.out.println("request.getSession() = " + request.getSession(true));
        request.getSession().invalidate();
        System.out.println("request.getSession() = " + request.getSession(true));
//        IDao<User> userDao = new Dao<>(User.class);
//        System.out.println(userDao.findAll());
//        System.out.println(userDao.find(2));
//        System.out.println(userDao.find(5));
//        userDao.delete(5);
//        userDao.delete(19);
//        User u = userDao.find(18);
//        u.setName("&&&&&&&&&&&");
//        u.setAccount(777);
//        u.setPassword("---");
//        u.setLogin("09876");
//        userDao.update(u);
//        User user = new User("newUser", "psw$$$", "name ewn iuht", 155);
//        userDao.save(user);
//        System.out.println(userDao.findAll());

        return "index";
    }
}
