package controller;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.*;

/**
 * Factory para cria��o e gerenciamento de telas de detalhe.
 * Implementa cache para evitar re-instancia��o desnecess�ria.
 */
public class TelaFactory {
    private final Navegador navegador;
    private final ServicoDAO servicoDAO;
    private final UsuarioDAO usuarioDAO;
    
    // Cache de telas por tipo e ID
    private final Map<String, JPanel> telaCache = new HashMap<>();
    
    public TelaFactory(Navegador navegador, ServicoDAO servicoDAO, UsuarioDAO usuarioDAO) {
        this.navegador = navegador;
        this.servicoDAO = servicoDAO;
        this.usuarioDAO = usuarioDAO;
    }
    
    /**
     * Cria ou recupera tela de visualiza��o de servi�o dispon�vel (para contratado)
     */
    public String criarVisServico(Servico servico) {
        String panelName = "VIS_SERVICO_" + servico.getIdServico();
        
        // Remove painel antigo se existir (dados podem ter mudado)
        navegador.removerPainel(panelName);
        
        // Cria nova tela com dados atualizados
        VisServico view = new VisServico(servico);
        VisServicoController controller = new VisServicoController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Cria ou recupera tela de visualiza��o de servi�o em andamento
     */
    public String criarVisServicoAndamento(Servico servico) {
        String panelName = "VIS_SERVICO_ANDAMENTO_" + servico.getIdServico();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria nova tela com dados atualizados
        VisServicoAndamento view = new VisServicoAndamento(servico);
        VisServicoAndamentoController controller = new VisServicoAndamentoController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Cria ou recupera tela de visualiza��o de servi�o para contratante (n�o aceito)
     */
    public String criarVisServicoCnte(Servico servico) {
        String panelName = "VIS_SERVICO_CNTE_" + servico.getIdServico();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria nova tela
        VisServicoCnte view = new VisServicoCnte(servico);
        VisServicoCnteController controller = new VisServicoCnteController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Cria ou recupera tela de visualização de serviço aceito (para contratante)
     */
    public String criarVisServicoCnteAceito(Servico servico) {
        String panelName = "VIS_SERVICO_CNTE_ACEITO_" + servico.getIdServico();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria nova tela
        VisServicoCnteAceito view = new VisServicoCnteAceito(servico);
        VisServicoCnteAceitoController controller = new VisServicoCnteAceitoController(view, servicoDAO, navegador, servico, this);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Cria ou recupera tela de visualização de contratado
     */
    public String criarVisContratado(Usuario usuario, String telaPreviaRetorno) {
        String panelName = "VIS_CONTRATADO_" + usuario.getIdUsuario();
        
        // Remove painel antigo se existir
        navegador.removerPainel(panelName);
        
        // Cria nova tela
        VisContratado view = new VisContratado(usuario);
        
        // Configura botão voltar diretamente
        view.voltar(e -> navegador.navegarPara(telaPreviaRetorno));
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Cria tela de configura��o de usu�rio (sempre nova para garantir dados atualizados)
     */
    public String criarTelaConfigUser(Usuario usuario) {
        String panelName = "CONFIG_USER";
        
        // Sempre remove e recria para garantir dados atualizados do banco
        navegador.removerPainel(panelName);
        
        // Recarrega dados do usu�rio do banco
        Usuario usuarioAtualizado = usuarioDAO.getUsuarioById(usuario.getIdUsuario());
        if (usuarioAtualizado != null) {
            usuario = usuarioAtualizado;
            navegador.setCurrentUser(usuarioAtualizado);
        }
        
        TelaConfigUser view = new TelaConfigUser();
        TelaConfigUserController controller = new TelaConfigUserController(view, usuarioDAO, navegador, usuario);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    /**
     * Limpa todas as telas de cache
     */
    public void limparCache() {
        telaCache.clear();
    }
    
    /**
     * Limpa apenas telas de um tipo espec�fico
     */
    public void limparCachePorPrefixo(String prefixo) {
        telaCache.entrySet().removeIf(entry -> entry.getKey().startsWith(prefixo));
    }
}
