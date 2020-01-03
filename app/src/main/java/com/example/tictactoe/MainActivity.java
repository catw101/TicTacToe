package com.example.tictactoe;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundNum, player1Points, player2Points;
    private TextView player1TextView, player2TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player1TextView = findViewById(R.id.player1);
        player2TextView = findViewById(R.id.player2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonId = "button" + i + j;
                int resId = getResources().getIdentifier(buttonId, "id", getPackageName());
                buttons[i][j] = findViewById(resId);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button reset = findViewById(R.id.reset_button);
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }
        if (player1Turn) {
            ((Button) v).setText("X");
        } else {
            ((Button) v).setText("O");
        }
        roundNum++;
        if (win()) {
            if (player1Turn) player1Wins();
            else player2Wins();
        }
        else if (roundNum == 9)
            draw();
        else
            player1Turn = !player1Turn;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(MainActivity.this, "Player 1 has won!", Toast.LENGTH_SHORT).show();
        updatePoints();
        reset();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(MainActivity.this, "Player 2 has won!", Toast.LENGTH_SHORT).show();
        updatePoints();
        reset();
    }

    private void draw() {
        Toast.makeText(MainActivity.this, "Draw!", Toast.LENGTH_SHORT).show();
        reset();
    }

    private void reset() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundNum = 0;
        player1Turn = true;
    }

    private void resetGame() {
        player1Points = 0;
        player2Points = 0;
        updatePoints();
        reset();
    }

    private void updatePoints() {
        player1TextView.setText("Player 1: " + player1Points);
        player2TextView.setText("Player 2: " + player2Points);
    }

    private boolean win() {
        String[][] arr = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                arr[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!arr[i][0].equals("") && (arr[i][0].equals(arr[i][1]) && arr[i][0].equals(arr[i][2]))) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (!arr[0][i].equals("") && (arr[0][i].equals(arr[1][i]) && arr[0][i].equals(arr[2][i]))) {
                return true;
            }
        }

        if (!arr[0][0].equals("") && (arr[0][0].equals(arr[1][1]) && arr[0][0].equals(arr[2][2]))) {
            return true;
        }

        if (!arr[0][2].equals("") && (arr[0][2].equals(arr[1][1]) && arr[0][2].equals(arr[2][0]))) {
            return true;
        }
        return false;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundNum", roundNum);
        outState.putInt("player1Points", player1Points);
        outState.putInt("player2Points", player2Points);
        outState.putBoolean("player1Turn", player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundNum = savedInstanceState.getInt("roundNum");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
        player1Turn = savedInstanceState.getBoolean("player1Turn");
    }
}
