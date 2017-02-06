package classes.controllers;

import classes.processors.MainProcessor;
import classes.processors.RequestProcessor;
import classes.request.RequestDTO;
import classes.response.ResponseDTO;

import java.util.Map;

public class WebController {
    private MainProcessor processor;

    public WebController(Map<String, RequestProcessor> processorByType) {
        processor = new MainProcessor(processorByType);
    }

    public ResponseDTO indentifyObject(RequestDTO transportObject) {
        ResponseDTO response = processor.processRequest(transportObject);
              return response;
    }
}
