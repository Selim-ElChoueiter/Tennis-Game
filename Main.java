package application;
	

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class Main extends Application {
	String musicFile = "bin\\application\\TennisSong.mp3";
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);

	String gameMusicFile = "bin\\application\\GameMusic.mp3";
    Media sound2 = new Media(new File(gameMusicFile).toURI().toString());
    MediaPlayer mediaPlayer2 = new MediaPlayer(sound2);
    
 
	Game game;
	
	@Override
	public void start(Stage primaryStage) {
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
	    mediaPlayer.play();

	    primaryStage.getIcons().add(new Image(getClass().getResource("icon.png").toExternalForm()));
		
		primaryStage.setTitle("MiniTennis");
		Singleton singleton=Singleton.getInstance();
		//Create main pane
		VBox mainmenu1=new VBox();
		mainmenu1.setId("root");
		
		//Create pane containing the buttons
		VBox buttons=new VBox();
		
		//set nodes in node pane to center horizontally
		buttons.setAlignment(Pos.BASELINE_CENTER);
		//set nodes inside main pane to center horizontally
		
		//Create buttons to add inside node pane
		Button signup=new Button("Create Account");
		buttons.getChildren().add(signup);
		Button login=new Button("Login");
		buttons.getChildren().add(login);
		
		//add label for Page Title and give it id for CSS
		Label createAcc=new Label("Create Account");
		mainmenu1.getChildren().add(createAcc);
		createAcc.setId("createAccount");

		//we add the node pane inside the main pane
		mainmenu1.getChildren().add(buttons);
		
		Button exit1=new Button("Exit");
		mainmenu1.getChildren().add(exit1);
		
		//we define the scene and link it's CSS file to it
		Scene scene=new Scene(mainmenu1,800,500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		
		
		
		mainmenu1.setOnKeyPressed(e->{
			if(KeyCode.X==e.getCode()) {
				this.mediaPlayer.pause();
				
			}
		});
		mainmenu1.setAlignment(Pos.CENTER);
		
		
		//create scene for main menu page2 after login/sign up
		VBox mainmenu2=new VBox();
		mainmenu2.setId("root2");
		Label welcome=new Label();
		welcome.setId("welcomeText");
		mainmenu2.setAlignment(Pos.CENTER);
		
		//create new VBox inside initial vbox to put the buttons in
		VBox buttons2=new VBox();
		buttons2.setAlignment(Pos.BASELINE_CENTER);
		Button play=new Button("Play");
		Button profile=new Button("Profile");

		Button score=new Button("Statistics");
		Button logout=new Button("Log Out");
		Button exit=new Button("Exit");
		buttons2.getChildren().addAll(play,profile,score,logout,exit);
		
		//add all to initial VBox
		mainmenu2.getChildren().addAll(welcome,buttons2);
		mainmenu2.setOnKeyPressed(e->{
			if(KeyCode.X==e.getCode()) {
				this.mediaPlayer.pause();
				
			}
		});
		Scene page2=new Scene(mainmenu2,800,500);
		page2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		//set spacing between nodes in main pane to one fourth of the scene's height
		mainmenu1.setSpacing(scene.getHeight()/4);
		
		//Create stage for login or sign up
		
		//sign up button is pressed so we display new stage containing form made
		//with FXML for user sign up
		signup.setOnAction(e->{
			try {
				primaryStage.setTitle("MiniTennis-SignUp");
				GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Sample.fxml"));
				
				Scene scene2 = new Scene(root,800,500);
				root.setId("signupForm");
				scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene2);
				primaryStage.show();
				Button next=(Button)root.getChildren().get(12);
				Button back=(Button)root.getChildren().get(13);
				Label status=(Label)root.getChildren().get(14);
				next.setOnAction(exx->{
					if(singleton.getUser()!=null) {
						welcome.setText("Welcome "+singleton.getUser().getName());
						primaryStage.setScene(page2);
					}else {
						status.setTextFill(Color.RED);
						status.setText("Please Complete your signup");
					}
				});
				back.setOnAction(exx->{
					primaryStage.setScene(scene);
				});
				
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		

		//login button is pressed so we display new stage containing form
		login.setOnAction(e->{


			try { 
		    	
		    	  GridPane root =(GridPane) FXMLLoader.load(getClass().getResource("Login.fxml"));
		    	primaryStage.setTitle("MiniTennis-Log In");
		    	Scene scene3= new Scene(root, 800, 500);
		    	root.setId("loginFormApplication");

		    	
		    	HBox loginbuttons=(HBox) root.getChildren().get(5);
		    	Button checkFields=(Button)loginbuttons.getChildren().get(0);
		    	Button back=(Button)loginbuttons.getChildren().get(1);
		    	
		    	checkFields.setOnAction(e2->{
					
					TextField em=(TextField)root.getChildren().get(2);
					PasswordField pass=(PasswordField)root.getChildren().get(4);
					
					//check if user has an account
					if(!em.getText().toString().isEmpty() || !pass.getText().toString().isEmpty()) {
						if(singleton.getUser()!=null) {
							if(singleton.checkUser(em.getText().toString(), pass.getText().toString())) {
								welcome.setText("Welcome "+singleton.getUser().getName());
								primaryStage.setScene(page2);
								primaryStage.show();
							
							}else {
								Label status=(Label)root.getChildren().get(6);
								status.setText("Wrong email or password");
								status.setTextFill(Color.RED);
							
							}
						}else {
							Label status=(Label)root.getChildren().get(6);
							status.setText("Wrong email or password");
							status.setTextFill(Color.RED);
						}
						
					}else {
						Label status=(Label)root.getChildren().get(6);
						status.setText("Please enter a valid email\n or password");
						status.setTextFill(Color.RED);
					}
					

				});
		    	
		    	back.setOnAction(exx->{
		    		primaryStage.setScene(scene);
		    	});

		    	scene3.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		    	primaryStage.setScene(scene3);
		        primaryStage.show(); 
		        
		        } catch(Exception ex){
		        	ex.printStackTrace();
		        }
			
			
		});
		
		
		
		
		
		//we are creating the root for the scene where the user will choose a game to play
		VBox chooseGame=new VBox();
		HBox buttonsGameType=new HBox();
		Label todo=new Label("Choose Mode");
		Button soloGame=new Button("Single Player");
		Button multiGame=new Button("Multiplayer");
		Button back=new Button("Back");
		
		chooseGame.setOnKeyPressed(e->{
			if(KeyCode.X==e.getCode()) {
				this.mediaPlayer.pause();
				
			}
		});
		
		HBox bpane=new HBox();
		VBox characcolors=new VBox();
		Label col=new Label("Character Color");
		Button redCharac=new Button("Red");
		Button blueCharac=new Button("Blue");
		Button greenCharac=new Button("Green");
		Button yellowCharac=new Button("Yellow");
		Button whiteCharac=new Button("White");
		characcolors.getChildren().addAll(col,redCharac,blueCharac,greenCharac,yellowCharac,whiteCharac);
		characcolors.setAlignment(Pos.CENTER);
		BorderPane paneCharacter=new BorderPane();
		Circle player=new Circle();
		Arc arc=new Arc();

		paneCharacter.setCenter(player);
		BorderPane paneRacket=new BorderPane();
		paneRacket.setCenter(arc);
		//paneCharacter.getChildren().addAll(arc,player);

		
	
		VBox racketcolors=new VBox();
		Label colr=new Label("Racket Color");
		Button redracket=new Button("Red");
		Button blueracket=new Button("Blue");
		Button greenracket=new Button("Green");
		Button yellowracket=new Button("Yellow");
		Button whiteracket=new Button("White");
		racketcolors.getChildren().addAll(colr,redracket,blueracket,greenracket,yellowracket,whiteracket);
		col.setId("labelId");
		colr.setId("labelId");
		racketcolors.setAlignment(Pos.CENTER);
		
		
		bpane.setPadding(new Insets(15, 15, 15, 15));
		bpane.getChildren().addAll(characcolors,paneCharacter,racketcolors,paneRacket);
		bpane.setAlignment(Pos.CENTER);
		

		
		redCharac.setOnAction(e->{
			player.setFill(Color.RED);

		});
		blueCharac.setOnAction(e->{
			player.setFill(Color.BLUE);

		});
		greenCharac.setOnAction(e->{
			player.setFill(Color.GREEN);

		});
		yellowCharac.setOnAction(e->{
			player.setFill(Color.YELLOW);

		});
		whiteCharac.setOnAction(e->{
			player.setFill(Color.WHITE);

		});

		redracket.setOnAction(e->{
			arc.setFill(Color.RED);

		});
		blueracket.setOnAction(e->{
			arc.setFill(Color.BLUE);

		});
		greenracket.setOnAction(e->{
			arc.setFill(Color.GREEN);

		});
		yellowracket.setOnAction(e->{
			arc.setFill(Color.YELLOW);

		});
		whiteracket.setOnAction(e->{
			arc.setFill(Color.WHITE);

		});
		
		
		VBox buttonsGame=new VBox();
		buttonsGameType.setAlignment(Pos.BASELINE_CENTER);
		buttonsGame.setAlignment(Pos.BASELINE_CENTER);
		chooseGame.setAlignment(Pos.CENTER);
	
		
		
		buttonsGameType.getChildren().addAll(soloGame,multiGame);
		buttonsGame.getChildren().addAll(buttonsGameType,back);
		chooseGame.getChildren().addAll(todo,bpane,buttonsGame);
		chooseGame.setId("chooseGamePage");
		
		Scene gameChooseScene=new Scene(chooseGame,800,500);
		gameChooseScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

		player.setRadius(40);

		arc.setStartAngle(0);
		arc.setLength(10);
		arc.setRadiusX(80);
		arc.setRadiusY(80);
		arc.setType(ArcType.ROUND);
		arc.setFill(Color.BLUE);
		player.setFill(Color.RED);
		player.setStroke(Color.BLACK);
		
		
		//Stage gameStage=new Stage();
		Pane gamePane=new Pane();
		Scene gameScene=new Scene(gamePane,800,500);
		//gameStage.setScene(gameScene);
		

		gameScene.getStylesheets().add(getClass().getResource("tennis.css").toExternalForm());
		
		

		soloGame.setOnAction(e->{
			mediaPlayer.stop();
			mediaPlayer.seek(Duration.ZERO);
			mediaPlayer2.play();
			mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
			primaryStage.setScene(gameScene);
			gamePane.getChildren().remove(this.game);
			GamePicker gamepick=new SoloPlayerGamePicker();
			gamePane.setId("singleRoot");
			this.game=gamepick.GetGame(player.getFill(),arc.getFill());
			gamePane.getChildren().add(game);
			primaryStage.setTitle("MiniTennis-SinglePlayer");
			primaryStage.show();
			game.setOnKeyPressed(ex->{
				//player movement right
				if(KeyCode.RIGHT==ex.getCode()) {
					game.moveRight();
				}
				//player movement left
				if(KeyCode.LEFT==ex.getCode()) {
					game.moveLeft();
				}
				if(KeyCode.UP==ex.getCode()) {
					game.moveForward();
				}
				if(KeyCode.DOWN==ex.getCode()) {
					game.moveBackwards();
				}
				//player prepares to hit the ball
				if(KeyCode.SPACE==ex.getCode()) {
					game.prepareHit();
				}
				if(KeyCode.ESCAPE==ex.getCode()) {
					mediaPlayer2.stop();
					mediaPlayer2.seek(Duration.ZERO);
					mediaPlayer.play();
					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
					game.stopGame();
					primaryStage.setScene(gameChooseScene);
					primaryStage.show();
				}
				if(KeyCode.X==ex.getCode()) {
					this.mediaPlayer2.pause();
				}
			});
			game.requestFocus();
			
			game.setOnKeyReleased(ex->{
				if(KeyCode.SPACE==ex.getCode()) {
					//player hits the ball
					game.hit();
				}
			});
			
		});
		
		
		multiGame.setOnAction(e->{
			mediaPlayer.stop();
			mediaPlayer.seek(Duration.ZERO);
			mediaPlayer2.play();
			mediaPlayer2.setCycleCount(MediaPlayer.INDEFINITE);
			primaryStage.setScene(gameScene);
			gamePane.getChildren().remove(this.game);
			GamePicker gamepick=new MultiPlayerGamePicker();
			gamePane.setId("multiRoot");
			this.game=gamepick.GetGame(player.getFill(),arc.getFill());
			PlayerController controller=new Player2ControllerTennis((MultiPlayerTennis) this.game);
			gamePane.getChildren().add(game);
			primaryStage.setTitle("MiniTennis-MultiPlayer");
			primaryStage.show();
			
			game.setOnKeyPressed(ex->{
				//player movement right
				if(KeyCode.RIGHT==ex.getCode()) {
					game.moveRight();
				}
				//player movement left
				if(KeyCode.LEFT==ex.getCode()) {
					game.moveLeft();
				}
				if(KeyCode.UP==ex.getCode()) {
					game.moveForward();
				}
				
				if(KeyCode.DOWN==ex.getCode()) {
					game.moveBackwards();
				}
				//player prepares to hit the ball
				if(KeyCode.SPACE==ex.getCode()) {
					game.prepareHit();
				}
				
				if(KeyCode.D==ex.getCode()) {
					controller.movePlayerRight();
				}
				//player movement left
				if(KeyCode.A==ex.getCode()) {
					controller.movePlayerLeft();
				}
				if(KeyCode.W==ex.getCode()) {
					controller.movePlayerForward();
				}
				
				if(KeyCode.S==ex.getCode()) {
					controller.movePlayerBackwards();
				}
				
				//player prepares to hit the ball
				if(KeyCode.ENTER==ex.getCode()) {
					controller.prepareHitPlayer();
				}

				if(KeyCode.ESCAPE==ex.getCode()) {
					mediaPlayer2.stop();
					mediaPlayer2.seek(Duration.ZERO);
					mediaPlayer.play();
					mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
					game.stopGame();
					primaryStage.setScene(gameChooseScene);
					primaryStage.show();
				}
				
				if(KeyCode.X==ex.getCode()) {
					this.mediaPlayer2.pause();
				}
			});
			game.requestFocus();
			
			
			game.setOnKeyReleased(ex->{
				if(KeyCode.SPACE==ex.getCode()) {

					game.hit();
				}
				if(KeyCode.ENTER==ex.getCode()) {
					controller.hitPlayer();
				}
				
				
			});
			
		});
		
		
		
		
		
		gameScene.widthProperty().addListener(e->game.setW(gamePane.getWidth()));
		gameScene.heightProperty().addListener(e->game.setH(gamePane.getHeight()));
		
		
		
		
		
		back.setOnAction(e->{
			//go back to previous scene
			primaryStage.setTitle("MiniTennis");
			primaryStage.setScene(page2);
			primaryStage.show();
		});
		

		exit1.setOnAction(e->{
			primaryStage.close();
		});
		
		
		score.setOnAction(e->{
			try {
			primaryStage.setTitle("MiniTennis-Statistics");
			VBox root = (VBox)FXMLLoader.load(getClass().getResource("Score.fxml"));
			Button butBack=new Button("Back");
			root.getChildren().addAll(butBack);
			Scene scene2 = new Scene(root,800,500);
			root.setId("scoreForm");
			scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene2);
			primaryStage.show();
			
			butBack.setOnAction(exx->{
				primaryStage.setScene(page2);
				primaryStage.show();
			});
			
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		//Main Menu 2 Buttons
		play.setOnAction(e->{
			//we set the scene as game select scene
			primaryStage.setTitle("MiniTennis-GameSelect");
			primaryStage.setScene(gameChooseScene);
			primaryStage.show();
		});
		
		profile.setOnAction(e->{
			try {//we get the FXML page for the profile and display it in a new stage
				primaryStage.setTitle("MiniTennis-Profile");
				GridPane root = (GridPane)FXMLLoader.load(getClass().getResource("Profile.fxml"));
				Button backBtn=new Button("Back");
				root.add(backBtn, 1, 5);
				root.setId("profileForm");
				Scene scene2 = new Scene(root,800,500);
				scene2.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				root.setId("profilePage");
				primaryStage.setScene(scene2);
				primaryStage.show();
				
				backBtn.setOnAction(ex->{
					primaryStage.setScene(page2);
					primaryStage.show();
				});
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		});
		
		logout.setOnAction(e->{
			//we go back to initial scene
			singleton.logout();
			primaryStage.setScene(scene);
			primaryStage.show();
		});
		
		//if user presses exit
		exit.setOnAction(e->{
			primaryStage.close();
		});

	}
	
	
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
