package com.hsinghai.yzj.handler;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import java.util.Set;

/**
 * <h2>安全处理程序拦截器</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public interface ISecurityHandlerInterceptor
		extends HandlerInterceptor {
	/**
	 * <p>
	 *     要包含的路径模式.
	 * </p>
	 *
	 * @return 要包含的路径模式.
	 */
	default Set<String> includePathPatterns() {
		throw new IllegalStateException("You need to provide a list of included path patterns.");
	}
	
	/**
	 * <p>
	 *     要排除的路径模式.
	 * </p>
	 *
	 * @return 要排除的路径模式.
	 */
	default Set<String> excludePathPatterns() {
		throw new IllegalStateException("You need to provide a list of excluded path patterns.");
	}
	
	/**
	 * <p>
	 *     最终要添加到 {@link InterceptorRegistry } 中的路径模式.
	 * </p>
	 *
	 * @return 尝试剔除 {@link #excludePathPatterns()} 后的 {@link #includePathPatterns()}.
	 */
	default Set<String> pathPatterns() {
		Set<String> includePathPatterns = this.includePathPatterns();
		includePathPatterns.removeAll(this.excludePathPatterns());
		return includePathPatterns;
	}
}
