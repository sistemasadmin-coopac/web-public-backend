#!/bin/bash

# Script para medir el tiempo de startup del backend en Cloud Run
# Uso: ./measure-startup.sh <SERVICE_NAME> <REGION> <PROJECT_ID>

SERVICE_NAME=${1:-web-public-backend-prod}
REGION=${2:-us-central1}
PROJECT_ID=${3:-coopac-elsalvador-backend}

echo "========================================"
echo "📊 Medición de Startup Time en Cloud Run"
echo "========================================"
echo "Servicio: $SERVICE_NAME"
echo "Región: $REGION"
echo "Proyecto: $PROJECT_ID"
echo ""

# Obtener los últimos logs
echo "🔍 Obteniendo últimos logs de Cloud Run..."
gcloud logging read "resource.type=cloud_run_revision AND resource.labels.service_name=$SERVICE_NAME" \
  --limit 100 \
  --format="table(timestamp,textPayload)" \
  --project=$PROJECT_ID \
  --sort-by="~timestamp"

echo ""
echo "========================================"
echo "⏱️  Búscate los siguientes patrones en los logs:"
echo "========================================"
echo "1. 'Starting new instance' - Inicio del deployment"
echo "2. 'Application started' - Aplicación iniciada"
echo "3. 'Started CacElsavaldorBackendApplication' - Spring Boot listo"
echo ""
echo "El tiempo entre estos eventos es tu startup time 📈"
echo ""
echo "💡 Tip: Copia los timestamps y calcula la diferencia"

