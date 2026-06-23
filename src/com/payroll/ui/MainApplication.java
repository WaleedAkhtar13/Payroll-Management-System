package com.payroll.ui;

import com.payroll.interfaces.IObserver;
import com.payroll.model.*;
import com.payroll.service.*;
import com.payroll.strategy.*;
import com.payroll.util.SessionManager;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;

public class MainApplication extends Application implements IObserver {
    private Stage primaryStage;
    private Label totalEmployeesLabel = new Label("Total: 0");
    private Label processedPayrollLabel = new Label("Processed: 0");

    // Luxury Dark Theme Blue Palette Style
    private final String BACKGROUND_COLOR = "#0F172A";
    private final String CARD_COLOR = "#1E293B";
    private final String ACCENT_BLUE = "#3B82F6";
    private final String ACCENT_GREEN = "#10B981";
    private final String ACCENT_RED = "#EF4444";
    private final String TEXT_LIGHT = "#F8FAFC";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Payroll Suite Management Studio");
        PayrollProcessor.getInstance().registerObserver(this);
        showLoginScreen();
    }

    private void showLoginScreen() {
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        Label title = new Label("PAYROLL AUTOMATION LOG IN");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.valueOf(TEXT_LIGHT));

        TextField txtUser = new TextField(); txtUser.setPromptText("Enter Username (admin / hr)");
        txtUser.setMaxWidth(280);
        txtUser.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-text-fill: white; -fx-border-color: #475569; -fx-border-radius: 4; -fx-padding: 8;");

        PasswordField txtPass = new PasswordField(); txtPass.setPromptText("Enter Password (admin123 / hr123)");
        txtPass.setMaxWidth(280);
        txtPass.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-text-fill: white; -fx-border-color: #475569; -fx-border-radius: 4; -fx-padding: 8;");

        Button btnLogin = new Button("Authenticate Account");
        btnLogin.setMinWidth(280);
        btnLogin.setStyle("-fx-background-color: " + ACCENT_BLUE + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10; -fx-cursor: hand;");

        Label lblError = new Label(); lblError.setTextFill(Color.TOMATO);

        btnLogin.setOnAction(e -> {
            if (SessionManager.getInstance().login(txtUser.getText(), txtPass.getText())) {
                showDashboard();
            } else {
                lblError.setText("Error: Invalid System Credentials!");
            }
        });

        root.getChildren().addAll(title, txtUser, txtPass, btnLogin, lblError);
        primaryStage.setScene(new Scene(root, 420, 380));
        primaryStage.show();
    }

    private void showDashboard() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: " + BACKGROUND_COLOR + ";");

        // Upper Header Status Panel
        HBox header = new HBox();
        header.setPadding(new Insets(15, 25, 15, 25));
        header.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-border-color: #334155; -fx-border-width: 0 0 1 0;");

        Label lblLogo = new Label("Payroll Workspace Manager | Session: " + SessionManager.getInstance().getCurrentUser().getRole());
        lblLogo.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        lblLogo.setTextFill(Color.valueOf(TEXT_LIGHT));

        Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
        Button btnLogout = new Button("Secure Logout");
        btnLogout.setStyle("-fx-background-color: " + ACCENT_RED + "; -fx-text-fill: white; -fx-font-weight: bold;");
        btnLogout.setOnAction(e -> {
            SessionManager.getInstance().logout();
            showLoginScreen();
        });
        header.getChildren().addAll(lblLogo, spacer, btnLogout);
        root.setTop(header);

        // Sidebar Navigation Tray
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(20));
        sidebar.setMinWidth(220);
        sidebar.setStyle("-fx-background-color: " + CARD_COLOR + ";");

        Button btnManageEmp = createSidebarButton("📊 Employee Management");
        Button btnProcessPayroll = createSidebarButton("💰 Run System Payroll");

        sidebar.getChildren().addAll(btnManageEmp, btnProcessPayroll);
        root.setLeft(sidebar);

        // Central Canvas Node Dynamic Frame
        StackPane workspace = new StackPane();
        workspace.setPadding(new Insets(25));

        // Default Stats View
        update();
        VBox defaultView = new VBox(25, totalEmployeesLabel, processedPayrollLabel);
        defaultView.setAlignment(Pos.CENTER);
        totalEmployeesLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 22)); totalEmployeesLabel.setTextFill(Color.LIGHTBLUE);
        processedPayrollLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 22)); processedPayrollLabel.setTextFill(Color.LIGHTGREEN);
        workspace.getChildren().add(defaultView);

        btnManageEmp.setOnAction(e -> workspace.getChildren().setAll(buildEmployeeCRUDWorkspace()));
        btnProcessPayroll.setOnAction(e -> workspace.getChildren().setAll(buildPayrollWorkspace()));

        root.setCenter(workspace);
        primaryStage.setScene(new Scene(root, 1050, 650));
    }

    private VBox buildEmployeeCRUDWorkspace() {
        VBox mainBox = new VBox(20);
        mainBox.setPadding(new Insets(10));

        Label title = new Label("Employee CRUD Matrix Center");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        title.setTextFill(Color.WHITE);

        // Input Controls Grid Layout
        GridPane grid = new GridPane();
        grid.setHgap(15); grid.setVgap(10);

        TextField txtId = new TextField(); txtId.setPromptText("e.g. EMP100");
        TextField txtName = new TextField(); txtName.setPromptText("Enter Full Name");
        TextField txtDept = new TextField(); txtDept.setPromptText("Engineering/HR etc.");
        TextField txtDesig = new TextField(); txtDesig.setPromptText("Software Engineer");
        TextField txtSearch = new TextField(); txtSearch.setPromptText("Type ID/Name to search filter...");
        txtSearch.setPrefWidth(250);

        // Dark Styling Context inputs
        String inputStyle = "-fx-background-color: #334155; -fx-text-fill: white; -fx-border-radius: 3; -fx-padding: 6;";
        txtId.setStyle(inputStyle); txtName.setStyle(inputStyle); txtDept.setStyle(inputStyle); txtDesig.setStyle(inputStyle);
        txtSearch.setStyle("-fx-background-color: " + CARD_COLOR + "; -fx-text-fill: white; -fx-border-color: #334155; -fx-padding: 6;");

        // Labels mapping
        grid.add(new Label("Employee ID:") {{ setTextFill(Color.LIGHTGRAY); }}, 0, 0); grid.add(txtId, 1, 0);
        grid.add(new Label("Full Name:") {{ setTextFill(Color.LIGHTGRAY); }}, 2, 0); grid.add(txtName, 3, 0);
        grid.add(new Label("Department:") {{ setTextFill(Color.LIGHTGRAY); }}, 0, 1); grid.add(txtDept, 1, 1);
        grid.add(new Label("Designation:") {{ setTextFill(Color.LIGHTGRAY); }}, 2, 1); grid.add(txtDesig, 3, 1);

        // CRUD Actions Button Box Frame Panel
        HBox actionButtons = new HBox(12);
        Button btnAdd = new Button("➕ Create Record"); btnAdd.setStyle("-fx-background-color: " + ACCENT_GREEN + "; -fx-text-fill: white; -fx-font-weight: bold;");
        Button btnUpdate = new Button("🔄 Update Record"); btnUpdate.setStyle("-fx-background-color: " + ACCENT_BLUE + "; -fx-text-fill: white; -fx-font-weight: bold;");
        Button btnDelete = new Button("❌ Delete Record"); btnDelete.setStyle("-fx-background-color: " + ACCENT_RED + "; -fx-text-fill: white; -fx-font-weight: bold;");
        actionButtons.getChildren().addAll(btnAdd, btnUpdate, btnDelete);

        // Feedback System Monitor Console Area
        Label lblFeedback = new Label("System Status: Operational."); lblFeedback.setTextFill(Color.LIGHTGREEN);
        lblFeedback.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));

        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(250);
        outputArea.setStyle("-fx-control-inner-background: " + CARD_COLOR + "; -fx-text-fill: #E2E8F0; -fx-font-family: 'Consolas';");

        // Lambda helper method to refresh view list anytime
        Runnable syncDisplayList = () -> {
            List<Employee> targetList = txtSearch.getText().isEmpty() ?
                    EmployeeManager.getInstance().getAllEmployees() :
                    EmployeeManager.getInstance().searchEmployees(txtSearch.getText());

            StringBuilder sb = new StringBuilder();
            sb.append(String.format("%-15s %-25s %-25s %-20s\n", "ID", "NAME", "DEPARTMENT", "DESIGNATION"));
            sb.append("-------------------------------------------------------------------------------------------------\n");
            for (Employee emp : targetList) {
                sb.append(String.format("%-15s %-25s %-25s %-20s\n", emp.getId(), emp.getName(), emp.getDepartment(), emp.getDesignation()));
            }
            outputArea.setText(sb.toString());
        };

        // Trigger base list on launch
        syncDisplayList.run();

        // --- CRUD LOGIC MAPPINGS ---

        // 1. CREATE ACTION
        btnAdd.setOnAction(e -> {
            if (txtId.getText().isEmpty() || txtName.getText().isEmpty()) {
                lblFeedback.setText("Validation Warning: ID and Name fields are required!");
                lblFeedback.setTextFill(Color.YELLOW);
                return;
            }
            Employee emp = new Employee(txtId.getText().trim(), txtName.getText().trim(), txtDept.getText().trim(), txtDesig.getText().trim(), "2026-06-20");
            boolean success = EmployeeManager.getInstance().addEmployee(emp);
            if (success) {
                PayrollProcessor.getInstance().setSalaryStructure(emp, new SalaryStructure(65000, 12000, 4000, 3000, 2500, 0, new StandardTaxStrategy()));
                lblFeedback.setText("Success: Created Employee Record " + emp.getId());
                lblFeedback.setTextFill(Color.LIGHTGREEN);
                syncDisplayList.run();
                update();
            } else {
                lblFeedback.setText("Error: Employee ID already registered inside collections!");
                lblFeedback.setTextFill(Color.TOMATO);
            }
        });

        // 2. UPDATE ACTION
        btnUpdate.setOnAction(e -> {
            if (txtId.getText().isEmpty()) {
                lblFeedback.setText("Validation Warning: Provide existing Employee ID context!");
                lblFeedback.setTextFill(Color.YELLOW);
                return;
            }
            Employee matched = EmployeeManager.getInstance().getEmployeeById(txtId.getText().trim());
            if (matched != null) {
                if (!txtName.getText().isEmpty()) matched.setName(txtName.getText().trim());
                if (!txtDept.getText().isEmpty()) matched.setDepartment(txtDept.getText().trim());
                if (!txtDesig.getText().isEmpty()) matched.setDesignation(txtDesig.getText().trim());

                EmployeeManager.getInstance().updateEmployee(matched);
                lblFeedback.setText("Success: Updated Employee Info for " + txtId.getText());
                lblFeedback.setTextFill(Color.LIGHTGREEN);
                syncDisplayList.run();
            } else {
                lblFeedback.setText("Error: Specified Employee tracking record does not exist!");
                lblFeedback.setTextFill(Color.TOMATO);
            }
        });

        // 3. DELETE ACTION
        btnDelete.setOnAction(e -> {
            if (txtId.getText().isEmpty()) {
                lblFeedback.setText("Validation Warning: Provide targets matching ID to drop!");
                lblFeedback.setTextFill(Color.YELLOW);
                return;
            }
            boolean success = EmployeeManager.getInstance().deleteEmployee(txtId.getText().trim());
            if (success) {
                PayrollProcessor.getInstance().removeSalaryStructure(txtId.getText().trim());
                lblFeedback.setText("Success: Purged Employee Record " + txtId.getText());
                lblFeedback.setTextFill(Color.LIGHTGREEN);
                syncDisplayList.run();
                update();
            } else {
                lblFeedback.setText("Error: Employee matching trace index target context not found.");
                lblFeedback.setTextFill(Color.TOMATO);
            }
        });

        // 4. READ/SEARCH EVENT LISTENER FILTER
        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> syncDisplayList.run());

        HBox searchTray = new HBox(10, new Label("🔍 Live Search Context Bar:") {{ setTextFill(Color.WHITE); setPadding(new Insets(5,0,0,0)); }}, txtSearch);
        mainBox.getChildren().addAll(title, grid, actionButtons, lblFeedback, searchTray, outputArea);
        return mainBox;
    }

    private VBox buildPayrollWorkspace() {
        VBox box = new VBox(15);
        Label title = new Label("Process Payroll Operations Hub");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 18));
        title.setTextFill(Color.WHITE);

        Button btnRun = new Button("🚀 Execute Automated Month Roll Run");
        btnRun.setStyle("-fx-background-color: " + ACCENT_GREEN + "; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 8; -fx-cursor: hand;");

        TextArea payslipsArea = new TextArea();
        payslipsArea.setEditable(false);
        payslipsArea.setPrefHeight(380);
        payslipsArea.setStyle("-fx-control-inner-background: " + CARD_COLOR + "; -fx-text-fill: #A7F3D0; -fx-font-family: 'Courier New'; -fx-font-size: 13px;");

        btnRun.setOnAction(e -> {
            PayrollProcessor.getInstance().runMonthlyPayroll("June", 2026);

            StringBuilder sb = new StringBuilder();
            for (PayrollRecord rec : PayrollProcessor.getInstance().getPayrollHistory()) {
                Employee emp = EmployeeManager.getInstance().getEmployeeById(rec.getEmployeeId());
                SalaryStructure struct = PayrollProcessor.getInstance().getSalaryStructure(rec.getEmployeeId());
                if (emp != null && struct != null) {
                    sb.append(PayslipGenerator.generatePayslip(emp, rec, struct)).append("\n\n");
                }
            }
            payslipsArea.setText(sb.toString());
        });

        box.getChildren().addAll(title, btnRun, payslipsArea);
        return box;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMinWidth(200);
        btn.setAlignment(Pos.BASELINE_LEFT);
        btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-padding: 10; -fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #334155; -fx-text-fill: white; -fx-font-size: 13px; -fx-padding: 10;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: #94A3B8; -fx-font-size: 13px; -fx-padding: 10;"));
        return btn;
    }

    @Override
    public void update() {
        totalEmployeesLabel.setText("Active Employees Managed Inside App: " + EmployeeManager.getInstance().getAllEmployees().size());
        processedPayrollLabel.setText("Current Batched Payroll Records Processed: " + PayrollProcessor.getInstance().getPayrollHistory().size());
    }
}