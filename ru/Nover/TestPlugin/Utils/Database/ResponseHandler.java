package ru.Nover.TestPlugin.Utils.Database;

public interface ResponseHandler<H, R> {
    R handleResponse(H handle) throws Exception;
}
