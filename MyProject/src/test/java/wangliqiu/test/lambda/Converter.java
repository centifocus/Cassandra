package wangliqiu.test.lambda;

@FunctionalInterface
public interface Converter<F, T> {
	T convert(F from);
}
