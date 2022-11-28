package com.hsinghai.yzj.handler;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * <h2>抽象的安全处理程序拦截器实现</h2>
 *
 * @author lyq
 * @since 1.0
 */
public abstract class AbstractSecurityHandlerInterceptor
		implements ISecurityHandlerInterceptor {
	protected static final String INCLUDE_ALL_URL_PATH_PATTERN = "/**";

	@Override
	public Set<String> includePathPatterns() {
		return newLinkedHashSet(INCLUDE_ALL_URL_PATH_PATTERN);
	}
	
	@Override
	public Set<String> excludePathPatterns() {
		return newLinkedHashSet();
	}
	
	/**
	 * <p>
	 *     创建一个含给定元素列表的有序集合, 若不给定一个元素则创建一个空的有序集合.
	 * </p>
	 *
	 * @param elements 添加到有序结合中的元素列表.
	 * @return 一个含 elements 的有序集合或空有序集合.
	 */
	@SafeVarargs
	private static <T> Set<T> newLinkedHashSet(T... elements) {
		if (elements == null || elements.length == 0) {
			return new LinkedHashSet<>(0);
		}
		Set<T> set = new LinkedHashSet<>(elements.length - 1);
		Collections.addAll(set, elements);
		return set;
	}
}
