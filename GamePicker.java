package application;

import javafx.scene.paint.Paint;

import javafx.scene.paint.Color;

public abstract class GamePicker {
	
	public Game GetGame(Paint color1,Paint color2) {
		Game myGame = this.CreateGame(color1,color2);
		myGame.startGame();
		return myGame;
		}
		public abstract Game CreateGame(Paint color1,Paint color2);

}
