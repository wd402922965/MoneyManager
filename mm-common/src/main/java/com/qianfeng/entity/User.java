package com.qianfeng.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by dc on 2020/11/3.
 */
@Data
@Entity
@Table(name = "mo_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    private String id;

    @Column(name = "u_name")
    private String userName;//用户名

    @Column(name = "u_username")
    private String username;//姓名

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_email")
    private String email;

    @Column(name = "u_code")
    private Integer code;

    @Column(name = "u_pic")
    private String pic;

    @Column(name = "u_money")
    private Double money;

    @Column(name = "u_role")
    private Integer role;

    @Column(name = "f_id")
    private String fid;

    @Column(name = "u_age")
    private Integer age;

    @Column(name = "u_sex")
    private Integer sex;

    @Column(name = "u_phone")
    private String phone;

    @Column(name = "u_professional")
    private String professional;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProfessional() {
        return professional;
    }

    public void setProfessional(String professional) {
        this.professional = professional;
    }
}
