<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.UserEmail" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<UserEmail> userEmailList = (List<UserEmail>)request.getAttribute("userEmailList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String title = (String)request.getAttribute("title"); //邮件标题查询关键字
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String sendTime = (String)request.getAttribute("sendTime"); //发送时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>客户邮件查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#userEmailListPanel" aria-controls="userEmailListPanel" role="tab" data-toggle="tab">客户邮件列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>UserEmail/userEmail_frontAdd.jsp" style="display:none;">添加客户邮件</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="userEmailListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>邮件id</td><td>邮件标题</td><td>接受客户</td><td>发送时间</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<userEmailList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		UserEmail userEmail = userEmailList.get(i); //获取到客户邮件对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=userEmail.getEmailId() %></td>
 											<td><%=userEmail.getTitle() %></td>
 											<td><%=userEmail.getUserObj().getName() %></td>
 											<td><%=userEmail.getSendTime() %></td>
 											<td>
 												<a href="<%=basePath  %>UserEmail/<%=userEmail.getEmailId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="userEmailEdit('<%=userEmail.getEmailId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="userEmailDelete('<%=userEmail.getEmailId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>客户邮件查询</h1>
		</div>
		<form name="userEmailQueryForm" id="userEmailQueryForm" action="<%=basePath %>UserEmail/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="title">邮件标题:</label>
				<input type="text" id="title" name="title" value="<%=title %>" class="form-control" placeholder="请输入邮件标题">
			</div>






            <div class="form-group">
            	<label for="userObj_user_name">接受客户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="sendTime">发送时间:</label>
				<input type="text" id="sendTime" name="sendTime" class="form-control"  placeholder="请选择发送时间" value="<%=sendTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="userEmailEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;客户邮件信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="userEmailEditForm" id="userEmailEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="userEmail_emailId_edit" class="col-md-3 text-right">邮件id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="userEmail_emailId_edit" name="userEmail.emailId" class="form-control" placeholder="请输入邮件id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="userEmail_title_edit" class="col-md-3 text-right">邮件标题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="userEmail_title_edit" name="userEmail.title" class="form-control" placeholder="请输入邮件标题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userEmail_content_edit" class="col-md-3 text-right">邮件内容:</label>
		  	 <div class="col-md-9">
			 	<textarea name="userEmail.content" id="userEmail_content_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userEmail_userObj_user_name_edit" class="col-md-3 text-right">接受客户:</label>
		  	 <div class="col-md-9">
			    <select id="userEmail_userObj_user_name_edit" name="userEmail.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="userEmail_sendTime_edit" class="col-md-3 text-right">发送时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date userEmail_sendTime_edit col-md-12" data-link-field="userEmail_sendTime_edit">
                    <input class="form-control" id="userEmail_sendTime_edit" name="userEmail.sendTime" size="16" type="text" value="" placeholder="请选择发送时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#userEmailEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxUserEmailModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var userEmail_content_edit = UE.getEditor('userEmail_content_edit'); //邮件内容编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.userEmailQueryForm.currentPage.value = currentPage;
    document.userEmailQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.userEmailQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.userEmailQueryForm.currentPage.value = pageValue;
    documentuserEmailQueryForm.submit();
}

/*弹出修改客户邮件界面并初始化数据*/
function userEmailEdit(emailId) {
	$.ajax({
		url :  basePath + "UserEmail/" + emailId + "/update",
		type : "get",
		dataType: "json",
		success : function (userEmail, response, status) {
			if (userEmail) {
				$("#userEmail_emailId_edit").val(userEmail.emailId);
				$("#userEmail_title_edit").val(userEmail.title);
				userEmail_content_edit.setContent(userEmail.content, false);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#userEmail_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#userEmail_userObj_user_name_edit").html(html);
		        		$("#userEmail_userObj_user_name_edit").val(userEmail.userObjPri);
					}
				});
				$("#userEmail_sendTime_edit").val(userEmail.sendTime);
				$('#userEmailEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除客户邮件信息*/
function userEmailDelete(emailId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "UserEmail/deletes",
			data : {
				emailIds : emailId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#userEmailQueryForm").submit();
					//location.href= basePath + "UserEmail/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交客户邮件信息表单给服务器端修改*/
function ajaxUserEmailModify() {
	$.ajax({
		url :  basePath + "UserEmail/" + $("#userEmail_emailId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#userEmailEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#userEmailQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*发送时间组件*/
    $('.userEmail_sendTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>

