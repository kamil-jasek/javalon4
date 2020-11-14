package pl.sda.spring.customers.dto;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use = Id.NAME, property = "type")
@JsonSubTypes({
    @Type(value = CreateCompanyDto.class, name = "COMPANY"),
    @Type(value = CreatePersonDto.class, name = "PERSON")
})
public abstract class CreateCustomerDto {

    public enum Type {
        COMPANY, PERSON;
    }

    private final Type type;

    protected CreateCustomerDto(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }
}
