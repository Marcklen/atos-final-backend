package com.marcklen.projeto.domain.dtos;

// classe usada apenas para a conversao de usuario (no caso email) e senha
public class CredenciaisDTO {

	private String email;
	private String senha;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}