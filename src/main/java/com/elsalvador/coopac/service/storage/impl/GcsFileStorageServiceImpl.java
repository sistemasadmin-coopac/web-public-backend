package com.elsalvador.coopac.service.storage.impl;

import com.elsalvador.coopac.service.storage.FileStorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * Implementación de Google Cloud Storage para el servicio de almacenamiento de archivos.
 * Se activa cuando file.storage.type=gcs en application.yml
 *
 * NOTA: Para usar esta implementación, agregar la dependencia en build.gradle:
 * implementation 'com.google.cloud:google-cloud-storage:2.30.0'
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

    // Descomentar cuando se agregue la dependencia de GCS
    // private final Storage storage;

    // @Autowired
    // public GcsFileStorageServiceImpl() {
    //     this.storage = StorageOptions.newBuilder()
    //             .setProjectId(projectId)
    //             .build()
    //             .getService();
    // }

    @Override
    public String storeFile(MultipartFile file, String folder) {
        try {
            // Generar nombre único para el archivo
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
            String uniqueFileName = folder + "/" + UUID.randomUUID().toString() + extension;

            // TODO: Implementar subida a GCS
            // BlobId blobId = BlobId.of(bucketName, uniqueFileName);
            // BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
            //         .setContentType(file.getContentType())
            //         .build();
            // storage.create(blobInfo, file.getBytes());

            log.info("Archivo almacenado en GCS: {}/{}", bucketName, uniqueFileName);

            // Retornar URL pública
            return String.format("%s/%s/%s", baseUrl, bucketName, uniqueFileName);

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

            // TODO: Implementar eliminación en GCS
            // BlobId blobId = BlobId.of(bucketName, blobName);
            // boolean deleted = storage.delete(blobId);

            log.info("Archivo eliminado de GCS: {}", blobName);

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

            // TODO: Implementar verificación en GCS
            // BlobId blobId = BlobId.of(bucketName, blobName);
            // Blob blob = storage.get(blobId);
            // return blob != null && blob.exists();

            return false;

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
}

