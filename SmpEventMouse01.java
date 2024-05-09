/*===  イベント処理          ===*/
/*===  マウスリスナの実装例  ===*/
// MouseListener は，マウスボタンのクリックなどを監視する
// マウスのクリックなどを監視するには，
//   1. MouseListener インターフェースを実装する
//   2. MouseListener を登録する（ addMouseListener() )
//   3. MouseListener インターフェースに登録されているメソッド
//      全てを必ず登録することが必要である

import java.awt.*;
import java.awt.event.*;

// 1. MouseListenerインタフェースを実装する
public class SmpEventMouse01 extends Frame implements MouseListener {
  // ■ フィールド変数
  String s1 = "", s2 = "", s3 = "";
  int x = -1, y;

  // ■ mainメソッド
  // プログラム全体のスタート地点
  public static void main(String [] args) {
    SmpEventMouse01 sem01 = new SmpEventMouse01(); // FrameTest クラスのオブジェクトを作成
  }

  // ■ コンストラクタ
  SmpEventMouse01(){
    super("SmpEventMouse 1");// 親クラスのコンストラクタを呼び出し
    this.setSize(320, 240);   // ウィンドウのサイズを指定
    addMouseListener(this);  // マウスリスナを登録する
    this.setVisible(true);   // 可視化
  }

  // ■ メソッド
  // Frame クラスの親クラスである Window クラスのメソッドをオーバーライド
  public void paint(Graphics g) {
    g.drawString(s1, 20, 70);       // 文字列s1を表示
    g.drawString(s2, 20, 80);       // 文字列s2を表示
    g.drawString(s3, 20, 90);       // 文字列s2を表示
    if (x != -1) {
      g.drawLine(x-10, y, x+10, y); // ＋マークを描画
      g.drawLine(x, y-10, x, y+10);
    }
  }

  // ■ メソッド
  // MouseListener インターフェースを実行したら
  // mouseEnterd メソッドを実装することが強要される
  public void mouseEntered(MouseEvent me) {  // 領域に入ったときに呼び出される
    s1 = "mouseEntered";                   // 識別文字列の代入
    repaint();                             // 再描画(paintを呼ぶ)
  }

  // ■ メソッド
  // MouseListener インターフェースを実行したら
  // mouseExited メソッドを実装することが強要される
  public void mouseExited(MouseEvent me) {   // 領域から出たときに呼び出される
    s1 = "mouseExited";                    // 識別文字列の代入
    repaint();                             // 再描画(paintを呼ぶ)
  }

  // ■ メソッド
  // MouseListener インターフェースを実行したら
  // mousePressed メソッドを実装することが強要される
  public void mousePressed(MouseEvent me) {  // マウスボタンが押されたときに呼び出される
    s2 = "mousePressed";                   // 識別文字列の代入
    repaint();                             // 再描画(paintを呼ぶ)
  }

  // ■ メソッド
  // MouseListener インターフェースを実行したら
  // mouseReleased メソッドを実装することが強要される
  public void mouseReleased(MouseEvent me) { // マウスボタンが放されたときに呼び出される
    s2 = "mouseReleased";                  // 識別文字列の代入
    repaint();                             // 再描画(paintを呼ぶ)
  }

  // ■ メソッド
  // MouseListener インターフェースを実行したら
  // mouseClicked メソッドを実装することが強要される
  public void mouseClicked(MouseEvent me) {  // マウスがクリックされたときに呼び出される
    s3 = "mouseClicked"; // 識別文字列の代入
    x = me.getX();       // クリックしたx位置を取得
    y = me.getY();       // クリックしたy位置を取得
    repaint();
  }
}