package application;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
public abstract class Game extends Pane{

			

			
			public abstract void startGame();
			
			protected abstract void ballMovement();
			
			public abstract void moveRight();
			
			public abstract void moveLeft();
			
			protected abstract void updateValues();
			
			public abstract void prepareHit();
			
			public abstract void hit();
			
			public abstract void setW(double width);
			
			public abstract void setH(double height);

			public abstract void moveForward();
			
			public abstract void moveBackwards();
		
			public abstract void chooseColors(Paint playerColor,Paint racketColor);

			public abstract void stopGame();
	
	
}
