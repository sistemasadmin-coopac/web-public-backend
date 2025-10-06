# Sistema de Gestión de Reportes Financieros - Documentación

## Resumen

Se ha implementado un sistema completo de CRUD para gestionar reportes financieros con soporte para almacenamiento de archivos tanto local (para desarrollo) como en Google Cloud Storage (para producción).

---

## 📁 Arquitectura de Archivos Creados

### DTOs
- **`FinancialAdminDTO.java`** - DTOs para administración de reportes financieros
  - `FinancialReportCategoryDTO` - Categorías de reportes
  - `FinancialReportDTO` - Reportes financieros
  - `FinancialsIntroDTO` - Texto de introducción
  - `FileUploadResponse` - Respuesta de subida de archivos

### Servicios de Almacenamiento (Abstracción)
- **`FileStorageService.java`** - Interface para almacenamiento de archivos
- **`LocalFileStorageServiceImpl.java`** - Implementación local (activa por defecto)
- **`GcsFileStorageServiceImpl.java`** - Implementación para Google Cloud Storage (preparada)

### Repositories Mejorados
- **`FinancialReportsRepository.java`** - Métodos para CRUD de reportes
- **`FinancialReportCategoriesRepository.java`** - Métodos para CRUD de categorías

### Servicios de Negocio
- **`ManageFinancialReportsService.java`** - Interface para gestión de reportes
- **`ManageFinancialReportsServiceImpl.java`** - Implementación con lógica de negocio
- **`ManageFinancialCategoriesService.java`** - Interface para gestión de categorías
- **`ManageFinancialCategoriesServiceImpl.java`** - Implementación con lógica de negocio

### Configuración
- **`FileStorageConfig.java`** - Configuración para servir archivos estáticos
- **`application.yml`** - Configuración actualizada con propiedades de almacenamiento

---

## 🔧 Configuración del Sistema

### Almacenamiento Local (Actual - Para Desarrollo)

```yaml
file:
  storage:
    type: local
    local:
      base-path: ./uploads
      base-url: http://localhost:8080/uploads
```

**Características:**
- ✅ Archivos se almacenan en carpeta `./uploads`
- ✅ URLs de acceso: `http://localhost:8080/uploads/financial-reports/archivo.pdf`
- ✅ Ideal para desarrollo y pruebas locales
- ✅ No requiere configuración adicional

### Almacenamiento en Google Cloud Storage (Preparado - Para Producción)

Para cambiar a GCS, simplemente modifica en `application.yml`:

```yaml
file:
  storage:
    type: gcs  # Cambiar de 'local' a 'gcs'
    gcs:
      bucket-name: cac-elsalvador-files
      project-id: tu-project-id
      base-url: https://storage.googleapis.com
```

**Pasos para implementar GCS:**

1. **Agregar dependencia en `build.gradle`:**
```gradle
implementation 'com.google.cloud:google-cloud-storage:2.30.0'
```

2. **Descomentar el código en `GcsFileStorageServiceImpl.java`** (líneas marcadas con TODO)

3. **Configurar credenciales de GCP:**
   - Crear Service Account en Google Cloud Console
   - Descargar archivo JSON de credenciales
   - Configurar variable de entorno: `GOOGLE_APPLICATION_CREDENTIALS`

4. **Cambiar `file.storage.type` a `gcs`** en application.yml

---

## 📦 Funcionalidades Implementadas

### 1. Gestión de Categorías de Reportes

**Operaciones disponibles:**
- ✅ Crear categoría
- ✅ Actualizar categoría
- ✅ Eliminar categoría (valida que no tenga reportes asociados)
- ✅ Obtener categoría por ID
- ✅ Listar todas las categorías

**Validaciones:**
- Slug único (formato: solo letras minúsculas, números y guiones)
- Nombres no vacíos
- Auto-asignación de `displayOrder` si no se especifica

### 2. Gestión de Reportes Financieros

