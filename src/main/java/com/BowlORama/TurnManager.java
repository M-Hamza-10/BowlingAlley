package com.BowlORama;

public class TurnManager {

    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private EventHandler turnhandler;
    private int tempturn = 0;
  
    public TurnManager(EventHandler turnHandler){
        this.turnhandler = turnHandler;
       player1 = new Player1("Aslam", new BowlingScore(), new Scorecardgraphics(1));
       player2 = new Player2(new BowlingScore(), new Scorecardgraphics(2));
       setturn(player1);
    }

    public void setturn(Player player){
        currentPlayer = player;
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
            if(player1.score.getTotalScore() > player2.score.getTotalScore()){

            }
        }
    }
    public void render(float dt){

        player1.render(dt);
        player1.update();
        player2.render(dt);
        player2.update();
    }

}
