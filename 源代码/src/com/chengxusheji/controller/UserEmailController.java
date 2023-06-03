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
import com.chengxusheji.service.UserEmailService;
import com.chengxusheji.po.UserEmail;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//UserEmail管理控制层
@Controller
@RequestMapping("/UserEmail")
public class UserEmailController extends BaseController {

    /*业务层对象*/
    @Resource UserEmailService userEmailService;

    @Resource UserInfoService userInfoService;
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("userEmail")
	public void initBinderUserEmail(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userEmail.");
	}
	/*跳转到添加UserEmail视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new UserEmail());
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "UserEmail_add";
	}

	/*客户端ajax方式提交添加客户邮件信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated UserEmail userEmail, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        userEmailService.addUserEmail(userEmail);
        message = "客户邮件添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询客户邮件信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String title,@ModelAttribute("userObj") UserInfo userObj,String sendTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (title == null) title = "";
		if (sendTime == null) sendTime = "";
		if(rows != 0)userEmailService.setRows(rows);
		List<UserEmail> userEmailList = userEmailService.queryUserEmail(title, userObj, sendTime, page);
	    /*计算总的页数和总的记录数*/
	    userEmailService.queryTotalPageAndRecordNumber(title, userObj, sendTime);
	    /*获取到总的页码数目*/
	    int totalPage = userEmailService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userEmailService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(UserEmail userEmail:userEmailList) {
			JSONObject jsonUserEmail = userEmail.getJsonObject();
			jsonArray.put(jsonUserEmail);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询客户邮件信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<UserEmail> userEmailList = userEmailService.queryAllUserEmail();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(UserEmail userEmail:userEmailList) {
			JSONObject jsonUserEmail = new JSONObject();
			jsonUserEmail.accumulate("emailId", userEmail.getEmailId());
			jsonUserEmail.accumulate("title", userEmail.getTitle());
			jsonArray.put(jsonUserEmail);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询客户邮件信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String title,@ModelAttribute("userObj") UserInfo userObj,String sendTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (sendTime == null) sendTime = "";
		List<UserEmail> userEmailList = userEmailService.queryUserEmail(title, userObj, sendTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    userEmailService.queryTotalPageAndRecordNumber(title, userObj, sendTime);
	    /*获取到总的页码数目*/
	    int totalPage = userEmailService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userEmailService.getRecordNumber();
	    request.setAttribute("userEmailList",  userEmailList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("sendTime", sendTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "UserEmail/userEmail_frontquery_result"; 
	}

	/*前台按照查询条件分页查询客户邮件信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(String title,@ModelAttribute("userObj") UserInfo userObj,String sendTime,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (title == null) title = "";
		if (sendTime == null) sendTime = "";
		userObj =  new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<UserEmail> userEmailList = userEmailService.queryUserEmail(title, userObj, sendTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    userEmailService.queryTotalPageAndRecordNumber(title, userObj, sendTime);
	    /*获取到总的页码数目*/
	    int totalPage = userEmailService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = userEmailService.getRecordNumber();
	    request.setAttribute("userEmailList",  userEmailList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("title", title);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("sendTime", sendTime);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "UserEmail/userEmail_userFrontquery_result"; 
	}
	
	
     /*前台查询UserEmail信息*/
	@RequestMapping(value="/{emailId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer emailId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键emailId获取UserEmail对象*/
        UserEmail userEmail = userEmailService.getUserEmail(emailId);

        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("userEmail",  userEmail);
        return "UserEmail/userEmail_frontshow";
	}

	/*ajax方式显示客户邮件修改jsp视图页*/
	@RequestMapping(value="/{emailId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer emailId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键emailId获取UserEmail对象*/
        UserEmail userEmail = userEmailService.getUserEmail(emailId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonUserEmail = userEmail.getJsonObject();
		out.println(jsonUserEmail.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新客户邮件信息*/
	@RequestMapping(value = "/{emailId}/update", method = RequestMethod.POST)
	public void update(@Validated UserEmail userEmail, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			userEmailService.updateUserEmail(userEmail);
			message = "客户邮件更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "客户邮件更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除客户邮件信息*/
	@RequestMapping(value="/{emailId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer emailId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  userEmailService.deleteUserEmail(emailId);
	            request.setAttribute("message", "客户邮件删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "客户邮件删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条客户邮件记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String emailIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = userEmailService.deleteUserEmails(emailIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出客户邮件信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String title,@ModelAttribute("userObj") UserInfo userObj,String sendTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(title == null) title = "";
        if(sendTime == null) sendTime = "";
        List<UserEmail> userEmailList = userEmailService.queryUserEmail(title,userObj,sendTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "UserEmail信息记录"; 
        String[] headers = { "邮件id","邮件标题","接受客户","发送时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<userEmailList.size();i++) {
        	UserEmail userEmail = userEmailList.get(i); 
        	dataset.add(new String[]{userEmail.getEmailId() + "",userEmail.getTitle(),userEmail.getUserObj().getName(),userEmail.getSendTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"UserEmail.xls");//filename是下载的xls的名，建议最好用英文 
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
