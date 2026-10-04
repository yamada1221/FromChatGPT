# ブロック崩し

Swingで動く2種類の試作ゲームです。左右の矢印キーでパドルを動かします。

JDK 17を使用し、UTF-8としてコンパイルします。

```sh
javac -encoding UTF-8 --release 8 -d out src/main/java/com/yaoroz/game/*.java
java -cp out com.yaoroz.game.Breakout
# または
java -cp out com.yaoroz.game.BreakoutGame
```

ゲーム画面の表示にはデスクトップ環境が必要です。
`Breakout`はボールを下に落とすと終了し、`BreakoutGame`は下端でも跳ね返る元のルールを維持しています。
描画・キー入力・ゲーム更新はSwingイベントスレッドで実行します。

回帰テストは画面を開かずに衝突判定・パドルの移動範囲・消したブロックの描画を確認します。
Java 8向けのクラスとJava 11向けのモジュールのコンパイルも確認します。

```sh
bash scripts/test.sh
```
