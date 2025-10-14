package model;

public class Servico {
	private Integer id;
	private String nome_Servico;
	private Double valor;
	private String modalidade;
	private String descricao;
	private Boolean aceito;
	private Usuario contratante;
	private Usuario contratado;
	private int idServico;
	private int idContratante;
	

	public Servico(Integer id, String nome_Servico, Double valor, String modalidade, String descricao, boolean aceito, Usuario contratante) {
		this.id = id;
		this.nome_Servico = nome_Servico;
		this.valor = valor;
		this.modalidade = modalidade;
		this.descricao = descricao;
		this.aceito = aceito;
		this.contratante = contratante;
	}

	public Servico(String nome_Servico, Double valor, String modalidade, String descricao, boolean aceito, Usuario contratante) {
		this(null, nome_Servico, valor, modalidade, descricao, aceito, contratante);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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