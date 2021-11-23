package com.example.rockpaperscissors;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnPlay;
    Button btnScissors;
    Button btnRock;
    Button btnPaper;
    TextView textViewResult;
    ImageView imageViewMe;
    ImageView imageViewComputer;
    TextView textViewCurScore;
    TextView textViewHighScore;
    String me = "";
    int curScore = 0;
    int highScore = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnScissors = (Button) findViewById(R.id.btnScissors);
        btnRock = (Button) findViewById(R.id.btnRock);
        btnPaper = (Button) findViewById(R.id.btnPaper);
        textViewResult = (TextView) findViewById(R.id.textViewResult);
        imageViewMe = (ImageView) findViewById(R.id.imageViewMe);
        imageViewComputer = (ImageView) findViewById(R.id.imageViewComputer);
        textViewCurScore = (TextView) findViewById(R.id.textViewCurScore);
        textViewHighScore = (TextView) findViewById(R.id.textViewHighScore);
        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                me = "scissors";
                imageViewMe.setImageResource(R.drawable.left_scissors);
                btnScissors.setEnabled(false);
                btnRock.setEnabled(true);
                btnPaper.setEnabled(true);
            }
        });
        btnRock.setOnClickListener(v -> {
            me = "rock";
            setMeImage(me);
            btnScissors.setEnabled(true);
            btnRock.setEnabled(false);
            btnPaper.setEnabled(true);
        });
        btnPaper.setOnClickListener(v -> {
            me = "paper";
            imageViewMe.setImageResource(R.drawable.left_paper);
            btnScissors.setEnabled(true);
            btnRock.setEnabled(true);
            btnPaper.setEnabled(false);
        });

        btnPlay.setOnClickListener(v -> {
            btnPlay.setEnabled(false);
            setSystemImage("scissors");
            setMeImage("scissors");
            showResult("scissors!");

            final Handler handler = new Handler();
            handler.postDelayed(() -> {
                setSystemImage("rock");
                setMeImage("rock");
                showResult("rock!");
            }, 2000);

            handler.postDelayed(() -> {
                setSystemImage("paper");
                setMeImage("paper");
                showResult("paper!");
            }, 3000);

            handler.postDelayed(() -> {
                setMeImage(me);
                playGame();
                btnPlay.setEnabled(true);
            }, 4000);
        });
    }

    public void playGame() {
        String system = randomComputer();
        setSystemImage(system);
        String result = whoWon(me, system);
        showToast(me + " vs " + system);
        showResult(result);
        handleScore(result);
    }

    public void setMeImage(String player) {
        if (player.equals("scissors")) {
            imageViewMe.setImageResource(R.drawable.left_scissors);
        } else if (player.equals("rock")) {
            imageViewMe.setImageResource(R.drawable.left_rock);
        } else if (player.equals("paper")) {
            imageViewMe.setImageResource(R.drawable.left_paper);
        }
    }

    public void setSystemImage(String system) {
        switch (system) {
            case "scissors":
                imageViewComputer.setImageResource(R.drawable.right_scissors);
                break;
            case "rock":
                imageViewComputer.setImageResource(R.drawable.right_rock);
                break;
            case "paper":
                imageViewComputer.setImageResource(R.drawable.right_paper);
                break;
        }
    }

    public String randomComputer() {
        String computer = "";
        Random rand = new Random();
        int random = rand.nextInt(3);
        if (random == 0) {
            computer = "scissors";
        } else if (random == 1) {
            computer = "rock";
        } else if (random == 2) {
            computer = "paper";
        } else {
            computer = "Wrong";
        }
        return computer;
    }

    public String whoWon(String me, String system) {
        String result = "";

        switch (me) {
            case "rock":
                if (system.equals("rock")) {
                    result = "Draw!";
                } else if (system.equals("paper")) {
                    result = "You Lose!";
                } else if (system.equals("scissors")) {
                    result = "You Win!";
                } else {
                    result = "";
                }
                break;
            case "paper":
                if (system.equals("rock")) {
                    result = "You Win!";
                } else if (system.equals("paper")) {
                    result = "Draw!";
                } else if (system.equals("scissors")) {
                    result = "You Lose!";
                } else {
                    result = " ";
                }
                break;
            case "scissors":
                switch (system) {
                    case "rock":
                        result = "You Lose!";
                        break;
                    case "paper":
                        result = "You Win!";
                        break;
                    case "scissors":
                        result = "Draw!";
                        break;
                    default:
                        result = " ";
                        break;
                }
                break;
            default:
                result = "Select your move!";
                break;
        }

        return result;
    }

    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    public void showResult(String result) {
        textViewResult.setText(result);
    }

    public void handleScore(String result) {
        if (result.equals("Win!")) {
            curScore = curScore + 1;
        } else if (result.equals("Lose!")) {
            curScore = 0;
        } else if (result.equals("Draw!")) {
        } else {
        }
        setCurScore(curScore);
        checkHighScore(curScore);

    }
    @SuppressLint("SetTextI18n")
    public void setCurScore(int score) {
        textViewCurScore.setText("" + score);
    }

    @SuppressLint("SetTextI18n")
    public void checkHighScore(int score) {
        if (score > highScore) {
            highScore = score;
            textViewHighScore.setText("" + score);
        }
    }

}