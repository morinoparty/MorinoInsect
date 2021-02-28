# ルール

## 作業の流れ

Git flow を利用し、作業する
https://danielkummer.github.io/git-flow-cheatsheet/index.ja_JP.html

1. **作業者** Git-flow で、develop から作業ブランチを作成
2. **作業者** 作業完了後　 PR を出す
3. **管理者** PR 時にコードレビュー
4. **管理者** 良ければ develop へマージ
5. **管理者** リリース作業を行う
6. **管理者** リリースする

基本的に master へは push しない。というかできなくする

## コミットについて

.commit_template を適用し、コミットメッセージの頭に、作業内容に最適な絵文字を挿入し、コミットメッセージを記入する

基本的に、修正時は「:+1: Fix ~」とか、機能追加時は「:sparkles: Add ~」とか。

絵文字コミットは必須
