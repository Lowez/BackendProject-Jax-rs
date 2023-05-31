package org.example.product.request;

import jakarta.validation.constraints.NotBlank;

public class ProductRequest {

    @NotBlank
    private String message;

    public ProductRequest(String message) {
        this.message = message;
    }

    public ProductRequest() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
