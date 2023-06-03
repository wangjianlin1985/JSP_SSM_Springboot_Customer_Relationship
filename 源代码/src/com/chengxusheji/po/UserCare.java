package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class UserCare {
    /*关怀id*/
    private Integer careId;
    public Integer getCareId(){
        return careId;
    }
    public void setCareId(Integer careId){
        this.careId = careId;
    }

    /*关怀主题*/
    @NotEmpty(message="关怀主题不能为空")
    private String careTitle;
    public String getCareTitle() {
        return careTitle;
    }
    public void setCareTitle(String careTitle) {
        this.careTitle = careTitle;
    }

    /*关怀内容*/
    @NotEmpty(message="关怀内容不能为空")
    private String careContent;
    public String getCareContent() {
        return careContent;
    }
    public void setCareContent(String careContent) {
        this.careContent = careContent;
    }

    /*关怀客户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*关怀时间*/
    @NotEmpty(message="关怀时间不能为空")
    private String careTime;
    public String getCareTime() {
        return careTime;
    }
    public void setCareTime(String careTime) {
        this.careTime = careTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonUserCare=new JSONObject(); 
		jsonUserCare.accumulate("careId", this.getCareId());
		jsonUserCare.accumulate("careTitle", this.getCareTitle());
		jsonUserCare.accumulate("careContent", this.getCareContent());
		jsonUserCare.accumulate("userObj", this.getUserObj().getName());
		jsonUserCare.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonUserCare.accumulate("careTime", this.getCareTime().length()>19?this.getCareTime().substring(0,19):this.getCareTime());
		return jsonUserCare;
    }}