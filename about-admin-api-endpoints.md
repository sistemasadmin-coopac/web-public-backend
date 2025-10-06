# About Admin API Endpoints

Esta documentación contiene todos los endpoints disponibles para la administración de la sección About, incluyendo ejemplos de curl y respuestas.

## Base URL
```
http://localhost:8080/api/admin/about
```

---

## 1. About Board (Junta Directiva)

### 1.1 Crear Miembro de Junta Directiva
**POST** `/api/admin/about/board/members`

#### Curl Example:
```bash
curl -X POST http://localhost:8080/api/admin/about/board/members \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "María Elena González",
    "position": "Presidenta",
    "photoUrl": "https://example.com/photos/maria-gonzalez.jpg",
    "bio": "Economista con más de 15 años de experiencia en el sector financiero cooperativo.",
    "linkedinUrl": "https://linkedin.com/in/maria-gonzalez",
    "email": "maria.gonzalez@coopac.com",
    "phone": "+503 2234-5678",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (201 Created):
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "fullName": "María Elena González",
  "position": "Presidenta",
  "photoUrl": "https://example.com/photos/maria-gonzalez.jpg",
  "bio": "Economista con más de 15 años de experiencia en el sector financiero cooperativo.",
  "linkedinUrl": "https://linkedin.com/in/maria-gonzalez",
  "email": "maria.gonzalez@coopac.com",
  "phone": "+503 2234-5678",
  "displayOrder": 1,
  "isActive": true
}
```

### 1.2 Actualizar Miembro de Junta Directiva
**PUT** `/api/admin/about/board/members/{id}`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/board/members/123e4567-e89b-12d3-a456-426614174000 \
  -H "Content-Type: application/json" \
  -d '{
    "fullName": "María Elena González Rodríguez",
    "position": "Presidenta Ejecutiva",
    "photoUrl": "https://example.com/photos/maria-gonzalez-updated.jpg",
    "bio": "Economista con más de 20 años de experiencia en el sector financiero cooperativo. Especialista en desarrollo sostenible.",
    "linkedinUrl": "https://linkedin.com/in/maria-gonzalez-rodriguez",
    "email": "maria.gonzalez@coopac.com",
    "phone": "+503 2234-5678",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "123e4567-e89b-12d3-a456-426614174000",
  "fullName": "María Elena González Rodríguez",
  "position": "Presidenta Ejecutiva",
  "photoUrl": "https://example.com/photos/maria-gonzalez-updated.jpg",
  "bio": "Economista con más de 20 años de experiencia en el sector financiero cooperativo. Especialista en desarrollo sostenible.",
  "linkedinUrl": "https://linkedin.com/in/maria-gonzalez-rodriguez",
  "email": "maria.gonzalez@coopac.com",
  "phone": "+503 2234-5678",
  "displayOrder": 1,
  "isActive": true
}
```

### 1.3 Eliminar Miembro de Junta Directiva
**DELETE** `/api/admin/about/board/members/{id}`

#### Curl Example:
```bash
curl -X DELETE http://localhost:8080/api/admin/about/board/members/123e4567-e89b-12d3-a456-426614174000
```

#### Response (204 No Content):
```
(Sin contenido en el cuerpo de la respuesta)
```

### 1.4 Actualizar Sección de Junta Directiva
**PUT** `/api/admin/about/board/section`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/board/section \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nuestra Junta Directiva",
    "subtitle": "Conoce a los líderes que guían nuestra cooperativa hacia el éxito y el crecimiento sostenible.",
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "456e7890-e12b-34d5-a678-901234567890",
  "title": "Nuestra Junta Directiva",
  "subtitle": "Conoce a los líderes que guían nuestra cooperativa hacia el éxito y el crecimiento sostenible.",
  "isActive": true
}
```

---

## 2. About Values (Valores Organizacionales)

### 2.1 Crear Valor
**POST** `/api/admin/about/values`

#### Curl Example:
```bash
curl -X POST http://localhost:8080/api/admin/about/values \
  -H "Content-Type: application/json" \
  -d '{
    "icon": "fas fa-handshake",
    "title": "Confianza",
    "description": "Construimos relaciones sólidas basadas en la transparencia y la honestidad con nuestros socios.",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (201 Created):
```json
{
  "id": "789e0123-e45f-67g8-h901-234567890123",
  "icon": "fas fa-handshake",
  "title": "Confianza",
  "description": "Construimos relaciones sólidas basadas en la transparencia y la honestidad con nuestros socios.",
  "displayOrder": 1,
  "isActive": true
}
```

### 2.2 Actualizar Valor
**PUT** `/api/admin/about/values/{id}`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/values/789e0123-e45f-67g8-h901-234567890123 \
  -H "Content-Type: application/json" \
  -d '{
    "icon": "fas fa-handshake",
    "title": "Confianza y Transparencia",
    "description": "Construimos relaciones sólidas y duraderas basadas en la transparencia, honestidad y respeto mutuo con nuestros socios y la comunidad.",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "789e0123-e45f-67g8-h901-234567890123",
  "icon": "fas fa-handshake",
  "title": "Confianza y Transparencia",
  "description": "Construimos relaciones sólidas y duraderas basadas en la transparencia, honestidad y respeto mutuo con nuestros socios y la comunidad.",
  "displayOrder": 1,
  "isActive": true
}
```

