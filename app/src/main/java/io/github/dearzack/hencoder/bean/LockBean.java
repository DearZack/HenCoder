package io.github.dearzack.hencoder.bean;

/**
 * Created by Zack on 2017/8/14.
 */

public class LockBean {
    private String name;

    public LockBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LockBean{" +
                "name='" + name + '\'' +
                '}';
    }
}
