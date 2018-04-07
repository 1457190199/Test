package com.newer.datang.data.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.newer.datang.data.dao.IRoleDao;
import com.newer.datang.data.entity.Role;
import com.newer.datang.data.util.DBUtil;

/**
 * 角色 dao接口实现类
 * @author zxl
 *
 */
public class RoleDaoImpl implements IRoleDao {
	
	 JdbcTemplate template = new JdbcTemplate(DBUtil.getDataSource());
	
	//根据ID修改与删除角色信息
		/* (non-Javadoc)
		 * @see com.newer.datang.data.dao.impl.IRoleDao#updateRoleById(java.lang.String, java.lang.Integer)
		 */
		@Override
		public boolean updateRoleById(String sql,Integer id) {
			int result = template.update(sql, id, Integer.class);
			return result>0?true:false;
		}
		
		//新增角色信息
		/* (non-Javadoc)
		 * @see com.newer.datang.data.dao.impl.IRoleDao#insertRole(java.lang.String)
		 */
		@Override
		public boolean insertRole(String sql) {
			int result = template.update(sql);
			return result>0?true:false;
		}
	
	//根据编号查询角色信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IRoleDao#selectRoleById(java.lang.Integer)
	 */
	@Override
	public Role selectRoleById(Integer roleId) {
		return (Role) template.queryForObject("select * from t_role where role_id=?",new Object[] { roleId }, new RowMapper(){

			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Role role = new Role();
				role.setRoleId(rs.getInt("role_id"));
				role.setRoleName(rs.getString("role_name"));
				role.setRoleDesc(rs.getString("role_desc"));
				return role;
			}
		});
	}
	
	//查询所有角色信息
	/* (non-Javadoc)
	 * @see com.newer.datang.data.dao.impl.IRoleDao#selectAllRoles()
	 */
	@Override
	public List<Role> selectAllRoles() {
		return template.query("select * from t_role", new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Role role = new Role();
				role.setRoleId(rs.getInt("role_id"));
				role.setRoleName(rs.getString("role_name"));
				role.setRoleDesc(rs.getString("role_desc"));
				return role;
			}
		});
	}
	
}
