package com.zkc.commerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * 自定义告警
 */
@Slf4j
@Component
public class CustomNotifier extends AbstractEventNotifier {
	
	protected CustomNotifier(InstanceRepository repository) {
		super(repository);
	}
	
	/**
	 * 对事件通知
	 */
	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		return Mono.fromRunnable(() -> {
			if (event instanceof InstanceStatusChangedEvent) {
				log.info("实例状态发生变化: [{}],[{}],[{}]",
						instance.getRegistration().getName(),
						event.getInstance(),
						((InstanceStatusChangedEvent) event).getStatusInfo().getStatus());
			} else {
				log.info("实例信息: [{}],[{}],[{}]",
						instance.getRegistration().getName(),
						event.getInstance(),
						event.getType());
			}
		});
	}
}
