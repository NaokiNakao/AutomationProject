package com.example.automationproject.views;

import com.example.automationproject.models.User;
import com.example.automationproject.services.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;

@Route(value = "/users", layout = MainView.class)
public class UsersView extends VerticalLayout {
    private TextField username = new TextField("Username");
    private TextField password = new TextField("Password");
    private Grid<User> grid = new Grid<>(User.class);
    private Binder<User> binder = new Binder<>(User.class);
    private UserService userService;
    private Button deleteButton = new Button("Delete");
    private User selectedUser;

    public UsersView(UserService userService) {
        this.userService = userService;

        grid.setColumns("username", "password");

        grid.asSingleSelect().addValueChangeListener(e ->
                onItemClick(e.getValue()));

        selectionModeDisable();

        deleteButton.addClickListener(e-> {
            userService.deleteUser(selectedUser.getId());
            selectionModeDisable();
            refreshGrid();
        });

        var title = new H1("Users");

        add(title, getForm(), grid);

        refreshGrid();
    }

    private void selectionModeDisable() {
        deleteButton.setEnabled(false);
        selectedUser = null;
    }

    private void onItemClick(User user) {
        selectedUser = user;
        deleteButton.setEnabled(true);
    }

    private Component getForm() {
        var layout = new HorizontalLayout();
        layout.setAlignItems(Alignment.BASELINE);

        var addButton = new Button("Add");
        addButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        layout.add(username, password, addButton, deleteButton);

        binder.bindInstanceFields(this);

        addButton.addClickListener(click -> {
            try {
                User user = new User();
                binder.writeBean(user);
                userService.saveUser(user);
                refreshGrid();
                userService.updateDevicesWithNewUser(user);
            }
            catch (Exception e) {
                //
            }
        });

        return layout;
    }

    private void refreshGrid() {
        grid.setItems(userService.getAllUsers());
    }
}
