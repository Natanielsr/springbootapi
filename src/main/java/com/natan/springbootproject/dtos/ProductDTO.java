package com.natan.springbootproject.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO(
        @NotBlank(message = "The name product cannot be empty") String name,
        @NotNull(message = "The price product cannot be null") BigDecimal price
) {}
