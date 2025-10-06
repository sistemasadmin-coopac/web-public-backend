package com.elsalvador.coopac.service.storage.impl;

import com.elsalvador.coopac.service.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * Implementación local del servicio de almacenamiento de archivos.
 * Se activa cuando file.storage.type=local en application.yml
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageServiceImpl implements FileStorageService {

    @Value("${file.storage.local.base-path:./uploads}")
    private String basePath;

    @Value("${file.storage.local.base-url:http://localhost:8080/uploads}")
    private String baseUrl;

    @Override
    public String storeFile(MultipartFile file, String folder) {
        try {
            // Crear directorio si no existe
            Path uploadPath = Paths.get(basePath, folder);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String uniqueFileName = UUID.randomUUID().toString() + extension;

            // Guardar archivo
            Path filePath = uploadPath.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            log.info("Archivo almacenado localmente: {}", filePath);

            // Retornar URL de acceso
            return baseUrl + "/" + folder + "/" + uniqueFileName;

        } catch (IOException e) {
            log.error("Error al almacenar archivo localmente", e);
            throw new RuntimeException("Error al almacenar el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // Extraer el path relativo de la URL
            String relativePath = fileUrl.replace(baseUrl + "/", "");
            Path filePath = Paths.get(basePath, relativePath);

            if (Files.exists(filePath)) {
                Files.delete(filePath);
                log.info("Archivo eliminado: {}", filePath);
            } else {
                log.warn("Archivo no encontrado para eliminar: {}", filePath);
            }

        } catch (IOException e) {
            log.error("Error al eliminar archivo", e);
            throw new RuntimeException("Error al eliminar el archivo: " + e.getMessage(), e);
        }
    }

    @Override
    public String getFileUrl(String fileName, String folder) {
        return baseUrl + "/" + folder + "/" + fileName;
    }

    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String relativePath = fileUrl.replace(baseUrl + "/", "");
            Path filePath = Paths.get(basePath, relativePath);
            return Files.exists(filePath);
        } catch (Exception e) {
            log.error("Error al verificar existencia del archivo", e);
            return false;
        }
    }
}

