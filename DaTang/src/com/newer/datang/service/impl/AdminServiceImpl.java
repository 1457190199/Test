package com.newer.datang.service.impl;

import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.dao.IRoleDao;
import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.data.entity.Role;
import com.newer.datang.data.util.PageBean;
import com.newer.datang.data.util.Radio;
import com.newer.datang.data.validate.RadioValidate;
import com.newer.datang.service.IAdminService;

/**
 *系统管理员服务层实现类
 * @author zxl
 *
 */
public class AdminServiceImpl implements IAdminService {
	@Resource
	IEmployeeDao emplopeeDao;
	@Resource
	IRoleDao roleDao;
	
	/* 分页查询员工
	 * @see com.newer.datang.service.impl.IAdminService#queryEmpLoyeeForPage(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String queryEmpLoyeeForPage(HttpServletRequest request) {
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		String sql = "select * from (select row_number() over(order by EMPLOYEE_ID) no, T_EMPLOYEE.* from T_EMPLOYEE )temp where temp.no between ? and ? ";
		List<EmpLoyee> allEmployee = emplopeeDao.selectAllEmployee(sql, new Object[]{start,end});
		PageBean<EmpLoyee> myPage = new PageBean<EmpLoyee>();
		myPage.setData(allEmployee);
		myPage.setTotalRecords(emplopeeDao.selectAllEmployee("select * from T_EMPLOYEE", null).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "admin/useradmin.jsp";
	}

	@Override
	public String inputPersonService(Model model) {
	    //查询所有角色：
	   	List<Role> allAddRoles = roleDao.selectAllRoles();
	    model.addAttribute("allAddRoles", allAddRoles);
		return "admin/personadd.jsp";
	}

	//新增员工
	@Override
	public String addPersonService(EmpLoyee loyee,HttpServletRequest request) {
		int roleId = Integer.parseInt(request.getParameter("roleId"));
		Role role = roleDao.selectRoleById(roleId);
		loyee.setRole(role);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    
	    String sql = "insert into T_EMPLOYEE (employee_id, employee_name, password, real_name, sex, birthday, duty, enrolldate, education, major, experience, role_id, parent_id) values (loyee_seq.nextval,?,?,?,?,to_date(?,'yyyy-MM-dd'),?,to_date(?,'yyyy-MM-dd'),?,?,?,?,null)";
		Object[] args =new Object[]{loyee.getEmployeeName(),loyee.getPassword(),loyee.getRealName(),loyee.getSex(),format.format(loyee.getBirthDay()),loyee.getDuty(),format.format(loyee.getEndrolldDate()),loyee.getEducation(),loyee.getMajor(),loyee.getExperience(),loyee.getRole().getRoleId()};

		boolean result = emplopeeDao.insertEmployee(sql,args);
		if(result){
			return "selectEmpLoyeeByPage.do";
		}
		return "input_addperson.do";
	}

	//根据ID删除员工
	@Override
	public String deleteEmpLoyeeById(Radio radio,BindingResult result,Model model) {
		System.out.println("删除员工ID："+radio.getRadio());
		RadioValidate validate = new RadioValidate();
		validate.validate(radio, result);
		if(!result.hasErrors()){
			int id = Integer.parseInt(radio.getRadio());
			emplopeeDao.updateEmployeeById("delete from T_EMPLOYEE where employee_id=?",new Object[]{id});
		}
		return "empadminQuery.do";
	}

	//查询所有身份为员工
	@Override
	public String queryPersonForPage(HttpServletRequest request) {
		int pageNo;
		int pageSize;
		try {
			pageNo = Integer.parseInt(request.getParameter("pageNo"));
			pageSize = Integer.parseInt(request.getParameter("pageSize"));
		} catch (Exception e) {
			pageNo = 1;
			pageSize = 5;
		}
		
		//起始位置编号
		int start = (pageNo - 1)*pageSize + 1;
		//结束位置编号
		int end = pageNo*pageSize;
		String sql = "select * from (select row_number() over(order by EMPLOYEE_ID) no, T_EMPLOYEE.* from T_EMPLOYEE where ROLE_ID=4 )temp where temp.no between ? and ? ";
		List<EmpLoyee> allEmployee = emplopeeDao.selectAllEmployee(sql, new Object[]{start,end});
		PageBean<EmpLoyee> myPage = new PageBean<EmpLoyee>();
		myPage.setData(allEmployee);
		myPage.setTotalRecords(emplopeeDao.selectAllEmployee("select * from T_EMPLOYEE where ROLE_ID=4 ", null).size());
		myPage.setPageSize(pageSize);
		myPage.setPageNo(pageNo);
		request.setAttribute("pd", myPage);
		return "admin/empdistribute.jsp";
	}

	//根据编号查询员工
	@Override
	public String queryPersonByIdService(HttpServletRequest request) {
		int id = Integer.parseInt(request.getParameter("radio"));
		EmpLoyee loyee = emplopeeDao.findEmployeeById(id);
		request.setAttribute("person", loyee);
		
		String sql = "select * from T_EMPLOYEE where ROLE_ID=3";
		List<EmpLoyee> allEmployee = emplopeeDao.selectAllEmployee(sql,null);
		request.setAttribute("loyeeList", allEmployee);
		return "admin/persondesc.jsp";
	}

	//根据编号修改员工所属主管
	@Override
	public String updateParentByIdService(HttpServletRequest request) {
		String parentId = request.getParameter("parent");
		String loyeeId = request.getParameter("id");
		emplopeeDao.updateEmployeeById("update T_EMPLOYEE set PARENT_ID=? where EMPLOYEE_ID=?", new Object[]{parentId,loyeeId});
			return "queryPersonForPage.do";
	}
}
