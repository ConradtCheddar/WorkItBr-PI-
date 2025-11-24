# Padronização de Redimensionamento de Fontes

## 📋 Visão Geral

Este documento descreve a padronização implementada para o redimensionamento de fontes em todas as views do projeto WorkItBr.

## 🎯 Problema Resolvido

**Antes da padronização:**
- Código duplicado em 15+ views
- Valores mágicos inconsistentes (panelHeight / 20, / 25, / 30, / 35, / 40)
- Falta de padrão entre diferentes tipos de componentes
- Difícil manutenção e alteração dos tamanhos

**Depois da padronização:**
- Código centralizado em uma classe utilitária
- Categorias bem definidas de tamanhos
- Fácil manutenção e ajustes globais
- Consistência visual em toda a aplicação

---

## 🔧 Classe FontScaler

### Localização
`src/util/FontScaler.java`

### Categorias de Tamanho

A classe define 5 categorias de tamanho de fonte:

| Categoria | Uso | Divisor | Tamanho Mínimo |
|-----------|-----|---------|----------------|
| `TITULO` | Títulos principais ("Login", "Cadastro") | panelHeight / 20 | 16px |
| `SUBTITULO` | Subtítulos e labels importantes | panelHeight / 25 | 14px |
| `BOTAO` | Botões de ação | panelHeight / 25 | 14px |
| `TEXTO` | Texto padrão, campos de entrada | panelHeight / 35 | 12px |
| `PEQUENO` | Textos auxiliares, hints | panelHeight / 45 | 10px |

### Fórmula de Cálculo

```java
tamanhoFonte = Math.max(tamanhoMinimo, alturaDoPanel / divisor)
```

Isso garante que:
- As fontes escalam proporcionalmente ao tamanho da janela
- Nunca ficam muito pequenas (respeitam o mínimo)
- Mantêm proporção consistente entre diferentes tipos de texto

---

## 💡 Como Usar

### Uso Básico - Método Recomendado

```java
import util.FontScaler;
import util.FontScaler.FontSize;

public class MinhaView extends JPanel {
    private JLabel lblTitulo;
    private JTextField tfNome;
    private JButton btnSalvar;
    
    public MinhaView() {
        // ... criar e adicionar componentes ...
        
        // Aplicar redimensionamento automático
        FontScaler.addAutoResize(this,
            new Object[] {lblTitulo, FontSize.TITULO},
            new Object[] {tfNome, FontSize.TEXTO},
            new Object[] {btnSalvar, FontSize.BOTAO}
        );
    }
}
```

### Uso Manual (quando necessário)

```java
// Aplicar fonte escalável a um componente específico
FontScaler.applyScaledFont(meuComponente, panelHeight, FontSize.TEXTO);

// Criar uma fonte escalável personalizada
Font minhaFonte = FontScaler.createScaledFont(panelHeight, FontSize.TITULO, "Arial", Font.BOLD);

// Calcular apenas o tamanho
int tamanho = FontScaler.calculateFontSize(panelHeight, FontSize.TEXTO);
```

---

## 📁 Views Atualizadas

As seguintes views foram migradas para usar o `FontScaler`:

### ✅ Telas Principais
- `TelaCadastro.java` - Tela de cadastro de usuários
- `TelaLogin.java` - Tela de login
- `TelaCadastroContratante.java` - Cadastro de trabalhos
- `TelaConfigUser.java` - Configurações de usuário
- `TelaContratado.java` - Painel do contratado
- `TelaAdm.java` - Painel administrativo

### ✅ Views de Visualização
- `VisServico.java` - Visualização de serviço (contratado)
- `VisServicoCnte.java` - Visualização de serviço (contratante)
- `VisServicoCnteAceito.java` - Serviço aceito (contratante)
- `VisServicoAndamento.java` - Serviço em andamento
- `VisContratado.java` - Perfil do contratado

### ⚠️ Views com Comportamento Customizado
- `wbBarra.java` - Mantém lógica customizada devido a cálculos específicos de ícones

---

## 🎨 Guia de Boas Práticas

### 1. Escolha a Categoria Correta

```java
// ✅ CORRETO
FontScaler.addAutoResize(this,
    new Object[] {lblTitulo, FontSize.TITULO},      // Títulos grandes
    new Object[] {lblSubtitulo, FontSize.SUBTITULO}, // Subtítulos
    new Object[] {tfNome, FontSize.TEXTO},           // Campos de texto
    new Object[] {btnSalvar, FontSize.BOTAO},        // Botões
    new Object[] {lblHint, FontSize.PEQUENO}         // Textos auxiliares
);

// ❌ EVITE
new Object[] {lblTitulo, FontSize.TEXTO}  // Título muito pequeno
```

### 2. Agrupe Redimensionamentos

