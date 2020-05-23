package com.wow.modele;

import java.io.IOException;

import javax.imageio.ImageIO;

public class Menusprite extends Sprite{
	public Menusprite(){
		
		this.source = "com/wow/images/menu.png";

		try {
			this.img = ImageIO.read(cl.getResource(this.source));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		this.sourceWidth = this.img.getWidth(null);
		this.sourceHeight = this.img.getHeight(null);
		this.sprites=1;
		this.width = (int)(this.sourceWidth/this.sprites*MENU_RATIO);
		this.height = (int)(this.sourceHeight *MENU_RATIO) ;
		this.sourceX=0;
		this.sourceY=0;
		this.x = (BOARD_WIDTH-this.width)/2;
		this.y = (BOARD_HEIGHT-this.height)/2;
		System.out.println(width+" - "+height);
	}

}
