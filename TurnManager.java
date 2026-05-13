package com.BowlORama;

public class TurnManager {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private EventHandler turnhandler;
    private int tempturn = 0;
    private Main game;
    private GameScreen gameScreen;
  
    public TurnManager(EventHandler turnHandler , Main game , GameScreen gameScreen){
        this.turnhandler = turnHandler;
        this.game = game;
        this.gameScreen = gameScreen;
       player1 = new Player1(gameScreen.player1Name, new BowlingScore(), new Scorecardgraphics(1 , game , gameScreen , gameScreen.player1Name));
       player2 = new Player2(gameScreen.player2Name , new BowlingScore(), new Scorecardgraphics(2 , game , gameScreen , gameScreen.player2Name));
       setturn(player1);
    }

    public void setturn(Player player){
        currentPlayer = player;
        if(currentPlayer instanceof Player1){
            currentPlayer.scoreDisplay.setPlayerLabel(gameScreen.player1Name + " 's Turn");
            player2.scoreDisplay.setPlayerLabel(gameScreen.player2Name);
        }
        else{
            currentPlayer.scoreDisplay.setPlayerLabel(gameScreen.player2Name + " 's Turn");
            player1.scoreDisplay.setPlayerLabel(gameScreen.player1Name);
        }

        tempturn = currentPlayer.getturn();
    }

    public void setscore(){
        int num = turnhandler.getpindowns();
        currentPlayer.setscore(num);
    }

    public void manageturn(float dt){

        if(turnhandler.turnended){
            setscore();
            currentPlayer.update();
            boolean frameChanged = (tempturn != currentPlayer.getturn());

        if (frameChanged) {
            turnhandler.resetpins();
            if (currentPlayer instanceof Player1)
                setturn(player2);
            else
                setturn(player1);
        } 
        else if(currentPlayer.getturn() == 9 && (currentPlayer.score.getscorearray()[9][0].equals("X") || currentPlayer.score.getscorearray()[9][1].equals("X"))){
                turnhandler.reset(dt);
                turnhandler.resetpins();
        }
        else if(currentPlayer.getsubturn() == 2){
            turnhandler.reset(dt);
            turnhandler.resetpins();
        }
        else if (currentPlayer.getsubturn() == 1) {
                turnhandler.reset(dt);
        }
        else if(currentPlayer.getsubturn() >= 3){
                turnhandler.reset(dt);
                turnhandler.resetpins();
                if (currentPlayer instanceof Player1)
                    setturn(player2);
                else
                    setturn(player1);
        }
            turnhandler.turnended = false;
        }
    }

    public void isFinished(){
        if(player1.score.isFinished() && player2.score.isFinished()){
            Highscoremanager.saveScore(
                player1.name,
                player1.score.getTotalScore()
            );

            Highscoremanager.saveScore(
                player2.name,
                player2.score.getTotalScore()
            );
            gameScreen.bgMusic.stop();
            if(player1.score.getTotalScore() > player2.score.getTotalScore()){
                game.setScreen(new PauseMenu(game, gameScreen , gameScreen.player1Name + " WINS!" ,true));
                // gameScreen.dispose();
            }
            else if(player1.score.getTotalScore() < player2.score.getTotalScore()){
                game.setScreen(new PauseMenu(game, gameScreen , gameScreen.player2Name + " WINS!" , true));
                // gameScreen.dispose();
            }
            else{
                game.setScreen(new PauseMenu(game, gameScreen , "DRAW" , true));
                // gameScreen.dispose();
            }
        }
    }
    public void render(float dt){

        player1.render(dt);
        player1.update();
        player2.render(dt);
        player2.update();
        isFinished();
    }

}
