package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.UserCareService;
import com.chengxusheji.po.UserCare;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//UserCare管理控制层
@Controller
@RequestMapping("/UserCare")
public class UserCareController extends BaseController {

    /*业务层对象*/
    @Resource UserCareService userCareService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("userCare")
	public void initBinderUserCare(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userCare.");
	}
	/*跳转到添加UserCare视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new UserCare());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "UserCare_add";
	}

	/*客户端ajax方式提交添加客户关怀信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated UserCare userCare, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        userCareService.addUserCare(userCare);
        message = "客户关怀添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询客户关怀信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String careTitle,@ModelAttribute("userObj") UserInfo userObj,String careTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (careTitle == null) careTitle = "";
		if (careTime == null) careTime = "";
		if(rows != 0)userCareService.setRows(rows);
		List<UserCare> userCareList = userCareService.queryUserCare(careTitle, userObj, careTime, page);
	    /*计算总的页数和总的记录数*/
	    userCareService.queryTotalPageAndRecordNumber(careTitle, userObj, careTime);
	    /*获取到总的页码数目*/
	    int totalPage = userCareService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userCareService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(UserCare userCare:userCareList) {
			JSONObject jsonUserCare = userCare.getJsonObject();
			jsonArray.put(jsonUserCare);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询客户关怀信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<UserCare> userCareList = userCareService.queryAllUserCare();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(UserCare userCare:userCareList) {
			JSONObject jsonUserCare = new JSONObject();
			jsonUserCare.accumulate("careId", userCare.getCareId());
			jsonUserCare.accumulate("careTitle", userCare.getCareTitle());
			jsonArray.put(jsonUserCare);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询客户关怀信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String careTitle,@ModelAttribute("userObj") UserInfo userObj,String careTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (careTitle == null) careTitle = "";
		if (careTime == null) careTime = "";
		List<UserCare> userCareList = userCareService.queryUserCare(careTitle, userObj, careTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    userCareService.queryTotalPageAndRecordNumber(careTitle, userObj, careTime);
	    /*获取到总的页码数目*/
	    int totalPage = userCareService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userCareService.getRecordNumber();
	    request.setAttribute("userCareList",  userCareList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("careTitle", careTitle);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("careTime", careTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "UserCare/userCare_frontquery_result"; 
	}

	/*前台按照查询条件分页查询客户关怀信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String careTitle,@ModelAttribute("userObj") UserInfo userObj,String careTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (careTitle == null) careTitle = "";
		if (careTime == null) careTime = "";
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<UserCare> userCareList = userCareService.queryUserCare(careTitle, userObj, careTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    userCareService.queryTotalPageAndRecordNumber(careTitle, userObj, careTime);
	    /*获取到总的页码数目*/
	    int totalPage = userCareService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userCareService.getRecordNumber();
	    request.setAttribute("userCareList",  userCareList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("careTitle", careTitle);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("careTime", careTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "UserCare/userCare_userFrontquery_result"; 
	}
	
	
	
     /*前台查询UserCare信息*/
	@RequestMapping(value="/{careId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer careId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键careId获取UserCare对象*/
        UserCare userCare = userCareService.getUserCare(careId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("userCare",  userCare);
        return "UserCare/userCare_frontshow";
	}

	/*ajax方式显示客户关怀修改jsp视图页*/
	@RequestMapping(value="/{careId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer careId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键careId获取UserCare对象*/
        UserCare userCare = userCareService.getUserCare(careId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonUserCare = userCare.getJsonObject();
		out.println(jsonUserCare.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新客户关怀信息*/
	@RequestMapping(value = "/{careId}/update", method = RequestMethod.POST)
	public void update(@Validated UserCare userCare, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			userCareService.updateUserCare(userCare);
			message = "客户关怀更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "客户关怀更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除客户关怀信息*/
	@RequestMapping(value="/{careId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer careId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  userCareService.deleteUserCare(careId);
	            request.setAttribute("message", "客户关怀删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "客户关怀删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条客户关怀记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String careIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = userCareService.deleteUserCares(careIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出客户关怀信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String careTitle,@ModelAttribute("userObj") UserInfo userObj,String careTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(careTitle == null) careTitle = "";
        if(careTime == null) careTime = "";
        List<UserCare> userCareList = userCareService.queryUserCare(careTitle,userObj,careTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "UserCare信息记录"; 
        String[] headers = { "关怀id","关怀主题","关怀客户","关怀时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<userCareList.size();i++) {
        	UserCare userCare = userCareList.get(i); 
        	dataset.add(new String[]{userCare.getCareId() + "",userCare.getCareTitle(),userCare.getUserObj().getName(),userCare.getCareTime()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"UserCare.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}
