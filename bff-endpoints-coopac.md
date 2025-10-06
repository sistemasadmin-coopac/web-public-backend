# Endpoints compuestos (BFF) — COOPAC El Salvador

## GET `/api/home`
```json
{
  "header": {
    "badgeText": "COOPAC El Salvador",
    "titleMain": "Bienvenido a",
    "titleHighlight": "COOPAC El Salvador",
    "subtitle": "Más de 30 años fortaleciendo la economía familiar.",
    "primaryCta": { "text": "Conocer servicios", "url": "/productos" },
    "secondaryCta": { "text": "Escríbenos por WhatsApp", "url": "https://wa.me/51970003173" }
  },
  "cards": [
    { "icon": "piggy-bank", "title": "Ahorros", "description": "Cuentas de ahorro con libre disponibilidad.", "order": 0 },
    { "icon": "wallet", "title": "Depósitos a Plazo", "description": "Mejores rendimientos con plazos definidos.", "order": 1 },
    { "icon": "users", "title": "Servicios", "description": "Atención cercana a nuestros socios.", "order": 2 }
  ],
  "promotions": [
    {
      "title": "Ahorro Libre",
      "tag": "Sin ITF",
      "description": "Ahorro de libre disponibilidad con intereses mensuales.",
      "highlightText": "Monto mínimo S/ 10",
      "cta": { "text": "Abrir cuenta", "url": "/productos/ahorro-libre" },
      "features": ["Sin mantenimiento de cuenta", "Sin ITF en cuenta de ahorro"],
      "order": 0
    },
    {
      "title": "Depósito a Plazo Fijo",
      "tag": "TREA hasta 8%",
      "description": "Plazos de 90 a 1,080 días con mejores rendimientos.",
      "highlightText": "Desde S/ 300",
      "cta": { "text": "Ver detalles", "url": "/productos/deposito-plazo-fijo" },
      "features": ["A mayor plazo, mayor rentabilidad", "Renovación automática al vencimiento"],
      "order": 1
    }
  ],
  "stats": [
    { "label": "Años de experiencia", "value": "30+", "icon": "calendar", "order": 0 },
    { "label": "Socios atendidos", "value": "10,000+", "icon": "users", "order": 1 },
    { "label": "Productos", "value": "2", "icon": "list", "order": 2 },
    { "label": "Sede principal", "value": "Paiján", "icon": "map", "order": 3 }
  ],
  "ctas": [
    {
      "position": "mid",
      "title": "¿Necesitas asesoría?",
      "subtitle": "Te ayudamos a elegir la mejor opción.",
      "button": { "text": "Escríbenos por WhatsApp", "url": "https://wa.me/51970003173" },
      "order": 0
    },
    {
      "position": "final",
      "title": "¿Listo para comenzar?",
      "subtitle": "Hazte socio y accede a nuestros beneficios.",
      "button": { "text": "Contactar", "url": "https://wa.me/51970003173" },
      "order": 0
    }
  ]
}
```

---

## GET `/api/products/page`
```json
{
  "header": {
    "badgeText": "Productos Financieros",
    "titleMain": "Nuestros",
    "titleHighlight": "Productos",
    "subtitle": "Elige la opción que mejor se adapte a ti."
  },
  "categories": [
    { "slug": "ahorros", "name": "Ahorros", "icon": "piggy-bank", "order": 0 },
    { "slug": "plazo-fijo", "name": "Depósitos a Plazo", "icon": "wallet", "order": 1 }
  ],
  "items": [
    {
      "slug": "ahorro-libre",
      "title": "Ahorro Libre",
      "summary": "Ahorro de libre disponibilidad con intereses mensuales.",
      "icon": "piggy-bank",
      "highlightText": "Desde S/ 10.00",
      "category": { "slug": "ahorros", "name": "Ahorros" },
      "order": 0
    },
    {
      "slug": "deposito-plazo-fijo",
      "title": "Depósito a Plazo Fijo",
      "summary": "Plazos de 90 a 1,080 días con mayor rentabilidad.",
      "icon": "wallet",
      "highlightText": "Desde S/ 300.00",
      "category": { "slug": "plazo-fijo", "name": "Depósitos a Plazo" },
      "order": 1
    }
  ]
}
```

---

