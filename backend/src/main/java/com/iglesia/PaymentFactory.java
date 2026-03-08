package com.iglesia;

// feat: apply Factory Pattern — create PaymentFactory to centralize Payment creation
// ADR-04: Factory Pattern — centraliza construcción de Payment, elimina duplicación

public class PaymentFactory {

    private PaymentFactory() {}

    public static Payment forEnrollment(Enrollment enrollment, Course course) {
        Payment payment = new Payment();
        payment.setType(PaymentType.INSCRIPCION_CURSO);
        payment.setAmount(course.getPrice());
        payment.setReferenceId(enrollment.getId());
        return payment;
    }

    public static Payment forOffering(Offering offering) {
        Payment payment = new Payment();
        payment.setType(PaymentType.OFRENDA);
        payment.setAmount(offering.getAmount());
        payment.setReferenceId(offering.getId());
        return payment;
    }
}
