//Basic Mouse Application Template
//
// sets up a window and puts some text on the screen.
// not threaded.

// Uses MouseListener for buttons
// Uses MouseMotionListener for mouse movement and dragging

// To use the mouse
// Add 
//    implements MouseListener
//    canvas.addMouseListener(this);
//    the required methods
//
//       public void mouseClicked(MouseEvent e)
//       public void mousePressed(MouseEvent e)
//       public void mouseReleased(MouseEvent e)



//K. Chun 8/2018

//*******************************************************************************
//Import Section
//Add Java libraries needed for the game
//import java.awt.Canvas;

//Graphics Libraries

import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

//Keyboard and Mouse
import java.awt.event.*;


//*******************************************************************************
// Class Definition Section

public class Submarine implements MouseListener, MouseMotionListener, KeyListener, Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
    final int WIDTH = 1000;
    final int HEIGHT = 700;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public BufferStrategy bufferStrategy;
    public Image rockPic;
    public Image bigPic;
    public Image fastPic;
    public Image insidePic;
    public Image oceanPic;
    public Image beastPic;
 //   public Image cursorPic;

    //Mouse position variables
    public int mouseX, mouseY;
  //  public int cursorX, cursorY;

    //button
    public Button shield;
    public Button shieldBack;
    public Button power;
    public Button powerBack;
    public Button hull;
    public Button hullBack;
    public Cryptid beast;
    public Rock basic;
    public Rock big;
    public Rock fast;
    public Rock[] debris;
    public Rock[] bigs;
    public Rock[] swarm;
    public Button gameOver;


    //timer variables
    public long startTime;
    public long currentTime;
    public long elapsedTime;

    public int debrisCounter = 0;
    public int bigsCounter = 0;
    public int swarmCounter = 0;

    public boolean startTimer;
    public int timer=0;
    public int counter=0;
    public int score=0;


    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        Submarine myApp = new Submarine();   //creates a new instance of the app
        new Thread(myApp).start();  //start up the thread
    }


    // Constructor Method
    // This has the same name as the class
    // This section is the setup portion of the program
    // Initialize your variables and construct your program objects here.
    public Submarine() {

        setUpGraphics();
        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
        canvas.addKeyListener(this);

        shieldBack = new Button(800,625,150,60,"");
        shield = new Button(800, 625, 150, 60, "     Shields");
        System.out.println("DONE");

        powerBack = new Button(800,550,150,60,"");
        power = new Button (800,550,150,60, "      Power");

        hullBack = new Button(25,625,150,60,"");
        hull = new Button (25,625,150,60, "Hull Strength");
        gameOver = new Button(0,0,1000,700,"Game Over");

        rockPic = Toolkit.getDefaultToolkit().getImage("meteor.png");
        basic = new Rock (3,2,2);

        bigPic = Toolkit.getDefaultToolkit().getImage("big.png");
        big = new Rock (7,5,5);

        fastPic = Toolkit.getDefaultToolkit().getImage("fast.png");
        fast = new Rock (2,2,2);

        debris = new Rock[100];
        for(int d=0; d<debris.length; d++){
            debris[d] = new Rock (basic.health,basic.width,basic.height);
        }
        bigs = new Rock[100];
        for(int b=0; b<bigs.length; b++){
            bigs[b] = new Rock (big.health,big.width,big.height);
        }
        swarm = new Rock[75];
        for(int f=0; f<swarm.length; f++){
            swarm[f] = new Rock (fast.health,fast.width,fast.height);
        }
        beastPic = Toolkit.getDefaultToolkit().getImage("download.png");
        beast = new Cryptid (1000000000,2,2);


        oceanPic = Toolkit.getDefaultToolkit().getImage("ocean.png");
        insidePic = Toolkit.getDefaultToolkit().getImage("inside.png");
     //   cursorPic = Toolkit.getDefaultToolkit().getImage("cursor.png");

    }// BasicGameApp()


    //*******************************************************************************
//User Method Section
//
// put your code to do things here.
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        JPanel panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.


        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE");

    }

    //paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);
        g.setColor(Color.BLACK);
        g.setFont(new Font("TimesRoman", Font.BOLD, 25));
