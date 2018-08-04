package org.lyg.session;

import org.apache.shiro.session.mgt.SimpleSession;

/**
 * @author :lyg
 * @time :2018/8/4 0004
 */
public class DSession extends SimpleSession {
    // 用户浏览器类型
    private String userAgent;

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

}
