
package com.tj.cloud.core.utils;

import com.tj.cloud.core.constant.CommonConstant;
import com.tj.cloud.core.http.CloudResult;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * 简化{@code R<T>} 的访问操作,例子 <pre>
 * CloudResult<Integer> result = CloudResult.ok(0);
 * // 使用场景1: 链式操作: 断言然后消费
 * RetOps.of(result)
 * 		.assertCode(-1,r -> new RuntimeException("error "+r.getCode()))
 * 		.assertDataNotEmpty(r -> new IllegalStateException("oops!"))
 * 		.useData(System.out::println);
 *
 * // 使用场景2: 读取原始值(data),这里返回的是Optional
 * RetOps.of(result).getData().orElse(null);
 *
 * // 使用场景3: 类型转换
 * R<String> s = RetOps.of(result)
 *        .assertDataNotNull(r -> new IllegalStateException("nani??"))
 *        .map(i -> Integer.toHexString(i))
 *        .peek();
 * </pre> * @Author codingMan_tj * @Date 2024/3/26 11:58 * @version v1.0.0
 */
public class RetOps<T> {

	/** 状态码为成功 */
	public static final Predicate<CloudResult<?>> CODE_SUCCESS = r -> CommonConstant.SUCCESS == r.getCode();

	/** 数据有值 */
	public static final Predicate<CloudResult<?>> HAS_DATA = r -> ObjectUtils.isNotEmpty(r.getData());

	/** 数据有值,并且包含元素 */
	public static final Predicate<CloudResult<?>> HAS_ELEMENT = r -> ObjectUtils.isNotEmpty(r.getData());

	/** 状态码为成功并且有值 */
	public static final Predicate<CloudResult<?>> DATA_AVAILABLE = CODE_SUCCESS.and(HAS_DATA);

	private final CloudResult<T> original;

	// ~ 初始化
	// ===================================================================================================

	RetOps(CloudResult<T> original) {
		this.original = original;
	}

	public static <T> RetOps<T> of(CloudResult<T> original) {
		return new RetOps<>(Objects.requireNonNull(original));
	}

	// ~ 杂项方法
	// ===================================================================================================

	/**
	 * 观察原始值
	 * @return R
	 */
	public CloudResult<T> peek() {
		return original;
	}

	/**
	 * 读取{@code code}的值
	 * @return 返回code的值
	 */
	public int getCode() {
		return original.getCode();
	}

	/**
	 * 读取{@code data}的值
	 * @return 返回 Optional 包装的data
	 */
	public Optional<T> getData() {
		return Optional.ofNullable(original.getData());
	}

	/**
	 * 有条件地读取{@code data}的值
	 * @param predicate 断言函数
	 * @return 返回 Optional 包装的data,如果断言失败返回empty
	 */
	public Optional<T> getDataIf(Predicate<? super CloudResult<?>> predicate) {
		return predicate.test(original) ? getData() : Optional.empty();
	}

	/**
	 * 读取{@code msg}的值
	 * @return 返回Optional包装的 msg
	 */
	public Optional<String> getMsg() {
		return Optional.of(original.getDesc());
	}

	/**
	 * 对{@code code}的值进行相等性测试
	 * @param value 基准值
	 * @return 返回ture表示相等
	 */
	public boolean codeEquals(int value) {
		return original.getCode() == value;
	}

	/**
	 * 对{@code code}的值进行相等性测试
	 * @param value 基准值
	 * @return 返回ture表示不相等
	 */
	public boolean codeNotEquals(int value) {
		return !codeEquals(value);
	}

	/**
	 * 是否成功
	 * @return 返回ture表示成功
	 * @see CommonConstant#SUCCESS
	 */
	public boolean isSuccess() {
		return codeEquals(CommonConstant.SUCCESS);
	}

	/**
	 * 是否失败
	 * @return 返回ture表示失败
	 */
	public boolean notSuccess() {
		return !isSuccess();
	}

	// ~ 链式操作
	// ===================================================================================================

	/**
	 * 断言{@code code}的值
	 * @param expect 预期的值
	 * @param func 用户函数,负责创建异常对象
	 * @param <Ex> 异常类型
	 * @return 返回实例，以便于继续进行链式操作
	 * @throws Ex 断言失败时抛出
	 */
	public <Ex extends Exception> RetOps<T> assertCode(int expect, Function<? super CloudResult<T>, ? extends Ex> func)
			throws Ex {
		if (codeNotEquals(expect)) {
			throw func.apply(original);
		}
		return this;
	}

