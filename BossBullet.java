//BossBullet
import java.awt.*;
class BossBullet extends MovingObject{
    double X,Y,absX,absY,k,t;
    Image enmyblt_img = this.getToolkit().getImage("img/bossA_mode2.png");
    BossBullet(){
        w=h=7;  //弾の半径5
        dx=0;dy=5;  //水平方向の初速度は0、鉛直方向の初速度は3(下向きを正)
        hp=0;  //発射するまで弾は死亡している
        point=10;
        X=Y=absX=absY=k=t=0;
        heal=0;
    }
    void move(Graphics buf,int apWidth, int apHeight){
        if(hp>0){  //hpが1以上ないと更新しないため、hpが0になると実質死亡
            buf.setColor(Color.black);
//            buf.fillOval(x-w,y-h,2*w,2*h);
            buf.drawImage(enmyblt_img,x-w, y-h, 2*w, 2*h, this);
            if(y>0 && y<apHeight && x>0 && x<apWidth){  //弾の位置が画面内なら、弾の位置を更新
                x = x+dx;
                y = y+dy;
            }
            else  //弾が画面内になければ、弾は死亡
                hp=0;
        }
    }

    void revive(int x, int y){}

    void revive_every_direction(int x,int y,double rad){  //x,yはEnemyBの位置。偏角を引数として受け取り、その方向へ発射(速度は10)
        this.x = x;
        this.y = y;
        hp=1;
        this.dx = (int)(Math.cos(rad)*10);
        this.dy = (int)(Math.sin(rad)*10);
        System.out.println("bossA dx; "+this.dx + "bossA dy;"+this.dy);
    }

    int tracking_dx(int ftrx,int ftry,int bossAx,int bossAy){  //自機に向けて発射する弾の速度のx成分を返すメソッド
        X = (double)(ftrx - bossAx);  //Xは、自機とEnemyBの水平距離（符号付）
        absX = Math.abs(X);
        Y = (double)(ftry - bossAy);  //Yは、自機とEnemyBの鉛直距離（符号付）
        absY = Math.abs(Y);
        k = absX + absY;
        int bossAdx = (int)((X/k)*10);  //水平距離と鉛直距離の比をかけることでdx,dyを計算。（速度を足すとおおよそ10になるように)
        return bossAdx;
    }
    int tracking_dy(int ftrx,int ftry,int bossAx,int bossAy){  //自機に向けて発射する弾の速度のy成分を返すメソッド
        X = (double)(ftrx - bossAx);
        absX = Math.abs(X);
        Y = (double)(ftry - bossAy);
        absY = Math.abs(Y);
        k = absX + absY;
        int bossAdy = (int)((Y/k)*10);
        return bossAdy;
    }
    void revive_tracking(int x,int y,int dx,int dy){  //x,yはEnemyBの位置。偏角を引数として受け取り、その方向へ発射(速度は10)
        this.x = x;
        this.y = y;
        hp=1;
        this.dx = dx;
        this.dy = dy;
        System.out.println("bossA dx; "+this.dx + "bossA dy;"+this.dy);
    }
}