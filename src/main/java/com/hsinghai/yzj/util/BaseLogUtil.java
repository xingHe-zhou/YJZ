package com.hsinghai.yzj.util;

import org.slf4j.Logger;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiConsumer;

/**
 * <h2>日志工具</h2>
 *
 * @author 云上的云
 * @since 1.0
 */
public abstract class BaseLogUtil {
	
	public static <T> void trace(Logger logger, T target) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, target, (l, arguments) -> {
			if (l.isTraceEnabled()) l.trace("Location({})、Message({})", stackTraceElement, target);
		});
	}
	
	public static <T> void debug(Logger logger, T target) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, target, (l, arguments) -> {
			if (l.isDebugEnabled()) l.debug("Location({})、Message({})", stackTraceElement, target);
		});
	}
	
	public static <T> void info(Logger logger, T target) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, target, (l, arguments) -> {
			if (l.isInfoEnabled()) l.info("Location({})、Message({})", stackTraceElement, target);
		});
	}
	
	public static <T> void warn(Logger logger, T target) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, target, (l, arguments) -> {
			if (l.isWarnEnabled()) l.warn("Location({})、Message({})", stackTraceElement, target);
		});
	}
	
	public static <T> void error(Logger logger, T target) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, target, (l, arguments) -> {
			if (l.isErrorEnabled()) l.error("Location({})、Message({})", stackTraceElement, target);
		});
	}
	
	@SafeVarargs
	public static <E extends RuntimeException> void trace(E occurredException, Logger logger, Class<E>... serviceExceptions) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, occurredException, (l, arguments) -> {
			if (l.isTraceEnabled()) l.trace(stackTraceElement + " ------> {}:{}", arguments);
		}, serviceExceptions);
	}
	
	@SafeVarargs
	public static <E extends RuntimeException> void error(E occurredException, Logger logger, Class<E>... serviceExceptions) {
		StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[2];
		recordLog(logger, occurredException, (l, arguments) -> {
			if (l.isErrorEnabled()) l.error(stackTraceElement + " ------> {}:{}", arguments);
		}, serviceExceptions);
	}
	
	/**
	 * <p>
	 *     方法定义了 `记录日志` 流程处理的一个骨架, 但没做真正的记录操作, 这一点是通过消费商交给其它公开方法来自行记录的. <br /> <br />
	 *
	 *
	 *     请注意, 主动抛出的 {@link IllegalArgumentException} 和 {@link IllegalStateException} 以及业务层自定义的一些异常.
	 *     如, `校验信息是否正常` 这种非 `系统异常` 的 `业务异常`, 本方法总是忽略在日志中打印异常具体的堆栈信息, 只会打印异常类名称以及异常消息.
	 * </p>
	 *
	 * @param logger            日志记录器. 这是一个 slf4j 的, 因此可以适配所有不同品种的日志记录器.
	 * @param target            要记录的目标实例, 这可能是一个在处理程序执行期间发生的异常.
	 * @param loggerBiConsumer  logger 的消费商, 由其它公开方法自行消费这个 logger, 在这里的消费操作应是去执行记录.
	 * @param serviceExceptions 业务异常字节码可变列表.
	 */
	@SafeVarargs
	protected static <T, E extends RuntimeException> void recordLog(Logger logger, T target, BiConsumer<Logger, Object[]> loggerBiConsumer, Class<E>... serviceExceptions) {
		Assert.notNull(logger, "logger");
		Assert.notNull(target, "target");
		if (target instanceof RuntimeException) {
			RuntimeException occurredException = (RuntimeException) target;
			String occurredExceptionClassName = target.getClass().getName();
			String occurredExceptionMessage = occurredException.getMessage();
			if (isServiceExceptions(occurredException, serviceExceptions)) {
				loggerBiConsumer.accept(logger, new Object[]{occurredExceptionClassName, occurredExceptionMessage});
			} else {
				loggerBiConsumer.accept(logger, new Object[]{occurredExceptionClassName, occurredExceptionMessage, occurredException});
			}
		} else {
			loggerBiConsumer.accept(logger, null);
		}
	}
	
	/**
	 * <p>
	 *     给定一个异常对象, 判断此对象是否为一个业务异常. <br /> <br />
	 *
	 *
	 *     对于业务异常的定义: <br />
	 *     主动抛出的 {@link IllegalArgumentException} 和 {@link IllegalStateException} 以及业务层自定义的一些异常都是(<em>如: 校验信息是否正常这种业务异常, 而非系统异常</em>).
	 * </p>
	 *
	 * @param occurredException 在处理程序执行期间发生的异常.
	 * @param serviceExceptions 业务异常字节码实例可变列表, 这将作为 occurredException 是否为业务异常地筛选依据.
	 * @return true: occurredException 视为一个业务异常; false: occurredException 不视为一个业务异常;
	 */
	@SafeVarargs
	protected static Boolean isServiceExceptions(RuntimeException occurredException, Class<? extends RuntimeException>... serviceExceptions) {
		boolean isServiceException = Arrays
										.stream(serviceExceptions)
										.map(serviceException -> serviceException.cast(occurredException))
										.anyMatch(Objects :: nonNull);
		return occurredException instanceof IllegalArgumentException || occurredException instanceof IllegalStateException || isServiceException;
	}
}
