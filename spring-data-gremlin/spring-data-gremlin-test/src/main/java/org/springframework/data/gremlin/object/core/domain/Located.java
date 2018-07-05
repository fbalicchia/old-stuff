package org.springframework.data.gremlin.object.core.domain;

import lombok.Data;
import org.springframework.data.gremlin.annotation.*;

import java.util.Date;

/**
 * Created by gman on 3/08/15.
 */
@Data
@Edge("was_located")
public class Located {

    @Id
    private String id;

    @Property("location_date")
    private Date date;

    @FromVertex
    private Person person;

    @ToVertex
    private Location location;

    public Located() {
    }

    public Located(Date date, Person person, Location location) {
        this.date = date;
        this.person = person;
        this.location = location;
    }
}
