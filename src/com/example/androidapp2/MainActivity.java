package com.example.androidapp2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;

import android.R.string;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
//import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements OnClickListener, OnItemClickListener {
	private static final int MY_ACTIVITY_CODE = 1;
	private static final String SHARED_PREFS_NAME = null;
	private ArrayList<Level> levels;
	private ListView levelListView;
	private ArrayAdapter<String> adapter;
	
	private int plateau[][];
	private int currentLevel;

	private ArrayList<String> levelsTexts;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("SOKOBAN");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }  
        LinearLayout mainLayout= new LinearLayout(this);
      //  Tex
        levels= new ArrayList<Level>();
        setLevels();
        levelListView= (ListView) findViewById(R.id.levels);
    //    stateListView= (ListView) findViewById(R.id.state);
        currentLevel=0;
      // levelListView= new ListView(this);
       levelsTexts= getLevelsText();
       // levelsTexts= new ArrayList<String>();
        if(levelsTexts.isEmpty()){
        for (int i = 0; i < levels.size(); i++) {
        //	Level l= levels.get(i);
		//	if (l.completed) 
		//		levelsTexts.add(String.format("%s %40s", l.getText(), "COMPLETED"));			
		//	else
			    levelsTexts.add(String.format("%s %40s", "level "+ (i+1), "not completed"));
			//levelsTexts.add(l.getText()+ "\t \t \t\t \t not completed yet");
		}}
		adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, levelsTexts);
		levelListView.setAdapter(adapter);	
	//	stateListView.setAdapter(adapterState);
     //  RadioButton rd= new RadioButton(this);
     //   rd.findViewById(R.id.level1);
     //   rd.setOnClickListener(this);
   //     onClick(new View(this));
      //  creerUneCarte();*/
      //  levelListView.setEnabled(false);
     //   levelListView.getItemIdAtPosition(0);
		//adapter.se;
        levelListView.setOnItemClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onStop() {
    	saveLevelsText();
    	super.onStop();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
    void setLevels()
	{
    		
		try {
			/*File f=new File("niveaux.sok");
			FileReader	fr=new FileReader(f);*/
			InputStreamReader isr= new InputStreamReader(getAssets().open("niveaux.sok"));
			BufferedReader	br=new BufferedReader(isr);
			try {
				String	line = br.readLine();	
				int k=1;
				while(line!=null)
				{		
					int n= line.length();	
					int[] t = new int[n];					
					for (int i = 0; i < n; i++) 
						t[i]= Character.getNumericValue( line.charAt(i));
					Level level= new Level(t, "Level "+ k, false);					
					levels.add(level);					
					line=br.readLine();	
					k++;
					//System.out.println("LEVEL "+ k);
				}
			isr.close();
			br.close();
			} catch (IOException e) {
				
				System.out.println("erreur "+e.getMessage());			
			}
		} catch (FileNotFoundException e) {
			System.out.println("ERREUR---- "+e.getMessage());
		} catch (IOException e) {
			System.out.println("ERREUR--getAssets-- "+e.getMessage());
		}		 
		
	}
	
    

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Intent intent= new Intent(this, GameActivity.class);
		startActivity(intent);
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
		// TODO Auto-generated method stub
		Level level= levels.get(position);
		Intent intent= new Intent(this, GameActivity.class);
		intent.putExtra("levelArray", level.array);
		intent.putExtra("levelNumber", position);
		currentLevel= position;
		startActivityForResult(intent, MY_ACTIVITY_CODE);		
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		levels.get(currentLevel).setCompleted(true);
		if(requestCode== MY_ACTIVITY_CODE){
		//	Toast.makeText(this, "requestCode== MY_ACTIVITY_CODE"+ resultCode, 9000).show();
			if (resultCode== RESULT_OK) {
			//	Toast.makeText(this,"Congratulation! You have successfully completed this level, you can move onto the next level now", 15000).show();
				levels.get(currentLevel).setCompleted(true);
				levelsTexts.set(currentLevel, String.format("%s %40s", levels.get(currentLevel).getText(), "COMPLETED"));
				//levelListView.getChildAt(currentLevel).setBackgroundColor(Color.GREEN);
				AlertDialog successMessage= new AlertDialog.Builder(this).create();
				successMessage.setMessage("Congratulation! You have successfully completed this level, you can move onto the next level now");
				successMessage.show();
				adapter.notifyDataSetChanged();;
			}
			
		}
	}
	 public boolean saveLevelsText() {
	        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
	        SharedPreferences.Editor mEdit1 = sp.edit();
	     //   Set<String> set = new LinkedHashSet<String>();
	        JSONArray jsonArray = new JSONArray(levelsTexts);
	    //    for (String lt : levelsTexts) {
		//		set.add(lt);
		//	}
	//        set.addAll(levelsTexts);
	        
	        mEdit1.putString("list", jsonArray.toString());
	        return mEdit1.commit();
	    }

	    public ArrayList<String> getLevelsText() {
	        SharedPreferences sp = this.getSharedPreferences(SHARED_PREFS_NAME, Activity.MODE_PRIVATE);
	        SharedPreferences.Editor editor = sp.edit();
	        //NOTE: if shared preference is null, the method return empty Hashset and not null          
	       // Set<String> set = sp.getStringSet("list", new LinkedHashSet<String>());
	        //
	        ;
	        ArrayList<String> arrayList = new ArrayList<String>();
			try {
				JSONArray jsonArray = new JSONArray(sp.getString("list", "[]"));
				
		        for (int i = 0; i < jsonArray.length(); i++) {
					arrayList.add(jsonArray.getString(i));
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        	    
	     //   System.out.println(arrayList);  
	        return arrayList;
	    }
	   
}
