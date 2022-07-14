package com.xu1an.common;

/**
 * Created with IntelliJ IDEA.
 *
 * @Author: Xu1Aan
 * @Date: 2022/07/11/18:38
 * @Description:  TODO 这个key  value的存在必要是什么？ map不能使用么  对象统计使用的
 */
public class KeyValue {
    private String key;
    private String value;

    public KeyValue(final String key, final String value) {
        this.key = key;
        this.value = value;
    }


    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(final String value) {
        this.value = value;
    }
}
