# 🖨️ Sistema de Controle de Impressora Elgin
*Impressora Galuma*
---

## 📘 Sobre o Projeto

*Este projeto foi desenvolvido por Gabriela, Matheus e Luan para realizar operações de impressão por meio de uma impressora térmica. O sistema permite ao usuário interagir com a impressora por meio do terminal, com diferentes funcionalidades. O projeto foi desenvolvido como atividade da disciplina de **Programação Orientada a Objetos (POO).***

---

## 🧰 Tecnologias Utilizadas

- **Java** *JDK 27*
- **IntelliJ IDEA**
- **JNA 5.15.0** (comunicação com a DLL)
- **DLL E1_Impressora01** (SDK da Elgin)
- **Impressora Elgin i9**

---

## 📁 Estrutura do Projeto

```
/src                       → Código-fonte
   Main.java               → Inicia o programa
   Impressora.java         → Classe das funções do menu
   Entrada.java            → Classe com funções de entrada
   ImpressoraDLL.java      → DLL da Elgin
   Usuario.java            → Classe das funções de login
/Bibliotecas E1 Impressora → DLLs da Elgin (x64 e x86)
/libs Java                 → jna-5.15.0.jar
.gitignore                 → Arquivo que proibe que determinados arquivos vá para o git
CANC_SAT.xml               → Arquivo SAT de cancelamento 
README.md                  → Arquivo de explicação do projeto 
XMLSAT.xml                 → Arquivo SAT
```

---

## ⚙️ Instalação e Execução

### 1. Baixar o projeto
1. Acesse o repositório no GitHub.
2. Clique em **Code → Download ZIP**.
3. Extraia o arquivo.

### 2. Abrir no IntelliJ IDEA
1. **File → Open** e escolha a pasta extraída.
2. Em **File → Project Structure → Libraries**, confirme que o `jna-5.15.0.jar` está adicionado.
3. Conecte e ligue a impressora.
4. Abra `Main.java` e clique em ▶️ **Run**.

---

## 🔐 Login

- **Usuário:** *admin*
- **Senha:** *1111*
- **Quantidade de tentativas:** *3*

---

## 📋 Funcionalidades do Menu

| Opção | O que faz                          |
|-------|------------------------------------|
| 1     | *Configurar conexão*               |
| 2     | *Abrir conexão*                    |
| 3     | *Fechar conexão*                   |
| 4     | *Imprimir texto*                   |
| 5     | *Imprimir QR Code*                 |
| 6     | *Imprimir código de barras*        |
| 7     | *Imprimir XML SAT*                 |
| 8     | *Imprimir XML de cancelamento SAT* |
| 9     | *Abrir gaveta*                     |
| 10    | *Sinal sonoro (bip)*               |
| 11    | *Avançar papel*                    |
| 12    | *Status do papel*                  |
| 0     | *Sair*                             |

---

## 🧪 Testes Realizados

- Login com senha errada 3 vezes
- Impressão de texto
- Impressão de XML SAT
- Impressão de QR Code
- Impressão de Código de barras
- Emissão de Bip
- Abertura da Gaveta

---

## 📚 Documentação Adicional

- Manual da impressora Elgin
- Documentação do SDK E1_Impressora

---

## 👥 Autores

- Gabriela Silva dos Santos
- Luan Igor Oliveira Costa
- Matheus da Silva Souza
