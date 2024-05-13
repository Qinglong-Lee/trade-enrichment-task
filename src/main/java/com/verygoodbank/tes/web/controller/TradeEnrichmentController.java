package com.verygoodbank.tes.web.controller;

import com.verygoodbank.tes.service.TradeEnrichmentService;
import com.verygoodbank.tes.util.CommonUtils;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedReader;
import java.io.IOException;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class TradeEnrichmentController {
    private static final Logger LOG = LoggerFactory.getLogger(TradeEnrichmentController.class);
    @Value("${enrich.output.file-name}")
    private String OUTPUT_FILENAME;
    @Autowired
    TradeEnrichmentService tradeEnrichmentService;

    @PostMapping(value = "/enrich", consumes = { "multipart/form-data" })
    public ResponseEntity<byte[]> tradeEnrichment(@RequestPart("file") MultipartFile file) throws IOException {
        long startTimestamp = System.currentTimeMillis();
//        parse file to BufferedReader
        BufferedReader br = CommonUtils.parseToBufferedReader(file);

//        process the business logic based on BufferedReader
        String res = tradeEnrichmentService.processData(br);

//        construct response headers of the CSV format for browser download the result data as CSV file
        HttpHeaders Headers = CommonUtils.constructCsvResponseHeaders(OUTPUT_FILENAME);

//        response with CREATED status
        ResponseEntity<byte[]> filebyte = new ResponseEntity<>(res.getBytes(), Headers, HttpStatus.CREATED);

        long endTimestamp = System.currentTimeMillis();
        long cost = (endTimestamp - startTimestamp) / 1000;

        LOG.info("Total Cost: {}s", cost);

        return filebyte;
    }

}


