package com.mihai.foursquare.domain;

import com.mihai.foursquare.util.Operation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RequestParams {
	
	private String location;
	
	private Integer radius;
	
	private Integer limit;

    private Operation operation;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final RequestParams that = (RequestParams) o;

        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (radius != null ? !radius.equals(that.radius) : that.radius != null) return false;
        if (limit != null ? !limit.equals(that.limit) : that.limit != null) return false;
        return operation == that.operation;

    }

    @Override
    public int hashCode() {
        int result = location != null ? location.hashCode() : 0;
        result = 31 * result + (radius != null ? radius.hashCode() : 0);
        result = 31 * result + (limit != null ? limit.hashCode() : 0);
        result = 31 * result + (operation != null ? operation.hashCode() : 0);
        return result;
    }
}
