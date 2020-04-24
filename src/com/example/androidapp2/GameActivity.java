package com.example.androidapp2;

import com.example.androidapp2.R.drawable;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.os.Build;

public class GameActivity extends ActionBarActivity implements View.OnClickListener {
	//	private static final int MAP_LENGTH = 8;
	
	int length;
	//	LinearLayout mainLayout;
	private int mapValues[][];
	private ImageView mapView[][];
	private Button left;
	private Button up;
	private Button down;
	private Button right;
	private Player player;
	ImageView wallImageView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
			.add(R.id.container, new PlaceholderFragment()).commit();
		}	
		//	int[] level1= {1,1,1,1,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,0,1,1,1,1,1,1,1,2,0,2,3,1,1,3,0,2,4,1,1,1,1,1,1,1,2,1,1,1,1,1,1,1,3,1,1,1,1,1,1,1,1,1,1,1};  
		Bundle extra= getIntent().getExtras();
		this.setTitle("Level "+ (extra.getInt("levelNumber")+1));
		int[] level= extra.getIntArray("levelArray") ;
		length= (int) Math.sqrt(level.length);
		int k=0;
		mapValues= new int[length][length] ;
		for (int i = 0; i < length; i++) {
			for (int j = 0; j < length; j++) {				
				mapValues[i][j]=level[k++];
			}
		}
		mapView= new ImageView[length][length] ;
		player= new Player(mapValues);		
		display();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
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



	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_game, container,
					false);
			return rootView;
		}
	}
	public void display() {			
		LinearLayout mainLayout= new LinearLayout(this);
		mainLayout.setOrientation(1);
		mainLayout.setGravity(Gravity.CENTER_HORIZONTAL);
		LinearLayout[] layout=new LinearLayout[length];

		for (int i = 0; i < length; i++) {			
			layout[i]= new LinearLayout(this);
			layout[i].setGravity(Gravity.CENTER);
			//layout[i].setOrientation(1);
			for (int j = 0; j < length; j++) {
				mapView[i][j]= new ImageView(this);
				int imgCode=R.drawable.objectif;
				switch (mapValues[i][j]) {
				case CONSTANT.BLANK:
					imgCode=R.drawable.blank;
					if(i==player.get_i() && j==player.get_j() )
						imgCode=R.drawable.mario_bas;
					break;
				case CONSTANT.WALL:
					imgCode= R.drawable.mur;
					break;
				case CONSTANT.BOX:
					imgCode=R.drawable.caisse;
					break;
				case CONSTANT.STORAGE_LOCATION:
					imgCode= R.drawable.objectif;
					if(i==player.get_i() && j==player.get_j() )
						imgCode=R.drawable.mario_bas;
					break;
				case CONSTANT.BOX_OK:
					imgCode= R.drawable.caisse_ok;						
					break;
				case CONSTANT.PLAYER:
					imgCode= R.drawable.mario_bas;						
					break;
				default:
					imgCode= R.drawable.abc_ab_bottom_solid_dark_holo;
					break;
				}
				mapView[i][j].setImageResource(imgCode);
				layout[i].addView(mapView[i][j]);
			}
			mainLayout.addView(layout[i]);
		}
		LinearLayout layoutDirUp= new LinearLayout(this);
		layoutDirUp.setGravity(Gravity.CENTER);
		LinearLayout layoutDirDown= new LinearLayout(this);
		layoutDirDown.setGravity(Gravity.CENTER);
		LinearLayout layout2= new LinearLayout(this);
		layout2.setGravity(Gravity.CENTER);
		left= new Button(this);
		left.setText("left");		
		up= new Button(this);
		up.setText("up");
		down= new Button(this);
		down.setText("down");
		right= new Button(this);
		right.setText("right");

		left.setOnClickListener(this);
		up.setOnClickListener(this);
		down.setOnClickListener(this);
		right.setOnClickListener(this);
		layoutDirUp.addView(up);
		layout2.addView(left);
		layout2.addView(right);
		layoutDirDown.addView(down);
		/**/
		mainLayout.addView(layoutDirUp);
		mainLayout.addView(layout2);
		mainLayout.addView(layoutDirDown);
		addContentView(mainLayout, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT ));
	}


	@Override
	public void onClick(View v) {
		char toucheAppuye= 'r';
		if (v==left) 			
			toucheAppuye='q' ;
		else if(v==up)
			toucheAppuye='z' ;
		else if(v==down)	
			toucheAppuye='s' ;
		else if(v==right)	
			toucheAppuye='d' ;
		player.seDeplacer(mapValues, toucheAppuye);
		display();
		if (success()) {
		//	Toast.makeText(this,"Congratulation! You have successfully completed this level, you can move onto the next level now", 9000).show();
		//	Intent intent= new Intent();
		//	intent.putExtra("levelNumber", true);
			setResult(RESULT_OK);
			finish();
		}
	}
	boolean success()
	{
		boolean StorageLocationIsNotHere=true;
		for (int i = 0 ; i <mapValues.length ; i++)		
			for (int j = 0 ; j < mapValues[i].length ; j++)
				if(mapValues[i][j]==CONSTANT.STORAGE_LOCATION)
					StorageLocationIsNotHere=false;

		return StorageLocationIsNotHere;
	}
}
