package com.golf.wolf.managers;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.content.ContentValues;

@Table(name = "Players")
public class Player extends Model {
	
	@Column(name = "_id")
	public int _id;
	
	@Column(name = "Name")
	public String name;
	
	@Column(name = "Picture")
	public String picture;
	
	@Column(name = "Score")
	public int score;
	
	@Column(name = "RunningTotal")
	public int runningTotal;
	
	
	public Player(){
        super();
	}
	
	public Player(String name){
		super();
		this.name = name;
		this.score = 0;
	}
		
	/**
	 * constructs a conventvalues object for provider crud
	 * @return ContentValues a view of the player object
	 */
	public ContentValues getPlayerContentValues() {
		ContentValues cv = new ContentValues();
		cv.put("Name", name);
		cv.put("Picture", picture);
		cv.put("Score", score);
		cv.put("RunningTotal", runningTotal);
		return cv; 
	}
	
	
}
