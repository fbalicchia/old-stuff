package org.springframework.data.gremlin.object.tests.janus.jpa;

import org.springframework.data.gremlin.object.jpa.repository.AbstractTransactionRepositoryTest;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = Janus_JPA_TestConfiguration.class)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Janus_JPA_TransactionRepositoryTest extends AbstractTransactionRepositoryTest {

}
