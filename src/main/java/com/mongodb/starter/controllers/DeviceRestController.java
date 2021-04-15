package com.mongodb.starter.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mongodb.starter.models.Device;
import com.mongodb.starter.repositories.DeviceRepository;

@RestController
@RequestMapping(value = "/api/v1")
//@CrossOrigin("*")
public class DeviceRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(DeviceRestController.class);
	private final DeviceRepository deviceRepository;
	
	@Autowired
	Device device;
	
	public DeviceRestController(DeviceRepository deviceRepository) {
		// TODO Auto-generated constructor stub
		this.deviceRepository = deviceRepository;
	}

	@GetMapping(value = "/")
	public String hello() throws FileNotFoundException, IOException {
		return "Welcome to ITA Device Rest API Server";
	}

	@GetMapping(value = "/all")
	public List<Device> getAll() {
		List<Device> devices = new ArrayList<Device>();
		devices = deviceRepository.findAll();
		return devices;	
	}

	@GetMapping(value = "/find/{id}")
	public String find(@PathVariable String id) {
		try {
			//Convert to string
			return deviceRepository.findOne(id).toString();
		} catch (Exception e) {
			return ("The Device: " + id + " is not found");
		}
	}

	@PostMapping(value = "/save")
//	public ResponseEntity<Device> save(@RequestBody Device dev) {
		public String save(@RequestBody Device dev) {
		Device obj;
		String json;
		if (dev.getDeviceName().isEmpty()) {
			return("DeviceName can not be null.... Please add a DeviceName");
		}
		else if(dev.getOs().isEmpty()) {
			return "DeviceOs "+dev.getOs()+" is not a valid value.... Please add a new value";
		}else if(dev.getOsversion().isEmpty()) {
			return "DeviceOsVersion can not be null.... Please add a DeviceOsVersion";
		}else if(dev.getAppPackage().isEmpty()) {
			return "DeviceAppPackage can not be null.... Please add a DeviceAppPackage";
		}else if(dev.getAppActivy().isEmpty()) {
			return "DeviceAppActivity can not be null.... Please add a DeviceAppActivity";
		}else if(dev.getIp().isEmpty()) {
		    return "DeviceIP can not be null.... Please add a DeviceIP";
		}else if(dev.getPort().isEmpty()) {
			return "DevicePort can not be null.... Please add a DevicePort";
		}else {
			obj = deviceRepository.save(dev);
			json ="{\"id\":\""+obj.getId()+"\","+"\"devicename\":\" "+obj.getDeviceName()+"\"," +"\"os\":\" " + obj.getOs()+"\"," + "\"osversion\": \"" + obj.getOsversion()+"\"," + "\"apppackage\":\" " + obj.getAppPackage()+"\"," + "\"appactivity\":\" " + obj.getAppActivy()+"\"," +"\"browser\":\" " + obj.getBrowser()+"\"," + "\"ip\": \"" + obj.getIp() +"\","+ "\"port\":\"" + obj.getPort()+"\"," + "\"apklocation\":\" " + obj.getApklocation()+"\"," +"\"maxinstances\":" + obj.getMaxInstances()+"," + "\"isconnected\": " + obj.getIsConnected()+"}";
		}
		return json;
	}

	@GetMapping(value = "/delete/{id}")
	public String delete(@PathVariable String id) {
//		public ResponseEntity<Device> delete(@PathVariable String id) {
		Device dev = deviceRepository.findOne(id);
		if (dev != null) {
			deviceRepository.delete(id);
		} else {
			return "Device " + id + "is not found, please try it again";
//			return new ResponseEntity<Device>(dev, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return "The Device whit id: "+id+" has been removed. Successfully!";
//		return new ResponseEntity<Device>(dev, HttpStatus.OK);
	}

	// Get Device Validation if Device Exist

	@PutMapping(value = "/update/{id}")
	public ResponseEntity<Device> update(@PathVariable String id, @RequestBody Device dev) {
		Device toUpdate = deviceRepository.findOne(id);
		if (toUpdate != null) {
			toUpdate.setDeviceName(dev.getDeviceName());
			toUpdate.setOs(dev.getOs());
			toUpdate.setOsversion(dev.getOsversion());
			toUpdate.setAppPackage(dev.getAppPackage());
			toUpdate.setAppActivy(dev.getAppActivy());
			toUpdate.setBrowser(dev.getBrowser());
			toUpdate.setIp(dev.getIp());
			toUpdate.setPort(dev.getPort());
			toUpdate.setApklocation(dev.getApklocation());
			toUpdate.setMaxInstances(dev.getMaxInstances());
			toUpdate.setIsConnected(dev.getIsConnected());

			Device obj = deviceRepository.update(toUpdate);
			return new ResponseEntity<Device>(obj, HttpStatus.OK);
		} else {
			return new ResponseEntity<Device>(deviceRepository.findOne(id), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public final Exception handleAllExceptions(RuntimeException e) {
		LOGGER.error("Internal server error.", e);
		return e;
	}
}
