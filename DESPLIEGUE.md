# 🚀 Despliegue a Producción - Google Cloud Run

Guía simplificada para desplegar CAC El Salvador Backend a producción.

## ⚠️ Paso 0: Merge de develop → main (REQUERIDO)

**Antes de desplegar a producción, debes hacer un Pull Request de `develop` a `main`.**

### Crear Pull Request en GitHub

```bash
# 1. Actualizar rama develop
git checkout develop
git pull origin develop

# 2. Verificar que compila
./gradlew clean build

# 3. Push cambios
git push origin develop
```

**En GitHub:**
1. Ve a: https://github.com/elsalvador/cac-elsalvador-backend/pulls
2. Click "New pull request"
3. Base: `main` ← Compare: `develop`
4. Título: `release: versión X.Y.Z a producción`
5. Descripción: Lista de cambios
6. Click "Create pull request"
7. Espera revisión y aprobación
8. Click "Merge pull request"

---

## 🤖 Despliegue Automático (GitHub Actions)

**Al hacer merge a main, GitHub Actions automáticamente:**

1. **Build** - Compila la aplicación con Gradle
2. **Docker** - Construye imagen Docker
3. **Push** - Sube imagen a Google Artifact Registry
4. **Deploy** - Despliega a Cloud Run
5. **Notifica** - Muestra URL pública

### ¿Cómo funciona?

```
┌─────────────────────────────────────────────────────────────────┐
│  1️⃣ Haces merge develop → main en GitHub                        │
└──────────────────────┬──────────────────────────────────────────┘
                       │
       ┌───────────────▼───────────────┐
       │  GitHub Actions Workflow      │
       │  (.github/workflows/deploy.yml)
       └───────────────┬───────────────┘
                       │
       ┌───────────────▼────────────────┐
       │ 2️⃣ BUILD JOB                    │
       │ - Ejecuta ./gradlew clean build │
       │ - Genera JAR                    │
       └───────────────┬────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │ 3️⃣ DOCKER JOB                   │
       │ - Build imagen Docker           │
       │ - Push a Artifact Registry      │
       └───────────────┬────────────────┘
                       │
       ┌───────────────▼────────────────┐
       │ 4️⃣ DEPLOY JOB                   │
       │ - Deploy a Cloud Run            │
       │ - Configura variables           │
       │ - Configura secretos            │
       └───────────────┬────────────────┘
                       │
┌──────────────────────▼──────────────────────┐
│  5️⃣ ✅ API pública en Cloud Run             │
│  URL: https://web-public-backend-prod-...  │
└───────────────────────────────────────────────┘
```

### Ver progreso del despliegue

1. Ve a GitHub: https://github.com/elsalvador/cac-elsalvador-backend
2. Click en "Actions"
3. Verás el workflow ejecutándose
4. Espera a que termine (normalmente 5-10 minutos)
5. Si todo va bien, verás ✅ en los tres jobs: Build, Docker, Deploy

---

## 📋 Configuración Previa (Una sola vez)

Esto ya está hecho, pero si necesitas reconfigurar:

### Secretos en Google Secret Manager

```bash
# Estos secretos deben existir en GCP Secret Manager:
- DB_USERNAME          # Usuario base de datos
- DB_PASSWORD          # Contraseña base de datos
- GCS_BUCKET_NAME      # Bucket de Google Cloud Storage
- GOOGLE_CLIENT_ID     # OAuth2 Client ID
- GOOGLE_CLIENT_SECRET # OAuth2 Secret
- GCP_PROJECT_ID       # ID del proyecto
```

### Permisos en Google Cloud

- GitHub Actions Service Account tiene permisos para:
  - Construir imágenes Docker
  - Subir a Artifact Registry
  - Desplegar a Cloud Run
  - Acceder a Cloud SQL
  - Leer secretos

---

## 🌐 Acceder a la Aplicación

Después del despliegue, la aplicación está disponible en:

```
URL Pública: https://web-public-backend-prod-xxxxx.run.app

API Health: https://web-public-backend-prod-xxxxx.run.app/api/v1/health

Swagger UI: https://web-public-backend-prod-xxxxx.run.app/swagger-ui.html
```

---

## 📊 Monitoreo

