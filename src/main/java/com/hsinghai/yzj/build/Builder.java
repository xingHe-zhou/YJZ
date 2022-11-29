package com.hsinghai.yzj.build;

import org.springframework.lang.NonNull;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * <h2>通用建造者</h2>
 * <p>
 *     这是一个适用于任何持有可序列化标识的类的实例建造者.
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
	
	public <V> Builder<T> with(final BiConsumer<T, V> setterBiConsumer, final Supplier<V> setterValueSupplier, @NonNull Predicate<V> attributeValuePredicate) {
		Assert.notNull(setterValueSupplier, "setterValueSupplier");
		return with(setterBiConsumer, setterValueSupplier.get(), attributeValuePredicate);
	}
	
	public <V> Builder<T> with(final BiConsumer<T, V> setterBiConsumer, final V setterValue, @NonNull Predicate<V> attributeValuePredicate) {
		Assert.notNull(setterBiConsumer, "setterBiConsumer");
		Assert.notNull(setterValue, "setterValue");
		Assert.notNull(attributeValuePredicate, "attributeValuePredicate");
		
		if (attributeValuePredicate.test(setterValue)) {
			Consumer<T> setterConsumer = instance -> setterBiConsumer.accept(instance, setterValue);
			if (this.setterConsumerChain == null) {
				this.setterConsumerChain = setterConsumer;
			} else {
				this.setterConsumerChain = this.setterConsumerChain.andThen(setterConsumer);
			}
			return this;
		}
		
		throw new IllegalStateException("Failure of calibration.");
	}
	
	public T build() {
		T t = this.noArgsConstructorSupplier.get();
		if (this.setterConsumerChain == null) return t;
		this.setterConsumerChain.accept(t);
		return t;
	}
}
