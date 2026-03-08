package com.iglesia;

// feat: apply SRP to EnrollmentController — extract EnrollmentService
// ADR-01: Controller ahora solo maneja HTTP, delega toda la lógica a EnrollmentService

import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @PostMapping
    public EnrollmentResponse create(@RequestBody EnrollmentRequest request) {
        return enrollmentService.create(request);
    }

    @PreAuthorize("hasRole('ADMIN') or hasRole('CLIENT')")
    @GetMapping
    public List<EnrollmentResponse> list() {
        return enrollmentService.list();
    }

    public record EnrollmentRequest(
        @NotNull Long personId,
        @NotNull Long courseId
    ) {}

    public record EnrollmentResponse(
        Long id,
        Long personId,
        String personName,
        Long courseId,
        String courseName,
        String status,
        Long paymentId,
        String paymentStatus
    ) {
        public static EnrollmentResponse from(Enrollment enrollment, Payment payment) {
            String personName = enrollment.getPerson().getFirstName() + " " + enrollment.getPerson().getLastName();
            String paymentStatus = payment == null ? null : payment.getStatus().name();
            return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getPerson().getId(),
                personName,
                enrollment.getCourse().getId(),
                enrollment.getCourse().getName(),
                enrollment.getStatus().name(),
                enrollment.getPaymentId(),
                paymentStatus
            );
        }
    }
}
