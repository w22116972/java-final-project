import java.awt.*;
import java.net.*;
import java.applet.*;
import java.util.Random;

public class Woodstock extends Applet implements Runnable
{	
	
	/*----------���O�ŧi----------*/
    Graphics g;//�Ϲ�
    Thread thread;//�����
	int gameStatus=1;
    
	
    /*----------���ɫŧi----------*/
	Image airplane;//�۾�(�L�a��)
	Image bullet;//�۾��l�u
    Image enemy[] = new Image[4];//�ĤH  
    Image e_bullet[] = new Image[4];//�ľ��l�u
    Image boss[] = new Image[4];//�]��
    Image b_bullet[] = new Image[4];//�]���l�u    
    
    Image try_w;//����
    Image[] number = new Image[10];//����
	Image background;//�I��
	Image b_start,b_help,b_menu,help;//����s
	Image life_img;//���W���ͩR�Ϯ�(�R��)	
	Image title;//�C���W��
	Image game_over;//�C�������e��
	Image game_win;//�C���ӧQ�e��
    
	
	/*----------�U�ܼƫŧi----------*/	
	//���L�ܼ�    
    boolean fire = false;//����
    boolean fly = true;
    boolean hit = false;//����
	
	//�����ܼ�
	int window_left = 0;
	int window_right = 600;
	int window_up = 0;
	int window_down = 600;
    
    //���a�ܼ�	
    int speed_player;//��l�t��
	int x_player;
	int y_player;
	
	//�l�u
	int b_delay;//����l�u�X�{���p�ƾ�
	int i;
	int bullet_xy[][] = new int[100][2]; //[][2]�s��l�u��X�y�СBY�y��
	
	//�ĤH
	int enemy_num = 60;
	int b;
	int enemy_xy[][] = new int[enemy_num][3]; //[][3]�s��ĤH��X�y�СBY�y�СB�ľ�������
	int delay; //�ľ�����X�{
	int current_enemy; //�����ثe�X�{�b�e���W���ľ�
	int enemy_move = 1;
	
	//�ľ��l�u
	int e_bullet_xy[][][] = new int[60][30][2]; //[60]�s��ĤH�s���B[100]�s��l�u�s���B[2]�s��ĤH�l�u��X�y�СBY�y��
	int e_bullet_num[][] = new int[60][1]; //[60]�s��ĤH�s���B[1] = {�l�u�s��}
	int e_bullet_delay[][] = new int[60][1]; //[60]�s��ĤH�s���B[1] = {�l�u����ɶ�}
	
	long timeing;//�C8�@��[1��
    //long delay = 0;
	int score, level, real_level;//����
    int hnt , tnt , tho, hun, ten, one;//���Ʀ��
    int life; 