### 2.3 Eliminar Valor
**DELETE** `/api/admin/about/values/{id}`

#### Curl Example:
```bash
curl -X DELETE http://localhost:8080/api/admin/about/values/789e0123-e45f-67g8-h901-234567890123
```

#### Response (204 No Content):
```
(Sin contenido en el cuerpo de la respuesta)
```

### 2.4 Actualizar Sección de Valores
**PUT** `/api/admin/about/values/section`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/values/section \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nuestros Valores Fundamentales",
    "subtitle": "Los principios que guían cada una de nuestras acciones y decisiones en el compromiso con nuestros socios y la comunidad.",
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "abc1234d-e5f6-7890-1234-567890abcdef",
  "title": "Nuestros Valores Fundamentales",
  "subtitle": "Los principios que guían cada una de nuestras acciones y decisiones en el compromiso con nuestros socios y la comunidad.",
  "isActive": true
}
```

---

## 3. About History (Historia y Timeline)

### 3.1 Actualizar Evento del Timeline
**PUT** `/api/admin/about/history/timeline/{id}`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/history/timeline/def5678e-9abc-1234-5678-90def1234567 \
  -H "Content-Type: application/json" \
  -d '{
    "yearLabel": "1995",
    "title": "Fundación de la Cooperativa",
    "description": "Nace COOPAC El Salvador con la visión de brindar servicios financieros accesibles y de calidad a la comunidad salvadoreña.",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "def5678e-9abc-1234-5678-90def1234567",
  "yearLabel": "1995",
  "title": "Fundación de la Cooperativa",
  "description": "Nace COOPAC El Salvador con la visión de brindar servicios financieros accesibles y de calidad a la comunidad salvadoreña.",
  "displayOrder": 1,
  "isActive": true
}
```

### 3.2 Actualizar Sección de Historia
**PUT** `/api/admin/about/history/section`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/history/section \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nuestra Trayectoria",
    "subtitle": "Más de 25 años construyendo confianza y creciendo junto a nuestros socios. Conoce los momentos más importantes de nuestra historia.",
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "ghi7890f-1234-5678-9abc-def123456789",
  "title": "Nuestra Trayectoria",
  "subtitle": "Más de 25 años construyendo confianza y creciendo junto a nuestros socios. Conoce los momentos más importantes de nuestra historia.",
  "isActive": true
}
```

---

## 4. About Impact (Métricas de Impacto)

### 4.1 Actualizar Métrica de Impacto
**PUT** `/api/admin/about/impact/metrics/{id}`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/impact/metrics/jkl9012g-3456-7890-bcde-f123456789ab \
  -H "Content-Type: application/json" \
  -d '{
    "label": "Socios Activos",
    "valueText": "15,000+",
    "footnote": "Datos actualizados a diciembre 2024",
    "icon": "fas fa-users",
    "displayOrder": 1,
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "jkl9012g-3456-7890-bcde-f123456789ab",
  "label": "Socios Activos",
  "valueText": "15,000+",
  "footnote": "Datos actualizados a diciembre 2024",
  "icon": "fas fa-users",
  "displayOrder": 1,
  "isActive": true
}
```

### 4.2 Actualizar Sección de Impacto
**PUT** `/api/admin/about/impact/section`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/impact/section \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Nuestro Impacto en Números",
    "subtitle": "Las cifras que reflejan nuestro compromiso constante con el desarrollo y bienestar de nuestros socios y la comunidad salvadoreña.",
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "mno3456h-7890-1234-cdef-56789012345a",
  "title": "Nuestro Impacto en Números",
  "subtitle": "Las cifras que reflejan nuestro compromiso constante con el desarrollo y bienestar de nuestros socios y la comunidad salvadoreña.",
  "isActive": true
}
```

---

## 5. About Mission Vision (Misión y Visión)

### 5.1 Actualizar Misión y Visión
**PUT** `/api/admin/about/mission-vision`

#### Curl Example:
```bash
curl -X PUT http://localhost:8080/api/admin/about/mission-vision \
  -H "Content-Type: application/json" \
  -d '{
    "missionTitle": "Nuestra Misión",
    "missionText": "Brindar servicios financieros cooperativos de calidad, promoviendo el desarrollo económico y social de nuestros socios y sus familias, contribuyendo al crecimiento sostenible de las comunidades salvadoreñas.",
    "visionTitle": "Nuestra Visión",
    "visionText": "Ser la cooperativa líder en El Salvador, reconocida por la excelencia en nuestros servicios, la innovación financiera y nuestro compromiso con el desarrollo humano integral y sostenible.",
    "isActive": true
  }'
