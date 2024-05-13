package com.verygoodbank.tes.service.iml;

import com.verygoodbank.tes.holder.ProductCacheHolder;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AsyncProcessServiceImplTest {
    @InjectMocks
    private AsyncProcessServiceImpl serviceUnderTest;
    @Mock
    private ProductCacheHolder mockProductHolder;

    @Test
   public void testBatchMapProductIdToName() {
        List<String> mockBatchTrades = new ArrayList<>(){
            {add("20160101,1,EUR,10.0");}
            {add("20160101,2,EUR,20.1");}
        };

        when(mockProductHolder.getProductName(any())).thenReturn("NAME");

        Future<List<String>> res = serviceUnderTest.batchMapProductIdToName(mockBatchTrades);

        Assert.assertTrue(res != null);
    }
}