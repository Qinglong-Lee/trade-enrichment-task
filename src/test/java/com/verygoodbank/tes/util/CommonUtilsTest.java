package com.verygoodbank.tes.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CommonUtilsTest {

    @Test
    public void extractId() {
        String id = CommonUtils.extractId("20160101,3,EUR,30.34");
        Assert.assertTrue("3".equals(id));
    }

    @Test
    public void replaceIdToName() {
        String newLine = CommonUtils.replaceIdToName("20160101,3,EUR,30.34", "MOCK_NAME");
        Assert.assertTrue("20160101,MOCK_NAME,EUR,30.34".equals(newLine));
    }

    @Test
    public void dateFormatValidation() {
        Assert.assertTrue(CommonUtils.dateFormatValidation("20160101"));
        Assert.assertFalse(CommonUtils.dateFormatValidation("2016-01-01"));
    }
}