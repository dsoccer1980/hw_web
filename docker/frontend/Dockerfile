FROM node:alpine
ARG DIR=./frontend

WORKDIR /usr/src/app

ADD $DIR/package.json ./
ADD $DIR/webpack.config.js ./

RUN npm i

ADD $DIR/public/ ./public
ADD $DIR/src ./src/
ADD $DIR/.env ./

EXPOSE 3000

CMD npm start