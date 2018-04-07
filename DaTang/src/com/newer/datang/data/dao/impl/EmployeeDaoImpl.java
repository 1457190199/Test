package com.newer.datang.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.data.entity.Role;
import com.newer.datang.data.util.DBUtil;

/**
 * 员工 dao接口实现类
 * @author zxl
 *
 */
public class EmployeeDaoImpl implements IEmployeeDao {
	
	JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());
	
	//根据ID修改与删除员工信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#updateEmployeeById(java.lang.String, java.lang.Integer)
	 */
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#updateEmployeeById(java.lang.String, java.lang.Integer)
	 */
	@Override
	public boolean updateEmployeeById(String sql,Object[] args) {
		int result = template.update(sql,args);
		return result>0?true:false;
	}
	
	//新增员工信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#insertEmployee(java.lang.String)
	 */
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#insertEmployee(java.lang.String)
	 */
	@Override
	public boolean insertEmployee(String sql,Object[] args) {
		int result = template.update(sql,args);
		return result>0?true:false;
	}
	
	//查询员工记录
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#selectAllEmployee(java.lang.String, java.lang.Object[])
	 */
	@Override
	public List<EmpLoyee> selectAllEmployee(String sql,Object[] args) {
		try {
			return template.query(sql, args, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					EmpLoyee emp = new EmpLoyee();
					emp.setEmployeeId(rs.getInt("EMPLOYEE_ID"));
					emp.setEmployeeName(rs.getString("EMPLOYEE_NAME"));
					emp.setBirthDay(rs.getDate("BIRTHDAY"));
					emp.setDuty(rs.getString("DUTY"));
					emp.setEducation(rs.getString("EDUCATION"));
					emp.setEndrolldDate(rs.getDate("ENROLLDATE"));
					emp.setExperience(rs.getString("EXPERIENCE"));
					emp.setMajor(rs.getString("MAJOR"));
					emp.setRole(new RoleDaoImpl().selectRoleById(rs.getInt("ROLE_ID")));
					emp.setParent(new EmployeeDaoImpl().findEmployeeById(rs.getInt("PARENT_ID")));
					emp.setPassword(rs.getString("PASSWORD"));
					emp.setRealName(rs.getString("REAL_NAME"));
					emp.setSex(rs.getString("SEX"));
					return emp;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}
	
	//根据ID查询员工
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#findEmployeeById(java.lang.Integer)
	 */
	@Override
	public EmpLoyee findEmployeeById(Integer employeeId) {
		try {
			return template.queryForObject(
					"select * from t_employee where employee_id=?",
					new Object[] { employeeId }, new RowMapper(){

				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					EmpLoyee employee = new EmpLoyee();
					employee.setEmployeeId(rs.getInt("employee_id"));
					employee.setEmployeeName(rs.getString("employee_name"));
					employee.setPassword(rs.getString("password"));
					employee.setRealName(rs.getString("real_name"));
					employee.setSex(rs.getString("sex"));
					employee.setBirthDay(rs.getTimestamp("birthday"));
					employee.setDuty(rs.getString("duty"));
					employee.setEndrolldDate(rs.getTimestamp("enrolldate"));
					employee.setEducation(rs.getString("education"));
					employee.setMajor(rs.getString("major"));
					employee.setExperience(rs.getString("experience"));
					employee.setRole(new RoleDaoImpl().selectRoleById(rs.getInt("role_id")));
					EmpLoyee parentemployee = new EmpLoyee();
					parentemployee.setEmployeeId(rs.getInt("parent_id"));
					employee.setParent(parentemployee);
					return employee;
				}
			});
		} catch (Exception e) {
			return null;
		}
	}

	//查询主管下员工数量
	@Override
	public int selectCountEmpLoyeeByParentId(int id) {
		try {
			String sql = "select count(*) from t_employee where PARENT_ID=?";
			int queryForInt = template.queryForInt(sql, id);
			return queryForInt;
		} catch (Exception e) {
			return 0;
		}
	}
	
}
