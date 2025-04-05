package com.example.application.views.User;

import com.example.application.services.UserService;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.textfield.TextField;

import java.util.List;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import com.example.application.Entities.User;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;

@PageTitle("User")
@Route("User")
@Menu(order = 3, icon = LineAwesomeIconUrl.MONEY_BILL_WAVE_SOLID)
public class UserView extends VerticalLayout {
    private final UserService userService;

    private final Grid<User> grid = new Grid<>(User.class);
    private final TextField usernameField = new TextField("Username");
    private final TextField emailField = new TextField("Email");

    private Dialog dialog = new Dialog();

    public UserView(UserService userService) {
        this.userService = userService;
        setSizeFull();

        // Initialize the grid
        grid.setColumns("idUser", "username", "email");

        // Add event listener to the "Edit" button
        grid.asSingleSelect().addValueChangeListener(event -> editUser(event.getValue()));

        // Create the layout
        add(grid, createUserForm(), createButtons());

        // Load users into the grid
        refreshGrid();
    }

    private FormLayout createUserForm() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(usernameField, emailField);
        return formLayout;
    }

    private Button createSaveButton() {
        Button saveButton = new Button("Save", e -> {
            User user = new User();
            user.setUsername(usernameField.getValue());
            user.setEmail(emailField.getValue());

            // Perform the create or update operation
            try {
                if (usernameField.getValue().isEmpty() || emailField.getValue().isEmpty()) {
                    Notification.show("Username and email must be filled");
                    return;
                }

                if (userService.getUserByUsername(user.getUsername()).isPresent()) {
                    Notification.show("Username already exists.");
                    return;
                }

                userService.createUser(user);
                refreshGrid();
                dialog.close();
                Notification.show("User saved");
            } catch (Exception ex) {
                Notification.show("Error: " + ex.getMessage());
            }
        });
        return saveButton;
    }

    private Button createCancelButton() {
        Button cancelButton = new Button("Cancel", e -> dialog.close());
        return cancelButton;
    }

    private VerticalLayout createButtons() {
        Button addButton = new Button("Add User", e -> showUserDialog());
        addButton.addClickListener(e -> {
            dialog.open();
            usernameField.clear();
            emailField.clear();
        });

        VerticalLayout layout = new VerticalLayout(addButton);
        layout.setSpacing(true);
        return layout;
    }

    private void showUserDialog() {
        dialog.removeAll();
        dialog.add(createUserForm(), createSaveButton(), createCancelButton());
        dialog.open();
    }

    private void editUser(User user) {
        if (user != null) {
            usernameField.setValue(user.getUsername());
            emailField.setValue(user.getEmail());
            dialog.open();
        }
    }

    private void refreshGrid() {
        List<User> users = userService.getAllUsers();
        grid.setItems(users);
    }
}
