package ua.hav.ui.controller;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import ua.hav.domain.commands.Command;
import ua.hav.domain.commands.factory.CommandFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Юля on 11.08.2016.
 */
@WebServlet("/Library")
public class Controller extends HttpServlet {
    private static Logger LOGGER = Logger.getLogger(Controller.class);
    private Map<String, String> prevRequestParams;
    private String prevUrl;

    public void init(ServletConfig config) throws ServletException {
        ServletContext context = config.getServletContext();
        String root = context.getRealPath("/");
        String path = "/WEB-INF/log4j.properties";
        String fullPath = root + File.separator + path;

        PropertyConfigurator.configure(fullPath);
        LOGGER.info("Servlet inits");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Map<String, String> requestParms = getParamMap(request);
        System.out.println("request map = " + requestParms);
        System.out.println("prevRequest = " + prevRequestParams);

        if (!requestParms.isEmpty()) {

            if (areMapsEqual(requestParms, prevRequestParams)) {
                System.out.println("Nothing");
                putParams(request);
                request.getRequestDispatcher(prevUrl).forward(request, response);
                return;
            }
        }
        Command command = CommandFactory.getCommand(request);
        String goToUrl = command.execute(request, response) + ".jsp";
        if (goToUrl.contains("+")) {

            goToUrl = "WEB-INF/" + goToUrl.replace("+", "");
        }
        prevRequestParams = getParamMap(request);
        prevUrl = goToUrl;
        System.out.println("request map = " + request.getParameterMap());
        System.out.println("prevRequest = " + prevRequestParams);
        request.getRequestDispatcher(goToUrl).forward(request, response);
    }

    private void putParams(HttpServletRequest request) {
        for (String s : prevRequestParams.keySet()) {

            System.out.println("s = " + s + ", " + prevRequestParams.get(s));
        }

    }

    private Map<String, String> getParamMap(HttpServletRequest request) {
        Map<String, String> params = new HashMap<>();
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String name = parameterNames.nextElement();
            params.put(name, request.getParameter(name));
        }
        return params;
    }

    private boolean areMapsEqual(Map<String, String> m1, Map<String, String> m2) {
        if (m1 == null || m2 == null) {
            return false;
        }
        if (m1.size() != m2.size()) {
            return false;
        }
        Set<String> keys1 = m1.keySet();
        Set<String> keys2 = m2.keySet();
        if (keys1.size() != keys2.size()) {
            return false;
        }
        for (String key : keys1) {
            if (!m1.get(key).equals(m2.get(key))) {
                return false;
            }
        }
        return true;
    }
}
