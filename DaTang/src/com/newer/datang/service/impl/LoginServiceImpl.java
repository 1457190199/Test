package com.newer.datang.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.newer.datang.data.dao.IEmployeeDao;
import com.newer.datang.data.dao.IRoleDao;
import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.service.ILoginService;

/**
 * 登录服务层实现类
 * @author zxl
 *
 */
public class LoginServiceImpl implements ILoginService {
	@Resource
	IEmployeeDao emplopeeDao;
	@Resource
	IRoleDao roleDao;
	
	/* (non-Javadoc)
	 * @see com.newer.datang.service.ILoginService#inputLoginService(org.springframework.ui.Model, javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String inputLoginService(Model model,HttpSession session) {
		session.removeAttribute("user");
		EmpLoyee loyee = new EmpLoyee();
		model.addAttribute("loyee",loyee);
		return "login.jsp";
	}

	/* (non-Javadoc)
	 * @see com.newer.datang.service.ILoginService#loginService(com.newer.datang.data.entity.EmpLoyee)
	 */
	@Override
	public String loginService(EmpLoyee loyee,BindingResult bingResult,HttpSession session) {
		if(bingResult.hasErrors()){
			return "login.jsp";
		}else{
			String result = null;
			List<EmpLoyee> loyeeList = emplopeeDao.selectAllEmployee("select * from T_EMPLOYEE where EMPLOYEE_NAME=? and PASSWORD=?", new Object[]{loyee.getEmployeeName(),loyee.getPassword()});
			if(loyeeList.size()>0){
				String roleName = loyeeList.get(0).getRole().getRoleName();
				if(roleName.equals("系统管理员")){
					result="admin/welcome.jsp";
				}else if(roleName.equals("主管")){
					result="manager/welcome.jsp";
				}else if(roleName.equals("员工")){
					result="person/welcome.jsp";
				}
				session.setAttribute("user", loyeeList.get(0));
				return result;
			}else{
				return "login.jsp";
			}
		}
	}
}
