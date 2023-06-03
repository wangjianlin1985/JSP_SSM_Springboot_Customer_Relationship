package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.UserEmail;

import com.chengxusheji.mapper.UserEmailMapper;
@Service
public class UserEmailService {

	@Resource UserEmailMapper userEmailMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加客户邮件记录*/
    public void addUserEmail(UserEmail userEmail) throws Exception {
    	userEmailMapper.addUserEmail(userEmail);
    }

    /*按照查询条件分页查询客户邮件记录*/
    public ArrayList<UserEmail> queryUserEmail(String title,UserInfo userObj,String sendTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_userEmail.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_userEmail.userObj='" + userObj.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_userEmail.sendTime like '%" + sendTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return userEmailMapper.queryUserEmail(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<UserEmail> queryUserEmail(String title,UserInfo userObj,String sendTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_userEmail.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_userEmail.userObj='" + userObj.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_userEmail.sendTime like '%" + sendTime + "%'";
    	return userEmailMapper.queryUserEmailList(where);
    }

    /*查询所有客户邮件记录*/
    public ArrayList<UserEmail> queryAllUserEmail()  throws Exception {
        return userEmailMapper.queryUserEmailList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String title,UserInfo userObj,String sendTime) throws Exception {
     	String where = "where 1=1";
    	if(!title.equals("")) where = where + " and t_userEmail.title like '%" + title + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_userEmail.userObj='" + userObj.getUser_name() + "'";
    	if(!sendTime.equals("")) where = where + " and t_userEmail.sendTime like '%" + sendTime + "%'";
        recordNumber = userEmailMapper.queryUserEmailCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取客户邮件记录*/
    public UserEmail getUserEmail(int emailId) throws Exception  {
        UserEmail userEmail = userEmailMapper.getUserEmail(emailId);
        return userEmail;
    }

    /*更新客户邮件记录*/
    public void updateUserEmail(UserEmail userEmail) throws Exception {
        userEmailMapper.updateUserEmail(userEmail);
    }

    /*删除一条客户邮件记录*/
    public void deleteUserEmail (int emailId) throws Exception {
        userEmailMapper.deleteUserEmail(emailId);
    }

    /*删除多条客户邮件信息*/
    public int deleteUserEmails (String emailIds) throws Exception {
    	String _emailIds[] = emailIds.split(",");
    	for(String _emailId: _emailIds) {
    		userEmailMapper.deleteUserEmail(Integer.parseInt(_emailId));
    	}
    	return _emailIds.length;
    }
}
