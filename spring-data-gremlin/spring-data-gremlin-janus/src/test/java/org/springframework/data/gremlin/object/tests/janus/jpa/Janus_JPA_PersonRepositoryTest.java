package org.springframework.data.gremlin.object.tests.janus.jpa;

import org.springframework.data.gremlin.object.jpa.repository.AbstractPersonRepositoryTest;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = Janus_JPA_TestConfiguration.class)
@SuppressWarnings("SpringJavaAutowiringInspection")
public class Janus_JPA_PersonRepositoryTest extends AbstractPersonRepositoryTest {

    //    @Autowired
    //    protected NativePersonRepository nativePersonRepository;

    //    @Test
    //    public void testDeleteAllExcept() throws Exception {
    //        int count = ((NativePersonRepository)repository).deleteAllExceptUser("Lara");
    //        assertEquals(4, count);
    //
    //        Iterable<Person> persons = repository.findAll();
    //        assertNotNull(persons);
    //        Iterator<Person> iterator = persons.iterator();
    //        assertNotNull(iterator);
    //        assertNotNull(iterator.next());
    //        assertFalse(iterator.hasNext());
    //    }
}
