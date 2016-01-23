package com.andigital.foursquare.dto;

import com.andigital.foursquare.util.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.Size;

@AllArgsConstructor
@Getter
public class RequestParamsDTO {

	@NotBlank
	@Size(min = 3, max = 15)
	private String location;

	@NotBlank
	@NumberFormat
    @Size(min = 1, max = 1000)
	private Integer radius;

	@NotBlank
	@NumberFormat
    @Size(min = 1, max = 50)
	private Integer limit;

	@NotBlank
	private Operation operation;
}
