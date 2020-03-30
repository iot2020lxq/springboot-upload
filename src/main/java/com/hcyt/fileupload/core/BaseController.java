/**
 * Copyright &copy; 2015-2020 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.hcyt.fileupload.core;

import com.hcyt.fileupload.json.AjaxJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制器支持类
 * @author jeeplus
 * @version 2016-3-23
 */
public abstract class BaseController {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 验证Bean实例对象
	 */
	@Autowired
	protected Validator validator;

	@Value("${endpiont}")
	protected String endpiont;

	@Value("${accessId}")
	protected String accessId;

	@Value("${accessKey}")
	protected String accessKey;

	@Value("${bucket}")
	protected String bucket;

	@Value("${cdnUrl}")
	protected String cdnUrl;

	@Value("${audios}")
	protected String audios;

	@Value("${videos}")
	protected String videos;

	@Value("${resources}")
	protected String resources;

	@Value("${logFilePath}")
	protected String logFilePath;

	@Value("${photos}")
	protected String photos;

	/**
	 * 添加Model消息
	 * @param messages
	 */
	protected void addMessage(Model model, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		model.addAttribute("message", sb.toString());
	}
	/**
	 * @param messages
	 */
	protected String getMessage( String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		return sb.toString();
	}

	/**
	 * 添加Flash消息
	 * @param messages
	 */
	protected void addMessage(RedirectAttributes redirectAttributes, String... messages) {
		StringBuilder sb = new StringBuilder();
		for (String message : messages){
			sb.append(message).append(messages.length>1?"<br/>":"");
		}
		redirectAttributes.addFlashAttribute("message", sb.toString());
	}

	/**
	 * 客户端返回字符串
	 * @param response
	 * @param string
	 * @return
	 */
	protected String renderString(HttpServletResponse response, String string) {
		try {
			response.reset();
	        response.setContentType("application/json");
	        response.setCharacterEncoding("utf-8");
			response.getWriter().print(string);
			return null;
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 参数绑定异常
	 */
//	@ExceptionHandler({BindException.class, ConstraintViolationException.class, ValidationException.class})
//    public String bindException() {
//        return "error/400";
//    }

	/**
	 *系统登录异常
	 */
//	@ExceptionHandler({Exception.class})
//    public String exception() {
//        return "error/500";
//    }

	/**
	 * 空指针异常
	 */
	@ResponseBody
	@ExceptionHandler(NullPointerException.class)
	public 	AjaxJson nullException(){
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setMsg("系统错误！");
		ajaxJson.setSuccess(false);
		return ajaxJson;
	}

}
