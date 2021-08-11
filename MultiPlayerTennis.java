package application;

import java.io.File;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;

//Circle(double centerX, double centerY, double radius)
//Arc(double centerX, double centerY, double radiusX, double radiusY, double startAngle, double length)

public class MultiPlayerTennis extends Game{
	
	private double width=800,height=500;
	private Circle player1, player2;
	private Arc racket1, racket2;
	private double radius=Math.min(this.width,this.height)*0.04;
	private double racketRadius=this.radius+Math.min(this.width,this.height)*0.05;
	private int player1Movement=5;
	private int player2Movement=5;
	private Circle ball;
	private Line net;
	private int dx=3,dy=5;
	private double ballradius=Math.min(this.width,this.height)*0.03;
	private double touchedNet;
	private Circle player1Shadow;
	private Circle player2Shadow;
	private Arc racket1Shadow;
	private Arc racket2Shadow;
	private Circle ballShadow;
	private int tmpdX=dx,tmpdY=dy;
	private double distanceNet;
	private Label score1,score2;
	private int point1=0,game1=0,point2=0,game2=0;
	private Timeline animation;
	private Rectangle scoreboard1,scoreboard2;
	private Singleton singleton;
	private boolean started=false;
	private Circle boost;
	private int timeBoost=0;
	private int time=0;
	private boolean hit1=false;
	private boolean hit2=false;
	private Rectangle status;
	private Label statusText;
	StackPane statusBoard;
	private StackPane pane1,pane2;
	private double tmpW=this.width,tmpH=this.height;
	String musicFile = "bin\\application\\TennisSoundEffect.mp3";
    Media sound = new Media(new File(musicFile).toURI().toString());
    MediaPlayer mediaPlayer = new MediaPlayer(sound);
    
    String congratsMusic="bin\\application\\ClappingSoundEffect.mp3";
    Media sound2=new Media(new File(congratsMusic).toURI().toString());
    MediaPlayer mediaWon=new MediaPlayer(sound2);
	
