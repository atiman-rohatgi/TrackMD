package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccount {

    @FXML
    private TextField email;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private Hyperlink signInHyperlink;

    @FXML
    private TextField username1;

    public void initialize() {
    	signInHyperlink.setOnAction( e -> {
//    		FXMLLoader loginView = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
//			Stage current = (Stage)((Node) e.getSource()).getScene().getWindow();
//			current.close();
			ViewFactory.getViewFactoryInstance().showLoginView(e);
//			
//			try {
//				Scene scene = new Scene(loginView.load());
//				Stage stage = new Stage();
//				stage.setScene(scene);
//				stage.show();
//			} catch (IOException e1) {
////				// TODO Auto-generated catch block
//				e1.printStackTrace();
//				
//			}
    	});
    }
}
