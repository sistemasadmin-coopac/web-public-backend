# API Documentation - Contact Admin

Este documento contiene los curl commands y ejemplos de respuesta para la administración completa de contacto.

## Base URL
```
http://localhost:8080
```

---

## 📞 Contact - Endpoints

### 1. Obtener todos los datos de contacto completos

**GET** `/api/admin/contact/complete`

```bash
curl -X GET "http://localhost:8080/api/admin/contact/complete" \
  -H "Content-Type: application/json"
```

**Ejemplo de Respuesta:**
```json
{
  "header": {
    "titleMain": "Estamos aquí para ti",
    "subtitle": "Nuestro equipo está listo para brindarte atención personalizada."
  },
  "sections": {
    "contactInfo": {
      "title": "Información de Contacto",
      "items": [
        {
          "id": "channel-1",
          "type": "phone",
          "icon": "phone",
          "label": "Teléfono Principal",
          "value": "044-544011",
          "description": "Atención en horario de oficina.",
          "order": 0,
          "useGlobalValue": true,
          "customValue": null,
          "isActive": true
        },
        {
          "id": "channel-2",
          "type": "whatsapp",
          "icon": "message-circle",
          "label": "WhatsApp",
          "value": "970003173",
          "description": "Atención inmediata.",
          "order": 1,
          "useGlobalValue": true,
          "customValue": null,
          "isActive": true
        },
        {
          "id": "channel-3",
          "type": "email",
          "icon": "mail",
          "label": "Email",
          "value": "info@coopac-elsalvador.pe",
          "description": "Respuesta en 24 horas.",
          "order": 2,
          "useGlobalValue": false,
          "customValue": "info@coopac-elsalvador.pe",
          "isActive": true
        },
        {
          "id": "channel-4",
          "type": "location",
          "icon": "map-pin",
          "label": "Oficina Principal",
          "value": "Paiján, Provincia de Ascope, Paiján",
          "description": "Paiján, La Libertad, Perú.",
          "order": 3,
          "useGlobalValue": false,
          "customValue": "Paiján, Provincia de Ascope, Paiján",
          "isActive": true
        }
      ]
    },
    "schedule": {
      "title": "Horarios de Atención",
      "items": [
        {
          "id": "schedule-1",
          "label": "Lunes a Viernes",
          "open": "08:00",
          "close": "17:00",
          "isClosed": false,
          "order": 0,
          "note": null,
          "isActive": true
        },
        {
          "id": "schedule-2",
          "label": "Sábado",
          "open": "08:00",
          "close": "12:00",
          "isClosed": false,
          "order": 1,
          "note": "Atención limitada",
          "isActive": true
        },
        {
          "id": "schedule-3",
          "label": "Domingo",
          "open": "",
          "close": "",
          "isClosed": true,
          "order": 2,
          "note": "Cerrado",
          "isActive": true
        }
      ],
      "note": "En días festivos nuestras oficinas permanecen cerradas."
    },
    "location": {
      "title": "Nuestras Ubicaciones",
      "subtitle": "Visítanos en nuestras oficinas",
      "places": [
        {
          "id": "location-1",
          "name": "COOPAC El Salvador - Paiján",
          "address": "Paiján, Provincia de Ascope, La Libertad, Perú",
          "phone": "044-544011",
          "whatsapp": "970003173",
          "scheduleLabel": "Lunes a Viernes 8:00 AM - 5:00 PM",
          "map": {
            "lat": -7.73129,
            "lng": -79.30174,
            "zoom": 15
          },
          "actions": [
            {
              "label": "Llamar",
              "type": "phone",
              "value": "044544011",
              "primary": false,
              "order": 0
            },
            {
              "label": "WhatsApp",
              "type": "whatsapp",
              "value": "https://wa.me/970003173",
              "primary": true,
              "order": 1
            }
          ],
          "isActive": true
        }
      ]
    }
  }
}
```

---

### 2. Actualizar canal de contacto

**PUT** `/api/admin/contact/channels/{id}`

```bash
curl -X PUT "http://localhost:8080/api/admin/contact/channels/channel-1" \
  -H "Content-Type: application/json" \
  -d '{
    "icon": "phone-call",
    "label": "Teléfono de Atención",
    "description": "Disponible en horario de oficina de lunes a viernes.",
    "useGlobalValue": true,
    "displayOrder": 0,
    "isActive": true
  }'
```

**Ejemplo de Respuesta:**
```json
{
  "id": "channel-1",
  "type": "phone",
  "icon": "phone-call",
  "label": "Teléfono de Atención",
  "value": "044-544011",
  "description": "Disponible en horario de oficina de lunes a viernes.",
  "order": 0,
  "useGlobalValue": true,
  "customValue": null,
  "isActive": true
}
```

### 3. Actualizar canal con valor personalizado

**PUT** `/api/admin/contact/channels/{id}`

```bash
curl -X PUT "http://localhost:8080/api/admin/contact/channels/channel-3" \
  -H "Content-Type: application/json" \
  -d '{
    "label": "Correo de Soporte",
    "description": "Para consultas técnicas y soporte.",
    "useGlobalValue": false,
    "customValue": "soporte@coopac-elsalvador.pe"
  }'
```

