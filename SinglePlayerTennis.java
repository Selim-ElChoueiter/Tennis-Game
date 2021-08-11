package application;

import java.io.File;
import java.net.URISyntaxException;
import java.util.ArrayList;

import javafx.scene.paint.Paint;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class SinglePlayerTennis extends Game  {

		private double width=800,height=500;
		private Circle player;
		private Arc racket;
		private double radius=Math.min(this.width,this.height)*0.04;
		private double racketRadius=this.radius+Math.min(this.width,this.height)*0.05;
		private int playerMovement=5;
		private Circle ball;
		private int dx=3,dy=5;
		private double ballradius=Math.min(this.width,this.height)*0.03;
		private Label result;
		private int misses=0;
		private int time=0;
		private Circle playerShadow;
		private Arc racketShadow;
		private Circle ballShadow;
		private int tmpdX=dx,tmpdY=dy;
		private Timeline animation;
		private int score=0;
		private Rectangle scoreboard;
		private Singleton singleton;
		private boolean hit=false;
		private double tmpX=width,tmpY=height;
		private StackPane sb;
		String musicFile = "bin\\application\\TennisSoundEffect.mp3";
	    Media sound = new Media(new File(musicFile).toURI().toString());
	    MediaPlayer mediaPlayer = new MediaPlayer(sound);
		
		
		public SinglePlayerTennis() {
			
			//we are adding the ball
			this.ball=new Circle(this.width/2,this.height/6,ballradius);
			
			//we are adding the player and racket in the pane
			this.player=new Circle(this.width/2,this.height*5/6,this.radius);
			this.racket=new Arc(this.player.getCenterX(),this.player.getCenterY(),this.racketRadius,this.racketRadius,0,10);
			
			
			this.playerShadow=new Circle(this.width/2,this.height*5/6,this.radius+2);
			this.racketShadow=new Arc(this.racket.getCenterX(),this.racket.getCenterY(),this.racketRadius+2,this.racketRadius,-3,16);
			this.ballShadow=new Circle(this.width/2,this.height/6,ballradius+2);
			

			
			//we are adding a color to the player and racket as well as setting the arc type to round so it
			//shows fully instead of just the border
			this.playerShadow.setOpacity(0.2);
			this.racketShadow.setOpacity(0.2);
			this.racketShadow.setType(ArcType.ROUND);
			this.ballShadow.setOpacity(0.2);
			
			this.racket.setType(ArcType.ROUND);
			this.ball.setFill(Color.LAWNGREEN);
			
			this.getChildren().addAll(this.playerShadow,this.racketShadow,this.ballShadow,this.racket,this.player,this.ball);

			

			this.racket.setStroke(Color.BLACK);
			
			this.sb=new StackPane();
			this.scoreboard=new Rectangle(0,0,this.width/4,this.height/10);
			//we are adding the score board
			this.result=new Label();
			this.result.setFont(Font.font("Rockwell Extra Bold",Math.min(this.width,this.height)/33.33));
			this.result.setText("Missed balls:"+this.misses+"\nScore:"+this.score+"\nHighScore:"+this.singleton.getInstance().getUser().getHighscoreSolo());
			this.scoreboard.setFill(Color.BLACK);

			this.result.setTextFill(Color.ORANGE);
			sb.getChildren().addAll(this.scoreboard,this.result);
			this.getChildren().add(sb);
			
			this.sb.setLayoutX(0);
			this.sb.setLayoutY(0);

			
			animation=new Timeline(new KeyFrame(Duration.millis(100),e->this.ballMovement()));
			animation.setCycleCount(Timeline.INDEFINITE);
			
		}
		
		@Override
		public void startGame() {
			animation.play();
		}
		
		@Override
		public void ballMovement() {
			
			this.time++;
			//if ball is on any of the corners, it bounces off of it
			if(this.ball.getCenterY()-this.ball.getRadius()<0) {
				this.dy=-dy;
				this.hit=false;
			}else if(this.ball.getCenterY()+this.ball.getRadius()>this.height) {
				this.ball.setCenterX(this.width/3);
				this.ball.setCenterY(this.height/6);
				this.ballShadow.setCenterX(this.width/3);
				this.ballShadow.setCenterY(this.height/6);
				this.dx=this.tmpdX;
				this.dy=this.tmpdY;
				this.misses++;
				this.hit=false;
				int hm=this.singleton.getInstance().getUser().getHighestmissedSolo();
				if(this.misses>hm) {
					this.singleton.getInstance().getUser().setHighestmissedSolo(this.misses);
				}
				this.score=0;
				this.result.setText("Missed balls:"+this.misses+"\nScore:"+this.score+"\nHighScore:"+this.singleton.getInstance().getUser().getHighscoreSolo());
			}
			if(this.ball.getCenterX()-this.ball.getRadius()<0) {
				this.dx=-dx;
				this.hit=false;
			}else if(this.ball.getCenterX()+this.ball.getRadius()>this.width) {
				this.dx=-dx;
				this.hit=false;
			}
			
			this.ballShadow.setCenterX(this.ball.getCenterX()+dx);
			this.ballShadow.setCenterY(this.ball.getCenterY()+dy);
			
			this.ball.setCenterX(this.ball.getCenterX()+dx);
			this.ball.setCenterY(this.ball.getCenterY()+dy);
			
			

			if(this.time%300==0) {
				this.dx++;
				this.dy++;
				this.tmpdX=this.dx;
				this.tmpdY=this.dy;
			}
		}
		
		
		
		
		
		
		@Override
		public void moveRight() {
			
			if(this.player.getCenterX()+this.racketRadius<=this.width) {
				if(this.racket.getStartAngle()!=-30 && this.racket.getStartAngle()!=210) {
					this.racket.setStartAngle(0);
					this.racketShadow.setStartAngle(-3);
				}else if(this.racket.getStartAngle()==210) {
					this.racketShadow.setStartAngle(-33);
					this.racket.setStartAngle(-30);
				}
				
				
				this.player.setCenterX(this.player.getCenterX()+playerMovement);
				this.racket.setCenterX(this.racket.getCenterX()+playerMovement);
				
				this.playerShadow.setCenterX(this.player.getCenterX());
				this.racketShadow.setCenterX(this.racket.getCenterX());

			}
		}
		
		@Override
		public void moveLeft() {
			
			if(this.player.getCenterX()-this.racketRadius>=0) {
				
				
				if(this.racket.getStartAngle()!=210 && this.racket.getStartAngle()!=-30) {
					this.racketShadow.setStartAngle(177);
					this.racket.setStartAngle(180);
				}else if(this.racket.getStartAngle()==-30){
					this.racketShadow.setStartAngle(207);
					this.racket.setStartAngle(210);
				}
				this.player.setCenterX(this.player.getCenterX()-playerMovement);
				this.racket.setCenterX(this.racket.getCenterX()-playerMovement);
				
				this.playerShadow.setCenterX(this.player.getCenterX());
				this.racketShadow.setCenterX(this.racket.getCenterX());
				
			}
		}
		
		
		@Override
		public void moveForward() {
			if(this.player.getCenterY()-this.radius>=0) {
				this.player.setCenterY(this.player.getCenterY()-this.playerMovement);
				this.playerShadow.setCenterY(this.playerShadow.getCenterY()-this.playerMovement);
				this.racket.setCenterY(this.racket.getCenterY()-this.playerMovement);
				this.racketShadow.setCenterY(this.racketShadow.getCenterY()-this.playerMovement);
			}
		}

		@Override
		public void moveBackwards() {

			if(this.player.getCenterY()+this.radius<=this.height) {
				this.player.setCenterY(this.player.getCenterY()+this.playerMovement);
				this.playerShadow.setCenterY(this.playerShadow.getCenterY()+this.playerMovement);
				this.racket.setCenterY(this.racket.getCenterY()+this.playerMovement);
				this.racketShadow.setCenterY(this.racketShadow.getCenterY()+this.playerMovement);
			}
		}
		
		
		
		
		
		
		
		@Override
		public void prepareHit() {
			if(this.racket.getStartAngle()==0) {
				this.racket.setStartAngle(-30);
				this.racketShadow.setStartAngle(-33);
			}else if(this.racket.getStartAngle()==180){
				this.racketShadow.setStartAngle(207);
				this.racket.setStartAngle(210);
			}
		}
		
		
		
		
		@Override
		public void hit() {
			if(this.racket.getStartAngle()==-30) {
				this.racket.setStartAngle(0);
				this.racketShadow.setStartAngle(-3);
			}else if(this.racket.getStartAngle()==210) {
				this.racket.setStartAngle(180);
				this.racketShadow.setStartAngle(177);
			}
			
			double x,y,distanceX,distanceY;
			
			//we are finding the distance between the ball and the racket
			if(this.racket.getStartAngle()<90) {
				x=Math.cos(this.racket.getStartAngle())*this.racketRadius;
				y=Math.sin(this.racket.getStartAngle())*this.racketRadius;
				distanceX=this.ball.getCenterX()-(this.player.getCenterX()+x);
				distanceY=this.ball.getCenterY()-(this.player.getCenterY()+y);
			}else{
				x=Math.cos(180-this.racket.getStartAngle())*this.racketRadius;
				y=Math.sin(180-this.racket.getStartAngle())*this.racketRadius;
				distanceX=this.ball.getCenterX()-(this.player.getCenterX()-x);
				distanceY=this.ball.getCenterY()-(this.player.getCenterY()-y);
			}
			double distance=Math.sqrt((Math.pow(distanceX,2)+Math.pow(distanceY, 2)));
			
			double rand=Math.random()*5;
			int power=(int)rand;

			//we are checking if the racket is on the left or right side
			if(distance-this.ballradius<=0 && this.hit==false) {
				if(this.ball.getCenterX()>this.player.getCenterX()) {
					this.dx=this.tmpdX;
					this.dy=this.tmpdY;
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
					this.score++;
					this.hit=true;
					int hs=this.singleton.getInstance().getUser().getHighscoreSolo();
					if(this.score>hs) {
						this.singleton.getInstance().getUser().setHighscoreSolo(this.score);
					}
					this.result.setText("Missed balls:"+this.misses+"\nScore:"+this.score+"\nHighScore:"+this.singleton.getInstance().getUser().getHighscoreSolo());
					this.singleton.getInstance().getUser().setForehandSolo(this.singleton.getInstance().getUser().getForehandSolo()+1);
					this.mediaPlayer.play();
					this.mediaPlayer.seek(Duration.ZERO);
				}else if(this.ball.getCenterX()<this.player.getCenterX()){
					this.dx=this.tmpdX;
					this.dy=this.tmpdY;
					double hitType=Math.floor(Math.random()*2);
					if(hitType==0) {
						this.dy=dy+power;
						this.dy=-dy;
						this.dx=dx+power;
						this.dx=-dx;
					}else{
						this.dy=dy+power;
						this.dy=-dy;
						this.dx=dx+power;
						this.dx=dx;
					}
					this.score++;
					this.hit=true;
					int hs=this.singleton.getInstance().getUser().getHighscoreSolo();
					if(this.score>hs) {
						this.singleton.getInstance().getUser().setHighscoreSolo(this.score);
					}
					this.result.setText("Missed balls:"+this.misses+"\nScore:"+this.score+"\nHighScore:"+this.singleton.getInstance().getUser().getHighscoreSolo());
					this.singleton.getInstance().getUser().setBackhandSolo(this.singleton.getInstance().getUser().getBackhandSolo()+1);
					this.mediaPlayer.play();
					this.mediaPlayer.seek(Duration.ZERO);
				}
			}	
			
			
		}
		
		
		
		
		
		@Override
		public void updateValues() {
			//we are updating the center's center as well as radius
			//we are updating the arc's center and radius
			this.radius=Math.min(this.width,this.height)*0.04;
			this.racketRadius=this.radius+Math.min(this.width,this.height)*0.05;
			this.ballradius=Math.min(this.width,this.height)*0.03;
			

			
			this.result.setFont(Font.font("Rockwell Extra Bold",Math.min(this.width,this.height)/33.33));
			this.scoreboard.setX(0);
			this.scoreboard.setY(0);
			this.scoreboard.setWidth(this.width/4);
			this.scoreboard.setHeight(this.height/10);
			
			this.sb.setLayoutX(0);
			this.sb.setLayoutY(0);
			
			double oldX=this.player.getCenterX()/this.tmpX;
			double oldY=this.player.getCenterY()/this.tmpY;
			
			
			this.player.setCenterX(oldX*this.width);
			this.player.setCenterY(oldY*this.height);
			this.player.setRadius(this.radius);
			
			
			this.racket.setCenterX(this.player.getCenterX());
			this.racket.setCenterY(this.player.getCenterY());
			this.racket.setRadiusX(this.racketRadius);
			this.racket.setRadiusY(this.racketRadius);
			
			this.ball.setCenterX((this.ball.getCenterX()/this.tmpX)*this.width);
			this.ball.setCenterY((this.ball.getCenterY()/this.tmpY)*this.height);
			this.ball.setRadius(ballradius);
					
			this.racketShadow.setCenterX(this.player.getCenterX());
			this.racketShadow.setCenterY(this.player.getCenterY());
			this.racketShadow.setRadiusX(this.racketRadius+2);
			this.racketShadow.setRadiusY(this.racketRadius+2);
			
			this.playerShadow.setCenterX(this.player.getCenterX());
			this.playerShadow.setCenterY(this.player.getCenterY());
			this.playerShadow.setRadius(this.radius+2);
			
			this.ballShadow.setCenterX(this.ball.getCenterX());
			this.ballShadow.setCenterY(this.ball.getCenterY());
			this.ballShadow.setRadius(ballradius+2);
			
		}
		
		
		
		
		@Override
		public void setW(double width) {
			this.tmpX=this.width;
			this.tmpY=this.height;
			this.width=width;
			updateValues();
		}
		@Override
		public void setH(double height) {
			this.tmpY=this.height;
			this.tmpX=this.width;
			this.height=height;
			updateValues();
		}

		
		
		public void chooseColors(Paint playerColor,Paint racketColor) {
			this.player.setFill(playerColor);
			this.racket.setFill(racketColor);
		}


		@Override
		public void stopGame() {
			this.animation.stop();
		}
	}

	

