package application;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import scanning.ScannerLibrary;

/*
 * Copyright © 2010 by Chase E. Arline
All rights reserved. This program or any portion thereof
may not be reproduced or used in any manner whatsoever
without the express written permission of the publisher.
 */

public class Applicat extends Application {
	static FadeTransition transit = new FadeTransition();
	Group fadeGroup = new Group();
	GridPane mainGrid = new GridPane();
	static ScannerLibrary lib;
	static File path;
	
	@Override
	public void init() {
		  try {
			startUp();
		} catch (EncryptedDocumentException | InvalidFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  transit.setToValue(0);
		  transit.setCycleCount(Timeline.INDEFINITE);
		  transit.setDuration(new Duration(1000));
	      transit.setAutoReverse(true);
	      
	}
	
	@Override 
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Club Sign-in");
		mainGrid.setAlignment(Pos.CENTER);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(25,25,25,25));
		
		Scene scene = new Scene(mainGrid,375,250);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(Applicat.class.getResource
				("cascadingSheet.css").toExternalForm());
		
		Font basic = Font.font("Tahoma",FontWeight.NORMAL,18);
		
		Text sceneTitle = new Text("Login");
		sceneTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,32));
		
		sceneTitle.setFill(Color.WHITE);
		
		Label userName = new Label("ID Number: ");
	
		userName.setTextFill(Color.WHITE);
		userName.setFont(Font.font("Tahoma",FontWeight.NORMAL,18));
		
		TextField userTextField = new TextField();
		
		
		
		Label scanning = new Label("Waiting for scan");
		scanning.setFont(Font.font(16));
		scanning.setFont(basic);
		scanning.setTextFill(Color.rgb(144, 161, 127));
		Button loginButton = new Button("Login");
		Button signupButton = new Button("Sign up");
		HBox loginBox = new HBox(10);
		HBox signupBox = new HBox(10);
		loginBox.setAlignment(Pos.BOTTOM_RIGHT);
		loginBox.getChildren().add(loginButton);
		signupBox.setAlignment(Pos.BOTTOM_LEFT);
		signupBox.getChildren().add(signupButton);
		
		final Text actionText = new Text();
		actionText.setFill(Color.WHITE);	
		
		mainGrid.add(sceneTitle, 0,0, 2,1);
		
		mainGrid.add(userName, 0,1, 2,1);
		mainGrid.add(userTextField, 2,1, 2,1);
		
		mainGrid.add(actionText, 3, 4,2,1);
		
		mainGrid.add(scanning, 3, 0,2,1);
		mainGrid.add(signupBox, 2,3, 1,1);
		mainGrid.add(loginBox, 3,3, 1,1);
		transit.setNode(scanning);
		transit.play();
		
		userTextField.requestFocus();
		
		userTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
	        @Override
	        public void handle(KeyEvent ke) {
	            KeyCode kc = ke.getCode();
	            if ((kc.equals(KeyCode.ENTER) || kc.equals(KeyCode.UP) || kc.equals(KeyCode.DOWN) || kc.equals(KeyCode.LEFT) || kc.equals(KeyCode.RIGHT))) {
	               loginButton.fire();
	            }
	        }
	    }); 
		
		loginButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		      System.out.println(fadeGroup.getChildren().size());
		      
		    }
		});
		
		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		      
		    
		    }
		});
		
		
		primaryStage.show();
		
	}
	
	
	
	public static void startUp() throws EncryptedDocumentException, InvalidFormatException, IOException {
		path = null;
		
		JFileChooser chooser = new JFileChooser(Desktop.getDesktop().toString());
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			       null, "xlsx");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
				path = chooser.getSelectedFile();
				System.out.println(path.getAbsolutePath());
			}
		lib = new ScannerLibrary(path);
	}
	
	public static void main(String[] args) {
		new Applicat().launch(args);
	}
	
}
