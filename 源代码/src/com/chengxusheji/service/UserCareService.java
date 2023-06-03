package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.UserCare;

import com.chengxusheji.mapper.UserCareMapper;
@Service
public class UserCareService {

	@Resource UserCareMapper userCareMapper;
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

    /*添加客户关怀记录*/
    public void addUserCare(UserCare userCare) throws Exception {
    	userCareMapper.addUserCare(userCare);
    }

    /*按照查询条件分页查询客户关怀记录*/
    public ArrayList<UserCare> queryUserCare(String careTitle,UserInfo userObj,String careTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!careTitle.equals("")) where = where + " and t_userCare.careTitle like '%" + careTitle + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_userCare.userObj='" + userObj.getUser_name() + "'";
    	if(!careTime.equals("")) where = where + " and t_userCare.careTime like '%" + careTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return userCareMapper.queryUserCare(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<UserCare> queryUserCare(String careTitle,UserInfo userObj,String careTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!careTitle.equals("")) where = where + " and t_userCare.careTitle like '%" + careTitle + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_userCare.userObj='" + userObj.getUser_name() + "'";
    	if(!careTime.equals("")) where = where + " and t_userCare.careTime like '%" + careTime + "%'";
    	return userCareMapper.queryUserCareList(where);
    }

    /*查询所有客户关怀记录*/
    public ArrayList<UserCare> queryAllUserCare()  throws Exception {
        return userCareMapper.queryUserCareList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String careTitle,UserInfo userObj,String careTime) throws Exception {
     	String where = "where 1=1";
    	if(!careTitle.equals("")) where = where + " and t_userCare.careTitle like '%" + careTitle + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_userCare.userObj='" + userObj.getUser_name() + "'";
    	if(!careTime.equals("")) where = where + " and t_userCare.careTime like '%" + careTime + "%'";
        recordNumber = userCareMapper.queryUserCareCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取客户关怀记录*/
    public UserCare getUserCare(int careId) throws Exception  {
        UserCare userCare = userCareMapper.getUserCare(careId);
        return userCare;
    }

    /*更新客户关怀记录*/
    public void updateUserCare(UserCare userCare) throws Exception {
        userCareMapper.updateUserCare(userCare);
    }

    /*删除一条客户关怀记录*/
    public void deleteUserCare (int careId) throws Exception {
        userCareMapper.deleteUserCare(careId);
    }

    /*删除多条客户关怀信息*/
    public int deleteUserCares (String careIds) throws Exception {
    	String _careIds[] = careIds.split(",");
    	for(String _careId: _careIds) {
    		userCareMapper.deleteUserCare(Integer.parseInt(_careId));
    	}
    	return _careIds.length;
    }
}
