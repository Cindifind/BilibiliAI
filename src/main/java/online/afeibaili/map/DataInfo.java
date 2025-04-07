package online.afeibaili.map;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@Data
public class DataInfo {
    private String group;

    @JsonProperty("business_id")
    private int businessId;

    @JsonProperty("refresh_row_factor")
    private double refreshRowFactor;

    @JsonProperty("refresh_rate")
    private int refreshRate;

    @JsonProperty("max_delay")
    private int maxDelay;

    private String token;


    @JsonProperty("host_list")
    private List<Host> hostList;
}
