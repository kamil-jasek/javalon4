package pl.sda.spring.customers.dto;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class PersonDto extends CustomerDto {

    private final String firstName;
    private final String lastName;
    private final String pesel;

    public PersonDto(UUID id, List<AddressDto> addresses, String firstName, String lastName, String pesel) {
        super(id, addresses);
        requireNonNull(firstName, lastName, pesel);
        this.firstName = firstName;
        this.lastName = lastName;
        this.pesel = pesel;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPesel() {
        return pesel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        PersonDto personDto = (PersonDto) o;
        return firstName.equals(personDto.firstName) &&
            lastName.equals(personDto.lastName) &&
            pesel.equals(personDto.pesel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, pesel);
    }
}
