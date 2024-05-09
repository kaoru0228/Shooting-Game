//BossA
import java.awt.*;
class BossA extends MovingObject {
    int delaytime = 10;
    int hp_max;
    int hp_gage;
    int hp1_3;
    int s,t,u,k;
    Image bossA_img = this.getToolkit().getImage("img/bossA_mode2.png");
    
    //コンストラクタ
    BossA(int apWidth, int apHeight) {
        super(apWidth, apWidth);
        w=50;  //敵の半径は12
        h=50;
        hp=0;  //初期状態は死亡
        hp_max=30;  //３の倍数にしないとエラーするので注意
        point=100;
        s=t=u=k=0;
        heal=0;
    }
    void move(Graphics buf,int apWidth,int apHeight){
        buf.setColor(Color.black);
        if(hp>0){  //敵が生きていれば
//            buf.fillOval(x-w, y-h, 2*w, 2*h);  //gcを使って丸形の敵を描く
            buf.drawImage(bossA_img,x-w, y-h, 2*w, 2*h, this);
            x=x+dx;  //座標の更新
            y=y+dy;
            if(Math.random()<0.01){  //1%の確率で速度を再取得
                this.dy = (int)(Math.random()*20-10);
                this.dx = (int)(Math.random()*20-10);
            }
            if(y>apHeight)
                dy=dy*(-1);
            else if(y<-h)
                dy=dy*(-1);
            else if(x>apWidth)
                dx=dx*(-1);
            else if(x<0)
                dx=dx*(-1);
            if(this.hp == 1)
                point = 50000;
            //残りHPゲージを表示
            hp_gage = hp_max*10;
            hp1_3 = (int)(hp_max/3);

            if(k==0){
                buf.setColor(Color.black);
                buf.fillRect(70,15, hp_gage, 10);
                k = 1;
            }
            else if(s<=hp_gage){
                buf.setColor(Color.black);
                buf.fillRect(70,15, hp_gage, 10);
                buf.setColor(Color.red);
                buf.fillRect(70,15,s,10);
                s+=10;
            }
            else if(t<=hp_gage){
                buf.setColor(Color.red);
                buf.fillRect(70,15, hp_gage, 10);
                buf.setColor(Color.green);
                buf.fillRect(70,15,t,10);
                t+=10;
            }
            else if(u<=hp_gage){
                buf.setColor(Color.green);
                buf.fillRect(70,15, hp_gage, 10);
                buf.setColor(Color.blue);
                buf.fillRect(70,15,u,10);
                u+=10;
            }

            if(u>hp_gage){
                if(this.hp >= hp1_3*2){
                    buf.setColor(Color.green);
                    buf.fillRect(70,15, hp_gage, 10);
                    buf.setColor(Color.blue);
                    buf.fillRect(70,15, (int)(hp_gage*(this.hp - hp1_3*2)/hp1_3), 10);
                }
                else if(this.hp >= hp1_3){
                    buf.setColor(Color.red);
                    buf.fillRect(70,15, hp_gage, 10);
                    buf.setColor(Color.green);
                    buf.fillRect(70,15, (int)(hp_gage*(this.hp - hp1_3)/hp1_3), 10);
                }
                else{
                    buf.setColor(Color.black);
                    buf.fillRect(70,15, hp_gage, 10);
                    buf.setColor(Color.red);
                    buf.fillRect(70,15, (int)(hp_gage*this.hp/hp1_3), 10);
                }
            }
        }
    }
    
    void revive(int apWidth, int apHeight){  //ボスを生成
        x = (int)(apWidth/2);  //敵の初期x座標は真ん中
        y = -h;  //初期y座標は上の方
        dy = (int)(Math.random()*20-10);  //鉛直方向の初速度
        dx = (int)(Math.random()*20-10);  //水平方向の初速度
        hp = hp_max;
        explosion=1;
    }
}