**Operaciones disponibles:**
- ✅ Crear reporte
- ✅ Actualizar reporte
- ✅ Eliminar reporte (elimina archivos asociados)
- ✅ Obtener reporte por ID
- ✅ Listar todos los reportes
- ✅ Listar reportes por categoría
- ✅ Subir archivo de reporte (PDF, XLS, XLSX, DOC, DOCX - máx 50MB)
- ✅ Subir miniatura (imágenes - máx 5MB)

**Validaciones:**
- Slug único por reporte
- Categoría válida
- Formato de archivo permitido
- Tamaño máximo de archivo
- Validación de trimestre (Q1, Q2, Q3, Q4)
- Validación de tipo de entrega (file o link)

### 3. Sistema de Almacenamiento de Archivos

**Características:**
- ✅ Abstracción mediante interface
- ✅ Generación automática de nombres únicos (UUID)
- ✅ Organización en carpetas:
  - `financial-reports/` - Archivos de reportes
  - `financial-reports/thumbnails/` - Miniaturas
- ✅ Eliminación automática de archivos al actualizar/eliminar reportes
- ✅ Validación de tipos de archivo
- ✅ Validación de tamaño de archivo

---

## 🗂️ Estructura de Base de Datos

### Tabla: `financial_report_categories`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| name | VARCHAR | Nombre de la categoría |
| slug | VARCHAR(100) | Slug único para URLs |
| description | TEXT | Descripción de la categoría |
| display_order | INTEGER | Orden de visualización |
| is_active | BOOLEAN | Estado activo/inactivo |
| created_at | TIMESTAMP | Fecha de creación |
| updated_at | TIMESTAMP | Fecha de actualización |

### Tabla: `financial_reports`

| Campo | Tipo | Descripción |
|-------|------|-------------|
| id | UUID | Identificador único |
| category_id | UUID | ID de la categoría |
| slug | VARCHAR(150) | Slug único para URLs |
| title | VARCHAR(500) | Título del reporte |
| summary | TEXT | Resumen del reporte |
| year | INTEGER | Año del reporte |
| quarter | VARCHAR(2) | Trimestre (Q1, Q2, Q3, Q4) |
| publish_date | DATE | Fecha de publicación |
| delivery_type | VARCHAR(10) | Tipo: 'file' o 'link' |
| file_format | VARCHAR(10) | Formato: pdf, xlsx, etc |
| file_url | VARCHAR(1000) | URL del archivo |
| file_size_bytes | BIGINT | Tamaño del archivo |
| thumbnail_url | VARCHAR(1000) | URL de la miniatura |
| tags | TEXT[] | Etiquetas (array) |
| is_public | BOOLEAN | Visible públicamente |
| is_active | BOOLEAN | Estado activo/inactivo |
| display_order | INTEGER | Orden de visualización |
| created_at | TIMESTAMP | Fecha de creación |
| updated_at | TIMESTAMP | Fecha de actualización |

---

## 🔄 Flujo de Trabajo Típico

### Crear un Reporte Financiero Completo

```
1. Crear categoría (si no existe)
   └─> POST /api/admin/financial/categories
   
2. Subir archivo PDF del reporte
   └─> POST /api/admin/financial/reports/upload-file
   └─> Retorna: { fileUrl, fileName, fileSizeBytes, fileFormat }
   
3. (Opcional) Subir miniatura
   └─> POST /api/admin/financial/reports/upload-thumbnail
   └─> Retorna: { fileUrl, fileName, fileSizeBytes, fileFormat }
   
4. Crear reporte con las URLs obtenidas
   └─> POST /api/admin/financial/reports
   └─> Incluir: fileUrl, thumbnailUrl, categoryId, etc.
```

### Actualizar un Reporte

```
1. (Opcional) Subir nuevo archivo
   └─> POST /api/admin/financial/reports/upload-file
   
2. Actualizar reporte
   └─> PUT /api/admin/financial/reports/{id}
   └─> El sistema elimina automáticamente el archivo anterior
```

### Eliminar un Reporte

```
1. Eliminar reporte
   └─> DELETE /api/admin/financial/reports/{id}
   └─> El sistema elimina automáticamente:
       - Archivo del reporte
       - Miniatura (si existe)
```

