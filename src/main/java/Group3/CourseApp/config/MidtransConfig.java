package Group3.CourseApp.config;

import com.midtrans.ConfigFactory;
import com.midtrans.Config;
import com.midtrans.service.MidtransSnapApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MidtransConfig {

    @Value("${midtrans.server-key}")
    private String serverKey;

    @Value("${midtrans.client-key}")
    private String clientKey;

    @Bean
    public MidtransSnapApi midtransSnapApi() {
        Config config = Config.builder()
                .setServerKey(serverKey)
                .setClientKey(clientKey)
                .setIsProduction(false)
                .build();
        return new ConfigFactory(config).getSnapApi();
    }
}
