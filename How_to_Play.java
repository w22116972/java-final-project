import java.awt.*;
import java.net.*;
import java.applet.*;
import java.util.Random;

public class How_to_Play extends Applet implements Runnable{
	
	//���O�ŧi
    Graphics g;//�Ϲ�
    Thread thread;//�����
    
    //�����ܼ�
    Image woodstock_down, woodstock_up, woodstock_hit;//�k���J(���a)
    Image try_w;//�j�� 
    Image cloud;//���h 
    Image grass;//��a
    Image score_w;//����
    Image background;//�Ť�
    
    //���L�ܼ�    
    boolean fly = false;//����  
    boolean hit = false;//����
    
    //�U���ܼ�
    
    //���a�ܼ�	
    int height_player = 150;//��l��
    int speed_player = 0;//��l�t��
    int x_player, y_player;//��m��
    
    //��l��
    public void init(){
    	
        background = getImage(getCodeBase(), "background1.jpg");
        woodstock_down = getImage(getCodeBase(), "woodstock_down.gif");
        woodstock_up = getImage(getCodeBase(), "woodstock_up.gif");
        cloud = getImage(getCodeBase(), "cloud.gif");
        grass = getImage(getCodeBase(), "grass.gif");
        try_w = getImage(getCodeBase(), "try_w.gif");
    }
    
    //�����
    public void start(){
    	
        thread = new Thread(this);
        thread.start();
    }
    public void stop(){
    	
        thread = null;
    }
    public void run(){
   	    
        long time = System.currentTimeMillis();
        while ( Thread.currentThread() == thread && !hit) {
            try{
            	place_counter();
                hit_something();//���漲���ƥ�
                repaint();//��ø�e��
                time += 8;//�������v�ɶ�(�ثe�H8�@��̦X�A)
                Thread.sleep( Math.max(0, time - System.currentTimeMillis()));
            }
            catch( InterruptedException e){
                break;
            }
        }
    }
    
    //ø�s�e��
    public void paint(Graphics g){
	
        g.drawImage( background, 0, 0, getWidth(), getHeight(), this);//�ŤѭI��
        
        //���h��
        g.drawImage(cloud, -100, -150, this);
        g.drawImage(cloud, 100, -150, this);
        g.drawImage(cloud, 300, -150, this);
        //��a��
        g.drawImage(grass, -100, 530, this);
        g.drawImage(grass, 100, 530, this);
        g.drawImage(grass, 300, 530, this);
        //�k���J(���a)
        if( fly){                                    
            g.drawImage(woodstock_up, x_player, y_player, this);
        }else{
            g.drawImage(woodstock_down, x_player, y_player, this);
        }
        //�������s
        if( hit)
        	g.drawImage(try_w, 285, 584, this);
        	
    }
    
    //�e���վ�
    private Image bgImage;
    private Graphics bg;
    public void update( Graphics g){
    	
        if( bgImage == null){
        bgImage = createImage( this.getSize().width, this.getSize().height);
        bg = bgImage.getGraphics();
        }
        bg.setColor(getBackground());
        bg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        bg.setColor(getForeground());
        paint( bg);
        g.drawImage(bgImage, 0, 0, this);
    }
    
    //�y�Эp��
    public void place_counter(){
    	
    	random_number_reset();//����üƭ��m
    	//�k���J(���a)
        x_player = 175;
	    y_player = height_player;
	    height_player += 2;//�V�U����
	    if( fly)         //�V�W����
		    height_player -= speed_player; 
	    
    }
    
    //�üƭ��m
    public void random_number_reset(){
    	
 	    //�[�t��
 	    if( speed_player <= 3 && fly){
 	        speed_player ++ ;
 	    }
 	    
    }
    
    //�����ƥ�
    public void hit_something(){
    	
	    if(y_player>=450 || y_player<=60)
	    	hit=true;
    }
    
    //�ƹ��ƥ�
    public boolean mouseDown(Event e, int x, int y){
    fly = true;
    showStatus("Fly up~");
        if(x>=285 && x<=460)
        	if(y>=584 && y<=620){
    	    height_player = 150;
    	    hit=false;
    	    start();
        }
    return(true);
    }
    public boolean mouseMove(Event e, int x, int y){
    fly = false;
    showStatus("Fly down~");
    return(true);
    }
    public boolean mouseUp(Event e, int x, int y){
    fly = false;
    showStatus("Fly down~");
    return(true);
    }
}
