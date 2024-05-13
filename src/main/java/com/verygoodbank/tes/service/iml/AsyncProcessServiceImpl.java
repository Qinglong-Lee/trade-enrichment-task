package com.verygoodbank.tes.service.iml;

import com.verygoodbank.tes.holder.ProductCacheHolder;
import com.verygoodbank.tes.service.AsyncProcessService;
import com.verygoodbank.tes.util.CommonUtils;
import com.verygoodbank.tes.web.controller.TradeEnrichmentController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
@Service
public class AsyncProcessServiceImpl implements AsyncProcessService {
    private static final Logger LOG = LoggerFactory.getLogger(AsyncProcessServiceImpl.class);
    @Autowired
    ProductCacheHolder productCacheHolder;

    @Async("globalServerExecutor")
    @Override
    public Future<List<String>> batchMapProductIdToName(List<String> batchTrades) {
        List<String> res = new ArrayList<>();
        batchTrades.stream()
                .forEach(l -> {
                    String date = CommonUtils.extractDate(l);
                    if(CommonUtils.dateFormatValidation(date)) {
                        res.add(CommonUtils.replaceIdToName(l,
                                productCacheHolder.getProductName(CommonUtils.extractId(l))));
                    } else {
                        LOG.error("Date format Wrong: {}", date);
                    }
                });

        return new AsyncResult<>(res);
    }
}
