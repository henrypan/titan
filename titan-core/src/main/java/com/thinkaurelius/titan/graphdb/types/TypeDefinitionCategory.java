package com.thinkaurelius.titan.graphdb.types;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.thinkaurelius.titan.core.*;
import com.thinkaurelius.titan.graphdb.internal.ElementCategory;
import com.thinkaurelius.titan.graphdb.internal.RelationCategory;
import com.tinkerpop.blueprints.Direction;

import java.util.Set;

/**
 * @author Matthias Broecheler (me@matthiasb.com)
 */

public enum TypeDefinitionCategory {

    //Relation Types
    HIDDEN(Boolean.class),
    SORT_KEY(long[].class),
    SORT_ORDER(Order.class),
    TTL(Integer.class),
    SIGNATURE(long[].class),
    MULTIPLICITY(Multiplicity.class),
    DATATYPE(Class.class),
    UNIDIRECTIONAL(Direction.class),

    //General admin
    STATUS(SchemaStatus.class),

    //Index Types
    ELEMENT_CATEGORY(ElementCategory.class),
    INDEX_CARDINALITY(Cardinality.class),
    INTERNAL_INDEX(Boolean.class),
    BACKING_INDEX(String.class),
    INDEXSTORE_NAME(String.class),

    //Consistency Types
    CONSISTENCY_LEVEL(ConsistencyModifier.class),

    //Schema Edges
    RELATIONTYPE_INDEX(),
    CONSISTENCY_MODIFIER(),
    INDEX_FIELD(RelationCategory.EDGE,Parameter[].class);

    public static final Set<TypeDefinitionCategory> PROPERTY_KEY_DEFINITION_CATEGORIES = ImmutableSet.of(HIDDEN, SORT_KEY, SORT_ORDER, TTL, SIGNATURE, MULTIPLICITY, DATATYPE);
    public static final Set<TypeDefinitionCategory> EDGE_LABEL_DEFINITION_CATEGORIES = ImmutableSet.of(HIDDEN, SORT_KEY, SORT_ORDER, TTL, SIGNATURE, MULTIPLICITY, UNIDIRECTIONAL);
    public static final Set<TypeDefinitionCategory> CONSISTENCY_MODIFIER_DEFINITION_CATEGORIES = ImmutableSet.of(CONSISTENCY_LEVEL);
    public static final Set<TypeDefinitionCategory> INDEX_DEFINITION_CATEGORIES = ImmutableSet.of(STATUS, ELEMENT_CATEGORY,INDEX_CARDINALITY,INTERNAL_INDEX, BACKING_INDEX,INDEXSTORE_NAME);



    private final RelationCategory relationCategory;
    private final Class dataType;

    private TypeDefinitionCategory() {
        this(RelationCategory.EDGE,null);
    }

    private TypeDefinitionCategory(Class<?> dataType) {
        this(RelationCategory.PROPERTY, dataType);
    }

    private TypeDefinitionCategory(RelationCategory relCat, Class<?> dataType) {
        Preconditions.checkArgument(relCat!=null && relCat.isProper());
        Preconditions.checkArgument(relCat==RelationCategory.EDGE || dataType !=null);
        this.relationCategory = relCat;
        this.dataType = dataType;
    }

    public boolean hasDataType() {
        return dataType !=null;
    }

    public Class<?> getDataType() {
        Preconditions.checkState(hasDataType());
        return dataType;
    }

    public boolean isProperty() {
        return relationCategory==RelationCategory.PROPERTY;
    }

    public boolean isEdge() {
        return relationCategory==RelationCategory.EDGE;
    }

    public boolean verifyAttribute(Object attribute) {
        Preconditions.checkState(dataType !=null);
        return attribute != null && dataType.equals(attribute.getClass());
    }

    public Object defaultValue(TypeDefinitionMap map) {
        switch(this) {
            case SORT_ORDER: return Order.ASC;
            case STATUS: return SchemaStatus.ENABLED;
            default: return null;
        }
    }

}