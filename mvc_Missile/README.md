# MVCを用いた具体的な実装例

## Singleton Patternについて

### Singleton Patternとは？
プログラムにおいて、あるクラスが唯一のインスタンスしか持たないことを確約したいときに用いる手法。

これはデザインパターンと呼ばれる手法の一つである。

### Singleton Patternの実装法
とりあえず、以下に実装例を示す。
```java
public class Test {
    private Test() {}
    static Test instance = new Test();
    public static Test getInstance() {return instance;}
}
```
では、なぜこれで`Test`クラスが唯一のインスタンスしか持たないことを確約できるのか説明する。
まず、コンストラクタが`private`に指定されていることで、クラス外部から`Test`クラスのインスタンスを生成できない。

これでだけだと外部から`Test`クラスのインスタンスを利用できなくなるので、`Test`クラスのインスタンスを静的な[^*1]プロパティ`instance`に代入し、`getInstance`関数を用いてそれを返すようにする。

よって、インスタンスの生成タイミングは`getInstance`関数を呼び出したときである。

#### static変数・関数について補足[^*1]

変数・関数が静的であるとは、それらがインスタンスオブジェクトでなくクラス自体に紐づけされている状態を示す。    

注意点としては、static関数内ではstaticな変数・関数しか使えない。

以下、使用例。

```java
public class Sample {
  public static int x_static = 0;
  public int y_non_static = 0;
  public static void func_static(){
    System.out.println(x_static);
    //System.out.println(y_non_static);		このような記述はできない。
  }
  public void func_non_static(){
    System.out.println(x_static);
    System.out.println(y_non_static);
  }
}
```

```java
public static void main(){
  Sample a = new Sample();
  a.y_non_static = 20;
  
  Sample b = new Sample();
  b.y_non_static = 300;
  
  Sample.x_static = 1;
  
  Sample.func_static();
  //a.func_static();		このような記述はできない。
  //Sample.func_non_static();		このような記述はできない。
  a.func_non_static();
  b.func_non_static();
}
```



## Observer Patternについて

### Observer Patternとは？

あるクラス(Observer)が、別のあるクラス(Observable)の状態に応じて何かしらの行動を起こしたいときに用いる手法。

これもデザインパターンと呼ばれる手法の一つである。

### Observer Patternの抽象的説明

`Observable`クラスは、自分の好きなタイミングで(基本的にはプロパティの状態が変更されたとき)`Observer`クラスの持つ`UpdateXXX`関数を実行する(たいていは引数にプロパティの値を渡す)。この行動は一般的に ”`Observable`が`Observer`に通知する” というように表現される。

実際に関数を呼び出すきっかけになるのは`Observable`クラスだが、`Observer`クラスが`Observable`クラスを監視している、というように`Observer`クラスが自発的に動いているかのように表現されることが多い。これは`Observable`クラスの持つ`Observer`への参照を削除するタイミングを`Observer`クラスが制御するべきであるからと、自分は考えている。

### Observer Patternの活用例

MVCのModelとViewの関係に対して、Observer Patternを用いると効率的である。

Model`Observable`の状態が変化するたびに、その値を表示するView`Observer`の表示が切り変わる、といった具合である。

