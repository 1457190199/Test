package com.newer.datang.data.dao;

import java.util.List;

import com.newer.datang.data.entity.Role;

public interface IRoleDao {

	//根据ID修改与删除角色信息
	boolean updateRoleById(String sql, Integer id);

	//新增角色信息
	boolean insertRole(String sql);

	//根据编号查询角色信息
	Role selectRoleById(Integer roleId);

	//查询所有角色信息
	List<Role> selectAllRoles();

}