//package com.hebeu.service.utils;
//
//import java.lang.reflect.Method;
//
//import javax.servlet.http.HttpServletRequest;
//
//import com.hebeu.common.DateUtil;
//import com.hebeu.common.HttpUtil;
//import com.hebeu.common.IDUtil;
//import com.hebeu.common.IPUtil;
//import com.hebeu.model.LogsModel;
//import com.hebeu.common.annotation.ActionC;
//import com.hebeu.common.annotation.ActionT;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.alibaba.fastjson.JSONObject;
//
//
//@Aspect
//@Slf4j
//@Component
//public class LogAspect {
//	@Autowired
//	private LogsMapper logsMapper;
//
//	@Autowired
//	public void setLogsMapper(LogsMapper logsMapper) {
//		this.logsMapper = logsMapper;
//	}
//
//	@Around("execution(* com.hebeu.service.impl.*.*(..))")
//	public Object logAround(ProceedingJoinPoint pjp) {
//		LogsModel logsModel = new LogsModel();
//		Object returnObj = null;
//		Object[] targetArgs = pjp.getArgs();
//		String args = null;
//		logsModel.setId(IDUtil.getShortUUID());
//		if(targetArgs.length>0) {
//			//参数序列化
//			args = JSONObject.toJSONString(targetArgs);
//			logsModel.setRequestArgs(args);
//		}
////		String ClassName = pjp.getTarget().getClass().getName();
//		String className = pjp.getSignature().getDeclaringTypeName();
//		logsModel.setClassName(className);
//		MethodSignature methodSignature = (MethodSignature)pjp.getSignature();
//		String methodName = methodSignature.getName();
//		logsModel.setMethodName(methodName);
//		HttpServletRequest request = HttpUtil.getHttpServletRequest();
//		//请求IP
//		String requestIp = IPUtil.getIpAddr(request);
//		logsModel.setRequestIp(requestIp);
////		Admins admins = (Admins)request.getSession().getAttribute("adminInfo");
////		String adminName = admins.getAdminname();
////		logsModel.setOperatorName(adminName);
//		//获取method类对象
//		Method method = methodSignature.getMethod();
//		String logType ="";
//		try{
//			String origin = HttpUtil.getHttpServletRequest().getHeader("Origin");
//			String referer = HttpUtil.getHttpServletRequest().getHeader("Referer");
//			if(referer!=null){
//				referer = referer.replaceAll(origin,"");
//				referer = referer.substring(1,referer.length());
//				if(referer.substring(0,referer.indexOf("/")).equals("vendor")){
//					logType+="管理员";
//				}else{
//					logType+="客户";
//				}
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//		}
//		if(method.isAnnotationPresent(ActionT.class)) {
//			ActionT action = method.getAnnotation(ActionT.class);
//			logType += analysisActionT(action);
//		}
//		if(method.isAnnotationPresent(ActionC.class)) {
//			ActionC actionC = method.getAnnotation(ActionC.class);
//			logType += analysisActionC(actionC);
//		}
//		logsModel.setLogType(logType);
//		try {
//			//记录开始时间
//			long beginTime = System.currentTimeMillis();
//			returnObj = pjp.proceed();
//			long endTime = System.currentTimeMillis();
//			logsModel.setExecutionTime(endTime-beginTime);
//			logsModel.setCreateDate(DateUtil.getCurrentTimeMill());
//			if(method.isAnnotationPresent(ActionT.class)) {
//				this.logsMapper.insert(logsModel);
//			}
//			return returnObj;
//		} catch (Throwable e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	public String analysisActionT(ActionT actionT) {
//		switch (actionT.value()) {
//		case LOGIN:
//			return "登录";
//		case QUERY:
//			return "查询";
//		case INSERT:
//			return "添加";
//		case DELETE:
//			return "删除";
//		case UPDATE:
//			return "修改";
//		default:
//			return "未定义";
//		}
//	}
//
//	public String analysisActionC(ActionC actionC) {
//		switch (actionC.value()) {
//		case CART:
//			return "购物车信息";
//		case CLIENT:
//			return "客户信息";
//		case GOODS:
//			return "商品信息";
//		case ORDER:
//			return "交易信息";
//		case VENDOR:
//			return "商户信息";
//		default:
//			return "未定义";
//		}
//	}
//}
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
