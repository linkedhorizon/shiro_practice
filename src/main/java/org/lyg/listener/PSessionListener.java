package org.lyg.listener;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;

/**
 * @author :lyg
 * @time :2018/8/4 0004
 */
public class PSessionListener implements SessionListener {
    @Override
    public void onStart(Session session) {
        System.out.println("listener会话开始");
    }

    @Override
    public void onStop(Session session) {
        System.out.println("listener会话结束");
    }

    @Override
    public void onExpiration(Session session) {
        System.out.println("listener会话过期");
    }
}
