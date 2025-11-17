package controller;

import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import model.Servico;
import model.ServicoDAO;
import model.Usuario;
import model.UsuarioDAO;
import view.*;

public class TelaFactory {
    private final Navegador navegador;
    private final ServicoDAO servicoDAO;
    private final UsuarioDAO usuarioDAO;
    
    public TelaFactory(Navegador navegador, ServicoDAO servicoDAO, UsuarioDAO usuarioDAO) {
        this.navegador = navegador;
        this.servicoDAO = servicoDAO;
        this.usuarioDAO = usuarioDAO;
    }
    
    public String criarVisServico(Servico servico) {
        String panelName = "VIS_SERVICO" + servico.getIdServico();
        
        navegador.removerPainel(panelName);
        
        VisServico view = new VisServico(servico);
        VisServicoController controller = new VisServicoController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarVisServicoAndamento(Servico servico) {
        String panelName = "VIS_SERVICO_ANDAMENTO"+ servico.getIdServico();
        
        navegador.removerPainel(panelName);
        
        VisServicoAndamento view = new VisServicoAndamento(servico);
        VisServicoAndamentoController controller = new VisServicoAndamentoController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarVisServicoCnte(Servico servico) {
        String panelName = "VIS_SERVICO_CNTE";
        
        navegador.removerPainel(panelName);
        
        VisServicoCnte view = new VisServicoCnte(servico);
        VisServicoCnteController controller = new VisServicoCnteController(view, servicoDAO, navegador, servico);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarVisServicoCnteAceito(Servico servico) {
        String panelName = "VIS_SERVICO_CNTE_ACEITO";
        
        navegador.removerPainel(panelName);
        
        VisServicoCnteAceito view = new VisServicoCnteAceito(servico);
        VisServicoCnteAceitoController controller = new VisServicoCnteAceitoController(view, servicoDAO, navegador, servico, this);
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarVisContratado(Usuario usuario, String telaPreviaRetorno) {
        String panelName = "VIS_CONTRATADO";
        
        navegador.removerPainel(panelName);
        
        VisContratado view = new VisContratado(usuario);
        
        view.voltar(e -> navegador.navegarPara(telaPreviaRetorno));
        
        navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarTelaConfigUser(Usuario usuario) {
        String panelName = "CONFIG_USER";
        
        navegador.removerPainel(panelName);
        
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
    
    public String criarVisServicoCnteFinalizado(Servico s) {
    	String panelName = "VIS_FINALIZADO";
    	
    	navegador.removerPainel(panelName);
    	
    	VisServicoCnteFinalizado view = new VisServicoCnteFinalizado(s);
		VisServicoCnteFinalizadoController controller = new VisServicoCnteFinalizadoController(view, servicoDAO, navegador, s, this);
		
		navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public String criarTelaVisArquivo(Servico s) {
    	String panelName = "ARQUIVO";
    	
    	navegador.removerPainel(panelName);
    	
    	TelaVisArquivos view = new TelaVisArquivos(s);
		
		navegador.adicionarPainel(panelName, view);
        return panelName;
    }
    
    public void limparCache() {
        String[] prefixos = {
            "VIS_SERVICO",
            "VIS_SERVICO_ANDAMENTO",
            "VIS_SERVICO_CNTE",
            "VIS_SERVICO_CNTE_ACEITO",
            "VIS_CONTRATADO",
            "CONFIG_USER"
        };
        
        for (String prefixo : prefixos) {
            limparCachePorPrefixo(prefixo);
        }
    }
    
    public void limparCachePorPrefixo(String prefixo) {
        navegador.removerPainelPorPrefixo(prefixo);
    }
}