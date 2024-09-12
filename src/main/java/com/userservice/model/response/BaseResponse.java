package com.userservice.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    protected long totalItems;
    protected long totalPages;
}
