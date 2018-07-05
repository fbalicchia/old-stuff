package org.springframework.data.gremlin.object.core.repository;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.gremlin.object.core.domain.Likes;
import org.springframework.data.gremlin.object.core.domain.Located;
import org.springframework.data.gremlin.object.core.domain.Location;

import java.util.*;
import java.util.stream.StreamSupport;

import static org.junit.Assert.*;


@SuppressWarnings("SpringJavaAutowiringInspection")
public abstract class AbstractEdgeRepositoryTest extends BaseRepositoryTest
{

    @Autowired
    protected LocatedRepository locatedRepository;

    @Test
    public void should_save_simple_edge()
    {

        Likes likes = new Likes(graham, lara);
        graham.getLikes().add(likes);
        lara.getLikes().add(likes);
        likesRepository.save(likes);
        List<Likes> allLikes = Lists.newArrayList();
        CollectionUtils.addAll(allLikes, likesRepository.findAll());
        assertEquals(6, allLikes.size());

    }

    @Test
    public void should_findAll_Located()
    {
        List<Located> located = Lists.newArrayList();

        CollectionUtils.addAll(located, locatedRepository.findAll());
        assertTrue(located.size() > 0);

        for (Located locate : located)
        {
            Assert.assertNotNull(locate.getLocation());
            Assert.assertNotNull(locate.getPerson());
        }
    }

    @Test
    public void should_deleteAll_Located()
    {
        List<Located> located = Lists.newArrayList();

        CollectionUtils.addAll(located, locatedRepository.findAll());
        assertTrue(located.size() > 0);
        located.clear();
        locatedRepository.deleteAll();
        CollectionUtils.addAll(located, locatedRepository.findAll());
        assertEquals(0, located.size());
    }

    @Test
    public void should_save_edge()
    {

        long count = StreamSupport.stream(locatedRepository.findAll().spliterator(), false).count();
        Location loc = locationRepository.save(new Location(35, 165));
        Located located = new Located(new Date(), graham, loc);
        graham.getLocations().add(located);
        locatedRepository.save(located);
        List<Located> newLocated = Lists.newArrayList();
        CollectionUtils.addAll(newLocated, locatedRepository.findAll());
        assertEquals(count + 1, newLocated.size());
    }

    @Test
    public void should_find_by_referenced()
    {

        Likes likes = new Likes(graham, lara);
        likesRepository.save(likes);
        Iterable<Likes> all = likesRepository.findAll();
        Iterable<Likes> found = likesRepository.findByPerson1_FirstName("Graham");
        Collection<Likes> disjunction = CollectionUtils.disjunction(all, found);
        assertEquals(3, disjunction.size());
    }

    @Test
    public void should_find_by_query()
    {
        Likes likes = new Likes(lara, graham);
        likesRepository.save(likes);
        /*Iterator<Likes> query = likesRepository.findByLiking("Lara", "Graham").iterator();*/

        List<Likes> byLiking = likesRepository.findByLiking("Lara", "Graham");
        assertEquals(1, byLiking.size());
        assertEquals(likes, byLiking.get(0));
    }
}
