# BancoDigitalEduc360

Este projeto foi desenvolvido como parte do **desafio final do curso de Java da Educ360**. O objetivo era criar um **mini banco digital** com funcionalidades completas para gerenciar **clientes, contas bancárias, cartões e seguros**, utilizando tecnologias modernas e boas práticas de desenvolvimento.

---

## 📌 Descrição do Projeto

O sistema foi projetado para atender às necessidades de uma instituição financeira, permitindo:

- Gerenciamento de **clientes**, **contas bancárias** e **cartões**.
- Funcionalidades avançadas como **transferências via Pix**, emissão de **apólices de seguro** e controle de **limites de transação**.

A aplicação foi desenvolvida como uma **API REST** utilizando **Spring Boot**, com persistência de dados em um **banco de dados relacional (MySQL)**.

---

## ⚙️ Funcionalidades Implementadas

### 1. Cadastro de Clientes

- Dados: **CPF**, **Nome**, **Data de Nascimento** e **Endereço**.
- Categorias:
  - **Comum**
  - **Super**
  - **Premium**

### 2. Gerenciamento de Contas

- Tipos de conta:
  - **Conta Corrente**:
    - Taxa mensal de manutenção.
    - Permite transferências via **Pix**.
  - **Conta Poupança**:
    - Rendimentos mensais com base na categoria do cliente.
    - Permite transferências via **Pix**.

### 3. Emissão de Cartões

- **Cartão de Crédito**:
  - Limite de crédito conforme categoria:
    - Comum: R$ 1.000,00
    - Super: R$ 5.000,00
    - Premium: R$ 10.000,00
  - Bloqueio ao atingir o limite.
  - Troca de senha e ativação/desativação.

- **Cartão de Débito**:
  - Limite diário de transação.
  - Bloqueio após atingir o limite diário.
  - Permite alteração do limite pelo usuário.

### 4. Seguros de Cartão de Crédito

- **Seguro Viagem**:
  - Gratuito para clientes Premium.
  - Opcional para Comum e Super (R$ 50,00/mês).

- **Seguro de Fraude**:
  - Cobertura automática para todos os cartões.
  - Apólice base de R$ 5.000,00.
  - Geração automática de apólices eletrônicas com:
    - Número da apólice
    - Data de contratação
    - Detalhes do cartão
    - Valor e condições de acionamento

---

## 🧠 Como Desenvolvi o Projeto

### 1. Planejamento

- Entendimento dos requisitos.
- Escolha de tecnologias:
  - **Spring Boot** pela robustez.
  - **MySQL** como banco de dados.
- Estruturação em camadas:
  - **Controller**
  - **Endity**
  - **Enuns**
  - **Service**
  - **Repository**

### 2. Modelagem do Banco de Dados

- Entidades principais:
  - **Cliente**, **Conta**, **Cartão** e **Seguro**.
- Relacionamentos:
  - Um cliente pode ter várias contas.
  - Uma conta pode ter vários cartões.
  - Um cartão pode ter um ou mais seguros.

### 3. Implementação

- **Backend**:
  - API REST com **Spring Boot**.
  - CRUD com **Spring Data JPA**.
  - Validações com **Jakarta Validation**.


- **Regras de Negócio**:
  - Cálculo de taxas, limites e rendimentos.
  - Controle de status dos cartões.
  - Geração de apólices automáticas.

### 4. Testes

- **Thunder Client** para testes de endpoints.
- Testes de integração entre as camadas.

### 5. Documentação

- Criação do `README.md`.
- Instruções de instalação e exemplos de uso.

---

## 🛠 Tecnologias Utilizadas

- Java 17  
- Spring Boot  
- Spring Data JPA  
- Spring Security (JWT)  
- MySQL  
- Maven  
- Thunder Client  

---

## ✅ Conclusão

Este projeto foi uma excelente oportunidade para aplicar os conhecimentos adquiridos no curso de Java da **Educ360**. Ele envolveu:

- Modelagem de dados
- Implementação de regras de negócio complexas


