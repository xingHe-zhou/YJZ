package com.hsinghai.yzj.config;

import com.hsinghai.yzj.handler.ISecurityHandlerInterceptor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.LinkedList;
import java.util.List;

/**
 * <h2>处理程序拦截器配置</h2>
 * <p>
 *     一个专门处理 {@link HandlerInterceptor} 的配置类,
 *     有且仅有重写了 {@link #addInterceptors} 方法, 这是为了对配置信息做逻辑上的隔离.
 * </p>
 *
 * @author lyq
 * @since 1.0
 */
@Configuration
public class HandlerInterceptorConfig
		implements WebMvcConfigurer {
	final ApplicationContext applicationContext;
	final List<ISecurityHandlerInterceptor> interceptors;
	
	public HandlerInterceptorConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
		this.interceptors = new LinkedList<>(applicationContext.getBeansOfType(ISecurityHandlerInterceptor.class).values());
	}
	
	@Override
	public void addInterceptors(@NonNull InterceptorRegistry registry) {
		this.interceptors.forEach(
				interceptor -> registry
						.addInterceptor(interceptor)
						.addPathPatterns(new LinkedList<>(interceptor.includePathPatterns()))
						.excludePathPatterns(new LinkedList<>(interceptor.excludePathPatterns()))
		);
	}
}
