package com.example.sahtyapp1.Response;

public class MessageResponse {
    private Long idUser;


    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public MessageResponse(String message , Long idUser) {
        this.message = message;
        this.idUser=idUser;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
