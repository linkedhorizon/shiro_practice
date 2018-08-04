package org.lyg.session;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.ValidatingSession;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.lyg.util.ByteSourceUtils;
import org.lyg.util.RedisUtil;

import java.io.Serializable;

/**
 * @author :lyg
 * @time :2018/8/4 0004
 */
public class DSessionDao extends CachingSessionDAO {
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        }else{
            return ByteSourceUtils.serialize((Serializable) key);
        }
    }
    // 会话key
    private String keyPrefix = "shiro_redis_session:";

    @Override
    protected void doUpdate(Session session) {


        System.out.println("dao会话更新");
    }

    @Override
    protected void doDelete(Session session) {


        System.out.println("dao会话删除");
    }

    @Override
    protected Serializable doCreate(Session session) {
        // 生成会话 id
        Serializable sessionId = generateSessionId(session);
        // 分派 sessionID
        assignSessionId(session, sessionId);
        System.out.println("dao会话生成");
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        byte[] bytes = getByteKey(sessionId);
        byte[] value = RedisUtil.get(bytes);
        if(value == null){
            return null;
        }
        return (Session)ByteSourceUtils.deserialize(value);
    }
}
