<%@ page contentType="text/html; charset=gb2312" language="java" import="java.sql.*" errorPage="" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>��������</title>
<link href="../css/css.css" rel="stylesheet" type="text/css" />
</head>

<body><!-- #BeginLibraryItem "/Library/topbanner.lbi" -->
<link href="../css/css.css" rel="stylesheet" type="text/css" />
<div id="logo"><img src="../images/top.jpg" width="1002" height="258" /></div>
<!-- #EndLibraryItem --><table width="1002" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td class="rightimg"><!-- #BeginLibraryItem "/Library/left.lbi" --><div id="left">
				      <p><a href="queryAllTaskForManager.do" target="_self">�鿴����</a></p>
				      <p><a href="inputAddTask.do" target="_self">�ƶ�����</a></p>
				      <p><a href="inputTrimTask.do" target="_self">��������</a></p>
				      <p><a href="selectAllTaskByCondition.do" target="_self">��������</a></p>
				      <p><a href="selectAllPerson.do" target="_self">�鿴��Ա</a></p>
				      <p><a href="input_login.do" target="_self">�˳�ϵͳ</a></p>
</div><!-- #EndLibraryItem --><div id="right">
        <p>�����ڵ�λ�� &gt;&gt; �������� &gt;&gt; ����������Ϣ</p>
        <h1>����������Ϣ</h1>
        
        <form id="form1" name="form1" method="post" action="">
          <p>�������ƣ�<label>
          <input name="textfield" type="text" size="50" />
          </label></p>
          <p>����������
            <label>
            <textarea name="textfield2"></textarea>
            </label>
          </p>
            <p>��ʼʱ�䣺
              <label>
                <input name="textfield3" type="text" size="16" />
              </label>
             <span class="marginleft1">����ʱ�䣺
              <label>
                <input name="textfield4" type="text" size="16" />
              </label></span>
          </p>
          <p>ʵʩ��Ա��
            <label>
              <select name="select">
                <option value="timi">Ա��һ</option>
              </select>
            </label>
            <span class="marginleft">����״̬��
             δʵʩ
            </span></p>
            <p>
              <label>
              <input name="Submit" type="reset" class="menu2" value="����" />
              </label>
           &nbsp; 
              <label>
              <input name="Submit2" type="submit" class="menu1" value="�ύ" />
              </label>
           </p>
      </form>
        <p>&nbsp;</p>
      </div>
    </td></tr>
</table>
<!-- #BeginLibraryItem "/Library/bottom.lbi" --><div id="bottom"><img src="../images/button.jpg" width="1002" height="17" /></div><!-- #EndLibraryItem --></body>
</html>