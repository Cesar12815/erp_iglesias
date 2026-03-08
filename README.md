# ERP Iglesias — Parcial Arquitectura de Software

Sistema ERP para gestión de iglesias: personas, cursos, inscripciones, ofrendas y pagos.

## Stack
- **Backend:** Java 17 + Spring Boot 3.2.3 + Spring Security + JWT
- **Base de datos:** PostgreSQL 16
- **Frontend:** Angular 17 + Angular Material
- **Contenedores:** Docker + Docker Compose

## Correr el proyecto

```bash
docker-compose up --build
```

| Servicio   | URL                        |
|------------|----------------------------|
| Frontend   | http://localhost:4200      |
| Backend    | http://localhost:8080/api  |
| PostgreSQL | localhost:5432/iglesiadmin |

**Credenciales por defecto:** `admin@iglesia.com` / `admin123`

---

## Cambios arquitectónicos implementados (Parcial)

### Cambio 1 — ADR-01: SRP en EnrollmentController
**Commit:** `feat: apply SRP to EnrollmentController — extract EnrollmentService`
- Se extrajo toda la lógica de negocio a `EnrollmentService.java`
- El Controller ahora solo maneja HTTP (recibe request, llama service, retorna response)
- Archivos: `EnrollmentService.java` (nuevo), `EnrollmentController.java` (refactorizado)

### Cambio 2 — ADR-08: ISP en ApiService del frontend
**Commit:** `feat: apply ISP to ApiService — split into domain-specific Angular services`
- `api.service.ts` (177 líneas, 1 servicio) → 7 servicios especializados en `src/app/services/`
- Archivos nuevos: `church.service.ts`, `people.service.ts`, `course.service.ts`, `enrollment.service.ts`, `offering.service.ts`, `payment.service.ts`, `dashboard.service.ts`, `models.ts`

### Cambio 3 — ADR-04: Factory Pattern para Payment
**Commit:** `feat: apply Factory Pattern — create PaymentFactory to centralize Payment creation`
- Se creó `PaymentFactory.java` con métodos `forEnrollment()` y `forOffering()`
- Elimina duplicación: la construcción de Payment ya no está repetida en dos Controllers
- Archivos: `PaymentFactory.java` (nuevo)

### Cambio 4 — ADR-03: Repository Pattern en OfferingRepository
**Commit:** `feat: apply Repository Pattern — add semantic query methods to OfferingRepository`
- Se añadió `sumAmountByCreatedAtBetween()` con `@Query` JPQL
- El Dashboard ya no construye fechas manualmente — el repositorio encapsula la consulta
- Archivos: `OfferingRepository.java` (modificado)

### Cambio 5 — ADR-06: DTO Pattern en PersonController
**Commit:** `feat: apply DTO Pattern to PersonController — create PersonDTO to decouple domain from HTTP response`
- Se creó `PersonDTO.java` como clase independiente con `churchId` y `churchName`
- El Controller retorna DTO en lugar de entidad JPA directamente
- Archivos: `PersonDTO.java` (nuevo), `PersonController.java` (modificado)
