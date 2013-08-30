package com.golf.wolf.managers;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import android.graphics.drawable.Drawable;

@Table(name = "Players")
public class Player extends Model {
	
	@Column(name = "Name")
	public String name;
	
	@Column(name = "Picture")
	public Drawable picture;
	
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
		
	
	
}
