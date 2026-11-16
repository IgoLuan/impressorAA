public class Main {
    private static final int maximoTentativas = 3;

    public static void main(String[] args) {
        Usuario usuario = new Usuario("admin", "1111");
        Impressora impressora = new Impressora();

        if (!fazerLogin(usuario)) {
            System.out.println("Número máximo de tentativas atingido. Encerrando o programa.");
            return;
        }
        mostrarMenu(impressora);

        if (impressora.taAberto()) {
            impressora.fecharConexao();
        }
        System.out.println("Programa encerrado");
    }

    private static boolean fazerLogin(Usuario usuario) {
        System.out.println("=== Sistema de Controle da Impressora Elgin ===");
        for (int tentativa = 1; tentativa <= maximoTentativas; tentativa++) {
            String login = Entrada.lerTexto("Usuário: ");
            String senha = Entrada.lerTexto("Senha: ");

            if (usuario.autenticar(login, senha)) {
                System.out.println("Login realizado com sucesso!\n");
                return true;
            }
            int restantes = maximoTentativas - tentativa;
            if (restantes > 0) {
                System.out.println("Usuário ou senha incorretos. Tentativas restantes: " + restantes);
            }
        }
        return false;
    }

    private static void mostrarMenu(Impressora impressora) {
        int opcao;
        do {
            System.out.println("\n========== MENU ==========");
            System.out.println(" 1 - Configurar conexão");
            System.out.println(" 2 - Abrir conexão");
            System.out.println(" 3 - Fechar conexão");
            System.out.println(" 4 - Imprimir texto");
            System.out.println(" 5 - Imprimir QR Code");
            System.out.println(" 6 - Imprimir código de barras");
            System.out.println(" 7 - Imprimir XML SAT");
            System.out.println(" 8 - Imprimir XML de cancelamento SAT");
            System.out.println(" 9 - Abrir gaveta");
            System.out.println("10 - Sinal sonoro (bip)");
            System.out.println("11 - Avançar papel");
            System.out.println("12 - Status do papel");
            System.out.println(" 0 - Sair");
            System.out.println("==========================");

            opcao = Entrada.lerInteiro("Escolha uma opção: ", 0, 12);

            if (opcao >= 4 && opcao <= 12 && !impressora.conexaoDisponivel()) {
                continue;
            }

            switch (opcao) {
                case 1 -> opcaoConfigurarConexao(impressora);
                case 2 -> impressora.abrirConexao();
                case 3 -> impressora.fecharConexao();
                case 4 -> opcaoImprimirTexto(impressora);
                case 5 -> opcaoImprimirQRCode(impressora);
                case 6 -> opcaoImprimirCodigoBarras(impressora);
                case 7 -> opcaoImprimirXMLSAT(impressora);
                case 8 -> opcaoImprimirXMLCancelamento(impressora);
                case 9 -> impressora.abrirGaveta();
                case 10 -> opcaoSinalSonoro(impressora);
                case 11 -> opcaoAvancarPapel(impressora);
                case 12 -> impressora.statusPapel();
                case 0 -> System.out.println("Saindo...");
            }
        } while (opcao != 0);
    }

    private static void opcaoConfigurarConexao(Impressora impressora) {
        if (impressora.taAberto()) {
            System.out.println("Feche a conexão atual antes de reconfigurar.");
            return;
        }
        System.out.println("=== Configurar conexão ===");
        int tipo = Entrada.lerInteiro("Tipo (1=USB, 2=Serial, 3=Rede, 4=Bluetooth): ", 1, 4);
        String modelo = Entrada.lerTexto("Modelo (ex.: i8 ou i9): ");
        String conexao = Entrada.lerTexto("Conexão (USB, COM2 ou IP: ");
        int parametro = Entrada.lerInteiro("Parâmetro (0 para USB; velocidade ou porta): ", 0, 1000000);
        impressora.configurarConexao(tipo, modelo, conexao, parametro);
    }

    private static void opcaoImprimirTexto(Impressora impressora) {
        String texto = Entrada.lerTexto("Texto a imprimir: ");
        int copias = Entrada.lerInteiro("Quantidade de cópias (1 a 10): ", 1, 10);
        impressora.imprimirTexto(texto, copias);
    }

    private static void opcaoImprimirQRCode(Impressora impressora) {
        String dados = Entrada.lerTexto("Conteúdo do QR Code: ");
        impressora.imprimirQRCode(dados);
    }

    private static void opcaoImprimirCodigoBarras(Impressora impressora) {
        impressora.imprimirCodigoBarras();
    }

    private static void opcaoImprimirXMLSAT(Impressora impressora) {
        String caminho = "XMLSAT.xml";
        impressora.imprimirXMLSAT(caminho);
    }

    private static void opcaoImprimirXMLCancelamento(Impressora impressora) {
        String caminho = "CANC_SAT.xml";
        impressora.imprimirXMLCancelamentoSAT(caminho);
    }

    private static void opcaoSinalSonoro(Impressora impressora) {
        int quantidade = Entrada.lerInteiro("Quantidade de bips (1 a 63): ", 1, 63);
        impressora.sinalSonoro(quantidade);
    }

    private static void opcaoAvancarPapel(Impressora impressora) {
        int linhas = Entrada.lerInteiro("Quantas linhas avançar (1 a 255): ", 1, 255);
        impressora.avancarPapel(linhas);
    }
}