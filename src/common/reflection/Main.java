package common.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/*
 * Example of reflection in Java.
 */
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String a = "aaa";
		Class<?> processor = null;
		Object obj = null;
		try {
			processor = Class.forName("common.reflection.LineDistributorExample");
			Method method = processor.getMethod("distribute", String.class);
			obj = method.invoke(processor.newInstance(), a);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
		Integer b = (Integer) obj;
		if ( b == null ) System.out.println("NULL");
		else {
			System.out.println(b);
		}
	}

}
