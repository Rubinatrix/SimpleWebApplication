package server;

import java.util.HashMap;
import java.util.Map;

// the main purpose of this class - to take request body and return proper server response instead
// we can change the algorithm of conversion without changing other classes

public class RequestConverter {
    private ClientRequestBody requestBody;

    public RequestConverter(ClientRequestBody requestBody) {
        this.requestBody = requestBody;
    }

    private String convertContent(String content) {

        StringBuilder result = new StringBuilder();

        //get array of all entries
        String[] entries = content.split("\\s+");

        Map<String, Integer> map = new HashMap<>();
        for (String entry : entries) {
            // sometimes we get empty string as first element of the map; we will ignore it
            if (!entry.equals("")) {
                Integer count = map.get(entry);
                if (count == null) {
                    map.put(entry, 1);
                } else {
                    map.put(entry, count + 1);
                }
            }
        }

        //build our response content according to the Example format
        if (!map.isEmpty()) {
            for (String entry : map.keySet()) {
                result.append(entry).append(" - ").append(map.get(entry)).append("\n");
            }
            result.append("\n");
        }
        result.append("Unique: ").append(map.size());

        return result.toString();
    }

    public ServerResponse getResponse() {
        ServerResponse response = new ServerResponse();
        try {
            // if request body is null than our response content will be empty string
            if (requestBody != null) {
                response.setContent(convertContent(requestBody.getContent()));
            }
        } catch (Exception e) {
            // return error code 1 and some description for every type of Exception
            response.setErrorCode(1);
            response.setErrorDescription("Unidentified request converting error");
        }
        return response;
    }
}
