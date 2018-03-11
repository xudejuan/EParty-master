package com.example.liu.eparty.bean;

import java.io.Serializable;

/**
 * author：liu
 * email：413853428@qq.com
 * version：
 * date：2018/1/7:17:58
 * introduction：
 */

public class Member implements Serializable{

    private int memberId;
    private String name;
    private String phone;
    private String identity;

    public Member(String name, String phone, String identity) {
        this.name = name;
        this.phone = phone;
        this.identity = identity;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }
}
