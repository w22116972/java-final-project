import java.awt.*;
import java.net.*;
import java.applet.*;
import java.util.Random;

public class Woodstock extends Applet implements Runnable
{	
	
	/*----------類別宣告----------*/
    Graphics g;//圖像
    Thread thread;//執行緒
	int gameStatus=1;
    
	
    /*----------圖檔宣告----------*/
	Image airplane;//自機(林冠廷)
	Image bullet;//自機子彈
    Image enemy[] = new Image[4];//敵人  
    Image e_bullet[] = new Image[4];//敵機子彈
    Image boss[] = new Image[4];//魔王
    Image b_bullet[] = new Image[4];//魔王子彈    
    
    Image try_w;//重玩
    Image[] number = new Image[10];//分數
	Image background;//背景
	Image b_start,b_help,b_menu,help;//控制鈕
	Image life_img;//左上角生命圖案(愛心)	
	Image title;//遊戲名稱
	Image game_over;//遊戲結束畫面
	Image game_win;//遊戲勝利畫面
    
	
	/*----------各變數宣告----------*/	
	//布林變數    
    boolean fire = false;//飛行
    boolean fly = true;
    boolean hit = false;//撞擊
	
	//視窗變數
	int window_left = 0;
	int window_right = 600;
	int window_up = 0;
	int window_down = 600;
    
    //玩家變數	
    int speed_player;//初始速度
	int x_player;
	int y_player;
	
	//子彈
	int b_delay;//延遲子彈出現的計數器
	int i;
	int bullet_xy[][] = new int[100][2]; //[][2]存放子彈的X座標、Y座標
	
	//敵人
	int enemy_num = 60;
	int b;
	int enemy_xy[][] = new int[enemy_num][3]; //[][3]存放敵人的X座標、Y座標、敵機的等級
	int delay; //敵機延遲出現
	int current_enemy; //紀錄目前出現在畫面上的敵機
	int enemy_move = 1;
	
	//敵機子彈
	int e_bullet_xy[][][] = new int[60][30][2]; //[60]存放敵人編號、[100]存放子彈編號、[2]存放敵人子彈的X座標、Y座標
	int e_bullet_num[][] = new int[60][1]; //[60]存放敵人編號、[1] = {子彈編號}
	int e_bullet_delay[][] = new int[60][1]; //[60]存放敵人編號、[1] = {子彈延遲時間}
	
	long timeing;//每8毫秒加1次
    //long delay = 0;
	int score, level, real_level;//分數
    int hnt , tnt , tho, hun, ten, one;//分數位數
    int life; 

