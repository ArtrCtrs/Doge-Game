package com.wow.modele;

public class Roundproperties {
	private int numberGreens;
	private int numberReds;
	private int numberBlues;
	private int numberGreys;
	private int ennemiescycle;
	private int roundcycle;
	
	public Roundproperties(int green, int red, int blue,int grey,int roundcycle, int ennemiescycle){
		this.numberGreens=green;
		this.numberReds=red;
		this.numberBlues=blue;
		this.numberGreys=grey;
		this.roundcycle=roundcycle;
		this.ennemiescycle=ennemiescycle;
	}
	
	public int getGreen(){
		return this.numberGreens;
	}
	public int getRed(){
		return this.numberReds;
	}
	public int getBlue(){
		return this.numberBlues;
	}
	public int getGrey(){
		return this.numberGreys;
	}
	public int getRoundCycle(){
		return this.roundcycle;
	}
	public int getEnnemiesCycle(){
		return this.ennemiescycle;
	}
	

}
