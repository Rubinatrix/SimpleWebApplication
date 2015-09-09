package server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SimpleRESTController {

    @RequestMapping(value = "/")
    public ResponseEntity<String> testConnection() {

        return new ResponseEntity<String>("Web application is running!", HttpStatus.OK);
    }

    @RequestMapping(value = "/json")
    public ResponseEntity<ServerResponse> testJSONResponse() {

        ServerResponse response = new ServerResponse();
        response.setContent("Some content of a JSON body");

        return new ResponseEntity<ServerResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity<ServerResponse> proceedPOSTRequest(@RequestBody ClientRequestBody requestBody) {

        ServerResponse response = new RequestConverter(requestBody).getResponse();

        return new ResponseEntity<ServerResponse>(response, HttpStatus.OK);
    }
}