	//boss
	int boss_level;
	int boss_xy[][] = new int [4][3];//�s��boss��X�BY�y��
	int boss_life[][]= new int [4][1];//�]���ͩR��	 
	int boss_move;//�]�����ʳt�v
	int b_bullet1_xy[][]=new int [100][2];//[boss�l�u�s��][boss x_bullet , y_bullet] 
	int b_bullet2_xy[][]=new int [100][2];
	int b_bullet3_xy[][]=new int [100][2];
	int b_bullet_delay[]=new int [4];//�]���l�u����p�ƾ�
	int b_bullet_num[]=new int [4];//�l�u�s��
	int temp_delay[]=new int [4];
	
	
    /*----------��l��----------*/
    public void init()
    {
    	/*----------�פJ����-----------*/
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
		
		
		/*----------��l���a�ܼ�----------*/	
	    speed_player = 0;//��l�t��
		x_player = 250;//��l�y��
		y_player = 500;
				
		//��l�۾��l�u�y��
		for(int o = 0; o <= 99; o++)
		{
		    for(int p = 0; p <= 1; p++)
		    {
		    	bullet_xy[o][p] = 0;
		    }
		} 
		b_delay = 0;//��l�l�u����ɶ�
		i = 0;
		
		/*----------��l�ľ��ܼ�----------*/
		//��l�ĤH�y��
		for(int o = 0; o < enemy_num; o++)
		{
		    for(int p = 0; p <= 2; p++)
		    {
		    	enemy_xy[o][p] = 0;
		    }
		} 
		b = 0;
		delay = 0; //�ľ�����X�{
		current_enemy = 0; //�����ثe�X�{�b�e���W���ľ�
				
		//��l�ľ��l�u�y��
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
		
		//��l�ľ��l�u�p�ƾ�
		for(int o = 0; o < enemy_num; o++)
		{
		    e_bullet_num[o][0] = 0;
		} 
		
		//��l�ľ��l�u����
		for(int o = 0; o < enemy_num; o++)
		{
		    e_bullet_delay[o][0] = 0;
		} 
		
		/*----------��l�]���ܼ�----------*/
		//boss
		boss_level=0;
		boss_xy[1][0] = 0; //�ŧi�Ĥ@��boss����l��m
		boss_xy[1][1] = 0;
		boss_xy[2][0] = 0; //�ŧi�ĤG��boss����l��m
		boss_xy[2][1] = 0;
		boss_xy[3][0] = 0; //�ŧi�ĤT��boss����l��m
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
		
		/*----------��l����----------*/
		//timeing = 0;//�C8�@��[1��
		score = 0; 
		level = 1;
		real_level = 0;//����
	    hnt = 0; 
		tnt = 0; 
		tho=0; 
		hun=0; 
		ten=0; 
		one=0;
	    life = 3;
    }
	
	
    //ø�s�e��
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
			
			//�ĤH��
			if(real_level<=3)
			{
                int e;
                e = delay / 400;  //����ĤH�X�{���ɶ��A�C��s300���e���A�~�s�W�@�ӼĤH				
				if( e == current_enemy && current_enemy < enemy_num ) //��ֿn���ɶ�/300 = �ӥX�{���ĤH�s���ɡA�X�{�ĤH
				{
				    enemy_xy[current_enemy][0] = (int)(Math.random()*400);
		            enemy_xy[current_enemy][1] = 0;
					g.drawImage(enemy[real_level], enemy_xy[current_enemy][0],enemy_xy[current_enemy][1], this);
					
					enemy_xy[current_enemy][2] = real_level;  //�O���C�[�ľ�������
					
					current_enemy++; //�ĤH�s��++		
				}
				
				delay++;  //�C��@���e���Adelay + 1
			}
			
			//�w�s�b�ĤH��
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

			//���_�s�W�ľ��l�u
			for( int counter = 0; counter < enemy_num; counter++ )
			{
			    if( enemy_xy[counter][0] != 0 && enemy_xy[counter][1] != 0 )
				{
				    if( e_bullet_delay[counter][0] == 100 ) //����ĤH�l�u�o�g
					{
					    if( e_bullet_num[counter][0] > 29 )
						{
						    e_bullet_num[counter][0] = 0;
						}
						
					    e_bullet_xy[counter][ e_bullet_num[counter][0] ][0] = enemy_xy[counter][0] + 50;  //�ľ��l�uX�y��
						e_bullet_xy[counter][ e_bullet_num[counter][0] ][1] = enemy_xy[counter][1] + 50;  //�ľ��l�uY�y��
						e_bullet_num[counter][0]++;  //�ľ��l�u�s��++
						e_bullet_delay[counter][0] = 0; //����ɶ��k0
					}
					
					else if( e_bullet_delay[counter][0] < 100 )
					{
					    e_bullet_delay[counter][0]++;
					}
				}	
			}
						
