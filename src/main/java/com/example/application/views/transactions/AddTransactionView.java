package com.example.application.views.transactions;

import com.example.application.Entities.Category;
import com.example.application.Entities.Transactions;
import com.example.application.services.CategoryService;
import com.example.application.services.TransactionService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.vaadin.lineawesome.LineAwesomeIconUrl;

import com.vaadin.flow.component.confirmdialog.ConfirmDialog;

@PageTitle("Add Transaction")
@Route("transactions")
@Menu(order = 1, icon = LineAwesomeIconUrl.MONEY_BILL_WAVE_SOLID)
@Uses(Icon.class)
public class AddTransactionView extends Composite<VerticalLayout> {

    private final TransactionService transactionService;
    private final CategoryService categoryService;

    private Grid<Transactions> transactionsGrid;
    private NumberField amountField;
    private TextField businessField;
    private DatePicker datePicker;
    private Select<Category> categorySelect;
    private Select<PaymentMethod> paymentMethodSelect;
    private TextArea notesField;
    private Select<String> transactionTypeSelect;
    
    private Binder<Transactions> binder;
    private Transactions currentTransaction;


    public AddTransactionView(TransactionService transactionService, CategoryService categoryService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        initializeView();
        setupForm();
        setupGrid();
        setupBindings();
        loadTransactions();
    }
    
    private void initializeView() {
        VerticalLayout layoutColumn = new VerticalLayout();
        H3 h3 = new H3("Add Transaction");
        
        amountField = new NumberField("Amount");
        businessField = new TextField("Business/Description");
        datePicker = new DatePicker("Date");
        datePicker.setValue(LocalDate.now());
        categorySelect = new Select<>();
        categorySelect.setLabel("Category");
        paymentMethodSelect = new Select<>();
        paymentMethodSelect.setLabel("Payment Method");
        notesField = new TextArea("Notes");
        transactionTypeSelect = new Select<>();
        transactionTypeSelect.setLabel("Type");
        transactionTypeSelect.setItems("Income", "Expense", "Investment", "Savings");  // Usando String
        FormLayout formLayout = new FormLayout();
        formLayout.add(businessField, amountField, datePicker, categorySelect, paymentMethodSelect, notesField, transactionTypeSelect);
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        
        HorizontalLayout buttonLayout = new HorizontalLayout();
        Button saveButton = new Button("Save", e -> saveTransaction());
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        Button cancelButton = new Button("Cancel", e -> clearForm());
        
        buttonLayout.add(saveButton, cancelButton);
        buttonLayout.setSpacing(true);
        
        transactionsGrid = new Grid<>(Transactions.class, false);
        
        layoutColumn.add(h3, formLayout, buttonLayout, transactionsGrid);
        layoutColumn.setWidth("100%");
        layoutColumn.setMaxWidth("1200px");
        layoutColumn.setAlignSelf(Alignment.CENTER);
        
        getContent().add(layoutColumn);
        getContent().setAlignItems(Alignment.CENTER);
        getContent().setJustifyContentMode(JustifyContentMode.START);
        getContent().setWidth("100%");
    }
    
    private void setupForm() {
        // Configure Category dropdown
        categorySelect.setItems(categoryService.getAllCategories());
        categorySelect.setItemLabelGenerator(Category::getName);

        // Opciones para el dropdown
        transactionTypeSelect.setItems("Income", "Expense", "Investment", "Savings");
        
        // Configure Payment Method dropdown
        List<PaymentMethod> paymentMethods = new ArrayList<>();
        paymentMethods.add(new PaymentMethod("CASH", "Cash"));
        paymentMethods.add(new PaymentMethod("CHECK", "Check"));
        paymentMethods.add(new PaymentMethod("DEBIT", "Debit Card"));
        paymentMethods.add(new PaymentMethod("CREDIT", "Credit Card"));
        paymentMethodSelect.setItems(paymentMethods);
        paymentMethodSelect.setItemLabelGenerator(PaymentMethod::getLabel);
    }
    
