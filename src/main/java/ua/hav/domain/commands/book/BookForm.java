package ua.hav.domain.commands.book;

import ua.hav.domain.Book;
import ua.hav.domain.commands.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
public class BookForm implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("act", "Save book");
        request.setAttribute("book", new Book());
        return "bookform";
    }
}