**Ejemplo de Respuesta:**
```json
{
  "id": "channel-3",
  "type": "email",
  "icon": "mail",
  "label": "Correo de Soporte",
  "value": "soporte@coopac-elsalvador.pe",
  "description": "Para consultas técnicas y soporte.",
  "order": 2,
  "useGlobalValue": false,
  "customValue": "soporte@coopac-elsalvador.pe",
  "isActive": true
}
```

---

### 4. Actualizar horario de contacto

**PUT** `/api/admin/contact/schedule/{id}`

```bash
curl -X PUT "http://localhost:8080/api/admin/contact/schedule/schedule-1" \
  -H "Content-Type: application/json" \
  -d '{
    "label": "Lunes a Viernes",
    "openTime": "08:30",
    "closeTime": "17:30",
    "isClosed": false,
    "displayOrder": 0,
    "note": "Horario extendido"
  }'
```

**Ejemplo de Respuesta:**
```json
{
  "id": "schedule-1",
  "label": "Lunes a Viernes",
  "open": "08:30",
  "close": "17:30",
  "isClosed": false,
  "order": 0,
  "note": "Horario extendido",
  "isActive": true
}
```

### 5. Marcar día como cerrado

**PUT** `/api/admin/contact/schedule/{id}`

```bash
curl -X PUT "http://localhost:8080/api/admin/contact/schedule/schedule-3" \
  -H "Content-Type: application/json" \
  -d '{
    "label": "Domingo",
    "isClosed": true,
    "note": "Oficinas cerradas"
  }'
```

**Ejemplo de Respuesta:**
```json
{
  "id": "schedule-3",
  "label": "Domingo",
  "open": "",
  "close": "",
  "isClosed": true,
  "order": 2,
  "note": "Oficinas cerradas",
  "isActive": true
}
```

---

### 6. Actualizar ubicación de contacto

**PUT** `/api/admin/contact/locations/{id}`

```bash
curl -X PUT "http://localhost:8080/api/admin/contact/locations/location-1" \
  -H "Content-Type: application/json" \
  -d '{
    "name": "COOPAC El Salvador - Oficina Central",
    "address": "Av. Principal 123, Paiján, La Libertad, Perú",
    "phoneOverride": "044-544012",
    "whatsappOverride": "970003174",
    "scheduleLabel": "Lunes a Viernes 8:30 AM - 5:30 PM",
    "latitude": -7.73129,
    "longitude": -79.30174,
    "displayOrder": 0,
    "isActive": true
  }'
```

**Ejemplo de Respuesta:**
```json
{
  "id": "location-1",
  "name": "COOPAC El Salvador - Oficina Central",
  "address": "Av. Principal 123, Paiján, La Libertad, Perú",
  "phone": "044-544012",
  "whatsapp": "970003174",
  "scheduleLabel": "Lunes a Viernes 8:30 AM - 5:30 PM",
  "map": {
    "lat": -7.73129,
    "lng": -79.30174,
    "zoom": 15
  },
  "actions": [
    {
      "label": "Llamar",
      "type": "phone",
      "value": "044544012",
      "primary": false,
      "order": 0
    },
    {
      "label": "WhatsApp",
      "type": "whatsapp",
      "value": "https://wa.me/970003174",
      "primary": true,
      "order": 1
    }
  ],
  "isActive": true
}
```

---

## ❌ Respuestas de Error

### 404 - Recurso no encontrado
```json
{
  "timestamp": "2025-09-29T16:45:15.123456",
  "status": 404,
  "error": "Not Found",
  "message": "Canal de contacto no encontrado con ID: 99999999-9999-9999-9999-999999999999",
  "path": "/api/admin/contact/channels/99999999-9999-9999-9999-999999999999"
}
```

### 400 - Datos inválidos
```json
{
  "timestamp": "2025-09-29T16:45:15.123456",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "path": "/api/admin/contact/channels/channel-1"
}
```

---

## 📝 Resumen de Endpoints

### Contact Admin (4 endpoints):
- `GET /api/admin/contact/complete` - Obtener todos los datos de contacto
- `PUT /api/admin/contact/channels/{id}` - Actualizar canal de contacto
- `PUT /api/admin/contact/schedule/{id}` - Actualizar horario
- `PUT /api/admin/contact/locations/{id}` - Actualizar ubicación

### Comportamiento de Valores Globales:
- **useGlobalValue: true** - Usa el valor de SiteSettings
- **useGlobalValue: false** - Usa el customValue especificado

### Tipos de Canales Válidos:
- `phone` - Teléfono
- `whatsapp` - WhatsApp
- `email` - Correo electrónico
- `location` - Ubicación/dirección

### Campos de Horarios:
- **openTime/closeTime**: Formato "HH:mm" (ej: "08:30")
- **isClosed**: true para días cerrados (openTime y closeTime se ignoran)
- **note**: Nota adicional para el horario

### Campos de Ubicaciones:
- **latitude/longitude**: Coordenadas para el mapa (BigDecimal)
- **phoneOverride/whatsappOverride**: Sobreescribe valores globales para esta ubicación
- **scheduleLabel**: Etiqueta personalizada de horarios para esta ubicación
