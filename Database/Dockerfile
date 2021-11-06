FROM postgres:12.6

# セットアップ用スクリプトを配置
COPY ./setup/init.sql /docker-entrypoint-initdb.d/