```java
// ✅ CORRETO - Uma chamada para todos os componentes
FontScaler.addAutoResize(this,
    new Object[] {comp1, FontSize.TEXTO},
    new Object[] {comp2, FontSize.TEXTO},
    new Object[] {comp3, FontSize.BOTAO}
);

// ❌ EVITE - Múltiplas chamadas separadas
FontScaler.addAutoResize(this, new Object[] {comp1, FontSize.TEXTO});
FontScaler.addAutoResize(this, new Object[] {comp2, FontSize.TEXTO});
```

### 3. Quando Usar Listeners Customizados

Use listeners customizados apenas quando:
- Precisa ajustar outros elementos além de fontes (ícones, imagens)
- Tem cálculos específicos não cobertos pelas categorias
- Necessita lógica condicional durante o redimensionamento

```java
// Exemplo: Componente com comportamento especial
addComponentListener(new ComponentAdapter() {
    @Override
    public void componentResized(ComponentEvent e) {
        // Aplicar FontScaler primeiro
        int h = getHeight();
        FontScaler.applyScaledFont(lblTitulo, h, FontSize.TITULO);
        
        // Depois aplicar lógica customizada
        ajustarIcones();
        reposicionarElementos();
    }
});
```

---

## 🔄 Migrando Views Antigas

### Passo a Passo

1. **Adicionar imports:**
```java
import util.FontScaler;
import util.FontScaler.FontSize;
```

2. **Identificar o listener existente:**
```java
// ANTES
addComponentListener(new ComponentAdapter() {
    @Override
    public void componentResized(ComponentEvent e) {
        int panelHeight = getHeight();
        int fontSizeTitulo = Math.max(16, panelHeight / 20);
        lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, fontSizeTitulo));
        // ... mais componentes ...
    }
});
```

3. **Substituir pelo FontScaler:**
```java
// DEPOIS
FontScaler.addAutoResize(this,
    new Object[] {lblTitulo, FontSize.TITULO}
    // ... mais componentes ...
);
```

---

## 📊 Benefícios da Padronização

### Manutenibilidade
- ✅ Mudanças centralizadas em um único arquivo
- ✅ Fácil ajustar proporções globalmente
- ✅ Código mais limpo e legível

### Consistência
- ✅ Mesma experiência visual em todas as telas
- ✅ Proporções padronizadas
- ✅ Comportamento previsível

### Performance
- ✅ Código otimizado
- ✅ Sem recalculações desnecessárias
- ✅ Listener único por painel

### Facilidade de Desenvolvimento
- ✅ Menos código para escrever
- ✅ Menos bugs relacionados a tamanhos
- ✅ Novos desenvolvedores aprendem um padrão

---

## 🛠️ Ajustando Proporções Globalmente

Para modificar os tamanhos em toda a aplicação, edite `FontScaler.java`:

```java
// Alterar divisores para fontes maiores/menores
private static final int DIVISOR_TITULO = 20;      // Diminuir = fontes maiores
private static final int DIVISOR_TEXTO = 35;       // Aumentar = fontes menores

// Alterar tamanhos mínimos
private static final int MIN_TITULO = 16;          // Aumentar = mínimo maior
private static final int MIN_TEXTO = 12;           // Diminuir = mínimo menor
```

---

## 📝 Exemplo Completo

```java
package view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import util.FontScaler;
import util.FontScaler.FontSize;

public class ExemploView extends JPanel {
    
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JButton btnSalvar;
    private JLabel lblHint;
    
    public ExemploView() {
        setLayout(new MigLayout("fill", "[grow]", "[][]20[][]20[][]"));
        
        // Criar componentes
        lblTitulo = new JLabel("Cadastro de Usuário");
        add(lblTitulo, "cell 0 0,alignx center");
        
        lblSubtitulo = new JLabel("Preencha os dados abaixo");
        add(lblSubtitulo, "cell 0 1,alignx center");
        
        tfNome = new JTextField();
        add(tfNome, "cell 0 2,growx");
        
        tfEmail = new JTextField();
        add(tfEmail, "cell 0 3,growx");
        
        btnSalvar = new JButton("Salvar");
        add(btnSalvar, "cell 0 4,alignx center");
        
        lblHint = new JLabel("* Todos os campos são obrigatórios");
        add(lblHint, "cell 0 5,alignx center");
        
        // Aplicar redimensionamento padronizado
        FontScaler.addAutoResize(this,
            new Object[] {lblTitulo, FontSize.TITULO},
            new Object[] {lblSubtitulo, FontSize.SUBTITULO},
            new Object[] {tfNome, FontSize.TEXTO},
            new Object[] {tfEmail, FontSize.TEXTO},
            new Object[] {btnSalvar, FontSize.BOTAO},
            new Object[] {lblHint, FontSize.PEQUENO}
        );
    }
}
```

---

## 📞 Suporte

Para dúvidas ou sugestões sobre a padronização, consulte:
- Código fonte: `src/util/FontScaler.java`
- Exemplos: Qualquer view em `src/view/`
- Este documento: `PADRONIZACAO_REDIMENSIONAMENTO.md`

---

**Última atualização:** 2025-11-04  
**Versão:** 1.0
