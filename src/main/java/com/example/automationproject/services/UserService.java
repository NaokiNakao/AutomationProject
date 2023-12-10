package com.example.automationproject.services;

import com.example.automationproject.models.Device;
import com.example.automationproject.models.User;
import com.example.automationproject.repositories.UserRepository;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Optional<User> user = getUserById(id);

        HttpEntity<User> requestEntity = new HttpEntity<>(user.get(), headers);

        restTemplate.exchange(
                "http://localhost:5000/api/remove-user",
                HttpMethod.POST,
                requestEntity,
                User.class
        );
        userRepository.deleteById(id);
    }

    public void updateDevicesWithNewUser(User user) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<User> requestEntity = new HttpEntity<>(user, headers);

        restTemplate.exchange(
                "http://localhost:5000/api/add-user",
                HttpMethod.POST,
                requestEntity,
                User.class
        );
    }

}
