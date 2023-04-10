import java.awt.*;

public class Cryptid {
    public int xpos;				//the x position
    public int ypos;				//the y position
    public int startX;
    public int startY;
    public int health;
    public boolean isAlive;			//a boolean to denote if the object is alive or dead.
    public int width;
    public int height;
    public Rectangle rec;
    public boolean isScored;

    public Cryptid (int xParameter, int yParameter, int healthParameter, int widthParameter, int heightParameter){
        xpos = xParameter;
        ypos = yParameter;
        health = healthParameter;
        width = widthParameter;
        height = heightParameter;
        isAlive=true;
        rec= new Rectangle (500-(width/2),350-(height/2),width,height);	//construct a rectangle
    }
    public Cryptid (int healthParameter, int widthParameter, int heightParameter){
        startX = (int)((Math.random()*688)+156);
        startX=-1000;
        startY = (int)((Math.random()*361)+163);
        xpos = startX;
        ypos = startY;
        health = healthParameter;
        width = widthParameter;
        height = heightParameter;
        isAlive=false;
        isScored=false;
        rec= new Rectangle (startX-(width/2),startY-(height/2),width,height);	//construct a rectangle
    }
    public void move(){
        if (isAlive == true) {
            height = height + 30;
            width = width + 30;
        }
        xpos = startX-(width/2);
        ypos = startY-(height/2);
        rec= new Rectangle (startX-(width/2),startY-(height/2),width,height);	//construct a rectangle

    }

}

