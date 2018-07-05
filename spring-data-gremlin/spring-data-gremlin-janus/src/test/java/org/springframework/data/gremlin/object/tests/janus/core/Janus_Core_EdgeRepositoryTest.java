package org.springframework.data.gremlin.object.tests.janus.core;


import org.springframework.data.gremlin.object.core.repository.AbstractEdgeRepositoryTest;
import org.springframework.test.context.ContextConfiguration;


@ContextConfiguration(classes = Janus_Core_TestConfiguration.class)
public class Janus_Core_EdgeRepositoryTest extends AbstractEdgeRepositoryTest
{

//    @Test
//    @Override
//    public void should_find_by_query() throws ScriptException
//    {
//        Likes likes = new Likes(lara, graham);
//        likesRepository.save(likes);
//
//        String test = "graph.V().has('firstName', 'lara').outE('Likes').as('x').inV.filter{it.firstName == 'lara'}.back('x')";
//        //DefaultGraphTraversal obj = (DefaultGraphTraversal) engine.eval(test);
//
//
//
//
//        //        assertTrue(query.hasNext());
////        assertEquals(likes, query.next());
////        assertFalse(query.hasNext());
//    }

}
