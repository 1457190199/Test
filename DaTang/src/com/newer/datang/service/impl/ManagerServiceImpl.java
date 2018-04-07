package com.newer.datang.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.dao.IPlanDao;
import com.newer.datang.data.dao.IRoleDao;
import com.newer.datang.data.dao.ITaskDao;
import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.data.entity.Plan;
import com.newer.datang.data.entity.Task;
import com.newer.datang.data.util.PageBean;
import com.newer.datang.service.IManagerService;

/**
 * 主管 服务层实现类
 * 
 * @author zxl
 *
 */
public class ManagerServiceImpl implements IManagerService {
	@Resource
	ITaskDao taskDao;
	@Resource
	IPlanDao planDao;
	@Resource
	IEmployeeDao emplopeeDao;
	@Resource
	IRoleDao roleDao;
	
	//查询所有任务  分页
	@Override
	public String queryAllTask(HttpServletRequest request) {
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by TASK_ID) no, T_TASK.* from T_TASK )temp where temp.no between ? and ? ";
		List<Task> list = taskDao.selectTask(sql, new Object[]{start,end});
		PageBean<Task> myPage = new PageBean<Task>();
		myPage.setData(list);
		myPage.setTotalRecords(taskDao.selectTask("select * from T_TASK", null).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/taskview.jsp";
	}

	//管理任务 （查询任务并查询所关联的计划）
	@Override
	public String managementTaskService(HttpServletRequest request, HttpSession session) {
		int taskId;
		try {
			taskId = Integer.parseInt(request.getParameter("id"));
		} catch (Exception e) {
			Task obj = (Task) session.getAttribute("task");
			taskId = obj.getTaskId();
		}
		
		Task task = taskDao.findTaskById(taskId);
		session.setAttribute("task", task);

		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by PLAN_ID) no, T_PLAN.* from T_PLAN where TASK_ID=?)temp where temp.no between ? and ? ";
		List<Plan> list = planDao.selectPlan(sql, new Object[]{taskId,start,end});
		PageBean<Plan> myPage = new PageBean<Plan>();
		myPage.setData(list);
		myPage.setTotalRecords(planDao.selectPlanCountByTaskId(taskId));
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/taskparticular.jsp";
	}

	//根据计划编号查询
	@Override
	public String managerSelectPlanByIdService(HttpServletRequest request, HttpSession session) {
		Integer id = Integer.parseInt(request.getParameter("planId"));
		Plan plan = planDao.selectPlanById(id);
		request.setAttribute("plan", plan);
		return "manager/program.jsp";
	}

	//新增任务准备
	@Override
	public String managerInputAddTaskService(Model modle) {
		String sql = "select * from T_EMPLOYEE where ROLE_ID=4";
		List<EmpLoyee> list = emplopeeDao.selectAllEmployee(sql, null);
		modle.addAttribute("list", list);
		return "manager/task.jsp";
	}

	//新增任务
	@Override
	public String managementTaskService(Task task, HttpServletRequest request) {
		int implementorId = Integer.parseInt(request.getParameter("implementorId"));
		task.setImplementor(emplopeeDao.findEmployeeById(implementorId));
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String sql="insert into T_TASK (task_id, task_name, begin_date, end_date, real_begin_date, real_end_date, status, implementor_id, assigner_id, task_desc) values (task_seq.nextval,?, to_date(?,'yyyy-MM-dd'), to_date(?, 'yyyy-MM-dd'), to_date(?,'yyyy-MM-dd'), to_date(?,'yyyy-MM-dd'),?,?,?,?)";
		Object[] args = {task.getTaskName(),format.format(task.getBeginDate()),format.format(task.getEndDate()),null,null,"未实施",task.getImplementor().getEmployeeId(),null,task.getTaskDesc()};
		taskDao.updateTask(sql, args);
		return "inputAddTask.do";
	}

	//调整任务准备
	@Override
	public String managerInputTrimTaskService(HttpServletRequest request) {
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by TASK_ID) no, T_TASK.* from T_TASK )temp where temp.no between ? and ? ";
		List<Task> list = taskDao.selectTask(sql, new Object[]{start,end});
		PageBean<Task> myPage = new PageBean<Task>();
		myPage.setData(list);
		myPage.setTotalRecords(taskDao.selectTask("select * from T_TASK", null).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/taskundone.jsp";
	}

	//根据ID删除任务极其关联的计划
	@Override
	public String managerDeleteTaskService(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		String sql = "delete  from t_plan where TASK_ID=?";
		planDao.updatePlan(sql, new Object[]{id});
		String taskSql = "delete from t_task where task_id=?";
		taskDao.updateTask(taskSql, new Object[]{id});
		return "inputTrimTask.do";
	}

	//查看所有人员
	@Override
	public String managerSelectAllPersonService(HttpServletRequest request,HttpSession session) {
		EmpLoyee user = (EmpLoyee) session.getAttribute("user");
		
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by EMPLOYEE_ID) no, T_EMPLOYEE.* from T_EMPLOYEE where PARENT_ID=?)temp where temp.no between ? and ? ";
		List<EmpLoyee> list = emplopeeDao.selectAllEmployee(sql, new Object[]{user.getEmployeeId(),start,end});
		PageBean<EmpLoyee> myPage = new PageBean<EmpLoyee>();
		myPage.setData(list);
		myPage.setTotalRecords(emplopeeDao.selectAllEmployee("select * from T_EMPLOYEE where PARENT_ID=?", new Object[]{user.getEmployeeId()}).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/checkper.jsp";
	}

	//根据ID查询员工详细信息
	@Override
	public String managerSelectPersonDetailedByIdService(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("id"));
		EmpLoyee loyee = emplopeeDao.findEmployeeById(id);
		request.setAttribute("loyee", loyee);
		return "manager/personinfo.jsp";
	}

	//查询状态为实施中的任务
	@Override
	public String managerSelectAllTaskByConditionService(HttpServletRequest request) {
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by TASK_ID) no, T_TASK.* from T_TASK where STATUS ='实施中')temp where temp.no between ? and ? ";
		List<Task> list = taskDao.selectTask(sql, new Object[]{start,end});
		PageBean<Task> myPage = new PageBean<Task>();
		myPage.setData(list);
		myPage.setTotalRecords(taskDao.selectTask("select * from T_TASK where STATUS ='实施中'",null).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/intendance.jsp";
	}

	//根据任务编号查询任务详情
	@Override
	public String managerSelectTaskByTaskIdService(HttpServletRequest request,HttpSession session) {
		int taskId;
		try {
			taskId = Integer.parseInt(request.getParameter("taskId"));
		} catch (Exception e) {
			Task obj = (Task) session.getAttribute("task");
			taskId = obj.getTaskId();
		}
		
		Task task = taskDao.findTaskById(taskId);
		session.setAttribute("task", task);

		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		
		String sql = "select * from (select row_number() over(order by PLAN_ID) no, T_PLAN.* from T_PLAN where TASK_ID=?)temp where temp.no between ? and ? ";
		List<Plan> list = planDao.selectPlan(sql, new Object[]{taskId,start,end});
		PageBean<Plan> myPage = new PageBean<Plan>();
		myPage.setData(list);
		myPage.setTotalRecords(planDao.selectPlanCountByTaskId(taskId));
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "manager/programinten.jsp";
	}

	//根据任务编号修改任务状态
	@Override
	public String managerUpdateTaskStatusByTaskIdService(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("taskId"));
		String status = request.getParameter("status");
		String sql = "update T_TASK set STATUS=? where TASK_ID=?";
		taskDao.updateTask(sql, new Object[]{status,id});
		return "selectAllTaskByCondition.do";
	}
	
	
}
