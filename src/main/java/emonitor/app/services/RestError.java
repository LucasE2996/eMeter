/*
 * @(#)RestError.java 1.0 10/04/2018
 */

package emonitor.app.services;

import lombok.Getter;

/**
 * A classe <code>RestError</code> é uma abstração de erros ocorridos
 * à REST API
 *
 * @author Lucas Rosa
 * @version 1.0 10/04/2018
 */
public class RestError {

    @Getter private int code;
    @Getter private String message;

    public RestError(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

