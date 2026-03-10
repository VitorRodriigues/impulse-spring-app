# 🎟️ API de Gerenciamento de Eventos e Venda de Ingressos

Este projeto foi desenvolvido como **teste prático para processo seletivo**, com o objetivo de demonstrar conhecimentos em **Java 17**, **Spring Boot**, **API REST**, **arquitetura em camadas**, **validação de dados**, **regras de negócio** e **testes automatizados**.

A aplicação consiste em uma **API para gerenciamento de eventos e venda de ingressos**, permitindo cadastrar eventos, registrar participantes e realizar a compra de ingressos respeitando a capacidade máxima de cada evento.

---

# 🛠 Tecnologias Utilizadas

* Java 17
* Spring Boot
* Maven
* H2 Database
* Spring Data JPA
* Bean Validation
* JUnit 5
* Mockito
* **Optimistic Lock (JPA)** para controle de concorrência

---

# 🏗 Arquitetura do Projeto

O projeto segue uma **arquitetura em camadas**, separando responsabilidades para facilitar manutenção, testes e evolução da aplicação.

```
src
 ├── main
 │   ├── java
 │   │   └── com.example.eventapi
 │   │       ├── controller
 │   │       ├── service
 │   │       ├── repository
 │   │       ├── model
 │   │       ├── dto
 │   │       └── exception
 │   └── resources
 │       └── application.properties
 └── test
```

### Camadas da aplicação

**Controller**

Responsável por expor os endpoints da API REST.

**Service**

Contém as regras de negócio da aplicação.

**Repository**

Responsável pelo acesso ao banco de dados utilizando Spring Data JPA.

**Model**

Representa as entidades persistidas no banco de dados.

**DTO**

Objetos utilizados para entrada e saída de dados da API, evitando expor diretamente as entidades.

**Exception**

Tratamento centralizado de exceções e erros de negócio.

---

# 📌 Funcionalidades Implementadas

## 🐣 Nível Junior — Fundamentos e API REST

### CRUD de Eventos

Operações disponíveis para gerenciamento de eventos:

* Criar evento
* Listar eventos
* Buscar evento por ID
* Atualizar evento
* Deletar evento

### Estrutura do Evento

| Campo      | Descrição                          |
| ---------- | ---------------------------------- |
| nome       | Nome do evento                     |
| data       | Data do evento                     |
| local      | Local do evento                    |
| capacidade | Quantidade máxima de participantes |

### Validações

Utilizando **Bean Validation** foram implementadas as seguintes regras:

* O **nome do evento não pode ser vazio**
* A **data do evento não pode estar no passado**

### Boas práticas REST

A API utiliza corretamente **verbos HTTP** e **status codes**.

| Status Code | Descrição                        |
| ----------- | -------------------------------- |
| 200         | Requisição realizada com sucesso |
| 201         | Recurso criado                   |
| 204         | Recurso removido                 |
| 400         | Requisição inválida              |
| 404         | Recurso não encontrado           |

---

# 🚀 Nível Pleno — Regras de Negócio e Testes

Além dos requisitos do nível anterior, foram implementadas funcionalidades relacionadas à **venda de ingressos**.

## 👤 Participantes

Foi criada a entidade **Participante**, contendo:

| Campo | Descrição             |
| ----- | --------------------- |
| nome  | Nome do participante  |
| email | Email do participante |

---

## 🎟 Venda de Ingressos

A API permite que um participante realize a **compra de ingresso para um evento**.

### Regras de Negócio

Antes de confirmar uma compra, o sistema verifica:

* Se o **evento existe**
* Se ainda há **capacidade disponível**

Após a compra:

* A **capacidade do evento é decrementada**

Caso o evento esteja lotado, a API retorna erro informando que **não há mais vagas disponíveis**.

---

# 🔒 Controle de Concorrência

Para evitar problemas em cenários onde **vários usuários tentam comprar ingressos simultaneamente**, foi implementado **Optimistic Lock utilizando JPA**.

Essa abordagem garante que:

* Dois usuários não consigam comprar a **última vaga ao mesmo tempo**
* O sistema detecte conflitos de atualização no banco
* A integridade da capacidade do evento seja preservada

A implementação utiliza o mecanismo de **versionamento de entidades** através da anotação:

```
@Version
```

Dessa forma, caso ocorra uma tentativa de atualização concorrente, o sistema detecta o conflito e pode tratar a exceção adequadamente.

---

# 📜 Histórico de Compras

A API disponibiliza um endpoint para:

* Listar todos os **ingressos comprados por um participante**

---

# 📡 Endpoints da API

## Eventos

### Criar evento

```
POST /eventos
```

Exemplo de requisição:

```json
{
  "nome": "Java Conference",
  "data": "2026-05-10",
  "local": "São Paulo",
  "capacidade": 100
}
```

---

### Listar eventos

```
GET /eventos
```

---

### Buscar evento por ID

```
GET /eventos/{id}
```

---

### Atualizar evento

```
PUT /eventos/{id}
```

---

### Deletar evento

```
DELETE /eventos/{id}
```

---

## Participantes

### Criar participante

```
POST /participantes
```

---

## Compra de Ingressos

### Comprar ingresso

```
POST /ingressos
```

Exemplo:

```json
{
  "eventoId": 1,
  "participanteId": 2
}
```

---

## Histórico de ingressos

```
GET /participantes/{id}/ingressos
```

---

# 🧪 Testes Automatizados

Foram implementados **testes unitários utilizando JUnit e Mockito**, focados na **Service de compra de ingressos**.

Os testes garantem:

* Compra permitida quando há capacidade
* Bloqueio quando o evento está lotado
* Validação correta da lógica de decremento de vagas

Executar testes:

```
mvn test
```

---

# 💾 Banco de Dados

O projeto utiliza **H2 Database**, um banco em memória ideal para desenvolvimento e testes.

Configuração:

```
jdbc:h2:mem:eventdb
```

Console do H2:

```
http://localhost:8080/h2-console
```

---

# ▶ Como Executar o Projeto

### Clonar o repositório

```
git clone https://github.com/seu-usuario/event-api.git
```

### Entrar no diretório

```
cd event-api
```

### Compilar o projeto

```
mvn clean install
```

### Executar a aplicação

```
mvn spring-boot:run
```

A API ficará disponível em:

```
http://localhost:8080
```

---

# 🔮 Melhorias Futuras

Possíveis evoluções do projeto:

* Autenticação com **Spring Security**
* Banco de dados **PostgreSQL**
* Containerização com **Docker**

---

# 👨‍💻 Considerações Finais

Este projeto foi desenvolvido com foco em demonstrar:

* boas práticas no desenvolvimento de APIs REST
* organização em arquitetura em camadas
* validação de dados
* implementação de regras de negócio
* controle de concorrência com **Optimistic Lock**
* qualidade de código através de **testes automatizados**
