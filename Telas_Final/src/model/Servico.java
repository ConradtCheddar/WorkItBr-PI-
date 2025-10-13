package model;

public class Servico {
	
	private String nome_Servico;
	private String valor;
	private String modalidade;
	private String descricao;
	private Boolean aceito;
	private Usuario idContratante;
	private Usuario idContratado;

	public Servico(String nome_Servico, String valor, String modalidade, String descricao, boolean aceito, Usuario idContratante) {
		this.nome_Servico = nome_Servico;
		this.valor = valor;
		this.modalidade = modalidade;
		this.descricao = descricao;
		this.aceito = aceito;
		this.idContratante = idContratante;

}

	public String getNome_Servico() {
		return nome_Servico;
	}

	public void setNome_Servico(String nome_Servico) {
		this.nome_Servico = nome_Servico;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Boolean getAceito() {
		return aceito;
	}

	public void setAceito(Boolean aceito) {
		this.aceito = aceito;
	}

	public Usuario getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(Usuario idContratante) {
		this.idContratante = idContratante;
	}

	public Usuario getIdContratado() {
		return idContratado;
	}

	public void setIdContratado(Usuario idContratado) {
		this.idContratado = idContratado;
	}
	
	
	}