	//boss
	int boss_level;
	int boss_xy[][] = new int [4][3];//存放boss的X、Y座標
	int boss_life[][]= new int [4][1];//魔王生命值	 
	int boss_move;//魔王移動速率
	int b_bullet1_xy[][]=new int [100][2];//[boss子彈編號][boss x_bullet , y_bullet] 
	int b_bullet2_xy[][]=new int [100][2];
	int b_bullet3_xy[][]=new int [100][2];
	int b_bullet_delay[]=new int [4];//魔王子彈延遲計數器
	int b_bullet_num[]=new int [4];//子彈編號
	int temp_delay[]=new int [4];
	
	
    /*----------初始化----------*/
    public void init()
    {
    	/*----------匯入圖檔-----------*/
   		background = getImage(getCodeBase(), "background.gif");
        airplane = getImage(getCodeBase(), "airplane.png");
        for(int a = 1; a < 4; a++) enemy[a] = getImage(getCodeBase(), "enemy"+a+".png");
        for(int i = 0; i < 10; i++) number[i] = getImage(getCodeBase(), i+".gif");
        try_w = getImage(getCodeBase(), "try_w.gif");
		bullet = getImage(getCodeBase(), "bullet.png");
		life_img = getImage(getCodeBase(), "life.png");
		
		b_start = getImage(getCodeBase(), "b_start.png");
    	b_help = getImage(getCodeBase(), "b_help.png");
    	b_menu = getImage(getCodeBase(), "b_menu.png");
		help = getImage(getCodeBase(), "help.png");
		
		e_bullet[1] = getImage(getCodeBase(), "e_bullet_1.png");
		e_bullet[2] = getImage(getCodeBase(), "e_bullet_2.png");
		e_bullet[3] = getImage(getCodeBase(), "e_bullet_3.png");

		boss[1] = getImage(getCodeBase(), "boss1.png");
		boss[2] = getImage(getCodeBase(), "boss2.png");
		boss[3] = getImage(getCodeBase(), "boss3.png");
		
		b_bullet[1] = getImage(getCodeBase(), "b_bullet1.png");
		b_bullet[2] = getImage(getCodeBase(), "b_bullet2.png");
		b_bullet[3] = getImage(getCodeBase(), "b_bullet3.png");

		title = getImage(getCodeBase(), "title.png");
		
		game_over = getImage(getCodeBase(), "game_over.gif");
		game_win = getImage(getCodeBase(), "game_win.gif");
		
		
		/*----------初始玩家變數----------*/	
	    speed_player = 0;//初始速度
		x_player = 250;//初始座標
		y_player = 500;
				
		//初始自機子彈座標
		for(int o = 0; o <= 99; o++)
		{
		    for(int p = 0; p <= 1; p++)
		    {
		    	bullet_xy[o][p] = 0;
		    }
		} 
		b_delay = 0;//初始子彈延遲時間
		i = 0;
		
		/*----------初始敵機變數----------*/
		//初始敵人座標
		for(int o = 0; o < enemy_num; o++)
		{
		    for(int p = 0; p <= 2; p++)
		    {
		    	enemy_xy[o][p] = 0;
		    }
		} 
		b = 0;
		delay = 0; //敵機延遲出現
		current_enemy = 0; //紀錄目前出現在畫面上的敵機
				
		//初始敵機子彈座標
		for(int o = 0; o < enemy_num; o++)
		{
		    for(int p = 0; p <= 29; p++)
		    {
			    for(int q = 0; q <= 1; q ++)
		    	{
					e_bullet_xy[o][p][q] = 0;
				}
		    }
		}
		
		//初始敵機子彈計數器
		for(int o = 0; o < enemy_num; o++)
		{
		    e_bullet_num[o][0] = 0;
		} 
		
		//初始敵機子彈延遲
		for(int o = 0; o < enemy_num; o++)
		{
		    e_bullet_delay[o][0] = 0;
		} 
		
		/*----------初始魔王變數----------*/
		//boss
		boss_level=0;
		boss_xy[1][0] = 0; //宣告第一個boss的初始位置
		boss_xy[1][1] = 0;
		boss_xy[2][0] = 0; //宣告第二個boss的初始位置
		boss_xy[2][1] = 0;
		boss_xy[3][0] = 0; //宣告第三個boss的初始位置
		boss_xy[3][1] = 0;
		
		boss_life[1][0]=10;
		boss_life[2][0]=20;
		boss_life[3][0]=30;
		boss_move=1;
		
		for(int bn=0;bn<100;bn++)
		{
			for(int z=0;z<2;z++)
			{
				b_bullet1_xy[bn][z]=0;
				b_bullet2_xy[bn][z]=0;
				b_bullet3_xy[bn][z]=0;
			}
		}
		for(int a=1;a<=3;a++)
		{
			b_bullet_delay[a]=0;
			b_bullet_num[a]=0;			
			temp_delay[a]=0;		
		}	
		
		/*----------初始分數----------*/
		//timeing = 0;//每8毫秒加1次
		score = 0; 
		level = 1;
		real_level = 0;//分數
	    hnt = 0; 
		tnt = 0; 
		tho=0; 
		hun=0; 
		ten=0; 
		one=0;
	    life = 3;
    }
	
	
    //繪製畫面
    public void paint(Graphics g)
    {
	
		g.drawImage( background, 0, 0, getWidth(), getHeight(), this);
		
		if(gameStatus==1)	//menu
	    {
    		g.drawImage( b_start, 240, 400, this);
    		g.drawImage( b_help, 240, 487, this);
			g.drawImage( title, 150, 50, this);
	    }
	    
	    if(gameStatus==2)	//help
	    {
    		g.drawImage( b_menu, 400, 450, this);
			g.drawImage( help, 150, 100, this);
	    }
	    
	    if(gameStatus==4)
	    {
	    	g.drawImage( game_over, 0, 0,  getWidth(), getHeight(), this);
	    	g.drawImage( b_menu, 450, 500, this);  	
	    }
	    
	    if(gameStatus==5)
	    {
	    	g.drawImage( game_win, 0, 0,  getWidth(), getHeight(), this);
	    	g.drawImage( b_menu, 450, 500, this);  	
	    }
	    
	    if(gameStatus==3)  	//gameStart
	    {
			
			//敵人區
			if(real_level<=3)
			{
                int e;
                e = delay / 400;  //延遲敵人出現的時間，每刷新300次畫面，才新增一個敵人				
				if( e == current_enemy && current_enemy < enemy_num ) //當累積的時間/300 = 該出現的敵人編號時，出現敵人
				{
				    enemy_xy[current_enemy][0] = (int)(Math.random()*400);
		            enemy_xy[current_enemy][1] = 0;
					g.drawImage(enemy[real_level], enemy_xy[current_enemy][0],enemy_xy[current_enemy][1], this);
					
					enemy_xy[current_enemy][2] = real_level;  //記錄每架敵機的等級
					
					current_enemy++; //敵人編號++		
				}
				
				delay++;  //每刷一次畫面，delay + 1
			}
			
			//已存在敵人區
			if(real_level<=3)
			{ 
				for( int l = 0; l < enemy_num; l ++ )
				{
				    if(enemy_xy[l][0] != 0 && enemy_xy[l][1] != 0 )
					{
				        g.drawImage(enemy[enemy_xy[l][2]], enemy_xy[l][0],enemy_xy[l][1], this);
						
					}
				}
			}

			//不斷新增敵機子彈
			for( int counter = 0; counter < enemy_num; counter++ )
			{
			    if( enemy_xy[counter][0] != 0 && enemy_xy[counter][1] != 0 )
				{
				    if( e_bullet_delay[counter][0] == 100 ) //延遲敵人子彈發射
					{
					    if( e_bullet_num[counter][0] > 29 )
						{
						    e_bullet_num[counter][0] = 0;
						}
						
					    e_bullet_xy[counter][ e_bullet_num[counter][0] ][0] = enemy_xy[counter][0] + 50;  //敵機子彈X座標
						e_bullet_xy[counter][ e_bullet_num[counter][0] ][1] = enemy_xy[counter][1] + 50;  //敵機子彈Y座標
						e_bullet_num[counter][0]++;  //敵機子彈編號++
						e_bullet_delay[counter][0] = 0; //延遲時間歸0
					}
					
					else if( e_bullet_delay[counter][0] < 100 )
					{
					    e_bullet_delay[counter][0]++;
					}
				}	
			}
						
			//讓既存的敵機子彈繼續飛
			for( int counter = 0; counter < enemy_num; counter++ )
			{
				//if( enemy_xy[counter][0] != 0 && enemy_xy[counter][1] != 0 )
				//{
                    for( int j = 0; j < 30; j ++ )		
                    {
                        if( e_bullet_xy[counter][j][0] != 0 && e_bullet_xy[counter][j][1] != 0 )					
					        g.drawImage(e_bullet[enemy_xy[counter][2]], e_bullet_xy[counter][j][0], e_bullet_xy[counter][j][1], this);
					}
				//}
			}
			
					
			//boss 區		
			if( boss_level != 0 )
			{
				if(boss_xy[1][1] != 0)
				    g.drawImage(boss[1], boss_xy[1][0], boss_xy[1][1], this);
				
				if(boss_xy[2][1] != 0)
				    g.drawImage(boss[2], boss_xy[2][0], boss_xy[2][1], this);
				
				if(boss_xy[3][1] != 0)
				    g.drawImage(boss[3], boss_xy[3][0], boss_xy[3][1], this);
			}
			
					
			//新增boss子彈
			for( int counter = 1; counter <=3 ; counter++ )
			{
				if( boss_xy[counter][1] != 0 )
				{
					//延遲boss子彈
					if( b_bullet_delay[counter] > 5999 )
					{
					    b_bullet_delay[counter] = 0;
					}
					b_bullet_delay[counter]++;
					temp_delay[counter] = b_bullet_delay[counter]/60;
					
				    if( temp_delay[counter] == b_bullet_num[counter] )
					{
				    	if(counter == 1)
				    	{
						g.drawImage(b_bullet[counter], boss_xy[counter][0], boss_xy[counter][1], this); 
						b_bullet1_xy[ b_bullet_num[counter] ][0] = boss_xy[counter][0]+50;
						b_bullet1_xy[ b_bullet_num[counter] ][1] = boss_xy[counter][1]+100;
						b_bullet_num[counter]++;
				    	}
				    	if(counter == 2)
				    	{
						g.drawImage(b_bullet[counter], boss_xy[counter][0], boss_xy[counter][1], this); 
						b_bullet2_xy[ b_bullet_num[counter] ][0] = boss_xy[counter][0]+50;
						b_bullet2_xy[ b_bullet_num[counter] ][1] = boss_xy[counter][1]+100;
						b_bullet_num[counter]++;
				    	}
				    	if(counter == 3)
				    	{
						g.drawImage(b_bullet[counter], boss_xy[counter][0], boss_xy[counter][1], this); 
						b_bullet3_xy[ b_bullet_num[counter] ][0] = boss_xy[counter][0]+50;
						b_bullet3_xy[ b_bullet_num[counter] ][1] = boss_xy[counter][1]+100;
						b_bullet_num[counter]++;
				    	}
				    	
					} 
					
					if(b_bullet_num[counter]>99)
					{
						b_bullet_num[counter] = 0;
					}
				}
			}
			
			//已存在boss子彈
			for( int counter = 0; counter < 100; counter++ )
			{	
				if(b_bullet1_xy[counter][0]!=0 && b_bullet1_xy[counter][1]!=0 )
				{
					g.drawImage(b_bullet[1] , b_bullet1_xy[counter][0] , b_bullet1_xy[counter][1] , this);
				}
				if(b_bullet2_xy[counter][0]!=0 && b_bullet2_xy[counter][1]!=0 )
				{
					g.drawImage(b_bullet[2] , b_bullet2_xy[counter][0] , b_bullet2_xy[counter][1] , this);
				}
				if(b_bullet3_xy[counter][0]!=0 && b_bullet3_xy[counter][1]!=0 )
				{
					g.drawImage(b_bullet[3] , b_bullet3_xy[counter][0] , b_bullet3_xy[counter][1] , this);
				}
		    }
						
			//分數區
			g.drawImage(number[hnt], 380, 0,this);
			g.drawImage(number[tnt], 420, 0,this);
			g.drawImage(number[tho], 460, 0,this);
			g.drawImage(number[hun], 500, 0,this);
			g.drawImage(number[ten], 540, 0,this);
			g.drawImage(number[one], 580, 0,this);
						
			//生命剩餘
			if(life >= 1)
			{
				g.drawImage(life_img, -5, 0, this);
				if(life >= 2)
				{
					g.drawImage(life_img, 65, 0, this);
					if(life >= 3)
					{
						g.drawImage(life_img, 135, 0, this);
						if(life >= 4)
						{
							g.drawImage(life_img, 205, 0, this);
							if(life >= 5) g.drawImage(life_img, 275, 0, this);
						}
					}
				}	
			}
			
			//糊塗塔克(玩家)
			g.drawImage(airplane, x_player, y_player, this);
			
			//畫出已經存在的子彈
			for( int j = 0; j < 100; j++ )
			{
				if( bullet_xy[j][0]!=0 && bullet_xy[j][1]!=0 )
				{
					g.drawImage(bullet, bullet_xy[j][0], bullet_xy[j][1], this);
				}
			}
		
			if( fire ) //加入子彈
			{
			    int delay_temp;
				delay_temp = b_delay/10;
			   
				if(!hit && delay_temp == i ) 
				{
					g.drawImage(bullet, x_player, y_player, this);
					if( i < 100 && b_delay < 1000)
					{
						bullet_xy[i][0] = x_player + 50;
						bullet_xy[i][1]	= y_player;
						i++;					
					}
					else
					{
					    b_delay = 0;
						i = 0;
						bullet_xy[i][0] = x_player + 50;
						bullet_xy[i][1]	= y_player;
						i++;	
					}
				}
				b_delay++;
			}
		}
    }
    
