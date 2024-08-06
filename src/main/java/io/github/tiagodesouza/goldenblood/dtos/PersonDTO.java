package io.github.tiagodesouza.goldenblood.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PersonDTO(
        @JsonProperty("nome") String name,
        @JsonProperty("cpf") String cpf,
        @JsonProperty("rg") String rg,
        @JsonProperty("data_nasc") String birthDate,
        @JsonProperty("sexo") String gender,
        @JsonProperty("mae") String mother,
        @JsonProperty("pai") String father,
        @JsonProperty("email") String email,
        @JsonProperty("cep") String postalCode,
        @JsonProperty("endereco") String street,
        @JsonProperty("numero") int number,
        @JsonProperty("bairro") String neighborhood,
        @JsonProperty("cidade") String city,
        @JsonProperty("estado") String state,
        @JsonProperty("telefone_fixo") String landline,
        @JsonProperty("celular") String mobile,
        @JsonProperty("altura") double height,
        @JsonProperty("peso") int weight,
        @JsonProperty("tipo_sanguineo") String bloodType) {
}
