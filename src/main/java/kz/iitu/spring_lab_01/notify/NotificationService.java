package kz.iitu.spring_lab_01.notify;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class NotificationService {

    private final Notifier primary;              // @Primary -> EmailNotifier
    private final Notifier console;              // указан явно через @Qualifier
    private final List<Notifier> all;            // все, упорядочены через @Order
    private final Map<String, Notifier> byName;  // ключ — имя бина

    public NotificationService(
            Notifier primary,
            @Qualifier("console") Notifier console,
            List<Notifier> all,
            Map<String, Notifier> byName) {
        this.primary = primary;
        this.console = console;
        this.all = all;
        this.byName = byName;
    }

    public String viaPrimary(String message) {
        return primary.send(message);
    }

    public String viaConsole(String message) {
        return console.send(message);
    }

    public List<String> viaAll(String message) {
        return all.stream().map(n -> n.send(message)).toList();
    }

    public List<String> names() {
        return all.stream().map(Notifier::channel).toList();
    }
}