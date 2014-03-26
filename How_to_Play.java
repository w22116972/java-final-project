import java.awt.*;
import java.net.*;
import java.applet.*;
import java.util.Random;

public class How_to_Play extends Applet implements Runnable{
	
	//類別宣告
    Graphics g;//圖像
    Thread thread;//執行緒
    
    //圖檔變數
    Image woodstock_down, woodstock_up, woodstock_hit;//糊塗塔克(玩家)
    Image try_w;//磚塊 
    Image cloud;//雲層 
    Image grass;//草地
    Image score_w;//分數
    Image background;//藍天
    
    //布林變數    
    boolean fly = false;//飛行  
    boolean hit = false;//撞擊
    
    //各項變數
    
    //玩家變數	
    int height_player = 150;//初始區
    int speed_player = 0;//初始速度
    int x_player, y_player;//位置區
    
    //初始化
    public void init(){
    	
        background = getImage(getCodeBase(), "background1.jpg");
        woodstock_down = getImage(getCodeBase(), "woodstock_down.gif");
        woodstock_up = getImage(getCodeBase(), "woodstock_up.gif");
        cloud = getImage(getCodeBase(), "cloud.gif");
        grass = getImage(getCodeBase(), "grass.gif");
        try_w = getImage(getCodeBase(), "try_w.gif");
    }
    
    //執行緒
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
                hit_something();//執行撞擊事件
                repaint();//重繪畫面
                time += 8;//執行緒休眠時間(目前以8毫秒最合適)
                Thread.sleep( Math.max(0, time - System.currentTimeMillis()));
            }
            catch( InterruptedException e){
                break;
            }
        }
    }
    
    //繪製畫面
    public void paint(Graphics g){
	
        g.drawImage( background, 0, 0, getWidth(), getHeight(), this);//藍天背景
        
        //雲層區
        g.drawImage(cloud, -100, -150, this);
        g.drawImage(cloud, 100, -150, this);
        g.drawImage(cloud, 300, -150, this);
        //草地區
        g.drawImage(grass, -100, 530, this);
        g.drawImage(grass, 100, 530, this);
        g.drawImage(grass, 300, 530, this);
        //糊塗塔克(玩家)
        if( fly){                                    
            g.drawImage(woodstock_up, x_player, y_player, this);
        }else{
            g.drawImage(woodstock_down, x_player, y_player, this);
        }
        //重玩按鈕
        if( hit)
        	g.drawImage(try_w, 285, 584, this);
        	
    }
    
    //畫面調整
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
    
    //座標計算
    public void place_counter(){
    	
    	random_number_reset();//執行亂數重置
    	//糊塗塔克(玩家)
        x_player = 175;
	    y_player = height_player;
	    height_player += 2;//向下移動
	    if( fly)         //向上移動
		    height_player -= speed_player; 
	    
    }
    
    //亂數重置
    public void random_number_reset(){
    	
 	    //加速區
 	    if( speed_player <= 3 && fly){
 	        speed_player ++ ;
 	    }
 	    
    }
    
    //撞擊事件
    public void hit_something(){
    	
	    if(y_player>=450 || y_player<=60)
	    	hit=true;
    }
    
    //滑鼠事件
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
