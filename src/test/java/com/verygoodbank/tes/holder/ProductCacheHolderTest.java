package com.verygoodbank.tes.holder;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;
import java.util.concurrent.ConcurrentHashMap;

@RunWith(MockitoJUnitRunner.class)
public class ProductCacheHolderTest {
    @InjectMocks
    private ProductCacheHolder holderUnderTest;

    @Test
    public void getProductName() {
        ConcurrentHashMap<String, String> productMap = new ConcurrentHashMap<>();
        productMap.put("1", "NAME1");
        productMap.put("2", "NAME2");

        ReflectionTestUtils.setField(holderUnderTest, "productMap", productMap);

        String name1 = holderUnderTest.getProductName("1");
        Assert.assertTrue("NAME1".equals(name1));

        String nameMissing = holderUnderTest.getProductName("3");
        Assert.assertTrue("Missing Product Name".equals(nameMissing));
    }
}