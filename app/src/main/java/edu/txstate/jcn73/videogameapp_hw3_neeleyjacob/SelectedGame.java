package edu.txstate.jcn73.videogameapp_hw3_neeleyjacob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

public class SelectedGame extends AppCompatActivity {
    int id;
    String name;
    double price;
    int position;
    int oneStar;
    int twoStar;
    int threeStar;
    int fourStar;
    int fiveStar;
    String url;
    double avgRating;
    String api_url;
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
        TextView averageRating = findViewById(R.id.txtAvgRating);

        Button info = findViewById(R.id.btnGameInfo);
        Button calcRating = findViewById(R.id.btnCalcAvg);
        Button updatePrice = findViewById(R.id.btnUpdatePrice);

        EditText newPrice = findViewById(R.id.txtUpdatePrice);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(SelectedGame.this);
        position = pref.getInt("KEY_POSITION",0);
        id = pref.getInt("KEY_ID",0);
        name = pref.getString("KEY_NAME","");
        price = pref.getFloat("KEY_PRICE" , 0.00f);
        oneStar = pref.getInt("KEY_R1", 0);
        twoStar = pref.getInt("KEY_R2", 0);
        threeStar = pref.getInt("KEY_R3", 0);
        fourStar = pref.getInt("KEY_R4", 0);
        fiveStar = pref.getInt("KEY_R5", 0);
        url = pref.getString("KEY_URL","");
        api_url = "https://androidstudiodemo-93279-default-rtdb.firebaseio.com/games/" + position + ".json";
        DecimalFormat fmt = new DecimalFormat("###,###.##");

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
                avgRating = (Double.valueOf(oneStar *1 + twoStar*2+threeStar*3+fourStar*4+fiveStar*5))/(
                        Double.valueOf(oneStar + twoStar + threeStar+ fourStar + fiveStar));
                averageRating.setText("Average User Score: "+String.valueOf(fmt.format(avgRating)));


            }
        });
        updatePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //build JSONObject and listener for put request
                JSONObject jsonBody = new JSONObject();
                double updatedPrice;
                try {
                    updatedPrice = Double.parseDouble(newPrice.getText().toString());
                }
                catch (Exception ex) {
                    Toast.makeText(SelectedGame.this, "Input Error", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    jsonBody.put("ID",id);
                    jsonBody.put("Name",name);
                    jsonBody.put("Price",updatedPrice);
                    jsonBody.put("Rating1",oneStar);
                    jsonBody.put("Rating2",twoStar);
                    jsonBody.put("Rating3",threeStar);
                    jsonBody.put("Rating4",fourStar);
                    jsonBody.put("Rating5",fiveStar);
                    jsonBody.put("url",url);
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
                String requestBody = jsonBody.toString();
                Response.Listener<String> listener = new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(SelectedGame.this,"Data Updated...", Toast.LENGTH_LONG).show();
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SelectedGame.this,"Update Failed.",Toast.LENGTH_LONG).show();
                    }
                };
                //request
                StringRequest request = new StringRequest(Request.Method.PUT,api_url,listener,errorListener){
                    @Override
                    public String getBodyContentType(){return "application/json; charset=utf-9";}

                    @Override
                    public byte[] getBody() throws AuthFailureError{
                        try {
                            return requestBody == null ? null: requestBody.getBytes("utf-8");
                        }
                        catch(UnsupportedEncodingException uee){
                            uee.printStackTrace();
                            return null;
                        }
                    }
                };
                //send request using Queue
                RequestQueue queue = Volley.newRequestQueue(SelectedGame.this);
                queue.add(request);
            }
        });

        //display
        gameId.setText(String.valueOf("Game ID: "+id));
        gameName.setText(name);
        gamePrice.setText("Price: "+String.valueOf(fmt.format(price)));
        starOne.setText(String.valueOf("1 Stars: "+oneStar));
        starTwo.setText(String.valueOf("2 Stars: "+twoStar));
        starThree.setText(String.valueOf("3 Stars: "+threeStar));
        starFour.setText(String.valueOf("4 Stars: "+fourStar));
        starFive.setText(String.valueOf("5 Stars: "+fiveStar));
    }

}