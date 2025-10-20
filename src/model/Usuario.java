package model;

public class Usuario {
	private String email;
	private String usuario;
	private String cpfCnpj;
	private String telefone;
	private String senha;
	private boolean contratado;
	private boolean contratante;
	private boolean admin;
	private String caminhoFoto; // Added missing field
	private int idUsuario;
	private String github;

	public Usuario(String email, String usuario, String cpfCnpj, String telefone, String senha, String github, boolean contratado,
			boolean contratante, boolean admin, String caminhoFoto) {
		this.email = email;
		this.usuario = usuario;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.senha = senha;
		this.contratado = contratado;
		this.contratante = contratante;
		this.admin = admin;
		this.caminhoFoto = caminhoFoto;
		this.github = github; // Corrigido: atribui o valor ao campo
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isContratado() {
		return contratado;
	}

	public void setContratado(boolean contratado) {
		this.contratado = contratado;
	}

	public boolean isContratante() {
		return contratante;
	}

	public void setContratante(boolean contratante) {
		this.contratante = contratante;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}
	
}