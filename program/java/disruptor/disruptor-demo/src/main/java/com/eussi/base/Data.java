package com.eussi.base;

/**
 * Created by wangxueming on 2019/6/20.
 */
public class Data {
    private int id;
    private String value;

    public Data(int id, String value) {
        this.id = id;
        this.value = value;
    }

    @Override
    public String toString() {
        return "Data{" +
                "id=" + id +
                ", value='" + value + '\'' +
                '}';
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
