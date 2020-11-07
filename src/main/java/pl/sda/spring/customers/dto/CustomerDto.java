package pl.sda.spring.customers.dto;

import static pl.sda.spring.customers.util.Precondition.requireNonNull;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class CustomerDto {

    private final UUID id;
    private final List<AddressDto> addresses;

    protected CustomerDto(UUID id, List<AddressDto> addresses) {
        requireNonNull(id, addresses);
        this.id = id;
        this.addresses = addresses;
    }

    public UUID getId() {
        return id;
    }

    public List<AddressDto> getAddresses() {
        return addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CustomerDto that = (CustomerDto) o;
        return id.equals(that.id) &&
            addresses.equals(that.addresses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addresses);
    }
}
