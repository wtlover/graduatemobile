package com.bgw.an.app.activity.chat.view;

/**
 * �Ӳ��ʱ ʱ����Щ�Ĳ��
 * @author ���� staryumou@163.com:
 * @version ����ʱ�䣺2015��11��17�� ����1:24:01 ��˵�� :
 */
public class ParallaxViewTag {
	protected int index;
	protected float xIn;
	protected float xOut;
	protected float yIn;
	protected float yOut;
	protected float alphaIn;
	protected float alphaOut;
	

	@Override
	public String toString() {
		return "ParallaxViewTag [index=" + index + ", xIn=" + xIn + ", xOut="
				+ xOut + ", yIn=" + yIn + ", yOut=" + yOut + ", alphaIn="
				+ alphaIn + ", alphaOut=" + alphaOut + "]";
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public float getxIn() {
		return xIn;
	}

	public void setxIn(float xIn) {
		this.xIn = xIn;
	}

	public float getxOut() {
		return xOut;
	}

	public void setxOut(float xOut) {
		this.xOut = xOut;
	}

	public float getyIn() {
		return yIn;
	}

	public void setyIn(float yIn) {
		this.yIn = yIn;
	}

	public float getyOut() {
		return yOut;
	}

	public void setyOut(float yOut) {
		this.yOut = yOut;
	}

	public float getAlphaIn() {
		return alphaIn;
	}

	public void setAlphaIn(float alphaIn) {
		this.alphaIn = alphaIn;
	}

	public float getAlphaOut() {
		return alphaOut;
	}

	public void setAlphaOut(float alphaOut) {
		this.alphaOut = alphaOut;
	}

}
