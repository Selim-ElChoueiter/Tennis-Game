package application;

import javafx.scene.paint.Paint;



public class MultiPlayerGamePicker extends GamePicker{

	@Override
	public Game CreateGame(Paint color1,Paint color2) {
	Game tennis=new MultiPlayerTennis();
	tennis.chooseColors(color1,color2);
	return tennis;
	}
	
}
