// Define o pacote onde esta classe está localizada
package controller;

// Importa HashMap para estrutura de dados chave-valor
import java.util.HashMap;
// Importa Map, interface para mapas
import java.util.Map;
// Importa JPanel, componente painel Swing
import javax.swing.JPanel;
// Importa a classe Servico que representa um serviço no sistema
import model.Servico;
// Importa ServicoDAO, responsável por operações de banco de dados relacionadas a serviços
import model.ServicoDAO;
// Importa a classe Usuario que representa um usuário no sistema
import model.Usuario;
// Importa UsuarioDAO, responsável por operações de banco de dados relacionadas a usuários
import model.UsuarioDAO;
// Importa todas as classes de view (visualização)
import view.*;

/**
 * Fábrica responsável por criar e gerenciar telas dinâmicas de visualização.
 * <p>
 * Cada método cria uma nova instância de tela (ou recria uma existente) para
 * garantir que os dados exibidos estejam sempre atualizados. A factory também
 * delega ao {@link Navegador} a responsabilidade de registrar/remover painéis
 * no CardLayout principal.
 * </p>
 */
public class TelaFactory {
    // Referência ao navegador que controla a navegação entre telas
    private final Navegador navegador;
    // Referência ao DAO de serviços para operações de banco de dados
    private final ServicoDAO servicoDAO;
    // Referência ao DAO de usuários para operações de banco de dados
    private final UsuarioDAO usuarioDAO;
    
    /**
     * Construtor que inicializa a fábrica com suas dependências.
     * 
     * @param navegador objeto que controla a navegação
     * @param servicoDAO DAO de serviços
     * @param usuarioDAO DAO de usuários
     */
    public TelaFactory(Navegador navegador, ServicoDAO servicoDAO, UsuarioDAO usuarioDAO) {
        // Atribui a referência do navegador recebido ao atributo da classe
        this.navegador = navegador;
        // Atribui a referência do DAO de serviços recebido ao atributo da classe
        this.servicoDAO = servicoDAO;
        // Atribui a referência do DAO de usuários recebido ao atributo da classe
        this.usuarioDAO = usuarioDAO;
    }
    
