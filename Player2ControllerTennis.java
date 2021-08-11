package application;

public class Player2ControllerTennis implements PlayerController {

	private MultiPlayerTennis player;
	
	public Player2ControllerTennis(MultiPlayerTennis player) {
		this.player=player;
	}
	
	public void movePlayerLeft() {
		this.player.moveLeftPlayer2();
	}
	
	public void movePlayerRight() {
		this.player.moveRightPlayer2();
	}
	
	public void movePlayerForward() {
		this.player.moveForwardPlayer2();
	}
	
	public void movePlayerBackwards() {
		this.player.moveBackwardplayer2();
	}
	
	public void hitPlayer() {
		this.player.hitPlayer2();
	}
	
	public void prepareHitPlayer() {
		this.player.prepareHitPlayer2();
	}
	
	
	
}
