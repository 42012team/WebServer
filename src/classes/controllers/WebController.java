package classes.controllers;

import classes.processors.MainProcessor;
import classes.request.RequestDTO;
import classes.response.ResponseDTO;

public class WebController {
    private MainProcessor processor;

    public WebController() {
        processor = new MainProcessor();

    }

    public ResponseDTO identifyObject(RequestDTO transportObject) {
        ResponseDTO response = processor.processRequest(transportObject);
        return response;
    }
}
