package ua.hav.domain.commands.factory;

import org.apache.log4j.Logger;
import ua.hav.domain.commands.*;
import ua.hav.domain.commands.book.*;
import ua.hav.domain.commands.genre.*;
import ua.hav.domain.commands.phone.PhoneList;
import ua.hav.domain.commands.user.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Юля on 12.08.2016.
 */
public class CommandFactory {
    private static Map<String, Command> commands = new HashMap<String, Command>();
    private static Logger logger = Logger.getLogger(CommandFactory.class);
    private static Command noCommand = new NoCommand();

    static {
        commands.put("Login", new Login());
        commands.put("Main page", new MainPage());

        commands.put("User list", new UserList());
        commands.put("User form", new UserForm());
        commands.put("Reg page", new UserForm());
        commands.put("Register user", new Register());
        commands.put("Edit user", new UserEdit());
        commands.put("Update user", new UpdateUser());
        commands.put("Delete user", new DeleteUser());
        commands.put("Filter user", new FilterUser());
        commands.put("ResetFilter user", new ResetFilterUser());

        commands.put("Book list", new BookList());
        commands.put("Book form", new BookForm());
        commands.put("Save book", new SaveBook());
        commands.put("Update book", new UpdateBook());
        commands.put("Edit book", new BookEdit());
        commands.put("Delete book", new DeleteBook());
        commands.put("Filter book", new SearchByFieldsBook());
        commands.put("ResetFilter book", new ResetFilterBook());

        commands.put("Genre list", new GenreList());
        commands.put("Genre form", new GenreForm());
        commands.put("Save genre", new SaveGenre());
        commands.put("Edit genre", new GenreEdit());
        commands.put("Update genre", new UpdateGenre());
        commands.put("Delete genre", new DeleteGenre());

        commands.put("Phone list", new PhoneList());
    }

    public static Command getCommand(HttpServletRequest request) {
        String name = request.getParameter("command");
        logger.debug("requested command: " + name);
        Command command = commands.get(name);
        if (command == null) {
            command = noCommand;
        }
        logger.debug("command to execute: " + command);
        return command;
    }
}
