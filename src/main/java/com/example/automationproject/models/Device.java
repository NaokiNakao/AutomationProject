package com.example.automationproject.models;

import jakarta.persistence.*;

@Entity
@Table(name = "devices")
public class Device {

    @Id
    @SequenceGenerator(
            name = "device_id_sequence",
            sequenceName = "device_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "device_id_sequence"
    )
    private Long id;

    private String hostname;
    private String platform;
    private int port;
    private String device;
    private String username;
    private String password;
    private String groups;

    public Device() {
    }

    public Device(Long id, String hostname, String platform, int port, String device, String username, String password, String groups) {
        this.id = id;
        this.hostname = hostname;
        this.platform = platform;
        this.port = port;
        this.device = device;
        this.username = username;
        this.password = password;
        this.groups = groups;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }
}
