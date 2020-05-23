package com.wow.modele;

public class Missiletype {
	private String name;
	private int price;
	private boolean unlocked;
	private int id;
	private boolean equiped;
	private int missileDelay;
	
	public Missiletype(int id, String name, boolean unlocked, int price, boolean equiped, int missileDelay){
		this.id=id;
		this.name=name;
		this.unlocked=unlocked;
		this.price=price;
		this.equiped=equiped;
		this.missileDelay=missileDelay;
	}
	
	public int getMissileDelay(){
		return this.missileDelay;
	}
	public int getId(){
		return this.id;
	}
	public String getName(){
		return this.name;
	}
	public boolean getUnlocked(){
		return this.unlocked;
	}
	public int getPrice(){
		return this.price;
	}
	public boolean getEquiped(){
		return this.equiped;
	}
	public void setEquiped(boolean e){
		this.equiped=e;
	}
	
	public void unlock(){
		this.unlocked=true;
	}

}
