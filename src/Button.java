import java.awt.*;

/**
 * Created by chales on 11/6/2017.
 */
public class Button {

   //VARIABLE DECLARATION SECTION
   //Here's where you state which variables you are going to use.
   public int xpos;				//the x position
   public int ypos;				//the y position
   public boolean isAlive;			//a boolean to denote if the object is alive or dead.
   public int width;
   public int height;
   public Rectangle rec;			//declare a rectangle variable
   public String text;

   // METHOD DEFINITION SECTION

   // Constructor Definition
   // A constructor builds the object when called and sets variable values.
   public Button(int xParameter, int yParameter, int widthParameter, int heightParameter, String textParameter)
   {
      xpos = xParameter;
      ypos = yParameter;
      width = widthParameter;
      height = heightParameter;
      text = textParameter;
      isAlive=false;
      rec= new Rectangle (xpos,ypos,width,height);	//construct a rectangle
   } // constructor


}
