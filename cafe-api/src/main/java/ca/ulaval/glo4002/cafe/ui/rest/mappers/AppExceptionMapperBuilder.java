package ca.ulaval.glo4002.cafe.ui.rest.mappers;

import ca.ulaval.glo4002.cafe.ui.rest.DTO.ErrorDTO;
import jakarta.ws.rs.core.Response;

public class AppExceptionMapperBuilder {
    public static Response buildResponse(RuntimeException exception, int status, String errorCode) {
        return Response
                .status(status)
                .entity(AppExceptionMapperBuilder.buildErrorDTO(exception, errorCode))
                .build();
    }

    private static ErrorDTO buildErrorDTO(RuntimeException exception, String errorCode) {
        return new ErrorDTO(
                errorCode,
                exception.getMessage()
        );
    }
}