    private void setupGrid() {
        transactionsGrid.addColumn(Transactions::getTransactionName).setHeader("Description").setAutoWidth(true);
        transactionsGrid.addColumn(Transactions::getTransactionAmount).setHeader("Amount").setAutoWidth(true);
        transactionsGrid.addColumn(transaction -> transaction.getCategory().getName()).setHeader("Category").setAutoWidth(true);
        transactionsGrid.addColumn(Transactions::getTransactionDate).setHeader("Date").setAutoWidth(true);
    
        
        // Add edit and delete buttons
        transactionsGrid.addComponentColumn(transaction -> {
            HorizontalLayout buttonsLayout = new HorizontalLayout();
            
            Button editButton = new Button(new Icon(VaadinIcon.EDIT), e -> openEditDialog(transaction));
            editButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_TERTIARY);
            
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH), e -> confirmDelete(transaction));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_SMALL, ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_TERTIARY);
            
            buttonsLayout.add(editButton, deleteButton);
            return buttonsLayout;
        }).setHeader("Actions").setAutoWidth(true);
        
        transactionsGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_COMPACT);
        transactionsGrid.setHeight("400px");
    }
    
    private void setupBindings() {
        binder = new Binder<>(Transactions.class);
        
        binder.forField(amountField)
                .asRequired("Amount is required")
                .withConverter(Double::valueOf, Number::doubleValue)
                .bind(Transactions::getTransactionAmount, Transactions::setTransactionAmount);
        
        binder.forField(businessField)
                .asRequired("Description is required")
                .bind(Transactions::getTransactionName, Transactions::setTransactionName);
        
        binder.forField(datePicker)
                .asRequired("Date is required")
                .bind(Transactions::getTransactionDate, Transactions::setTransactionDate);
        
        binder.forField(categorySelect)
                .asRequired("Category is required")
                .bind(Transactions::getCategory, Transactions::setCategory);
        
        binder.forField(transactionTypeSelect)
                .asRequired("Transaction type is required")
                .bind(Transactions::getTransactionType, Transactions::setTransactionType);

        binder.forField(notesField)
                .bind(Transactions::getNotes, Transactions::setNotes);
    }
    
    private void saveTransaction() {
        try {
            if (currentTransaction == null) {
                currentTransaction = new Transactions();
            }
            
            binder.writeBean(currentTransaction);
            transactionService.saveTransaction(currentTransaction);
            
            Notification.show("Transaction saved successfully!", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
            
            clearForm();
            loadTransactions();
        } catch (ValidationException e) {
            Notification.show("Please fill all required fields", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        } catch (Exception e) {
            Notification.show("Error saving transaction: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_ERROR);
        }
    }
    
    private void clearForm() {
        binder.readBean(null);
        currentTransaction = null;
        amountField.clear();
        businessField.clear();
        datePicker.setValue(LocalDate.now());
        categorySelect.clear();
        paymentMethodSelect.clear();
        notesField.clear();
    }
    
    private void loadTransactions() {
        transactionsGrid.setItems(transactionService.getAllTransactions());
    }
    
    private void openEditDialog(Transactions transaction) {
        Dialog editDialog = new Dialog();
        editDialog.setHeaderTitle("Edit Transaction");
        
        FormLayout editForm = new FormLayout();
        
        NumberField editAmount = new NumberField("Amount");
        editAmount.setValue(transaction.getTransactionAmount());
        
        TextField editBusiness = new TextField("Description");
        editBusiness.setValue(transaction.getTransactionName());
        
        DatePicker editDate = new DatePicker("Date");
        editDate.setValue(transaction.getTransactionDate());
        
        Select<Category> editCategory = new Select<>();
        editCategory.setLabel("Category");
        editCategory.setItems(categoryService.getAllCategories());
        editCategory.setItemLabelGenerator(Category::getName);
        editCategory.setValue(transaction.getCategory());
        
        TextArea editNotes = new TextArea("Notes");
        editNotes.setValue(transaction.getNotes());

        Select<String> edittransactionType = new Select<>();
        edittransactionType.setLabel("Type");
        edittransactionType.setItems("Income", "Expense", "Investment", "Savings");
        edittransactionType.setValue(transaction.getTransactionType());

        
        editForm.add(editBusiness, editAmount, editDate, editCategory, editNotes, edittransactionType);
        editForm.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );
        
        Button saveButton = new Button("Save", e -> {
            // Update transaction with new values
            transaction.setTransactionAmount(editAmount.getValue());
            transaction.setTransactionName(editBusiness.getValue());
            transaction.setTransactionDate(editDate.getValue());
            transaction.setCategory(editCategory.getValue());
            transaction.setNotes(editNotes.getValue());
            transaction.setTransactionType(edittransactionType.getValue());
            
            // Save and close
            transactionService.saveTransaction(transaction);
            loadTransactions();
            editDialog.close();
            Notification.show("Transaction updated", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        
        Button cancelButton = new Button("Cancel", e -> editDialog.close());
        
        HorizontalLayout buttonLayout = new HorizontalLayout(saveButton, cancelButton);
        buttonLayout.setSpacing(true);
        
        VerticalLayout dialogLayout = new VerticalLayout(editForm, buttonLayout);
        editDialog.add(dialogLayout);
        
        editDialog.open();
    }
    
    private void confirmDelete(Transactions transaction) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Delete Transaction");
        dialog.setText("Are you sure you want to delete this transaction?");
        
        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");
        
        dialog.setConfirmText("Delete");
        dialog.addConfirmListener(event -> {
            transactionService.deleteTransaction(transaction.getIdTransactions());
            loadTransactions();
            Notification.show("Transaction deleted", 3000, Notification.Position.TOP_CENTER)
                    .addThemeVariants(NotificationVariant.LUMO_SUCCESS);
        });
        
        dialog.open();
    }
    
    // Inner class for Payment Method
    static class PaymentMethod {
        private final String value;
        private final String label;
        
        public PaymentMethod(String value, String label) {
            this.value = value;
            this.label = label;
        }
        
        public String getValue() {
            return value;
        }
        
        public String getLabel() {
            return label;
        }
    }
}
