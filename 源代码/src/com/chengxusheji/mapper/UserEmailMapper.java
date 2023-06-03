package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.UserEmail;

public interface UserEmailMapper {
	/*添加客户邮件信息*/
	public void addUserEmail(UserEmail userEmail) throws Exception;

	/*按照查询条件分页查询客户邮件记录*/
	public ArrayList<UserEmail> queryUserEmail(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有客户邮件记录*/
	public ArrayList<UserEmail> queryUserEmailList(@Param("where") String where) throws Exception;

	/*按照查询条件的客户邮件记录数*/
	public int queryUserEmailCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条客户邮件记录*/
	public UserEmail getUserEmail(int emailId) throws Exception;

	/*更新客户邮件记录*/
	public void updateUserEmail(UserEmail userEmail) throws Exception;

	/*删除客户邮件记录*/
	public void deleteUserEmail(int emailId) throws Exception;

}
