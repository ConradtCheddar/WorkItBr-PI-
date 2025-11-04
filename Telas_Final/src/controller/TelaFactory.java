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
 * Factory para criação e gerenciamento de telas de detalhe.
 * Implementa remoção automática de painéis antigos para garantir dados atualizados.
 */
public class TelaFactory {
    private final Navegador navegador;
    private final ServicoDAO servicoDAO;
    private final UsuarioDAO usuarioDAO;
    
    public TelaFactory(Navegador navegador, ServicoDAO servicoDAO, UsuarioDAO usuarioDAO) {
        this.navegador = navegador;
        this.servicoDAO = servicoDAO;
        this.usuarioDAO = usuarioDAO;
    }
    
    /**
     * Cria ou recupera tela de visualização de serviço disponível (para contratado)
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
     * Cria ou recupera tela de visualização de serviço em andamento
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
     * Cria ou recupera tela de visualização de serviço para contratante (não aceito)
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
     * Cria tela de configuração de usuário (sempre nova para garantir dados atualizados)
     */
    public String criarTelaConfigUser(Usuario usuario) {
        String panelName = "CONFIG_USER";
        
        // Sempre remove e recria para garantir dados atualizados do banco
        navegador.removerPainel(panelName);
        
        // Recarrega dados do usuário do banco
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
     * Limpa todas as telas dinâmicas criadas pela factory
     */
    public void limparCache() {
        // Remove todos os painéis de visualização de serviços e usuários
        String[] prefixos = {
            "VIS_SERVICO_",
            "VIS_SERVICO_ANDAMENTO_",
            "VIS_SERVICO_CNTE_",
            "VIS_SERVICO_CNTE_ACEITO_",
            "VIS_CONTRATADO_",
            "CONFIG_USER"
        };
        
        for (String prefixo : prefixos) {
            limparCachePorPrefixo(prefixo);
        }
    }
    
    /**
     * Limpa apenas telas de um tipo específico
     */
    public void limparCachePorPrefixo(String prefixo) {
        // Como não temos acesso direto aos painéis registrados,
        // apenas removemos os conhecidos. Em implementação futura,
        // o Navegador poderia fornecer lista de painéis registrados.
        // Use the new removerPainelPorPrefixo to remove panels whose names start with the prefix
        navegador.removerPainelPorPrefixo(prefixo);
    }
}