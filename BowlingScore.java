package com.BowlORama;

public class BowlingScore {

    // frame scores
    private String[][] scores = new String[10][3];

    //Change tempturn also if req
    private int turn = 0; 
    private int subturn = 0;

    // each frame score
    private int[] TurnTotalScore = new int[10];

    // final cumulative score
    private int TotalScore = 0;

    public BowlingScore() {
        initturnscore();
    }


    private void initturnscore(){
        for(int i = 0 ; i < 10 ; i++ ){
            TurnTotalScore[i] = -1;
        }
    }

    // add score
    public int setscore(int score2) {

        if(turn >= 10)
            return -1;

        if (score2 < 0 || score2 > 10) 
            return -1; // invalid
    
        
        String score = String.valueOf(score2);
        if(score2 == 10)
            score = "X";

        if(subturn == 1){
                scores[turn][subturn] = String.valueOf(score2 - getValue(scores[turn][subturn-1]));
        }
        else
            scores[turn][subturn] = score;

        settotalscore();

        // strike in frames 1 to 9
        if (score.equals("X") && turn < 9 && subturn == 0) {
            subturn = 0;
            turn++;
            return 0;
        }

        // normal movement
        subturn++;

        // frame completed
        if (turn < 9 && subturn >= 2) {
            subturn = 0;
            turn++;
        }


        if (turn == 9) {

            if (subturn == 2) {

                int first = getValue(scores[9][0]);
                int second = getValue(scores[9][1]);

                if (first + second < 10 && first != 10) {
                    turn++;
                    subturn = 0;
                }
            }

            else if (subturn >= 3) {
                turn++;
                subturn = 0;
            }
        }

        return 1;
    }

    public void settotalscore() {


        TotalScore = 0;
        for (int i = 0; i < 10; i++) {

            try{
            if (TurnTotalScore[i] != -1)
                TotalScore += TurnTotalScore[i];
            else if(scores[i][0] != null && !scores[i][0].isEmpty()){
                if(scores[i][1] != null && !scores[i][1].isEmpty())
                    TotalScore += (getValue(scores[i][0]) + getValue(scores[i][1]));
                else{
                    TotalScore += getValue(scores[i][0]);
                }
            }
            }
            catch(Exception e){
            }
        }

        for (int i = 0; i < 10; i++) {

            if (scores[i][0] == null)
                return;

            int tempscore1 = getValue(scores[i][0]);
            int tempscore2 = (scores[i][1] != null) ? getValue(scores[i][1]) : 0;

            if (i == 9) {

                if (scores[9][1] == null)
                    return;

                int frameTotal = tempscore1 + tempscore2;

                if (scores[9][2] != null)
                    frameTotal += getValue(scores[9][2]);

                TurnTotalScore[9] = frameTotal;
                continue;
            }

            int tempscore3 = nextBall(i, 1);
            int tempscore4 = nextBall(i, 2);

            // strike
            if (scores[i][0].equals("X")) {
                if (tempscore3 == -1 || tempscore4 == -1)
                    return;

                TurnTotalScore[i] = 10 + tempscore3 + tempscore4;
            }

            //Spare
            else if (scores[i][1] != null && !scores[i][1].isEmpty()) {
                if (tempscore1 + tempscore2 == 10) { // Spare
                    if (tempscore3 == -1) return;
                    TurnTotalScore[i] = 10 + tempscore3;
                } else { // Open Frame
                    TurnTotalScore[i] = tempscore1 + tempscore2;
                }
            }

            // open frame
            else if (scores[i][1] != null) {
                TurnTotalScore[i] = tempscore1 + tempscore2;
            }
        }


    }

    // get next balls after strike/spare
    private int nextBall(int frame, int number) {

        try{
        int count = 0;

        for (int i = frame + 1; i < 10; i++) {
            for (int j = 0; j < 3; j++) {

                if (scores[i][j] != null  && !scores[i][j].isEmpty()) {
                    count++;

                    if (count == number)
                        return getValue(scores[i][j]);
                }
            }
        }

        return -1;
    }
    catch(NumberFormatException e){
        return -1;
    }
   
    }

    // convert score string to int
    private int getValue(String s) {

       
        if (s == null || s.isEmpty()) {
            return 0;
        }

        if (s.equals("X"))
            return 10;

        try {
        return Integer.parseInt(s);
        } 
        catch (NumberFormatException e) {
        return 0; 
        }
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public int getFrameScore(int frame) {
        return TurnTotalScore[frame];
    }

    public int[] getScoreArray(){
        return TurnTotalScore;
    }
    public String getsubturnscore(int turn){
        if(scores[turn][0] == null) 
            scores[turn][0] = "";
        if(scores[turn][1] == null) 
            scores[turn][1] = "";
        if(scores[turn][2] == null )
            scores[turn][2] = "";

        return scores[turn][0] + " " + scores[turn][1] + " "  + scores[turn][2];
    }

    public String getRoll(int frame, int ball) {
        return scores[frame][ball];
    }

    public int getturn() {
        return turn;
    }

    public int getsubturn() {
        return subturn;
    }

    public boolean isFinished() {
        return turn > 9;
    }
}