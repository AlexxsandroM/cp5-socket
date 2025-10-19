# Como rodar (Windows PowerShell)

Pré-requisitos
- Java 21+
- Não precisa ter Maven instalado globalmente: use `mvnw.cmd`

Compilar e testar:
```powershell
& ".\mvnw.cmd" -q -DskipTests=false test
```

Executar servidor (terminal 1):
```powershell
& ".\mvnw.cmd" -q exec:java -Dexec.mainClass="br.com.fiap.cp5_socket.Server"
```

Executar cliente (terminal 2):
```powershell
& ".\mvnw.cmd" -q exec:java -Dexec.mainClass="br.com.fiap.cp5_socket.Client"
```

Fluxo exibido no console
- Cliente: digite o texto puro; veremos também a versão criptografada (CSV).
- Servidor: mostra a mensagem criptografada recebida e a versão descriptografada.
- Resposta do servidor segue o mesmo padrão e o cliente exibirá a versão descriptografada.
- Para encerrar, digite `exit` em qualquer lado.

Detalhes técnicos
- Criptografia RSA por bytes (CSV) para evitar erro de tamanho de bloco.
- Métodos: `RSAUtil.encryptToCsv(String, e, n)` e `RSAUtil.decryptFromCsv(String)`.
