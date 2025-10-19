# CP5 Socket (Cliente/Servidor com RSA)

Este projeto demonstra comunicação Cliente/Servidor via sockets TCP em Java, com troca de chaves públicas e uso de RSA para criptografar mensagens durante a conversa.

## O que o projeto faz
- Inicia um Servidor que escuta em `localhost:5000`.
- Um Cliente se conecta ao Servidor.
- Servidor e Cliente trocam suas chaves públicas (n, e) de RSA.
- Cada mensagem enviada é criptografada com a chave pública do destinatário e enviada como CSV de inteiros (um número por byte do texto).
- Do outro lado, a mensagem recebida é descriptografada e exibida em texto puro.
- Você verá no console, em ambos os lados, tanto o conteúdo criptografado (CSV) quanto o texto descriptografado.
- Para encerrar, digite `exit` no Cliente ou no Servidor.

> Observação: O esquema de criptografia por bytes (CSV) é didático e não deve ser usado em produção. Para cenários reais, considere RSA com padding (OAEP) e/ou criptografia híbrida (RSA + AES).

## Estrutura principal
- `src/main/java/br/com/fiap/cp5_socket/Server.java`: ponto de entrada do servidor.
- `src/main/java/br/com/fiap/cp5_socket/Client.java`: ponto de entrada do cliente.
- `src/main/java/br/com/fiap/cp5_socket/ConnectionHandler.java`: lógica de troca de chaves e envio/recebimento de mensagens.
- `src/main/java/br/com/fiap/cp5_socket/RSAUtil.java`: utilitários de RSA, incluindo conversão texto↔BigInteger e a variante didática CSV (por byte).

## Pré-requisitos
- Java 17 ou superior (o POM está em Spring Boot 3.x; Java 17+ recomendado)
- Não é necessário instalar Maven globalmente: use o Maven Wrapper `mvnw.cmd` que já está no repositório.

## Como executar (Windows PowerShell)
Abra dois terminais.

1) Terminal do Servidor
- Entre na pasta onde está o `pom.xml`:
```powershell
Set-Location C:cp5-socket\cp5-socket\cp5-socket
```
- Execute o servidor:
```powershell
& .\mvnw.cmd -q exec:java --% -Dexec.mainClass=br.com.fiap.cp5_socket.Server
```

2) Terminal do Cliente
- Na mesma pasta do `pom.xml`:
```powershell
Set-Location C:cp5-socket\cp5-socket\cp5-socket
```
- Execute o cliente:
```powershell
& .\mvnw.cmd -q exec:java --% -Dexec.mainClass=br.com.fiap.cp5_socket.Client
```

> Dica: Se preferir chamar pelo caminho absoluto, substitua `.\n> mvnw.cmd` por `C:cp5-socket\cp5-socket\cp5-socket\mvnw.cmd` e mantenha o `-f "...\\pom.xml"` nos comandos.

## O que esperar na execução
- Ao iniciar, Cliente e Servidor imprimem mensagens de conexão e a troca de chaves públicas.
- Quando você digitar um texto no Cliente, verá:
  - A versão criptografada (CSV) que foi enviada ao Servidor.
  - No Servidor, a mesma mensagem aparece criptografada e, em seguida, descriptografada (texto puro).
- O caminho inverso acontece quando o Servidor responde.
- Digite `exit` para encerrar.

## Observações sobre acentuação
- Dependendo da configuração do PowerShell, acentos podem aparecer incorretos. Para melhorar:
```powershell
[Console]::InputEncoding  = [System.Text.Encoding]::UTF8
[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$env:JAVA_TOOL_OPTIONS = '-Dfile.encoding=UTF-8'
```
Rode os comandos acima em cada terminal antes de iniciar o servidor/cliente se notar caracteres estranhos.

## Licença
Uso educacional.