## GET `/api/about/page`
```json
{
  "header": {
    "badgeText": "Nosotros",
    "titleMain": "Nuestra Historia y",
    "titleHighlight": "Misión",
    "subtitle": "Comprometidos con la comunidad de La Libertad, Perú."
  },
  "history": {
    "title": "Nuestra Historia",
    "subtitle": "Más de 30 años impulsando el ahorro y crédito responsable en La Libertad.",
    "highlight": { "value": "30+", "title": "Años de Historia", "note": "Comprometidos con nuestras familias socias." },
    "timeline": [
      { "yearLabel": "1976", "title": "Constitución de la COOPAC", "description": "Constituida el 08 de agosto de 1976; reconocida por Resolución Nº 091-OZT-77 (14/07/1977).", "order": 0 },
      { "yearLabel": "1994", "title": "Supervisión SBS", "description": "Inscrita en la SBS con el Nº 065 (22/11/1994), Resolución SBS Nº 809-94.", "order": 1 },
      { "yearLabel": "Actualidad", "title": "Compromiso Cooperativo", "description": "Entidad asociativa, sin fines de lucro, que opera con recursos de sus socios.", "order": 2 }
    ]
  },
  "missionVision": {
    "mission": {
      "title": "Nuestra Misión",
      "text": "Aportar al desarrollo económico y social de nuestro pueblo mediante servicios de ahorro, crédito y previsión social, resguardando reservas y capital social con equidad, solidaridad, respeto y confianza de los asociados."
    },
    "vision": {
      "title": "Nuestra Visión",
      "text": "Constituirnos en la mejor alternativa cooperativista en la provincia, brindando un servicio basado en los principios de equidad, solidaridad, respeto y confianza."
    }
  },
  "values": {
    "title": "Nuestros Valores",
    "subtitle": "Principios que guían nuestro trabajo.",
    "items": [
      { "icon": "hand-heart", "title": "Solidaridad", "description": "Trabajamos unidos por el bienestar común.", "order": 0 },
      { "icon": "shield-check", "title": "Confianza", "description": "Relaciones basadas en transparencia y cumplimiento.", "order": 1 },
      { "icon": "scale", "title": "Responsabilidad", "description": "Manejo responsable de recursos y ética profesional.", "order": 2 },
      { "icon": "sparkles", "title": "Innovación", "description": "Mejoramos continuamente con tecnología y métodos.", "order": 3 },
      { "icon": "accessibility", "title": "Accesibilidad", "description": "Facilitamos el acceso a servicios financieros.", "order": 4 },
      { "icon": "trending-up", "title": "Crecimiento", "description": "Impulsamos el crecimiento económico de nuestros socios.", "order": 5 }
    ]
  },
  "impact": {
    "title": "Nuestro Impacto",
    "subtitle": "Cifras que reflejan nuestro compromiso.",
    "metrics": [
      { "label": "Años de Experiencia", "value": "30+", "footnote": "Fortaleciendo la economía familiar", "icon": "calendar", "order": 0 }
    ]
  },
  "board": {
    "title": "Consejo de Administración",
    "subtitle": "Líderes comprometidos con la comunidad cooperativa.",
    "members": [
      { "fullName": "PAOLO ANDRE AMAYA ALVARADO", "position": "Presidente", "order": 0 },
      { "fullName": "MONICA ELIZABETH HANCCO ANTICONA", "position": "Vicepresidente", "order": 1 },
      { "fullName": "MARIA DEL PILAR BAUTISTA CAPRISTAN DE LOPEZ", "position": "Secretario", "order": 2 },
      { "fullName": "DARLING DE LA ROSA ZUÑIGA ASTO", "position": "Primer Vocal", "order": 3 },
      { "fullName": "ROSARIO MARUJA URBANO DELGADO", "position": "Segundo Vocal", "order": 4 }
    ]
  }
}
```

---

