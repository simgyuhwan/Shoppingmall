package com.growing.sgh.common.prop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("file")
public class FileProperties {
    private String itemImgLocation;
}
