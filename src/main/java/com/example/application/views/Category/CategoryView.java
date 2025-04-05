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

import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Category")
@Route("category")
@Menu(order = 2, icon = LineAwesomeIconUrl.MONEY_BILL_WAVE_SOLID)
@Uses(Icon.class)
public class CategoryView extends VerticalLayout {
    
    private final CategoryService categoryService;

    private Grid<Category> grid = new Grid<>(Category.class);
    private TextField categoryName = new TextField("Nombre de Categoría");
    private ComboBox<Category.CategoryType> type = new ComboBox<>("Tipo");
    private Checkbox isActive = new Checkbox("¿Activa?");

    private Button saveButton = new Button("Guardar");
    private Button deleteButton = new Button("Eliminar");
    private Category selectedCategory = null;

    public CategoryView(CategoryService categoryService) {
        this.categoryService = categoryService;

        setSizeFull();
        configureGrid();
        configureForm();

        add(grid, getFormLayout());
        updateGrid();
    }

    private void configureGrid() {
        grid.setColumns("idCategory", "categoryName", "type", "active");  // Eliminamos la columna del usuario
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);
        grid.setSizeFull();

        grid.asSingleSelect().addValueChangeListener(event -> {
            selectedCategory = event.getValue();
            if (selectedCategory != null) {
                categoryName.setValue(selectedCategory.getCategoryName());
                type.setValue(selectedCategory.getType());
                isActive.setValue(selectedCategory.isActive());
            }
        });
    }

    private FormLayout getFormLayout() {
        type.setItems(Category.CategoryType.values());
        saveButton.addClickListener(e -> saveCategory());
        deleteButton.addClickListener(e -> deleteCategory());

        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR);

        FormLayout formLayout = new FormLayout();
        formLayout.add(categoryName, type, isActive,
                new HorizontalLayout(saveButton, deleteButton));
        return formLayout;
    }

    private void configureForm() {
        categoryName.setClearButtonVisible(true);
        isActive.setValue(true);
    }

    private void saveCategory() {
        if (categoryName.isEmpty() || type.isEmpty()) {
            Notification.show("Todos los campos son obligatorios.", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (selectedCategory == null) {
            selectedCategory = new Category();
        }

        selectedCategory.setCategoryName(categoryName.getValue());
        selectedCategory.setType(type.getValue());
        selectedCategory.setActive(isActive.getValue());

        categoryService.saveCategory(selectedCategory);  // Guardamos la categoría (puede ser nueva o actualizada)
        Notification.show("Categoría guardada.", 2000, Notification.Position.BOTTOM_START);
        clearForm();
        updateGrid();
    }

    private void deleteCategory() {
        if (selectedCategory != null && selectedCategory.getIdCategory() != null) {
            categoryService.deleteCategory(selectedCategory.getIdCategory());  // Eliminamos la categoría seleccionada
            Notification.show("Categoría eliminada.", 2000, Notification.Position.BOTTOM_START);
            clearForm();
            updateGrid();
        } else {
            Notification.show("No se seleccionó ninguna categoría para eliminar.", 3000, Notification.Position.MIDDLE);
        }
    }

    private void clearForm() {
        selectedCategory = null;
        categoryName.clear();
        type.clear();
        isActive.setValue(true);
    }

    private void updateGrid() {
        grid.setItems(categoryService.getAllCategories());  // Actualizamos el grid con todas las categorías
    }
}
