package org.springframework.data.gremlin.schema.property;


import org.apache.tinkerpop.gremlin.structure.Element;
import org.springframework.data.gremlin.annotation.Index.IndexType;
import org.springframework.data.gremlin.repository.GremlinGraphAdapter;
import org.springframework.data.gremlin.schema.GremlinSchema;
import org.springframework.data.gremlin.schema.property.accessor.GremlinPropertyAccessor;
import org.springframework.data.gremlin.schema.property.mapper.GremlinPropertyMapper;
import org.springframework.data.gremlin.schema.property.mapper.GremlinStandardPropertyMapper;

import java.util.Map;


/**
 * <p>
 * Defines a property of a mapped Class.
 * </p>
 * <p>
 * Each GremlinProperty has a {@link GremlinPropertyAccessor} defining how
 * the Class' Field is accessed, and a {@link GremlinPropertyMapper} defining how the
 * property is mapped to the database.
 * </p>
 * <p>
 * The property also has a name, type and possibly an index.
 * </p>
 *
 * @author Gman
 */
public class GremlinProperty<C>
{

    private String name;

    private GremlinSchema<?> schema;

    private GremlinPropertyAccessor accessor;

    private GremlinPropertyMapper propertyMapper;

    private Class<C> type;

    private IndexType index = IndexType.NONE;

    private String indexName;

    public GremlinProperty(Class<C> cls, String name, IndexType index, String indexName)
    {
        this(cls, name, new GremlinStandardPropertyMapper());
        this.index = index;
        this.indexName = indexName;
    }

    public GremlinProperty(Class<C> cls, String name)
    {
        this(cls, name, new GremlinStandardPropertyMapper());
    }

    public GremlinProperty(Class<C> cls, String name, GremlinPropertyMapper propertyMapper)
    {
        this.type = cls;
        this.name = name;
        this.propertyMapper = propertyMapper;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public GremlinPropertyAccessor getAccessor()
    {
        return accessor;
    }

    public void setAccessor(GremlinPropertyAccessor accessor)
    {
        this.accessor = accessor;
    }

    public Class<C> getType()
    {
        return type;
    }

    public void setType(Class<C> type)
    {
        this.type = type;
    }

    public IndexType getIndex()
    {
        return index;
    }

    public void setIndex(IndexType index)
    {
        this.index = index;
    }

    public String getIndexName()
    {
        return indexName;
    }

    public void setIndexName(String indexName)
    {
        this.indexName = indexName;
    }

    public <V> void setSchema(GremlinSchema<V> schema)
    {
        this.schema = schema;
    }

    public GremlinSchema getSchema()
    {
        return schema;
    }

    @Override
    public String toString()
    {
        final StringBuilder sb = new StringBuilder("GremlinProperty{");
        sb.append("name='").append(name).append('\'');
        sb.append(", accessor=").append(accessor);
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }

    public void copyToVertex(GremlinGraphAdapter graphAdapter, Element element, Object val, Map<Object, Object> cascadingSchemas)
    {
        propertyMapper.copyToVertex(this, graphAdapter, element, val, cascadingSchemas);
    }

    public <V> Object loadFromVertex(GremlinGraphAdapter graphAdapter, Element element, Map<GremlinSchema, V> cascadingSchemas)
    {
        return propertyMapper.loadFromVertex(this, graphAdapter, element, cascadingSchemas);
    }

}
