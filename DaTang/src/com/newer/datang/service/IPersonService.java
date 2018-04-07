package com.newer.datang.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import com.newer.datang.data.entity.Plan;
import com.newer.datang.data.entity.PlanDTO;

/**
 * 员工 服务层接口
 * @author zxl
 *
 */
public interface IPersonService {

	String queryAllTask(HttpServletRequest request);

	String managementTaskService(HttpServletRequest request,HttpSession session);

	String deletePlansByIdService(HttpServletRequest request);

	String addPlanByTaskIdService(Plan plan, HttpSession session);

	String inputSelectTaskService(Model model);

	String queryTaskAdvService(Model model,PlanDTO dto,HttpServletRequest request);

	String queryPlanByIdService(HttpServletRequest request);

	String updatePlanByIdService(Plan plan, HttpServletRequest request);

}