# Regras de Negócio - Package (Encomenda)

Este documento descreve as regras de negócio da entidade `PackageEntity` do sistema de portaria de condomínio.

O sistema controla encomendas recebidas na portaria e entregues aos moradores.

---

# Contexto do Domínio

Entidades existentes no sistema:

User → usuário do sistema (ADMIN ou PORTEIRO)

Unit → unidade / apartamento do condomínio

Resident → morador que reside em uma unidade

PackageEntity → encomenda recebida na portaria

Relacionamentos:

Resident pertence a uma Unit

PackageEntity pertence a um Resident

PackageEntity registra qual User recebeu a encomenda

PackageEntity registra qual User entregou a encomenda

---

# Ciclo de Vida da Encomenda

Uma encomenda pode passar pelos seguintes estados:

RECEIVED → STORED → PICKED_UP

ou

RECEIVED → STORED → CANCELED

---

# Enum de Status da Encomenda

A aplicação deve suportar os seguintes status:

RECEIVED  
STORED  
PICKED_UP  
CANCELED

---

# Regras de Negócio

## 1. Toda encomenda deve ter um morador

Uma encomenda obrigatoriamente deve estar vinculada a um `Resident`.

Antes de salvar a encomenda o sistema deve validar se o morador existe.

---

## 2. Deve registrar quem recebeu a encomenda

Ao cadastrar uma encomenda, o sistema deve registrar qual `User` recebeu a encomenda na portaria.

Campos obrigatórios nesse momento:

receivedBy  
receivedAt

O campo `receivedAt` deve ser gerado automaticamente com a data e hora atual.

Exemplo:

receivedAt = LocalDateTime.now()

---

## 3. Campos obrigatórios

Uma encomenda deve possuir obrigatoriamente:

description  
sender  
resident  
receivedBy

---

## 4. Estado inicial da encomenda

Ao criar uma nova encomenda:

status = RECEIVED  
receivedAt = data atual  
pickedUpAt = null  
deliveredBy = null

---

## 5. Regras de retirada da encomenda

Uma encomenda só pode ser retirada se:

status != PICKED_UP  
status != CANCELED

Ao retirar a encomenda:

status deve mudar para PICKED_UP  
pickedUpAt deve receber a data atual  
deliveredBy deve registrar o usuário que realizou a entrega

---

## 6. Não permitir retirada duplicada

Uma encomenda não pode ser retirada duas vezes.

Se status == PICKED_UP o sistema deve lançar exceção de regra de negócio.

---

## 7. Regras de cancelamento

Uma encomenda só pode ser cancelada se ainda não tiver sido retirada.

Se status == PICKED_UP o cancelamento não é permitido.

Ao cancelar:

status = CANCELED

---

## 8. Não deletar encomendas do banco

Encomendas não devem ser removidas fisicamente do banco de dados.

Para manter histórico do sistema, o correto é alterar o status para:

CANCELED

---

# Responsabilidades do PackageService

O `PackageService` deve implementar os seguintes métodos:

createPackage  
pickupPackage  
cancelPackage  
findById

O serviço deve:

validar se o Resident existe  
validar se o User existe  
aplicar as regras de negócio definidas neste documento  
lançar exceções quando regras forem violadas

---

# Boas práticas de implementação

Usar Spring Boot

Usar Spring Data JPA

Usar métodos @Transactional no service

Manter controllers sem lógica de negócio

Toda regra deve estar no Service