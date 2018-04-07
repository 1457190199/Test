package com.newer.datang.data.dao;

import java.util.List;

import com.newer.datang.data.entity.EmpLoyee;

public interface IEmployeeDao {

	//根据ID修改与删除员工信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#updateEmployeeById(java.lang.String, java.lang.Integer)
	 */
	boolean updateEmployeeById(String sql, Object[] args);

	//新增员工信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IEmployeeDao#insertEmployee(java.lang.String)
	 */
	boolean insertEmployee(String sql,Object[] args);

	//查询员工记录
	List<EmpLoyee> selectAllEmployee(String sql, Object[] args);

	//根据ID查询员工
	EmpLoyee findEmployeeById(Integer employeeId);

	int selectCountEmpLoyeeByParentId(int id);

}