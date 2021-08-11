package application;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class ScoreController {

	@FXML private Text highscore;
	@FXML private Text mostmissed;
	@FXML private Text forehand;
	@FXML private Text backhand;
	@FXML private Text forehandMulti;
	@FXML private Text backhandMulti;
	@FXML private Text winsMulti;
	@FXML private Text lossesMulti;
	@FXML private Text playedMulti;

	@FXML
	protected void initialize() {
		
		User user=Singleton.getInstance().getUser();
		
		highscore.setText("High Score:"+user.getHighestmissedSolo());
		mostmissed.setText("Most Missed Balls:"+user.getHighestmissedSolo());
		forehand.setText("Number of Forehand hits:"+user.getForehandSolo());
		backhand.setText("Number of Backhand hits:"+user.getBackhandSolo());
		forehandMulti.setText("Number of forehand hits:"+user.getForehandMulti());
		backhandMulti.setText("Number of Backhand hits:"+user.getBackhandMulti());
		playedMulti.setText("Number of games played:"+user.getPlayedMulti());
		winsMulti.setText("Number of wins:"+user.getWinsMulti());
		lossesMulti.setText("Number of losses:"+user.getLossesMulti());

		highscore.setFill(Color.ORANGE);
		mostmissed.setFill(Color.ORANGE);
		forehand.setFill(Color.ORANGE);
		backhand.setFill(Color.ORANGE);
		forehandMulti.setFill(Color.ORANGE);
		backhandMulti.setFill(Color.ORANGE);
		winsMulti.setFill(Color.ORANGE);
		lossesMulti.setFill(Color.ORANGE);
		playedMulti.setFill(Color.ORANGE);
		
	}
}
