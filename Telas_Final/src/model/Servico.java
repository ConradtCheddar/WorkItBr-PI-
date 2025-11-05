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

	/**
	 * Retorna o nome do serviço.
	 * 
	 * @return nome do serviço (campo nome_Servico)
	 */
	public String getNome_Servico() {
		return nome_Servico;
	}

	public void setNome_Servico(String nome_Servico) {
		this.nome_Servico = nome_Servico;
	}

	/**
	 * Retorna o valor do serviço.
	 * 
	 * @return valor (Double) associado ao serviço
	 */
	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	/**
	 * Retorna a modalidade do serviço (por exemplo presencial/online).
	 * 
	 * @return modalidade do serviço
	 */
	public String getModalidade() {
		return modalidade;
	}

	public void setModalidade(String modalidade) {
		this.modalidade = modalidade;
	}

	/**
	 * Retorna a descrição livre do serviço.
	 * 
	 * @return descrição do serviço
	 */
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	/**
	 * Indica se o serviço foi aceito.
	 * 
	 * @return true se aceito, false caso contrário (pode ser null)
	 */
	public Boolean getAceito() {
		return aceito;
	}

	public void setAceito(Boolean aceito) {
		this.aceito = aceito;
	}

	/**
	 * Retorna o usuário contratante (quem solicitou o serviço).
	 * 
	 * @return objeto Usuario representando o contratante
	 */
	public Usuario getContratante() {
		return contratante;
	}

	public void setContratante(Usuario contratante) {
		this.contratante = contratante;
	}

	/**
	 * Retorna o usuário contratado (quem realizará o serviço), ou null se ainda não
	 * houver.
	 * 
	 * @return objeto Usuario do contratado ou null
	 */
	public Usuario getContratado() {
		return contratado;
	}

	public void setContratado(Usuario contratado) {
		this.contratado = contratado;
	}

	/**
	 * Retorna o identificador interno do serviço.
	 * 
	 * @return idServico (int)
	 */
	public int getIdServico() {
		return idServico;
	}

	public void setIdServico(int idServico) {
		this.idServico = idServico;
	}

	/**
	 * Retorna o id do contratante associado ao serviço.
	 * 
	 * @return id do contratante (int)
	 */
	public int getIdContratante() {
		return idContratante;
	}

	public void setIdContratante(int idContratante) {
		this.idContratante = idContratante;
	}

	/**
	 * Retorna o id do contratado associado ao serviço.
	 * 
	 * @return id do contratado (int)
	 */
	public int getIdContratado() {
		return idContratado;
	}

	public void setIdContratado(int idContratado) {
		this.idContratado = idContratado;
	}

}