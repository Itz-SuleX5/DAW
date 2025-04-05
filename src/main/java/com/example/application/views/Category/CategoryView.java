package com.example.application.views.Category;


import com.example.application.Entities.Category;
import com.example.application.Entities.User;
import com.example.application.services.CategoryService;
import com.example.application.services.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Category")
@Route("category")
@Menu(order = 2, icon = LineAwesomeIconUrl.MONEY_BILL_WAVE_SOLID)
@Uses(Icon.class)
public class CategoryView extends VerticalLayout {
    
    private final CategoryService categoryService;

    private Grid<Category> grid = new Grid<>(Category.class);
    private TextField categoryName = new TextField("Nombre de Categoría");
    private TextField descriptionField = new TextField("Description");
    private TextField userIdField = new TextField("User ID");

    @Autowired
    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;

        // Configure the grid
        grid.setColumns("id", "name", "description", "userId");
        grid.addComponentColumn(category -> createEditButton(category));

        // Add components
        Button saveButton = new Button("Save", event -> saveCategory());
        add(grid, createFormLayout(), saveButton);

        // Show data in the grid
        updateGrid();
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(categoryName, descriptionField, userIdField);
        return formLayout;
    }

    private void saveCategory() {
        String name = categoryName.getValue();
        String description = descriptionField.getValue();
        Long userId = Long.parseLong(userIdField.getValue());

        Category category = new Category(name, description, userId);
        categoryService.saveCategory(category);
        Notification.show("Category saved!");

        // Clear form and update grid
        categoryName.clear();
        descriptionField.clear();
        userIdField.clear();
        updateGrid();
    }

    private void updateGrid() {
        grid.setItems(categoryService.getAllCategories());
    }

    private Button createEditButton(Category category) {
        Button editButton = new Button("Edit", event -> editCategory(category));
        return editButton;
    }

    private void editCategory(Category category) {
        // Set values in the form for editing
        categoryName.setValue(category.getName());
        descriptionField.setValue(category.getDescription());
        userIdField.setValue(String.valueOf(category.getUserId()));
    }
}
