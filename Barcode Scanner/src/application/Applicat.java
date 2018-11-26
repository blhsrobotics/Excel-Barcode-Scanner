package application;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.bind.JAXBException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import scanning.ScannerLibrary;
import xmlfiler.Day;
import xmlfiler.Identifiers;
import xmlfiler.Student;
import xmlfiler.XMLFiler;
import xmlfiler.XmlDay;

/*
 * Copyright � 2010 by Chase E. Arline
All rights reserved. This program or any portion thereof
may not be reproduced or used in any manner whatsoever
without the express written permission of the publisher.
 */

public class Applicat extends Application {
	static FadeTransition transit = new FadeTransition();
	Group fadeGroup = new Group();
	GridPane mainGrid = new GridPane();
	GridPane listGrid = new GridPane();
	static ScannerLibrary lib;
	static File path;
	boolean hasLoggedStudents = false;
	static XmlDay xmlDay;
	static Day today;
	static XMLFiler dayFiler;
	static XMLFiler libFiler;
	static Font basic;
	static ObservableList<Student> data;
	int windowHeight = 650;
	int windowWidth = 240;
	TableView<Student> table;
	@Override
	public void init() {
		  try {
			startUp();  
			basic = Font.font("Tahoma",FontWeight.NORMAL,18);
		  } catch (EncryptedDocumentException | InvalidFormatException | IOException | JAXBException e) {
			e.printStackTrace();
		}
		
		  transit.setToValue(0);
		  transit.setCycleCount(Timeline.INDEFINITE);
		  transit.setDuration(new Duration(1000));
	      transit.setAutoReverse(true);
	      
	}
	
