package com.elsalvador.coopac.service.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interfaz para el servicio de almacenamiento de archivos.
 * Permite cambiar entre almacenamiento local y Google Cloud Storage sin modificar el código del negocio.
 */
public interface FileStorageService {

    /**
     * Almacena un archivo y retorna la URL de acceso
     *
     * @param file Archivo a almacenar
     * @param folder Carpeta destino (ej: "financial-reports", "thumbnails")
     * @return URL del archivo almacenado
     */
    String storeFile(MultipartFile file, String folder);

    /**
     * Almacena un archivo con un nombre específico y retorna la URL de acceso
     *
     * @param file Archivo a almacenar
     * @param folder Carpeta destino (ej: "financial-reports", "thumbnails")
     * @param fileName Nombre específico para el archivo (sin extensión)
     * @return URL del archivo almacenado
     */
    String storeFileWithName(MultipartFile file, String folder, String fileName);

    /**
     * Elimina un archivo del almacenamiento
     *
     * @param fileUrl URL del archivo a eliminar
     */
    void deleteFile(String fileUrl);

    /**
     * Obtiene la URL pública de un archivo
     *
     * @param fileName Nombre del archivo
     * @param folder Carpeta donde está el archivo
     * @return URL pública del archivo
     */
    String getFileUrl(String fileName, String folder);

    /**
     * Verifica si un archivo existe
     *
     * @param fileUrl URL del archivo
     * @return true si existe, false si no
     */
    boolean fileExists(String fileUrl);

    /**
     * Obtiene un archivo como Base64
     *
     * @param fileName Nombre del archivo (con extensión)
     * @param folder Carpeta donde está el archivo
     * @return Contenido del archivo en Base64 con prefijo data:image, o null si no existe
     */
    String getFileAsBase64(String fileName, String folder);
}
