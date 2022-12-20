package ca.ulaval.glo4002.cafe.ui.rest.DTO;

public class ErrorDTO {
    private String error;
    private String description;

    public ErrorDTO() {}

    public ErrorDTO(String error, String description) {
        this.error = error;
        this.description = description;
    }

    public String getError() {
        return this.error;
    }

    public String getDescription() {
        return this.description;
    }
}
