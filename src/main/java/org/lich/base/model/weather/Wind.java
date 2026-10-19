package org.lich.base.model.weather;

public class Wind{
	private int deg;
	private Object speed;
	private Object gust;

	public void setDeg(int deg){
		this.deg = deg;
	}

	public int getDeg(){
		return deg;
	}

	public void setSpeed(Object speed){
		this.speed = speed;
	}

	public Object getSpeed(){
		return speed;
	}

	public void setGust(Object gust){
		this.gust = gust;
	}

	public Object getGust(){
		return gust;
	}
}