O resultado foi um sistema **robusto e escalável**, preparado para atender às demandas reais de uma instituição financeira.

---

# 🚀 Como Rodar o Projeto BancoDigitalEduc360 Localmente

Siga os passos abaixo para clonar, configurar e executar o projeto em sua máquina.

---

## ✅ Pré-requisitos

Certifique-se de ter os seguintes softwares instalados:

- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven](https://maven.apache.org/download.cgi)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)
- [Thunder client](https://www.postman.com/downloads/) (opcional para testar a API)
- Uma IDE como [IntelliJ IDEA](https://www.jetbrains.com/idea/) ou [Eclipse](https://www.eclipse.org/downloads/)

---

## 📥 Passo 1 – Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/BancoDigitalEduc360.git
cd BancoDigitalEduc360
```

## 📥  Passo 2 – Configurar o Banco de Dados MySQL

- Acesse o MySQL Workbench ou terminal.

- Crie o banco de dados com o seguinte comando:
```bash
CREATE DATABASE bancodigital;
```

- No arquivo src/main/resources/application.properties, configure com seu usuário e senha do MySQL:  
```bash
# Configuração do banco de dados MySQL
spring.datasource.url=jdbc:mysql://localhost:3306/bancodigital?useSSL=false&serverTimezone=UTC
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuração do Hibernate para gerenciar o esquema do banco de dados
spring.jpa.hibernate.ddl-auto=update

# Mostrar as consultas SQL no console
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```
## 📥  Passo 3 – Compilar o Projeto com Maven
```bash
mvn clean install
```
## 📥  Passo 4 – Executar a Aplicação
- Execute o projeto com:
```bash
mvn spring-boot:run
```
- Ou abra na sua IDE e execute a classe principal (a que contém @SpringBootApplication). A API estará disponível em:
```bash
http://localhost:8080
``` 

## 📥  Passo 5 – Testar os Endpoints da API
- Você pode usar o Postman ou Thunder Client para testar os endpoints.

Exemplos:
```bash
                                          Cliente

- **POST /clientes - Criar um novo cliente
- **GET /clientes/{id} - Obter detalhes de um cliente
- **PUT /clientes/{id} - Atualizar informações de um cliente
- **DELETE /clientes/{id} - Remover um cliente
- **GET /clientes - Listar todos os clientes
``` 
```bash
                                          Conta

POST /contas - Criar uma nova conta
GET /contas/{id} - Obter detalhes de uma conta
POST /contas/{id}/transferencia - Realizar uma transferência entre contas
GET /contas/{id}/saldo - Consultar saldo da conta
POST /contas/{id}/pix - Realizar um pagamento via Pix
POST /contas/{id}/deposito - Realizar um depósito na conta
POST /contas/{id}/saque - Realizar um saque da conta
PUT /contas/{id}/manutencao - Aplicar taxa de manutenção mensal (para conta
corrente)
PUT /contas/{id}/rendimentos - Aplicar rendimentos (para conta poupança)
``` 
```bash
                                          Cartão
POST /cartoes - Emitir um novo cartão
GET /cartoes/{id} - Obter detalhes de um cartão
POST /cartoes/{id}/pagamento - Realizar um pagamento com o cartão
PUT /cartoes/{id}/limite - Alterar limite do cartão
PUT /cartoes/{id}/status - Ativar ou desativar um cartão
PUT /cartoes/{id}/senha - Alterar senha do cartão
GET /cartoes/{id}/fatura - Consultar fatura do cartão de crédito
POST /cartoes/{id}/fatura/pagamento - Realizar pagamento da fatura do cartão
de crédito
PUT /cartoes/{id}/limite-diario - Alterar limite diário do cartão de débito
``` 
```bash
                                          Seguro 
POST /seguros - Contratar um seguro
GET /seguros/{id} - Obter detalhes de uma apólice de seguro
GET /seguros - Listar todos os seguros disponíveis
PUT /seguros/{id}/cancelar- Cancelar uma apólice de seguro
``` 





