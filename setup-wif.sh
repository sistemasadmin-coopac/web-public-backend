#!/bin/bash

# Script para configurar Workload Identity Federation para GitHub Actions
# Este script automatiza la configuración completa de WIF en GCP

set -e  # Salir si hay algún error

# Colores para output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== Configuración de Workload Identity Federation ===${NC}\n"

# Paso 1: Solicitar información al usuario
echo -e "${YELLOW}Por favor, proporciona la siguiente información:${NC}"
read -p "ID del Proyecto de GCP: " PROJECT_ID
read -p "Nombre del bucket de GCS (ejemplo: coopac-elsalvador-files): " BUCKET_NAME

# Variables fijas
GITHUB_REPO="sistemasadmin-coopac/web-public-backend"
SERVICE_ACCOUNT_NAME="github-actions-deployer"
WORKLOAD_IDENTITY_POOL="github-pool"
WORKLOAD_IDENTITY_PROVIDER="github-provider"
LOCATION="us-central1"

echo -e "\n${GREEN}Configuración:${NC}"
echo "  Proyecto: ${PROJECT_ID}"
echo "  Repositorio: ${GITHUB_REPO}"
echo "  Bucket: ${BUCKET_NAME}"
echo "  Cuenta de servicio: ${SERVICE_ACCOUNT_NAME}"
echo ""

read -p "¿Es correcto? (s/n): " confirm
if [ "$confirm" != "s" ]; then
    echo "Abortado por el usuario"
    exit 1
fi

# Obtener el número de proyecto
echo -e "\n${YELLOW}Obteniendo número de proyecto...${NC}"
PROJECT_NUMBER=$(gcloud projects describe $PROJECT_ID --format="value(projectNumber)")
echo "  Número de proyecto: ${PROJECT_NUMBER}"

# Paso 2: Habilitar APIs
echo -e "\n${YELLOW}Habilitando APIs necesarias...${NC}"
gcloud services enable iamcredentials.googleapis.com \
  cloudresourcemanager.googleapis.com \
  sts.googleapis.com \
  run.googleapis.com \
  storage.googleapis.com \
  containerregistry.googleapis.com \
  --project="${PROJECT_ID}"
echo -e "${GREEN}✓ APIs habilitadas${NC}"

