package application.controllers;

import java.net.URL;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import application.AES;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class MainController implements Initializable
{
	@FXML public VBox mainVBox;
	@FXML public TextArea inputTextArea;
	@FXML public ComboBox<String> keyLengthComboBox;
	@FXML public ComboBox<String> modeComboBox;
	@FXML public ComboBox<String> paddingComboBox;
	@FXML public TextField keyTextField;
	@FXML public Button encryptButton;
	@FXML public Button decryptButton;
	@FXML public TextArea outputTextArea;
	
	@Override
	public void initialize(URL location, ResourceBundle resources)
	{
		mainVBox.setStyle("-fx-background-color: #242424;");
		
		inputTextArea.setPromptText("Enter text to encrypt/decrypt here...");
		
		keyLengthComboBox.setItems(FXCollections.observableArrayList("128", "192", "256"));
		modeComboBox.setItems(FXCollections.observableArrayList("ECB", "CBC", "OFB", "CFB", "CTR"));
		paddingComboBox.setItems(FXCollections.observableArrayList("PKCS5Padding", "PKCS7Padding"));
		
		
		keyLengthComboBox.setPromptText("Key Length");
		modeComboBox.setPromptText("Mode");
		paddingComboBox.setPromptText("Padding");
		
		keyTextField.setPromptText("Key");
		
		outputTextArea.setEditable(false);
		encryptButton.setOnAction(e ->
		{
			String keyLength = keyLengthComboBox.getSelectionModel().getSelectedItem();
			String mode = modeComboBox.getSelectionModel().getSelectedItem();
			String padding = paddingComboBox.getSelectionModel().getSelectedItem();
			String key = keyTextField.textProperty().get();
			
			if(keyLength == null || mode == null || padding == null || key == null || key.equals(""))
				return;
			if(key.length() != Integer.parseInt(keyLength)/8)
				return;
			String cipherText = "";
			try
			{
				cipherText = AES.encrypt(inputTextArea.getText(), mode, padding, key);
			}
			catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e1)
			{
				e1.printStackTrace();
			}
			outputTextArea.setText(cipherText);
			
		});
		decryptButton.setOnAction(e ->
		{
			String keyLength = keyLengthComboBox.getSelectionModel().getSelectedItem();
			String mode = modeComboBox.getSelectionModel().getSelectedItem();
			String padding = paddingComboBox.getSelectionModel().getSelectedItem();
			String key = keyTextField.textProperty().get();
			
			if(keyLength == null || mode == null || padding == null || key == null || key.equals(""))
				return;
			if(key.length() != Integer.parseInt(keyLength)/8)
				return;
			String plainText = "";
			try
			{
				plainText = AES.decrypt(inputTextArea.getText(), mode, padding, key);
			}
			catch (InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | IllegalBlockSizeException | BadPaddingException e1)
			{
				e1.printStackTrace();
			}
			outputTextArea.setText(plainText);
		});
	}
}
