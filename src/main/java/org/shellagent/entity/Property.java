package org.shellagent.entity;

import java.io.Serializable;

public class Property implements Serializable {
    private String type;

    private String key;

    private String val;

    private Integer seq;

    private static final long serialVersionUID = 1L;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val == null ? null : val.trim();
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public Property(String type,String key,String val) {
        setType(type);
        setKey(key);
        setVal(val);
    }
    public Property() {}
}
