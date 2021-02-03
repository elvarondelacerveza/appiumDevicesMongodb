package com.mongodb.starter.controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@RequestMapping(value = "/v1/")
//@CrossOrigin("*")
public class DeviceRestController {

	private final static Logger LOGGER = LoggerFactory.getLogger(DeviceRestController.class);
	private final DeviceRepository deviceRepository;
	
	public DeviceRestController(DeviceRepository deviceRepository) {
		// TODO Auto-generated constructor stub
		this.deviceRepository = deviceRepository;
	}
	
	@GetMapping(value = "/all")
	public List<Device> getAll() throws FileNotFoundException, IOException {
		return deviceRepository.findAll();
	}
	
	@GetMapping(value = "/find/{id}")
	public Device find(@PathVariable String id) {
		return deviceRepository.findOne(id);
	}

	@PostMapping(value = "/save")
	public ResponseEntity<Device> save(@RequestBody Device dev) {
		// Mandar los parametros del requestBody en su lugar se debera de llenar y mandarle esos valores de manera dinamica 		
		Device obj = deviceRepository.save(dev);
		return new ResponseEntity<Device>(obj, HttpStatus.OK);
	}

	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<Device> delete(@PathVariable String id) {
		Device dev = deviceRepository.findOne(id);
		if (dev != null) {
			deviceRepository.delete(id);
		}else {
			return new ResponseEntity<Device>(dev, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<Device>(dev, HttpStatus.OK);
	}
	
	//Get Device Valide si el Dispositivo Existe
	
	@PutMapping(value="/update/{id}")
	public ResponseEntity<Device> update(@PathVariable String id, @RequestBody Device dev){
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
		}else {
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
