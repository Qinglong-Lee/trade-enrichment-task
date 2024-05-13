package com.verygoodbank.tes.service;

import java.util.List;
import java.util.concurrent.Future;

public interface AsyncProcessService {
    Future<List<String>> batchMapProductIdToName(List<String> batchTrades);
}
