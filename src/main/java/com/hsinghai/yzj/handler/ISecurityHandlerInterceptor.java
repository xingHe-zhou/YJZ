package com.hsinghai.yzj.handler;

import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Set;

/**
 * <h2>安全处理程序拦截器</h2>
 *
 * @author lyq
 * @since 1.0
 */
public interface ISecurityHandlerInterceptor
		extends HandlerInterceptor, Ordered {
	/**
	 * <p>
	 *     拦截器实例要包含的 URL 路径模式.
	 * </p>
	 *
	 * @return 包含的 URL 路径模式.
	 */
	default Set<String> includePathPatterns() {
		throw new IllegalStateException("You need to provide a list of included path patterns.");
	}
	
	/**
	 * <p>
	 *     拦截器实例要排除的 URL 路径模式.
	 * </p>
	 *
	 * @return 排除的 URL 路径模式.
	 */
	default Set<String> excludePathPatterns() {
		throw new IllegalStateException("You need to provide a list of excluded path patterns.");
	}
}
