package com.thinkaurelius.titan.diskstorage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.thinkaurelius.titan.diskstorage.common.DistributedStoreManager;
import com.thinkaurelius.titan.diskstorage.common.DistributedStoreManager.Deployment;
import com.thinkaurelius.titan.diskstorage.keycolumnvalue.KeyColumnValueStore;
import com.thinkaurelius.titan.diskstorage.keycolumnvalue.KeyRange;
import com.thinkaurelius.titan.testcategory.OrderedKeyStoreTests;

public abstract class DistributedStoreManagerTest {
    
    protected DistributedStoreManager manager;
    protected KeyColumnValueStore store;
    
    @Test
    @Category({ OrderedKeyStoreTests.class })
    public void testGetDeployment() {
        assertEquals(Deployment.LOCAL, manager.getDeployment());
    }
    
    @Test
    @Category({ OrderedKeyStoreTests.class })
    public void testGetLocalKeyPartition() throws StorageException {
        List<KeyRange> local = store.getLocalKeyPartition();
        assertNotNull(local);
        assertEquals(1, local.size());
        assertNotNull(local.get(0).getStart());
        assertNotNull(local.get(0).getEnd());
        System.err.println("range: " + local.get(0).getStart() + ":" + local.get(0).getStart().length() + " " + local.get(0).getEnd() + ":" + local.get(0).getEnd().length());
    }

}