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
	private int idUsuario;
	private String github;
	private String imagem64;
	private String caminhoFoto;

	public Usuario(String email, String usuario, String cpfCnpj, String telefone, String senha, String github, boolean contratado,
			boolean contratante, boolean admin) {
		this.email = email;
		this.usuario = usuario;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.senha = senha;
		this.contratado = contratado;
		this.contratante = contratante;
		this.admin = admin;
		this.github = github; // Corrigido: atribui o valor ao campo
	}

	/**
	 * Retorna o e-mail do usuário.
	 * @return endereço de e-mail cadastrado
	 */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * Retorna o nome de usuário (login/apelido).
	 * @return nome de usuário
	 */
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * Retorna o CPF ou CNPJ do usuário.
	 * @return cpf ou cnpj como String
	 */
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	/**
	 * Retorna o número de telefone do usuário.
	 * @return telefone como String
	 */
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	/**
	 * Retorna a senha do usuário (texto puro no objeto; evite expondo em logs).
	 * @return senha em texto
	 */
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	/**
	 * Indica se o usuário está registrado como contratado (prestador).
	 * @return true se contratado, false caso contrário
	 */
	public boolean isContratado() {
		return contratado;
	}

	public void setContratado(boolean contratado) {
		this.contratado = contratado;
	}

	/**
	 * Indica se o usuário é contratante (solicitante de serviços).
	 * @return true se contratante
	 */
	public boolean isContratante() {
		return contratante;
	}

	public void setContratante(boolean contratante) {
		this.contratante = contratante;
	}

	/**
	 * Indica se o usuário tem privilégios de administrador.
	 * @return true se admin
	 */
	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	/**
	 * Retorna o identificador interno do usuário.
	 * @return idUsuario (int)
	 */
	public int getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	/**
	 * Retorna o nome do repositório/usuário do GitHub cadastrado.
	 * @return github (String) ou null
	 */
	public String getGithub() {
		return github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	/**
	 * Retorna a imagem do usuário codificada em Base64, se houver.
	 * @return imagem em Base64 (String) ou null
	 */
	public String getImagem64() {
		return imagem64;
	}

	public void setImagem64(String imagem64) {
		this.imagem64 = imagem64;
	}

	/**
	 * Retorna o caminho local da foto do usuário (se armazenado).
	 * @return caminho do arquivo de foto (String)
	 */
	public String getCaminhoFoto() {
		return caminhoFoto;
	}

	public void setCaminhoFoto(String caminhoFoto) {
		this.caminhoFoto = caminhoFoto;
	}
	
	
}