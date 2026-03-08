package com.iglesia;

// feat: apply DTO Pattern to PersonController — create PersonDTO to decouple domain from HTTP response
// ADR-06: DTO Pattern — desacopla el modelo JPA de la respuesta HTTP, evita exponer relaciones LAZY

public record PersonDTO(
    Long id,
    String firstName,
    String lastName,
    String document,
    String phone,
    String email,
    Long churchId,
    String churchName
) {
    public static PersonDTO from(Person person) {
        return new PersonDTO(
            person.getId(),
            person.getFirstName(),
            person.getLastName(),
            person.getDocument(),
            person.getPhone(),
            person.getEmail(),
            person.getChurch().getId(),
            person.getChurch().getName()
        );
    }
}
