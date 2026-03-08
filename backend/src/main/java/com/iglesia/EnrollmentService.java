package com.iglesia;

// feat: apply SRP to EnrollmentController — extract EnrollmentService
// ADR-01: Single Responsibility Principle

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final PersonRepository personRepository;
    private final CourseRepository courseRepository;
    private final PaymentRepository paymentRepository;
    private final ChurchRepository churchRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository,
                             PersonRepository personRepository,
                             CourseRepository courseRepository,
                             PaymentRepository paymentRepository,
                             ChurchRepository churchRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
        this.paymentRepository = paymentRepository;
        this.churchRepository = churchRepository;
    }

    public EnrollmentController.EnrollmentResponse create(EnrollmentController.EnrollmentRequest request) {
        Church church = requireChurch();

        Person person = personRepository.findById(request.personId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Persona no encontrada"));
        Course course = courseRepository.findById(request.courseId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Curso no encontrado"));

        if (!person.getChurch().getId().equals(church.getId())
            || !course.getChurch().getId().equals(church.getId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Datos no pertenecen a la iglesia");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setPerson(person);
        enrollment.setCourse(course);
        enrollment.setStatus(EnrollmentStatus.PENDIENTE);
        enrollmentRepository.save(enrollment);

        // Usa PaymentFactory — ADR-04
        Payment payment = PaymentFactory.forEnrollment(enrollment, course);
        paymentRepository.save(payment);

        enrollment.setPaymentId(payment.getId());
        enrollmentRepository.save(enrollment);

        return EnrollmentController.EnrollmentResponse.from(enrollment, payment);
    }

    public List<EnrollmentController.EnrollmentResponse> list() {
        Church church = requireChurch();
        return enrollmentRepository.findAllByPersonChurchId(church.getId())
            .stream()
            .map(enrollment -> {
                Payment payment = enrollment.getPaymentId() != null
                    ? paymentRepository.findById(enrollment.getPaymentId()).orElse(null)
                    : null;
                return EnrollmentController.EnrollmentResponse.from(enrollment, payment);
            })
            .toList();
    }

    private Church requireChurch() {
        return churchRepository.findAll().stream().findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Debe registrar una iglesia primero"));
    }
}
