package kz.iitu.spring_lab_01.notify;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component("sms")
@Order(3)
public class SmsNotifier implements Notifier {

    private static final Logger log = LoggerFactory.getLogger(SmsNotifier.class);

    @Override
    public String send(String message) {
        log.info("SMS >> {}", message);
        return "sms:" + message;
    }

    @Override
    public String channel() {
        return "sms";
    }
}
