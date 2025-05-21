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
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
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
    private TextField descriptionField = new TextField("Descripción");
    private TextField userIdField = new TextField("User ID");

    private Button saveButton;
    private Button cancelButton;

    private Category selectedCategory;

    @Autowired
    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;
        setWidthFull();
        setAlignItems(Alignment.CENTER);
        add(createCard());
        updateGrid();
    }

    private VerticalLayout createCard() {
        VerticalLayout card = new VerticalLayout();
        card.setWidth("100%");
        card.setMaxWidth("800px");
        card.getStyle()
            .set("box-shadow", "0 2px 6px rgba(0,0,0,0.15)")
            .set("border-radius", "8px")
            .set("padding", "2rem")
            .set("background-color", "#1d2b3a");

        H3 title = new H3("Manage Categories");
        title.getStyle().set("color", "#f0f0f0");

        FormLayout form = createFormLayout();

        saveButton = new Button("Save", event -> saveCategory());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);

        cancelButton = new Button("Cancel", event -> clearForm());
        cancelButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        cancelButton.setIcon(new Icon(VaadinIcon.CLOSE));

        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);

        grid.setColumns("id", "name", "description", "userId");
        grid.setSelectionMode(Grid.SelectionMode.NONE);
        grid.addComponentColumn(this::createActionButtons).setHeader("Actions");
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        grid.setWidthFull();

        card.add(title, form, buttonLayout, grid);
        return card;
    }

    private FormLayout createFormLayout() {
        FormLayout formLayout = new FormLayout();
        formLayout.add(categoryName, descriptionField, userIdField);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("600px", 2)
        );
        return formLayout;
    }

    private HorizontalLayout createActionButtons(Category category) {
        Button editButton = new Button(new Icon(VaadinIcon.EDIT));
        editButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_TERTIARY);
        editButton.getElement().setProperty("title", "Edit");
        editButton.addClickListener(event -> editCategory(category));

        Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ICON, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
        deleteButton.getElement().setProperty("title", "Delete");
        deleteButton.addClickListener(event -> {
            categoryService.deleteCategory(category.getId());
            Notification.show("Category deleted");
            updateGrid();
            clearForm();
        });

        return new HorizontalLayout(editButton, deleteButton);
    }

    private void saveCategory() {
        try {
            String name = categoryName.getValue();
            String description = descriptionField.getValue();
            Long userId = Long.parseLong(userIdField.getValue());

            if (selectedCategory == null) {
                selectedCategory = new Category(name, description, userId);
            } else {
                selectedCategory.setName(name);
                selectedCategory.setDescription(description);
                selectedCategory.setUserId(userId);
            }

            categoryService.saveCategory(selectedCategory);
            Notification.show("Category saved!");
            updateGrid();
            clearForm();

        } catch (NumberFormatException e) {
            Notification.show("Invalid user ID", 3000, Notification.Position.MIDDLE);
        }
    }

    private void editCategory(Category category) {
        selectedCategory = category;
        categoryName.setValue(category.getName());
        descriptionField.setValue(category.getDescription());
        userIdField.setValue(String.valueOf(category.getUserId()));
    }

    private void clearForm() {
        categoryName.clear();
        descriptionField.clear();
        userIdField.clear();
        selectedCategory = null;
    }

    private void updateGrid() {
        grid.setItems(categoryService.getAllCategories());
    }
}
