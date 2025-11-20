# Proyecto ParcialElectiva – CI/CD Java

Este proyecto demuestra un pipeline de CI/CD con Java, Maven, Checkstyle, JUnit y JaCoCo usando GitHub Actions.  
Automatiza análisis de estilo, ejecución de pruebas y validación de cobertura.

---

## Requisitos previos

- Java 17
- Maven 3.8+
- Docker (para usar `act` localmente)

---

## Instalación y ejecución del pipeline

Desde la raíz del proyecto, puedes ejecutar los pasos principales en un solo bloque de comandos bash:

```bash
# 1. Instalar dependencias (sin ejecutar tests)
mvn install -DskipTests

# 2. Ejecutar linter (Checkstyle)
mvn checkstyle:check

# 3. Compilar proyecto
mvn package

# 4. Preparar cobertura JaCoCo
mvn jacoco:prepare-agent

# 5. Ejecutar pruebas unitarias (JUnit)
mvn test

# 6. Generar reportes de cobertura
mvn jacoco:report

# 7. Validar cobertura mínima (80–85%)
mvn jacoco:check

# Ejecutar workflow completo
act -W .github/workflows/ci-quality.yml

# Ejecutar solo el job 'build'
act -j build
```

