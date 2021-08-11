package application;


import javafx.scene.paint.Paint;

public class SoloPlayerGamePicker extends GamePicker{

		public Game CreateGame(Paint color1,Paint color2) {
		Game tennis=new SinglePlayerTennis();
		tennis.chooseColors(color1,color2);
		return tennis;
		}



	
}
