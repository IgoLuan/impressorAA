public class Usuario {
    private String usuario;
    private String senha;

    public Usuario(String usuario, String senha) {
        this.usuario = usuario;
        this.senha = senha;
    }

    public boolean autenticar(String usuarioDigitado, String senhaDigitada) {
        return usuario.equals(usuarioDigitado) && senha.equals(senhaDigitada);
    }
}