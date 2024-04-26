//package application;
//
//import java.io.IOException;
//
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.control.Hyperlink;
//import javafx.scene.control.RadioButton;
//import javafx.scene.control.TextField;
//import javafx.scene.control.ToggleGroup;
//import javafx.scene.input.KeyCode;
//import javafx.stage.Stage;
//
//
//public class Login {
//	@FXML
//	private TextField username;
//	@FXML
//	private TextField password;
//	@FXML
//	private Button signInBtn;
//	@FXML
//	private RadioButton patientRadioBtn;
//	@FXML
//	private RadioButton nurseRadioBtn;
//	@FXML
//	private RadioButton doctorRadioBtn;
//	@FXML
//	private Hyperlink createAccountLink;
//	@FXML
//	private ToggleGroup Iama;
//	
//	public Login() {
//		System.out.println("Login in contructor called");
//	}
//	
////	@Override
//	public void initialize() {	
//		System.out.println("initializing");
//		
//		username.setOnKeyPressed(e->{
//			if(e.getCode() == KeyCode.ENTER) {
//				password.requestFocus();
//			}
//		});
//		
//		password.setOnKeyPressed(e -> {
//			if (e.getCode() == KeyCode.ENTER) {
//				signInBtn.requestFocus();
//				signInBtn.fire();
//			}
//		});
//		
//		signInBtn.setOnAction( e -> {			
//			RadioButton selectedRB = (RadioButton) Iama.getSelectedToggle();
//			String role = selectedRB.getText();
//			String uname = username.getText();
//			String pw = password.getText();
//	
//			User user = new User(uname, pw,  role);
//			
//			authenticateUser(user);
//		});
//		
//		createAccountLink.setOnAction(e -> {
//			ViewFactory.getViewFactoryInstance().showCreateAccountView(e);
//			
//			// will be viewFactory.getAccountView!
////			FXMLLoader createAccountView = new FXMLLoader(getClass().getResource("/fxml/CreateAccount.fxml"));
////			
////			try {
////				Scene scene = new Scene(createAccountView.load());
////				Stage stage = new Stage();
////				stage.setScene(scene);
////				stage.show();
////			} catch (IOException e1) {
//////				// TODO Auto-generated catch block
////				e1.printStackTrace();
////				
////			}
////			
//		});
//	
//		
//		
//	}
//	
//	public void authenticateUser(User user) {
////		try {
////			
////		}
//		
//		
//
//		
//	}
//	
//	public boolean authorizeUser(User user) {
//		//
//		boolean x = true;
//		return x;
//	}
//	
//}


package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import java.net.URL;
import javafx.event.Event;
import java.util.ResourceBundle;

public class Login implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Button signInBtn;
    @FXML
    private RadioButton patientRadioBtn;
    @FXML
    private RadioButton nurseRadioBtn;
    @FXML
    private RadioButton doctorRadioBtn;
    @FXML
    private Hyperlink createAccountLink;
    @FXML
    private ToggleGroup Iama;
    
    public Login() {
        System.out.println("Login constructor called");
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { 
        System.out.println("initializing");
        
        // ... existing initialization code ...

        // Setup the action for the 'create account' hyperlink
        createAccountLink.setOnAction(e -> {
            try {
                showCreateAccountView(e);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
    }

    private void showCreateAccountView(Event e) throws IOException {
        // Load the create account view
        FXMLLoader createAccountLoader = new FXMLLoader(getClass().getResource("/Views/CreateAccount.fxml"));
        Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.setScene(new Scene(createAccountLoader.load()));
        stage.show();
    }
    
    private void authenticateUser(User user) {
        // Implement your authentication logic here
        // This is just a placeholder example
        if (authorizeUser(user)) {
            // If user is authorized, navigate to the next view
            System.out.println("User is authenticated and authorized.");
            // You would then show the appropriate view based on the user's role
        } else {
            // Handle the case where the user is not authorized
            System.out.println("User is not authorized.");
        }
    }
    
    private boolean authorizeUser(User user) {
		boolean x = true;
		return x;
    }
}
