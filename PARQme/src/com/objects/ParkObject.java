package com.objects;

import java.util.Arrays;
import java.util.List;

public class ParkObject {
	private float lat;
	private float lon;
	private String location;
	private int spotNum;
	private int minIncrement;
	private int maxTime;
	private int unitPrice;
	
	public ParkObject(){
		
	}
	
//	public ParkObject(String loc, float lat, float lon, int spot){
//		this.lat=lat;
//		this.lon=lon;
//		this.location=loc;
//		this.spotNum=spot;
//	}
	public ParkObject(String result){
		List<String> fields = Arrays.asList(result.split("@"));
		this.location=fields.get(0);
		this.lat=Float.parseFloat(fields.get(1));
		this.lon=Float.parseFloat(fields.get(2));
		this.minIncrement=Integer.parseInt(fields.get(3));
		this.maxTime=Integer.parseInt(fields.get(4));
		this.unitPrice=Integer.parseInt(fields.get(5));
		this.spotNum=Integer.parseInt(fields.get(6));
	}
	public int getRate() {
		return minIncrement;
	}

	public void setRate(int rate) {
		this.minIncrement = rate;
	}

	public int getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}

	@Override
	public String toString(){
		return location + lat + lon + spotNum;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSpotNum() {
		return spotNum;
	}
	public void setSpotNum(int spotNum) {
		this.spotNum = spotNum;
	}

	public int getMinIncrement() {
		return minIncrement;
	}

	public void setMinIncrement(int minIncrement) {
		this.minIncrement = minIncrement;
	}

	public int getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
}