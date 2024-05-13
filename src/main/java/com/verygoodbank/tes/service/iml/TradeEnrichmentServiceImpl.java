package com.verygoodbank.tes.service.iml;

import com.verygoodbank.tes.constant.Constants;
import com.verygoodbank.tes.service.AsyncProcessService;
import com.verygoodbank.tes.service.TradeEnrichmentService;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
@Service
public class TradeEnrichmentServiceImpl implements TradeEnrichmentService {
    private static final Logger LOG = LoggerFactory.getLogger(TradeEnrichmentServiceImpl.class);
    @Autowired
    AsyncProcessService asyncProcessService;

    @Value("${enrich.batch.count}")
    private int BATCH_COUNT;
    @Override
    public String processData(BufferedReader br) throws IOException {
//        result data
        StringBuilder processedData = new StringBuilder();
//        Future list of all batch jobs
        List<Future<List<String>>> futures = new ArrayList<>();
//        temp cache of each batch data after the whole trades data splited
//        just cache line String instead of construt Trade Obj to avoid uneccessary memory wasting
//        use Stirng replacing instead of Obj fields setting
        List<String> batchTrades = new ArrayList<>();
//        each line of the file, indicates a trade data
        String line;
//        cache the title line first for output file tile
        String title = br.readLine();
        processedData.append(title).append(Constants.NEWLINE_CHARACTER);

//        read lines and batch processing by split data into several count of chuncks base on the BATCH_COUNT
        while ((line = br.readLine()) != null) {
            batchTrades.add(line);

//            enough count for a batch
            if (batchTrades.size() >= BATCH_COUNT) {
                LOG.info("Batch Size: {}", batchTrades.size());

//              aysnc processing Product Name & ID mapping with a gloable thread pool
                Future<List<String>> future = asyncProcessService.batchMapProductIdToName(batchTrades);
//                chache Future for no-blocking result get
                futures.add(future);
//                reset chache of batch
                batchTrades = new ArrayList<>();
            }
        }
        // last batch may not has enough count for BATCH_COUNT
        if (CollectionUtils.isNotEmpty(batchTrades)) {
            LOG.info("Batch Size: {}", batchTrades.size());

            Future<List<String>> future = asyncProcessService.batchMapProductIdToName(batchTrades);
            futures.add(future);
        }

//        merge each batch into the final result as a String
        futures.stream().forEach(e -> {
            try {
                List<String> trades = e.get();
                trades.stream().forEach(l -> processedData.append(l).append(Constants.NEWLINE_CHARACTER));
            } catch (InterruptedException | ExecutionException ex) {
                throw new RuntimeException(ex);
            }
        });

        return  processedData.toString();
    }
}
