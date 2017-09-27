# ObserverPatternを用いたMVCの実装例

## やること

1.  TextViewはコンソールから入力されたテキストをTextControllerに渡す
2.  TextControllerは入力されたテキストをTextModelに書き込む
3.  オブザーバーパータンでTextModelの変更を検知してTextViewを更新する

## クラス

```
.
├── README.md
├── controllers
│   └── TextController.py
├── main.py
├── models
│   ├── Observed.py
│   └── TextModel.py
└── views
    ├── Observer.py
    └── TextView.py
```

### Observer

-   後述するObservedを継承したクラスのインスタンス（監視対象）を監視するクラス
-   これを継承するとupdateメソッドの実装を強制される
-   監視対象は変更が加わるとupdateメソッドを実行するようになっている
    -   その際引数として監視対象自身を渡す
-   updateメソッドが実行されると引数で渡された監視対象を参照して必要な値を取り出して処理するように設計する