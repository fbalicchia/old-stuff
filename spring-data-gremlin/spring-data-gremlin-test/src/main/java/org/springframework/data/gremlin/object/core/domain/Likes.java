package org.springframework.data.gremlin.object.core.domain;

import lombok.Getter;
import org.springframework.data.gremlin.annotation.Edge;
import org.springframework.data.gremlin.annotation.FromVertex;
import org.springframework.data.gremlin.annotation.Id;
import org.springframework.data.gremlin.annotation.ToVertex;

import java.util.Date;

/**
 * Created by gman on 14/09/15.
 */
@Edge
public class Likes {

    @Id
    private String id;

    @Getter
    private Date date = new Date();

    @FromVertex @Getter
    private Person person1;

    @ToVertex @Getter
    private Person person2;

    public Likes() {
    }

    public Likes(Person person1, Person person2) {
        this.person1 = person1;
        this.person2 = person2;
        person1.getLikes().add(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Likes likes = (Likes) o;

        return !(id != null ? !id.equals(likes.id) : likes.id != null);

    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
