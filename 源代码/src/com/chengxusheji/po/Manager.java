﻿package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Manager {
    /*管理用户名*/
    @NotEmpty(message="管理用户名不能为空")
    private String managerUserName;
    public String getManagerUserName(){
        return managerUserName;
    }
    public void setManagerUserName(String managerUserName){
        this.managerUserName = managerUserName;
    }

    /*登录密码*/
    @NotEmpty(message="登录密码不能为空")
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*姓名*/
    @NotEmpty(message="姓名不能为空")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*性别*/
    @NotEmpty(message="性别不能为空")
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*年龄*/
    @NotNull(message="必须输入年龄")
    private Integer age;
    public Integer getAge() {
        return age;
    }
    public void setAge(Integer age) {
        this.age = age;
    }

    /*所在部门*/
    private Department departmentObj;
    public Department getDepartmentObj() {
        return departmentObj;
    }
    public void setDepartmentObj(Department departmentObj) {
        this.departmentObj = departmentObj;
    }

    /*管理备注*/
    private String managerMemo;
    public String getManagerMemo() {
        return managerMemo;
    }
    public void setManagerMemo(String managerMemo) {
        this.managerMemo = managerMemo;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonManager=new JSONObject(); 
		jsonManager.accumulate("managerUserName", this.getManagerUserName());
		jsonManager.accumulate("password", this.getPassword());
		jsonManager.accumulate("name", this.getName());
		jsonManager.accumulate("sex", this.getSex());
		jsonManager.accumulate("age", this.getAge());
		jsonManager.accumulate("departmentObj", this.getDepartmentObj().getDepartmentName());
		jsonManager.accumulate("departmentObjPri", this.getDepartmentObj().getDepartmentNo());
		jsonManager.accumulate("managerMemo", this.getManagerMemo());
		return jsonManager;
    }}