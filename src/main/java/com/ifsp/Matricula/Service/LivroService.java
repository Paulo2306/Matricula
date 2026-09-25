package com.ifsp.Matricula.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class LivroService {
	public static final String endereco_armazenamento_arquivo = "src/main/resources/static/images";

	public String salvarCapa(MultipartFile arquivo) throws IOException {
		if (arquivo == null || arquivo.isEmpty()) {
			throw new IllegalArgumentException("O arquivo da capa está vazio");
		}

		String nomeOriginal = arquivo.getOriginalFilename();
		if (nomeOriginal == null || nomeOriginal.isBlank()) {
			throw new IllegalArgumentException("O nome do arquivo está vazio");
		}

		Path diretorio = Path.of(endereco_armazenamento_arquivo).toAbsolutePath().normalize();
		Path nomeArquivo = Path.of(nomeOriginal).getFileName();
		Path destino = diretorio.resolve(nomeArquivo).normalize();
		if (!destino.startsWith(diretorio)) {
			throw new SecurityException("Nome do arquivo não é viável!");
		}

		Files.createDirectories(diretorio);
		try (var inputStream = arquivo.getInputStream()) {
			Files.copy(inputStream, destino, StandardCopyOption.REPLACE_EXISTING);
		}
		return "/images/" + nomeArquivo;
	}
}
