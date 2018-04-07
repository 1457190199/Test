package com.newer.datang.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.newer.datang.data.entity.Task;

/**
 * 主管 服务层接口
 * @author zxl
 *
 */
public interface IManagerService {

	String queryAllTask(HttpServletRequest request);

	String managementTaskService(HttpServletRequest request, HttpSession session);

	String managerSelectPlanByIdService(HttpServletRequest request, HttpSession session);

	String managerInputAddTaskService(Model modle);

	String managementTaskService(Task task, HttpServletRequest request);

	String managerInputTrimTaskService(HttpServletRequest request);

	String managerDeleteTaskService(HttpServletRequest request);

	String managerSelectAllPersonService(HttpServletRequest request,HttpSession session);

	String managerSelectPersonDetailedByIdService(HttpServletRequest request);

	String managerSelectAllTaskByConditionService(HttpServletRequest request);

	String managerSelectTaskByTaskIdService(HttpServletRequest request,HttpSession session);

	String managerUpdateTaskStatusByTaskIdService(HttpServletRequest request);

}
