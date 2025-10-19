package br.com.fiap.cp5_socket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Cp5SocketApplication {

	public static void main(String[] args) {
		SpringApplication.run(Cp5SocketApplication.class, args);
	}

}

// Para rodar o servidor: java -cp target/cp5-socket-1.0-SNAPSHOT.jar br.com.fiap.cp5_socket.Server
// Para rodar o cliente: java -cp target/cp5-socket-1.0-SNAPSHOT.jar br.com.fiap.cp5_socket.Client
