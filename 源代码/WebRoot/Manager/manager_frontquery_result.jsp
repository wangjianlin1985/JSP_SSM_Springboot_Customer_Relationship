﻿<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Manager" %>
<%@ page import="com.chengxusheji.po.Department" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Manager> managerList = (List<Manager>)request.getAttribute("managerList");
    //获取所有的departmentObj信息
    List<Department> departmentList = (List<Department>)request.getAttribute("departmentList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String managerUserName = (String)request.getAttribute("managerUserName"); //管理用户名查询关键字
    String name = (String)request.getAttribute("name"); //姓名查询关键字
    Department departmentObj = (Department)request.getAttribute("departmentObj");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>管理员查询</title>
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
			    	<li role="presentation" class="active"><a href="#managerListPanel" aria-controls="managerListPanel" role="tab" data-toggle="tab">管理员列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>Manager/manager_frontAdd.jsp" style="display:none;">添加管理员</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="managerListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>管理用户名</td><td>姓名</td><td>性别</td><td>年龄</td><td>所在部门</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<managerList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		Manager manager = managerList.get(i); //获取到管理员对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=manager.getManagerUserName() %></td>
 											<td><%=manager.getName() %></td>
 											<td><%=manager.getSex() %></td>
 											<td><%=manager.getAge() %></td>
 											<td><%=manager.getDepartmentObj().getDepartmentName() %></td>
 											<td>
 												<a href="<%=basePath  %>Manager/<%=manager.getManagerUserName() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="managerEdit('<%=manager.getManagerUserName() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="managerDelete('<%=manager.getManagerUserName() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
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
    		<h1>管理员查询</h1>
		</div>
		<form name="managerQueryForm" id="managerQueryForm" action="<%=basePath %>Manager/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="managerUserName">管理用户名:</label>
				<input type="text" id="managerUserName" name="managerUserName" value="<%=managerUserName %>" class="form-control" placeholder="请输入管理用户名">
			</div>






			<div class="form-group">
				<label for="name">姓名:</label>
				<input type="text" id="name" name="name" value="<%=name %>" class="form-control" placeholder="请输入姓名">
			</div>






            <div class="form-group">
            	<label for="departmentObj_departmentNo">所在部门：</label>
                <select id="departmentObj_departmentNo" name="departmentObj.departmentNo" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(Department departmentTemp:departmentList) {
	 					String selected = "";
 					if(departmentObj!=null && departmentObj.getDepartmentNo()!=null && departmentObj.getDepartmentNo().equals(departmentTemp.getDepartmentNo()))
 						selected = "selected";
	 				%>
 				 <option value="<%=departmentTemp.getDepartmentNo() %>" <%=selected %>><%=departmentTemp.getDepartmentName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="managerEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;管理员信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="managerEditForm" id="managerEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="manager_managerUserName_edit" class="col-md-3 text-right">管理用户名:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="manager_managerUserName_edit" name="manager.managerUserName" class="form-control" placeholder="请输入管理用户名" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="manager_password_edit" class="col-md-3 text-right">登录密码:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="manager_password_edit" name="manager.password" class="form-control" placeholder="请输入登录密码">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="manager_name_edit" class="col-md-3 text-right">姓名:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="manager_name_edit" name="manager.name" class="form-control" placeholder="请输入姓名">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="manager_sex_edit" class="col-md-3 text-right">性别:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="manager_sex_edit" name="manager.sex" class="form-control" placeholder="请输入性别">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="manager_age_edit" class="col-md-3 text-right">年龄:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="manager_age_edit" name="manager.age" class="form-control" placeholder="请输入年龄">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="manager_departmentObj_departmentNo_edit" class="col-md-3 text-right">所在部门:</label>
		  	 <div class="col-md-9">
			    <select id="manager_departmentObj_departmentNo_edit" name="manager.departmentObj.departmentNo" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="manager_managerMemo_edit" class="col-md-3 text-right">管理备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="manager_managerMemo_edit" name="manager.managerMemo" rows="8" class="form-control" placeholder="请输入管理备注"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#managerEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxManagerModify();">提交</button>
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
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.managerQueryForm.currentPage.value = currentPage;
    document.managerQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.managerQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.managerQueryForm.currentPage.value = pageValue;
    documentmanagerQueryForm.submit();
}

/*弹出修改管理员界面并初始化数据*/
function managerEdit(managerUserName) {
	$.ajax({
		url :  basePath + "Manager/" + managerUserName + "/update",
		type : "get",
		dataType: "json",
		success : function (manager, response, status) {
			if (manager) {
				$("#manager_managerUserName_edit").val(manager.managerUserName);
				$("#manager_password_edit").val(manager.password);
				$("#manager_name_edit").val(manager.name);
				$("#manager_sex_edit").val(manager.sex);
				$("#manager_age_edit").val(manager.age);
				$.ajax({
					url: basePath + "Department/listAll",
					type: "get",
					success: function(departments,response,status) { 
						$("#manager_departmentObj_departmentNo_edit").empty();
						var html="";
		        		$(departments).each(function(i,department){
		        			html += "<option value='" + department.departmentNo + "'>" + department.departmentName + "</option>";
		        		});
		        		$("#manager_departmentObj_departmentNo_edit").html(html);
		        		$("#manager_departmentObj_departmentNo_edit").val(manager.departmentObjPri);
					}
				});
				$("#manager_managerMemo_edit").val(manager.managerMemo);
				$('#managerEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除管理员信息*/
function managerDelete(managerUserName) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Manager/deletes",
			data : {
				managerUserNames : managerUserName,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#managerQueryForm").submit();
					//location.href= basePath + "Manager/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交管理员信息表单给服务器端修改*/
function ajaxManagerModify() {
	$.ajax({
		url :  basePath + "Manager/" + $("#manager_managerUserName_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#managerEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#managerQueryForm").submit();
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

})
</script>
</body>
</html>

