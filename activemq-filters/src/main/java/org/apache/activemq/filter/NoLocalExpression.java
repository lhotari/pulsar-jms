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
package org.apache.activemq.filter;

import jakarta.jms.JMSException;
import java.io.IOException;
import org.apache.activemq.util.JMSExceptionSupport;

public class NoLocalExpression implements BooleanExpression {

  private final String connectionId;

  public NoLocalExpression(String connectionId) {
    this.connectionId = connectionId;
  }

  public boolean matches(MessageEvaluationContext message) throws JMSException {
    try {
      if (message.isDropped()) {
        return false;
      }
      String messageConnectionId = message.getMessage().getProducerId().getConnectionId();
      return !connectionId.equals(messageConnectionId);
    } catch (IOException e) {
      throw JMSExceptionSupport.create(e);
    }
  }

  public Object evaluate(MessageEvaluationContext message) throws JMSException {
    return matches(message) ? Boolean.TRUE : Boolean.FALSE;
  }
}