## GET `/api/contact/page`
```json
{
  "header": {
    "badgeText": "Contáctanos",
    "titleMain": "Estamos",
    "titleHighlight": "aquí para ayudarte",
    "subtitle": "Nuestro equipo está listo para brindarte atención personalizada.",
    "primaryCta": { "text": "Contactar por WhatsApp", "url": "https://wa.me/51970003173" }
  },
  "channels": [
    { "type": "phone", "icon": "phone", "label": "Teléfono Principal", "description": "Atención en horario de oficina.", "value": "044-544011", "order": 0 },
    { "type": "whatsapp", "icon": "message-circle", "label": "WhatsApp", "description": "Atención inmediata.", "value": "970003173", "order": 1 },
    { "type": "email", "icon": "mail", "label": "Email", "description": "Respuesta en 24 horas.", "value": "info@coopac-elsalvador.pe", "order": 2 },
    { "type": "location", "icon": "map-pin", "label": "Oficina Principal", "description": "Paiján, La Libertad, Perú.", "value": "Paiján, Provincia de Ascope", "order": 3 }
  ],
  "schedule": [
    { "label": "Lunes a Viernes", "openTime": "08:00:00", "closeTime": "17:00:00", "closed": false, "order": 0 },
    { "label": "Sábado", "openTime": "08:00:00", "closeTime": "12:00:00", "closed": false, "order": 1 },
    { "label": "Domingo", "openTime": null, "closeTime": null, "closed": true, "order": 2, "note": "Cerrado" }
  ],
  "locations": [
    {
      "name": "COOPAC El Salvador - Paiján",
      "address": "Paiján, Provincia de Ascope, La Libertad, Perú",
      "phone": "044-544011",
      "whatsapp": "970003173",
      "scheduleLabel": "Lunes a Viernes 8:00 AM - 5:00 PM",
      "googleMapsEmbedUrl": "https://maps.google.com/?q=Paij%C3%A1n,+La+Libertad,+Per%C3%BA",
      "order": 0
    }
  ]
}
```

---

## GET `/api/financials/page`
```json
{
  "header": {
    "badgeText": "Transparencia",
    "titleMain": "Reportes",
    "titleHighlight": "Financieros",
    "subtitle": "Conoce nuestros estados y reportes"
  },
  "intro": {
    "text": "Explora nuestra galería de reportes financieros organizados por categorías. Cada documento refleja nuestro compromiso con la transparencia y la responsabilidad."
  },
  "categories": [
    { "slug": "auditados", "name": "Estados Financieros Auditados", "description": "Estados anuales auditados por firmas independientes.", "order": 0, "items": 1 },
    { "slug": "trimestrales", "name": "Informes Trimestrales", "description": "Desempeño y evolución por trimestre.", "order": 1, "items": 1 },
    { "slug": "memorias", "name": "Memorias Anuales", "description": "Documentos institucionales de actividades y logros.", "order": 2, "items": 1 },
    { "slug": "especiales", "name": "Informes Especiales", "description": "Documentos específicos sobre temas particulares.", "order": 3, "items": 1 }
  ],
  "featured": {
    "auditados": [
      {
        "slug": "ef-auditados-2023",
        "title": "Estados Financieros Auditados 2023",
        "summary": "Informe auditado del ejercicio 2023.",
        "year": 2023,
        "quarter": null,
        "publishDate": "2024-03-31",
        "deliveryType": "binary",
        "fileFormat": "PDF",
        "fileUrl": "/files/ef-auditados-2023.pdf",
        "fileSizeBytes": 2800000,
        "order": 0
      }
    ],
    "trimestrales": [
      {
        "slug": "informe-q4-2023",
        "title": "Informe Financiero Q4 2023",
        "summary": "Resultados del cuarto trimestre 2023.",
        "year": 2023,
        "quarter": "Q4",
        "publishDate": "2024-01-31",
        "deliveryType": "binary",
        "fileFormat": "PDF",
        "fileUrl": "/files/informe-q4-2023.pdf",
        "fileSizeBytes": 1800000,
        "order": 0
      }
    ],
    "memorias": [
      {
        "slug": "memoria-2023",
        "title": "Memoria Anual 2023",
        "summary": "Memoria institucional del año 2023.",
        "year": 2023,
        "quarter": null,
        "publishDate": "2024-04-20",
        "deliveryType": "binary",
        "fileFormat": "PDF",
        "fileUrl": "/files/memoria-2023.pdf",
        "fileSizeBytes": 8200000,
        "order": 0
      }
    ],
    "especiales": [
      {
        "slug": "sostenibilidad-2023",
        "title": "Informe de Sostenibilidad 2023",
        "summary": "Reporte de sostenibilidad 2023.",
        "year": 2023,
        "quarter": null,
        "publishDate": "2024-06-10",
        "deliveryType": "binary",
        "fileFormat": "PDF",
        "fileUrl": "/files/sostenibilidad-2023.pdf",
        "fileSizeBytes": 4100000,
        "order": 0
      }
    ]
  }
}
```