			//���J�s���ľ��l�u�~��
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
			
					
			//boss ��		
			if( boss_level != 0 )
			{
				if(boss_xy[1][1] != 0)
				    g.drawImage(boss[1], boss_xy[1][0], boss_xy[1][1], this);
				
				if(boss_xy[2][1] != 0)
				    g.drawImage(boss[2], boss_xy[2][0], boss_xy[2][1], this);
				
				if(boss_xy[3][1] != 0)
				    g.drawImage(boss[3], boss_xy[3][0], boss_xy[3][1], this);
			}
			
					
			//�s�Wboss�l�u
			for( int counter = 1; counter <=3 ; counter++ )
			{
				if( boss_xy[counter][1] != 0 )
				{
					//����boss�l�u
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
			
			//�w�s�bboss�l�u
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
						
			//���ư�
			g.drawImage(number[hnt], 380, 0,this);
			g.drawImage(number[tnt], 420, 0,this);
			g.drawImage(number[tho], 460, 0,this);
			g.drawImage(number[hun], 500, 0,this);
			g.drawImage(number[ten], 540, 0,this);
			g.drawImage(number[one], 580, 0,this);
						
			//�ͩR�Ѿl
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
			
			//�k���J(���a)
			g.drawImage(airplane, x_player, y_player, this);
			
			//�e�X�w�g�s�b���l�u
			for( int j = 0; j < 100; j++ )
			{
				if( bullet_xy[j][0]!=0 && bullet_xy[j][1]!=0 )
				{
					g.drawImage(bullet, bullet_xy[j][0], bullet_xy[j][1], this);
				}
			}
		
			if( fire ) //�[�J�l�u
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
    
    /*----------�������------------*/
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
				score_counter();//������ƭp��
                place_counter();//����y�Эp��
                //hit_something();//���漲���ƥ�
                repaint();//��ø�e��
				player_fire();
				enemy_fire();
				boss();
				crash2();
				survive();
                time += 8;//�������v�ɶ�(�ثe�H8�@��̦X�A)
                Thread.sleep(Math.max(0, time - System.currentTimeMillis()));
            }
            catch(InterruptedException e){
                break;
            }
        }
    }
      
    //�e���վ�
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
      
    
    /*-----------�y�Эp��-------------*/
    public void place_counter()
    {    	
    	//�ľ�����
    	for(int c = 0; c < enemy_num; c++)
    	{
    		if(enemy_xy[c][1] < 600)
    		{
	    		enemy_xy[c][1] += level;//�V�U����
    		}
    		
    		else
    		{
    			enemy_xy[c][0] = 0;
    			enemy_xy[c][1] = 0;
    		}
    	}
		
		//�l�u����
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
		
		//�ľ��l�u����
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
	  	   
	   //boss �l�u���� 
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
	   
	   //boss����
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
      
    
    /*----------��ܤ��� ----------*/
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
   	
    
    /*----------�����ƥ�(�۾��l�u�M�ĤH)----------*/
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

	
	/*----------�����ƥ�(�ľ��l�u�M�۾�)----------*/
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
	
	
	/*----------�P�_�۾��O�_�s��----------*/
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
	
	
	/*----------�۾��ۯ}----------*/
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
	
	
	/*�P�_boss�X���ɾ�*/
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
    
	
    /*----------�ƹ��ƥ�----------*/  
	public boolean mouseDown(Event e, int x, int y)
	{
		fly = true;
		if(gameStatus==3)
		{
			fire = true;  
			x_player = hit_horizon_edge(x)-50;
		}
		
		//�D���
		if( (x>=240 && x <= 368) && (y>=400 && y<=453) && gameStatus==1)
		{
			gameStatus=3;
			start();
		}
		
		if( (x>=240 && x <= 368) && (y>=487 && y<=536) && gameStatus==1)
		{
			gameStatus=2;//�i�Jhelp
			start();
		}
		
		//help
		if( (x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==2)
		{
			gameStatus=1;//�^�D���
			start();
		}
		
		if((x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==4)
		{
			gameStatus=1;//�^�D���
			start();	
		}
		
		if((x>=400 && x <= 600) && (y>=450 && y<=575) && gameStatus==5)
		{
			gameStatus=1;//�^�D���
			start();	
		}
		
		return(true);
    }
    
	
	/*----------���w�۾��d��-----------*/
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
	
	
	/*----------���۾���۷ƹ�����-----------*/
	public boolean mouseMove(Event e, int x, int y)
	{	
		if(gameStatus==3)
		{
			x_player = hit_horizon_edge(x)-50;
		}
		
		return true;
	}
    
	/*-----------���U�ƹ��o�g�l�u----------*/
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
