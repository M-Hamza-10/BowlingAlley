package com.BowlORama;

public class BowlingScore {

    // frame scores
    private String[][] scores = new String[10][3];

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
        
        String score = String.valueOf(score2);
        if(score2 == 10)
            score = "X";

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

        // 10th frame max 3 balls
        if (turn == 9 && subturn >= 3) {
            subturn = 2;
        }

        return 1;
    }

    public void settotalscore() {

        for (int i = 0; i < 10; i++) {

            if (scores[i][0] == null)
                return;

            int tempscore1 = getValue(scores[i][0]);
            int tempscore2 = (scores[i][1] != null) ? getValue(scores[i][1]) : 0;

            int tempscore3 = nextBall(i, 1);
            int tempscore4 = nextBall(i, 2);

            // strike
            if (scores[i][0].equals("X")) {
                if (tempscore3 == -1 || tempscore4 == -1)
                    return;

                TurnTotalScore[i] = 10 + tempscore3 + tempscore4;
            }

            // spare
            else if (scores[i][1] != null && tempscore1 + tempscore2 == 10) {
                if (tempscore3 == -1)
                    return;

                TurnTotalScore[i] = 10 + tempscore3;
            }

            // open frame
            else if (scores[i][1] != null) {
                TurnTotalScore[i] = tempscore1 + tempscore2;
            }
        }

        //TotalScore = 0;

        for (int i = 0; i < 10; i++) {
            TotalScore += TurnTotalScore[i];
        }
    }

    // get next balls after strike/spare
    private int nextBall(int frame, int number) {

        int count = 0;

        for (int i = frame + 1; i < 10; i++) {
            for (int j = 0; j < 3; j++) {

                if (scores[i][j] != null) {
                    count++;

                    if (count == number)
                        return getValue(scores[i][j]);
                }
            }
        }

        return -1;
    }

    // convert score string to int
    private int getValue(String s) {

        if (s.equals("X"))
            return 10;

        return Integer.parseInt(s);
    }

    public int getTotalScore() {
        return TotalScore;
    }

    public int getFrameScore(int frame) {
        return TurnTotalScore[frame];
    }

    public String getsubturnscore(int turn){
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
        return (turn == 9 && subturn >= 2 && scores[9][2] != null);
    }
}