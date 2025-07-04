package online.map;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

@Data
public class Host {
    private String host;
    private int port;

    @JsonProperty("wss_port")
    private int wssPort;

    @JsonProperty("ws_port")
    private int wsPort;
}
