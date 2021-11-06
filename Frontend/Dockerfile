# ビルド環境
FROM node:lts-alpine3.10 as build-stage

ARG REPOSITORY_URL
ARG PUBLIC_DOMAIN

COPY . /src/

WORKDIR /src/

RUN npm install && PUBLIC_DOMAIN=${PUBLIC_DOMAIN} npm run generate 

# 本番環境
FROM nginx:1.19.9 as production-stage

ARG REPOSITORY_URL
ARG PUBLIC_DOMAIN

# HTMLファイル
COPY  --from=build-stage ./src/dist/ /usr/share/nginx/html/

# NGINX設定ファイル
COPY ./default.conf.template /etc/nginx/conf.d/default.conf.template
COPY ./default.nginx.conf /etc/nginx/nginx.conf

ENV TZ Asia/Tokyo
RUN echo "${TZ}" > /etc/timezone \
    && dpkg-reconfigure -f noninteractive tzdata

# 起動
CMD ["/bin/bash", "-c", "envsubst '$$REPOSITORY_URL' < /etc/nginx/conf.d/default.conf.template > /etc/nginx/conf.d/default.conf && nginx -g 'daemon off;'"]
