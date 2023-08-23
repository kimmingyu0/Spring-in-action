package tacos.messaging;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.jms.JMSException;
import jakarta.jms.Message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import tacos.Order;

@Slf4j
@Service

public class JmsOrderMessagingService implements OrderMessagingService {

  private JmsTemplate jms;

  @Autowired
  public JmsOrderMessagingService(JmsTemplate jms) {
    this.jms = jms;
  }

  @Override
  public void sendOrder(Order order) {
    log.info("SEND ORDER:  " + order);
    jms.convertAndSend("tacocloud.order.queue", order,
        this::addOrderSource);
  }
  
  private Message addOrderSource(Message message) throws JMSException {
    message.setStringProperty("X_ORDER_SOURCE", "WEB");
    return message;
  }

}