    /**
     * Cria (ou recria) uma tela de visualização de serviço (para contratado).
     * Remove qualquer painel anterior com o mesmo nome para evitar stale UI.
     * @param servico serviço a ser exibido
     * @return nome do painel registrado no navegador
     */
    public String criarVisServico(Servico servico) {
        // Gera um nome único para o painel baseado no ID do serviço
        String panelName = "VIS_SERVICO_" + servico.getIdServico();
        
        // Remove painel antigo se existir (dados podem ter mudado)
        // Garante que sempre exiba dados atualizados
        navegador.removerPainel(panelName);
        
        // Cria nova tela com dados atualizados
        // Cria a view de visualização do serviço
        VisServico view = new VisServico(servico);
        // Cria o controller para gerenciar as ações da view
        VisServicoController controller = new VisServicoController(view, servicoDAO, navegador, servico);
        
        // Adiciona o painel ao navegador para que possa ser exibido
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Cria (ou recria) uma tela de visualização de serviço em andamento.
     */
    public String criarVisServicoAndamento(Servico servico) {
        // Gera um nome único para o painel baseado no ID do serviço
        String panelName = "VIS_SERVICO_ANDAMENTO_" + servico.getIdServico();
        
        // Remove painel antigo se existir para garantir dados atualizados
        navegador.removerPainel(panelName);
        
        // Cria a view de visualização de serviço em andamento
        VisServicoAndamento view = new VisServicoAndamento(servico);
        // Cria o controller para gerenciar as ações da view
        VisServicoAndamentoController controller = new VisServicoAndamentoController(view, servicoDAO, navegador, servico);
        
        // Adiciona o painel ao navegador
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Cria (ou recria) a visualização de serviço para o contratante (não aceito).
     */
    public String criarVisServicoCnte(Servico servico) {
        // Gera um nome único para o painel baseado no ID do serviço
        String panelName = "VIS_SERVICO_CNTE_" + servico.getIdServico();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria a view de visualização de serviço para o contratante
        VisServicoCnte view = new VisServicoCnte(servico);
        // Cria o controller para gerenciar as ações da view
        VisServicoCnteController controller = new VisServicoCnteController(view, servicoDAO, navegador, servico);
        
        // Adiciona o painel ao navegador
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Cria (ou recria) a visualização de serviço aceito para o contratante.
     */
    public String criarVisServicoCnteAceito(Servico servico) {
        // Gera um nome único para o painel baseado no ID do serviço
        String panelName = "VIS_SERVICO_CNTE_ACEITO_" + servico.getIdServico();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria a view de visualização de serviço aceito para o contratante
        VisServicoCnteAceito view = new VisServicoCnteAceito(servico);
        // Cria o controller, passando também a própria factory para permitir navegação para outras telas
        VisServicoCnteAceitoController controller = new VisServicoCnteAceitoController(view, servicoDAO, navegador, servico, this);
        
        // Adiciona o painel ao navegador
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Cria (ou recria) a visualização de um contratado específico.
     * @param usuario usuário cujo perfil será exibido
     * @param telaPreviaRetorno nome da tela para onde voltar ao pressionar voltar
     */
    public String criarVisContratado(Usuario usuario, String telaPreviaRetorno) {
        // Gera um nome único para o painel baseado no ID do usuário
        String panelName = "VIS_CONTRATADO_" + usuario.getIdUsuario();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria a view de visualização do perfil do contratado
        VisContratado view = new VisContratado(usuario);
        
        // Configura botão voltar para retornar à tela previa informada
        // Define a ação do botão voltar para navegar para a tela especificada
        view.voltar(e -> navegador.navegarPara(telaPreviaRetorno));
        
        // Adiciona o painel ao navegador
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Cria a tela de configuração do usuário. Sempre recria para garantir dados atualizados.
     * @param usuario usuário que terá sua configuração exibida
     * @return nome do painel configurado
     */
    public String criarTelaConfigUser(Usuario usuario) {
        // Nome fixo do painel (sempre o mesmo para configuração de usuário)
        String panelName = "CONFIG_USER";
        
        // Sempre remove e recria para garantir dados atualizados do banco
        navegador.removerPainel(panelName);
        
        // Recarrega dados do usuário do banco
        // Busca os dados mais recentes do usuário no banco de dados
        Usuario usuarioAtualizado = usuarioDAO.getUsuarioById(usuario.getIdUsuario());
        // Se o usuário foi encontrado no banco
        if (usuarioAtualizado != null) {
            // Atualiza a referência local com os dados do banco
            usuario = usuarioAtualizado;
            // Atualiza o usuário atual no navegador
            navegador.setCurrentUser(usuarioAtualizado);
        }
        
        // Cria a view da tela de configuração
        TelaConfigUser view = new TelaConfigUser();
        // Cria o controller para gerenciar as ações da view
        TelaConfigUserController controller = new TelaConfigUserController(view, usuarioDAO, navegador, usuario);
        
        // Adiciona o painel ao navegador
        navegador.adicionarPainel(panelName, view);
        // Retorna o nome do painel criado
        return panelName;
    }
    
    /**
     * Remove todas as telas dinâmicas criadas por esta fábrica.
     */
    public void limparCache() {
        // Array com todos os prefixos de painéis criados por esta factory
        String[] prefixos = {
            "VIS_SERVICO_",
            "VIS_SERVICO_ANDAMENTO_",
            "VIS_SERVICO_CNTE_",
            "VIS_SERVICO_CNTE_ACEITO_",
            "VIS_CONTRATADO_",
            "CONFIG_USER"
        };
        
        // Percorre todos os prefixos
        for (String prefixo : prefixos) {
            // Limpa todos os painéis que começam com o prefixo
            limparCachePorPrefixo(prefixo);
        }
    }
    
    /**
     * Remove painéis cujo nome começa com o prefixo informado.
     * Delegado ao Navegador para varrer e remover os painéis registrados.
     */
    public void limparCachePorPrefixo(String prefixo) {
        // Delega ao navegador a remoção de todos os painéis com o prefixo especificado
        navegador.removerPainelPorPrefixo(prefixo);
    }
} // Fim da classe TelaFactory