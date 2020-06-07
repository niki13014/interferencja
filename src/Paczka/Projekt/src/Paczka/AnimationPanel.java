package Paczka;

 


import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

 

import javax.swing.JPanel;

 


public class AnimationPanel extends JPanel implements Runnable
{
    int n;    
    int []u=new int[40];
    int []yi=new int[40];
    
    boolean active=true;
    

 

    int f=0;
    int z=0;
    
    double y;
    Color color;

 

    
    
    
    AnimationPanel(){
            super();
            setPreferredSize(new Dimension(300, 300));
    }
            
    
    public void changeColor(Color k){
        color=k;
    }
    
    
    public void run() {

 

        while (active) {
        
            n++;  
            int ii;
            ii=0;
            z++;
            f++;
        
        
            
            if(n>=1600)
                n=0;
            
            
        
            if(f>80*(40+Animation.lambda))
                f=(-3200+(79*Animation.lambda));
            if(z>120*(40+Animation.lambda))
                z=1600+39*Animation.lambda;
    
            
        
        for(ii=0;ii<39;ii++)
                u[ii]=-1600-(40*Animation.lambda)+(40+Animation.lambda)*(ii+1)+f;
            
            
            
            
            for(ii=0;ii<39;ii++)
            {
                yi[ii]=-3200-(79*Animation.lambda)+(40+Animation.lambda)*(ii+1)+z;
            }

 

            

 

            
    
            
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

 

            repaint(); 
        }
    }
    

 

    
    
        
        
        public void paintComponent(Graphics g) {

 

            super.paintComponent(g);
            
            
            Graphics2D g2 = (Graphics2D) g;
            g2.setStroke(new BasicStroke(7));
           
            
            
            g2.fillOval(35, 295+Animation.distance/2, 10, 10);
            g2.fillOval(35, 295-Animation.distance/2, 10, 10);
            g2.setColor(change());
            g2.fillOval(35, 295+Animation.distance/2, 10, 10);
            g2.fillOval(35, 295-Animation.distance/2, 10, 10);
                            
            
        //    g2.drawOval(200-n/2, 50-n/2, n, n);
            
            
        //    g2.drawOval(400-n/2, 50-n/2, n, n);
        
            g2.setColor(color);
            
            for(int ii=0;ii<39;ii++){
                g2.drawOval(40-u[ii]/2, 300-u[ii]/2+Animation.distance/2, u[ii], u[ii]);
                g2.drawOval(40-u[ii]/2, 300-u[ii]/2-Animation.distance/2, u[ii], u[ii]);
                
            
            }
             
            for(int ii=0;ii<39;ii++){
                
                g2.drawOval(40-yi[ii]/2,300-yi[ii]/2+Animation.distance/2, yi[ii], yi[ii]);
                g2.drawOval(40-yi[ii]/2,300-yi[ii]/2-Animation.distance/2, yi[ii], yi[ii]);
                
            }
            
            g2.setColor(Color.BLACK);
       
           
    int []u=new int[40];
           
					//g2.fillRect(45-u[10]/2, 355-u[10]/2+Animation.distance/2, 5, 10000000);
    			g2.fillRect(38, 295-Animation.distance/2+10+2, 5, Animation.distance-4);
            
            

 

            
            }

 

        private Color change() {
            // TODO Auto-generated method stub
            
            Color k=null;
            if(Animation.lambda>=30 && Animation.lambda<=100)
                k=Color.green;
            if(Animation.lambda>100 && Animation.lambda<=(590-470))
                k=Color.green;
            if(Animation.lambda>(590-470) && Animation.lambda<=(630-470))
                k=Color.orange;
            if(Animation.lambda>(630-470) && Animation.lambda<=(700-470))
                k=Color.red;
            
            
            return k;
        }
            
            
}