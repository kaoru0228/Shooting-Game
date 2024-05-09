//Fighter
import java.awt.*;
class Fighter extends MovingObject{
 boolean lflag;  //GomeMasterで定義したもので、左キーを押している間はTrue、離すとFalse
 boolean rflag;  //右キー
 boolean uflag;  //上キー
 boolean dflag;  //下キー
 boolean sflag;  //スペースキー
 int delaytime;  //次の球を発射するまでの待ち時間
 Image fighter_img = this.getToolkit().getImage("img/fighter.jpeg");
 
 //コンストラクタ
 Fighter(int apWidth, int apHeight){
  x = (int)(apWidth/2);  //横のスタート位置は画面の真ん中
  y = (int)(apHeight*0.9);  //縦のスタート位置は下の方
  dx = 10;
  dy = 10;
  w = 10;
  h = 10;
  lflag = false;  //初めはどのキーも押していない状態
  rflag = false;
  uflag = false;
  dflag = false;
  sflag = false;
  delaytime = 5;  //弾の発射待ち時間は5(毎回1ずつ減り、0になったら発射可能)
  point = 0;
  heal=0;
 }
 void revive(int apWidth, int apHeight) { }  //Fighterが復活することはないため、空

 void move(Graphics buf,int apWidth, int apHeight) {
//  buf.setColor(Color.blue);
//  buf.fillRect(x - w,y - h,2*w,2*h);  //自機は四角形
  buf.drawImage(fighter_img , x-w , y-h , 2*w , 2*h , this);  //自機に画像を張り付ける x - w,y - h,2*w,2*h
  if(lflag && !rflag && x > w)  //左キーONかつ右キーOFFかつ左の壁に当たっていないなら
   x = x - dx;  //左へ移動
  if(rflag && !lflag && x < apWidth - w)
   x = x + dx;
  if(uflag && !dflag && y > h)
   y = y - dy;
  if(dflag && !uflag && y < apHeight - h)
   y = y +dy;
   // System.out.println("frags:" + lflag + rflag + delaytime);  //状況をチェック
   // System.out.println("(x,y)=" + "(" + x + "," + y + ")");  //状態確認
 }
}