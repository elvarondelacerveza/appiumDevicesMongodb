package com.mongodb.starter.repositories;

import com.mongodb.starter.models.Device;
//import com.mongodb.starter.models.Person;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository {

    Device save(Device device);

//    List<Device> saveAll(List<Device> devices);

    List<Device> findAll();

    List<Device> findAll(List<String> ids);

    Device findOne(String id);

    long count();

    long delete(String id);

    long delete(List<String> ids);

    long deleteAll();

    Device update(Device device);

//    long update(List<Device> devices);

//    double getAverageAge();

}