	public MultiPlayerTennis() {
		//we are adding the ball
		this.ball=new Circle(this.width/3,this.height/2,ballradius);
		
		//we are adding the player and racket in the pane
		this.player1=new Circle(this.width/2,this.height*5/6,this.radius);
		this.racket1=new Arc(this.player1.getCenterX(),this.player1.getCenterY(),this.racketRadius,this.racketRadius,0,10);
		this.player2=new Circle(this.width/2,this.height*1/6,this.radius);
		this.racket2=new Arc(this.player2.getCenterX(),this.player2.getCenterY(),this.racketRadius,this.racketRadius,0,10);
		
		this.net=new Line((this.ballradius*3), this.height/2, this.width-(this.ballradius*3), this.height/2);

		this.player1Shadow=new Circle(this.width/2,this.height*5/6,this.radius+2);
		this.racket1Shadow=new Arc(this.racket1.getCenterX(),this.racket1.getCenterY(),this.racketRadius+2,this.racketRadius,-3,16);
		
		this.player2Shadow=new Circle(this.width/2,this.height*1/6,this.radius+2);
		this.racket2Shadow=new Arc(this.racket2.getCenterX(),this.racket2.getCenterY(),this.racketRadius+2,this.racketRadius,-3,16);
		this.ballShadow=new Circle(this.ballradius,this.height/2,ballradius+2);
		
		this.singleton.getInstance().getUser().setPlayedMulti(this.singleton.getInstance().getUser().getPlayedMulti()+1);
		//we are adding a color to the player and racket as well as setting the arc type to round so it
		//shows fully instead of just the border
		this.player1Shadow.setOpacity(0.2);
		this.racket1Shadow.setOpacity(0.2);
		this.racket1Shadow.setType(ArcType.ROUND);
		
		
		this.player2Shadow.setOpacity(0.2);
		this.racket2Shadow.setOpacity(0.2);
		this.racket2Shadow.setType(ArcType.ROUND);
		this.ballShadow.setOpacity(0.2);

		
		this.player1.setStroke(Color.BLACK);
		this.player2.setStroke(Color.BLACK);
		this.racket1.setType(ArcType.ROUND);
		this.ball.setFill(Color.LAWNGREEN);
		this.player2.setFill(Color.RED);
		this.racket2.setFill(Color.BLUE);
		this.racket2.setType(ArcType.ROUND);
		this.net.setFill(Color.WHITE);
		
		this.getChildren().addAll(this.player1Shadow,this.racket1Shadow,this.player2Shadow,this.racket2Shadow,this.ballShadow,this.racket1,this.player1,this.racket2,this.player2,this.net,this.ball);
		this.score1=new Label();
		this.score1.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		this.score1.setText("Games:"+this.game1+"\nPoints:"+this.point1);
		this.score2=new Label();
		this.score2.setText("Games:"+this.game2+"\nPoints:"+this.point2);
		this.score2.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		
		
		this.pane1=new StackPane();
		this.scoreboard1=new Rectangle(0,0,this.width/4,this.height/9);
		this.scoreboard1.setFill(Color.BLACK);
		this.score1.setTextFill(Color.ORANGE);
		pane1.getChildren().addAll(this.scoreboard1,this.score1);
		
		this.pane2=new StackPane();
		this.scoreboard2=new Rectangle(this.width-this.width/4,this.height-this.height/9,this.width/4,this.height/9);
		this.scoreboard2.setFill(Color.BLACK);
		this.score2.setTextFill(Color.ORANGE);
		pane2.getChildren().addAll(this.scoreboard2,this.score2);
		pane2.setLayoutX(0);
		pane2.setLayoutY(0);
		pane1.setLayoutX(this.width-this.scoreboard1.getWidth());
		pane1.setLayoutY(this.height-this.scoreboard1.getHeight());
		
		
		this.statusBoard=new StackPane();
		this.statusText=new Label();
		this.statusText.setText("Game has started");
		this.status=new Rectangle(0,0,this.width/4,this.height/15);
		this.statusText.setTextFill(Color.WHITE);
		statusBoard.getChildren().addAll(this.status,this.statusText);
		this.getChildren().add(statusBoard);
		statusBoard.setLayoutX(0);
		statusBoard.setLayoutY(this.height-this.status.getHeight());
		this.statusText.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		
		
		
		this.getChildren().addAll(pane1,pane2);
		this.animation=new Timeline(new KeyFrame(Duration.millis(100),e->this.ballMovement()));
		this.animation.setCycleCount(Timeline.INDEFINITE);
		
	}
	
	
	
	
	@Override
	public void startGame() {
		this.animation.play();
	}
	
	
	
	
	@Override
	public void ballMovement() {
		
		
		this.time++;
		
		if(this.player1Movement!=5 || this.player2Movement!=5) {
			this.timeBoost++;
		}
		
		if(this.timeBoost%100==0) {
			this.timeBoost=0;
			this.player1Movement=5;
			this.player2Movement=5;
		}
		
		if(time%300==0) {
			if(!this.getChildren().contains(boost)) {
				double posX=Math.random()*this.width;
				double posY=this.scoreboard2.getHeight()+Math.random()*(this.height-this.scoreboard1.getHeight()-this.scoreboard2.getHeight());
				this.boost=new Circle(posX,posY,ballradius*0.6);
				this.boost.setFill(Color.RED);
				this.getChildren().add(this.boost);
			}
		}
		
		

		//if ball is on any of the corners, it bounces off of it
		if(this.ball.getCenterY()-this.ball.getRadius()<0) {
			this.statusText.setText("Player 1 wins point");
			this.resetBallPlayer2();
			this.pointSystem(1);

		}else if(this.ball.getCenterY()+this.ball.getRadius()>this.height) {
			this.statusText.setText("Player 2 wins point");
			this.resetBallPlayer1();
			this.pointSystem(2);
		}
		
		if(this.ball.getCenterX()-this.ball.getRadius()<0) {
			this.dx=-dx;
		}else if(this.ball.getCenterX()+this.ball.getRadius()>this.width) {
			dx=-dx;
		}
		
		this.ballShadow.setCenterX(this.ball.getCenterX()+dx);
		this.ballShadow.setCenterY(this.ball.getCenterY()+dy);
		
		this.ball.setCenterX(this.ball.getCenterX()+dx);
		this.ball.setCenterY(this.ball.getCenterY()+dy);
		
		this.distanceNet=Math.abs(this.ball.getCenterY()-this.height/2);

		if(this.distanceNet<=this.ballradius && this.started==true) {
			if(this.dy==this.tmpdY ){
				this.touchedNet=Math.floor(Math.random()*4);
					if(this.touchedNet==1) {
						if(dy>0) {
							this.statusText.setText("Player 2 has hit the net");
							this.resetBallPlayer2();
							this.pointSystem(1);
						}else {
							this.statusText.setText("Player 1 has hit the net");
							this.resetBallPlayer1();
							this.pointSystem(2);
						}
					}
			}
		}
	}
	
	
	