### Ver Logs en Tiempo Real

```bash
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod" \
  --limit=100 \
  --follow
```

### Ver Métricas

```bash
gcloud monitoring time-series list \
  --filter="resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod"
```

### En Google Cloud Console

1. Cloud Run → web-public-backend-prod
2. Ver:
   - Requests
   - Latency
   - Error Rate
   - Memory usage
   - CPU usage

---

## 🔄 Rollback a Versión Anterior

Si hay problemas después del despliegue:

```bash
# Ver revisiones
gcloud run revisions list --service=web-public-backend-prod --region=us-central1

# Obtener ID de revisión anterior
PREVIOUS_REVISION=$(gcloud run revisions list --service=web-public-backend-prod \
  --region=us-central1 --sort-by="~created" --limit=2 --format='value(name)' | tail -1)

# Redirigir tráfico a revisión anterior
gcloud run services update-traffic web-public-backend-prod \
  --to-revisions=$PREVIOUS_REVISION=100 \
  --region=us-central1
```

---

## 🐛 Troubleshooting

### El workflow falla en "Build"

```bash
# Causas posibles:
- Código con errores de compilación
- Dependencias no disponibles

# Solución:
1. Verifica que ./gradlew clean build funciona localmente
2. Ve a GitHub Actions y revisa el log del error
3. Corrige el problema en develop
4. Crea nuevo PR
```

### El workflow falla en "Docker"

```bash
# Causas posibles:
- Dockerfile inválido
- JAR no encontrado

# Solución:
1. Verifica Dockerfile es válido
2. Revisa que build/libs/*.jar existe
3. En GitHub Actions, revisa el log del error
```

### El workflow falla en "Deploy"

```bash
# Causas posibles:
- Permisos insuficientes
- Secretos no configurados
- Cloud SQL no disponible

# Solución:
1. Verifica secretos en Google Secret Manager
2. Revisa IAM permissions del Service Account
3. Verifica Cloud SQL está en estado RUNNABLE
4. Revisa logs en Cloud Run
```

### La aplicación corre pero retorna errores

```bash
# Ver logs:
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=web-public-backend-prod" \
  --limit=50

# Ver salud:
curl https://web-public-backend-prod-xxxxx.run.app/api/v1/health
```

---

## 📋 Checklist de Despliegue

- [ ] Cambios en rama `develop` completados
- [ ] `./gradlew clean build` funciona localmente
- [ ] PR de develop → main creado
- [ ] Code review completado
- [ ] PR aprobado
- [ ] Merge a main hecho
- [ ] GitHub Actions workflow ejecutándose
- [ ] Esperar 5-10 minutos
- [ ] Ver ✅ en los tres jobs (Build, Docker, Deploy)
- [ ] Verificar URL pública accesible
- [ ] Test health endpoint
- [ ] Revisar logs sin errores

---

## 📚 Variables de Entorno (Referencia)

| Variable | Descripción | Ejemplo |
|----------|-------------|---------|
| SPRING_PROFILES_ACTIVE | Perfil activo | prod |
| DB_NAME | Base de datos | web-public-coopac-db |
| GCS_PROJECT_ID | Proyecto GCP | coopac-elsalvador-backend |
| CLOUD_SQL_INSTANCE | Cloud SQL Instance | coopac-elsalvador-backend:us-central1:... |
| JAVA_TOOL_OPTIONS | JVM Options | -Xmx768m -XX:+UseZGC |

Se leen de Google Secret Manager automáticamente.

---

## 🔗 Enlaces Útiles

- GitHub Repo: https://github.com/elsalvador/cac-elsalvador-backend
- GitHub Actions: https://github.com/elsalvador/cac-elsalvador-backend/actions
- Google Cloud Console: https://console.cloud.google.com
- Cloud Run: https://console.cloud.google.com/run
- Cloud Logs: https://console.cloud.google.com/logs
- Artifact Registry: https://console.cloud.google.com/artifacts

---

## 📞 Ayuda

Si necesitas ayuda:

1. Revisa los logs en GitHub Actions
2. Revisa los logs en Google Cloud Logging
3. Verifica que `./gradlew clean build` funciona localmente
4. Contacta al equipo DevOps

