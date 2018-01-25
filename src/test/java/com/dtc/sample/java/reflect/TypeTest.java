package com.dtc.sample.java.reflect;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

/**
 * <ul>
 * 结论：
 * <li>1. Generic中文翻译虽然是泛型之意，但带有*Generic*的方法或属性，描述的都是源代码中的声明部分</li>
 * <li>2. Class字节码虽然有类型擦除机制，但从Class中可以获取源代码中的声明部分</li>
 * </ul>
 * 
 * @author tim
 *
 */
public class TypeTest {

	public abstract class MyHashMap<K, V> extends HashMap<K, V> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -4934329231130024011L;

	}

	/**
	 * {@link java.lang.Class#getGenericSuperclass()}用例一：
	 * 
	 * <p>
	 * 检查从{@code Class<HashMap>}中获取的GenericSuperclass
	 * </p>
	 * 
	 * <ul>
	 * 结论:
	 * <li>1. 并不能获取HashMap中真实的参数类型</li>
	 * <li>2. 只能获取到源代码中的声明参数类型</li>
	 * </ul>
	 * 
	 */
	@Test
	public void testForGenericSuperclass1() {
		Map<String, Object> map = new HashMap<String, Object>();
		Type parameterizedType = map.getClass().getGenericSuperclass();
		Assert.assertTrue(parameterizedType instanceof ParameterizedType);
		ParameterizedType pt = (ParameterizedType) parameterizedType;
		Assert.assertEquals("java.util.AbstractMap<K, V>", pt.getTypeName());
		Type ptRawType = pt.getRawType();
		Assert.assertTrue(ptRawType instanceof Class);
		Assert.assertEquals("java.util.AbstractMap", ptRawType.getTypeName());
		Type[] actualTypeArguments = pt.getActualTypeArguments();
		Assert.assertTrue(actualTypeArguments[0] instanceof TypeVariable);
		Assert.assertTrue(actualTypeArguments[1] instanceof TypeVariable);
		Assert.assertEquals("K", actualTypeArguments[0].getTypeName());
		Assert.assertEquals("V", actualTypeArguments[1].getTypeName());
	}

	/**
	 * {@link java.lang.Class#getGenericSuperclass()}用例二：
	 * 
	 * <p>
	 * 检查从匿名类中获取GenericSuperclass
	 * </p>
	 * 
	 * <ul>
	 * 结论:
	 * <li>1.
	 * 每个新创建匿名实例对应着不一样的{@code Class}实例，意味着每使用一次匿名类创建实例时，都会相应地创建一个新的{@code Class}实例</li>
	 * <li>2. 通过匿名类可以获取到真实的类型参数类型，究其原因是创建一个匿名实例意味着生成一个新的源代码class声明：public class
	 * TypeTest$1 extends MyHashMap<String, Object>。
	 * {@link java.lang.Class#getGenericSuperclass()}获取的就是真实的源代码父类的声明</li>
	 * </ul>
	 * 
	 */
	@Test
	public void testForGenericSuperclass2() {
		//
		Map<String, Object> map = new MyHashMap<String, Object>() {
			private static final long serialVersionUID = 1L;
		};

		Map<String, Object> map2 = new MyHashMap<String, Object>() {
			private static final long serialVersionUID = 7679789874253479110L;
		};
		Assert.assertNotEquals(map.getClass(), map2.getClass());
		System.out.println(map.getClass().getName()); // com.dtc.sample.java.reflect.TypeTest$1
		System.out.println(map2.getClass().getName());// com.dtc.sample.java.reflect.TypeTest$2
		Type parameterizedType = map.getClass().getGenericSuperclass();
		Assert.assertTrue(parameterizedType instanceof ParameterizedType);
		ParameterizedType pt = (ParameterizedType) parameterizedType;
		Assert.assertEquals(
				"com.dtc.sample.java.reflect.TypeTest.com.dtc.sample.java.reflect.TypeTest$MyHashMap<java.lang.String, java.lang.Object>",
				pt.getTypeName());
		Type ptRawType = pt.getRawType();
		Assert.assertTrue(ptRawType instanceof Class);
		Assert.assertEquals("com.dtc.sample.java.reflect.TypeTest$MyHashMap", ptRawType.getTypeName());
		Type[] actualTypeArguments = pt.getActualTypeArguments();
		Assert.assertTrue(actualTypeArguments[0] == "".getClass());
		Assert.assertTrue(actualTypeArguments[1] == Object.class);
		Assert.assertEquals("java.lang.String", actualTypeArguments[0].getTypeName());
		Assert.assertEquals("java.lang.Object", actualTypeArguments[1].getTypeName());
		Assert.assertTrue(this.getClass() == pt.getOwnerType());
	}
	
	
	
	/**
	 * {@link Class#getTypeParameters()}测试用例一：
	 * 
	 * 通过{@link Class#getTypeParameters()}可以获取到一个类型声明中参数类型。
	 */
	@Test
	public void testForGenericDeclaration1() {
		TypeVariable<Class<Map>>[] typeParameters = Map.class.getTypeParameters();//获取到的就是Map接口定义中的<K,V>
		Assert.assertEquals("K",typeParameters[0].getTypeName());
		Assert.assertEquals("V", typeParameters[1].getTypeName());
	}
	
	private HashMap<String,Object> map;
	
	@Test
	public void test() throws NoSuchFieldException, SecurityException {
		
		Map<String,Object> mapin=new HashMap<>();
		
		Type rawType = ((ParameterizedType)this.getClass().getDeclaredField("map").getGenericType()).getRawType();
		Class<?> fieldClass=(Class<?>)rawType;
		System.out.println(fieldClass==mapin.getClass());
		
		System.out.println(fieldClass.getGenericSuperclass().getTypeName());
		
		System.out.println(mapin.getClass().getGenericSuperclass().getTypeName());
		System.out.println(String.class.getTypeName());
		
	}


}
