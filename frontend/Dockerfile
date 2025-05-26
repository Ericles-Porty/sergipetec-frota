# Build Stage 1 - instalar dependências e build opcional
FROM node:22-alpine AS build

WORKDIR /app

# Copia package.json e yarn.lock
COPY package.json yarn.lock ./

# Instala dependências com yarn
RUN yarn install

# Copia o restante do código
COPY . .

# Opcional: build para produção (comentar se for só dev)
# RUN yarn build

# Stage 2 - imagem final para rodar em dev (mais leve que build)
FROM node:22-alpine

WORKDIR /app

# Copia tudo da build stage (com node_modules e código)
COPY --from=build /app ./

EXPOSE 5173

# Rodar o Nuxt em modo desenvolvimento
CMD ["yarn", "dev"]
