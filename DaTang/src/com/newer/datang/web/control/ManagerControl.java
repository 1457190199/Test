package com.newer.datang.web.control;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 主管 控制器
 * @author zxl
 *
 */
import org.springframework.web.bind.annotation.RequestMapping;

import com.newer.datang.data.entity.Task;
import com.newer.datang.service.IManagerService;

@Controller
public class ManagerControl {
	@Resource
	IManagerService managerService;
	
	//查询所有任务
	@RequestMapping("queryAllTaskForManager.do")
	public String queryAllTask(HttpServletRequest request) {
		return managerService.queryAllTask(request);
	}
	
	//管理任务 （查询任务并查询所关联的计划）
	@RequestMapping("managementTaskManager.do")
	public String  managementTaskManager(HttpServletRequest request,HttpSession session) {
		return managerService.managementTaskService(request,session);
	}
		
	//根据计划编号查询
	@RequestMapping("managerSelectPlanById.do")
	public String managerSelectPlanById(HttpServletRequest request,HttpSession session){
		return managerService.managerSelectPlanByIdService(request,session);
	}
	
	//新增任务准备
	@RequestMapping("inputAddTask.do")
	public String inputAddTask(Model modle) {
		return managerService.managerInputAddTaskService(modle);
	}
	
	//新增任务
	@RequestMapping("addTask.do")
	public String addTask(Task task,HttpServletRequest request) {
		return managerService.managementTaskService(task,request);
	}
	
	//调整任务准备
	@RequestMapping("inputTrimTask.do")
	public String inputTrimTask(HttpServletRequest request) {
		return managerService.managerInputTrimTaskService(request);
	}
	
	//根据ID删除任务极其关联的计划
	@RequestMapping("deleteTask.do")
	public String deleteTask(HttpServletRequest request) {
		return managerService.managerDeleteTaskService(request);
	}
	
	//查看所有人员
	@RequestMapping("selectAllPerson.do")
	public String selectAllPerson(HttpServletRequest request,HttpSession session) {
		return managerService.managerSelectAllPersonService(request,session);
	}
	
	//根据ID查询员工详细信息
	@RequestMapping("selectPersonDetailedById.do")
	public String selectPersonDetailedById(HttpServletRequest request) {
		return managerService.managerSelectPersonDetailedByIdService(request);
	}
	
	//查询状态为实施中的任务
	@RequestMapping("selectAllTaskByCondition.do")
	public String selectAllTaskByCondition(HttpServletRequest request) {
		return managerService.managerSelectAllTaskByConditionService(request);
	}
	
	//根据任务编号查询任务详情
	@RequestMapping("selectTaskByTaskId.do")
	public String selectTaskByTaskId(HttpServletRequest request,HttpSession session) {
		return managerService.managerSelectTaskByTaskIdService(request,session);
	}
	
	//根据任务编号修改任务状态
	@RequestMapping("updateTaskStatusByTaskId.do")
	public String updateTaskStatusByTaskId(HttpServletRequest request) {
		return managerService.managerUpdateTaskStatusByTaskIdService(request);
	}
	
	
}
