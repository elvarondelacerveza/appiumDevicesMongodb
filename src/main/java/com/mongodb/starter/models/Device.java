package com.mongodb.starter.models;

import java.util.Objects;

import org.bson.types.ObjectId;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;

@JsonInclude(Include.NON_NULL)
public class Device {

	@JsonSerialize(using = ToStringSerializer.class)
	private ObjectId id;
	private String devicename;
	private String os;
	private String osversion;
	private String apppackage;
	private String appactivity;
	private String browser;
	private String ip;
	private String port;
	private String apklocation;
	private Integer maxinstances;
	private boolean isconnected;


	public Device(){
		
	}

	public Device(String devicename, String os, String osversion, String apppackage, String appactivity, String browser,
			String ip, String port, String apklocation, Integer maxinstances, boolean isconnected) {
		super();
		this.devicename = devicename;
		this.os = os;
		this.osversion = osversion;
		this.apppackage = apppackage;
		this.appactivity = appactivity;
		this.browser = browser;
		this.ip = ip;
		this.port = port;
		this.apklocation = apklocation;
		this.maxinstances = maxinstances;
		this.isconnected = isconnected;
	}

	public ObjectId getId() {
		return id;
	}

	public Device setId(ObjectId id) {
		this.id = id;
		return this;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsversion() {
		return osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getApklocation() {
		return apklocation;
	}

	public void setApklocation(String apklocation) {
		this.apklocation = apklocation;
	}

	public boolean getIsConnected() {
		return isconnected;
	}

	public void setIsConnected(boolean isconnected) {
		this.isconnected = isconnected;
	}

	public String getDeviceName() {
		return devicename;
	}

	public void setDeviceName(String deviceName) {
		this.devicename = deviceName;
	}

	public Integer getMaxInstances() {
		return maxinstances;
	}

	public void setMaxInstances(Integer maxInstances) {
		this.maxinstances = maxInstances;
	}

	public String getAppPackage() {
		return apppackage;
	}

	public void setAppPackage(String appPackage) {
		this.apppackage = appPackage;
	}

	public String getAppActivy() {
		return appactivity;
	}

	public void setAppActivy(String appActivity) {
		this.appactivity = appActivity;
	}

	public String getBrowser() {
		return browser;
	}

	public void setBrowser(String browser) {
		this.browser = browser;
	}

	
	
	@Override
	public String toString() {
		return "Device [id=" + id + ", devicename=" + devicename + ", os=" + os + ", osversion=" + osversion
				+ ", apppackage=" + apppackage + ", appactivity=" + appactivity + ", browser=" + browser + ", ip=" + ip
				+ ", port=" + port + ", apklocation=" + apklocation + ", maxinstances=" + maxinstances
				+ ", isconnected=" + isconnected + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, devicename, os, osversion, apppackage, appactivity, browser, ip, port, apklocation,
				maxinstances, isconnected);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Device other = (Device) obj;
		if (apklocation == null) {
			if (other.apklocation != null)
				return false;
		} else if (!apklocation.equals(other.apklocation))
			return false;
		if (appactivity == null) {
			if (other.appactivity != null)
				return false;
		} else if (!appactivity.equals(other.appactivity))
			return false;
		if (apppackage == null) {
			if (other.apppackage != null)
				return false;
		} else if (!apppackage.equals(other.apppackage))
			return false;
		if (browser == null) {
			if (other.browser != null)
				return false;
		} else if (!browser.equals(other.browser))
			return false;
		if (devicename == null) {
			if (other.devicename != null)
				return false;
		} else if (!devicename.equals(other.devicename))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ip == null) {
			if (other.ip != null)
				return false;
		} else if (!ip.equals(other.ip))
			return false;
		if (isconnected != other.isconnected)
			return false;
		if (maxinstances == null) {
			if (other.maxinstances != null)
				return false;
		} else if (!maxinstances.equals(other.maxinstances))
			return false;
		if (os == null) {
			if (other.os != null)
				return false;
		} else if (!os.equals(other.os))
			return false;
		if (osversion == null) {
			if (other.osversion != null)
				return false;
		} else if (!osversion.equals(other.osversion))
			return false;
		if (port == null) {
			if (other.port != null)
				return false;
		} else if (!port.equals(other.port))
			return false;
		return true;
	}

}
