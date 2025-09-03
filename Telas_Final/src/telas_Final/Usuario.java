package telas_Final;

public class Usuario {
	private String email;
	private String usuario;
	private String cpfCnpj;
	private String telefone;
	private String senha;
	private boolean contratado;
	private boolean contratante;

	public Usuario(String email, String usuario, String cpfCnpj, String telefone, String senha, boolean contratado,
			boolean contratante) {
		this.email = email;
		this.usuario = usuario;
		this.cpfCnpj = cpfCnpj;
		this.telefone = telefone;
		this.senha = senha;
		this.contratado = contratado;
		this.contratante = contratante;
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
}
