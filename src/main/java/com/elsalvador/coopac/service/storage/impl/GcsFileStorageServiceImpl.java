package com.elsalvador.coopac.service.storage.impl;

import com.elsalvador.coopac.service.storage.FileStorageService;
import com.google.cloud.storage.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Implementación de Google Cloud Storage para el servicio de almacenamiento de archivos.
 * Se activa cuando file.storage.type=gcs en application.yml
 */
@Service
@Slf4j
@ConditionalOnProperty(name = "file.storage.type", havingValue = "gcs")
public class GcsFileStorageServiceImpl implements FileStorageService {

    @Value("${file.storage.gcs.bucket-name}")
    private String bucketName;

    @Value("${file.storage.gcs.project-id}")
    private String projectId;

    @Value("${file.storage.gcs.base-url:https://storage.googleapis.com}")
    private String baseUrl;

    private final Storage storage;

    public GcsFileStorageServiceImpl(@Value("${file.storage.gcs.project-id}") String projectId) {
        this.storage = StorageOptions.newBuilder()
                .setProjectId(projectId)
                .build()
                .getService();
        log.info("GCS Storage inicializado para proyecto: {}", projectId);
    }

    @Override
    public String storeFile(MultipartFile file, String folder) {
        try {
            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String uniqueFileName = folder + "/" + UUID.randomUUID().toString() + extension;

            // Subir archivo a GCS
            BlobId blobId = BlobId.of(bucketName, uniqueFileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getBytes());

            log.info("Archivo almacenado en GCS: {}/{}", bucketName, uniqueFileName);

            // Retornar URL pública
            return String.format("%s/%s/%s", baseUrl, bucketName, uniqueFileName);

        } catch (Exception e) {
            log.error("Error al almacenar archivo en GCS", e);
            throw new RuntimeException("Error al almacenar el archivo en GCS: " + e.getMessage(), e);
        }
    }

    @Override
    public String storeFileWithName(MultipartFile file, String folder, String fileName) {
        try {
            // Usar el nombre específico proporcionado
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String finalFileName = folder + "/" + fileName + extension;

            // Subir archivo a GCS
            BlobId blobId = BlobId.of(bucketName, finalFileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();
            storage.create(blobInfo, file.getBytes());

            log.info("Archivo almacenado en GCS con nombre específico: {}/{}", bucketName, finalFileName);

            // Retornar URL pública
            return String.format("%s/%s/%s", baseUrl, bucketName, finalFileName);

        } catch (Exception e) {
            log.error("Error al almacenar archivo en GCS", e);
            throw new RuntimeException("Error al almacenar el archivo en GCS: " + e.getMessage(), e);
        }
    }

    @Override
    public void deleteFile(String fileUrl) {
        try {
            // Extraer el nombre del blob de la URL
            String blobName = extractBlobNameFromUrl(fileUrl);

            // Eliminar archivo de GCS
            BlobId blobId = BlobId.of(bucketName, blobName);
            boolean deleted = storage.delete(blobId);

            if (deleted) {
                log.info("Archivo eliminado de GCS: {}", blobName);
            } else {
                log.warn("No se pudo eliminar el archivo de GCS (posiblemente no existe): {}", blobName);
            }

        } catch (Exception e) {
            log.error("Error al eliminar archivo de GCS", e);
            throw new RuntimeException("Error al eliminar el archivo de GCS: " + e.getMessage(), e);
        }
    }

    @Override
    public String getFileUrl(String fileName, String folder) {
        String blobName = folder + "/" + fileName;
        return String.format("%s/%s/%s", baseUrl, bucketName, blobName);
    }

    @Override
    public boolean fileExists(String fileUrl) {
        try {
            String blobName = extractBlobNameFromUrl(fileUrl);

            // Verificar si el archivo existe en GCS
            BlobId blobId = BlobId.of(bucketName, blobName);
            Blob blob = storage.get(blobId);
            return blob != null && blob.exists();

        } catch (Exception e) {
            log.error("Error al verificar existencia del archivo en GCS", e);
            return false;
        }
    }

    private String extractBlobNameFromUrl(String fileUrl) {
        // Extrae el nombre del blob de una URL como:
        // https://storage.googleapis.com/bucket-name/folder/file.pdf
        String prefix = baseUrl + "/" + bucketName + "/";
        return fileUrl.replace(prefix, "");
    }

    @Override
    public String getFileAsBase64(String fileName, String folder) {
        try {
            String blobName = folder + "/" + fileName;
            BlobId blobId = BlobId.of(bucketName, blobName);
            Blob blob = storage.get(blobId);

            if (blob == null || !blob.exists()) {
                log.debug("Archivo no encontrado en GCS: {}/{}", folder, fileName);
                return null;
            }

            // Descargar bytes del archivo
            byte[] fileBytes = blob.getContent();

            // Convertir a Base64
            String base64Content = java.util.Base64.getEncoder().encodeToString(fileBytes);

            // Determinar el tipo MIME basado en la extensión
            String contentType = blob.getContentType();
            if (contentType == null || contentType.isEmpty()) {
                contentType = "image/jpeg"; // Por defecto para imágenes
            }

            // Retornar con prefijo data:
            return String.format("data:%s;base64,%s", contentType, base64Content);

        } catch (Exception e) {
            log.error("Error al obtener archivo como Base64 de GCS: {}/{}", folder, fileName, e);
            return null;
        }
    }
}
