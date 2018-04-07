package com.newer.datang.service;


import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import com.newer.datang.data.entity.EmpLoyee;

/**
 * 登录  服务层接口
 * @author zxl
 *
 */
public interface ILoginService {

	String inputLoginService(Model model,HttpSession session);

	String loginService(EmpLoyee loyee,BindingResult bingResul,HttpSession session);

}