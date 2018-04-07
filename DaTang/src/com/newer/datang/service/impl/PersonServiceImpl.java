package com.newer.datang.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.newer.datang.data.dao.IPlanDao;
import com.newer.datang.data.dao.ITaskDao;
import com.newer.datang.data.entity.Plan;
import com.newer.datang.data.entity.PlanDTO;
import com.newer.datang.data.entity.Task;
import com.newer.datang.data.util.PageBean;
import com.newer.datang.service.IPersonService;

public class PersonServiceImpl implements IPersonService {
	@Resource
	ITaskDao taskDao;
	@Resource
	IPlanDao planDao;
	
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
		return "person/task.jsp";
	}

	//管理任务 （查询任务并查询所关联的计划）
	@Override
	public String managementTaskService(HttpServletRequest request,HttpSession session) {
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
		return "person/taskview.jsp";
	}

	//根据计划ID删除计划
	@Override
	public String deletePlansByIdService(HttpServletRequest request) {
		String[] str =request.getParameterValues("planId");
		if(str!=null){
			String sql = "delete  from t_plan where PLAN_ID = 0";
			for (int i = 0;i<str.length;i++) {
				sql = sql+"or PLAN_ID = ? ";
			}
			planDao.updatePlan(sql, str);
		}
		return "managementTask.do";
	}

	//根据任务编号添加计划
	@Override
	public String addPlanByTaskIdService(Plan plan, HttpSession session) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Task task = (Task)session.getAttribute("task");
		String sql = "insert into T_PLAN (plan_id,plan_name,status,is_feedback,begin_date,end_date,task_id,feedback_info,plan_desc) values (t_plan_seq.nextval,?,'已完成','否',to_date(?,'yyyy-MM-dd'),to_date(?,'yyyy-MM-dd'),?,null,?)";
		Object[] args = {plan.getPlanName(),format.format(plan.getBeginDate()),format.format(plan.getEndDate()),task.getTaskId(),plan.getPlanDesc()};
		boolean result = planDao.updatePlan(sql, args);
		if(result){
			String task_sql ="update T_TASK set STATUS='实施中' where TASK_ID=?";
			taskDao.updateTask(task_sql, new Object[]{task.getTaskId()});
		}
		return "managementTask.do";
	}

	//高级查询计划准备
	@Override
	public String inputSelectTaskService(Model model) {
		String sql="select * from T_TASK";
		List<Task> tasks = taskDao.selectTask(sql, null);
		model.addAttribute("tasks", tasks);
		return "person/checkpro.jsp";
	}

	//高级查询计划
	@Override
	public String queryTaskAdvService(Model model,PlanDTO dto,HttpServletRequest request) {
		Integer taskId = Integer.parseInt(request.getParameter("taskId"));
		List<Object> params = new ArrayList<Object>();
		String sql = "select * from T_PLAN where TASK_ID=? ";
		params.add(taskId);
		
		String beginDateBefore = dto.getBeginDateBefore();
		String beginDateAfter = dto.getBeginDateAfter();

		if ((beginDateBefore != null && !"".equals(beginDateBefore)) && 
				(beginDateAfter != null && !"".equals(beginDateAfter))) {
			//true:保证用户在日期文本框输入了值
			SimpleDateFormat sdf = 
					new SimpleDateFormat("yyyy-MM-dd");
			Date pay1;
			Date pay2;
			try {
				pay1 = sdf.parse(beginDateBefore);
				pay2 = sdf.parse(beginDateAfter);
				if (pay1.before(pay2)) {
					sql += "and BEGIN_DATE between ? and ? ";
					params.add(pay1);
					params.add(pay2);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String endDateBefore = dto.getEndDateBefore();
		String endDateAfter = dto.getEndDateAfter();
		
		if ((endDateBefore != null && !"".equals(endDateBefore)) && 
				(endDateAfter != null && !"".equals(endDateAfter))) {
			//true:保证用户在日期文本框输入了值
			SimpleDateFormat sdf = 
					new SimpleDateFormat("yyyy-MM-dd");
			Date pay1;
			Date pay2;
			try {
				pay1 = sdf.parse(endDateBefore);
				pay2 = sdf.parse(endDateAfter);
				if (pay1.before(pay2)) {
					sql += "and END_DATE between ? and ? ";
					params.add(pay1);
					params.add(pay2);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String isFeedback = dto.getIsFeedback();
		if(isFeedback!=null && !"".equals(isFeedback)){
			sql+="and IS_FEEDBACK = ? ";
			params.add(isFeedback);
		}
		List<Plan> list = planDao.selectPlan(sql, params.toArray());
		request.setAttribute("list", list);
		
		Task superTask = taskDao.findTaskById(taskId);
		model.addAttribute("superTask", superTask);
		
		List<Task> tasks = taskDao.selectTask("select * from T_TASK", null);
		model.addAttribute("tasks", tasks);
		return "person/checkpro.jsp";
	}

	//根据计划编号查询计划
	@Override
	public String queryPlanByIdService(HttpServletRequest request) {
		Integer id = Integer.parseInt(request.getParameter("id"));
		Plan plan = planDao.selectPlanById(id);
		request.setAttribute("plan", plan);
		return "person/feedback.jsp";
	}

	@Override
	public String updatePlanByIdService(Plan plan, HttpServletRequest request) {
		String sql="update T_PLAN set STATUS=?,IS_FEEDBACK=?,FEEDBACK_INFO=? where PLAN_ID=?";
		Object[] args = {plan.getStatus(),plan.getIsFeedback(),plan.getFeedbackInfo(),plan.getPlanId()};
		planDao.updatePlan(sql, args);
		return "inputSelectTask.do";
	}
}
