# Validações de Campos Implementadas

## Resumo das Mudanças

Este documento descreve as melhorias implementadas no sistema de validação de campos de formulário.

## O que foi removido

1. **Máscaras antigas com MaskFormatter** - As máscaras baseadas em `MaskFormatter` foram completamente removidas de:
   - `TelaCadastro.java`
   - Métodos `aplicarMascaraCPF()` e `aplicarMascaraTelefone()` foram removidos
   - Imports de `MaskFormatter` e `JFormattedTextField` foram removidos

2. **Campos JFormattedTextField** foram substituídos por **JTextField** normais com formatação inteligente

## O que foi implementado

### 1. Nova Classe: `util/FieldValidator.java`

Esta classe utilitária fornece:

#### Métodos de Validação:
- **`validarCPF(String cpf)`** - Valida CPF usando o algoritmo oficial com dígitos verificadores
- **`validarCNPJ(String cnpj)`** - Valida CNPJ usando o algoritmo oficial com dígitos verificadores
- **`validarEmail(String email)`** - Valida formato de email usando regex
- **`validarTelefone(String telefone)`** - Valida telefone (aceita 10 ou 11 dígitos)

#### Métodos de Formatação:
- **`formatarCPF(String cpf)`** - Formata CPF para XXX.XXX.XXX-XX
- **`formatarCNPJ(String cnpj)`** - Formata CNPJ para XX.XXX.XXX/XXXX-XX
- **`formatarTelefone(String telefone)`** - Formata telefone para (XX) XXXXX-XXXX ou (XX) XXXX-XXXX
- **`removerFormatacao(String valor)`** - Remove toda formatação, deixando apenas números

#### DocumentFilters (Formatação Automática):
- **`CPFDocumentFilter`** - Aplica formatação automática de CPF enquanto o usuário digita
- **`CNPJDocumentFilter`** - Aplica formatação automática de CNPJ enquanto o usuário digita
- **`TelefoneDocumentFilter`** - Aplica formatação automática de telefone enquanto o usuário digita

### 2. Atualizações em `view/TelaCadastro.java`

**Mudanças:**
- Campos `tfEmail`, `tfTelefone` e `tfCPF` agora são `JTextField` (não mais `JFormattedTextField`)
- Aplicação automática de formatação usando DocumentFilters:
  ```java
  ((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());
  ((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CPFDocumentFilter());
  ```
- Removidos listeners de foco desnecessários
- Placeholder atualizado para "CPF ou CNPJ"

### 3. Atualizações em `controller/CadastroController.java`

**Validações adicionadas antes do cadastro:**
1. **Validação de Email** - Verifica se o email tem formato válido
2. **Validação de Telefone** - Verifica se tem 10 ou 11 dígitos
3. **Validação de CPF/CNPJ** - Verifica:
   - Se tem 11 dígitos, valida como CPF
   - Se tem 14 dígitos, valida como CNPJ
   - Usa algoritmos oficiais de validação com dígitos verificadores

**Mensagens de erro específicas:**
- "Email inválido!"
- "Telefone inválido! Deve ter 10 ou 11 dígitos."
- "CPF inválido!"
- "CNPJ inválido!"
- "CPF/CNPJ inválido! Digite 11 dígitos para CPF ou 14 para CNPJ."

### 4. Atualizações em `view/TelaConfigUser.java`

**Mudanças:**
- Aplicação de DocumentFilters para formatação automática de telefone e CPF
- Import de `FieldValidator` para uso dos filtros

### 5. Atualizações em `controller/TelaConfigUserController.java`

**Validações adicionadas antes da atualização:**
- Mesmas validações do `CadastroController`
- Validação de email, telefone e CPF/CNPJ antes de salvar alterações

## Vantagens da Nova Implementação

### 1. Validação Real
- ✅ **CPF e CNPJ são validados de verdade** usando os algoritmos oficiais
- ✅ Não aceita sequências repetidas (111.111.111-11, etc.)
- ✅ Verifica dígitos verificadores calculados matematicamente

### 2. Formatação Inteligente
- ✅ Formata automaticamente enquanto o usuário digita
- ✅ Não permite digitar mais caracteres que o necessário
- ✅ Remove caracteres automaticamente ao apagar

### 3. Experiência do Usuário
- ✅ Feedback imediato com mensagens de erro específicas
- ✅ Formatação visual sem interferir na digitação
- ✅ Suporte para CPF ou CNPJ no mesmo campo

### 4. Manutenibilidade
- ✅ Código centralizado na classe `FieldValidator`
- ✅ Fácil reutilização em outras partes do sistema
- ✅ Fácil adicionar novas validações

## Como Usar em Novos Campos

### Aplicar formatação automática:

```java
// Para CPF
((AbstractDocument) tfCPF.getDocument()).setDocumentFilter(new FieldValidator.CPFDocumentFilter());

// Para CNPJ
((AbstractDocument) tfCNPJ.getDocument()).setDocumentFilter(new FieldValidator.CNPJDocumentFilter());

// Para Telefone
((AbstractDocument) tfTelefone.getDocument()).setDocumentFilter(new FieldValidator.TelefoneDocumentFilter());
```

### Validar campos:

```java
// Validar CPF
if (!FieldValidator.validarCPF(cpf)) {
    JOptionPane.showMessageDialog(null, "CPF inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
    return;
}

// Validar Email
if (!FieldValidator.validarEmail(email)) {
    JOptionPane.showMessageDialog(null, "Email inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
    return;
}

// Validar Telefone
if (!FieldValidator.validarTelefone(telefone)) {
    JOptionPane.showMessageDialog(null, "Telefone inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
    return;
}
```

## Exemplos de Validação

### CPF Válido:
- ✅ 123.456.789-09
- ✅ 111.444.777-35

### CPF Inválido:
- ❌ 111.111.111-11 (sequência repetida)
- ❌ 123.456.789-00 (dígitos verificadores errados)

### Telefone Válido:
- ✅ (11) 98765-4321 (celular - 11 dígitos)
- ✅ (11) 3456-7890 (fixo - 10 dígitos)

### Email Válido:
- ✅ usuario@exemplo.com
- ✅ nome.sobrenome@empresa.com.br

### Email Inválido:
- ❌ usuario@
- ❌ @exemplo.com
- ❌ usuario.exemplo.com

## Status da Compilação

✅ **Projeto compilado com sucesso!**

Apenas warnings informativos sobre uso de APIs depreciadas e operações unchecked em outras partes do código (não relacionadas às mudanças).

## Próximos Passos Sugeridos

1. Testar o cadastro com CPFs válidos e inválidos
2. Testar o cadastro com emails e telefones inválidos
3. Testar a tela de configuração com as validações
4. Considerar adicionar validação de senha forte (tamanho mínimo, caracteres especiais, etc.)
5. Considerar adicionar validação de campos obrigatórios com destaque visual
