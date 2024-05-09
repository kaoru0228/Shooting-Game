//FighterBullet
import java.awt.*;
class FighterBullet extends MovingObject{
    Image penetrate1_img = this.getToolkit().getImage("img/penetration1.jpg");
    Image penetrate2_img = this.getToolkit().getImage("img/penetration2.jpg");
    Image penetrate3_img = this.getToolkit().getImage("img/penetration3.jpg");
    Image penetrate4_img = this.getToolkit().getImage("img/penetration4.jpg");
    int penet_count;
    int penet_count2;
    FighterBullet(){
        w=h=3;  //弾の半径３
        dx=0;dy=-10;  //水平方向の初速度は0、鉛直方向の初速度は-6(下向きを正)
        hp=0;  //発射するまで弾は死亡している
        point=0;
        heal=0;
        penet_count=0;
        penet_count2=0;
    }
    void move(Graphics buf, int abWidth, int abHeight){}  //abstractで強制されているmove関数を定義しておく（使わない）

    void move(Graphics buf,int apWidth, int apHeight,int ftrbltmode){
        if(hp>0){  //hpが1以上ないと更新しないため、hpが0になると実質死亡
            if(ftrbltmode==0){
                buf.setColor(Color.green);
                buf.fillRect(x-w,y-h,w,5*h);
                hp=1;
            }
            else if(ftrbltmode==1){
                ftr_blt_mode=1;
                hp=1;  //hpが復活するため、貫通する
                if(penet_count<5){
                    buf.drawImage(penetrate1_img , x-w , y-h , 2*w , 2*h , this);
                    penet_count++;
                }
                else if(penet_count<10){
                    buf.drawImage(penetrate2_img , x-w , y-h , 2*w , 2*h , this);
                    penet_count++;
                }
                else if(penet_count<15){
                    buf.drawImage(penetrate3_img , x-w , y-h , 2*w , 2*h , this);
                    penet_count++;
                }
                else if(penet_count<20){
                    buf.drawImage(penetrate4_img , x-w , y-h , 2*w , 2*h , this);
                    penet_count++;
                    if(penet_count==20){
                        penet_count2++;
                        penet_count=0;
                        if(penet_count2==100){
                            ftr_blt_mode=0;
                            penet_count2=0;
                        }
                    }
                }
            }
            if(y>0 && y<apHeight+h && x>0 && x<apWidth+w){  //弾の位置が画面内なら、弾の位置を更新
                x=x+dx;
                y=y+dy;
            }
            else  //弾が画面内になければ、弾は死亡
                hp=0;
        }
    }
    void revive(int x,int y){  //x,yはFighterの位置。弾が復活したら、その位置からHP1で発射
        this.x=x;
        this.y=y;
        hp=1;
    }
}