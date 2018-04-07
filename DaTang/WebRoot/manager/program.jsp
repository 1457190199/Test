<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>计划详细信息</title>
<link href="${pageContext.request.contextPath }/css/css.css" rel="stylesheet" type="text/css" />
</head>

<body><!-- #BeginLibraryItem "/Library/topbanner.lbi" -->
<div align="center">
<div id="logo"><img src="${pageContext.request.contextPath }/images/top.jpg" width="1002" height="258" /></div>
<!-- #EndLibraryItem --><table width="1002" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="rightimg"><!-- #BeginLibraryItem "/Library/left.lbi" --><div id="left">
				      <p><a href="queryAllTaskForManager.do" target="_self">查看任务</a></p>
				      <p><a href="inputAddTask.do" target="_self">制定任务</a></p>
				      <p><a href="inputTrimTask.do" target="_self">调整任务</a></p>
				      <p><a href="selectAllTaskByCondition.do" target="_self">跟踪任务</a></p>
				      <p><a href="selectAllPerson.do" target="_self">查看人员</a></p>
				      <p><a href="input_login.do" target="_self">退出系统</a></p>
</div><!-- #EndLibraryItem --><div id="right">
        <p>您现在的位置 &gt;&gt; 查看任务 &gt;&gt; 计划详细信息</p>
		<h1>计划详细信息：</h1>
        <form id="form1" name="form1" method="post" action="managementTaskManager.do">
		  
		  <table width="700" border="0" cellpadding="0" cellspacing="0" class="table">
            <tr>
              <td width="15%" class="tdcolor">计划名称</td>
              <td width="579" colspan="3">${plan.planName}</td>
            </tr>
            <tr>
              <td class="tdcolor">计划描述</td>
              <td colspan="3">${plan.planDesc}</td>
            </tr>
            <tr>
              <td class="tdcolor">开始时间</td>
              <td width="579">${plan.beginDate}</td>
              <td width="20%" class="tdcolor">结束时间</td>
              <td width="50%"><p>${plan.endDate}</p></td>
            </tr>
            <tr>
              <td class="tdcolor">所属任务</td>
              <td>${plan.task.taskName}</td>
              <td class="tdcolor">计划状态</td>
              <td>${plan.status}</td>
            </tr>
            <tr>
              <td class="tdcolor">反馈信息</td>
              <td colspan="3">${plan.feedbackInfo}</td>
            </tr>
          </table>
		  <p>
              <label>
              <input name="Submit" type="submit" class="menu4" value="返回" />
            </label>
          </p>
      </form>
      </div>
    </td></tr>
</table>
<!-- #BeginLibraryItem "/Library/copyright.lbi" --><div class="copyright">COPYRIGHT 2007 DATANG TELECOM TECHNOLOGY CO.LTD 京ICP备06071639号</div><!-- #EndLibraryItem --><!-- #BeginLibraryItem "/Library/bottom.lbi" --><div id="bottom"><img src="${pageContext.request.contextPath }/images/button.jpg" width="1002" height="17" /></div><!-- #EndLibraryItem -->
</div>
</body>
</html>
