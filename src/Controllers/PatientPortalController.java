package Controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import application.LoginFile;
import application.PatientFile;
import application.PatientVisitFile;
import application.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import Services.MessagingService;

public class PatientPortalController {

    @FXML
    private TextField bloodPressureTF;

    @FXML
    private TextArea commentTA;

    @FXML
    private Button editContactInfoBtn;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField heightTF;

    @FXML
    private Button logOutBtn;

    @FXML
    private ListView<String> messagesList;

    @FXML
    private Label patientName;

    @FXML
    private TextField phoneNumberTF;

    @FXML
    private TextArea replyTF;

    @FXML
    private Button sendBtn;

    @FXML
    private Hyperlink signUpBtn;

    @FXML
    private TextField tempTF;

    @FXML
    private TextField weightTF;
    
    private MessagingService messagingService = MessagingService.getInstance();
    
    public PatientPortalController() throws FileNotFoundException {
    	
    }
    
    public void initialize() throws FileNotFoundException{	
    	
    	//gets the patient visit from login or sign up
    	PatientVisitFile.getFileInstance().accessFile(
    			LoginFile.getFileInstance().getFName()
    			,LoginFile.getFileInstance().getLName()
    			,LoginFile.getFileInstance().getDateOfBirth());
    	PatientFile.getFileInstance().accessFile(
    			LoginFile.getFileInstance().getFName()
    			,LoginFile.getFileInstance().getLName()
    			,LoginFile.getFileInstance().getDateOfBirth());
    	
    	bloodPressureTF.setText(PatientVisitFile.getFileInstance().getBloodPressure());
    	weightTF.setText(PatientVisitFile.getFileInstance().getWeight());
    	heightTF.setText(PatientVisitFile.getFileInstance().getHeight());
    	tempTF.setText(PatientVisitFile.getFileInstance().getBodyTemp());
    	commentTA.setText(PatientVisitFile.getFileInstance().getHealthConcerns());
    	patientName.setText(LoginFile.getFileInstance().getFName() +" "+ LoginFile.getFileInstance().getLName());
    	phoneNumberTF.setText(PatientFile.getFileInstance().getPhone());
    	emailTF.setText(PatientFile.getFileInstance().getEmail());
    	// tests System.out.print(PatientVisitFile.getFileInstance().getBloodPressure());
    	// System.out.print("error");
    	//Handler for contact change button
    	editContactInfoBtn.setOnAction(e->{
    		PatientFile.getFileInstance().setEmail(emailTF.getText());
    		PatientFile.getFileInstance().setPhone(phoneNumberTF.getText());
    		try {
				PatientFile.getFileInstance().contactChangeFileEdit();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
    	});
    	
    	//handler for sign btn
    	signUpBtn.setOnAction(e->{
    		ViewFactory.getViewFactoryInstance().showPatientSignUpView(e);
    	});
    	
    	//handler for log out
    	logOutBtn.setOnAction(e->{
    		ViewFactory.getViewFactoryInstance().showLoginView(e);
    	});
    	
    	sendBtn.setOnAction(e -> {
    	    String messageContent = replyTF.getText().trim();
    	    if (!messageContent.isEmpty()) {
    	        String patientFirstName = LoginFile.getFileInstance().getFName(); // This needs to match exactly how conversation IDs are determined
    	        try {
    	            messagingService.sendMessageToConversation(patientFirstName, patientFirstName, messageContent); // Adjust as needed if sender/receiver logic is different
    	            loadMessages(patientFirstName); // Refresh message display
    	            replyTF.clear(); // Clear the input field after sending
    	        } catch (IllegalArgumentException ex) {
    	            System.out.println("Failed to send message: " + ex.getMessage());
    	            // Handle error (e.g., show an error message to the user)
    	        }
    	    }
    	});

    	String patientFirstName = LoginFile.getFileInstance().getFName(); // Assume this gets the logged-in patient's first name
        messagingService.ensureConversationExists(patientFirstName);
        loadMessages(patientFirstName);
    }
    
    private void loadMessages(String conversationId) {
        if (messagingService.conversationExists(conversationId)) {
            List<String> messages = messagingService.getMessagesFromConversation(conversationId)
                .stream()
                .map(message -> String.format("%s: %s", message.getSender(), message.getContent())) // Customize as needed
                .collect(Collectors.toList());
            messagesList.getItems().setAll(messages); // Display messages in the ListView
        } else {
            messagesList.getItems().clear(); // Ensure the list is clear if there's no conversation
        }
    }

}