	@Override 
	public void start(Stage primaryStage) {
		VBox platform  = new VBox();
		MenuBar menus = new MenuBar();
		Menu tools = new Menu("Tools");
		MenuItem closeAll = new MenuItem("Close");
		closeAll.onActionProperty().set((ActionEvent e)->
			{lib.signOutEveryone(today);
			table.refresh();
			}
		)
		;
		tools.getItems().add(closeAll);
		menus.getMenus().add(tools);
		primaryStage.setTitle("Club Sign-in");
		mainGrid.setAlignment(Pos.TOP_LEFT);
		mainGrid.setHgap(10);
		mainGrid.setVgap(10);
		mainGrid.setPadding(new Insets(25,25,25,25));
		listGrid.setAlignment(Pos.CENTER);
		listGrid.setHgap(0);
		listGrid.setVgap(0);
		listGrid.setPadding(new Insets(0,0,0,0));
		Stage list = new Stage();
		windowHeight = (25*today.getStudents().size())+40;
		Scene listScene = new Scene(listGrid,windowWidth,windowHeight);
		list.setResizable(false);
		TableColumn<Student, String> nameCol = new TableColumn("Name");
		nameCol.setCellValueFactory(cellData -> cellData.getValue().getNameProp());
		TableColumn<Student, String> status = new TableColumn("Signed In");
		status.setCellValueFactory(cellData -> cellData.getValue().getLoginProp());
		nameCol.setMinWidth(120);
		status.setMinWidth(120);
		table = new TableView<Student>(data);
		table.getColumns().addAll(nameCol,status);
		table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		table.setPrefHeight(windowHeight);
		table.setPrefWidth(windowWidth);
		final VBox box = new VBox();
		box.setSpacing(5);
		box.setPadding(new Insets(0,0,0,0));
		box.getChildren().add(table);
		
		listGrid.add(box, 0, 0);
		list.setScene(listScene);
		list.show();
		platform.getChildren().add(menus);
		platform.getChildren().add(mainGrid);
		Scene scene = new Scene(platform,400,175);
		primaryStage.setScene(scene);
		scene.getStylesheets().add(Applicat.class.getResource
				("cascadingSheet.css").toExternalForm());
		
		listScene.getStylesheets().add(Applicat.class.getResource
				("tableStyle.css").toExternalForm());
		
		Text sceneTitle = new Text("Student Login");
		sceneTitle.setFont(Font.font("Tahoma",FontWeight.NORMAL,32));
		
		sceneTitle.setFill(Color.WHITE);
		
		Label userName = new Label("ID Number: ");
	
		userName.setTextFill(Color.WHITE);
		userName.setFont(Font.font("Tahoma",FontWeight.NORMAL,18));
		
		TextField userTextField = new TextField();
		
		Button loginButton = new Button("Check In/Out");
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
		
		mainGrid.add(userName, 0,2, 2,1);
		mainGrid.add(userTextField, 2,2, 2,1);
		
		mainGrid.add(actionText, 3, 5,2,1);
		
		mainGrid.add(signupBox, 4,0, 1,1);
		mainGrid.add(loginBox,4,2,1,1);
		
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
		    	if(!lib.hasCurrentDay())
		    		lib.addCurrentDay();
		    	actionText.setFont(basic);
		    	FadeTransition fade =new FadeTransition(new Duration(1300));
		    	fade.setNode(actionText);
		    	fade.setToValue(0);
		    	fade.setFromValue(1);
		    	if(userTextField.getText().equals("close")) {
		    		lib.signOutEveryone(today);
		    		table.refresh();
		    	}
		    	try{
		    		lib.signInOut(Double.parseDouble(userTextField.getText()), today);
		    		dayFiler.write(today);
		    		table.refresh();
		    		userTextField.clear();
		    		actionText.setText("Logged in...");
			    	fade.play();
		    	hasLoggedStudents = true;
		    	}
		    	catch(NumberFormatException | NullPointerException | JAXBException g) {
		    		actionText.setText("Error: Incorrect Input");
		    		fade.play();
		    	}
		    	
		    }
		});
		
		signupButton.setOnAction(new EventHandler<ActionEvent>() {
			 
		    @Override
		    public void handle(ActionEvent e) {
		      Scene addScene;
		      GridPane secondGrid = new GridPane();
		      secondGrid.setAlignment(Pos.CENTER);
			  secondGrid.setHgap(10);
			  secondGrid.setVgap(10);
			  secondGrid.setPadding(new Insets(25,25,25,25));
			  addScene = new Scene(secondGrid,350,275);
		      addScene.getStylesheets().add(Applicat.class.getResource
					("cascadingSheet.css").toExternalForm());
		    
		      TextField userText = new TextField();
		      userText.setFont(basic);
		      
		      TextField numberBox = new TextField();
		      numberBox.setFont(basic);
		      
		      Button continueButton = new Button("Sign up");
		      HBox continueBox = new HBox(10);
		      continueBox.setAlignment(Pos.BOTTOM_RIGHT);
		      continueBox.getChildren().add(continueButton);
		      
		      Label userLabel = new Label("Name: ");
		      userLabel.setFont(basic);
		      userLabel.setTextFill(Color.WHITE);
		      
		      Label numLabel =  new Label("Scan card: ");
		      numLabel.setFont(basic);
		      numLabel.setTextFill(Color.WHITE);
		      
		      Button cancelButton = new Button("Cancel");
		      HBox cancelBox = new HBox(10);
		      cancelBox.setAlignment(Pos.BOTTOM_LEFT);
		      cancelBox.getChildren().add(cancelButton);
		      
		      secondGrid.add(userLabel, 0, 0,2,1);
		      secondGrid.add(userText, 3, 0,2,1);
		      secondGrid.add(numLabel, 0, 1,2,1);
		      secondGrid.add(numberBox, 3, 1,2,1);
		      secondGrid.add(continueBox, 3, 2,2,1);
		      secondGrid.add(cancelBox, 0, 2,2,1);
		      primaryStage.setScene(addScene);
		      continueButton.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent s) {
				    	Text pop = new Text();
				    	FadeTransition fader = new FadeTransition(new Duration(1300));
				    	secondGrid.add(pop, 3, 3,2,1);
				    	pop.setFont(basic);
				    	pop.setFill(Color.WHITE);
				    	fader.setNode(pop);
				    	fader.setToValue(0);
				    	fader.setFromValue(1);
				    	try {
				    	System.out.println("About to add stud");
				    		xmlDay.students().getStudents().add(
				    				new Identifiers(Double.parseDouble(numberBox.getText()),userText.getText()));
				    		xmlDay.populate();
				    		lib.populate(today.getStudents());
				    		System.out.println("added stud");
				    		libFiler.write(xmlDay.students());
				    		windowHeight = windowHeight+25;
				    		list.setHeight(windowHeight);
				    		table.setPrefHeight(windowHeight);
				    		data.add(today.getStudents().get(today.getStudents().size()-1));
				    		System.out.println("populated");
				    		pop.setText("Student added...");
				    	fader.play();	
				    	list.sizeToScene();
				    	}
				    catch(NumberFormatException | NullPointerException | JAXBException h) {
				    	pop.setText("Error: Incorrect Info");
				    	fader.play();
				    }
				    
				    fader.setOnFinished(new EventHandler<ActionEvent>() {
				    	
				        @Override
				        public void handle(ActionEvent event) {
				            primaryStage.setScene(scene);
				        }
				    });
				    
				    
				    
				    }});
		      
		      cancelButton.setOnAction(new EventHandler<ActionEvent>() {
				    @Override
				    public void handle(ActionEvent l) {
				    	primaryStage.setScene(scene);
				    }
					});

		      
		    }
		});
		
		
		primaryStage.show();
		
	}
	
	public static void startUp() throws EncryptedDocumentException, InvalidFormatException, IOException, JAXBException {
		path = null;
		File[] pathing = XmlDay.fileChooser();
		xmlDay = new XmlDay(pathing);
		today = xmlDay.day();
		dayFiler = xmlDay.dayFiler();
		libFiler = xmlDay.libFiler();
		lib = new ScannerLibrary(pathing[3]);
		lib.onlyKeepPrimarySheet();
		lib.populate(today.getStudents());
		data = FXCollections.observableArrayList(today.getStudents());
	}
	
	@Override
	public void stop() throws IOException, JAXBException {
		if(lib.hasCurrentDay())
		lib.closeBook(today);
		else
			lib.closeBook();
		libFiler.write(xmlDay.students());
		dayFiler.write(today);
		System.out.println("has current day: "+lib.hasCurrentDay());
		
	}
	
	public static void main(String[] args) {
		new Applicat().launch(args);
	}
	
}