	//Player 1 codes
	
	@Override
	public void moveRight() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player1)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player1Movement+=10;
			}
			
		}
		
		if(this.player1.getCenterX()+this.racketRadius<=this.width) {
			if(this.racket1.getStartAngle()!=-30 && this.racket1.getStartAngle()!=210) {
				this.setAngles(this.racket1, this.racket1Shadow, 0, -3);
			}else if(this.racket1.getStartAngle()==210) {
				this.setAngles(this.racket1, this.racket1Shadow, -30, -33);
			}
			
			
			this.player1.setCenterX(this.player1.getCenterX()+player1Movement);
			this.racket1.setCenterX(this.racket1.getCenterX()+player1Movement);
			
			this.player1Shadow.setCenterX(this.player1.getCenterX());
			this.racket1Shadow.setCenterX(this.racket1.getCenterX());

		}}
	
	
		
	@Override
	public void moveLeft() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player1)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player1Movement+=10;
			}
			
		}
		
		if(this.player1.getCenterX()-this.racketRadius>=0) {
			if(this.racket1.getStartAngle()!=210 && this.racket1.getStartAngle()!=-30) {
				this.setAngles(this.racket1, this.racket1Shadow, 180, 177);
			}else if(this.racket1.getStartAngle()==-30){
				this.setAngles(this.racket1, this.racket1Shadow, 210,207);
			}
			this.player1.setCenterX(this.player1.getCenterX()-player1Movement);
			this.racket1.setCenterX(this.racket1.getCenterX()-player1Movement);
			
			this.player1Shadow.setCenterX(this.player1.getCenterX());
			this.racket1Shadow.setCenterX(this.racket1.getCenterX());
			
		}
	}
	
	
	
	@Override
	public void moveForward() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player1)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player1Movement+=10;
			}
			
		}
		
		if(this.player1.getCenterY()-this.radius>=this.height/2) {
			this.player1.setCenterY(this.player1.getCenterY()-this.player1Movement);
			this.player1Shadow.setCenterY(this.player1Shadow.getCenterY()-this.player1Movement);
			this.racket1.setCenterY(this.racket1.getCenterY()-this.player1Movement);
			this.racket1Shadow.setCenterY(this.racket1Shadow.getCenterY()-this.player1Movement);
		}
	}
	
	
	@Override
	public void moveBackwards() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player1)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player1Movement+=10;
			}
			
		}
		
		if(this.player1.getCenterY()+this.radius<=this.height) {
			this.player1.setCenterY(this.player1.getCenterY()+this.player1Movement);
			this.player1Shadow.setCenterY(this.player1Shadow.getCenterY()+this.player1Movement);
			this.racket1.setCenterY(this.racket1.getCenterY()+this.player1Movement);
			this.racket1Shadow.setCenterY(this.racket1Shadow.getCenterY()+this.player1Movement);
		}
	}

	@Override
	public void prepareHit() {
		if(this.racket1.getStartAngle()==0) {
			this.setAngles(this.racket1, this.racket1Shadow, -30, -33);
		}else if(this.racket1.getStartAngle()==180){
			this.setAngles(this.racket1, this.racket1Shadow, 210, 207);
		}
	}

	
	@Override
	public void hit() {
		if(this.racket1.getStartAngle()==-30) {
			this.setAngles(this.racket1, this.racket1Shadow, 0, -3);
		}else if(this.racket1.getStartAngle()==210) {
			this.setAngles(this.racket1, this.racket1Shadow, 180, 177);
		}
		
		double x,y,distanceX,distanceY;
		
		//we are finding the distance between the ball and the racket
		if(this.racket1.getStartAngle()<90) {
			x=Math.cos(this.racket1.getStartAngle())*this.racketRadius;
			y=Math.sin(this.racket1.getStartAngle())*this.racketRadius;
			distanceX=this.ball.getCenterX()-(this.player1.getCenterX()+x);
			distanceY=this.ball.getCenterY()-(this.player1.getCenterY()+y);
		}else{
			x=Math.cos(180-this.racket1.getStartAngle())*this.racketRadius;
			y=Math.sin(180-this.racket1.getStartAngle())*this.racketRadius;
			distanceX=this.ball.getCenterX()-(this.player1.getCenterX()-x);
			distanceY=this.ball.getCenterY()-(this.player1.getCenterY()-y);
		}
		double distance=Math.sqrt((Math.pow(distanceX,2)+Math.pow(distanceY, 2)));
		
		double rand=Math.random()*6;
		int power=(int)rand;
		

		//we are checking if the racket is on the left or right side
		if(distance-this.ballradius<=0 && this.hit1==false) {
			if(this.ball.getCenterX()>this.player1.getCenterX()) {
				this.dx=this.tmpdX;
				this.dy=this.tmpdY;
				this.singleton.getInstance().getUser().setForehandMulti(this.singleton.getInstance().getUser().getForehandMulti()+1);
				this.started=true;
				double hitType=Math.floor(Math.random()*2);
				if(hitType==0) {
					this.dy=dy+power;
					this.dy=-dy;
					this.dx=dx+power;
					this.dx=-dx;
				}else {
					this.dy=dy+power;
					this.dy=-dy;
					this.dx=dx+power;
					this.dx=dx;
				}
				this.hit1=true;
				this.hit2=false;
				this.mediaPlayer.play();
				this.mediaPlayer.seek(Duration.ZERO);
			}else if(this.ball.getCenterX()<this.player1.getCenterX()){
				this.dx=this.tmpdX;
				this.dy=this.tmpdY;
				this.singleton.getInstance().getUser().setBackhandMulti(this.singleton.getInstance().getUser().getBackhandMulti()+1);
				this.started=true;
				double hitType=Math.floor(Math.random()*2);
				if(hitType==0) {
					this.dy=dy+power;
					this.dy=-dy;
					this.dx=dx+power;
					this.dx=-dx;
				}else {
					this.dy=dy+power;
					this.dy=-dy;
					this.dx=dx+power;
					this.dx=dx;
				}
				this.hit1=true;
				this.hit2=false;
				this.mediaPlayer.play();
				this.mediaPlayer.seek(Duration.ZERO);

			}
		}	
	}
	
	
	//player 2 codes
	
	public void prepareHitPlayer2() {
		if(this.racket2.getStartAngle()==0) {
			this.setAngles(this.racket2, this.racket2Shadow, 30, 27);
		}else if(this.racket2.getStartAngle()==180){
			this.setAngles(this.racket2, this.racket2Shadow, 150, 147);
		}
	}
	
	public void hitPlayer2() {
		if(this.racket2.getStartAngle()==30) {
			this.setAngles(this.racket2, this.racket2Shadow, 0,-3);
		}else if(this.racket2.getStartAngle()==150) {
			this.setAngles(this.racket2, this.racket2Shadow, 180, 177);
		}
		
		double x,y,distanceX,distanceY;
		
		//we are finding the distance between the ball and the racket
		if(this.racket2.getStartAngle()<90) {
			x=Math.cos(this.racket2.getStartAngle())*this.racketRadius;
			y=Math.sin(this.racket2.getStartAngle())*this.racketRadius;
			distanceX=this.ball.getCenterX()-(this.player2.getCenterX()+x);
			distanceY=this.ball.getCenterY()-(this.player2.getCenterY()+y);
		}else{
			x=Math.cos(180-this.racket2.getStartAngle())*this.racketRadius;
			y=Math.sin(180-this.racket2.getStartAngle())*this.racketRadius;
			distanceX=this.ball.getCenterX()-(this.player2.getCenterX()-x);
			distanceY=this.ball.getCenterY()-(this.player2.getCenterY()-y);
		}
		double distance=Math.sqrt((Math.pow(distanceX,2)+Math.pow(distanceY, 2)));
		
		double rand=Math.random()*6;
		int power=(int)rand;
		
		//we are checking if the racket is on the left or right side
		if(distance-this.ballradius<=0 && this.hit2==false) {
			if(this.ball.getCenterX()>this.player2.getCenterX()) {
				this.dy=this.tmpdY;
				this.dx=this.tmpdX;
				this.started=true;
				double hitType=Math.floor(Math.random()*2);
				if(hitType==0) {
					this.dy=dy+power;
					this.dx=dx+power;
				}else {
					this.dy=dy+power;
					this.dx=dx+power;
					this.dx=-dx;
				}
				this.hit1=false;
				this.hit2=true;
				this.mediaPlayer.play();
				this.mediaPlayer.seek(Duration.ZERO);
			}else if(this.ball.getCenterX()<this.player2.getCenterX()){
				this.dy=this.tmpdY;
				this.dx=this.tmpdX;
				this.started=true;
				double hitType=Math.floor(Math.random()*2);
				if(hitType==0) {
					this.dy=dy+power;
					this.dx=dx+power;

				}else {
					this.dy=dy+power;
					this.dx=dx+power;
					this.dx=-dx;
				}
				this.hit1=false;
				this.hit2=true;
				this.mediaPlayer.play();
				this.mediaPlayer.seek(Duration.ZERO);
			}
		}
	}
	
	
	
	
	
	
	
	public void moveRightPlayer2() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player2)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player2Movement+=10;
			}
			
		}
		
		if(this.player2.getCenterX()+this.racketRadius<=this.width) {
			if(this.racket2.getStartAngle()!=-30 && this.racket2.getStartAngle()!=210) {
				this.setAngles(this.racket2, this.racket2Shadow, 0, -3);
			}else if(this.racket2.getStartAngle()==210) {
				this.setAngles(this.racket2, this.racket2Shadow, -30, -33);
			}
			
			
			this.player2.setCenterX(this.player2.getCenterX()+player2Movement);
			this.racket2.setCenterX(this.racket2.getCenterX()+player2Movement);
			
			this.player2Shadow.setCenterX(this.player2.getCenterX());
			this.racket2Shadow.setCenterX(this.racket2.getCenterX());

		}
				
	}
	
	public void moveLeftPlayer2() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player2)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player2Movement+=10;
			}
			
		}
		
		if(this.player2.getCenterX()-this.racketRadius>=0) {
			
			
			if(this.racket2.getStartAngle()!=210 && this.racket2.getStartAngle()!=-30) {
				this.setAngles(this.racket2, this.racket2Shadow, 180, 177);
			}else if(this.racket2.getStartAngle()==-30){
				this.setAngles(this.racket2, this.racket2Shadow, 210,207);
			}
			this.player2.setCenterX(this.player2.getCenterX()-player2Movement);
			this.racket2.setCenterX(this.racket2.getCenterX()-player2Movement);
			
			this.player2Shadow.setCenterX(this.player2.getCenterX());
			this.racket2Shadow.setCenterX(this.racket2.getCenterX());
		}
	}
	
	
	public void moveBackwardplayer2() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player2)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player2Movement+=10;
			}
			
		}
		
		if(this.player2.getCenterY()-this.radius>=0) {
			this.player2.setCenterY(this.player2.getCenterY()-this.player2Movement);
			this.player2Shadow.setCenterY(this.player2Shadow.getCenterY()-this.player2Movement);
			this.racket2.setCenterY(this.racket2.getCenterY()-this.player2Movement);
			this.racket2Shadow.setCenterY(this.racket2Shadow.getCenterY()-this.player2Movement);
		}
	}
	
	public void moveForwardPlayer2() {
		
		if(this.getChildren().contains(this.boost)) {
			if(this.checkBoost(this.player2)) {
				this.status.setX(0);
				this.status.setY(this.height-this.status.getHeight());
				this.status.setWidth(this.width/4);
				this.statusText.setText("Boost has been picked up");
				this.player2Movement+=10;
			}
			
		}
		if(this.player2.getCenterY()+this.radius<=this.height/2) {
			this.player2.setCenterY(this.player2.getCenterY()+this.player2Movement);
			this.player2Shadow.setCenterY(this.player2Shadow.getCenterY()+this.player2Movement);
			this.racket2.setCenterY(this.racket2.getCenterY()+this.player2Movement);
			this.racket2Shadow.setCenterY(this.racket2Shadow.getCenterY()+this.player2Movement);
		}
	}
	
	
	
	@Override
	public void updateValues() {
		//we are updating the values of width and height

		this.radius=Math.min(this.width,this.height)*0.04;
		this.racketRadius=this.radius+Math.min(this.width,this.height)*0.05;
		this.ballradius=Math.min(this.width,this.height)*0.03;
		
		double oldX1=this.player1.getCenterX()/this.tmpW;
		double oldY1=this.player1.getCenterY()/this.tmpH;
		double oldX2=this.player2.getCenterX()/this.tmpW;
		double oldY2=this.player2.getCenterY()/this.tmpH;
		
		this.player1.setCenterX(oldX1*this.width);
		this.player1.setCenterY(oldY1*this.height);
		this.racket1.setCenterX(this.player1.getCenterX());
		this.racket1.setCenterY(this.player1.getCenterY());
		this.player1Shadow.setCenterX(this.player1.getCenterX());
		this.player1Shadow.setCenterY(this.player1.getCenterY());
		this.racket1Shadow.setCenterX(this.player1.getCenterX());
		this.racket1Shadow.setCenterY(this.player1.getCenterY());
		
		
		this.ball.setCenterX((this.ball.getCenterX()/this.tmpW)*this.width);
		this.ball.setCenterY((this.ball.getCenterY()/this.tmpH)*this.height);
		this.ballShadow.setCenterX(this.ball.getCenterX());
		this.ballShadow.setCenterY(this.ball.getCenterY());
		
		this.statusBoard.setLayoutX(0);
		this.status.setWidth(this.width/4);
		this.status.setHeight(this.height/15);
		this.statusBoard.setLayoutY(this.height-this.status.getY());
		this.statusText.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		
		this.score1.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		this.score2.setFont(Font.font("arial",Math.min(this.width,this.height)/33.33));
		
		this.scoreboard1.setWidth(this.width/4);
		this.scoreboard1.setHeight(this.height/9);
		
		this.scoreboard2.setWidth(this.width/4);
		this.scoreboard2.setHeight(this.height/9);
		
		this.pane1.setLayoutX(this.width-this.scoreboard1.getWidth());
		this.pane1.setLayoutY(this.height-this.scoreboard1.getHeight());
		
		this.pane2.setLayoutX(0);
		this.pane2.setLayoutY(0);
		
		if(this.getChildren().contains(this.boost)) {
			this.boost.setCenterX((this.boost.getCenterX()/this.tmpW)*this.width);
			this.boost.setCenterY((this.boost.getCenterY()/this.tmpH)*this.height);
			this.boost.setRadius(this.ballradius*0.6);
		}
		
		
		
		this.player2.setCenterX(oldX2*this.width);
		this.player2.setCenterY(oldY2*this.height);
		this.racket2.setCenterX(this.player2.getCenterX());
		this.racket2.setCenterY(this.player2.getCenterY());
		this.player2Shadow.setCenterX(this.player2.getCenterX());
		this.player2Shadow.setCenterY(this.player2.getCenterY());
		this.racket2Shadow.setCenterX(this.player2.getCenterX());
		this.racket2Shadow.setCenterY(this.player2.getCenterY());
		

		this.player1.setRadius(this.radius);	

		this.player1Shadow.setRadius(this.radius+2);
		

		this.player1Shadow.setCenterY(this.player1.getCenterY());
		this.player1Shadow.setCenterX(this.player1.getCenterX());
		

		this.statusBoard.setLayoutY(this.height-this.status.getHeight());
		
		
		this.racket1.setCenterY(this.player1.getCenterY());
		this.racket1.setCenterX(this.player1.getCenterX());
		this.racket1.setRadiusX(this.racketRadius);
		this.racket1.setRadiusY(this.racketRadius);
		
		
		this.racket1Shadow.setCenterX(this.player1.getCenterX());
		this.racket1Shadow.setCenterY(this.player1.getCenterY());
		this.racket1Shadow.setRadiusX(this.racketRadius+2);
		this.racket1Shadow.setRadiusY(this.racketRadius+2);
		
		
		this.net.setStartY(this.height/2);
		this.net.setEndY(this.height/2);
		this.net.setStartX(this.ballradius*3);
		this.net.setEndX(this.width-(this.ballradius*3));
		
		this.ball.setRadius(ballradius);
		this.ballShadow.setRadius(ballradius+2);
		

		this.player2.setRadius(this.radius);
		this.player2Shadow.setCenterX(this.player2.getCenterX());
		this.player2Shadow.setCenterY(this.player2.getCenterY());
		this.player2Shadow.setRadius(this.radius+2);
		
		this.racket2.setCenterX(this.player2.getCenterX());
		this.racket2.setCenterY(this.player2.getCenterY());
		this.racket2.setRadiusX(this.racketRadius);
		this.racket2.setRadiusY(this.racketRadius);
		this.racket2Shadow.setCenterX(this.player2.getCenterX());
		this.racket2Shadow.setCenterY(this.player2.getCenterY());
		this.racket2Shadow.setRadiusX(this.racketRadius+2);
		this.racket2Shadow.setRadiusY(this.racketRadius+2);
		
	}
		
	@Override
	public void setW(double width) {
		this.tmpW=this.width;
		this.tmpH=this.height;
		this.width=width;
		updateValues();
	}
	@Override
	public void setH(double height) {
		this.tmpH=this.height;
		this.tmpW=this.width;
		this.height=height;
		updateValues();
	}

	
	
	
	
	private void resetBallPlayer1() {
		this.ball.setCenterX(this.player2.getCenterX());
		this.ball.setCenterY(this.player2.getCenterY());
		this.ballShadow.setCenterX(this.player2.getCenterX());
		this.ballShadow.setCenterY(this.player2.getCenterY());
		this.dx=this.tmpdX;
		this.dy=this.tmpdY;
		this.started=false;
		this.hit1=false;
		this.hit2=false;
	}
	
	private void resetBallPlayer2() {
		this.ball.setCenterX(this.player1.getCenterX());
		this.ball.setCenterY(this.player1.getCenterY());
		this.ballShadow.setCenterX(this.player1.getCenterX());
		this.ballShadow.setCenterY(this.player1.getCenterY());
		this.dx=-this.tmpdX;
		this.dy=-this.tmpdY;
		this.started=false;
		this.hit1=false;
		this.hit2=false;
	}

	private void setAngles(Arc racket,Arc racket2,int angle1,int angle2) {
		racket.setStartAngle(angle1);
		racket2.setStartAngle(angle2);
	}
	
	private void pointSystem(int player) {
		if(player==1) {
			this.point1=this.point1+15;
			if(this.point1==60) {
				this.game1++;
				this.point1=0;
				this.point2=0;
			}
			this.score1.setText("Games:"+this.game1+"\nPoints:"+this.point1);
			this.score2.setText("Games:"+this.game2+"\nPoints:"+this.point2);
		}else {
			this.point2=this.point2+15;
			if(this.point2==60) {
				this.game2++;
				this.point2=0;
				this.point1=0;
			}
			this.score2.setText("Games:"+this.game2+"\nPoints:"+this.point2);
			this.score1.setText("Games:"+this.game1+"\nPoints:"+this.point1);
		}
		
		if(this.game1==6) {
			this.stopGame();
			this.singleton.getInstance().getUser().setWinsMulti(this.singleton.getInstance().getUser().getWinsMulti()+1);
			Stage winner=new Stage();
			BorderPane winnerPane=new BorderPane();
			Label winnerText=new Label("Winner: Player1");
			winnerText.setTextFill(Color.ORANGE);
			winnerPane.setTop(winnerText);
			Circle tmpPlayer=new Circle(250,250,125);
			tmpPlayer.setFill(this.player1.getFill());
			winnerPane.setCenter(tmpPlayer);
			winnerPane.setId("WinnerScene");
			winnerPane.setAlignment(winnerText, Pos.CENTER);
			Scene winnerScene=new Scene(winnerPane,500,500);
			winnerScene.getStylesheets().add(getClass().getResource("tennis.css").toExternalForm());
			winner.setScene(winnerScene);
			winner.show();
			mediaWon.play();
			mediaWon.seek(Duration.ZERO);
			this.statusText.setText("Player 1 has won");
			winnerPane.setAlignment(this.statusText, Pos.CENTER);
			winnerText.setFont(Font.font("Berlin Sans FB Demi",Math.min(this.width,this.height)/10));
			winnerText.setTextFill(this.player1.getFill());
		}else if(this.game2==6) {
			this.stopGame();
			this.singleton.getInstance().getUser().setLossesMulti(this.singleton.getInstance().getUser().getLossesMulti()+1);
			Stage winner=new Stage();
			BorderPane winnerPane=new BorderPane();
			Label winnerText=new Label("Winner: Player2");
			winnerText.setTextFill(Color.ORANGE);
			winnerPane.setTop(winnerText);
			Circle tmpPlayer=new Circle(250,250,125);
			tmpPlayer.setFill(this.player2.getFill());
			winnerPane.setCenter(tmpPlayer);
			winnerPane.setId("WinnerScene");

			winnerPane.setAlignment(winnerText, Pos.CENTER);
			Scene winnerScene=new Scene(winnerPane,500,500);
			winnerScene.getStylesheets().add(getClass().getResource("tennis.css").toExternalForm());
			winner.setScene(winnerScene);
			winnerText.setFont(Font.font("Berlin Sans FB Demi",Math.min(this.width,this.height)/10));
			winner.show();
			mediaWon.play();
			mediaWon.seek(Duration.ZERO);
			this.statusText.setText("Player 2 has won");
			winnerText.setTextFill(this.player2.getFill());
		}
		
	}
	
	
	public void chooseColors(Paint playerColor,Paint racketColor) {
		this.player1.setFill(playerColor);
		this.racket1.setFill(racketColor);
	}
	
	private boolean checkBoost(Circle player) {
		double distX=Math.pow((player.getCenterX()-this.boost.getCenterX()), 2);
		double distY=Math.pow((player.getCenterY()-this.boost.getCenterY()), 2);
		double distanceFinal=Math.sqrt(distX+distY);

		if(distanceFinal<=(player.getRadius()+this.boost.getRadius())) {
			this.getChildren().remove(this.boost);
			return true;
		}
		return false;
	}
	@Override
	public void stopGame() {
		this.animation.stop();
		
	}
	
	
	
	
}