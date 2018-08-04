package org.lyg.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;

import javax.servlet.http.HttpServletRequest;

/**
 * @author :lyg
 * @time :2018/8/4 0004
 */
public class DSessionFactory implements SessionFactory {
    @Override
    public Session createSession(SessionContext sessionContext) {
        DSession session = new DSession();
        if(null != sessionContext && sessionContext instanceof WebSessionContext){
            WebSessionContext webSessionContext = (WebSessionContext) sessionContext;
            HttpServletRequest request = (HttpServletRequest) webSessionContext.getServletRequest();
            if (null != request) {
                session.setHost(request.getRemoteAddr());
                session.setUserAgent(request.getHeader("User-Agent"));
            }
        }
        return session;
    }
}
