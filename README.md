# GoldenBlood Project

## Descrição
Este projeto é uma aplicação Spring Boot que expõe uma API para manipulação e consulta de dados relacionados a candidatos e doadores de sangue.

## Pré-requisitos

1. **Java 21** - Certifique-se de ter o JDK 21 instalado.
2. **Docker** - Para rodar o MySQL em um container.
3. **IntelliJ IDEA** - Para executar o projeto com o perfil `dev`.
4. **cURL** - Para fazer requisições HTTP via linha de comando.

## Configuração do Banco de Dados

1. **Rodando o MySQL com Docker:**

   Execute o seguinte comando para criar e iniciar um container Docker com MySQL:

   ```bash
   docker run --name meu-mysql-container -e MYSQL_ROOT_PASSWORD=goldenblood@123 -e MYSQL_DATABASE=goldenblood -e MYSQL_USER=goldenblood -e MYSQL_PASSWORD=goldenblood@123 -p 3306:3306 -d mysql:latest
   ```

   Isso criará um banco de dados chamado `goldenblood` com o usuário `goldenblood` e senha `goldenblood@123`.

## Executando o Projeto

1. Abra o projeto no IntelliJ IDEA.

2. Configure o perfil `dev`:

   No IntelliJ, vá em **Run > Edit Configurations** e adicione `--spring.profiles.active=dev` na seção de parâmetros de VM.

3. Execute o projeto:

   Clique em **Run** ou pressione `Shift + F10` para iniciar o projeto com o perfil `dev`.

## Cadastro e Autenticação de Usuário

Antes de utilizar os endpoints da API, é necessário criar e autenticar um usuário.

1. **Cadastro do Usuário:**
   ```bash
   curl --request POST \
     --url http://localhost:8080/api/auth/signup \
     --header 'Content-Type: application/json' \
     --data '{
       "username": "teste01",
       "email": "teste01@teste.com",
       "password": "123456"
     }'
   ```

2. **Autenticação do Usuário:**
   ```bash
   curl --request POST \
     --url http://localhost:8080/api/auth/login \
     --header 'Content-Type: application/json' \
     --data '{
       "username": "teste01",
       "password": "123456"
     }'
   ```

   O comando acima retorna um token JWT que será utilizado para acessar os endpoints protegidos.

## Endpoints Disponíveis

Após autenticar o usuário, você pode utilizar os seguintes endpoints:

1. **Candidatos por Estado:**
   ```bash
   curl --request GET \
     --url http://localhost:8080/v1/persons/candidates-by-state \
     --header 'Authorization: Bearer <seu-token-jwt>'
   ```

2. **Média de IMC por Faixa Etária:**
   ```bash
   curl --request GET \
     --url http://localhost:8080/v1/persons/average-imc-by-age-group \
     --header 'Authorization: Bearer <seu-token-jwt>'
   ```

3. **Porcentagem de Obesidade por Gênero:**
   ```bash
   curl --request GET \
     --url http://localhost:8080/v1/persons/obesity-percentage \
     --header 'Authorization: Bearer <seu-token-jwt>'
   ```

4. **Idade Média por Tipo Sanguíneo:**
   ```bash
   curl --request GET \
     --url http://localhost:8080/v1/persons/average-age-by-blood-type \
     --header 'Authorization: Bearer <seu-token-jwt>'
   ```

5. **Potenciais Doadores:**
   ```bash
   curl --request GET \
     --url http://localhost:8080/v1/persons/potential-donors \
     --header 'Authorization: Bearer <seu-token-jwt>'
   ```

## Considerações Finais

- Certifique-se de substituir `<seu-token-jwt>` pelo token retornado no processo de autenticação.
- Você pode testar os endpoints usando ferramentas como Insomnia, Postman, ou via linha de comando com cURL.
