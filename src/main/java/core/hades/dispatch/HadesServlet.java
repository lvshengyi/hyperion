package core.hades.dispatch;

import core.zeus.bean.BeanContainer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServlet;

/**
 * @author LvShengyI
 */
public class HadesServlet extends HttpServlet{

    private static final String JSP_PATH = "/WEB-INF/view/";

    private static final String ASSET_PATH = "/asset/";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException{
        BeanContainer.ini();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(JSP_PATH + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ASSET_PATH + "*");
    }
}
