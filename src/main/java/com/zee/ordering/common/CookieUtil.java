package com.zee.ordering.common;

import java.util.Base64;
import java.util.Objects;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.zee.ordering.context.ResponseContext;
import com.zee.ordering.entity.User;
/**
 * 获取浏览器Cookie取
 * @author rygao
 *
 */
public class CookieUtil {
	/**
	 * 读取auth cookie值 
	 * @param request
	 * @return
	 */
//	public static String getCookieValue(HttpServletRequest request) {
//		String cookieValue = "" ;
//		if(null != request.getCookies()){
//			for (Cookie cookie : request.getCookies()) {
//				if(Objects.equals(cookie.getName(), "auth")){
//					cookieValue = cookie.getValue()  ;
//					break ;
//				}
//			}
//		}
//		return cookieValue;
//	}
	/**
	 * 新增auth cookie值 
	 * @param user
	 */
//	public static void addCookie(HttpServletRequest request,User user){
//		int expiry = 1800; //秒,用户过期时间
//		String source = user.getUserName() + "$" +user.getPassword() ; 
//		byte[] result = Base64.getEncoder().encode(source.getBytes()) ; 
//		Cookie cookie = new Cookie("auth",new String(result)) ;
//		cookie.setMaxAge(expiry);
//		ResponseContext.getCurrent().addCookie(cookie);
//	}
	
	/**
	 * 修改auth cookie值 
	 * @param user
	 */
//	public static void setCookie(HttpServletRequest request,User user){
//		int expiry = 1800; //秒,用户过期时间
//		String source = user.getUserName() + "$" +user.getPassword() ; 
//		byte[] result = Base64.getEncoder().encode(source.getBytes()) ; 
//		Cookie[] cookies = request.getCookies();
//		for (Cookie cookie : cookies) {
//			if(Objects.equals(cookie.getName(), "auth")){
//				cookie.setValue(new String(result));
//				cookie.setMaxAge(expiry);
//				ResponseContext.getCurrent().addCookie(cookie);
//				break ;
//			}
//		}
//	}
	
	/**
	 * 删除auth cookie值 
	 */
//	public static void delCookie(HttpServletRequest request){
//		int expiry = 0; //秒,立即销毁
//		Cookie[] cookies = request.getCookies();
//		if(null != cookies){
//			for (Cookie cookie : cookies) {
//				if(Objects.equals(cookie.getName(), "auth")){
//					cookie.setValue(null);
//					cookie.setMaxAge(expiry);
//					ResponseContext.getCurrent().addCookie(cookie);
//					break ;
//				}
//			}
//		}
//	}
}