//        g.drawString("Mouse and Time Example", 100, 50);
//        g.drawString("Mouse Cursor is at  " + mouseX + ",  " + mouseY, 100, 150);

        g.drawImage(oceanPic,0,0,1000,700,null);
//        g.drawImage(insidePic,0,0,1000,700,null);
        //basic rock
        if(basic.isAlive == true){
            g.drawImage(rockPic,basic.xpos,basic.ypos,basic.width,basic.height,null);
        }
        for(int d=0; d<debris.length; d++){
            if(debris[d].isAlive == true) {
                g.drawImage(rockPic, debris[d].xpos, debris[d].ypos, debris[d].width, debris[d].height, null);
            }
        }

        if(big.isAlive == true){
            g.drawImage(bigPic,big.xpos,big.ypos,big.width,big.height,null);
        }
        for(int b=0; b<bigs.length; b++){
            if(bigs[b].isAlive == true){
                g.drawImage(bigPic,bigs[b].xpos,bigs[b].ypos,bigs[b].width,bigs[b].height,null);
            }
        }
        for(int f=0; f<swarm.length; f++) {
            if (swarm[f].isAlive == true) {
                g.drawImage(fastPic,swarm[f].xpos,swarm[f].ypos,swarm[f].width,swarm[f].height,null);
            }
        }
        if (beast.isAlive == true){
            g.drawImage(beastPic, 500-(beast.width/2), 350-(beast.height/2), beast.width, beast.height, null);
        }
        if (debrisCounter > 99 && bigsCounter > 99 && swarmCounter > 74) {
            System.out.println("It awakens");
            beast.isAlive = true;
        }

        g.drawImage(insidePic,0,0,1000,700,null);
        //print score to screen
        g.setColor(Color.WHITE);
        g.drawString("Score: "+ score,50,50);

        //shield
        g.setColor(Color.GRAY);
        g.fillRect(shieldBack.xpos, shieldBack.ypos, shieldBack.width, shieldBack.height);
        g.setColor(Color.BLACK);
        g.drawString(shieldBack.text, shieldBack.xpos + 5, shieldBack.ypos + 40);
        g.setColor(Color.YELLOW);
        g.fillRect(shield.xpos, shield.ypos, shield.width, shield.height);
        g.setColor(Color.BLACK);
        g.drawString(shield.text, shield.xpos + 5, shield.ypos + 40);

        //power
        g.setColor(Color.GRAY);
        g.fillRect(powerBack.xpos, powerBack.ypos, powerBack.width, powerBack.height);
        g.setColor(Color.BLACK);
        g.drawString(powerBack.text,powerBack.xpos + 5,powerBack.ypos + 40);
        g.setColor(Color.ORANGE);
        g.fillRect(power.xpos, power.ypos, power.width, power.height);
        g.setColor(Color.BLACK);
        g.drawString(power.text,power.xpos + 5,power.ypos + 40);

        //hull
        g.setColor(Color.GRAY);
        g.fillRect(hullBack.xpos,hullBack.ypos,hullBack.width,hullBack.height);
        g.setColor(Color.BLACK);
        g.drawString(hullBack.text,hullBack.xpos + 2, hullBack.ypos + 40);
        g.setColor(Color.GREEN);
        g.fillRect(hull.xpos,hull.ypos,hull.width,hull.height);
        g.setColor(Color.BLACK);
        g.drawString(hull.text,hull.xpos + 2, hull.ypos + 40);

        //gameOver
        if(beast.height>1400) {
            g.setColor(Color.BLACK);
            g.fillRect(gameOver.xpos, gameOver.ypos, gameOver.width, gameOver.height);
            g.setColor(Color.WHITE);
            g.drawString(gameOver.text, gameOver.xpos + 430, gameOver.ypos + 350);
        }
        else if (hull.width<=0){
            g.setColor(Color.BLACK);
            g.fillRect(gameOver.xpos, gameOver.ypos, gameOver.width, gameOver.height);
            g.setColor(Color.WHITE);
            g.drawString(gameOver.text, gameOver.xpos + 430, gameOver.ypos + 350);
        }

     //   g.drawImage(cursorPic,cursorX,cursorY,100,100,null);

//        //time
//        g.drawString(elapsedTime+"",70,340);
        System.out.println(timer);
