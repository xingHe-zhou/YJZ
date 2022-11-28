package com.hsinghai.yzj.handler;

import org.assertj.core.util.Sets;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

/**
 * <h2>抽象的安全处理程序拦截器实现</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
abstract class AbstractSecurityHandlerInterceptor
		implements ISecurityHandlerInterceptor {
	
	@Override
	public Set<String> includePathPatterns() {
		return newLinkedHashSet("/**");
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
		if (elements == null) {
			return new LinkedHashSet<>(0);
		}
		Set<T> set = new LinkedHashSet<>(elements.length - 1);
		Collections.addAll(set, elements);
		return set;
	}
}
