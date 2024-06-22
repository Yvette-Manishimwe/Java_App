package com.ecommerce.ne.dto;

import com.ecommerce.ne.entity.Student;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class LaptopDTO {
    @NotBlank(message = "Brand must not be null")
    private String brand;
    @NotBlank(message = "Sn must not be null")
    private String sn;
    private int studentId;
}
