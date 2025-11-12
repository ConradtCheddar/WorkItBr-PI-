package model;

public class Servico {
	private String nome_Servico;
	private Double valor;
	private String modalidade;
	private String descricao;
	private Usuario contratante;
	private Usuario contratado;
	private int idServico;
	private int idContratante;
	private int idContratado;
	private byte[] arquivo;
	private Status status;

	public Servico(Integer id, String nome_Servico, Double valor, String modalidade, String descricao, Status status,
			Usuario contratante) {
		this.idServico = (id != null) ? id : 0;
		this.nome_Servico = nome_Servico;
		this.valor = valor;
		this.modalidade = modalidade;
		this.descricao = descricao;
		this.contratante = contratante;
		this.status = status;
	}

	public Servico(String nome_Servico, Double valor, String modalidade, String descricao, Status status, Usuario contratante) {
		this(null, nome_Servico, valor, modalidade, descricao, status, contratante);
	}

	public Integer getId() {
		return idServico;
	}

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

	public byte[] getArquivo() {
		return arquivo;
	}

	public void setArquivo(byte[] arquivo) {
		this.arquivo = arquivo;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
	

	
	
	

}