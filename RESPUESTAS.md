**PARTE 1**

---

### 1. Diferencia entre CI y CD

**CI (Integración Continua):**  
La integración continua automatiza la incorporación de cambios de código cada vez que se hace un push o un pull request. Esto incluye ejecutar pruebas, construir el proyecto, y correr linters, asegurando que el código que llega a la rama principal siempre se mantenga estable y funcional.

**CD (Entrega Continua / Despliegue Continuo):**  
El despliegue continuo automatiza la entrega del software ya probado hacia un entorno, como staging o producción. Su correcto funcionamiento depende de que CI esté funcionando, ya que CD asume que el código que se entrega ya pasó todas las pruebas de calidad.

---

### 2. Lenguaje, linter y herramienta de cobertura

**Lenguaje:**
- **Java 17**  
  Java es estable, moderno y ampliamente soportado en GitHub Actions, lo que facilita su integración con pipelines de CI/CD.

**Linter:**
- **Checkstyle**: verifica estilo y formato de código.

**Herramienta de cobertura:**
- **JaCoCo**: estándar moderno en Java para medir cobertura de tests.
    - Se integra nativamente con Maven y genera reportes XML compatibles con GitHub Actions.
    - Facilita conocer qué porcentaje del código está siendo probado y ayuda a mantener la calidad del proyecto.

**Justificación general:**  
Java tiene excelente soporte en herramientas de build como Maven y Gradle. Checkstyle y SpotBugs son linters maduros y confiables, mientras que JaCoCo permite automatizar la validación de cobertura dentro del pipeline de CI/CD.

---

### 3. Umbral mínimo recomendado de cobertura

- **Propuesta:** 80–85% de cobertura de código.

**Justificación:**
- Menos de 70% no garantiza un mínimo de confiabilidad en los tests.
- Más del 90% suele ser artificial, obligando a probar código irrelevante.
- Un umbral de 80–85% asegura buena calidad de software sin forzar la creación de tests innecesarios, equilibrando esfuerzo y confiabilidad.
  **PARTE 3 – Uso de `nektos/act`**

---

**PARTE 3**

---

### 1. Qué es `act`

`act` es una herramienta de código abierto que permite **ejecutar workflows de GitHub Actions de manera local** en tu máquina.  
Con `act` puedes probar tus pipelines de CI/CD sin necesidad de hacer push al repositorio ni esperar a que GitHub Actions ejecute los jobs en la nube.  
Esto acelera el desarrollo y la depuración de los workflows, ya que los resultados se pueden ver inmediatamente en la terminal local.

---

### 2. Requisitos

- **Docker**: `act` utiliza contenedores Docker para simular los runners de GitHub Actions.
    - Debes tener Docker instalado y funcionando en tu sistema antes de usar `act`.
- **GitHub Actions workflow**: debe existir un archivo `.github/workflows/ci-quality.yml` (o el workflow que quieras probar) en tu repositorio.

---

### 3. Comando para ejecutar el workflow localmente

Para ejecutar tu workflow de CI/CD definido en GitHub Actions, abre la terminal en la raíz del proyecto y usa:

```bash
act -j build
```
---
 **PARTE 4**

---

### 1. Cómo identificar fallos en los logs

En GitHub Actions, cada paso de tu workflow genera **logs detallados**. Para cada tipo de verificación:

**Linter (Checkstyle):**
- Busca en los logs palabras clave como `ERROR` o `WARN`.
- Checkstyle muestra la **clase, línea y tipo de violación de estilo**.
- Un fallo de linter normalmente detiene el workflow si `failOnViolation` está en `true`.

**Pruebas (JUnit):**
- En el paso de `mvn test` o `mvn verify` se listan los tests ejecutados.
- Fallos aparecen como `FAILED` y se muestran la **clase, método y stacktrace** del error.
- Al final, Maven muestra un resumen: número de tests ejecutados, fallidos y omitidos.

**Cobertura (JaCoCo):**
- El paso `jacoco:check` falla si el porcentaje de cobertura está **por debajo del umbral configurado**.
- Los logs indican el **tipo de contador** (INSTRUCTION, LINE, CLASS), el **valor esperado** y el **valor real**.
- Esto permite identificar qué clases tienen poca cobertura.

---

### 2. Generar un run fallido y uno exitoso

**Run fallido:**
- Introduce un error intencional, por ejemplo:
    - Romper una regla de estilo en el código para que Checkstyle falle.
    - O eliminar un test existente para que `jacoco:check` no cumpla el umbral de cobertura.
- Al ejecutar el workflow, verás:
    - En **Checkstyle:** errores de estilo con `ERROR` y el workflow se detiene.
    - En **Tests:** tests marcados como `FAILED`.
    - En **Cobertura:** mensaje indicando que la cobertura está por debajo del mínimo (`Expected minimum: 0.8, Actual: 0.6`).
- Resultado: el **job termina con “ Failure”** y no se ejecutan los pasos siguientes.

**Run exitoso:**
- Todo el código cumple las reglas de estilo.
- Todos los tests pasan (`OK`) y se genera el reporte de JaCoCo.
- La cobertura está **por encima del umbral** definido.
- Resultado: el **job termina con “ Success”** y se pueden revisar los reportes completos en los logs o como artefactos.

---
**PARTE 5**

---

### 1. Métodos para detectar código generado por IA

Existen varias técnicas para intentar identificar código generado por IA:

**a) Análisis de patrones y estilo de codificación:**
- Herramientas de detección examinan **estilo, nomenclatura de variables, estructuras repetitivas y patrones de indentación** que suelen ser característicos de modelos de lenguaje.
- Por ejemplo, algunas plataformas de detección de código IA buscan frases o comentarios típicos de autogeneración y patrones de código predecibles.

**b) Herramientas basadas en aprendizaje automático:**
- Modelos entrenados con grandes corpus de código pueden **clasificar código como humano o generado por IA**.
- Evaluan probabilidades de que ciertas combinaciones de líneas o funciones aparezcan en código humano versus generado por IA.

---

### 2. Por qué no es posible asegurar al 100% la autoría

- El código generado por IA puede ser **modificado y mezclado con código humano**, haciendo imposible diferenciarlo con certeza absoluta.
- Los estilos de programación pueden variar mucho entre desarrolladores humanos, y los modelos de IA aprenden patrones de humanos reales, lo que **complica distinguir la fuente**.
- No existen marcas o “firmas digitales” confiables que identifiquen la autoría de manera definitiva.

---

### 3. Políticas razonables de uso de IA en educación y calidad de software

1. **Transparencia y citación:**
    - Estudiantes y desarrolladores deben **indicar cuándo se ha usado IA** para generar código, comentarios o documentación.

2. **Uso complementario, no sustituto:**
    - La IA puede ser una **herramienta de apoyo** para sugerencias, refactorización o documentación, pero **no reemplaza el aprendizaje ni la revisión humana**.

3. **Revisión y validación:**
    - Todo código generado por IA debe ser **revisado y testeado por humanos** antes de integrarse al proyecto o entregarse.

4. **Política académica clara:**
    - Definir límites de uso de IA en tareas o evaluaciones. Por ejemplo: se permite **ayuda para ejemplos y aprendizaje**, pero no para entregar código final sin intervención del estudiante.

5. **Monitoreo de calidad:**
    - En proyectos profesionales, usar IA **como apoyo en CI/CD** (tests automáticos, sugerencias de refactor) pero siempre con **validación humana** para mantener la confiabilidad del software.
---
