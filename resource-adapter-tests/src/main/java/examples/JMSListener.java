/*
 * Copyright DataStax, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package examples;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@MessageDriven(
  activationConfig = {
    @ActivationConfigProperty(propertyName = "Configuration", propertyValue = "{}"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = "the-queue")
  }
)
public class JMSListener implements MessageListener {

  @Inject private Event<ReceivedMessage> onJmsMessageEvent;

  @Override
  public void onMessage(final Message message) {
    try {
      System.out.println("Received message " + message);
      onJmsMessageEvent.fire(new ReceivedMessage(message.getBody(String.class)));
    } catch (final Throwable e) {
      e.printStackTrace(System.out);
    }
  }
}
