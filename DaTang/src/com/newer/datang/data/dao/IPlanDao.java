package com.newer.datang.data.dao;

import java.util.List;

import com.newer.datang.data.entity.Plan;

public interface IPlanDao {

	//查询计划
	List<Plan> selectPlan(String sql, Object[] args);

	//根据ID查询计划
	Plan selectPlanById(int id);

	//计划信息的增删改操作
	boolean updatePlan(String sql, Object[] args);

	//根据任务编号查询计划
	List<Plan> selectPlanByTaskId(int taskId);

	//根据任务编号查询关联的计划数量
	Integer selectPlanCountByTaskId(int int1);

}