<center>
  <p align="center">
    <img src="https://gitlab.com/desafio-banco-inter-thales/user-manager/-/raw/main/public/logo.png" width="150"/>&nbsp;
  </p>  
  <h1 align="center">🚀 Microserviço: Gerenciador de sessões para votações</h1>
  <p align="center">
    Microserviço referente ao desafio para DBC/Sicredi<br />
    Espero que gostem!!
  </p>
</center>
<br />

## Objetivo do microserviço

O objetivo do service é realizar todo o tratamento de votação no geral, criando tópicos para votação, abrindo sessões em cima
desse tópico criado, ao qual os usuários poderão votar nas sessões que estejam abertas, também poderemos consultar os resultados.

## Ferramentas necessárias para rodar o projeto em container

- Docker
- Docker compose

## Ferramentas necessárias para o projeto localmente

- IDE de sua preferência
- Postman
- JDK 17
    - Utilizei  instalação da versão corretto 17 pela própria IDE, no caso Intellij.
- Docker e Docker compose
    - Para rodar as dependências
- Maven
    - Utilizei a versão **3.9.2**

## Documentação

1. Acessar documentação após startar o service através da seguinte url:
```sh
http://localhost:8080/swagger-ui/index.html#/
```


## Pré-execução

1. Clonar o repositório:
```sh
git clone https://github.com/EduMonteiroDev/voting-service.git
```

2. Entre na pasta referente ao repositório:
```sh
cd voting-service
```

## Execução


É possível executar o projeto de duas formas:

>Importe a collection do postman que está na pasta postman com todos os endpoints configurados.

### Apenas containers

Execute o comando
```shell
docker-compose -f containers/docker-compose.yml up -d --build
```

> A opção **--build** serve para não utilizarmos o cache para construção
> da imagem do voting-service, caso queira buildar uma imagem já tendo gerado
> a primeira e não quer perder tempo pode remover essa opção de --build

### Aplicação rodando localmente

Execute o comando
```shell
docker-compose -f containers/database-compose.yml up -d --build

Ajuste a IDE para executar utilizando o contexto das enviroments variables abaixo

spring.application.name=voting-service
spring.datasource.url=jdbc:mysql://localhost:3306/voting?sslMode=REQUIRED
spring.datasource.username=admin
spring.datasource.password=mm#236533
spring.jpa.hibernate.ddl-auto=update
spring.jpa.generate-ddl=true
spring.jpa.show-sql=true
spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=admin
spring.rabbitmq.password=mm#236533
spring.rabbitmq.queue-name=votingQueue
```

2. Execute a aplicação
> Execute a API baseado no método main() na classe Main.java



## Banco de dados
O banco de dados principal é um MySQL e para subir localmente não precisamos de
passos extras, a própria aplicação se encarrega de subir as tabelas.

## Stop

Caso queira derrubar a aplicação dentro do container

1. Execute o comando
```shell
docker-compose -f containers/docker-compose.yml down
```

## Testes

1. Execute o comando

> É possível executar via IDE. Utilizando o Intellij vá até a pasta
> **src/test/java/com/sicredi/votingservice** clique com o botão direito e na metade
> das opções apresentadas escolha **Run Tests in 'com.sicredi.votingservice''**

## Responsabilidades do microserviço

- [x] Criar novo tópico para votação
- [x] Abrir sessões de votação
- [x] Votar nas sessões que foram abertas
- [x] Retornar o resultado da sessão de votação
