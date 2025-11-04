package model;

public class Servico {
	// Campo 'id' removido - usar apenas idServico para evitar confusão
	private String nome_Servico;
	private Double valor;
	private String modalidade;
	private String descricao;
	private Boolean aceito;
	private Usuario contratante;
	private Usuario contratado;
	private int idServico;
	private int idContratante;
	private int idContratado;

	public Servico(Integer id, String nome_Servico, Double valor, String modalidade, String descricao, boolean aceito,
			Usuario contratante) {
		this.idServico = (id != null) ? id : 0; // Usar idServico em vez de id
		this.nome_Servico = nome_Servico;
		this.valor = valor;
		this.modalidade = modalidade;
		this.descricao = descricao;
		this.aceito = aceito;
		this.contratante = contratante;
	}

	public Servico(String nome_Servico, Double valor, String modalidade, String descricao, boolean aceito,
			Usuario contratante) {
		this(null, nome_Servico, valor, modalidade, descricao, aceito, contratante);
	}

	// Método getId() agora retorna idServico
	public Integer getId() {
		return idServico;
	}

	// Método setId() agora define idServico
	public void setId(Integer id) {
		this.idServico = (id != null) ? id : 0;
	}

	public String getNome_Servico() {
		return nome_Servico;
	}

	public void setNome_Servico(String nome_Servico) {
		this.nome_Servico = nome_Servico;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
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

	public Usuario getContratante() {
		return contratante;
	}

	public void setContratante(Usuario contratante) {
		this.contratante = contratante;
	}

	public Usuario getContratado() {
		return contratado;
	}

	public void setContratado(Usuario contratado) {
		this.contratado = contratado;
	}

	public int getIdServico() {
		return idServico;
	}

	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}

	public int getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(int idContratante) {
		this.idContratante = idContratante;
	}

	public int getIdContratado() {
		return idContratado;
	}

	public void setIdContratado(int idContratado) {
		this.idContratado = idContratado;
	}

	
	
	

}