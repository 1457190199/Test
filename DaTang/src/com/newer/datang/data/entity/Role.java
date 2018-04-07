package com.newer.datang.data.entity;

/**
 * 角色 实体类
 * @author zxl
 *
 */
public class Role {
	private Integer roleId;	// 角色编号
	private String roleName;	//角色名称
	private String roleDesc;	//角色描述
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public String getRoleDesc() {
		return roleDesc;
	}
	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}
	public Role() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Role(Integer roleId, String roleName, String roleDesc) {
		super();
		this.roleId = roleId;
		this.roleName = roleName;
		this.roleDesc = roleDesc;
	}
	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", roleName=" + roleName + ", roleDesc=" + roleDesc + "]";
	}
}