---

## 🛡️ Seguridad y Validaciones

### Validación de Archivos de Reportes
- **Formatos permitidos**: PDF, XLS, XLSX, DOC, DOCX
- **Tamaño máximo**: 50 MB
- **Nombres únicos**: Generados con UUID

### Validación de Imágenes (Miniaturas)
- **Formatos permitidos**: JPG, PNG, GIF, WebP
- **Tamaño máximo**: 5 MB
- **Content-Type**: Validación de tipo MIME

### Validación de Datos
- **Slug**: Solo letras minúsculas, números y guiones
- **Slug único**: Verificación en base de datos
- **Categoría existente**: Validación de FK
- **Trimestre**: Solo Q1, Q2, Q3, Q4 o vacío
- **Tipo de entrega**: Solo 'file' o 'link'
- **Año**: Entre 1900 y 2100

---

## 📊 Ejemplos de Datos

### Categoría de Reporte
```json
{
  "name": "Estados Financieros",
  "slug": "estados-financieros",
  "description": "Informes trimestrales y anuales de la situación financiera",
  "displayOrder": 1,
  "isActive": true
}
```

### Reporte Financiero
```json
{
  "categoryId": "550e8400-e29b-41d4-a716-446655440000",
  "slug": "estado-financiero-q1-2024",
  "title": "Estado Financiero Q1 2024",
  "summary": "Informe financiero del primer trimestre del año 2024",
  "year": 2024,
  "quarter": "Q1",
  "publishDate": "2024-04-15",
  "deliveryType": "file",
  "fileFormat": "pdf",
  "fileUrl": "http://localhost:8080/uploads/financial-reports/uuid.pdf",
  "fileSizeBytes": 2458624,
  "thumbnailUrl": "http://localhost:8080/uploads/financial-reports/thumbnails/uuid.jpg",
  "tags": ["trimestral", "2024", "auditado"],
  "isPublic": true,
  "isActive": true,
  "displayOrder": 1
}
```

---

## 🚀 Próximos Pasos

Para completar el sistema, necesitas:

1. **Crear los Controllers REST** para exponer los endpoints:
   - `FinancialCategoriesController.java`
   - `FinancialReportsController.java`

2. **Implementar seguridad** (si es necesario):
   - Autenticación JWT
   - Roles de administrador

3. **Migrar a GCS** (cuando esté listo):
   - Agregar dependencia de Google Cloud Storage
   - Descomentar código en `GcsFileStorageServiceImpl`
   - Configurar credenciales
   - Cambiar `file.storage.type` a `gcs`

4. **Testing**:
   - Crear tests unitarios para los servicios
   - Crear tests de integración para los repositories

---

## 📝 Notas Importantes

### Cambio entre Local y GCS

El sistema está diseñado para cambiar fácilmente entre almacenamiento local y GCS:

- **Local**: `file.storage.type: local` (actual)
- **GCS**: `file.storage.type: gcs` (futuro)

Spring Boot automáticamente activa la implementación correcta usando `@ConditionalOnProperty`.

### Estructura de Carpetas Local

```
./uploads/
  └─ financial-reports/
      ├─ uuid1.pdf
      ├─ uuid2.xlsx
      └─ thumbnails/
          ├─ uuid1.jpg
          └─ uuid2.png
```

### URLs de Acceso

**Local:**
- Reporte: `http://localhost:8080/uploads/financial-reports/uuid.pdf`
- Miniatura: `http://localhost:8080/uploads/financial-reports/thumbnails/uuid.jpg`

**GCS (cuando se implemente):**
- Reporte: `https://storage.googleapis.com/bucket-name/financial-reports/uuid.pdf`
- Miniatura: `https://storage.googleapis.com/bucket-name/financial-reports/thumbnails/uuid.jpg`

---

## ✅ Verificación del Build

El proyecto se compiló exitosamente:
```
BUILD SUCCESSFUL in 2s
8 actionable tasks: 8 up-to-date
```

Todos los archivos están listos y sin errores de compilación.

