import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Board extends JPanel implements ActionListener {
        int b_height = 400;
        int b_width = 400;

        int max_Dots = 1600;
        int dot_Size = 10;
        int dots ;
        // X & Y coordinates we will use arrays
        int X[] = new int[max_Dots];
        int Y[] = new int[max_Dots];

        int apple_X;
        int apple_Y;
        // Images
        Image  apple,body,head;
        Timer timer;
        int delay = 200;
        boolean leftDirection = true;
        boolean rightDirection = false;
        boolean upDirection = false;
        boolean downDirection = false;
        boolean inGame = true;




        Board(){
            TAdapter tAdapter = new TAdapter();
            addKeyListener(tAdapter);
            setFocusable(true);
            setPreferredSize(new Dimension(b_width,b_height));
                setBackground(Color.BLACK);
                initGame();
                loadImages();

        }


        //Initialize game
          public void initGame(){
                dots = 3;
                // Initialize snake position (X,Y)
                X[0] = 50;
                Y[0] = 50;
                for(int i= 1;i<dots;i++){
                        X[i] = X[0]+dot_Size*i;
                        Y[i] = Y[0];
                }
                locateApple();
                // Timer class object
                timer = new Timer(delay,this);
                timer.start();

        }
        // Class to load images from resource folder
        public void loadImages(){
            ImageIcon bodyIcon = new ImageIcon("src/resources/dot.png" );
            body = bodyIcon.getImage();
            ImageIcon headIcon = new ImageIcon("src/resources/head.png" );
            head = headIcon.getImage();
            ImageIcon appleIcon = new ImageIcon("src/resources/apple.png" );
            apple = appleIcon.getImage();

        }

        // Draw images at particular position
    @Override
    public void paintComponent( Graphics g){
            super.paintComponent(g);
            doDrawing(g);
    }
    // Draw image
    public void doDrawing(Graphics g){
if(inGame){
    g.drawImage(apple,apple_X,apple_Y,this);
    for(int i =0;i<dots;i++){
        if(i==0){
            g.drawImage(head,X[0],Y[0],this);
        }else {
            g.drawImage(body,X[i],Y[i],this);
        }
    }
}else {
    gameOver(g);
    timer.stop();
}

        }
    //Randomize apple position
    public void locateApple(){
        apple_X = ((int)(Math.random()*39))*dot_Size;
        apple_Y = ((int)(Math.random()*39))*dot_Size;

    }

    //Check collision with border and body
    public void checkCollision(){
    // collision with body
            for(int i =1;i<dots;i++){
                if(i>4&&X[0]==X[i]&&Y[0]==Y[1]){
                    inGame = false;
                }
            }
            // collision with border
        if(X[0]<0){
            inGame = false;
        }
        if (X[0]>=b_width){
            inGame = false;
        }
        if (Y[0]<0){
            inGame = false;
        }
        if (Y[0]>=b_height){
            inGame = false;
        }

    }
    // gameover display
    public void gameOver(Graphics g){
            String msg = "Game Over";
        int score = ((dots-3)*100);
        String scoreMsg = "Score: "+Integer.toString(score);
        Font small = new Font("Helvetica",Font.BOLD,14);
        FontMetrics fontMetrics = getFontMetrics(small);  //width and height of font

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(msg,(b_width-fontMetrics.stringWidth(msg))/2,b_height/4);
        g.drawString(scoreMsg,(b_width-fontMetrics.stringWidth(scoreMsg))/2,3*(b_height/4));
    }



    @Override
    public void actionPerformed(ActionEvent actionEvent){
if(inGame){
    checkApple();
    checkCollision();
    move();
}
            repaint();
    }
    // Make snake move
    public void move(){
            for(int i =dots;i>0;i--){
                X[i] = X[i-1];
                Y[i] = Y[i-1];
            }
            if(leftDirection){
                X[0] -=dot_Size;
            }
            if (rightDirection){
                X[0]+=dot_Size;
            }
            if (upDirection){
                Y[0]-=dot_Size;
            }
            if (downDirection){
                Y[0]+=dot_Size;
            }
    }
    //Make snake eat food
    public void checkApple(){
            if(apple_X == X[0]&&apple_Y==Y[0]){
                dots++;
                locateApple();
            }
    }
    // Implement controls
    private class TAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if (key == keyEvent.VK_LEFT &&!rightDirection){
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == keyEvent.VK_RIGHT &&!leftDirection){
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == keyEvent.VK_UP &&!downDirection){
                upDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
            if (key == keyEvent.VK_DOWN &&!upDirection){
                downDirection = true;
                rightDirection = false;
                leftDirection = false;
            }
        }
    }


        }
