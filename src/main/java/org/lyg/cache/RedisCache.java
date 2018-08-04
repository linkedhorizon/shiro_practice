package org.lyg.cache;

import org.apache.shiro.cache.CacheException;
import org.lyg.util.ByteSourceUtils;
import org.lyg.util.RedisUtil;

import java.io.Serializable;
import java.util.*;

/**
 * @author :lyg
 * @time :2018/8/3 0003
 */
public class RedisCache<K,V> implements  org.apache.shiro.cache.Cache<K,V> {

    private String keyPrefix = "shiro_redis_session:";

    public String getKeyPrefix() {
        return keyPrefix;
    }

    public void setKeyPrefix(String keyPrefix) {
        this.keyPrefix = keyPrefix;
    }

    /**
     * 获得byte[]型的key
     * @param key
     * @return
     */
    private byte[] getByteKey(Object key){
        if(key instanceof String){
            String preKey = this.keyPrefix + key;
            return preKey.getBytes();
        }else{
            return ByteSourceUtils.serialize((Serializable) key);
        }
    }


    @Override
    public Object get(Object key) throws CacheException {

        byte[] bytes = getByteKey(key);
        byte[] value = RedisUtil.get(bytes);
        if(value == null){
            return null;
        }
        return ByteSourceUtils.deserialize(value);
    }

    /**
     * 将shiro的缓存保存到redis中
     */
    @Override
    public Object put(Object key, Object value) throws CacheException {
        RedisUtil.set(getByteKey(key),ByteSourceUtils.serialize((Serializable)value));
        byte[] bytes = RedisUtil.get(getByteKey(key));
        Object object = ByteSourceUtils.deserialize(bytes);
        return object;

    }

    @Override
    public Object remove(Object key) throws CacheException {

        byte[] bytes = RedisUtil.get(getByteKey(key));
        RedisUtil.del(getByteKey(key));

        return ByteSourceUtils.deserialize(bytes);
    }

    /**
     * 清空所有缓存
     */
    @Override
    public void clear() throws CacheException {
        RedisUtil.flushdb();
    }

    /**
     * 缓存的个数
     */
    @Override
    public int size() {
        Long size = RedisUtil.dbsize();
        return size.intValue();
    }

    /**
     * 获取所有的key
     */
    @Override
    public Set keys() {
        Set<byte[]> keys = RedisUtil.keys("*");
        Set<Object> set = new HashSet<Object>();
        for (byte[] bs : keys) {
            set.add(ByteSourceUtils.deserialize(bs));
        }
        return set;
    }


    /**
     * 获取所有的value
     */
    @Override
    public Collection values() {
        Set keys = this.keys();

        List<Object> values = new ArrayList<Object>();
        for (Object key : keys) {
            byte[] bytes = RedisUtil.get(getByteKey(key));
            values.add(ByteSourceUtils.deserialize(bytes));
        }
        return values;
    }
}
