FROM node:alpine

WORKDIR /app

COPY package.json /app

RUN npm install -g @angular/cli

RUN npm install

COPY . /app

# Expose the default Angular development server port
EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]
