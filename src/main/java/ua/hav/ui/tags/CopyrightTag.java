package ua.hav.ui.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * Created by Юля on 12.08.2016.
 */
public class CopyrightTag extends TagSupport {
    private String name;
    private String year;

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(String year) {
        this.year = year;
    }

    @Override
    public int doStartTag() {
        JspWriter out = pageContext.getOut();
        try {
            out.print("\t<hr>\r\n");
            out.print("\t\t<center>\r\n");
            out.print("\t\t\tCopyright by (c) " + name + ", " + year + "\r\n");
            out.print("\t\t</center>\r\n");
            out.print("\t<br>\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return EVAL_PAGE;
    }
}
