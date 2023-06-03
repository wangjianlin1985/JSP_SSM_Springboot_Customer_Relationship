package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class UserEmail {
    /*邮件id*/
    private Integer emailId;
    public Integer getEmailId(){
        return emailId;
    }
    public void setEmailId(Integer emailId){
        this.emailId = emailId;
    }

    /*邮件标题*/
    @NotEmpty(message="邮件标题不能为空")
    private String title;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    /*邮件内容*/
    @NotEmpty(message="邮件内容不能为空")
    private String content;
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

    /*接受客户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*发送时间*/
    @NotEmpty(message="发送时间不能为空")
    private String sendTime;
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonUserEmail=new JSONObject(); 
		jsonUserEmail.accumulate("emailId", this.getEmailId());
		jsonUserEmail.accumulate("title", this.getTitle());
		jsonUserEmail.accumulate("content", this.getContent());
		jsonUserEmail.accumulate("userObj", this.getUserObj().getName());
		jsonUserEmail.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonUserEmail.accumulate("sendTime", this.getSendTime().length()>19?this.getSendTime().substring(0,19):this.getSendTime());
		return jsonUserEmail;
    }}