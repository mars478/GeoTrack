package com.mars.geotrack;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = {"/track", "/getPos"}, loadOnStartup = 1)
public class TrackServlet extends HttpServlet {

    static TrackServlet instance = null;

    @Override
    public synchronized void init() throws ServletException {
        super.init();
        instance = this;
    }

    public static TrackServlet getInstance() {
        return instance;
    }

    public double posX = -1.0f;
    public double posY = -1.0f;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
        posX = Double.parseDouble(req.getParameter("data[posX]"));
        posY = Double.parseDouble(req.getParameter("data[posY]"));
        System.out.println("recieved: " + posX + " - " + posY);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            String ret = "undefined";
            if (posX != -1.0f && posY != -1.0f) {
                ret = "{\"posX\":" + posX + ", \"posY\":" + posY + "}";
            }
            out.println(ret);
        }
    }
}
