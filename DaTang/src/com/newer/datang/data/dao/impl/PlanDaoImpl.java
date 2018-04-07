package com.newer.datang.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.newer.datang.data.dao.IPlanDao;
import com.newer.datang.data.dao.ITaskDao;
import com.newer.datang.data.entity.Plan;
import com.newer.datang.data.util.DBUtil;

/**
 * 计划DAO层实现类
 * @author zxl
 *
 */
public class PlanDaoImpl implements IPlanDao {
	@Resource
	ITaskDao taskDao;
	
	JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());
	
	//查询计划
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IPlanDao#selectPlan(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<Plan> selectPlan(String sql,Object[] args) {
		try {
			return template.query(sql, args, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Plan plan = new Plan();
					plan.setPlanId(rs.getInt("PLAN_ID"));
					plan.setPlanName(rs.getString("PLAN_NAME"));
					plan.setStatus(rs.getString("STATUS"));
					plan.setIsFeedback(rs.getString("IS_FEEDBACK"));
					plan.setBeginDate(rs.getDate("BEGIN_DATE"));
					plan.setEndDate(rs.getDate("END_DATE"));
					plan.setTask(taskDao.findTaskById(rs.getInt("TASK_ID")));
					plan.setFeedbackInfo(rs.getString("FEEDBACK_INFO"));
					plan.setPlanDesc(rs.getString("PLAN_DESC"));
					return plan;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//根据ID查询计划
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IPlanDao#selectPlanById(int)
	 */
	@Override
	public Plan selectPlanById(int id) {
		try {
			String sql = "select * from T_PLAN where PLAN_ID=?";
			return template.queryForObject(sql, new Object[]{id}, new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Plan plan = new Plan();
					plan.setPlanId(rs.getInt("PLAN_ID"));
					plan.setPlanName(rs.getString("PLAN_NAME"));
					plan.setStatus(rs.getString("STATUS"));
					plan.setIsFeedback(rs.getString("IS_FEEDBACK"));
					plan.setBeginDate(rs.getDate("BEGIN_DATE"));
					plan.setEndDate(rs.getDate("END_DATE"));
					plan.setTask(taskDao.findTaskById(rs.getInt("TASK_ID")));
					plan.setFeedbackInfo(rs.getString("FEEDBACK_INFO"));
					plan.setPlanDesc(rs.getString("PLAN_DESC"));
					return plan;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//计划信息的增删改操作
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IPlanDao#updatePlan(java.lang.String, java.lang.Object[])
	 */
	@Override
	public boolean updatePlan(String sql,Object[] args) {
		int result = template.update(sql, args);
		return result>0?true:false;
	}
	
	//根据任务编号查询计划
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IPlanDao#selectPlanByTaskId(int)
	 */
	@Override
	public List<Plan> selectPlanByTaskId(int taskId) {
		try {
			String sql = "select * from T_PLAN where TASK_ID=?";
			return template.query(sql, new Object[]{taskId}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Plan plan = new Plan();
					plan.setPlanId(rs.getInt("PLAN_ID"));
					plan.setPlanName(rs.getString("PLAN_NAME"));
					plan.setStatus(rs.getString("STATUS"));
					plan.setIsFeedback(rs.getString("IS_FEEDBACK"));
					plan.setBeginDate(rs.getDate("BEGIN_DATE"));
					plan.setEndDate(rs.getDate("END_DATE"));
					plan.setTask(taskDao.findTaskById(rs.getInt("TASK_ID")));
					plan.setFeedbackInfo(rs.getString("FEEDBACK_INFO"));
					plan.setPlanDesc(rs.getString("PLAN_DESC"));
					return plan;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	//根据任务编号查询关联的计划数量
	@Override
	public Integer selectPlanCountByTaskId(int id) {
		try {
			return template.queryForInt("select count(*) from T_PLAN where TASK_ID=?", new Object[]{id});
		} catch (Exception e) {
			return 0;
		}
	} 
	
	
}
