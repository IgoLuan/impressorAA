import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

public class Impressora {
    private static final String ASS_QRCODE_CANCELAMENTO = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";
    private int tipo = 1;
    private String modelo = "i9";
    private String conexao = "USB";
    private int parametro = 0;

    private boolean conexaoAberta = false;
    public boolean taAberto() {
        return conexaoAberta;
    }

    public boolean conexaoDisponivel() {
        if (!conexaoAberta) {
            System.out.println("Conexão fechada. Abra a conexão antes.");
            return false;
        }
        return true;
    }

    private boolean retornoDLL(int retorno, String operacao) {
        if (retorno == 0) {
            System.out.println("[OK] " + operacao + " realizado com sucesso.");
            return true;
        }
        System.out.println("[ERRO] " + operacao + " falhou. Código: " + retorno);
        return false;
    }

    private boolean finalizarImpressao() {
        return retornoDLL(ImpressoraDLL.INSTANCE.Corte(5), "Corte do papel");
    }

    private String lerArquivoXml(String caminho) {
        try {
            return Files.readString(Path.of(caminho));
        } catch (NoSuchFileException e) {
            System.out.println("Arquivo não encontrado: " + caminho);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return null;
    }
    // ------------------------------------------------------------------
    // Opções da impressora
    // ------------------------------------------------------------------

    //Opção 1
    public void configurarConexao(int tipo, String modelo, String conexao, int parametro) {
        if (conexaoAberta) {
            System.out.println("Feche a conexão atual antes de reconfigurar.");
            return;
        }
        this.tipo = tipo;
        this.modelo = modelo;
        this.conexao = conexao;
        this.parametro = parametro;
        System.out.println("Configuração salva: tipo=" + tipo + ", modelo=" + modelo + ", conexao=" + conexao + ", parametro=" + parametro);
    }

    //Opção 2
    public void abrirConexao() {
        if (conexaoAberta) {
            System.out.println("A conexão já está aberta.");
            return;
        }
        try {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("[OK] Conexão aberta com sucesso.");
            } else {
                System.out.println("[ERRO] Não foi possível abrir. Código: " + retorno);
            }
        } catch (LinkageError e) {
            System.out.println("Não foi possível carregar a DLL: " + e.getMessage());
        }
    }

    //Opção 3
    public void fecharConexao() {
        if (!conexaoAberta) {
            System.out.println("A conexão já está fechada.");
            return;
        }
        int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
        if (retornoDLL(retorno, "Fechamento da conexão")) {
            conexaoAberta = false;
        }
    }

    //Opção 4
    public void imprimirTexto(String texto, int copias) {
        for (int i = 1; i <= copias; i++) {
            int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto + "\n", 1, 0, 0);

            if (retorno < 0) {
                System.out.println("[ERRO] Falha ao imprimir a cópia " + i + ". Código: " + retorno);
                return;
            }
        }
        if (finalizarImpressao()) {
            System.out.println(copias + " cópias impressas.");
        }
    }

    //Opção 5
    public void imprimirQRCode(String dados) {
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, 4, 4);
        if (retornoDLL(retorno, "Impressão do QR Code")) {
            finalizarImpressao();
        }
    }

    //Opção 6
    public void imprimirCodigoBarras() {
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A12345678912", 100, 2, 3);
        if (retornoDLL(retorno, "Impressão do código de barras")) {
            finalizarImpressao();
        }
    }

    //Opção 7
    public void imprimirXMLSAT(String caminho) {
        String xml = lerArquivoXml(caminho);
        if (xml == null) return;

        int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(xml, 0);
        if (retornoDLL(retorno, "Impressão do XML SAT")) {
            finalizarImpressao();
        }
    }

    //Opção 8
    public void imprimirXMLCancelamentoSAT(String caminho) {
        String xml = lerArquivoXml(caminho);
        if (xml == null) return;

        int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(xml, ASS_QRCODE_CANCELAMENTO, 0);
        if (retornoDLL(retorno, "Impressão do XML de cancelamento")) {
            finalizarImpressao();
        }
    }

    //Opção 9
    public void abrirGaveta() {
        int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 5);
        retornoDLL(retorno, "Abertura da gaveta");
    }

    //Opção 10
    public void sinalSonoro(int quantidade) {
        int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(quantidade, 50, 5);
        if (retorno == 0) {
            System.out.println("Bip! (" + quantidade + "x)");
        } else {
            System.out.println("[ERRO] Sinal sonoro falhou. Código: " + retorno);
        }
    }

    //Opção 11
    public void avancarPapel(int linhas) {
        int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
        retornoDLL(retorno, "Avanço do papel");
    }

    //Opção 12
    public void statusPapel() {
        int retorno = ImpressoraDLL.INSTANCE.StatusImpressora(3);
        switch (retorno) {
            case 5 -> System.out.println("Papel: há papel.");
            case 6 -> System.out.println("Papel: está acabando.");
            case 7 -> System.out.println("Papel: sem papel!");
            default -> System.out.println("[ERRO] Não foi possível ler o status do papel. Código: " + retorno);
        }
    }
}