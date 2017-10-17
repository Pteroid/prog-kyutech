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

#### static変数・関数について補足[^*1]

変数・関数が静的であるとは、それらがインスタンスオブジェクトでなくクラス自体に紐づけされている状態を示す。

例えば、`Samaple`クラスの静的な変数`public int abc`・関数`public void func(){}`は`Sample.abc=10;`,`Sample.func();`のようにして呼び出せる。

## Observer Patternについて

### Observer Patternとは？

あるクラス(Observer)が、別のあるクラス(Observable)の状態に応じて何かしらの行動を起こしたいときに用いる手法。

これもデザインパターンと呼ばれる手法の一つである。

### Observer Patternの実装構造について抽象的説明

`Observable`クラスは、自分の好きなタイミングで(基本的にはプロパティの状態が変更されたとき)`Observer`クラスの持つ`UpdateXXX`関数を実行する(たいていは引数にプロパティの値を渡す)。この行動は一般的に ”`Observable`が`Observer`に通知する” というように表現される。

実際に関数を呼び出すきっかけになるのは`Observable`クラスだが、`Observer`クラスが`Observable`クラスを監視している、というように`Observer`クラスが自発的に動いているかのように表現されることが多い。これは`Observable`クラスの持つ`Observer`への参照を削除するタイミングを`Observer`クラスが制御するべきであるからと、自分は考えている。

### Observer Patternの活用例

MVCのModelとViewの関係に対して、Observer Patternを用いると効率的である。

Model`Observable`の状態が変化するたびに、その値を表示するView`Observer`の表示が切り変わる、といった具合である。

