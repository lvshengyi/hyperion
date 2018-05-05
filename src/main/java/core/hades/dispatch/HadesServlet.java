package core.hades.dispatch;

import com.alibaba.fastjson.JSON;
import com.sun.deploy.util.ReflectionUtil;
import common.utils.CodecUtil;
import common.utils.StreamUtil;
import core.zeus.bean.BeanContainer;
import core.zeus.bean.ZeusContext;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LvShengyI
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class HadesServlet extends HttpServlet {

    private static final String JSP_PATH = "/WEB-INF/view/";

    private static final String ASSET_PATH = "/asset/";

    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        BeanContainer.ini();
        ServletContext servletContext = servletConfig.getServletContext();
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping(JSP_PATH + "*");
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        defaultServlet.addMapping(ASSET_PATH + "*");
    }

    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String requestMethodName = request.getMethod().toLowerCase();
        String requestPath = request.getPathInfo();
        Handler handler = ActionHandler.getHandler(requestMethodName, requestPath);

        try {
            if (handler != null) {
                Class controllerClass = handler.getControllerClass();
                Object controllerBean = BeanContainer.getBean(controllerClass.getName());
                Map<String, Object> paramMap = new HashMap<>(16);
                Enumeration<String> paramNames = request.getParameterNames();

                while (paramNames.hasMoreElements()) {
                    String paramName = paramNames.nextElement();
                    String paramValue = request.getParameter(paramName);
                    paramMap.put(paramName, paramValue);
                }

                String body = CodecUtil.decodeURL(StreamUtil.getString(request.getInputStream()));
                if (StringUtils.isNotEmpty(body)) {
                    String[] params = body.split("&");
                    for (String param : params) {
                        String[] arr = param.split("=");
                        String paramName = arr[0];
                        String paramValue = arr[1];
                        paramMap.put(paramName, paramValue);
                    }
                }

                Param param = new Param(paramMap);
                Method actionMethod = handler.getActionMethod();

                actionMethod.setAccessible(true);
                Object result = actionMethod.invoke(controllerBean, param);

                if(result instanceof View){
                    View view = (View)result;
                    String path = view.getPath();
                    if(path.startsWith("/")){
                        response.sendRedirect(request.getContextPath() + path);
                    }else{
                        Map<String, Object> model = view.getModel();
                        for(Map.Entry<String, Object> entry : model.entrySet()){
                            request.setAttribute(entry.getKey(), entry.getValue());
                        }
                        request.getRequestDispatcher(JSP_PATH + path).forward(request, response);
                    }
                }else if(result instanceof Data){
                    Data data = (Data)result;
                    Object model = data.getModel();
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter writer = response.getWriter();
                    String json = JSON.toJSONString(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ServletException e) {
            e.printStackTrace();
        }
    }
}