	/**
	 * 断言成功
	 * @param func 用户函数,负责创建异常对象
	 * @param <Ex> 异常类型
	 * @return 返回实例，以便于继续进行链式操作
	 * @throws Ex 断言失败时抛出
	 */
	public <Ex extends Exception> RetOps<T> assertSuccess(Function<? super CloudResult<T>, ? extends Ex> func)
			throws Ex {
		return assertCode(CommonConstant.SUCCESS, func);
	}

	/**
	 * 断言业务数据有值
	 * @param func 用户函数,负责创建异常对象
	 * @param <Ex> 异常类型
	 * @return 返回实例，以便于继续进行链式操作
	 * @throws Ex 断言失败时抛出
	 */
	public <Ex extends Exception> RetOps<T> assertDataNotNull(Function<? super CloudResult<T>, ? extends Ex> func)
			throws Ex {
		if (Objects.isNull(original.getData())) {
			throw func.apply(original);
		}
		return this;
	}

	/**
	 * 断言业务数据有值,并且包含元素
	 * @param func 用户函数,负责创建异常对象
	 * @param <Ex> 异常类型
	 * @return 返回实例，以便于继续进行链式操作
	 * @throws Ex 断言失败时抛出
	 */
	public <Ex extends Exception> RetOps<T> assertDataNotEmpty(Function<? super CloudResult<T>, ? extends Ex> func)
			throws Ex {
		if (ObjectUtils.isEmpty(original.getData())) {
			throw func.apply(original);
		}
		return this;
	}

	/**
	 * 对业务数据(data)转换
	 * @param mapper 业务数据转换函数
	 * @param <U> 数据类型
	 * @return 返回新实例，以便于继续进行链式操作
	 */
	public <U> RetOps<U> map(Function<? super T, ? extends U> mapper) {
		CloudResult<U> result = CloudResult.restResult(mapper.apply(original.getData()), original.getCode(),
				original.getDesc());
		return of(result);
	}

	/**
	 * 对业务数据(data)转换
	 * @param predicate 断言函数
	 * @param mapper 业务数据转换函数
	 * @param <U> 数据类型
	 * @return 返回新实例，以便于继续进行链式操作
	 * @see RetOps#CODE_SUCCESS
	 * @see RetOps#HAS_DATA
	 * @see RetOps#HAS_ELEMENT
	 * @see RetOps#DATA_AVAILABLE
	 */
	public <U> RetOps<U> mapIf(Predicate<? super CloudResult<T>> predicate, Function<? super T, ? extends U> mapper) {
		CloudResult<U> result = CloudResult.restResult(mapper.apply(original.getData()), original.getCode(),
				original.getDesc());
		return of(result);
	}

	// ~ 数据消费
	// ===================================================================================================

	/**
	 * 消费数据,注意此方法保证数据可用
	 * @param consumer 消费函数
	 */
	public void useData(Consumer<? super T> consumer) {
		consumer.accept(original.getData());
	}

	/**
	 * 条件消费(错误代码匹配某个值)
	 * @param consumer 消费函数
	 * @param codes 错误代码集合,匹配任意一个则调用消费函数
	 */
	public void useDataOnCode(Consumer<? super T> consumer, int... codes) {
		useDataIf(o -> Arrays.stream(codes).filter(c -> original.getCode() == c).findFirst().isPresent(), consumer);
	}

	/**
	 * 条件消费(错误代码表示成功)
	 * @param consumer 消费函数
	 */
	public void useDataIfSuccess(Consumer<? super T> consumer) {
		useDataIf(CODE_SUCCESS, consumer);
	}

	/**
	 * 条件消费
	 * @param predicate 断言函数
	 * @param consumer 消费函数,断言函数返回{@code true}时被调用
	 * @see RetOps#CODE_SUCCESS
	 * @see RetOps#HAS_DATA
	 * @see RetOps#HAS_ELEMENT
	 * @see RetOps#DATA_AVAILABLE
	 */
	public void useDataIf(Predicate<? super CloudResult<T>> predicate, Consumer<? super T> consumer) {
		if (predicate.test(original)) {
			consumer.accept(original.getData());
		}
	}

}
