package pl.sda.spring.customers.dto;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public final class CompanyDto extends CustomerDto {

    private final String name;
    private final String vatNumber;

    public CompanyDto(UUID id, List<AddressDto> addresses, String name, String vatNumber) {
        super(id, addresses);
        requireNonNull(name, vatNumber);
        this.name = name;
        this.vatNumber = vatNumber;
    }

    public String getName() {
        return name;
    }

    public String getVatNumber() {
        return vatNumber;
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
        CompanyDto that = (CompanyDto) o;
        return name.equals(that.name) &&
            vatNumber.equals(that.vatNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), name, vatNumber);
    }
}
