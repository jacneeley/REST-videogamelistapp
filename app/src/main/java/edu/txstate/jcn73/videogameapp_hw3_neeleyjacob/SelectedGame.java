package edu.txstate.jcn73.videogameapp_hw3_neeleyjacob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

public class SelectedGame extends AppCompatActivity {
    int id;
    String name;
    double price;
    int oneStar;
    int twoStar;
    int threeStar;
    int fourStar;
    int fiveStar;
    String url;
    double avgRating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_game);

        TextView gameId = findViewById(R.id.txtID);
        TextView gameName = findViewById(R.id.txtName);
        TextView gamePrice = findViewById(R.id.txtGamePrice);
        TextView starOne = findViewById(R.id.txt1Star);
        TextView starTwo = findViewById(R.id.txt2Star);
        TextView starThree = findViewById(R.id.txt3Star);
        TextView starFour = findViewById(R.id.txt4Star);
        TextView starFive = findViewById(R.id.txt5Star);
        Button info = findViewById(R.id.btnGameInfo);
        Button calcRating = findViewById(R.id.btnCalcAvg);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SelectedGame.this);
        id = pref.getInt("KEY_ID",0);
        name = pref.getString("KEY_NAME","");
        price = pref.getFloat("KEY_PRICE" , 0.00f);
        oneStar = pref.getInt("KEY_R1", 0);
        twoStar = pref.getInt("KEY_R2", 0);
        threeStar = pref.getInt("KEY_R3", 0);
        fourStar = pref.getInt("KEY_R4", 0);
        fiveStar = pref.getInt("KEY_R5", 0);
        url = pref.getString("KEY_URL","");

        //display
        DecimalFormat fmtPrice = new DecimalFormat("###,###.##");
        gameId.setText(String.valueOf("Game ID: "+id));
        gameName.setText(name);
        gamePrice.setText("Price: "+String.valueOf(fmtPrice.format(price)));
        starOne.setText(String.valueOf("1 Stars: "+oneStar));
        starTwo.setText(String.valueOf("2 Stars: "+twoStar));
        starThree.setText(String.valueOf("3 Stars: "+threeStar));
        starFour.setText(String.valueOf("4 Stars: "+fourStar));
        starFive.setText(String.valueOf("5 Stars: "+fiveStar));


        info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
            }
        });

        calcRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //average calculation
            }
        });
    }
}