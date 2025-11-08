# 📊 Guía: Cómo Medir el Tiempo de Startup en GCP Cloud Run

## 🎯 Objetivo
Medir cuánto tarda el backend en estar listo para recibir tráfico después del deployment.

## 📍 Dónde Ver los Logs

### Opción 1: Cloud Console (Más Fácil)
1. Ve a [Cloud Console](https://console.cloud.google.com)
2. Navegá a **Cloud Run** → **web-public-backend-prod**
3. Haz clic en la pestaña **Logs**
4. Filtra por la revisión más reciente

### Opción 2: Línea de Comandos
```bash
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod" \
  --limit 200 \
  --project=coopac-elsalvador-backend \
  --sort-by="~timestamp" \
  --format="table(timestamp,severity,textPayload)"
```

## ⏱️ Qué Buscar en los Logs

### 1️⃣ Inicio del Pod
```
Starting new instance. Reason: DEPLOYMENT_ROLLOUT
```
**Este es el TIEMPO 0** ⏱️

### 2️⃣ JVM Iniciando
```
Picked up JAVA_TOOL_OPTIONS: -Xmx768m -XX:+UseZGC
```

### 3️⃣ Spring Boot Iniciando
```
Starting CacElsavaldorBackendApplication
```

### 4️⃣ ✅ APLICACIÓN LISTA
```
Tomcat started on port 8080 (http) with context path ''
```
**Este es el TIEMPO FINAL** ✅

O busca:
```
Started CacElsavaldorBackendApplication in X.XXX seconds
```

## 📈 Cálculo del Startup Time

**Fórmula:**
```
Startup Time = Timestamp de "Tomcat started" - Timestamp de "Starting new instance"
```

### Ejemplo:
- Starting new instance: **18:03:41.360**
- Tomcat started: **18:03:50.250**
- **Startup Time: ~9 segundos** ✅

## 🎯 Benchmarks de Referencia

| Configuración | Tiempo Esperado |
|--------------|-----------------|
| Sin optimizaciones | 30-45 segundos |
| Con lazy-initialization | 20-30 segundos |
| Con todo optimizado | **10-20 segundos** |

## 🔍 Métricas Adicionales a Monitorear

### Healthcheck Success
```
GET /actuator/health/readiness
200 OK
```

### Primer Request
Después de que Tomcat está listo, Cloud Run envía el primer request de tráfico real.

### Memory Usage
Busca líneas con uso de memoria (especialmente después de lazy-init)

## 📊 Comando para Extraer Startup Time

```bash
# En Google Cloud Shell o local con gcloud configurado:
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod" \
  --limit 50 \
  --project=coopac-elsalvador-backend \
  --sort-by="~timestamp" | grep -E "Starting new instance|Tomcat started|Started CacElsavaldorBackendApplication"
```

## 💾 Guardando los Logs

```bash
# Exportar logs a archivo
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod" \
  --limit 200 \
  --project=coopac-elsalvador-backend \
  --sort-by="~timestamp" > startup-logs.txt
```

## ✅ Checklist

- [ ] Pod iniciado exitosamente
- [ ] Spring Boot inició correctamente
- [ ] Healthchecks pasando
- [ ] Startup time < 30 segundos
- [ ] Sin errores de JVM
- [ ] Sin errores de BD
- [ ] Memoria estable

## 🎉 Resultado Esperado

Después de las optimizaciones implementadas:
- **Antes**: 45-60 segundos
- **Después**: 15-25 segundos
- **Reducción**: ~50-70% más rápido ⚡

---

**Próximas Pasos:**
1. Documenta el tiempo exacto de startup
2. Compáralo con la línea base anterior
3. Si el tiempo es > 30 segundos, podemos hacer más ajustes
4. Si el tiempo es < 20 segundos, ¡excelente! 🚀

