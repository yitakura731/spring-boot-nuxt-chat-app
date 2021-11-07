# SpringbootSample2

- 学習用に作製したチャットアプリケーションです
- WebSocket(Stomp)によりチャットを実現しています

## 設計

<img src="Document/image/architect.png" height="120px" width="500px">

#### フロントエンド

- Nuxt/Vue2 の SPA として、Nginx コンテナでホストしています

#### バックエンド

- ビジネスロジックの Rest-API を公開しています。

#### データベース

- チャットの内容を永続化します

## ユーザーインターフェース

<img src="Document/image/login.png" height="340px" width="180px">

<img src="Document/image/friend.png" height="340px" width="180px">

<img src="Document/image/room.png" height="340px" width="180px">

<img src="Document/image/chat.png" height="340px" width="180px">

## セットアップ

1. リポジトリをクローン

   ```
   git clone https://github.com/yitakura731/SpringbootSample2.git
   ```

1. Docker Compose でビルドします

   ```
   docker-compose up -d --build
   ```

1. ブラウザアクセス

   ```
   http://mypc.local:81
   ```
