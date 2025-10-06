# COOPAC El Salvador - Endpoints Testing

## cURL Commands

### 1. Home Page
```bash
curl -X GET "http://localhost:8080/api/home" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

### 2. Products Page
```bash
curl -X GET "http://localhost:8080/api/products/page" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

### 3. About Page
```bash
curl -X GET "http://localhost:8080/api/about/page" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

### 4. Contact Page
```bash
curl -X GET "http://localhost:8080/api/contact/page" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

### 5. Financial Page
```bash
curl -X GET "http://localhost:8080/api/financials/page" \
  -H "Accept: application/json" \
  -H "Content-Type: application/json"
```

## All in One (Para probar todos los endpoints secuencialmente)
```bash
echo "Testing Home Page..."
curl -X GET "http://localhost:8080/api/home" -H "Accept: application/json" | jq .

echo -e "\n\nTesting Products Page..."
curl -X GET "http://localhost:8080/api/products/page" -H "Accept: application/json" | jq .

echo -e "\n\nTesting About Page..."
curl -X GET "http://localhost:8080/api/about/page" -H "Accept: application/json" | jq .

echo -e "\n\nTesting Contact Page..."
curl -X GET "http://localhost:8080/api/contact/page" -H "Accept: application/json" | jq .

echo -e "\n\nTesting Financial Page..."
curl -X GET "http://localhost:8080/api/financials/page" -H "Accept: application/json" | jq .
```

## Windows PowerShell Version
```powershell
# Home Page
Invoke-RestMethod -Uri "http://localhost:8080/api/home" -Method GET -Headers @{"Accept"="application/json"}

# Products Page
Invoke-RestMethod -Uri "http://localhost:8080/api/products/page" -Method GET -Headers @{"Accept"="application/json"}

# About Page
Invoke-RestMethod -Uri "http://localhost:8080/api/about/page" -Method GET -Headers @{"Accept"="application/json"}

# Contact Page
Invoke-RestMethod -Uri "http://localhost:8080/api/contact/page" -Method GET -Headers @{"Accept"="application/json"}

# Financial Page
Invoke-RestMethod -Uri "http://localhost:8080/api/financials/page" -Method GET -Headers @{"Accept"="application/json"}
```
