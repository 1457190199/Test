package com.newer.datang.test;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.dao.IPlanDao;
import com.newer.datang.data.dao.IRoleDao;
import com.newer.datang.data.dao.ITaskDao;
import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.data.entity.Plan;
import com.newer.datang.data.entity.Role;
import com.newer.datang.data.entity.Task;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml"})
public class DaoTest {
	
	@Resource
	IEmployeeDao emplopeeDao;
	
	@Resource
	IRoleDao roleDao;
	
	@Resource
	ITaskDao taskDao;
	
	@Resource
	IPlanDao planDao;
	
	//@Test
	public void planDaoImplTest(){
		Integer counts = planDao.selectPlanCountByTaskId(2);
		System.out.println("-----------------"+counts);
	}

	//@Test
	public void taskDaoImplTest() {
		List<Task> list = taskDao.selectTask("select * from T_TASK", null);
		for (Task task : list) {
			System.out.println(task);
		}
	}
	
	//@Test
	public void RoleDaoImplTest() {
		List<Role> selectRoleById = roleDao.selectAllRoles();
		System.out.println(selectRoleById.size());
	}
	
	@Test
	public void EmployeeDaoImplTest() {
		System.out.println("----------------"+emplopeeDao.selectAllEmployee("select * from T_TASK",null));
	}
}
