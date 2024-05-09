//EnemyBullet
import java.awt.*;
class EnemyBullet extends MovingObject{
    double X,Y,absX,absY,k,t;
    Image enmyblt_img = this.getToolkit().getImage("img/bossA_mode2.png");
    EnemyBullet(){
        w=h=5;  //弾の半径5
        dx=0;dy=3;  //水平方向の初速度は0、鉛直方向の初速度は3(下向きを正)
        hp=0;  //発射するまで弾は死亡している
        X=Y=absX=absY=k=t=0;
        point=10;
        heal=0;
    }
    void move(Graphics buf,int apWidth, int apHeight){
        if(hp>0){  //hpが1以上ないと更新しないため、hpが0になると実質死亡
            buf.setColor(Color.red);
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

    void revive(int x,int y,int dx, int dy){  //x,yはEnemyBの位置。dx,dyは速度。弾が復活したら、その位置からHP1で発射
        this.x = x;
        this.y = y;
        hp=1;
        this.dx = dx;
        this.dy = dy;
    }
    int enmyblt_x_velocity(int ftrx,int ftry,int enemyB_x,int enemyB_y){  //自機に向けて発射する弾の速度のx成分を返すメソッド
        X = (double)(ftrx - enemyB_x);  //Xは、自機とEnemyBの水平距離（符号付）
        absX = Math.abs(X);
        Y = (double)(ftry - enemyB_y);  //Yは、自機とEnemyBの鉛直距離（符号付）
        absY = Math.abs(Y);
        k = absX + absY;
        
        int enemyBblt_dx = (int)((X/k)*10);  //水平距離と鉛直距離の比をかけることでdx,dyを計算。（速度を足すとおおよそ10になるように）
        int enemyBblt_dy = (int)((Y/k)*10);
        System.out.println();
        t = X/k;
        System.out.println("absX ; " + absX + ", absY ; " +absY +", absX+absY ; "+ k);
        System.out.println("X/(absX+absY) ; " + t);
        System.out.println("dx ; "+enemyBblt_dx + ", dy ; " + enemyBblt_dy);
        return enemyBblt_dx;
    }
    int enmyblt_y_velocity(int ftrx, int ftry,int enemyB_x,int enemyB_y){  //自機に向けて発射する弾の速度のy成分を返すメソッド
        X = (double)(ftrx - enemyB_x);
        absX = Math.abs(X);
        Y = (double)(ftry - enemyB_y);
        absY = Math.abs(Y);
        k = absX + absY;
        int enemyBblt_dy = (int)((Y/k)*10);
        return enemyBblt_dy;
    }
    

/*        //EnmyBの弾が自機を狙うよう速度を設定
        int X = ftr.x - EnmyB.x;  //Xは、自機とEnemyBの水平距離（符号付）
        int absX;  //absXは、水平距離（符号なし）
        if(X >= 0)
            absX = X;
        else
            absX = X*(-1);

        int Y = ftr.y - EnmyB.y;  //Yは、自機とEnemyBの鉛直距離（符号付）
        int absY;  //absYは、鉛直距離（符号なし）
        if(Y>=0)
            absY = Y;
        else
            absY = Y*(-1);
        dx = (int)((X/(absX+absY))*(Math.random()*3));  //水平距離と鉛直距離の比をかけることでdx,dyを計算。（速度を足すとおおよそ3になるように）
        dy = (int)((Y/(absX+absY))*(Math.random()*3));*/
}