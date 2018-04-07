package com.newer.datang.data.dao;

import java.util.List;

import com.newer.datang.data.entity.Task;

public interface ITaskDao {

	//根据条件查询任务列表
	List<Task> selectTask(String sql, Object[] args);

	//根据ID查询任务
	Task findTaskById(Integer id);

	//对任务表进行增删改操作
	boolean updateTask(String sql, Object[] args);

}