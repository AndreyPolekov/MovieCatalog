package Actions;

import DataStructure.AccountsDataBase;
import DataStructure.CustomerAccount;
import DataStructure.Memento;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthorizationInSystem {

    public Button createAccountButton;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField loginField;

    private AccountsDataBase dataBase;

    private boolean authorizationStatus;
    private Memento oldState;
    private Stage stage;

    private Button showCatalogButton;

    private CustomerAccount currentUserInSystem;

    public AuthorizationInSystem() {
        dataBase = new AccountsDataBase();
        oldState = new Memento();
        authorizationStatus = false;
    }

    public Memento getOldState() {
        return oldState;
    }

    public AccountsDataBase getDataBase() {
        return dataBase;
    }

    public void setDataBase(AccountsDataBase dataBase) {
        this.dataBase = dataBase;
    }

    public boolean getStatus() {
        return authorizationStatus;
    }

    public AuthorizationInSystem(Stage previousStage) throws IOException {
        authorizationStatus = false;
        // this.showCatalogButton=
        //Parent root = FXMLLoader.load(getClass().getResource("/AuthorizeWindow.fxml"));
        //Scene scene = new Scene(root);
        //stage = new Stage();
        //stage.setScene(scene);
        //this.previousStage = previousStage;
    }

    public void openAutorizeShow(Button showCatalogButton) {
        this.showCatalogButton = showCatalogButton;
        System.out.println(this.showCatalogButton);
        stage.show();
    }

    @FXML
    public void addNewUser() {
        if (dataBase.addNewUser(new CustomerAccount(loginField.getText(), passwordField.getText()))) {
            System.out.println(dataBase.getCustomerAccountsDataBase().size());
        }
        clearFieldsAuthorization();
        stage.close();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void clearFieldsAuthorization() {
        loginField.clear();
        passwordField.clear();
    }

    public void enterInSystem() {
        if (!authorizationStatus) {
            if (dataBase.checkIsAvailableThisUser(new CustomerAccount(loginField.getText(), passwordField.getText()))) {

                currentUserInSystem = dataBase.getUserObject(loginField.getText());
                authorizationStatus = true;
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Authorization");
                alert.setHeaderText("Enter in system");
                alert.setContentText("Enter in system done!");
                alert.showAndWait().ifPresent(rs -> {
                    if (rs == ButtonType.OK) {
                    }
                });
                oldState.setState(currentUserInSystem.getStatus());
                stage.close();
            } else {
                authorizationStatus = false;
                showErrorAuthorizationInSystem();
            }
        }
    }

    public void exitFromSystem() {
        if (authorizationStatus) {
            authorizationStatus = false;
            //   showCatalogButton.setDisable(true);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Authorization");
            alert.setHeaderText("Exit from system");
            alert.setContentText("Exit from system done!");
            alert.showAndWait().ifPresent(rs -> {
                if (rs == ButtonType.OK) {
                }
            });
            clearFieldsAuthorization();
            this.stage.close();
        }
    }

    public void initializationStartStatus() {

    }

    public CustomerAccount getCurrentUserInSystem() {
        return currentUserInSystem;
    }

    public void showErrorAuthorizationInSystem() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Sign in");
        alert.setContentText("Check your login and password");
        alert.showAndWait().ifPresent(rs -> {
        if (rs == ButtonType.OK) {
        }
    });
}

    public boolean checkCorrectInfo() {
        return false;
    }
}
