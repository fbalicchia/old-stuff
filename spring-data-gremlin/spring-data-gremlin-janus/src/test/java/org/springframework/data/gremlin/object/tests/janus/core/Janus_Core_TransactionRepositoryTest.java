package org.springframework.data.gremlin.object.tests.janus.core;

import org.springframework.data.gremlin.object.core.repository.AbstractTransactionRepositoryTest;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = Janus_Core_TestConfiguration.class)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Janus_Core_TransactionRepositoryTest extends AbstractTransactionRepositoryTest {

}
