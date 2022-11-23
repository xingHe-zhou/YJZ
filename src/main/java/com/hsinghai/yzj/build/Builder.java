package com.hsinghai.yzj.build;

import de.danielbechler.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * <h2>建造者</h2>
 * <p>
 *     这是一个适用于任何持有可序列化标识的类的实例建造者, 但不提供对构建目标对象的 setter 方法进行保护或是一些业务上的检查.
 *     如果有这方面的需求, 请单独继承本类来实现您的需求.
 * </p>
 *
 * @author lyq
 * @since 1.0
 */
public class Builder<T extends Serializable> {
	private final Supplier<T> noArgsConstructorSupplier;
	private Consumer<T> setterConsumerChain;
	
	private Builder(final Supplier<T> noArgsConstructorSupplier) {
		this.noArgsConstructorSupplier = Objects.requireNonNull(noArgsConstructorSupplier);
	}
	
	public static <T extends Serializable> Builder<T> builder(final Supplier<T> noArgsConstructorSupplier) {
		return new Builder<>(noArgsConstructorSupplier);
	}
	
	public <V> Builder<T> with(final BiConsumer<T, V> setterBiConsumer, final Supplier<V> setterValueSupplier) {
		Assert.notNull(setterValueSupplier, "setterValueSupplier");
		return with(setterBiConsumer, setterValueSupplier.get());
	}
	
	public <V> Builder<T> with(final BiConsumer<T, V> setterBiConsumer, final V setterValue) {
		Assert.notNull(setterBiConsumer, "setterBiConsumer");
		Assert.notNull(setterValue, "setterValue");
		Consumer<T> setterConsumer = instance -> setterBiConsumer.accept(instance, setterValue);
		if (setterConsumerChain == null) {
			setterConsumerChain = setterConsumer;
		} else {
			setterConsumerChain = setterConsumerChain.andThen(setterConsumer);
		}
		return this;
	}
	
	public T build() {
		T t = noArgsConstructorSupplier.get();
		setterConsumerChain.accept(t);
		return t;
	}
}