# Paso 3: Crear cuenta de servicio
echo -e "\n${YELLOW}Creando cuenta de servicio...${NC}"
if gcloud iam service-accounts describe ${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com --project="${PROJECT_ID}" &>/dev/null; then
    echo -e "${YELLOW}  La cuenta de servicio ya existe, omitiendo...${NC}"
else
    gcloud iam service-accounts create ${SERVICE_ACCOUNT_NAME} \
      --display-name="GitHub Actions Deployer" \
      --project="${PROJECT_ID}"
    echo -e "${GREEN}✓ Cuenta de servicio creada${NC}"
fi

# Paso 4: Asignar permisos
echo -e "\n${YELLOW}Asignando permisos a la cuenta de servicio...${NC}"

# Permiso para Container Registry
gcloud projects add-iam-policy-binding ${PROJECT_ID} \
  --member="serviceAccount:${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --role="roles/storage.admin" \
  --condition=None

# Permiso para Cloud Run
gcloud projects add-iam-policy-binding ${PROJECT_ID} \
  --member="serviceAccount:${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --role="roles/run.admin" \
  --condition=None

# Permiso para actuar como cuenta de servicio
gcloud projects add-iam-policy-binding ${PROJECT_ID} \
  --member="serviceAccount:${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --role="roles/iam.serviceAccountUser" \
  --condition=None

# Permiso para Cloud Storage
gcloud projects add-iam-policy-binding ${PROJECT_ID} \
  --member="serviceAccount:${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --role="roles/storage.objectAdmin" \
  --condition=None

echo -e "${GREEN}✓ Permisos asignados${NC}"

# Paso 5: Crear Workload Identity Pool
echo -e "\n${YELLOW}Creando Workload Identity Pool...${NC}"
if gcloud iam workload-identity-pools describe ${WORKLOAD_IDENTITY_POOL} \
  --project="${PROJECT_ID}" \
  --location="global" &>/dev/null; then
    echo -e "${YELLOW}  El pool ya existe, omitiendo...${NC}"
else
    gcloud iam workload-identity-pools create ${WORKLOAD_IDENTITY_POOL} \
      --project="${PROJECT_ID}" \
      --location="global" \
      --display-name="GitHub Actions Pool"
    echo -e "${GREEN}✓ Pool creado${NC}"
fi

# Paso 6: Crear Workload Identity Provider
echo -e "\n${YELLOW}Creando Workload Identity Provider...${NC}"
if gcloud iam workload-identity-pools providers describe ${WORKLOAD_IDENTITY_PROVIDER} \
  --project="${PROJECT_ID}" \
  --location="global" \
  --workload-identity-pool="${WORKLOAD_IDENTITY_POOL}" &>/dev/null; then
    echo -e "${YELLOW}  El provider ya existe, omitiendo...${NC}"
else
    gcloud iam workload-identity-pools providers create-oidc ${WORKLOAD_IDENTITY_PROVIDER} \
      --project="${PROJECT_ID}" \
      --location="global" \
      --workload-identity-pool="${WORKLOAD_IDENTITY_POOL}" \
      --display-name="GitHub Provider" \
      --attribute-mapping="google.subject=assertion.sub,attribute.actor=assertion.actor,attribute.repository=assertion.repository,attribute.repository_owner=assertion.repository_owner" \
      --attribute-condition="assertion.repository_owner=='sistemasadmin-coopac'" \
      --issuer-uri="https://token.actions.githubusercontent.com"
    echo -e "${GREEN}✓ Provider creado${NC}"
fi

# Paso 7: Vincular el proveedor con la cuenta de servicio
echo -e "\n${YELLOW}Vinculando provider con cuenta de servicio...${NC}"
gcloud iam service-accounts add-iam-policy-binding \
  "${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --project="${PROJECT_ID}" \
  --role="roles/iam.workloadIdentityUser" \
  --member="principalSet://iam.googleapis.com/projects/${PROJECT_NUMBER}/locations/global/workloadIdentityPools/${WORKLOAD_IDENTITY_POOL}/attribute.repository/${GITHUB_REPO}"
echo -e "${GREEN}✓ Vinculación completada${NC}"

# Paso 8: Crear bucket si no existe
echo -e "\n${YELLOW}Verificando bucket de GCS...${NC}"
if gsutil ls -b gs://${BUCKET_NAME} &>/dev/null; then
    echo -e "${YELLOW}  El bucket ya existe${NC}"
else
    echo -e "${YELLOW}  Creando bucket...${NC}"
    gcloud storage buckets create gs://${BUCKET_NAME} \
      --project=${PROJECT_ID} \
      --location=${LOCATION} \
      --uniform-bucket-level-access
    echo -e "${GREEN}✓ Bucket creado${NC}"
fi

# Dar permisos al service account sobre el bucket
echo -e "\n${YELLOW}Asignando permisos del bucket a la cuenta de servicio...${NC}"
gcloud storage buckets add-iam-policy-binding gs://${BUCKET_NAME} \
  --member="serviceAccount:${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com" \
  --role="roles/storage.objectAdmin"
echo -e "${GREEN}✓ Permisos del bucket asignados${NC}"

# Hacer el bucket público para lectura (opcional, según tus necesidades)
echo -e "\n${YELLOW}¿Deseas hacer el bucket público para lectura? (s/n):${NC}"
read -p "" make_public
if [ "$make_public" = "s" ]; then
    gcloud storage buckets add-iam-policy-binding gs://${BUCKET_NAME} \
      --member="allUsers" \
      --role="roles/storage.objectViewer"
    echo -e "${GREEN}✓ Bucket configurado como público${NC}"
fi

# Paso 9: Mostrar valores para GitHub Secrets
echo -e "\n${GREEN}=== ¡Configuración completada! ===${NC}\n"
echo -e "${YELLOW}Ahora configura los siguientes secrets en GitHub:${NC}"
echo -e "${GREEN}https://github.com/${GITHUB_REPO}/settings/secrets/actions${NC}\n"

WIF_PROVIDER=$(gcloud iam workload-identity-pools providers describe ${WORKLOAD_IDENTITY_PROVIDER} \
  --project="${PROJECT_ID}" \
  --location="global" \
  --workload-identity-pool="${WORKLOAD_IDENTITY_POOL}" \
  --format="value(name)")

echo -e "${YELLOW}WIF_PROVIDER:${NC}"
echo "${WIF_PROVIDER}"
echo ""

echo -e "${YELLOW}WIF_SERVICE_ACCOUNT:${NC}"
echo "${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com"
echo ""

echo -e "${YELLOW}GCP_PROJECT_ID:${NC}"
echo "${PROJECT_ID}"
echo ""

echo -e "${YELLOW}GCS_BUCKET_NAME:${NC}"
echo "${BUCKET_NAME}"
echo ""

echo -e "${YELLOW}DB_USERNAME y DB_PASSWORD:${NC}"
echo "Configura estos valores según tu base de datos"
echo ""

echo -e "${GREEN}=== Resumen de recursos creados ===${NC}"
echo "✓ Cuenta de servicio: ${SERVICE_ACCOUNT_NAME}@${PROJECT_ID}.iam.gserviceaccount.com"
echo "✓ Workload Identity Pool: ${WORKLOAD_IDENTITY_POOL}"
echo "✓ Workload Identity Provider: ${WORKLOAD_IDENTITY_PROVIDER}"
echo "✓ Bucket de GCS: gs://${BUCKET_NAME}"
echo ""
echo -e "${GREEN}¡Listo para usar GitHub Actions con WIF!${NC}"

