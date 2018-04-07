package com.newer.datang.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.dao.IPlanDao;
import com.newer.datang.data.dao.ITaskDao;
import com.newer.datang.data.entity.Task;
import com.newer.datang.data.util.DBUtil;

/**
 * 任务类 Dao层实现类
 * @author zxl
 *
 */

public class TaskDaoImpl implements ITaskDao {
	@Resource
	IEmployeeDao emplopeeDao;
	@Resource
	IPlanDao planDao;
	
	JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());
	
	//根据条件查询任务列表
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.ITaskDao#selectTask(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<Task> selectTask(String sql,Object[] args) {
		try {
			return template.query(sql, args, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Task task = new Task();
					task.setTaskId(rs.getInt("TASK_ID"));
					task.setTaskName(rs.getString("TASK_NAME"));
					task.setBeginDate(rs.getDate("BEGIN_DATE"));
					task.setEndDate(rs.getDate("END_DATE"));
					task.setRealBeginDate(rs.getDate("REAL_BEGIN_DATE"));
					task.setRealEndDate(rs.getDate("REAL_END_DATE"));
					task.setStatus(rs.getString("STATUS"));
					task.setImplementor(emplopeeDao.findEmployeeById(rs.getInt("IMPLEMENTOR_ID")));
					task.setAssigner(emplopeeDao.findEmployeeById(rs.getInt("ASSIGNER_ID")));
					task.setTaskDesc(rs.getString("TASK_DESC"));
					task.setPlanCount(planDao.selectPlanCountByTaskId(rs.getInt("TASK_ID")));
					return task;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//根据ID查询任务
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.ITaskDao#findTaskById(java.lang.Integer)
	 */
	@Override
	public Task findTaskById(Integer id) {
		try {
			String sql = "select * from T_TASK where TASK_ID=?";
			return template.queryForObject(sql, new Object[]{id}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					Task task = new Task();
					task.setTaskId(rs.getInt("TASK_ID"));
					task.setTaskName(rs.getString("TASK_NAME"));
					task.setBeginDate(rs.getDate("BEGIN_DATE"));
					task.setEndDate(rs.getDate("END_DATE"));
					task.setRealBeginDate(rs.getDate("REAL_BEGIN_DATE"));
					task.setRealEndDate(rs.getDate("REAL_END_DATE"));
					task.setStatus(rs.getString("STATUS"));
					task.setImplementor(emplopeeDao.findEmployeeById(rs.getInt("IMPLEMENTOR_ID")));
					task.setAssigner(emplopeeDao.findEmployeeById(rs.getInt("ASSIGNER_ID")));
					task.setTaskDesc(rs.getString("TASK_DESC"));
					task.setPlanCount(planDao.selectPlanCountByTaskId(rs.getInt("TASK_ID")));
					return task;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//对任务表进行增删改操作
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.ITaskDao#updateTask(java.lang.String, java.lang.Object[])
	 */
	@Override
	public boolean updateTask(String sql,Object[] args) {
		int result = template.update(sql, args);
		return result>0?true:false;
	}
	
	
}





