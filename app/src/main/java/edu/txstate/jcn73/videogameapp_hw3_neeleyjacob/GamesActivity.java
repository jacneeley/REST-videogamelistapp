package edu.txstate.jcn73.videogameapp_hw3_neeleyjacob;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class GamesActivity extends ListActivity {
    public String API_URL = "https://androidstudiodemo-93279-default-rtdb.firebaseio.com/games.json";
    ArrayList<Games> gamesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Response.Listener<java.lang.String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    JSONArray array = new JSONArray(response);
                    gamesList = new ArrayList<Games>();
                    for (int i = 0; i < array.length(); i++){
                        Games game = new Games();
                        JSONObject obj = array.getJSONObject(i);
                        game.setId(obj.getInt("ID"));
                        game.setName(obj.getString("Name"));
                        game.setPrice(obj.getDouble("Price"));
                        game.setRating1(obj.getInt("Rating1"));
                        game.setRating2(obj.getInt("Rating2"));
                        game.setRating3(obj.getInt("Rating3"));
                        game.setRating4(obj.getInt("Rating4"));
                        game.setRating5(obj.getInt("Rating5"));
                        game.setUrl(obj.getString("url"));
                        gamesList.add(game);
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                //display
                try{
                setListAdapter(new ArrayAdapter<Games>(
                        GamesActivity.this,
                        R.layout.game_item,
                        R.id.txtGameBasic,
                        gamesList));
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        };
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(GamesActivity.this, error.getMessage(),Toast.LENGTH_LONG).show();
            }
        };
        //request
        StringRequest strRequest = new StringRequest(Request.Method.GET,API_URL,listener,errorListener);
        //send
        RequestQueue queue = Volley.newRequestQueue(GamesActivity.this);
        queue.add(strRequest);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Games selectedGame = gamesList.get(position);

        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(GamesActivity.this);
        SharedPreferences.Editor editor = pref.edit();

        editor.putInt("KEY_POSITION",position);
        editor.putInt("KEY_ID",selectedGame.getId());
        editor.putString("KEY_NAME", selectedGame.getName());
        editor.putFloat("KEY_PRICE", (float) selectedGame.getPrice());
        editor.putInt("KEY_R1", selectedGame.getRating1());
        editor.putInt("KEY_R2", selectedGame.getRating2());
        editor.putInt("KEY_R3", selectedGame.getRating3());
        editor.putInt("KEY_R4", selectedGame.getRating4());
        editor.putInt("KEY_R5", selectedGame.getRating5());
        editor.putString("KEY_URL",selectedGame.getUrl());
        editor.commit();

        Intent next = new Intent(GamesActivity.this, SelectedGame.class);
        startActivity(next);
    }
}