    /*----------執行緒區------------*/
    public void start()
    {
		init();
        thread = new Thread(this);
        thread.start();
    }
    public void stop()
    {  	
        thread = null;
    }
    public void run()
    { 
        long time = System.currentTimeMillis();
        while ( Thread.currentThread() == thread && !hit) 
        {
            try{
				score_counter();//執行分數計算
                place_counter();//執行座標計算
                //hit_something();//執行撞擊事件
                repaint();//重繪畫面
				player_fire();
				enemy_fire();
				boss();
				crash2();
				survive();
                time += 8;//執行緒休眠時間(目前以8毫秒最合適)
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            }
            catch(InterruptedException e){
                break;
            }
        }
    }
      
    //畫面調整
    private Image bgImage;
    private Graphics bg;
    public void update(Graphics g)
    {
    	
        if(bgImage == null){
        bgImage = createImage(this.getSize().width, this.getSize().height);
        bg = bgImage.getGraphics();
        }
        bg.setColor(getBackground());
        bg.fillRect(0, 0, this.getSize().width, this.getSize().height);
        bg.setColor(getForeground());
        paint(bg);
        g.drawImage(bgImage, 0, 0, this);
    }
      
    
    /*-----------座標計算-------------*/
    public void place_counter()
    {    	
    	//敵機移動
    	for(int c = 0; c < enemy_num; c++)
    	{
    		if(enemy_xy[c][1] < 600)
    		{
	    		enemy_xy[c][1] += level;//向下移動
    		}
    		
    		else
    		{
    			enemy_xy[c][0] = 0;
    			enemy_xy[c][1] = 0;
    		}
    	}
		
		//子彈移動
	   for( int k = 0; k < 100; k++ )
	   {
	       if( bullet_xy[k][1] > 0 )
	       {	
	    	   bullet_xy[k][1] = bullet_xy[k][1] - level;
	       }
	       else
	       {
	    	   bullet_xy[k][1] = 0;
	       }
	   }
		
		//敵機子彈移動
	   for( int counter = 0; counter < 60; counter++ )
	   {
	       //if( enemy_xy[counter][0] != 0 && enemy_xy[counter][1] != 0 )
		  // {
		        for( int j = 0; j < 30; j ++ )	
				{
				    if( e_bullet_xy[counter][j][0] != 0 && e_bullet_xy[counter][j][1] != 0 )
		                e_bullet_xy[counter][j][1] = e_bullet_xy[counter][j][1] + level + 1;
				    
				    if(e_bullet_xy[counter][j][1]>=window_down)
				    {
				    	e_bullet_xy[counter][j][0] = 0;
				    	e_bullet_xy[counter][j][1] = 0;
				    }
				    	
				}
			//}       
	   }
	  	   
	   //boss 子彈移動 
	   for( int k = 0; k < 100; k++ )
	   {
		   //if(boss_xy[1][1]!=0)
		   {
			   if(b_bullet1_xy[k][0] !=0 && b_bullet1_xy[k][1] != 0)
			   {
				   b_bullet1_xy[k][1] += level; 
			   }
		   
			   else if(b_bullet1_xy[k][1] > 600)
			   {
				   b_bullet1_xy[k][0] = 0;
				   b_bullet1_xy[k][1] = 0;
			   }
		   }
		   //if(boss_xy[2][1]!=0)
		   {
			   if(b_bullet2_xy[k][0] !=0 && b_bullet2_xy[k][1] != 0)
			   {
				   b_bullet2_xy[k][1] += level; 
			   }
		   
			   else if(b_bullet2_xy[k][1] > 600)
			   {
				   b_bullet2_xy[k][0] = 0;
				   b_bullet2_xy[k][1] = 0;
			   }
		   }
		   //if(boss_xy[3][1]!=0)
		   {
			   if(b_bullet3_xy[k][0] !=0 && b_bullet3_xy[k][1] != 0)
			   {
				   b_bullet3_xy[k][1] += level; 
			   }
		   
			   else if(b_bullet3_xy[k][1] > 600)
			   {
				   b_bullet3_xy[k][0] = 0;
				   b_bullet3_xy[k][1] = 0;
			   }
		   }
	   }
	   
	   //boss移動
	   for( int counter = 1; counter <= 3; counter++ )
	   {
		   if( boss_xy[counter][1] != 0 )
			{
				boss_xy[counter][0] += boss_move;
				
				if(boss_xy[counter][0]<=0 || boss_xy[counter][0]>=500)
					boss_move=boss_move*(-1);
			} 
        } 		
    }
      
    
    /*----------顯示分數 ----------*/
    public void score_counter()
    {
    	hnt = score/100000;
    	tnt = (score%100000)/10000;
    	tho = ((score%100000)%10000)/1000;
    	hun = (((score%100000)%10000)%1000)/100;
    	ten = ((((score%100000)%10000)%1000)%100)/10;
    	one = (((((score%100000)%10000)%1000)%100)%10)/1;
    		
		if(real_level<=3)
			real_level = current_enemy/20 + 1;
    }
   	
    
    /*----------撞擊事件(自機子彈和敵人)----------*/
	public void player_fire()
	{
		for( int m = 0; m < 100; m++ )
		{
			if( bullet_xy[m][0] != 0 && bullet_xy[m][1] != 0 )
			{
			
				for( int counter = 1; counter <= 3; counter++ )
	            {
					if( boss_xy[counter][1] != 0 )
					{
							if( Math.abs( (bullet_xy[m][0]+2) - (boss_xy[counter][0]+50) ) < ( 50+2 ) && Math.abs( (bullet_xy[m][1]+7) - (boss_xy[counter][1]+50) ) < (50+7))
							{
								bullet_xy[m][0] = 0;
								bullet_xy[m][1] = 0;
								boss_life[counter][0]--;
								score = score + counter*100;
							}
					}
				}
				
				for( int n = 0; n < 60; n++ )
				{
					if( enemy_xy[n][0] != 0 && enemy_xy[n][1] != 0 )
					{
						if( Math.abs( (bullet_xy[m][0]+2) - (enemy_xy[n][0]+50) ) < ( 50+2 ) && Math.abs( (bullet_xy[m][1]+7) - (enemy_xy[n][1]+50) ) < (50+7))
						{
							bullet_xy[m][0] = 0;
							bullet_xy[m][1] = 0;
							enemy_xy[n][0] = 0;
							enemy_xy[n][1] = 0;
							score++;
						}
					}
				}
			}
		}
	}

	
	/*----------撞擊事件(敵機子彈和自機)----------*/
	public void enemy_fire()
	{
		for( int counter = 0; counter < enemy_num; counter++ )
		{
			//if( enemy_xy[counter][0] != 0 && enemy_xy[counter][1] != 0 )
			//{
                for( int j = 0; j < 30; j ++ )		
                {
                    if( e_bullet_xy[counter][j][0] != 0 && e_bullet_xy[counter][j][1] != 0 )					
					{
						if( Math.abs( (e_bullet_xy[counter][j][0]+2) - (x_player+50) ) < (50+2) && Math.abs( (e_bullet_xy[counter][j][1]+7) - (y_player+50) ) < (50+7) )
					    {
							e_bullet_xy[counter][j][0] = 0;
					        e_bullet_xy[counter][j][1] = 0;
					        life--;
						}
					}
				}
			//}
		}

		for( int m = 0; m < 100; m++ )
		{
			if(b_bullet1_xy[m][0]!=0 && b_bullet1_xy[m][1]!=0)
			{
				if(Math.abs((b_bullet1_xy[m][0]+2) - (x_player+50) ) < (50+2) && Math.abs( (b_bullet1_xy[m][1]+7) - (y_player+50) ) < (50+7) )
				{
					b_bullet1_xy[m][0]=0;
					b_bullet1_xy[m][1]=0;
					life--;
				}
			}
			if(b_bullet2_xy[m][0]!=0 && b_bullet2_xy[m][1]!=0)
			{
				if(Math.abs((b_bullet2_xy[m][0]+2) - (x_player+50) ) < (50+2) && Math.abs( (b_bullet2_xy[m][1]+7) - (y_player+50) ) < (50+7) )
				{
					b_bullet2_xy[m][0]=0;
					b_bullet2_xy[m][1]=0;
					life--;
				}
			}
			if(b_bullet3_xy[m][0]!=0 && b_bullet3_xy[m][1]!=0)
			{
				if(Math.abs((b_bullet3_xy[m][0]+2) - (x_player+50) ) < (50+2) && Math.abs( (b_bullet3_xy[m][1]+7) - (y_player+50) ) < (50+7) )
				{
					b_bullet3_xy[m][0]=0;
					b_bullet3_xy[m][1]=0;
					life--;
				}
			}
		}
	}
	
	
	/*----------判斷自機是否存活----------*/
	public void survive()
	{
		if(life<=0)
		{
			gameStatus = 4;
		}
		
		else if(life > 0 && enemy_num > 57 && boss_life[1][0]==0 && boss_life[2][0]==0 && boss_life[3][0]==0)
		{
			gameStatus = 5;
		}
	}
	
	
	/*----------自機自破----------*/
	public void crash2() 
	{
		for( int m = 0; m < 60; m++ )
		{
			if( enemy_xy[m][0] != 0 && enemy_xy[m][1] != 0)
			{
				if( Math.abs( x_player - enemy_xy[m][0] ) < ( 100) && Math.abs( y_player - enemy_xy[m][1] ) < ( 100 ))
				{	
					enemy_xy[m][0] = 0;	
					enemy_xy[m][1] = 0;	
					life--;
				}
			}
		}
	}
	
	
	/*判斷boss出場時機*/
	public void boss()
	{
		if( boss_life[1][0]==0)
			{
			boss_xy[1][0] = 0;
			boss_xy[1][1] = 0;
			b_bullet_num[1]=0;
			}
		if(boss_life[2][0]==0)
			{
			boss_xy[2][0] = 0;
			boss_xy[2][1] = 0;
			b_bullet_num[2]=0;
			}
		if(boss_life[3][0]==0)
			{
			boss_xy[3][0] = 0;
			boss_xy[3][1] = 0;
			b_bullet_num[3]=0;
			}

		if(current_enemy==10)
			{
			boss_level=1;
			boss_xy[boss_level][0] = 300;
			boss_xy[boss_level][1] = 150;
			}
		
		else if(current_enemy==30)
			{
			boss_level=2;
			boss_xy[boss_level][0] = 300;
			boss_xy[boss_level][1] = 150;
			}
		else if(current_enemy==50)
			{
			boss_level=3;
			boss_xy[boss_level][0] = 300;
			boss_xy[boss_level][1] = 150;
			}	
	}
    
	
    /*----------滑鼠事件----------*/  
	public boolean mouseDown(Event e, int x, int y)
	{
		fly = true;
		if(gameStatus==3)
		{
			fire = true;  
			x_player = hit_horizon_edge(x)-50;
		}
		
		//主選單
		if( (x>=240 && x <= 368) && (y>=400 && y<=453) && gameStatus==1)
		{
			gameStatus=3;
			start();
		}
		
		if( (x>=240 && x <= 368) && (y>=487 && y<=536) && gameStatus==1)
		{
			gameStatus=2;//進入help
			start();
		}
		
		//help
		if( (x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==2)
		{
			gameStatus=1;//回主選單
			start();
		}
		
		if((x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==4)
		{
			gameStatus=1;//回主選單
			start();	
		}
		
		if((x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==5)
		{
			gameStatus=1;//回主選單
			start();	
		}
		
		return(true);
    }
    
	
	/*----------限定自機範圍-----------*/
	public int hit_horizon_edge( int x )
	{
		
			if( x >= window_right )
			{
				return window_right;
			}
			else if( x <= window_left )
			{
				return window_left;	
			}

			return x;
    }
	
	
	/*----------讓自機跟著滑鼠移動-----------*/
	public boolean mouseMove(Event e, int x, int y)
	{	
		if(gameStatus==3)
		{
			x_player = hit_horizon_edge(x)-50;
		}
		
		return true;
	}
    
	/*-----------按下滑鼠發射子彈----------*/
	public boolean mouseUp(Event e, int x, int y)
	{
		if(gameStatus==3)
		{
			fire = false;
			x_player = hit_horizon_edge(x)-50;	
		}
		
		return true;
    }
	
}
