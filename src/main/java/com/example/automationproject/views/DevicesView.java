package com.example.automationproject.views;

import com.example.automationproject.models.Device;
import com.example.automationproject.services.DeviceService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.IntegerField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "/devices", layout = MainView.class)
public class DevicesView extends VerticalLayout {
    private TextField device = new TextField("Device");
    private TextField hostname = new TextField("Hostname");
    private IntegerField port = new IntegerField("Port");
    private Grid<Device> grid = new Grid<>(Device.class);
    private Binder<Device> binder = new Binder<>(Device.class);
    private DeviceService deviceService;
    private Device selectedDevice;
    private Button backupButton = new Button("Backup");
    private Button deleteButton = new Button("Delete");

    public DevicesView(DeviceService deviceService) {
        this.deviceService = deviceService;

        grid.setColumns("device", "hostname", "port");

        var title = new H1("Devices");

        grid.asSingleSelect().addValueChangeListener(e ->
                onItemClick(e.getValue()));

        selectionModeDisable();

        backupButton.addClickListener(e -> {
            deviceService.backup(selectedDevice);
            selectionModeDisable();
        });

        deleteButton.addClickListener(e-> {
            deviceService.deleteDevice(selectedDevice.getId());
            deviceService.updateDevicesInventory();
            selectionModeDisable();
            refreshGrid();
        });

        add(title, getForm(), grid);

        refreshGrid();
    }

    private void selectionModeDisable() {
        backupButton.setEnabled(false);
        deleteButton.setEnabled(false);
        selectedDevice = null;
    }

    private void onItemClick(Device device) {
        selectedDevice = device;
        backupButton.setEnabled(true);
        deleteButton.setEnabled(true);
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var addButton = new Button("Add");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        layout.add(device, hostname, port, addButton, backupButton, deleteButton);

        binder.bindInstanceFields(this);

        addButton.addClickListener(click -> {
            try {
                Device device = new Device();
                device.setPlatform("cisco_ios_telnet");
                device.setUsername("admin");
                device.setPassword("cisco");
                device.setGroups("Routers");

                binder.writeBean(device);
                deviceService.saveDevice(device);
                refreshGrid();

                deviceService.updateDevicesInventory();
                deviceService.hardening(device.getDevice());
            }
            catch (Exception e) {
                //
            }
        });

        return layout;
    }

    private void refreshGrid() {
        grid.setItems(deviceService.getAllDevices());
    }
}
