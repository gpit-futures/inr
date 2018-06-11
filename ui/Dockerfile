FROM node:alpine AS build
WORKDIR /app

COPY package.json .
RUN npm install

COPY . .
RUN npm run build

FROM nginx:alpine
WORKDIR /var/www/html
COPY --from=build /app/dist .
COPY --from=build /app/nginx.conf /etc/nginx/nginx.conf
COPY --from=build /app/mime.types /etc/nginx/mime.types
