package com.dtc.sample.java.clonable;

import org.junit.Assert;
import org.junit.Test;
/**
 * clonable的用例
 * 
 * 总结：
 * 如果要对一个对象进行深度克隆操作，需要对该对象内部的所有复杂类型（不包括基础类型与基础类型的包装类），都进行clone()操作
 * 
 * @author tim
 */
public class ClonableTest {

	/**
	 * clonable用例一：浅克隆
	 * 
	 * 浅克隆只是为克隆对象创建了一个新的引用，但并没有克隆其内部的对象，内部对象还是使用同一个引用。
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void testShallowClone() throws CloneNotSupportedException {
		AbstractBox srcObject = new ShallowCloneBox(1000, true, 10000L, new Pen(1111, 11111));
		AbstractBox cloneObject = (AbstractBox) srcObject.clone();
		Assert.assertTrue(srcObject != cloneObject); // 经过clone之后，对象是不一样了
		Assert.assertTrue(srcObject.getOpenStatus() == cloneObject.getOpenStatus());
		Assert.assertTrue(srcObject.getHigh() == cloneObject.getHigh());
		Assert.assertTrue(srcObject.getPen() == cloneObject.getPen());
		Assert.assertTrue(srcObject.getWidth() == cloneObject.getWidth());
	}

	/**
	 * clonable用例二：深度克隆
	 * 
	 * 深度克隆要求对克隆对象内部的每一个对象(除了基础类型外）都要进行clone操作
	 * 
	 * @throws CloneNotSupportedException
	 */
	@Test
	public void testDeepClone() throws CloneNotSupportedException {
		AbstractBox srcObject = new DeepCloneBox(1000, true, 10000L, new Pen(1111, 11111));
		AbstractBox cloneObject = (AbstractBox) srcObject.clone();
		Assert.assertTrue(srcObject != cloneObject);
		Assert.assertTrue(srcObject.getOpenStatus() == cloneObject.getOpenStatus());
		Assert.assertTrue(srcObject.getHigh() == cloneObject.getHigh());
		Assert.assertTrue(srcObject.getPen() != cloneObject.getPen());
		Assert.assertTrue(srcObject.getWidth() == cloneObject.getWidth());

		// 这里证明了基本类型是没有必要clone的，因为基本类型每次设置就是一个全新的值了
		srcObject.setWidth(1L);
		cloneObject.setWidth(1999L);
		System.out.println(srcObject.getWidth()); // 1L
		System.out.println(cloneObject.getWidth()); // 1999L
		Assert.assertTrue(srcObject.getWidth() != cloneObject.getWidth());// true
	}

	private static class ShallowCloneBox extends AbstractBox {

		public ShallowCloneBox(int high, Boolean openStatus, Long width, Pen pen) {
			super(high, openStatus, width, pen);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}

	private static class DeepCloneBox extends AbstractBox {

		public DeepCloneBox(int high, Boolean openStatus, Long width, Pen pen) {
			super(high, openStatus, width, pen);
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			AbstractBox clone = (AbstractBox) super.clone();
			clone.setPen((Pen) clone.getPen().clone());
			return clone;
		}

	}

	private static abstract class AbstractBox implements Cloneable {

		private int high;

		private Boolean openStatus;

		private Long width;

		private Pen pen;

		public AbstractBox(int high, Boolean openStatus, Long width, Pen pen) {
			super();
			this.high = high;
			this.openStatus = openStatus;
			this.width = width;
			this.pen = pen;
		}

		public int getHigh() {
			return high;
		}

		public void setHigh(int high) {
			this.high = high;
		}

		public Boolean getOpenStatus() {
			return openStatus;
		}

		public void setOpenStatus(Boolean openStatus) {
			this.openStatus = openStatus;
		}

		public Long getWidth() {
			return width;
		}

		public void setWidth(Long width) {
			this.width = width;
		}

		public Pen getPen() {
			return pen;
		}

		public void setPen(Pen pen) {
			this.pen = pen;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}
	}

	private static class Pen implements Cloneable {

		private int type;

		private Integer size;

		public int getType() {
			return type;
		}

		public void setType(int type) {
			this.type = type;
		}

		public Integer getSize() {
			return size;
		}

		public void setSize(Integer size) {
			this.size = size;
		}

		public Pen(int type, Integer size) {
			super();
			this.type = type;
			this.size = size;
		}

		@Override
		protected Object clone() throws CloneNotSupportedException {
			return super.clone();
		}

	}
}