//        if(timer==(int)((Math.random()*10)) && counter<debris.length){
        if(timer==(int)(Math.random()*5) && counter<debris.length){
            debris[counter].isAlive=true;
            debris[counter].startX=(int)((Math.random()*688)+156);
            debris[counter].startY=(int)((Math.random()*361)+163);
            timer=0;
            counter++;

        }
        if(timer==(int)(Math.random()*7) && counter<bigs.length){
            bigs[counter].isAlive=true;
            bigs[counter].startX=(int)((Math.random()*688)+156);
            bigs[counter].startY=(int)((Math.random()*361)+163);
            timer=0;
            counter++;

        }
        if(timer==(int)(Math.random()*4) && counter<swarm.length){
            swarm[counter].isAlive=true;
            swarm[counter].startX=(int)((Math.random()*688)+156);
            swarm[counter].startY=(int)((Math.random()*361)+163);
            timer=0;
            counter++;

        }
//        if(timer==(int)(Math.random()*7) && counter<debris.length){
//
//
//        }
//        if(timer==(int)(Math.random()*13) && counter<bigs.length){
//
//
//        }
//        if(timer==(int)(Math.random()*5) && counter<swarm.length){
//
//        }
        if(timer==8){
            timer=0;
        }


        g.dispose();
        bufferStrategy.show();
    }

    // main thread
    // this is the code that plays the game after you set things up
    public void run() {

        while (true) {

            //get the current time
            currentTime = System.currentTimeMillis();

            //calculate the elapsed time
            elapsedTime = currentTime-startTime;
            moveThings();
            collision();
            render();  // paint the graphics
            pause(1000); // sleep for 10 ms
            timer++;
        }
    }

    public void moveThings(){
        basic.move();
        big.move();
        for(int d=0;d< debris.length;d++){
            debris[d].move();
        }
        for(int b=0; b<bigs.length; b++){
            bigs[b].move();
        }
        for(int f=0; f<swarm.length; f++){
            swarm[f].move();
        }
        beast.move();
    }
    public void collision(){
        if (basic.height>=450 && basic.isAlive==true && shield.isAlive==false){
            hull.width= hull.width-30;
            basic.isAlive=false;
        }
        for(int d=0; d<debris.length; d++){
            if (debris[d].height>=450 && debris[d].isAlive==true && shield.isAlive==false){
                hull.width= hull.width-30;
                debris[d].isAlive=false;
            }
        }
        if (big.height>=500 && big.isAlive==true && shield.isAlive==false){
            hull.width= hull.width-50;
            big.isAlive=false;
        }
        for(int b=0; b<bigs.length; b++){
            if (bigs[b].height>=500 && bigs[b].isAlive==true && shield.isAlive==false){
                hull.width= hull.width-50;
                bigs[b].isAlive=false;
            }
        }
        for(int f=0; f<swarm.length; f++){
            if(swarm[f].height>=150 && swarm[f].isAlive==true && shield.isAlive==false){
                hull.width = hull.width-10;
                swarm[f].isAlive=false;
            }
        }


        if (shield.isAlive==true){
            power.width = power.width-1;
        }
        if(basic.height>=450 && basic.isAlive==true && shield.isAlive==true) {
            power.width = power.width-10;
            basic.isAlive = false;
        }
        for(int d=0; d<debris.length; d++) {
            if (debris[d].height >= 450 && debris[d].isAlive == true && shield.isAlive == true) {
                power.width = power.width - 10;
                debris[d].isAlive = false;
            }
        }

        if(big.height>=500 && big.isAlive==true && shield.isAlive==true) {
            power.width = power.width-20;
            big.isAlive = false;
        }
        for(int b=0; b<bigs.length; b++) {
            if (bigs[b].height >= 500 && bigs[b].isAlive == true && shield.isAlive == true) {
                power.width = power.width - 20;
                bigs[b].isAlive = false;
            }
        }
        for(int f=0; f<swarm.length; f++){
            if(swarm[f].height>=150 && swarm[f].isAlive == true && shield.isAlive == true){
                power.width = power.width - 10;
                swarm[f].isAlive = false;
            }
        }

        if(power.width<=0) {
            shield.isAlive = false;
        }
        if(hull.width<=0){
            gameOver.isAlive=false;
        }
    }
    public void pause(int time) {
        //sleep
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {

        }
    }


    //*******************************************************************************

    // REQUIRED KEYBOARD METHODS
    public void keyPressed(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println("Key Pressed: " + key);

        if (key == 'd') {
            System.out.println("Yeah");
        }
    }

    public void keyReleased(KeyEvent e) {
        char key = e.getKeyChar();
        System.out.println("Key Released: " + key);

    }

    public void keyTyped(KeyEvent e) {
        // The getKeyModifiers method is a handy //way to get a String
        // representing the //modifier key.
        System.out.println("Key Typed: " + e.getKeyChar() + " "
                + KeyEvent.getKeyModifiersText(e.getModifiers()) + "\n");
    }
    //*******************************************************************************

    // REQUIRED Mouse Listener methods
    @Override
    public void mouseClicked(MouseEvent e) {

        int x, y;
        x = e.getX();
        y = e.getY();

        mouseX = x;
        mouseY = y;
//        cursorX = (x+10)/2;
//        cursorY = (y+10)/2;
        System.out.println();
        System.out.println("Mouse Clicked at " + x + ", " + y);

        if ((shield.rec.contains(x, y)) && shield.isAlive==false && power.width>0) {
            System.out.println("Shields Activated");
            shield.isAlive = true;
            startTime = System.currentTimeMillis();
            startTimer = true;
        }
//        System.out.println(shield.isAlive);
       else if ((shield.rec.contains(x, y)) && shield.isAlive==true && power.width>0) {
            System.out.println("Shields Deactivated");
            shield.isAlive = false;
            startTime = System.currentTimeMillis();
            startTimer = true;
        }

        if ((basic.rec.contains(x, y)) && basic.isAlive==true) {
            basic.health=basic.health-1;
        }
        for(int d=0; d<debris.length; d++){
            if ((debris[d].rec.contains(x, y)) && debris[d].isAlive==true && power.width>0) {
                debris[d].health=debris[d].health-1;
            }
        }

        if ((big.rec.contains(x, y)) && big.isAlive==true && power.width>0) {
            big.health=big.health-1;
        }
        for(int b=0; b<bigs.length; b++) {
            if ((bigs[b].rec.contains(x, y)) && bigs[b].isAlive==true && power.width>0) {
                bigs[b].health=bigs[b].health-1;
            }
        }
        for(int f=0; f<swarm.length; f++){
            if ((swarm[f].rec.contains(x,y)) && swarm[f].isAlive==true && power.width>0){{
            swarm[f].health=swarm[f].health-1;
            }
            }
        }


        if (basic.health==0){
            basic.isAlive=false;
            System.out.println("basic.isAlive =" + basic.isAlive);
        }
        for(int d=0; d<debris.length; d++) {
            if (debris[d].health == 0 && debris[d].isScored==false) {
                debris[d].isAlive = false;
                debrisCounter +=1;
                score=score+10;
                debris[d].isScored = true;
            }
        }
        if (big.health==0){
            big.isAlive=false;
            System.out.println("big.isAlive =" + big.isAlive);
        }
        for(int b=0; b<bigs.length; b++) {
            if (bigs[b].health==0 && bigs[b].isScored==false){
                bigs[b].isAlive=false;
                bigsCounter +=1;
                score=score+15;
                bigs[b].isScored = true;
            }
        }
        for(int f=0; f<swarm.length; f++){
            if (swarm[f].health==0 && swarm[f].isScored==false){{
            swarm[f].isAlive=false;
            swarmCounter +=1;
            score=score+5;
            swarm[f].isScored = true;
            }
            }
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse Button Pressed");


    }

    @Override
    public void mouseReleased(MouseEvent e) {


        System.out.println();
        System.out.println("Mouse Button Released");


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has entered the window");

    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println();
        System.out.println("Mouse has left the window");

    }

    // REQUIRED Mouse Listener methods
    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.println("Mouse is being dragged");
    }

    public void mouseMoved(MouseEvent e) {

        int x, y;
        x = e.getX();
        y = e.getY();
        mouseX = x;
        mouseY = y;
        // System.out.println("Mouse moving");
        // System.out.println("Mouse is at  " + x + ", " + y);

    }


}