```

#### Response (200 OK):
```json
{
  "id": "pqr5678i-9012-3456-defg-789012345678",
  "missionTitle": "Nuestra Misión",
  "missionText": "Brindar servicios financieros cooperativos de calidad, promoviendo el desarrollo económico y social de nuestros socios y sus familias, contribuyendo al crecimiento sostenible de las comunidades salvadoreñas.",
  "visionTitle": "Nuestra Visión",
  "visionText": "Ser la cooperativa líder en El Salvador, reconocida por la excelencia en nuestros servicios, la innovación financiera y nuestro compromiso con el desarrollo humano integral y sostenible.",
  "isActive": true
}
```

---

## 6. About Complete Data (Datos Completos)

### 6.1 Obtener Datos Completos de About
**GET** `/api/admin/about/complete`

#### Curl Example:
```bash
curl -X GET http://localhost:8080/api/admin/about/complete \
  -H "Accept: application/json"
```

#### Response (200 OK):
```json
{
  "missionVision": {
    "id": "pqr5678i-9012-3456-defg-789012345678",
    "missionTitle": "Nuestra Misión",
    "missionText": "Brindar servicios financieros cooperativos de calidad...",
    "visionTitle": "Nuestra Visión",
    "visionText": "Ser la cooperativa líder en El Salvador...",
    "isActive": true
  },
  "valuesSection": {
    "id": "abc1234d-e5f6-7890-1234-567890abcdef",
    "title": "Nuestros Valores Fundamentales",
    "subtitle": "Los principios que guían cada una de nuestras acciones...",
    "isActive": true
  },
  "values": [
    {
      "id": "789e0123-e45f-67g8-h901-234567890123",
      "icon": "fas fa-handshake",
      "title": "Confianza y Transparencia",
      "description": "Construimos relaciones sólidas y duraderas...",
      "displayOrder": 1,
      "isActive": true
    }
  ],
  "historySection": {
    "id": "ghi7890f-1234-5678-9abc-def123456789",
    "title": "Nuestra Trayectoria",
    "subtitle": "Más de 25 años construyendo confianza...",
    "isActive": true
  },
  "timelineEvents": [
    {
      "id": "def5678e-9abc-1234-5678-90def1234567",
      "yearLabel": "1995",
      "title": "Fundación de la Cooperativa",
      "description": "Nace COOPAC El Salvador con la visión...",
      "displayOrder": 1,
      "isActive": true
    }
  ],
  "impactSection": {
    "id": "mno3456h-7890-1234-cdef-56789012345a",
    "title": "Nuestro Impacto en Números",
    "subtitle": "Las cifras que reflejan nuestro compromiso...",
    "isActive": true
  },
  "impactMetrics": [
    {
      "id": "jkl9012g-3456-7890-bcde-f123456789ab",
      "label": "Socios Activos",
      "valueText": "15,000+",
      "footnote": "Datos actualizados a diciembre 2024",
      "icon": "fas fa-users",
      "displayOrder": 1,
      "isActive": true
    }
  ],
  "boardSection": {
    "id": "456e7890-e12b-34d5-a678-901234567890",
    "title": "Nuestra Junta Directiva",
    "subtitle": "Conoce a los líderes que guían nuestra cooperativa...",
    "isActive": true
  },
  "boardMembers": [
    {
      "id": "123e4567-e89b-12d3-a456-426614174000",
      "fullName": "María Elena González Rodríguez",
      "position": "Presidenta Ejecutiva",
      "photoUrl": "https://example.com/photos/maria-gonzalez-updated.jpg",
      "bio": "Economista con más de 20 años de experiencia...",
      "linkedinUrl": "https://linkedin.com/in/maria-gonzalez-rodriguez",
      "email": "maria.gonzalez@coopac.com",
      "phone": "+503 2234-5678",
      "displayOrder": 1,
      "isActive": true
    }
  ]
}
```

---

## Códigos de Estado HTTP

- **200 OK**: Operación exitosa (GET, PUT)
- **201 Created**: Recurso creado exitosamente (POST)
- **204 No Content**: Recurso eliminado exitosamente (DELETE)
- **400 Bad Request**: Error en los datos enviados
- **404 Not Found**: Recurso no encontrado
- **422 Unprocessable Entity**: Error de validación
- **500 Internal Server Error**: Error interno del servidor

---

## Notas Importantes

1. **Autenticación**: Estos endpoints pueden requerir autenticación/autorización según la configuración de seguridad del proyecto.

2. **Content-Type**: Siempre incluir `Content-Type: application/json` en las peticiones POST y PUT.

3. **UUIDs**: Todos los IDs utilizan formato UUID v4.

4. **Validación**: Los campos marcados como required en los DTOs son obligatorios.

5. **Display Order**: Se usa para ordenar elementos en el frontend.

6. **Active Status**: El campo `isActive` controla la visibilidad en el sitio público.

7. **Campos Opcionales**: 
   - `photoUrl`, `bio`, `linkedinUrl`, `email`, `phone` en BoardMember
   - `icon` en Values
   - `footnote` en ImpactMetrics
