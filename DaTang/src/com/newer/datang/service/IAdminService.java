package com.newer.datang.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.newer.datang.data.entity.EmpLoyee;
import com.newer.datang.data.util.Radio;

/**
 * 管理员 服务层接口
 * @author zxl
 *
 */
public interface IAdminService {

	public String queryEmpLoyeeForPage(HttpServletRequest request);

	public String inputPersonService(Model model);

	public String addPersonService(EmpLoyee loyee,HttpServletRequest request);

	public String deleteEmpLoyeeById(Radio radio,BindingResult result,Model model);

	public String queryPersonForPage(HttpServletRequest request);

	public String queryPersonByIdService(HttpServletRequest request);

	public String updateParentByIdService(HttpServletRequest request);

}