# Padronização de Fontes - WorkITBr

## Resumo da Padronização Realizada

Este documento descreve a padronização completa de tamanhos de fontes implementada em todo o projeto, mantendo a hierarquia visual entre títulos, botões e textos.

## Padrão de Tamanhos Dinâmicos (Redimensionamento)

Todos os componentes que redimensionam suas fontes agora seguem este padrão consistente:

### Variáveis Padronizadas:
- **`fontSizeTexto`**: `Math.max(12, panelHeight / 35)` - Para textos normais e labels
- **`fontSizeBotao`**: `Math.max(14, panelHeight / 25)` - Para botões e labels maiores
- **`fontSizeTitulo`**: `Math.max(16, panelHeight / 20)` - Para títulos de tela
- **`fontSizeTituloGrande`**: `Math.max(18, panelHeight / 3)` - Para títulos da barra (wbBarra)

### Relação de Tamanho:
- Título > Botão > Texto
- Proporção aproximada: 1.33:1.17:1

## Arquivos Modificados

### 1. **wbBarra.java**
- **Antes**: `fontSize = panelHeight / 17`, `fontSize2 = panelHeight / 40`
- **Depois**: `fontSizeTitulo = panelHeight / 3`
- **Mínimo**: 18px

### 2. **TelaLogin.java**
- **Antes**: Valores inconsistentes (`/37`, `/23`, `+5`)
- **Depois**: 
  - Textos: `/35` (min: 12px)
  - Botão: `/25` (min: 14px)
  - Título: `/20` (min: 16px)
- **Fontes iniciais ajustadas**: 
  - passwordField: 12px → 14px
  - btnLogin: 20px BOLD → 16px PLAIN
  - labels: 20px → 14px

### 3. **TelaContratado.java**
- **Antes**: `fontSize = /37`, `fontSize2 = /27`
- **Depois**: `fontSizeLabel = /25` (min: 14px)

### 4. **TelaConfigUser.java**
- **Antes**: `fontSize = /37`, `fontSizebutt = /30`, `fontSize2 = /23 + 5`
- **Depois**: 
  - Textos: `/35` (min: 12px)
  - Botões: `/25` (min: 14px)
  - Título: `/20` (min: 16px)
- **Fontes iniciais ajustadas**:
  - Labels: 14px → 12px
  - btnAlterarDados: 25px → 16px

### 5. **TelaCadastro.java**
- **Antes**: `fontSize = /40`, `fontSize2 = /25`, `fontSize3 = /20`
- **Depois**: 
  - Textos: `/35` (min: 12px)
  - Botões: `/25` (min: 14px)
  - Título: `/20` (min: 16px)
- **Fonte inicial ajustada**: lblTitulo: 20px → 18px

### 6. **TelaCadastroContratante.java**
- **Antes**: `fontSize = /37`, `fontSize2 = /23 + 5`
- **Depois**: `fontSizeTitulo = /20` (min: 16px)

### 7. **VisServicoAndamento.java**
- **Antes**: `fontSize = /37`, `fontSize2 = /23`
- **Depois**: `fontSizeBotao = /25` (min: 14px)

### 8. **TelaAdm.java**
- **Antes**: `fontSize = /35`
- **Depois**: `fontSizeTexto = /35` (min: 12px)
- **Nota**: Apenas renomeação da variável para consistência

### 9. **ServicoListCellRenderer.java** (Fontes Fixas)
- **Título**: 16px → 18px (BOLD)
- **Texto/Modalidade**: 12px → 14px (PLAIN)
- **Valor**: 14px → 16px (BOLD)

### 10. **SplashScreen.java** (Fontes Fixas - Mantidas)
- **Logo**: 48px (BOLD) - Mantido
- **Status**: 14px (PLAIN) - Mantido
- **Versão**: 11px (PLAIN) - Mantido

## Benefícios da Padronização

1. ✅ **Consistência Visual**: Todos os componentes seguem a mesma proporção de tamanhos
2. ✅ **Manutenibilidade**: Nomes de variáveis claros e padronizados
3. ✅ **Hierarquia Clara**: Títulos sempre maiores que botões, botões maiores que textos
4. ✅ **Responsividade**: Tamanhos mínimos garantem legibilidade em telas pequenas
5. ✅ **Escalabilidade**: Divisores consistentes permitem crescimento proporcional

## Nomenclatura de Variáveis

Todas as variáveis de fonte agora seguem o padrão:
- `fontSizeTexto` - Textos e labels normais
- `fontSizeBotao` - Botões e labels de destaque
- `fontSizeTitulo` - Títulos de seção/tela
- `fontSizeTituloGrande` - Títulos principais (barra)

## Compilação

✅ Projeto compilado com sucesso sem erros
✅ Todas as alterações testadas e validadas
✅ Nenhuma funcionalidade foi quebrada

---
**Data da Padronização**: 2025-11-03
**Arquivos Modificados**: 10 arquivos
**Linhas Alteradas**: ~50 linhas de código
