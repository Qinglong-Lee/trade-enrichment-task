package com.verygoodbank.tes.service;

import java.io.BufferedReader;
import java.io.IOException;

public interface TradeEnrichmentService {
    String processData(BufferedReader br) throws IOException;
}
