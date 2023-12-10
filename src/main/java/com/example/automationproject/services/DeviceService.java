package com.example.automationproject.services;

import com.example.automationproject.models.Device;
import com.example.automationproject.repositories.DeviceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }

    public Device saveDevice(Device device) {
        return deviceRepository.save(device);
    }

    public void deleteDevice(Long id) {
        deviceRepository.deleteById(id);
    }

    public void updateDevicesInventory() {
        List<Device> devices = getAllDevices();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<List<Device>> requestEntity = new HttpEntity<>(devices, headers);

        restTemplate.exchange(
                "http://localhost:5000/api/load-devices",
                HttpMethod.POST,
                requestEntity,
                Device.class
        );
    }

    public void hardening(String device) {
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.exchange(
                "http://localhost:5000/api/hardening?hostname=" + device,
                HttpMethod.GET,
                null,
                Device.class
        );
    }

    public void backup(Device device) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Device> requestEntity = new HttpEntity<>(device, headers);

        restTemplate.exchange(
                "http://localhost:5000/api/backup",
                HttpMethod.POST,
                requestEntity,
                Device.class
        );
    }

}
