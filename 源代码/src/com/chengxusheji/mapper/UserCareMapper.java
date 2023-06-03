package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.UserCare;

public interface UserCareMapper {
	/*添加客户关怀信息*/
	public void addUserCare(UserCare userCare) throws Exception;

	/*按照查询条件分页查询客户关怀记录*/
	public ArrayList<UserCare> queryUserCare(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有客户关怀记录*/
	public ArrayList<UserCare> queryUserCareList(@Param("where") String where) throws Exception;

	/*按照查询条件的客户关怀记录数*/
	public int queryUserCareCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条客户关怀记录*/
	public UserCare getUserCare(int careId) throws Exception;

	/*更新客户关怀记录*/
	public void updateUserCare(UserCare userCare) throws Exception;

	/*删除客户关怀记录*/
	public void deleteUserCare(int careId) throws Exception;

}
